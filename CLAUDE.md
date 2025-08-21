# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is an Android application designed to add aftermarket seat memory functionality to the BYD Dolphin Plus (Brazilian model) infotainment system. The app is meant to be sideloaded onto the BYD's Android-based 12.8-inch rotating touchscreen and replaces the functionality of the "Electro" app that provides seat memory features.

## Target Platform

- **Vehicle**: BYD Dolphin Plus (Brazilian market)
- **Infotainment System**: Android-based OS with 12.8-inch rotating touchscreen
- **Installation Method**: APK sideloading (BYD's system doesn't have Play Store access)
- **Android Compatibility**: Must work with BYD's custom Android implementation

## Development Setup

### Android Development
```bash
# Initialize Android project (if not exists)
./gradlew init

# Build debug APK for testing
./gradlew assembleDebug

# Build release APK for sideloading
./gradlew assembleRelease

# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest

# Clean build
./gradlew clean
```

### APK Decompilation (for analysis)
```bash
# Decompile Electro.apk for reference
jadx -d decompiled_electro Electro.apk

# Analyze native library
strings decompiled_electro/resources/lib/arm64-v8a/libelectropkg.so | grep seat

# Extract APK resources
apktool d Electro.apk -o electro_resources
```

### Python Backend (if using serial/CAN communication)
```bash
# Create virtual environment
python3 -m venv venv
source venv/bin/activate  # On macOS/Linux

# Install dependencies
pip install -r requirements.txt

# Run CAN bus listener (for development/testing)
python can_listener.py

# Test seat control commands
python test_seat_control.py
```

## Architecture

### Core Components

1. **Android App Layer**
   - `MainActivity`: Main UI for seat position management
   - `SeatProfileActivity`: Manage multiple driver profiles
   - `SeatControlService`: Background service for seat motor control
   - `ProfileDatabase`: SQLite storage for seat positions

2. **Communication Layer**
   - **CAN Bus Interface**: Communicates with vehicle's CAN network for seat control
   - **Serial Communication**: Alternative method if direct CAN access is restricted
   - **BYD API Integration**: Interface with any available BYD system APIs

3. **Data Model**
   - Seat positions: Driver seat (forward/back, height, tilt, lumbar)
   - User profiles: Multiple driver support with quick switching
   - Preferences: App settings and calibration data

### Key Technical Challenges

1. **CAN Bus Access**: BYD's infotainment may restrict direct CAN bus access. Solutions:
   - Use Android's Vehicle HAL if available
   - Serial communication through USB/OBD-II adapter
   - Reverse engineer BYD's seat control protocol

2. **Persistence**: Seat positions must survive app restarts and system updates
   - Use Android's SharedPreferences for simple data
   - SQLite database for complex profile management

3. **UI Integration**: Must work well with BYD's rotating screen
   - Support both portrait and landscape orientations
   - Match BYD's UI design language for seamless integration

## Project Structure

```
byd-seat-memory/
├── app/                                    # Android application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/                      # Java/Kotlin source code
│   │   │   ├── res/                       # Resources (layouts, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   │   └── test/                          # Unit tests
│   └── build.gradle
├── decompiled_electro/                     # Electro APK analysis results
│   ├── sources/                           # Decompiled Java source
│   └── resources/                         # APK resources and native libraries
├── docs/                                   # Comprehensive documentation
│   ├── BYD_HAL_COMPLETE_GUIDE.md         # Complete HAL development guide
│   ├── BYD_HAL_QUICK_REFERENCE.md        # Quick reference for developers
│   ├── BYD_TECHNICAL_RESEARCH.md         # Technical research findings
│   ├── ELECTRO_APP_ANALYSIS.md           # Electro APK reverse engineering
│   ├── PROTOCOL_ANALYSIS.md              # Communication protocol details
│   ├── IMPLEMENTATION_POC.md             # Proof-of-concept implementation
│   ├── INSTALLATION_GUIDE.md             # App installation procedures
│   └── DEVELOPMENT_ROADMAP.md            # Project development plan
├── gradle/                                # Gradle wrapper
├── build.gradle                           # Root build configuration
├── settings.gradle                        # Project settings
├── Electro.apk                           # Reference APK for analysis
└── README.md

```

## BYD-Specific Technical Details

### DiLink System Specifications
- **Hardware**: Qualcomm QCM6125 (Snapdragon 665), 3-4GB RAM, 32-64GB storage
- **OS**: Android 10/11 (DiLink 4.0 on Dolphin Plus)
- **Display**: 12.8-inch rotating touchscreen (1920x1280)
- **Critical**: Firmware after Oct 2023 blocks sideloading

### Vehicle Communication
- **CAN Networks**: Multiple buses (powertrain, body, comfort, charging)
- **Seat Control**: Via LIN bus (not directly accessible from CAN)
- **OBD-II Access**: Pins 9-10 provide charging CAN access
- **Protocol**: No documented seat position control in BYD API

### Installation Methods (Priority Order)
1. **Firmware ≤2307**: Use "third_party_apps" folder with password `GHY0613byd`
2. **ADB Activation**: Submit IMEI for developer access, install via wireless ADB
3. **Firmware Downgrade**: Last resort for newer vehicles

### Critical Implementation Notes - Updated from Electro App Analysis
- **Electro App Architecture**: Uses Socket.IO server on localhost (127.0.0.1) + native libraries
- **Seat Positions**: Limited to 5 discrete levels (-2, -1, 0, +1, +2) called "comfort stages"
- **Database Schema**: `seat_settings` table with driver/passenger comfort stages
- **Communication Flow**: Android App → Socket.IO → JNI → Native Library → Vehicle Protocol
- **Security**: Custom signature-level permission `br.com.rory.electro.PERMISSION_LOAD_SEAT_MEMORY`
- **Key Challenge**: Vehicle communication protocol hidden in stripped native library `libelectropkg.so`

### Safety Requirements
- Implement vehicle speed check (prevent adjustment while moving)
- Add position limits (prevent mechanical damage)
- Include manual override capability
- Test thoroughly with CAN simulator before vehicle deployment

## Testing Strategy

1. **Emulator Testing**: Use Android emulator with custom screen dimensions (12.8-inch, rotatable)
2. **Hardware Testing**: Test on actual BYD Dolphin Plus infotainment system
3. **CAN Simulation**: Use CAN bus simulator for seat control testing
4. **Profile Testing**: Verify multiple user profiles and switching

## Important Notes

- The original "Electro" app functionality is being reverse-engineered/recreated
- BYD's Android system lacks Play Store, requiring APK sideloading
- CAN bus protocol for BYD seat control needs to be documented through testing
- App should be lightweight to run smoothly on infotainment hardware
- Consider battery/power management as the app runs on vehicle system