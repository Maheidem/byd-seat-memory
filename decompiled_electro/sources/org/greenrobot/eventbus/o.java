package org.greenrobot.eventbus;

import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class o {

    /* renamed from: a, reason: collision with root package name */
    final Method f224a;
    final ThreadMode b;
    final Class<?> c;
    final int d;
    final boolean e;
    String f;

    public o(Method method, Class<?> cls, ThreadMode threadMode, int i, boolean z) {
        this.f224a = method;
        this.b = threadMode;
        this.c = cls;
        this.d = i;
        this.e = z;
    }

    private synchronized void a() {
        if (this.f == null) {
            StringBuilder sb = new StringBuilder(64);
            sb.append(this.f224a.getDeclaringClass().getName());
            sb.append('#');
            sb.append(this.f224a.getName());
            sb.append('(');
            sb.append(this.c.getName());
            this.f = sb.toString();
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof o)) {
            return false;
        }
        a();
        o oVar = (o) obj;
        oVar.a();
        return this.f.equals(oVar.f);
    }

    public int hashCode() {
        return this.f224a.hashCode();
    }
}
