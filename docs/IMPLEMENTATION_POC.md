# Proof of Concept Implementation Guide

## Overview
Based on the reverse engineering analysis of the reference APK, we now have a clear path to implement seat memory functionality for BYD vehicles using the discovered Hardware Abstraction Layer (HAL).

## Implementation Strategy

### Method 1: BYD HAL Integration (Recommended)
This approach uses BYD's official hardware abstraction layer discovered in the reference app.

#### Step 1: HAL Discovery and Testing
```java
public class BYDHardwareDetector {
    private static final String TAG = "BYDHardware";
    
    public static boolean isBYDHALAvailable(Context context) {
        try {
            Class.forName("android.hardware.bydauto.AbsBYDAutoDevice");
            Log.i(TAG, "BYD HAL classes found");
            return true;
        } catch (ClassNotFoundException e) {
            Log.w(TAG, "BYD HAL not available");
            return false;
        }
    }
    
    public static Object getBodyworkDevice(Context context) {
        try {
            Class<?> bodyworkClass = Class.forName(
                "android.hardware.bydauto.bodywork.BYDAutoBodyworkDevice");
            Method getInstance = bodyworkClass.getMethod("getInstance", Context.class);
            return getInstance.invoke(null, context);
        } catch (Exception e) {
            Log.e(TAG, "Failed to get BYDAutoBodyworkDevice", e);
            return null;
        }
    }
}
```

#### Step 2: Seat Control Interface
```java
public class BYDSeatController {
    private Object bodyworkDevice;
    private Class<?> bodyworkClass;
    
    public BYDSeatController(Context context) {
        bodyworkDevice = BYDHardwareDetector.getBodyworkDevice(context);
        if (bodyworkDevice != null) {
            bodyworkClass = bodyworkDevice.getClass();
            discoverMethods();
        }
    }
    
    private void discoverMethods() {
        // Use reflection to find seat control methods
        Method[] methods = bodyworkClass.getDeclaredMethods();
        for (Method method : methods) {
            String name = method.getName();
            Log.d("BYDSeat", "Available method: " + name);
            
            // Look for seat-related methods
            if (name.contains("Seat") || name.contains("Comfort") || 
                name.contains("Position")) {
                Log.i("BYDSeat", "Potential seat method: " + name);
            }
        }
    }
    
    public boolean setDriverComfortStage(int stage) {
        if (stage < -2 || stage > 2) {
            Log.w("BYDSeat", "Invalid comfort stage: " + stage);
            return false;
        }
        
        try {
            Method setMethod = bodyworkClass.getMethod("setDriverComfortStage", int.class);
            setMethod.invoke(bodyworkDevice, stage);
            return true;
        } catch (Exception e) {
            Log.e("BYDSeat", "Failed to set driver comfort stage", e);
            return false;
        }
    }
    
    public int getDriverComfortStage() {
        try {
            Method getMethod = bodyworkClass.getMethod("getDriverComfortStage");
            return (Integer) getMethod.invoke(bodyworkDevice);
        } catch (Exception e) {
            Log.e("BYDSeat", "Failed to get driver comfort stage", e);
            return 0; // Default position
        }
    }
}
```

#### Step 3: Socket.IO Communication Layer
```java
public class SeatMemoryServer {
    private static final int PORT = 8080;
    private BYDSeatController seatController;
    
    public void startServer(Context context) {
        seatController = new BYDSeatController(context);
        
        // Implement Socket.IO server similar to reference app
        SocketIOServer server = new SocketIOServer(PORT);
        
        server.addEventListener("LoadSeatMemory", LoadSeatMemoryData.class, 
            (client, data, ackRequest) -> {
                handleLoadSeatMemory(data);
            });
            
        server.addEventListener("SetSeatComfort", SetSeatComfortData.class,
            (client, data, ackRequest) -> {
                handleSetSeatComfort(data);
            });
            
        server.start();
        Log.i("SeatMemory", "Socket.IO server started on port " + PORT);
    }
    
    private void handleLoadSeatMemory(LoadSeatMemoryData data) {
        int position = data.getPosition();
        String seat = data.getSeat();
        
        if ("driver".equals(seat)) {
            seatController.setDriverComfortStage(position);
        } else if ("passenger".equals(seat)) {
            seatController.setPassengerComfortStage(position);
        }
    }
}
```

### Method 2: Reference App Integration (Fallback)
If direct HAL access is restricted, integrate with the existing reference app.

#### Step 1: Broadcast Receiver
```java
public class SeatMemoryIntegration {
    private Context context;
    
    public void loadSeatPosition(int position) {
        Intent intent = new Intent("br.com.rory.reference.LOAD_SEAT_MEMORY");
        intent.putExtra("position", position);
        intent.setPackage("br.com.rory.reference");
        context.sendBroadcast(intent, "br.com.rory.reference.PERMISSION_LOAD_SEAT_MEMORY");
    }
}
```

#### Step 2: Socket.IO Client
```java
public class ReferenceSocketClient {
    private Socket socket;
    
    public void connectToReference() {
        try {
            socket = IO.socket("http://127.0.0.1:8080");
            socket.connect();
            
            socket.on("connect", args -> {
                Log.i("Reference", "Connected to reference app");
            });
            
        } catch (URISyntaxException e) {
            Log.e("Reference", "Failed to connect", e);
        }
    }
    
    public void loadSeatMemory(int position, String seat) {
        JSONObject data = new JSONObject();
        try {
            data.put("position", position);
            data.put("seat", seat);
            socket.emit("LoadSeatMemory", data);
        } catch (JSONException e) {
            Log.e("Reference", "Failed to send command", e);
        }
    }
}
```

