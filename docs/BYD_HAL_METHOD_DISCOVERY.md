# BYD HAL Method Discovery Guide

## Overview

This guide provides code to discover and document BYD Hardware Abstraction Layer methods through runtime reflection. Use this on a BYD vehicle with DiLink system to map the complete API.

## BYD HAL Class Discovery Script

### Java Implementation

```java
package com.byd.hal.discovery;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class BYDHALDiscovery {
    private static final String TAG = "BYDHALDiscovery";
    
    // Known BYD HAL classes from reverse engineering
    private static final String[] BYD_HAL_CLASSES = {
        "android.hardware.bydauto.AbsBYDAutoDevice",
        "android.hardware.bydauto.ac.BYDAutoAcDevice",
        "android.hardware.bydauto.bodywork.BYDAutoBodyworkDevice",
        "android.hardware.bydauto.bodywork.AbsBYDAutoBodyworkListener",
        "android.hardware.bydauto.gearbox.BYDAutoGearboxDevice", 
        "android.hardware.bydauto.gearbox.AbsBYDAutoGearboxListener",
        "android.hardware.bydauto.instrument.BYDAutoInstrumentDevice",
        "android.hardware.bydauto.setting.BYDAutoSettingDevice",
        "android.hardware.bydauto.speed.BYDAutoSpeedDevice",
        "android.hardware.bydauto.statistic.BYDAutoStatisticDevice"
    };
    
    public static void discoverBYDHAL(Context context) {
        Log.i(TAG, "=== BYD HAL Discovery Started ===");
        
        for (String className : BYD_HAL_CLASSES) {
            discoverClass(className, context);
        }
        
        Log.i(TAG, "=== BYD HAL Discovery Completed ===");
    }
    
    private static void discoverClass(String className, Context context) {
        try {
            Class<?> clazz = Class.forName(className);
            Log.i(TAG, "\n[FOUND] Class: " + className);
            
            // Discover constructors
            discoverConstructors(clazz);
            
            // Discover methods
            discoverMethods(clazz);
            
            // Discover fields
            discoverFields(clazz);
            
            // Try to get instance if possible
            tryGetInstance(clazz, context);
            
        } catch (ClassNotFoundException e) {
            Log.w(TAG, "[NOT FOUND] Class: " + className);
        } catch (Exception e) {
            Log.e(TAG, "[ERROR] Failed to analyze " + className + ": " + e.getMessage());
        }
    }
    
    private static void discoverConstructors(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        Log.i(TAG, "  Constructors (" + constructors.length + "):");
        
        for (Constructor<?> constructor : constructors) {
            StringBuilder signature = new StringBuilder();
            signature.append("    ").append(Modifier.toString(constructor.getModifiers()));
            signature.append(" ").append(clazz.getSimpleName()).append("(");
            
            Class<?>[] params = constructor.getParameterTypes();
            for (int i = 0; i < params.length; i++) {
                if (i > 0) signature.append(", ");
                signature.append(params[i].getSimpleName());
            }
            signature.append(")");
            
            Log.i(TAG, signature.toString());
        }
    }
    
    private static void discoverMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Log.i(TAG, "  Methods (" + methods.length + "):");
        
        for (Method method : methods) {
            StringBuilder signature = new StringBuilder();
            signature.append("    ").append(Modifier.toString(method.getModifiers()));
            signature.append(" ").append(method.getReturnType().getSimpleName());
            signature.append(" ").append(method.getName()).append("(");
            
            Class<?>[] params = method.getParameterTypes();
            for (int i = 0; i < params.length; i++) {
                if (i > 0) signature.append(", ");
                signature.append(params[i].getSimpleName());
            }
            signature.append(")");
            
            Log.i(TAG, signature.toString());
            
            // Highlight seat-related methods
            String methodName = method.getName().toLowerCase();
            if (methodName.contains("seat") || methodName.contains("comfort") || 
                methodName.contains("stage") || methodName.contains("position")) {
                Log.i(TAG, "      *** SEAT-RELATED METHOD ***");
            }
        }
    }
    
    private static void discoverFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Log.i(TAG, "  Fields (" + fields.length + "):");
        
        for (Field field : fields) {
            StringBuilder signature = new StringBuilder();
            signature.append("    ").append(Modifier.toString(field.getModifiers()));
            signature.append(" ").append(field.getType().getSimpleName());
            signature.append(" ").append(field.getName());
            
            Log.i(TAG, signature.toString());
        }
    }
    
    private static void tryGetInstance(Class<?> clazz, Context context) {
        Log.i(TAG, "  Attempting to get instance...");
        
        try {
            // Try getInstance(Context) method
            Method getInstance = clazz.getMethod("getInstance", Context.class);
            Object instance = getInstance.invoke(null, context);
            
            if (instance != null) {
                Log.i(TAG, "    ✓ Successfully got instance via getInstance(Context)");
                
                // If this is BYDAutoBodyworkDevice, try seat methods
                if (clazz.getName().contains("BYDAutoBodyworkDevice")) {
                    testSeatMethods(instance, clazz);
                }
            } else {
                Log.w(TAG, "    ✗ getInstance returned null");
            }
            
        } catch (NoSuchMethodException e) {
            Log.w(TAG, "    ✗ No getInstance(Context) method found");
            
            // Try other getInstance variants
            tryAlternativeGetInstance(clazz, context);
            
        } catch (Exception e) {
            Log.e(TAG, "    ✗ getInstance failed: " + e.getMessage());
        }
    }
    
    private static void tryAlternativeGetInstance(Class<?> clazz, Context context) {
        try {
            // Try getInstance() without parameters
            Method getInstance = clazz.getMethod("getInstance");
            Object instance = getInstance.invoke(null);
            
            if (instance != null) {
                Log.i(TAG, "    ✓ Successfully got instance via getInstance()");
            }
        } catch (Exception e) {
            Log.w(TAG, "    ✗ No parameterless getInstance() method");
        }
        
        try {
            // Try default constructor
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            
            if (instance != null) {
                Log.i(TAG, "    ✓ Successfully created instance via constructor");
            }
        } catch (Exception e) {
            Log.w(TAG, "    ✗ Default constructor failed");
        }
    }
    
    private static void testSeatMethods(Object bodyworkDevice, Class<?> clazz) {
        Log.i(TAG, "    Testing seat control methods...");
        
        // Test methods based on string analysis findings
        String[] seatMethods = {
            "getDriverComfortStage",
            "setDriverComfortStage", 
            "getPassengerComfortStage",
            "setPassengerComfortStage",
            "setSeatHeatingState",
            "setSeatVentilatingState"
        };
        
        for (String methodName : seatMethods) {
            try {
                // Try method without parameters (getters)
                if (methodName.startsWith("get")) {
                    Method method = clazz.getMethod(methodName);
                    Object result = method.invoke(bodyworkDevice);
                    Log.i(TAG, "      ✓ " + methodName + "() = " + result);
                }
                
                // Try method with int parameter (setters)
                if (methodName.startsWith("set") && methodName.contains("Stage")) {
                    Method method = clazz.getMethod(methodName, int.class);
                    Log.i(TAG, "      ✓ Found " + methodName + "(int)");
                    // Don't actually call setter during discovery
                }
                
            } catch (NoSuchMethodException e) {
                Log.w(TAG, "      ✗ Method not found: " + methodName);
            } catch (Exception e) {
                Log.e(TAG, "      ✗ Error testing " + methodName + ": " + e.getMessage());
            }
        }
    }
}
```

