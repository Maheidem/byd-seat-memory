# BYD Seat Memory Protocol Analysis

## Major Discovery: BYD Hardware Abstraction Layer (HAL)

### BYD Auto Hardware Framework
The Electro app reveals that BYD vehicles expose hardware functionality through Android's Hardware Abstraction Layer (HAL) with custom BYD classes:

```java
// BYD Hardware Framework Classes Found
android.hardware.bydauto.AbsBYDAutoDevice                    // Base device class
android.hardware.bydauto.ac.BYDAutoAcDevice                 // Air conditioning
android.hardware.bydauto.bodywork.BYDAutoBodyworkDevice     // Body controls (likely includes seats)
android.hardware.bydauto.setting.BYDAutoSettingDevice       // Vehicle settings
android.hardware.bydauto.speed.BYDAutoSpeedDevice           // Speed information
android.hardware.bydauto.gearbox.BYDAutoGearboxDevice       // Transmission
android.hardware.bydauto.instrument.BYDAutoInstrumentDevice // Dashboard
android.hardware.bydauto.statistic.BYDAutoStatisticDevice   // Statistics
```

## Seat Control Implementation

### Database Schema (Confirmed)
```sql
CREATE TABLE seat_settings (
    driver_comfort_stage INTEGER NOT NULL 
        CHECK(driver_comfort_stage >= -2 AND driver_comfort_stage <= 2),
    passenger_comfort_stage INTEGER NOT NULL 
        CHECK(passenger_comfort_stage >= -2 AND passenger_comfort_stage <= 2)
)
```

### Seat Control Architecture
```
Android App Layer
       ↓
Socket.IO (127.0.0.1)
       ↓
Java Native Interface (JNI)
       ↓
Native Library (libelectropkg.so)
       ↓
BYD Hardware Abstraction Layer
       ↓
BYDAutoBodyworkDevice
       ↓
Vehicle CAN/LIN Bus
```

## Communication Protocol Details

### Socket.IO Commands
The app uses Socket.IO for internal communication with these action types:
- `LoadSeatMemory` - Load saved seat position
- `SetSeatComfort` - Set seat comfort level
- `GetSeatComfort` - Read current seat position
- `GetAppConfig` - Application configuration

### Command Structure
Based on string analysis, commands follow this pattern:
```json
{
  "action": "LoadSeatMemory",
  "position": 1,
  "seat": "driver"
}
```

### Service Architecture
The app runs multiple background services:
1. **MainService** - Primary service on local port
2. **SSBGS Service** - Secondary service 
3. **HBSApiServer** - Hardware Bridge Service API
4. **IBS Service** - Unknown purpose
5. **HBS Service** - Hardware Bridge Service

### Service Launching Commands
```bash
/system/bin --nice-name=ssbgs br.com.rory.electro.service.SSBGS > /dev/null 2>&1
/system/bin --nice-name=hbs br.com.rory.electro.service.h > /dev/null 2>&1
/system/bin --nice-name=ibs br.com.rory.electro.service.i > /dev/null 2>&1
```

## BYD Hardware Access Method

### Key Discovery: BYDAutoBodyworkDevice
The seat control appears to be implemented through:
```java
BYDAutoBodyworkDevice bodyworkDevice = getBYDAutoBodyworkDevice(context);
// Seat adjustment methods would be called on this device
```

### Method Signatures (Inferred)
Based on the analysis, the BYD HAL likely provides methods like:
```java
public class BYDAutoBodyworkDevice {
    public void setDriverComfortStage(int stage);      // -2 to +2
    public void setPassengerComfortStage(int stage);   // -2 to +2
    public int getDriverComfortStage();
    public int getPassengerComfortStage();
    
    // Listener for seat position changes
    public void addBodyworkListener(AbsBYDAutoBodyworkListener listener);
}
```

## Error Handling and Validation

### Command Processing
The app includes robust error handling:
- "Error processing command" - Invalid command structure
- "Invalid command format" - Malformed JSON
- "BYDAcquisitionClient instance is null" - Hardware access failure

