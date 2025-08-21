package org.acra.collector;

import android.os.Process;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.acra.ACRA;

/* loaded from: classes.dex */
final class DumpSysCollector {
    DumpSysCollector() {
    }

    public static String collectMemInfo() throws IOException {
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add("dumpsys");
            arrayList.add("meminfo");
            arrayList.add(Integer.toString(Process.myPid()));
            bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec((String[]) arrayList.toArray(new String[arrayList.size()])).getInputStream()), 8192);
            while (true) {
                try {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                    sb.append("\n");
                } catch (IOException e) {
                    e = e;
                    Log.e(ACRA.LOG_TAG, "DumpSysCollector.meminfo could not retrieve data", e);
                    CollectorUtil.safeClose(bufferedReader);
                    return sb.toString();
                }
            }
        } catch (IOException e2) {
            e = e2;
            bufferedReader = null;
        }
        CollectorUtil.safeClose(bufferedReader);
        return sb.toString();
    }
}