## Speed Safety Check Discovery

```java
public class BYDSpeedSafetyCheck {
    private static final String TAG = "BYDSpeedSafety";
    
    public static boolean isSafeForSeatAdjustment(Context context) {
        try {
            Class<?> speedClass = Class.forName("android.hardware.bydauto.speed.BYDAutoSpeedDevice");
            Method getInstance = speedClass.getMethod("getInstance", Context.class);
            Object speedDevice = getInstance.invoke(null, context);
            
            if (speedDevice != null) {
                // Try to find speed-related methods
                Method[] methods = speedClass.getDeclaredMethods();
                
                for (Method method : methods) {
                    String name = method.getName().toLowerCase();
                    
                    // Look for speed checking methods
                    if (name.contains("speed") || name.contains("moving") || 
                        name.contains("safe") || name.contains("stationary")) {
                        
                        Log.i(TAG, "Found speed method: " + method.getName());
                        
                        // If method takes no parameters and returns boolean/int
                        if (method.getParameterCount() == 0) {
                            try {
                                Object result = method.invoke(speedDevice);
                                Log.i(TAG, "  " + method.getName() + "() = " + result);
                                
                                // If result suggests vehicle is stopped
                                if (result instanceof Boolean) {
                                    return (Boolean) result;
                                } else if (result instanceof Integer) {
                                    int speed = (Integer) result;
                                    return speed == 0; // Assume 0 = stopped
                                }
                            } catch (Exception e) {
                                Log.w(TAG, "Error calling " + method.getName() + ": " + e.getMessage());
                            }
                        }
                    }
                }
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error accessing BYD speed device: " + e.getMessage());
        }
        
        return false; // Default to unsafe if can't determine
    }
}
```

