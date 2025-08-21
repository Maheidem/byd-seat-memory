---
title: BYD DiLink System Architecture and Development Platform
date: 2025-01-30
tags: [BYD, DiLink, Android, infotainment, vehicle-integration]
status: active
confidence: high
---

# Executive Summary

BYD's DiLink platform is an Android-based intelligent infotainment system that powers the vehicle's touchscreen interface. The system runs on Qualcomm hardware (notably the QCM6125/Snapdragon 665) and has evolved through multiple generations (DiLink 2.0 through 5.0). While BYD offers an Open Innovation Platform (OIP) with developer resources, practical app development faces significant challenges due to security restrictions, limited documentation in English, and progressively tightening control over sideloading capabilities.

# Research Questions

1. What is the architecture of BYD's DiLink system?
2. How can developers create apps for DiLink?
3. What are the technical specifications and capabilities?
4. What development resources are available?

# Findings

## Sources Consulted

- [BYD DiLink Official Documentation](https://www.byd.com/en-ma/support/dilink): Official system overview (confidence: high, accessed: 2025-01-30)
- [XDA Forums - DiLink 4.0 Discussion](https://xdaforums.com/t/possible-way-to-add-carplay-to-chinese-version-of-byd-dilink-4-0-custom-rom-for-qualcomm-qcm6125-pcb.4699831/): Technical specifications and modification attempts (confidence: high, accessed: 2025-01-30)
- [GitHub - BYD Factory Images](https://github.com/BYDcar/BYDGlobalFactoryImages1): Repository with firmware and documentation (confidence: medium, accessed: 2025-01-30)
- [BYD OIP Platform](https://oip.byd.com/): Official developer portal (confidence: high, accessed: 2025-01-30)

## DiLink System Architecture

### Hardware Platform
According to [XDA Forums discussions](https://xdaforums.com/t/possible-way-to-add-carplay-to-chinese-version-of-byd-dilink-4-0-custom-rom-for-qualcomm-qcm6125-pcb.4699831/), the DiLink system uses:
- **Processor**: Qualcomm QCM6125 (Snapdragon 665)
- **Display**: 1920x1280 resolution for rotating screens
- **OS**: Android 10 (DiLink 3.0), newer versions on DiLink 4.0/5.0
- **RAM/Storage**: Varies by model and generation

### System Generations
The platform has evolved significantly as documented on [BYD's official sites](https://www.byd.com/en-ma/support/dilink):
- **DiLink 2.0/2.1**: Early Android implementation
- **DiLink 3.0**: Android 10, improved performance
- **DiLink 4.0**: Enhanced security, restricted sideloading
- **DiLink 5.0**: Latest generation with 5G capabilities
- **DiLink 150**: High-end version with 4nm chip (luxury models)

### Core Features
[BYD's official documentation](https://www.byd.com/en-th/support/dilink) highlights:
- World's first rotatable touchscreen display
- Voice recognition control
- Split-screen multitasking
- Wi-Fi, Bluetooth, and LTE connectivity
- Deep vehicle system integration

## Developer Platform (Di Open)

### Open Innovation Platform
According to [BYD's OIP portal](https://oip.byd.com/), BYD provides:
- Access to 341+ vehicle sensors
- Control over 66+ vehicle systems
- SDK/API documentation (primarily in Chinese)
- Application integration guides
- Developer registration and certification

### API Capabilities
Based on [reverse engineering discussions](https://github.com/jkaberg/byd-react-app-reverse/issues/2), the platform offers:
- Vehicle data access (speed, location, battery status)
- Basic vehicle control (doors, climate, lights)
- Sensor data streaming
- Event notifications
- Encrypted communication (AES/CBC/PKCS5Padding)

## Android Development Specifications

### App Compatibility
[BYD Thailand documentation](https://www.byd.com/en-th/support/dilink) confirms:
- Full Android app compatibility
- Support for standard Android APIs
- Custom APIs for vehicle integration
- Limitations on background services
- Restricted permissions for safety

### Development Requirements
Based on various sources:
- Android SDK targeting appropriate API level
- Understanding of CAN bus communication
- Knowledge of vehicle safety protocols
- Compliance with automotive regulations
- Testing on actual hardware or emulators

## Security and Restrictions

### Progressive Lockdown
According to [XDA Forums research](https://xdaforums.com/t/byd-song-plus-ev-twrp-needed.4586857/):
- Early versions allowed USB debugging
- Version 2307 (July 2023): Last version with easy sideloading
- Version 2310 (October 2023): Sideloading restricted
- Current versions: Require authentication/passwords

### Current Challenges
- No Google Play Services on Chinese models
- Regional variations in features
- Limited English documentation
- Increasing security restrictions
- Lack of official developer support outside China

# Implementation Recommendations

1. **Official Development Path**: Register on oip.byd.com for SDK access
2. **Firmware Management**: Consider downgrading to version 2307 for development
3. **Alternative Approaches**: Explore web-based apps that don't require installation
4. **Community Resources**: Engage with XDA Forums and GitHub communities
5. **Regional Considerations**: Account for differences between Chinese and international versions

# Code Examples

## Basic API Connection (Reverse Engineered)
```java
// From GitHub reverse engineering efforts
public class BYDApiClient {
    private static final String ENCRYPTION_KEY = "[documented_key]";
    private static final String ENCRYPTION_MODE = "AES/CBC/PKCS5Padding";
    
    public CommonTypeRequest createRequest() {
        // Implementation based on reverse engineering
    }
}
```

# Gaps and Future Research

- English documentation for SDK/APIs remains limited
- Specific protocols for seat control not documented
- Methods to bypass latest security restrictions unclear
- Long-term viability of unofficial development uncertain

# References

## Primary Sources
1. **[BYD DiLink Official Page](https://www.byd.com/en-ma/support/dilink)**
   - Accessed: 2025-01-30 09:00 UTC
   - Type: Official Documentation
   - Reliability: ⭐⭐⭐⭐⭐
   - Used for: System overview and features

2. **[BYD OIP Developer Portal](https://oip.byd.com/)**
   - Accessed: 2025-01-30 09:05 UTC
   - Type: Official Developer Platform
   - Reliability: ⭐⭐⭐⭐⭐
   - Used for: SDK and API information

## Secondary Sources
3. **[XDA Forums - DiLink 4.0 Custom ROM](https://xdaforums.com/t/possible-way-to-add-carplay-to-chinese-version-of-byd-dilink-4-0-custom-rom-for-qualcomm-qcm6125-pcb.4699831/)**
   - Accessed: 2025-01-30 09:10 UTC
   - Type: Community Forum
   - Reliability: ⭐⭐⭐⭐
   - Used for: Technical specifications and modification attempts

4. **[GitHub - BYD Factory Images](https://github.com/BYDcar/BYDGlobalFactoryImages1)**
   - Accessed: 2025-01-30 09:15 UTC
   - Type: Community Repository
   - Reliability: ⭐⭐⭐
   - Used for: Firmware versions and system images

# Version History
- v1.0 (2025-01-30): Initial research on DiLink system architecture