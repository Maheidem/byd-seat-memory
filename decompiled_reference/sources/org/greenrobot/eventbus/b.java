package org.greenrobot.eventbus;

import java.util.logging.Level;

/* loaded from: classes.dex */
final class b implements Runnable, l {

    /* renamed from: a, reason: collision with root package name */
    private final k f213a = new k();
    private final c b;
    private volatile boolean c;

    b(c cVar) {
        this.b = cVar;
    }

    @Override // org.greenrobot.eventbus.l
    public void a(q qVar, Object obj) {
        j jVarA = j.a(qVar, obj);
        synchronized (this) {
            this.f213a.a(jVarA);
            if (!this.c) {
                this.c = true;
                this.b.b().execute(this);
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        while (true) {
            try {
                j jVarA = this.f213a.a(1000);
                if (jVarA == null) {
                    synchronized (this) {
                        jVarA = this.f213a.a();
                        if (jVarA == null) {
                            return;
                        }
                    }
                }
                this.b.a(jVarA);
            } catch (InterruptedException e) {
                this.b.c().a(Level.WARNING, Thread.currentThread().getName() + " was interruppted", e);
                return;
            } finally {
                this.c = false;
            }
        }
    }
}
