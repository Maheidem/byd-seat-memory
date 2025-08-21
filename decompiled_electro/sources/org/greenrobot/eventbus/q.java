package org.greenrobot.eventbus;

/* loaded from: classes.dex */
final class q {

    /* renamed from: a, reason: collision with root package name */
    final Object f227a;
    final o b;
    volatile boolean c = true;

    q(Object obj, o oVar) {
        this.f227a = obj;
        this.b = oVar;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof q)) {
            return false;
        }
        q qVar = (q) obj;
        return this.f227a == qVar.f227a && this.b.equals(qVar.b);
    }

    public int hashCode() {
        return this.f227a.hashCode() + this.b.f.hashCode();
    }
}
