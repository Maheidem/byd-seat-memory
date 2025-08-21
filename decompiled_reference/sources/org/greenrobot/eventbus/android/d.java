package org.greenrobot.eventbus.android;

import android.os.Looper;
import org.greenrobot.eventbus.f;
import org.greenrobot.eventbus.h;
import org.greenrobot.eventbus.l;

/* loaded from: classes.dex */
public class d implements h {
    @Override // org.greenrobot.eventbus.h
    public l a(org.greenrobot.eventbus.c cVar) {
        return new f(cVar, Looper.getMainLooper(), 10);
    }

    @Override // org.greenrobot.eventbus.h
    public boolean a() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
}
