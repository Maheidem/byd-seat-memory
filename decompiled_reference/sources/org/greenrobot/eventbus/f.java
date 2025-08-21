package org.greenrobot.eventbus;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;

/* loaded from: classes.dex */
public class f extends Handler implements l {

    /* renamed from: a, reason: collision with root package name */
    private final k f219a;
    private final int b;
    private final c c;
    private boolean d;

    public f(c cVar, Looper looper, int i) {
        super(looper);
        this.c = cVar;
        this.b = i;
        this.f219a = new k();
    }

    @Override // org.greenrobot.eventbus.l
    public void a(q qVar, Object obj) {
        j jVarA = j.a(qVar, obj);
        synchronized (this) {
            this.f219a.a(jVarA);
            if (!this.d) {
                this.d = true;
                if (!sendMessage(obtainMessage())) {
                    throw new e("Could not send handler message");
                }
            }
        }
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        try {
            long jUptimeMillis = SystemClock.uptimeMillis();
            do {
                j jVarA = this.f219a.a();
                if (jVarA == null) {
                    synchronized (this) {
                        jVarA = this.f219a.a();
                        if (jVarA == null) {
                            this.d = false;
                            return;
                        }
                    }
                }
                this.c.a(jVarA);
            } while (SystemClock.uptimeMillis() - jUptimeMillis < this.b);
            if (!sendMessage(obtainMessage())) {
                throw new e("Could not send handler message");
            }
            this.d = true;
        } finally {
            this.d = false;
        }
    }
}
