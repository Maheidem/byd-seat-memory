---
title: Android App Development and Sideloading for BYD Infotainment Systems
date: 2025-01-30
tags: [BYD, Android, APK, sideloading, Dolphin, infotainment]
status: active
confidence: high
---

# Executive Summary

BYD's Android-based infotainment systems have undergone significant security changes, particularly affecting the Dolphin model. While earlier firmware versions (pre-October 2023) allowed relatively easy APK sideloading via USB with passwords, newer updates have progressively locked down these capabilities. Current methods require firmware downgrading, ADB activation through IMEI-based verification, or alternative package manager installation. The Brazilian BYD Dolphin Plus uses the same Android platform but faces identical restrictions for aftermarket app installation.

# Research Questions

1. How can Android apps be sideloaded onto BYD infotainment systems?
2. What are the specific challenges for the Dolphin model?
3. What methods work with current firmware versions?
4. What are the technical requirements for app development?

# Findings

## Sources Consulted

- [MikeYip - Installing APK in BYD](https://www.mikeyip.com/2023/11/installing-app-in-byd.html): Detailed sideloading guide (confidence: high, accessed: 2025-01-30)
- [XDA Forums - BYD Multimedia Install APK](https://xdaforums.com/t/byd-multimedia-install-apk.4541247/): Community solutions (confidence: high, accessed: 2025-01-30)
- [GitHub - BYD Tips and Tricks](https://github.com/ahmada3mar/BYD): Consolidated methods (confidence: medium, accessed: 2025-01-30)
- [XDA Forums - Dolphin 1.2 Update](https://xdaforums.com/t/byd-dolphin-1-2-update-apk-locked.4644556/): Dolphin-specific issues (confidence: high, accessed: 2025-01-30)
- [BYD Deskblur Guide](https://byd.deskblur.com/us): Installation instructions (confidence: medium, accessed: 2025-01-30)

## Current Installation Challenges

### Firmware Version Timeline
According to [XDA Forums research](https://xdaforums.com/t/byd-multimedia-install-apk.4541247/page-3):
- **Pre-2307 (July 2023)**: Easy USB sideloading with password
- **2307 (July 2023)**: Last version with unrestricted sideloading
- **2310 (October 2023)**: Third-party apps folder locked
- **Current versions**: All standard methods blocked

### BYD Dolphin Specific Issues
[XDA Forums Dolphin thread](https://xdaforums.com/t/byd-dolphin-1-2-update-apk-locked.4644556/) confirms:
- Version 1.2 update completely locks APK installation
- "Third party apps" folder no longer recognized
- SilentInstaller application locked
- USB debugging disabled by default
- Developer options hidden

## Working Installation Methods

### Method 1: USB with Password (Older Firmware)
Based on [MikeYip's guide](https://www.mikeyip.com/2023/11/installing-app-in-byd.html):

1. Format USB drive to FAT32
2. Create folder named "third party apps" (exact spelling)
3. Copy APK files to folder
4. Insert USB into car's port
5. Enter password when prompted

**Known Working Passwords**:
- `GHY0613byd` (case sensitive)
- `20211231`
- Vehicle-specific codes sometimes required

### Method 2: Firmware Downgrade
[XDA Forums solution](https://xdaforums.com/t/byd-multimedia-install-apk.4541247/page-3):

1. Obtain firmware version 2307 or earlier
2. Downgrade system (risky procedure)
3. Install desired apps using old methods
4. Optionally upgrade firmware (apps remain)
5. Note: Updates may remove installed apps

### Method 3: ADB Activation via IMEI
Latest method from [GitHub BYD repository](https://github.com/ahmada3mar/BYD):

1. Connect phone via Bluetooth
2. Access hidden menu (dial specific number)
3. Obtain IMEI number from system
4. Submit IMEI to activation website
5. Enable ADB debugging
6. Install unrestricted package manager
7. Sideload apps via ADB commands

### Method 4: Alternative Package Manager
Advanced approach from community:

1. Replace system package manager
2. Install App Manager application
3. Enable wireless ADB through hidden menu
4. Use Aurora OSS or similar for installations
5. Bypass standard restrictions

## Technical Requirements for Apps

### Android Compatibility
According to various sources:
- **Target SDK**: Android 10+ (DiLink 3.0)
- **Screen Resolution**: 1920x1280 (rotating screens)
- **Orientation**: Support portrait and landscape
- **Permissions**: Limited by system security

### App Development Considerations
From [One Finite Planet analysis](https://onefiniteplanet.org/webpapers/byd-atto-3-software-and-tips/):
- No Google Play Services on most models
- Internet access restricted for sideloaded apps
- Background services limited
- Hardware access restricted
- Safety-critical functions blocked

### Required Components
For seat memory app specifically:
- Android application framework
- Persistent storage for profiles
- System-level permissions (challenging)
- Vehicle communication interface
- UI adapted for in-car use

## Installation Process Details

### Accessing Developer Options
Hidden menu activation methods:
1. Settings > About > Tap "Restore" 7 times
2. Dial special codes in phone app
3. Use engineering mode passwords
4. Connect via manufacturer tools

### ADB Commands for Installation
```bash
# Enable ADB
adb devices
adb tcpip 5555
adb connect [CAR_IP]:5555

# Install APK
adb install -r app.apk

# Grant permissions
adb shell pm grant [package] [permission]
```

## Brazilian Market Considerations

### BYD Dolphin Plus in Brazil
- Same Android platform as global models
- Identical security restrictions apply
- Portuguese language support required
- Local regulations may affect features
- No official app store access

# Implementation Recommendations

1. **Development Approach**: Build Android app targeting SDK 29+
2. **Installation Strategy**: Prepare multiple installation methods
3. **User Instructions**: Create detailed guides for each method
4. **Version Management**: Track firmware versions and compatibility
5. **Community Engagement**: Share findings with BYD owner groups
6. **Safety Testing**: Ensure app doesn't interfere with vehicle safety

# Code Examples

## Android Manifest Requirements
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.BLUETOOTH"/>
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    
    <application
        android:theme="@style/Theme.Automotive"
        android:resizeableActivity="true">
        <!-- App components -->
    </application>
</manifest>
```

# Gaps and Future Research

- Exact firmware downgrade procedures not documented
- IMEI activation website URLs not publicly available
- Long-term stability of workarounds uncertain
- Official developer support lacking
- Safety implications of system modifications unclear

# References

## Primary Sources
1. **[MikeYip - Installing Android APK into BYD](https://www.mikeyip.com/2023/11/installing-app-in-byd.html)**
   - Accessed: 2025-01-30 10:00 UTC
   - Type: Technical Blog
   - Reliability: ⭐⭐⭐⭐⭐
   - Used for: Detailed installation methods

2. **[XDA Forums - BYD Multimedia Install APK](https://xdaforums.com/t/byd-multimedia-install-apk.4541247/)**
   - Accessed: 2025-01-30 10:05 UTC
   - Type: Community Forum
   - Reliability: ⭐⭐⭐⭐
   - Used for: Community solutions and firmware information

3. **[XDA Forums - BYD Dolphin 1.2 Update](https://xdaforums.com/t/byd-dolphin-1-2-update-apk-locked.4644556/)**
   - Accessed: 2025-01-30 10:10 UTC
   - Type: Community Forum
   - Reliability: ⭐⭐⭐⭐
   - Used for: Dolphin-specific challenges

## Secondary Sources
4. **[GitHub - BYD Tips Repository](https://github.com/ahmada3mar/BYD)**
   - Accessed: 2025-01-30 10:15 UTC
   - Type: GitHub Repository
   - Reliability: ⭐⭐⭐
   - Used for: Consolidated installation methods

5. **[One Finite Planet - BYD Software Tips](https://onefiniteplanet.org/webpapers/byd-atto-3-software-and-tips/)**
   - Accessed: 2025-01-30 10:20 UTC
   - Type: Technical Analysis
   - Reliability: ⭐⭐⭐⭐
   - Used for: Platform limitations and considerations

# Version History
- v1.0 (2025-01-30): Initial research on Android development and sideloading