## Database Implementation

### SQLite Schema
```java
public class SeatMemoryDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "seat_memory.db";
    private static final int DATABASE_VERSION = 1;
    
    private static final String CREATE_SEAT_SETTINGS = 
        "CREATE TABLE seat_settings (" +
        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "profile_name TEXT NOT NULL, " +
        "driver_comfort_stage INTEGER NOT NULL " +
            "CHECK(driver_comfort_stage >= -2 AND driver_comfort_stage <= 2), " +
        "passenger_comfort_stage INTEGER NOT NULL " +
            "CHECK(passenger_comfort_stage >= -2 AND passenger_comfort_stage <= 2), " +
        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SEAT_SETTINGS);
    }
    
    public void saveProfile(String profileName, int driverStage, int passengerStage) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("profile_name", profileName);
        values.put("driver_comfort_stage", driverStage);
        values.put("passenger_comfort_stage", passengerStage);
        
        db.insert("seat_settings", null, values);
    }
}
```

## Android App Structure

### MainActivity
```java
public class MainActivity extends AppCompatActivity {
    private BYDSeatController seatController;
    private SeatMemoryDatabase database;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Check if BYD HAL is available
        if (BYDHardwareDetector.isBYDHALAvailable(this)) {
            seatController = new BYDSeatController(this);
            setupSeatControls();
        } else {
            showHALNotAvailableDialog();
        }
        
        database = new SeatMemoryDatabase(this);
    }
    
    private void setupSeatControls() {
        findViewById(R.id.btn_position_minus2).setOnClickListener(v -> 
            seatController.setDriverComfortStage(-2));
        findViewById(R.id.btn_position_minus1).setOnClickListener(v -> 
            seatController.setDriverComfortStage(-1));
        findViewById(R.id.btn_position_center).setOnClickListener(v -> 
            seatController.setDriverComfortStage(0));
        findViewById(R.id.btn_position_plus1).setOnClickListener(v -> 
            seatController.setDriverComfortStage(1));
        findViewById(R.id.btn_position_plus2).setOnClickListener(v -> 
            seatController.setDriverComfortStage(2));
    }
}
```

## Android Manifest Configuration
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.byd.seatmemory">

    <!-- Required permissions -->
    <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    
    <!-- Custom permission for seat memory -->
    <permission
        android:name="com.byd.seatmemory.PERMISSION_LOAD_SEAT_MEMORY"
        android:protectionLevel="signature"/>
    
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme">
        
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
        
        <!-- Background service for seat control -->
        <service
            android:name=".SeatMemoryService"
            android:enabled="true"
            android:exported="false"/>
            
        <!-- Receiver for seat memory commands -->
        <receiver
            android:name=".receiver.SeatMemoryReceiver"
            android:permission="com.byd.seatmemory.PERMISSION_LOAD_SEAT_MEMORY"
            android:exported="true">
            <intent-filter>
                <action android:name="com.byd.seatmemory.LOAD_SEAT_MEMORY"/>
            </intent-filter>
        </receiver>
        
    </application>
</manifest>
```

## Testing Strategy

### Unit Tests
```java
@RunWith(AndroidJUnit4.class)
public class SeatControllerTest {
    
    @Test
    public void testValidComfortStageRange() {
        for (int i = -2; i <= 2; i++) {
            assertTrue("Stage " + i + " should be valid", 
                SeatController.isValidComfortStage(i));
        }
    }
    
    @Test
    public void testInvalidComfortStageRange() {
        assertFalse(SeatController.isValidComfortStage(-3));
        assertFalse(SeatController.isValidComfortStage(3));
    }
}
```

### Integration Tests
```java
@RunWith(AndroidJUnit4.class)
public class BYDHALIntegrationTest {
    
    @Test
    public void testBYDHALAvailability() {
        Context context = InstrumentationRegistry.getTargetContext();
        boolean available = BYDHardwareDetector.isBYDHALAvailable(context);
        
        if (available) {
            assertNotNull(BYDHardwareDetector.getBodyworkDevice(context));
        } else {
            Log.w("Test", "BYD HAL not available on this device");
        }
    }
}
```

## Deployment Considerations

### APK Signing
The app must be signed with the same certificate as the reference app to share permissions, or use its own permission system.

### Installation Methods
1. **Sideload via USB**: Standard APK installation
2. **Third-party apps folder**: For compatible firmware versions
3. **ADB installation**: For developer-enabled systems

### Error Handling
```java
public class SeatControlErrorHandler {
    public static void handleSeatControlError(Exception e, String operation) {
        if (e instanceof SecurityException) {
            Log.e("SeatControl", "Permission denied for " + operation);
            // Show permission request dialog
        } else if (e instanceof ClassNotFoundException) {
            Log.e("SeatControl", "BYD HAL not available");
            // Fall back to reference app integration
        } else {
            Log.e("SeatControl", "Unknown error in " + operation, e);
            // Show generic error message
        }
    }
}
```

## Conclusion

This proof-of-concept implementation provides two pathways:
1. **Direct BYD HAL access** - Using the discovered hardware abstraction layer
2. **Reference app integration** - Working with the existing reference app via Socket.IO

The implementation maintains the same architecture patterns discovered in the reverse engineering analysis, ensuring compatibility with BYD's system design.