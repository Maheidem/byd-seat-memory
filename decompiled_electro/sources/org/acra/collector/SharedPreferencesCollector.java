package org.acra.collector;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import java.util.Map;
import java.util.TreeMap;
import org.acra.ACRA;

/* loaded from: classes.dex */
final class SharedPreferencesCollector {
    SharedPreferencesCollector() {
    }

    public static String collect(Context context) {
        StringBuilder sb = new StringBuilder();
        TreeMap treeMap = new TreeMap();
        treeMap.put("default", PreferenceManager.getDefaultSharedPreferences(context));
        String[] strArrAdditionalSharedPreferences = ACRA.getConfig().additionalSharedPreferences();
        if (strArrAdditionalSharedPreferences != null) {
            for (String str : strArrAdditionalSharedPreferences) {
                treeMap.put(str, context.getSharedPreferences(str, 0));
            }
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            String str2 = (String) entry.getKey();
            Map<String, ?> all = ((SharedPreferences) entry.getValue()).getAll();
            if (all.isEmpty()) {
                sb.append(str2);
                sb.append('=');
                sb.append("empty\n");
            } else {
                for (String str3 : all.keySet()) {
                    if (filteredKey(str3)) {
                        ACRA.log.d(ACRA.LOG_TAG, "Filtered out sharedPreference=" + str2 + "  key=" + str3 + " due to filtering rule");
                    } else {
                        Object obj = all.get(str3);
                        sb.append(str2);
                        sb.append('.');
                        sb.append(str3);
                        sb.append('=');
                        sb.append(obj == null ? "null" : obj.toString());
                        sb.append("\n");
                    }
                }
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    private static boolean filteredKey(String str) {
        for (String str2 : ACRA.getConfig().excludeMatchingSharedPreferencesKeys()) {
            if (str.matches(str2)) {
                return true;
            }
        }
        return false;
    }
}
