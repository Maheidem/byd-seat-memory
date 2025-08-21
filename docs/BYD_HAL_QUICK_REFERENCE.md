# BYD HAL Quick Reference Guide

## Essential Classes & Methods

### Core Device Access
```java
// Check HAL availability
boolean isAvailable = BYDHardwareDetector.isBYDHALAvailable(context);

// Get device instances
BYDAutoBodyworkDevice bodywork = BYDAutoBodyworkDevice.getInstance(context);
BYDAutoSpeedDevice speed = BYDAutoSpeedDevice.getInstance(context);
BYDAutoSettingDevice setting = BYDAutoSettingDevice.getInstance(context);
```

### Seat Control (Primary Functions)
```java
// Set seat positions (range: -2 to +2)
bodywork.setDriverComfortStage(1);      // Driver seat forward
bodywork.setPassengerComfortStage(-1);  // Passenger seat reclined

// Get current positions
int driverPos = bodywork.getDriverComfortStage();
int passengerPos = bodywork.getPassengerComfortStage();

// Safety check before adjustment
boolean safe = speed.isSafeForSeatAdjustment();
```

### Memory Profile Management
```java
// Save current position as profile
SeatProfile profile = new SeatProfile(driverPos, passengerPos);
setting.saveSeatMemoryProfile("Profile1", profile);

// Load saved profile
SeatProfile loaded = setting.loadSeatMemoryProfile("Profile1");
bodywork.setDriverComfortStage(loaded.getDriverStage());

// Get all profiles
List<String> profiles = setting.getAvailableSeatProfiles();
```

## Common Patterns

### HAL Detection & Fallback
```java
public class SeatController {
    private boolean useHAL;
    private BYDAutoBodyworkDevice device;
    
    public SeatController(Context context) {
        useHAL = BYDHardwareDetector.isBYDHALAvailable(context);
        if (useHAL) {
            device = BYDAutoBodyworkDevice.getInstance(context);
        } else {
            // Fall back to Electro app integration
            setupSocketIOClient();
        }
    }
    
    public boolean setSeatPosition(int position) {
        if (useHAL) {
            return setViaHAL(position);
        } else {
            return setViaElectroApp(position);
        }
    }
}
```

### Error Handling Template
```java
try {
    bodyworkDevice.setDriverComfortStage(position);
    return true;
} catch (ClassNotFoundException e) {
    Log.e(TAG, "BYD HAL not available");
    return useElectroAppFallback(position);
} catch (SecurityException e) {
    Log.e(TAG, "Permission denied");
    requestPermissions();
    return false;
} catch (Exception e) {
    Log.e(TAG, "HAL error", e);
    return false;
}
```

### Event Listener Setup
```java
bodyworkDevice.addBodyworkListener(new AbsBYDAutoBodyworkListener() {
    @Override
    public void onDriverSeatPositionChanged(int newStage) {
        updateUI(newStage);
    }
    
    @Override
    public void onBodyworkError(int errorCode, String errorMessage) {
        handleError(errorCode, errorMessage);
    }
    
    // Implement other required methods...
});
```

## Key Constants

### Seat Position Ranges
```java
public static final int MIN_COMFORT_STAGE = -2;  // Most reclined
public static final int MAX_COMFORT_STAGE = 2;   // Most forward  
public static final int NEUTRAL_POSITION = 0;    // Default position
```

### Error Codes
```java
public static final int DEVICE_NOT_AVAILABLE = 1001;
public static final int PERMISSION_DENIED = 1002;
public static final int SAFETY_INTERLOCK = 1006;
```

## Validation Functions
```java
// Position validation
public static boolean isValidComfortStage(int stage) {
    return stage >= -2 && stage <= 2;
}

// Safety validation
public static boolean canAdjustSeats(BYDAutoSpeedDevice speedDevice) {
    return speedDevice.isSafeForSeatAdjustment();
}
```

## Debugging Commands
```java
// Log all available methods
Method[] methods = BYDAutoBodyworkDevice.class.getDeclaredMethods();
for (Method m : methods) {
    Log.d("HAL", "Method: " + m.getName());
}

// Test device availability
AbsBYDAutoDevice device = BYDAutoBodyworkDevice.getInstance(context);
Log.d("HAL", "Device available: " + device.isAvailable());
Log.d("HAL", "Device info: " + device.getDeviceInfo());
```

## Manifest Requirements
```xml
<!-- Basic permissions -->
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

<!-- For system-level access (may be required) -->
<uses-permission android:name="android.permission.WRITE_SECURE_SETTINGS"/>

<!-- Custom seat memory permission -->
<permission
    android:name="com.byd.seatmemory.PERMISSION_LOAD_SEAT_MEMORY"
    android:protectionLevel="signature"/>
```

## Testing Checklist
- [ ] HAL availability detection works
- [ ] Seat positions validate correctly (-2 to +2)
- [ ] Safety checks prevent adjustment while moving
- [ ] Memory profiles save/load correctly
- [ ] Error handling covers all scenarios
- [ ] Fallback method works when HAL unavailable
- [ ] Resource cleanup on app termination

## Common Issues & Quick Fixes

**ClassNotFoundException**: BYD HAL not available
- ✅ Add fallback to Electro app integration
- ✅ Check if running on actual BYD hardware

**SecurityException**: Permission denied
- ✅ Add required permissions to manifest
- ✅ Sign with system certificate if needed

**Device not available**: HAL reports unavailable
- ✅ Check vehicle ignition state
- ✅ Verify firmware version compatibility
- ✅ Restart infotainment system

**Method not found**: HAL method doesn't exist
- ✅ Use reflection to discover actual method names
- ✅ Check BYD HAL version compatibility