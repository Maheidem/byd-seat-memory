# BYD Seat Memory App Installation Guide

## Prerequisites

### Vehicle Requirements
- BYD Dolphin Plus (Brazilian model)
- DiLink infotainment system with Android base
- Firmware version compatible with sideloading

### Required Files
- Seat Memory APK file
- USB drive (FAT32 formatted)
- Computer with ADB tools (optional)

## Installation Methods

## Method 1: USB Sideloading (Firmware 2307 or earlier)

### Step 1: Prepare USB Drive
1. Format USB drive as FAT32
2. Create folder named `third_party_apps`
3. Copy the Seat Memory APK to this folder
4. Create a text file named `password.txt` with content: `GHY0613byd`

### Step 2: Install on Vehicle
1. Start vehicle and ensure infotainment system is on
2. Insert USB drive into vehicle's USB port
3. Navigate to Settings → System → About
4. Tap build number 7 times
5. Enter password when prompted: `20211231`
6. Go to Developer Options → Install third party apps
7. Select the Seat Memory APK
8. Follow installation prompts

## Method 2: ADB Installation (All Firmware Versions)

### Step 1: Enable ADB
1. In vehicle, go to Settings → Bluetooth
2. Connect any Bluetooth device
3. While in Bluetooth menu, tap the top-right corner 5 times
4. A hidden menu should appear
5. Navigate to Developer Settings
6. Note the IMEI number displayed
7. Visit activation website: [URL would be provided]
8. Enter IMEI and request activation
9. Wait for confirmation (usually 24-48 hours)

### Step 2: Connect via ADB
1. Enable Wi-Fi on infotainment system
2. Note the IP address (Settings → Network → Wi-Fi → Advanced)
3. On computer, open terminal/command prompt
4. Connect to vehicle:
   ```bash
   adb connect [VEHICLE_IP]:5555
   ```
5. Verify connection:
   ```bash
   adb devices
   ```

### Step 3: Install APK
```bash
adb install -r SeatMemory.apk
```

## Method 3: Firmware Downgrade (For Newer Vehicles)

⚠️ **Warning**: Downgrading firmware may affect other vehicle functions. Proceed with caution.

### Step 1: Download Firmware
1. Obtain firmware version 2307 from BYD forums
2. Verify checksum matches official release
3. Extract to USB drive root

### Step 2: Prepare Update
1. Create `update.txt` in USB root with content:
   ```
   GHY0613byd
   ```
2. Place firmware files in root directory
3. Ensure USB is FAT32 formatted

### Step 3: Perform Downgrade
1. Turn off vehicle completely
2. Insert USB drive
3. Hold volume up + power on infotainment
4. Select "Update from USB"
5. Wait for completion (15-30 minutes)
6. After restart, use Method 1

## Post-Installation Setup

### Initial Configuration
1. Launch Seat Memory app
2. Grant necessary permissions:
   - Storage (for profile data)
   - Location (for driver detection)
   - Bluetooth (for key fob integration)
3. Create driver profile
4. Calibrate seat positions

### Setting Memory Positions
1. Adjust seat to desired position manually
2. Open Seat Memory app
3. Tap "Save Position"
4. Choose profile slot (1-3)
5. Name the position (e.g., "Driving", "Relaxed")

### Recalling Positions
1. Open Seat Memory app
2. Select driver profile
3. Tap desired position
4. Seat will automatically adjust
5. Wait for completion before driving

## Troubleshooting

### App Won't Install
- Verify APK is not corrupted
- Check firmware version compatibility
- Ensure sufficient storage space
- Try alternative installation method

### Seat Control Not Working
- Check vehicle is in Park
- Verify seat motors are functional
- Restart infotainment system
- Reinstall app with cleared data

### App Crashes
- Clear app cache and data
- Check for app updates
- Verify Android version compatibility
- Report issue with logs

### Lost After Update
- BYD system updates may remove app
- Keep APK backup on USB
- Re-install after updates
- Consider disabling auto-updates

## Safety Notes

⚠️ **Important Safety Information**:
- Never adjust seats while driving
- Ensure seat is locked in position before driving
- Keep manual controls accessible
- Test in safe environment first
- Have backup plan if app fails

## Uninstallation

### Via Settings
1. Settings → Apps → Seat Memory
2. Tap "Uninstall"
3. Confirm removal

### Via ADB
```bash
adb uninstall com.byd.seatmemory
```

## Support

### Community Support
- GitHub Issues: [repository URL]
- BYD Owners Forum: [forum URL]
- Telegram Group: [group invite]

### Logs Collection
To help diagnose issues:
```bash
adb logcat -d > seatmemory_log.txt
```

## Legal Disclaimer

This application is not affiliated with or endorsed by BYD Auto. Use at your own risk. Installation may void warranty. Always prioritize safety and follow local regulations regarding vehicle modifications.