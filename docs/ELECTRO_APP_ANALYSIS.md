# Electro App Reverse Engineering Analysis

## Executive Summary

The Electro APK (version 1.3.5) has been successfully decompiled and analyzed. The app implements seat memory functionality for BYD vehicles through a combination of Java application layer and native C/C++ libraries. **Key finding: The actual seat control logic is implemented in native code, making it difficult to fully reverse engineer without additional tools.**

## Application Architecture

### Package Structure
- **Package Name**: `br.com.rory.electro`
- **Version**: 1.3.5 (versionCode: 30)
- **Target SDK**: Android API 25 (Android 7.1)
- **Developer**: Rory (Brazilian developer)

### Core Components

#### 1. Java Layer (Application Logic)
- **MainActivity**: Primary UI and user interaction
- **MainService**: Background service for continuous operation
- **LoadSeatMemoryReceiver**: Broadcast receiver for seat memory operations
- **Socket.IO Integration**: Network communication layer

#### 2. Native Layer (Vehicle Communication)
- **libelectropkg.so**: Main native library containing seat control logic
- **libelectrolib.so**: Additional native functionality
- **libnative-lib.so**: Supporting native operations

## Seat Memory Implementation

### Database Schema
```sql
CREATE TABLE seat_settings (
    driver_comfort_stage INTEGER NOT NULL 
        CHECK(driver_comfort_stage >= -2 AND driver_comfort_stage <= 2),
    passenger_comfort_stage INTEGER NOT NULL 
        CHECK(passenger_comfort_stage >= -2 AND passenger_comfort_stage <= 2)
)
```

### Key Classes for Seat Control

#### 1. LoadSeatMemory Action
- **File**: `br.com.rory.electro.socket.io.action.LoadSeatMemory`
- **Method**: `execute(Context context, d socketIOManager, JSONObject data)`
- **Implementation**: Native method (JNI)
- **Purpose**: Loads and applies saved seat position

#### 2. SetSeatComfort Action
- **File**: `br.com.rory.electro.socket.io.action.SetSeatComfort`
- **Method**: `execute(Context context, d socketIOManager, JSONObject data)`
- **Implementation**: Native method (JNI)
- **Purpose**: Adjusts seat comfort settings

#### 3. GetSeatComfort Action
- **File**: `br.com.rory.electro.socket.io.action.GetSeatComfort`
- **Method**: `execute(Context context, d socketIOManager, JSONObject data)`
- **Implementation**: Native method (JNI)
- **Purpose**: Reads current seat positions

### Broadcast Receiver System
- **Action**: `br.com.rory.electro.LOAD_SEAT_MEMORY`
- **Permission**: `br.com.rory.electro.PERMISSION_LOAD_SEAT_MEMORY`
- **Security**: Signature-level permission (only same-signed apps)

## Communication Architecture

### Network Layer
The app uses a multi-layered communication approach:

1. **Local Socket.IO Server**: Runs on `127.0.0.1` (localhost)
2. **API Server**: Multiple server instances for different functions
   - MainService Server
   - SSBGS Server  
   - HBSApiServer
3. **Client-Server Model**: App acts as both client and server

### Protocol Analysis

#### Seat Control Flow
```
Android App → Socket.IO → Native Library → Vehicle Communication
     ↓              ↓            ↓                    ↓
User Input → JSON Commands → JNI Calls → CAN/Serial Protocol
```

#### Seat Position Values
- **Range**: -2 to +2 for both driver and passenger
- **Storage**: SQLite database with constraint validation
- **Types**: `driver_comfort_stage`, `passenger_comfort_stage`

## Key Findings

### 1. Communication Method
- **Primary**: Local socket communication via 127.0.0.1
- **Secondary**: Direct native library calls
- **Protocol**: Custom JSON-based commands over Socket.IO

### 2. Seat Control Limitations
- **Positions**: Only 5 discrete positions (-2, -1, 0, +1, +2)
- **Scope**: Driver and passenger seats only
- **Parameters**: "Comfort stage" rather than precise positioning

### 3. Native Implementation
- **All seat control logic is in native C/C++ code**
- **Java layer only handles UI and communication routing**
- **Reverse engineering vehicle protocol requires native code analysis**

### 4. Security Model
- Custom permission system for seat operations
- Signature-level protection
- Local-only communication (no external network access for seat control)

## Technical Limitations Discovered

### 1. Obfuscation
- Java code is partially obfuscated (class names like `a`, `b`, `c`)
- Native libraries are stripped of symbols
- Critical logic hidden in native implementation

### 2. Hardware Dependency  
- Native libraries compiled for ARM64 only
- Requires specific BYD vehicle integration
- Cannot be fully tested without actual vehicle hardware

### 3. Protocol Abstraction
- Vehicle communication protocol abstracted away
- No clear indication of CAN bus, serial, or other communication method
- Custom implementation per vehicle model likely

## Recommended Implementation Strategy

Based on this analysis, here's how to recreate the functionality:

### Phase 1: Architecture Replication
```java
// Java Application Layer
public class SeatMemoryService {
    private native void loadSeatPosition(int position);
    private native void saveSeatPosition(int position, SeatData data);
    private native SeatData getCurrentPosition();
}

// Native Layer (C/C++)
JNIEXPORT void JNICALL
Java_SeatMemoryService_loadSeatPosition(JNIEnv *env, jobject thiz, jint position) {
    // Vehicle communication implementation
    // This is where the actual CAN/serial protocol would go
}
```

### Phase 2: Database Layer
```sql
CREATE TABLE seat_profiles (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    driver_position INTEGER CHECK(driver_position >= -2 AND driver_position <= 2),
    passenger_position INTEGER CHECK(passenger_position >= -2 AND passenger_position <= 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Phase 3: Communication Protocol
- Implement Socket.IO server on localhost
- Create JSON command structure for seat operations
- Add permission system for security

## Missing Pieces

To fully replicate the Electro app functionality, we still need:

1. **Vehicle Protocol**: The actual CAN bus or serial communication protocol
2. **Hardware Integration**: How the app communicates with BYD's systems
3. **Calibration Data**: How seat positions map to motor positions
4. **Safety Mechanisms**: Speed checks, position limits, emergency stops

## Conclusion

The Electro app provides a solid architectural foundation for BYD seat memory functionality. However, the critical vehicle communication logic is implemented in native code that requires additional reverse engineering tools (like Ghidra or IDA Pro) to fully understand.

The app's approach of using:
- Local socket communication
- Native libraries for hardware access
- SQLite for profile storage
- Custom permission system

...provides a good blueprint for our own implementation, though we'll need to reverse engineer or develop our own vehicle communication protocol.

## Next Steps

1. **Analyze native libraries** with Ghidra/IDA Pro for vehicle protocol
2. **Monitor CAN bus** during Electro app seat operations
3. **Implement Socket.IO server** based on discovered architecture
4. **Create database schema** matching Electro's approach
5. **Build Android app** following the architectural patterns discovered