package org.webrtc;

import org.webrtc.Logging;

/* loaded from: classes.dex */
public class CallSessionFileRotatingLogSink {
    private long nativeSink;

    public CallSessionFileRotatingLogSink(String str, int i, Logging.Severity severity) {
        if (str == null) {
            throw new IllegalArgumentException("dirPath may not be null.");
        }
        this.nativeSink = nativeAddSink(str, i, severity.ordinal());
    }

    public static byte[] getLogData(String str) {
        if (str == null) {
            throw new IllegalArgumentException("dirPath may not be null.");
        }
        return nativeGetLogData(str);
    }

    private static native long nativeAddSink(String str, int i, int i2);

    private static native void nativeDeleteSink(long j);

    private static native byte[] nativeGetLogData(String str);

    public void dispose() {
        if (this.nativeSink != 0) {
            nativeDeleteSink(this.nativeSink);
            this.nativeSink = 0L;
        }
    }
}
