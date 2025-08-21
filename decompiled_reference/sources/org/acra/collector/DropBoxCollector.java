package org.acra.collector;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import org.acra.ACRA;

/* loaded from: classes.dex */
final class DropBoxCollector {
    private static final String NO_RESULT = "N/A";
    private static final String[] SYSTEM_TAGS = {"system_app_anr", "system_app_wtf", "system_app_crash", "system_server_anr", "system_server_wtf", "system_server_crash", "BATTERY_DISCHARGE_INFO", "SYSTEM_RECOVERY_LOG", "SYSTEM_BOOT", "SYSTEM_LAST_KMSG", "APANIC_CONSOLE", "APANIC_THREADS", "SYSTEM_RESTART", "SYSTEM_TOMBSTONE", "data_app_strictmode"};

    DropBoxCollector() {
    }

    public static String read(Context context, String[] strArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        char c;
        try {
            String dropBoxServiceName = Compatibility.getDropBoxServiceName();
            if (dropBoxServiceName == null) {
                return NO_RESULT;
            }
            Object systemService = context.getSystemService(dropBoxServiceName);
            int i = 2;
            char c2 = 0;
            int i2 = 1;
            Method method = systemService.getClass().getMethod("getNextEntry", String.class, Long.TYPE);
            if (method == null) {
                return "";
            }
            Time time = new Time();
            time.setToNow();
            time.minute -= ACRA.getConfig().dropboxCollectionMinutes();
            time.normalize(false);
            long millis = time.toMillis(false);
            ArrayList<String> arrayList = new ArrayList();
            if (ACRA.getConfig().includeDropBoxSystemTags()) {
                arrayList.addAll(Arrays.asList(SYSTEM_TAGS));
            }
            if (strArr != null && strArr.length > 0) {
                arrayList.addAll(Arrays.asList(strArr));
            }
            if (arrayList.isEmpty()) {
                return "No tag configured for collection.";
            }
            StringBuilder sb = new StringBuilder();
            for (String str : arrayList) {
                sb.append("Tag: ");
                sb.append(str);
                char c3 = '\n';
                sb.append('\n');
                Object[] objArr = new Object[i];
                objArr[c2] = str;
                objArr[i2] = Long.valueOf(millis);
                Object objInvoke = method.invoke(systemService, objArr);
                if (objInvoke == null) {
                    sb.append("Nothing.");
                    sb.append('\n');
                } else {
                    Class<?> cls = objInvoke.getClass();
                    Class<?>[] clsArr = new Class[i2];
                    clsArr[c2] = Integer.TYPE;
                    Method method2 = cls.getMethod("getText", clsArr);
                    Method method3 = objInvoke.getClass().getMethod("getTimeMillis", (Class[]) null);
                    Method method4 = objInvoke.getClass().getMethod("close", (Class[]) null);
                    while (objInvoke != null) {
                        long jLongValue = ((Long) method3.invoke(objInvoke, (Object[]) null)).longValue();
                        time.set(jLongValue);
                        sb.append("@");
                        sb.append(time.format2445());
                        sb.append(c3);
                        String str2 = (String) method2.invoke(objInvoke, 500);
                        if (str2 != null) {
                            sb.append("Text: ");
                            sb.append(str2);
                            sb.append('\n');
                            c = '\n';
                        } else {
                            sb.append("Not Text!");
                            c = '\n';
                            sb.append('\n');
                        }
                        method4.invoke(objInvoke, (Object[]) null);
                        objInvoke = method.invoke(systemService, str, Long.valueOf(jLongValue));
                        c3 = c;
                        i2 = 1;
                    }
                    i = 2;
                    c2 = 0;
                }
            }
            return sb.toString();
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | NoSuchMethodException | SecurityException | InvocationTargetException unused) {
            Log.i(ACRA.LOG_TAG, "DropBoxManager not available.");
            return NO_RESULT;
        }
    }
}