## Settings Device Discovery

```java
public class BYDSettingsDiscovery {
    private static final String TAG = "BYDSettings";
    
    public static void discoverSettings(Context context) {
        try {
            Class<?> settingsClass = Class.forName("android.hardware.bydauto.setting.BYDAutoSettingDevice");
            Method getInstance = settingsClass.getMethod("getInstance", Context.class);
            Object settingsDevice = getInstance.invoke(null, context);
            
            if (settingsDevice != null) {
                Log.i(TAG, "BYD Settings Device available");
                
                // Discover all methods
                Method[] methods = settingsClass.getDeclaredMethods();
                
                for (Method method : methods) {
                    String name = method.getName();
                    
                    // Look for seat-related settings
                    if (name.toLowerCase().contains("seat") || 
                        name.toLowerCase().contains("comfort") ||
                        name.toLowerCase().contains("memory")) {
                        
                        Log.i(TAG, "Found seat setting method: " + name);
                        
                        // Try to get parameter types
                        Class<?>[] params = method.getParameterTypes();
                        StringBuilder signature = new StringBuilder(name).append("(");
                        for (int i = 0; i < params.length; i++) {
                            if (i > 0) signature.append(", ");
                            signature.append(params[i].getSimpleName());
                        }
                        signature.append(")");
                        
                        Log.i(TAG, "  Signature: " + signature.toString());
                    }
                }
            }
            
        } catch (Exception e) {
            Log.e(TAG, "Error accessing BYD settings device: " + e.getMessage());
        }
    }
}
```

## Usage in Android App

### MainActivity Integration

```java
public class BYDDiscoveryActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        
        // Run discovery on background thread
        new Thread(() -> {
            BYDHALDiscovery.discoverBYDHAL(this);
            
            runOnUiThread(() -> {
                // Update UI with discovery results
                Toast.makeText(this, "BYD HAL Discovery Complete - Check Logs", 
                              Toast.LENGTH_LONG).show();
            });
        }).start();
    }
}
```

### Permissions Required

Add to AndroidManifest.xml:
```xml
<uses-permission android:name="android.permission.READ_LOGS"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>

<!-- May need system-level permissions -->
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW"/>
```

## Log Analysis Script

Create a script to extract and analyze the discovery logs:

```bash
#!/bin/bash
# extract_hal_logs.sh

echo "Extracting BYD HAL discovery logs..."

# Clear existing logs
adb logcat -c

# Start discovery app (replace with your package name)
adb shell am start -n com.your.package/.BYDDiscoveryActivity

# Wait for discovery to complete
sleep 10

# Extract relevant logs
adb logcat -d | grep "BYDHALDiscovery\|BYDSpeedSafety\|BYDSettings" > byd_hal_discovery.log

echo "Discovery logs saved to byd_hal_discovery.log"
echo "Analysis complete. Review the log file for method signatures."
```

## Expected Output Format

The discovery script will generate logs like:

```
[FOUND] Class: android.hardware.bydauto.bodywork.BYDAutoBodyworkDevice
  Constructors (1):
    private BYDAutoBodyworkDevice()
  Methods (12):
    public static BYDAutoBodyworkDevice getInstance(Context)
    public int getDriverComfortStage()
    public void setDriverComfortStage(int)
    public int getPassengerComfortStage() 
    public void setPassengerComfortStage(int)
    *** SEAT-RELATED METHOD ***
    public void setSeatHeatingState(int, boolean)
    *** SEAT-RELATED METHOD ***
    public void setSeatVentilatingState(int, boolean)
    *** SEAT-RELATED METHOD ***
  Fields (3):
    private static BYDAutoBodyworkDevice instance
    private Context context
    private boolean initialized
```

## Security Considerations

**Warning**: This discovery process may require:
- System-level app signing
- Special permissions on BYD's DiLink system
- Potential security bypasses

**Recommendations**:
1. Only run on development/test vehicles
2. Backup vehicle settings before testing
3. Never call setter methods during discovery without understanding their effects
4. Document all findings for future reference

## Next Steps

1. **Run Discovery**: Execute on BYD vehicle with DiLink system
2. **Document Results**: Create comprehensive API documentation
3. **Test Implementation**: Build seat control using discovered methods
4. **Validate Safety**: Ensure all safety mechanisms work properly
5. **Create SDK**: Package findings into reusable development kit

This discovery process will provide the complete BYD HAL API documentation needed for reliable seat memory implementation.