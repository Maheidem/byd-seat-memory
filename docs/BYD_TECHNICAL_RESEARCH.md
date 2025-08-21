# BYD Technical Research Documentation

## DiLink System Architecture

### Hardware Specifications
- **Processor**: Qualcomm QCM6125 (Snapdragon 665)
- **RAM**: 3-4 GB depending on model
- **Storage**: 32-64 GB eMMC
- **Display**: 12.8-inch IPS, 1920x1280 resolution, capacitive touch
- **Connectivity**: 4G LTE, Wi-Fi 802.11ac, Bluetooth 5.0
- **OS Base**: Android 10/11 (varies by DiLink version)

### DiLink Versions
- **DiLink 3.0**: Android 10 base, found in older models
- **DiLink 4.0**: Android 11 base, current in Dolphin Plus
- **DiLink 5.0**: Latest version with enhanced security

### System Architecture
```
┌─────────────────────────────────────┐
│     Android Application Layer        │
├─────────────────────────────────────┤
│     BYD Custom Framework Layer       │
├─────────────────────────────────────┤
│     Android HAL (Hardware Layer)     │
├─────────────────────────────────────┤
│     Vehicle Network Interface        │
├─────────────────────────────────────┤
│  CAN Bus │ LIN Bus │ FlexRay │ MOST │
└─────────────────────────────────────┘
```

## Vehicle Network Access

### CAN Bus Networks
1. **Powertrain CAN**: 500 kbps - Motor, battery, inverter
2. **Body CAN**: 125 kbps - Lights, windows, locks
3. **Comfort CAN**: 125 kbps - HVAC, seats, mirrors
4. **Charging CAN**: 250 kbps - Charging system

### OBD-II Port Pinout (BYD Specific)
```
Pin 1:  Manufacturer discretion
Pin 2:  J1850 Bus+ (not used)
Pin 3:  Manufacturer discretion  
Pin 4:  Chassis ground
Pin 5:  Signal ground
Pin 6:  CAN High (J-2284)
Pin 7:  K-Line (ISO 9141-2)
Pin 8:  Manufacturer discretion
Pin 9:  Charging CAN Low (BYD specific)
Pin 10: Charging CAN High (BYD specific)
Pin 11: Manufacturer discretion
Pin 12: Manufacturer discretion
Pin 13: Manufacturer discretion
Pin 14: CAN Low (J-2284)
Pin 15: L-Line (ISO 9141-2)
Pin 16: Battery voltage
```

### Seat Control Architecture
- Seats connected via **LIN bus** (Local Interconnect Network)
- LIN master node in Body Control Module (BCM)
- Seat ECU controls motors for:
  - Forward/backward position
  - Height adjustment
  - Backrest angle
  - Lumbar support (if equipped)
  - Headrest position

### Known CAN IDs (Partial List)
```
0x1D0: Vehicle speed
0x1D1: Battery SOC
0x1D2: Battery voltage/current
0x2C0: Climate control status
0x2C1: Door status
0x2C2: Light status
0x3B0: Seat heating/ventilation (not position)
```

## App Installation Methods

### Method 1: Firmware Downgrade
1. Download firmware version 2307 or earlier
2. Copy to USB drive root directory
3. Create file `update.txt` with content: `GHY0613byd`
4. Insert USB and follow update prompts
5. After downgrade, use "third party apps" folder

### Method 2: ADB Activation
1. Access Bluetooth settings
2. Tap version number 7 times
3. Enter password: `20211231`
4. Navigate to hidden developer menu
5. Submit IMEI to activation service
6. Enable ADB and install apps

### Method 3: Alternative Package Manager
1. Download modified package manager APK
2. Use wireless ADB to push to device
3. Replace system package manager
4. Install apps through new manager

## BYD API Analysis

### Official BYD App API
- **Base URL**: `https://app-api.byd.com/v1/`
- **Authentication**: OAuth 2.0 with refresh tokens
- **Encryption**: AES/CBC/PKCS5Padding

### Available Endpoints
```
/vehicle/status - Get vehicle status
/vehicle/control/climate - Control HVAC
/vehicle/control/locks - Control door locks
/vehicle/control/seats/heating - Control seat heating
/vehicle/control/seats/ventilation - Control seat ventilation
```

**Note**: No seat position control endpoints found

### API Request Example
```http
POST /vehicle/control/seats/heating
Authorization: Bearer {token}
Content-Type: application/json

{
  "seat": "driver",
  "level": 3,
  "duration": 10
}
```

## Reverse Engineering Findings

### Seat Control Protocol (Hypothetical)
Based on LIN bus analysis from similar vehicles:
```
Frame ID: 0x3D (master request)
Response: 0x3E (slave response)

Data format (8 bytes):
Byte 0: Command (0x01=read, 0x02=write)
Byte 1: Seat selection (0x01=driver, 0x02=passenger)
Byte 2: Position type (0x01=forward/back, 0x02=height, etc.)
Byte 3-4: Position value (0-1023)
Byte 5: Speed (0-255)
Byte 6-7: Checksum
```

### Security Considerations
- CAN bus messages not encrypted
- LIN bus uses simple checksum, not cryptographic
- Android app signing required for system-level access
- SELinux policies restrict hardware access

## Development Tools

### Required Hardware
1. **OBD-II Adapter**: ELM327 or compatible
2. **CAN Interface**: CANable, PCAN-USB, or similar
3. **Logic Analyzer**: For LIN bus sniffing
4. **Android Device**: For app development/testing

### Software Tools
- **Android Studio**: App development
- **can-utils**: CAN bus utilities for Linux
- **Wireshark**: Network protocol analysis
- **IDA Pro/Ghidra**: APK reverse engineering
- **CANalyze**: CAN bus analysis tool

## Community Resources

### GitHub Projects
- [OVMS (Open Vehicle Monitoring System)](https://github.com/openvehicles/Open-Vehicle-Monitoring-System-3)
- [python-OBD](https://github.com/brendan-w/python-OBD)
- [BYD-Battery-Emulator](https://github.com/dalathegreat/BYD-Battery-Emulator)

### Forums & Communities
- XDA Developers BYD threads
- Reddit r/BYD_Atto_3
- Facebook: BYD Owners Brazil
- Telegram: BYD Hacking Community

### Documentation Sources
- BYD Developer Portal (oip.byd.com) - Chinese only
- Service manuals from BYD dealers
- Reverse engineering wikis

## Legal & Safety Considerations

### Legal
- Modifying vehicle software may void warranty
- Check local regulations on vehicle modifications
- Ensure compliance with automotive safety standards

### Safety
- Never adjust seats while driving
- Implement position limits to prevent damage
- Test thoroughly before deployment
- Include emergency stop functionality
- Maintain manual override capability

## Implementation Roadmap

### Phase 1: Foundation (Weeks 1-2)
- Set up development environment
- Create basic Android app structure
- Implement profile storage system
- Design UI for seat memory management

### Phase 2: Research (Weeks 3-6)
- Monitor CAN/LIN bus during seat operation
- Reverse engineer BYD app for hidden APIs
- Test OBD-II communication methods
- Document discovered protocols

### Phase 3: Integration (Weeks 7-10)
- Implement discovered communication method
- Add seat control functionality
- Test on actual vehicle
- Refine based on testing

### Phase 4: Polish (Weeks 11-12)
- Optimize performance
- Add safety features
- Create installation guide
- Prepare for distribution