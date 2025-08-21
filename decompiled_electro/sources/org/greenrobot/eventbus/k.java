package org.greenrobot.eventbus;

/* loaded from: classes.dex */
final class k {

    /* renamed from: a, reason: collision with root package name */
    private j f222a;
    private j b;

    k() {
    }

    synchronized j a() {
        j jVar;
        jVar = this.f222a;
        if (this.f222a != null) {
            this.f222a = this.f222a.c;
            if (this.f222a == null) {
                this.b = null;
            }
        }
        return jVar;
    }

    synchronized j a(int i) {
        if (this.f222a == null) {
            wait(i);
        }
        return a();
    }

    synchronized void a(j jVar) {
        try {
            if (jVar == null) {
                throw new NullPointerException("null cannot be enqueued");
            }
            if (this.b != null) {
                this.b.c = jVar;
                this.b = jVar;
            } else {
                if (this.f222a != null) {
                    throw new IllegalStateException("Head present, but no tail");
                }
                this.b = jVar;
                this.f222a = jVar;
            }
            notifyAll();
        } catch (Throwable th) {
            throw th;
        }
    }
}