### Position Validation
- Driver/Passenger comfort stages: -2 to +2 (5 discrete positions)
- Database constraints prevent invalid values
- Real-time validation before sending to hardware

## Security Model

### Permission System
```xml
<permission 
    android:name="br.com.rory.electro.PERMISSION_LOAD_SEAT_MEMORY"
    android:protectionLevel="signature"/>
```

### Broadcast Receiver Protection
```xml
<receiver 
    android:name="br.com.rory.electro.receiver.LoadSeatMemoryReceiver"
    android:permission="br.com.rory.electro.PERMISSION_LOAD_SEAT_MEMORY"
    android:exported="true">
    <intent-filter>
        <action android:name="br.com.rory.electro.LOAD_SEAT_MEMORY"/>
    </intent-filter>
</receiver>
```

## Implementation Strategy for Recreation

### Phase 1: BYD HAL Integration
```java
// Attempt to access BYD's hardware framework
try {
    Class<?> bodyworkClass = Class.forName("android.hardware.bydauto.bodywork.BYDAutoBodyworkDevice");
    Method getInstance = bodyworkClass.getMethod("getInstance", Context.class);
    Object bodyworkDevice = getInstance.invoke(null, context);
    
    // Try to find seat control methods
    Method setDriverComfort = bodyworkClass.getMethod("setDriverComfortStage", int.class);
    setDriverComfort.invoke(bodyworkDevice, comfortLevel);
    
} catch (ClassNotFoundException | NoSuchMethodException e) {
    // Fallback to custom implementation
    Log.e("SeatMemory", "BYD HAL not available, using fallback");
}
```

### Phase 2: Socket.IO Server Implementation
```javascript
const io = require('socket.io')(server);

io.on('connection', (socket) => {
    socket.on('LoadSeatMemory', (data) => {
        const { position, seat } = data;
        // Call BYD HAL or custom implementation
        loadSeatPosition(seat, position);
    });
    
    socket.on('SetSeatComfort', (data) => {
        const { seat, comfortStage } = data;
        setSeatComfort(seat, comfortStage);
    });
});
```

### Phase 3: Database Layer
```java
public class SeatDatabase {
    private static final String CREATE_TABLE = 
        "CREATE TABLE seat_settings (" +
        "driver_comfort_stage INTEGER NOT NULL " +
        "CHECK(driver_comfort_stage >= -2 AND driver_comfort_stage <= 2), " +
        "passenger_comfort_stage INTEGER NOT NULL " +
        "CHECK(passenger_comfort_stage >= -2 AND passenger_comfort_stage <= 2))";
    
    public void saveSeatSettings(int driverStage, int passengerStage) {
        // Validate range and save to database
    }
}
```

## Critical Questions for Further Research

1. **BYD HAL Availability**: Are the `android.hardware.bydauto.*` classes available on all BYD vehicles?

2. **Method Signatures**: What are the exact method names and parameters for seat control?

3. **Permissions**: Does accessing BYD HAL require special system permissions?

4. **Hardware Detection**: How does the app detect if seat memory hardware is available?

5. **Position Mapping**: How do the -2 to +2 values map to actual motor positions?

## Next Steps for Implementation

1. **Test BYD HAL Access**: Create a simple app to test if BYD's hardware classes are accessible
2. **Reverse Engineer Methods**: Use reflection to discover available methods on BYDAutoBodyworkDevice
3. **Monitor System Calls**: Use strace/ltrace to see what system calls the Electro app makes
4. **CAN Bus Monitoring**: If HAL access fails, fall back to direct CAN bus monitoring
5. **Create Minimal POC**: Build a proof-of-concept using discovered architecture

## Conclusion

The Electro app reveals that BYD provides a comprehensive Hardware Abstraction Layer for vehicle functions. This is the key to implementing seat memory functionality - we need to access the `BYDAutoBodyworkDevice` class rather than implementing low-level CAN bus communication.

This discovery significantly simplifies the implementation approach and provides a much more reliable method for seat control than reverse engineering the CAN protocol.