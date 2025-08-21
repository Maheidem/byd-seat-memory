package org.acra.collector;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.util.SparseArray;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import org.acra.ACRA;

/* loaded from: classes.dex */
public final class ConfigurationCollector {
    private static final String FIELD_MCC = "mcc";
    private static final String FIELD_MNC = "mnc";
    private static final String FIELD_SCREENLAYOUT = "screenLayout";
    private static final String FIELD_UIMODE = "uiMode";
    private static final String PREFIX_HARDKEYBOARDHIDDEN = "HARDKEYBOARDHIDDEN_";
    private static final String PREFIX_KEYBOARD = "KEYBOARD_";
    private static final String PREFIX_KEYBOARDHIDDEN = "KEYBOARDHIDDEN_";
    private static final String PREFIX_NAVIGATION = "NAVIGATION_";
    private static final String PREFIX_NAVIGATIONHIDDEN = "NAVIGATIONHIDDEN_";
    private static final String PREFIX_ORIENTATION = "ORIENTATION_";
    private static final String PREFIX_SCREENLAYOUT = "SCREENLAYOUT_";
    private static final String PREFIX_TOUCHSCREEN = "TOUCHSCREEN_";
    private static final String PREFIX_UI_MODE = "UI_MODE_";
    private static final String SUFFIX_MASK = "_MASK";
    private static SparseArray<String> mHardKeyboardHiddenValues = new SparseArray<>();
    private static SparseArray<String> mKeyboardValues = new SparseArray<>();
    private static SparseArray<String> mKeyboardHiddenValues = new SparseArray<>();
    private static SparseArray<String> mNavigationValues = new SparseArray<>();
    private static SparseArray<String> mNavigationHiddenValues = new SparseArray<>();
    private static SparseArray<String> mOrientationValues = new SparseArray<>();
    private static SparseArray<String> mScreenLayoutValues = new SparseArray<>();
    private static SparseArray<String> mTouchScreenValues = new SparseArray<>();
    private static SparseArray<String> mUiModeValues = new SparseArray<>();
    private static final HashMap<String, SparseArray<String>> mValueArrays = new HashMap<>();

    static {
        SparseArray<String> sparseArray;
        int i;
        mValueArrays.put(PREFIX_HARDKEYBOARDHIDDEN, mHardKeyboardHiddenValues);
        mValueArrays.put(PREFIX_KEYBOARD, mKeyboardValues);
        mValueArrays.put(PREFIX_KEYBOARDHIDDEN, mKeyboardHiddenValues);
        mValueArrays.put(PREFIX_NAVIGATION, mNavigationValues);
        mValueArrays.put(PREFIX_NAVIGATIONHIDDEN, mNavigationHiddenValues);
        mValueArrays.put(PREFIX_ORIENTATION, mOrientationValues);
        mValueArrays.put(PREFIX_SCREENLAYOUT, mScreenLayoutValues);
        mValueArrays.put(PREFIX_TOUCHSCREEN, mTouchScreenValues);
        mValueArrays.put(PREFIX_UI_MODE, mUiModeValues);
        for (Field field : Configuration.class.getFields()) {
            if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers())) {
                String name = field.getName();
                try {
                    if (name.startsWith(PREFIX_HARDKEYBOARDHIDDEN)) {
                        sparseArray = mHardKeyboardHiddenValues;
                        i = field.getInt(null);
                    } else if (name.startsWith(PREFIX_KEYBOARD)) {
                        sparseArray = mKeyboardValues;
                        i = field.getInt(null);
                    } else if (name.startsWith(PREFIX_KEYBOARDHIDDEN)) {
                        sparseArray = mKeyboardHiddenValues;
                        i = field.getInt(null);
                    } else if (name.startsWith(PREFIX_NAVIGATION)) {
                        sparseArray = mNavigationValues;
                        i = field.getInt(null);
                    } else if (name.startsWith(PREFIX_NAVIGATIONHIDDEN)) {
                        sparseArray = mNavigationHiddenValues;
                        i = field.getInt(null);
                    } else if (name.startsWith(PREFIX_ORIENTATION)) {
                        sparseArray = mOrientationValues;
                        i = field.getInt(null);
                    } else if (name.startsWith(PREFIX_SCREENLAYOUT)) {
                        sparseArray = mScreenLayoutValues;
                        i = field.getInt(null);
                    } else if (name.startsWith(PREFIX_TOUCHSCREEN)) {
                        sparseArray = mTouchScreenValues;
                        i = field.getInt(null);
                    } else if (name.startsWith(PREFIX_UI_MODE)) {
                        sparseArray = mUiModeValues;
                        i = field.getInt(null);
                    }
                    sparseArray.put(i, name);
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    Log.w(ACRA.LOG_TAG, "Error while inspecting device configuration: ", e);
                }
            }
        }
    }

    private static String activeFlags(SparseArray<String> sparseArray, int i) {
        int i2;
        StringBuilder sb = new StringBuilder();
        for (int i3 = 0; i3 < sparseArray.size(); i3++) {
            int iKeyAt = sparseArray.keyAt(i3);
            if (sparseArray.get(iKeyAt).endsWith(SUFFIX_MASK) && (i2 = iKeyAt & i) > 0) {
                if (sb.length() > 0) {
                    sb.append('+');
                }
                sb.append(sparseArray.get(i2));
            }
        }
        return sb.toString();
    }

    public static String collectConfiguration(Context context) {
        try {
            return toString(context.getResources().getConfiguration());
        } catch (RuntimeException e) {
            Log.w(ACRA.LOG_TAG, "Couldn't retrieve CrashConfiguration for : " + context.getPackageName(), e);
            return "Couldn't retrieve crash config";
        }
    }

    private static String getFieldValueName(Configuration configuration, Field field) {
        String str;
        String name = field.getName();
        if (name.equals(FIELD_MCC) || name.equals(FIELD_MNC)) {
            return Integer.toString(field.getInt(configuration));
        }
        if (name.equals(FIELD_UIMODE)) {
            return activeFlags(mValueArrays.get(PREFIX_UI_MODE), field.getInt(configuration));
        }
        if (name.equals(FIELD_SCREENLAYOUT)) {
            return activeFlags(mValueArrays.get(PREFIX_SCREENLAYOUT), field.getInt(configuration));
        }
        SparseArray<String> sparseArray = mValueArrays.get(name.toUpperCase() + '_');
        return (sparseArray == null || (str = sparseArray.get(field.getInt(configuration))) == null) ? Integer.toString(field.getInt(configuration)) : str;
    }

    public static String toString(Configuration configuration) throws SecurityException {
        String string;
        StringBuilder sb = new StringBuilder();
        for (Field field : configuration.getClass().getFields()) {
            try {
                if (!Modifier.isStatic(field.getModifiers())) {
                    sb.append(field.getName());
                    sb.append('=');
                    if (field.getType().equals(Integer.TYPE)) {
                        string = getFieldValueName(configuration, field);
                    } else {
                        if (field.get(configuration) != null) {
                            string = field.get(configuration).toString();
                        }
                        sb.append('\n');
                    }
                    sb.append(string);
                    sb.append('\n');
                }
            } catch (IllegalAccessException | IllegalArgumentException e) {
                Log.e(ACRA.LOG_TAG, "Error while inspecting device configuration: ", e);
            }
        }
        return sb.toString();
    }
}
