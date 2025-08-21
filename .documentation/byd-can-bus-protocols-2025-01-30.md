---
title: BYD CAN Bus Access and Vehicle Communication Protocols
date: 2025-01-30
tags: [BYD, CAN-bus, OBD2, LIN-bus, vehicle-protocols, seat-control]
status: active
confidence: medium
---

# Executive Summary

BYD vehicles use a complex network of CAN and LIN bus systems for vehicle communication. While some reverse engineering efforts have successfully decoded battery management protocols, vehicle control systems like seat adjustment remain largely undocumented. Seat controls typically operate via LIN bus subsystems connected through CAN-LIN gateways, making direct control challenging without manufacturer-specific knowledge. The Open Vehicles project has made progress on basic BYD Atto 3 integration, but seat memory functionality requires deeper system access than currently documented.

# Research Questions

1. How can developers access BYD's CAN bus system?
2. What protocols are used for vehicle control, specifically seat adjustment?
3. What reverse engineering efforts have been successful?
4. How do CAN and LIN buses interact for seat control?

# Findings

## Sources Consulted

- [Open Vehicles - BYD Atto 3 Documentation](https://docs.openvehicles.com/en/latest/components/vehicle_byd_atto3/docs/index.html): Vehicle integration documentation (confidence: high, accessed: 2025-01-30)
- [GitHub - BYD React App Reverse Engineering](https://github.com/jkaberg/byd-react-app-reverse/issues/2): API reverse engineering efforts (confidence: medium, accessed: 2025-01-30)
- [BYD Battery CAN Protocol Reverse Engineering](https://watenan.uk/2023/07/24/reverse-engineering-the-byd-battery-box-premium-lvs-can-protocol-for-victron-venus-os/): Successful protocol decoding (confidence: high, accessed: 2025-01-30)
- [Copperhill - CAN and LIN Bus Guide](https://copperhilltech.com/blog/unlocking-vehicle-intelligence-a-practical-guide-to-can-and-lin-bus-networks/): Technical overview (confidence: high, accessed: 2025-01-30)

## BYD CAN Bus Architecture

### Physical Access Points
According to [Open Vehicles documentation](https://docs.openvehicles.com/en/latest/components/vehicle_byd_atto3/docs/index.html):
- **OBD2 Port Location**: Standard location under dashboard
- **Charging Network CAN**: CAN-H on OBD pin 9 (Pink), CAN-L on OBD pin 10 (Violet)
- **Multiple CAN Networks**: Separate buses for different subsystems
- **Gateway Modules**: Connect different network segments

### Known CAN Networks
Based on reverse engineering efforts:
1. **Main Vehicle CAN**: Engine/motor control, transmission
2. **Charging Sub-network**: Battery management, charging control
3. **Body Control CAN**: Doors, windows, lights
4. **Comfort CAN**: Climate, seats, infotainment

## Seat Control Architecture

### LIN Bus Implementation
[Copperhill's technical guide](https://copperhilltech.com/blog/unlocking-vehicle-intelligence-a-practical-guide-to-can-and-lin-bus-networks/) explains:
- Seat motors typically controlled via LIN bus (not CAN)
- LIN used for cost-effective local control
- Single-wire protocol (ISO 9141)
- Master-slave architecture
- Lower bandwidth suitable for seat adjustments

### CAN-LIN Gateway System
The seat control system architecture involves:
1. **User Input**: Button press or app command
2. **Body Control Module**: Processes request via CAN
3. **CAN-LIN Gateway**: Translates to LIN commands
4. **LIN Master**: Controls seat motor slaves
5. **Seat Motors**: Execute position changes

## Reverse Engineering Efforts

### Successful Projects

#### Open Vehicles OVMS
[OVMS BYD Atto 3 support](https://docs.openvehicles.com/en/latest/components/vehicle_byd_atto3/docs/index.html) includes:
- Basic vehicle monitoring
- Battery state information
- Some control functions
- Note: "Most data is reverse engineered based on assumptions"

#### BYD Battery Protocols
[Successful reverse engineering](https://watenan.uk/2023/07/24/reverse-engineering-the-byd-battery-box-premium-lvs-can-protocol-for-victron-venus-os/) methodology:
- Used candump for CAN sniffing
- Monitored message patterns during state changes
- Identified sender/receiver by disconnection testing
- Decoded data formats and scaling factors

#### BYD App API
[GitHub project](https://github.com/jkaberg/byd-react-app-reverse/issues/2) reveals:
- AES/CBC/PKCS5Padding encryption
- CommonTypeRequest structure
- JniLib components
- Remote control capabilities (not seat memory)

### Challenges and Limitations
- Seat control protocols remain undocumented
- No public projects successfully controlling seat memory
- LIN bus access more difficult than CAN
- Manufacturer uses proprietary protocols
- Safety concerns limit experimentation

## Technical Protocols

### OBD2 Standards
According to [CSS Electronics](https://www.csselectronics.com/pages/obd2-explained-simple-intro):
- CAN mandatory for OBD2 since 2008
- Standard PIDs don't include seat control
- Manufacturer-specific modes required
- 11-bit CAN IDs typical (0x7DF functional, 0x7E0-0x7E7 physical)

### Data Formats
Based on reverse engineering documentation:
- Temperature often in Kelvin
- Voltage in 1/10mV units
- Low byte first for WORD values
- Proprietary scaling factors common
- Encryption on some messages

## Implementation Approaches

### Direct CAN Access
Requirements for CAN bus interaction:
1. **Hardware**: OBD2 adapter (ELM327 or similar)
2. **Software**: CAN analysis tools (candump, Wireshark)
3. **Knowledge**: Message IDs, data formats, timing
4. **Safety**: Isolation to prevent vehicle damage

### LIN Bus Access for Seats
More complex requirements:
1. **Physical Access**: Interior panels removal
2. **Hardware**: LIN transceiver and analyzer
3. **Protocol Knowledge**: Master-slave timing
4. **Gateway Understanding**: CAN-LIN translation

# Implementation Recommendations

1. **Start with OBD2**: Use standard tools to explore available data
2. **Monitor Existing Operations**: Sniff CAN while using seat controls
3. **Focus on Gateway**: Identify CAN-LIN gateway messages
4. **Community Collaboration**: Share findings with OVMS project
5. **Safety First**: Use read-only monitoring before attempting control

# Code Examples

## CAN Message Monitoring
```python
# Example using python-can library
import can

# Setup CAN interface
bus = can.interface.Bus(channel='can0', bustype='socketcan')

# Monitor for seat-related messages
for message in bus:
    if message.arbitration_id in [0x3B0, 0x3B1]:  # Hypothetical seat IDs
        print(f"Seat message: {message.data.hex()}")
```

# Gaps and Future Research

- No documented BYD seat control CAN/LIN protocols
- Gateway translation algorithms unknown
- Safety interlocks not understood
- Need physical access to seat LIN bus
- Lack of BYD-specific diagnostic tools

# References

## Primary Sources
1. **[Open Vehicles - BYD Atto 3](https://docs.openvehicles.com/en/latest/components/vehicle_byd_atto3/docs/index.html)**
   - Accessed: 2025-01-30 09:20 UTC
   - Type: Technical Documentation
   - Reliability: ⭐⭐⭐⭐
   - Used for: CAN bus access points and known limitations

2. **[BYD Battery CAN Reverse Engineering](https://watenan.uk/2023/07/24/reverse-engineering-the-byd-battery-box-premium-lvs-can-protocol-for-victron-venus-os/)**
   - Accessed: 2025-01-30 09:25 UTC
   - Type: Technical Blog
   - Reliability: ⭐⭐⭐⭐⭐
   - Used for: Successful reverse engineering methodology

## Secondary Sources
3. **[GitHub - BYD App Reverse Engineering](https://github.com/jkaberg/byd-react-app-reverse/issues/2)**
   - Accessed: 2025-01-30 09:30 UTC
   - Type: GitHub Issue
   - Reliability: ⭐⭐⭐
   - Used for: API structure and encryption details

4. **[Copperhill - CAN/LIN Guide](https://copperhilltech.com/blog/unlocking-vehicle-intelligence-a-practical-guide-to-can-and-lin-bus-networks/)**
   - Accessed: 2025-01-30 09:35 UTC
   - Type: Technical Guide
   - Reliability: ⭐⭐⭐⭐⭐
   - Used for: Understanding seat control via LIN bus

5. **[CSS Electronics - OBD2 Guide](https://www.csselectronics.com/pages/obd2-explained-simple-intro)**
   - Accessed: 2025-01-30 09:40 UTC
   - Type: Technical Reference
   - Reliability: ⭐⭐⭐⭐⭐
   - Used for: OBD2 protocol standards

# Version History
- v1.0 (2025-01-30): Initial research on CAN bus and protocols