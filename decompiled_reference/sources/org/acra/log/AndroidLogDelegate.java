package org.acra.log;

import android.util.Log;

/* loaded from: classes.dex */
public final class AndroidLogDelegate implements ACRALog {
    @Override // org.acra.log.ACRALog
    public int d(String str, String str2) {
        return Log.d(str, str2);
    }

    @Override // org.acra.log.ACRALog
    public int d(String str, String str2, Throwable th) {
        return Log.d(str, str2, th);
    }

    @Override // org.acra.log.ACRALog
    public int e(String str, String str2) {
        return Log.e(str, str2);
    }

    @Override // org.acra.log.ACRALog
    public int e(String str, String str2, Throwable th) {
        return Log.e(str, str2, th);
    }

    @Override // org.acra.log.ACRALog
    public String getStackTraceString(Throwable th) {
        return Log.getStackTraceString(th);
    }

    @Override // org.acra.log.ACRALog
    public int i(String str, String str2) {
        return Log.i(str, str2);
    }

    @Override // org.acra.log.ACRALog
    public int i(String str, String str2, Throwable th) {
        return Log.i(str, str2, th);
    }

    @Override // org.acra.log.ACRALog
    public int v(String str, String str2) {
        return Log.v(str, str2);
    }

    @Override // org.acra.log.ACRALog
    public int v(String str, String str2, Throwable th) {
        return Log.v(str, str2, th);
    }

    @Override // org.acra.log.ACRALog
    public int w(String str, String str2) {
        return Log.w(str, str2);
    }

    @Override // org.acra.log.ACRALog
    public int w(String str, String str2, Throwable th) {
        return Log.w(str, str2, th);
    }

    @Override // org.acra.log.ACRALog
    public int w(String str, Throwable th) {
        return Log.w(str, th);
    }
}
