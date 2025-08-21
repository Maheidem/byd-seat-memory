package org.acra.collector;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.acra.ACRA;

/* loaded from: classes.dex */
final class SettingsCollector {
    SettingsCollector() {
    }

    public static String collectGlobalSettings(Context context) throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        Object objInvoke;
        if (Compatibility.getAPILevel() < 17) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        try {
            Class<?> cls = Class.forName("android.provider.Settings$Global");
            Field[] fields = cls.getFields();
            Method method = cls.getMethod("getString", ContentResolver.class, String.class);
            for (Field field : fields) {
                if (!field.isAnnotationPresent(Deprecated.class) && field.getType() == String.class && isAuthorized(field) && (objInvoke = method.invoke(null, context.getContentResolver(), (String) field.get(null))) != null) {
                    sb.append(field.getName());
                    sb.append("=");
                    sb.append(objInvoke);
                    sb.append("\n");
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            Log.w(ACRA.LOG_TAG, "Error : ", e);
        }
        return sb.toString();
    }

    public static String collectSecureSettings(Context context) throws SecurityException {
        StringBuilder sb = new StringBuilder();
        for (Field field : Settings.Secure.class.getFields()) {
            if (!field.isAnnotationPresent(Deprecated.class) && field.getType() == String.class && isAuthorized(field)) {
                try {
                    String string = Settings.Secure.getString(context.getContentResolver(), (String) field.get(null));
                    if (string != null) {
                        sb.append(field.getName());
                        sb.append("=");
                        sb.append((Object) string);
                        sb.append("\n");
                    }
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    Log.w(ACRA.LOG_TAG, "Error : ", e);
                }
            }
        }
        return sb.toString();
    }

    public static String collectSystemSettings(Context context) throws SecurityException {
        StringBuilder sb = new StringBuilder();
        for (Field field : Settings.System.class.getFields()) {
            if (!field.isAnnotationPresent(Deprecated.class) && field.getType() == String.class) {
                try {
                    String string = Settings.System.getString(context.getContentResolver(), (String) field.get(null));
                    if (string != null) {
                        sb.append(field.getName());
                        sb.append("=");
                        sb.append((Object) string);
                        sb.append("\n");
                    }
                } catch (IllegalAccessException | IllegalArgumentException e) {
                    Log.w(ACRA.LOG_TAG, "Error : ", e);
                }
            }
        }
        return sb.toString();
    }

    private static boolean isAuthorized(Field field) {
        if (field == null || field.getName().startsWith("WIFI_AP")) {
            return false;
        }
        for (String str : ACRA.getConfig().excludeMatchingSettingsKeys()) {
            if (field.getName().matches(str)) {
                return false;
            }
        }
        return true;
    }
}
