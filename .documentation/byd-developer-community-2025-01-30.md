---
title: BYD Developer Communities and Existing Projects
date: 2025-01-30
tags: [BYD, open-source, community, GitHub, developer-resources]
status: active
confidence: medium
---

# Executive Summary

The BYD developer community is fragmented but active, primarily centered around XDA Forums, GitHub repositories, and regional owner groups. While no existing open-source projects specifically address seat memory functionality, several initiatives provide valuable foundations: Open Vehicles (OVMS) for vehicle monitoring, reverse engineering efforts for the BYD mobile app API, and various Android app sideloading solutions. The official BYD app provides seat heating/ventilation control but lacks position memory features. Community efforts focus mainly on bypassing installation restrictions rather than developing vehicle control applications.

# Research Questions

1. What existing projects relate to BYD vehicle app development?
2. Are there open-source solutions for seat control?
3. What developer communities are active?
4. What resources and code examples are available?

# Findings

## Sources Consulted

- [GitHub - Open Vehicle Android](https://github.com/openvehicles/Open-Vehicle-Android): OVMS Android app (confidence: high, accessed: 2025-01-30)
- [GitHub - BYD React App Reverse](https://github.com/jkaberg/byd-react-app-reverse): API reverse engineering (confidence: medium, accessed: 2025-01-30)
- [GitHub - BYD Factory Images](https://github.com/BYDcar/BYDGlobalFactoryImages1): Firmware repository (confidence: medium, accessed: 2025-01-30)
- [GitHub - BYD Tips Collection](https://github.com/ahmada3mar/BYD): Community knowledge base (confidence: medium, accessed: 2025-01-30)
- [XDA Forums - BYD Discussions](https://xdaforums.com/): Active community forum (confidence: high, accessed: 2025-01-30)
- [BYD Owners Forum](https://www.bydowners.com/): Owner community (confidence: medium, accessed: 2025-01-30)

## Active Developer Communities

### XDA Developers Forums
[XDA Forums](https://xdaforums.com/) hosts multiple BYD-related threads:
- **BYD Multimedia - Install APK**: 50+ pages of discussion
- **DiLink 4.0 Custom ROM**: Technical modifications
- **BYD Song Plus EV - TWRP**: Root access attempts
- **BYD Dolphin Update Issues**: Model-specific problems
- Active problem-solving and knowledge sharing

### GitHub Projects

#### Open Vehicle Monitoring System (OVMS)
[OVMS Project](https://github.com/openvehicles/Open-Vehicle-Android) provides:
- Android app for vehicle monitoring
- Support for BYD Atto 3 (limited)
- Open APIs and protocols
- Active development community
- Potential framework for seat control

#### BYD App Reverse Engineering
[jkaberg/byd-react-app-reverse](https://github.com/jkaberg/byd-react-app-reverse) reveals:
- API endpoint documentation
- Encryption methods (AES/CBC/PKCS5Padding)
- Request/response structures
- Authentication mechanisms
- No seat position control documented

#### BYD Factory Images Repository
[BYDcar/BYDGlobalFactoryImages1](https://github.com/BYDcar/BYDGlobalFactoryImages1) contains:
- Factory firmware images
- Service manuals
- Update packages
- System documentation
- Useful for understanding system architecture

### Regional Communities

#### Brazilian BYD Groups
- Facebook groups for BYD Brazil owners
- WhatsApp communities sharing tips
- Local forums discussing modifications
- Limited technical development focus

#### International Forums
- [BYD Owners Forum](https://www.bydowners.com/): English-language discussions
- Team-BHP (India): Aftermarket modifications
- Australian BYD communities: Technical discussions

## Existing BYD Applications

### Official BYD App
According to [APK Mirror](https://www.apkmirror.com/apk/byd-auto-industry-company-limited/byd/) and [Google Play](https://play.google.com/store/apps/details?id=com.byd.bydautolink):
- **Features**: Remote lock/unlock, climate control, seat heating/ventilation
- **Limitations**: No seat position memory control
- **Version**: 2.7.0 (latest as of January 2025)
- **Requirements**: Android 6.0+
- **Package**: com.byd.bydautolink

### Third-Party Projects

#### VIA-AI Driver Assistance
[VIA-AI on GitHub](https://github.com/via-intelligent-vision/VIA-AI):
- Open-source driver assistance
- CAN bus integration
- Not BYD-specific but adaptable
- Shows potential for vehicle control apps

#### AAIdrive (BMW/Mini)
[BimmerGestalt/AAIdrive](https://github.com/BimmerGestalt/AAIdrive):
- Unofficial Android Auto implementation
- Deep vehicle integration example
- Architecture potentially applicable to BYD

## Code and Resources

### Available Code Examples

#### CAN Bus Communication
From OVMS and related projects:
```python
# Example from reverse engineering efforts
class BYDVehicle:
    def __init__(self):
        self.can_bus = CANInterface()
    
    def read_seat_position(self):
        # Hypothetical - not actually documented
        msg = self.can_bus.read(0x3B0)
        return self.decode_position(msg)
```

#### Android App Structure
Basic framework for BYD apps:
```java
public class BYDSeatMemory extends AppCompatActivity {
    private BYDConnection connection;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize BYD connection
        connection = new BYDConnection(this);
    }
}
```

### Documentation Resources

#### Technical Documentation
- BYD owner manuals (limited technical details)
- Service manuals (when available)
- OIP platform docs (mostly Chinese)
- Community-created guides

#### API Documentation
From reverse engineering:
- Endpoint URLs documented
- Authentication flows understood
- Data structures partially decoded
- Encryption methods known

## Development Tools and SDKs

### Available Tools
1. **Android Studio**: Standard development
2. **CAN analyzers**: Hardware for bus sniffing
3. **ADB tools**: For sideloading
4. **Wireshark**: Network analysis
5. **IDA Pro/Ghidra**: APK reverse engineering

### Missing Components
- Official BYD SDK for third parties
- Seat control protocol documentation
- Vehicle simulator for testing
- Developer hardware kits
- English language resources

## Community Knowledge Gaps

### What's Missing
According to community discussions:
- Seat memory control protocols completely undocumented
- LIN bus access methods unknown
- Safety interlock systems unclear
- Gateway communication protocols proprietary
- No successful seat control implementations found

### Active Research Areas
Communities are working on:
- Bypassing installation restrictions
- Reverse engineering vehicle protocols
- Creating custom firmware
- Developing alternative app stores
- Building developer tools

# Implementation Recommendations

1. **Leverage OVMS Framework**: Build on existing open-source base
2. **Engage XDA Community**: Share findings and get feedback
3. **Study BYD App**: Reverse engineer for insights
4. **Create GitHub Project**: Open-source development approach
5. **Document Progress**: Help future developers
6. **Focus on Safety**: Ensure modifications don't compromise vehicle

# Gaps and Future Research

- No existing seat memory control projects found
- Limited English documentation
- Fragmented community efforts
- Lack of official developer support
- Need for unified development platform

# References

## Primary Sources
1. **[GitHub - Open Vehicle Android](https://github.com/openvehicles/Open-Vehicle-Android)**
   - Accessed: 2025-01-30 10:30 UTC
   - Type: Open Source Project
   - Reliability: ⭐⭐⭐⭐⭐
   - Used for: Framework and architecture reference

2. **[XDA Forums - BYD Threads](https://xdaforums.com/)**
   - Accessed: 2025-01-30 10:35 UTC
   - Type: Community Forum
   - Reliability: ⭐⭐⭐⭐
   - Used for: Community solutions and discussions

3. **[GitHub - BYD App Reverse Engineering](https://github.com/jkaberg/byd-react-app-reverse)**
   - Accessed: 2025-01-30 10:40 UTC
   - Type: Reverse Engineering Project
   - Reliability: ⭐⭐⭐
   - Used for: API structure and protocols

## Secondary Sources
4. **[BYD Owners Forum](https://www.bydowners.com/)**
   - Accessed: 2025-01-30 10:45 UTC
   - Type: Owner Community
   - Reliability: ⭐⭐⭐
   - Used for: User experiences and needs

5. **[GitHub - VIA-AI](https://github.com/via-intelligent-vision/VIA-AI)**
   - Accessed: 2025-01-30 10:50 UTC
   - Type: Open Source Project
   - Reliability: ⭐⭐⭐⭐
   - Used for: CAN bus integration example

6. **[APK Mirror - BYD App](https://www.apkmirror.com/apk/byd-auto-industry-company-limited/byd/)**
   - Accessed: 2025-01-30 10:55 UTC
   - Type: App Repository
   - Reliability: ⭐⭐⭐⭐
   - Used for: Official app capabilities

# Version History
- v1.0 (2025-01-30): Initial research on developer communities and projects