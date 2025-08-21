package org.greenrobot.eventbus;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
final class j {
    private static final List<j> d = new ArrayList();

    /* renamed from: a, reason: collision with root package name */
    Object f221a;
    q b;
    j c;

    private j(Object obj, q qVar) {
        this.f221a = obj;
        this.b = qVar;
    }

    static j a(q qVar, Object obj) {
        synchronized (d) {
            int size = d.size();
            if (size <= 0) {
                return new j(obj, qVar);
            }
            j jVarRemove = d.remove(size - 1);
            jVarRemove.f221a = obj;
            jVarRemove.b = qVar;
            jVarRemove.c = null;
            return jVarRemove;
        }
    }

    static void a(j jVar) {
        jVar.f221a = null;
        jVar.b = null;
        jVar.c = null;
        synchronized (d) {
            if (d.size() < 10000) {
                d.add(jVar);
            }
        }
    }
}
