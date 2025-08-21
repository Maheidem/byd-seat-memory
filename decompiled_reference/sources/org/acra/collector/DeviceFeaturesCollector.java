package org.acra.collector;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import org.acra.ACRA;

/* loaded from: classes.dex */
final class DeviceFeaturesCollector {
    DeviceFeaturesCollector() {
    }

    public static String getFeatures(Context context) {
        if (Compatibility.getAPILevel() < 5) {
            return "Data available only with API Level >= 5";
        }
        StringBuilder sb = new StringBuilder();
        try {
            for (Object obj : (Object[]) PackageManager.class.getMethod("getSystemAvailableFeatures", (Class[]) null).invoke(context.getPackageManager(), new Object[0])) {
                String str = (String) obj.getClass().getField("name").get(obj);
                if (str != null) {
                    sb.append(str);
                } else {
                    String str2 = (String) obj.getClass().getMethod("getGlEsVersion", (Class[]) null).invoke(obj, new Object[0]);
                    sb.append("glEsVersion = ");
                    sb.append(str2);
                }
                sb.append("\n");
            }
        } catch (Throwable th) {
            Log.w(ACRA.LOG_TAG, "Couldn't retrieve DeviceFeatures for " + context.getPackageName(), th);
            sb.append("Could not retrieve data: ");
            sb.append(th.getMessage());
        }
        return sb.toString();
    }
}
