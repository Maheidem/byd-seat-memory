package org.greenrobot.eventbus.android;

import android.util.Log;
import java.util.logging.Level;
import org.greenrobot.eventbus.g;

/* loaded from: classes.dex */
public class c implements g {

    /* renamed from: a, reason: collision with root package name */
    private final String f212a;

    public c(String str) {
        this.f212a = str;
    }

    private int a(Level level) {
        int iIntValue = level.intValue();
        if (iIntValue < 800) {
            return iIntValue < 500 ? 2 : 3;
        }
        if (iIntValue < 900) {
            return 4;
        }
        return iIntValue < 1000 ? 5 : 6;
    }

    @Override // org.greenrobot.eventbus.g
    public void a(Level level, String str) {
        if (level != Level.OFF) {
            Log.println(a(level), this.f212a, str);
        }
    }

    @Override // org.greenrobot.eventbus.g
    public void a(Level level, String str, Throwable th) {
        if (level != Level.OFF) {
            Log.println(a(level), this.f212a, str + "\n" + Log.getStackTraceString(th));
        }
    }
}
