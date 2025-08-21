package org.acra.collector;

import java.io.IOException;
import java.io.Reader;

/* loaded from: classes.dex */
public final class CollectorUtil {
    public static void safeClose(Reader reader) throws IOException {
        if (reader == null) {
            return;
        }
        try {
            reader.close();
        } catch (IOException unused) {
        }
    }
}
