package org.acra.collector;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.acra.util.BoundedLinkedList;

/* loaded from: classes.dex */
class LogFileCollector {
    private LogFileCollector() {
    }

    public static String collectLogFile(Context context, String str, int i) throws IOException {
        BoundedLinkedList boundedLinkedList = new BoundedLinkedList(i);
        BufferedReader bufferedReader = str.contains("/") ? new BufferedReader(new InputStreamReader(new FileInputStream(str)), 1024) : new BufferedReader(new InputStreamReader(context.openFileInput(str)), 1024);
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    CollectorUtil.safeClose(bufferedReader);
                    return boundedLinkedList.toString();
                }
                boundedLinkedList.add(line + "\n");
            } catch (Throwable th) {
                CollectorUtil.safeClose(bufferedReader);
                throw th;
            }
        }
    }
}
