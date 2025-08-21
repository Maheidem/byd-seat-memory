package org.acra.collector;

import android.os.Process;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import org.acra.ACRA;
import org.acra.util.BoundedLinkedList;

/* loaded from: classes.dex */
class LogCatCollector {
    private static final int DEFAULT_TAIL_COUNT = 100;

    LogCatCollector() {
    }

    public static String collectLogCat(String str) throws Throwable {
        String str2;
        BufferedReader bufferedReader;
        final Process processExec;
        int iMyPid = Process.myPid();
        BufferedReader bufferedReader2 = null;
        if (!ACRA.getConfig().logcatFilterByPid() || iMyPid <= 0) {
            str2 = null;
        } else {
            str2 = Integer.toString(iMyPid) + "):";
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add("logcat");
        if (str != null) {
            arrayList.add("-b");
            arrayList.add(str);
        }
        ArrayList arrayList2 = new ArrayList(Arrays.asList(ACRA.getConfig().logcatArguments()));
        int iIndexOf = arrayList2.indexOf("-t");
        int i = -1;
        if (iIndexOf > -1 && iIndexOf < arrayList2.size()) {
            int i2 = iIndexOf + 1;
            int i3 = Integer.parseInt((String) arrayList2.get(i2));
            if (Compatibility.getAPILevel() < 8) {
                arrayList2.remove(i2);
                arrayList2.remove(iIndexOf);
                arrayList2.add("-d");
            }
            i = i3;
        }
        if (i <= 0) {
            i = 100;
        }
        BoundedLinkedList boundedLinkedList = new BoundedLinkedList(i);
        arrayList.addAll(arrayList2);
        try {
            try {
                processExec = Runtime.getRuntime().exec((String[]) arrayList.toArray(new String[arrayList.size()]));
                bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()), 8192);
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
            bufferedReader = bufferedReader2;
        }
        try {
            Log.d(ACRA.LOG_TAG, "Retrieving logcat output...");
            new Thread(new Runnable() { // from class: org.acra.collector.LogCatCollector.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        do {
                        } while (processExec.getErrorStream().read(new byte[8192]) >= 0);
                    } catch (IOException unused) {
                    }
                }
            }).start();
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                if (str2 == null || line.contains(str2)) {
                    boundedLinkedList.add(line + "\n");
                }
            }
            CollectorUtil.safeClose(bufferedReader);
        } catch (IOException e2) {
            e = e2;
            bufferedReader2 = bufferedReader;
            Log.e(ACRA.LOG_TAG, "LogCatCollector.collectLogCat could not retrieve data.", e);
            CollectorUtil.safeClose(bufferedReader2);
            return boundedLinkedList.toString();
        } catch (Throwable th2) {
            th = th2;
            CollectorUtil.safeClose(bufferedReader);
            throw th;
        }
        return boundedLinkedList.toString();
    }
}
