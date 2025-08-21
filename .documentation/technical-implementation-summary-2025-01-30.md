---
title: Technical Implementation Summary - BYD Seat Memory App Development
date: 2025-01-30
tags: [BYD, implementation, technical-summary, recommendations]
status: active
confidence: medium
project: BYD Dolphin Plus Seat Memory App
---

# Executive Summary

Developing an aftermarket seat memory app for the BYD Dolphin Plus in Brazil faces significant technical challenges. While the vehicle runs Android-based DiLink system on Qualcomm hardware, recent firmware updates have severely restricted app sideloading capabilities. Seat control operates through LIN bus subsystems rather than directly accessible CAN bus, requiring complex reverse engineering. No existing open-source projects provide seat position control, though the OVMS framework offers a potential starting point. Success will require a multi-pronged approach combining firmware downgrading, alternative installation methods, and potentially hardware-level access to vehicle networks.

# Technical Architecture Overview

## System Components

### DiLink Platform
- **Hardware**: Qualcomm QCM6125 (Snapdragon 665)
- **OS**: Android 10+ (varies by DiLink version)
- **Display**: 12.8-inch rotating screen (1920x1280)
- **Connectivity**: Wi-Fi, Bluetooth, 4G LTE
- **Version**: DiLink 3.0-5.0 depending on model year

### Vehicle Networks
- **Main CAN Bus**: Engine, transmission, primary systems
- **Body CAN Bus**: Doors, windows, lighting
- **Comfort CAN Bus**: Climate, infotainment
- **LIN Bus Subsystems**: Seats, mirrors, local controls
- **Gateway Modules**: CAN-LIN translation

### Seat Control Architecture
```
User Input → Body Control Module (CAN) → Gateway → LIN Master → Seat Motors
```

# Development Approach

## Phase 1: Research and Prototyping

### 1.1 Network Analysis
- Use OBD2 adapter to monitor CAN traffic
- Identify seat control message patterns
- Document CAN IDs and data formats
- Locate CAN-LIN gateway messages

### 1.2 App Development
- Create Android app targeting SDK 29+
- Implement profile storage system
- Design automotive-appropriate UI
- Add safety interlocks

### 1.3 Installation Methods
Primary approach:
1. Downgrade to firmware 2307 if possible
2. Use USB method with password: `GHY0613byd`
3. Install app via "third party apps" folder

Alternative approaches:
- ADB activation via IMEI verification
- Alternative package manager installation
- Web-based app (no installation required)

## Phase 2: Integration

### 2.1 Vehicle Communication
Options in order of feasibility:
1. **API Integration**: Reverse engineer BYD app API
2. **Bluetooth OBD2**: Limited to available PIDs
3. **Direct CAN Access**: Requires physical connection
4. **LIN Bus Access**: Most complex, requires hardware modification

### 2.2 Data Protocols
Known protocols from research:
- CAN: 11-bit IDs, 8-byte messages
- LIN: Single-wire, master-slave, 20 kbps
- API: AES/CBC/PKCS5Padding encryption
- OBD2: Standard PIDs don't include seats

## Phase 3: Implementation

### 3.1 Minimum Viable Product
Features:
- Save current seat position
- Recall saved positions
- Multiple user profiles
- Manual position adjustment backup

### 3.2 Enhanced Features
Future additions:
- Automatic profile switching
- Key fob integration
- Cloud backup
- Voice control

# Technical Challenges and Solutions

## Challenge 1: Installation Restrictions

**Problem**: Latest firmware blocks sideloading
**Solutions**:
1. Maintain compatibility matrix for firmware versions
2. Provide multiple installation methods
3. Consider web app alternative
4. Explore manufacturer partnerships

## Challenge 2: Seat Control Access

**Problem**: No documented protocols for seat control
**Solutions**:
1. Reverse engineer during manual seat operation
2. Analyze BYD app for hidden capabilities
3. Collaborate with OVMS community
4. Consider aftermarket hardware module

## Challenge 3: Safety and Reliability

**Problem**: Vehicle safety critical systems
**Solutions**:
1. Read-only monitoring initially
2. Implement multiple safety checks
3. Limit operation to park/neutral
4. Add manual override capability

# Implementation Roadmap

## Immediate Actions (Week 1-2)
1. Set up development environment
2. Acquire OBD2 adapter and tools
3. Create basic Android app framework
4. Join XDA Forums and BYD communities
5. Begin CAN bus monitoring

## Short Term (Month 1-2)
1. Decode seat control messages
2. Implement profile storage
3. Test installation methods
4. Create user documentation
5. Alpha testing with single vehicle

## Medium Term (Month 3-4)
1. Refine communication protocols
2. Add safety features
3. Beta testing with multiple vehicles
4. Create installation videos
5. Build community support

## Long Term (Month 5-6)
1. Public release
2. Multi-language support (Portuguese priority)
3. Cloud features
4. Explore commercial opportunities
5. Maintain and update

# Risk Assessment

## Technical Risks
- **High**: Firmware updates breaking functionality
- **High**: Unable to decode seat protocols
- **Medium**: Installation becoming impossible
- **Low**: Hardware damage (with proper precautions)

## Mitigation Strategies
1. Version-lock compatible firmware
2. Multiple fallback approaches
3. Community-driven development
4. Extensive testing protocols
5. Clear disclaimer and warnings

# Resource Requirements

## Development Tools
- Android Studio
- OBD2 Bluetooth adapter (~$20-50)
- CAN bus analyzer (~$200-500)
- Test vehicle access
- Development time (200-400 hours)

## Knowledge Requirements
- Android development
- Vehicle networking (CAN/LIN)
- Reverse engineering
- Automotive safety standards
- Portuguese language (for Brazil)

# Recommendations

## Primary Approach
1. **Start Simple**: Focus on API-based solution first
2. **Community First**: Open source from day one
3. **Safety Always**: Never compromise vehicle safety
4. **Document Everything**: Help future developers
5. **Iterative Development**: Release early and often

## Alternative Approach
If technical barriers prove insurmountable:
1. Partner with local BYD dealers
2. Explore official development channels
3. Consider hardware add-on solution
4. Focus on other vehicle features
5. Wait for more open firmware versions

# Conclusion

Developing a seat memory app for the BYD Dolphin Plus is technically challenging but potentially achievable. The main obstacles are firmware restrictions and undocumented protocols rather than fundamental impossibilities. Success will likely come from combining multiple approaches: reverse engineering, community collaboration, and creative workarounds. The project should proceed cautiously with safety as the top priority and community engagement as the key strategy.

# Next Steps

1. Create GitHub repository for project
2. Set up development environment
3. Acquire necessary hardware tools
4. Begin vehicle network analysis
5. Engage with BYD owner communities
6. Document all findings publicly
7. Build prototype Android app
8. Test installation methods
9. Iterate based on results

# References

See individual research documents for detailed source citations:
- byd-dilink-system-2025-01-30.md
- byd-can-bus-protocols-2025-01-30.md
- byd-android-development-2025-01-30.md
- byd-developer-community-2025-01-30.md

# Version History
- v1.0 (2025-01-30): Initial technical implementation summary