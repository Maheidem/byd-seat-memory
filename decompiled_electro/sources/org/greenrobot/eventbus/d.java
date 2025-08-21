package org.greenrobot.eventbus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.greenrobot.eventbus.g;

/* loaded from: classes.dex */
public class d {
    private static final ExecutorService m = Executors.newCachedThreadPool();
    boolean e;
    boolean g;
    boolean h;
    List<org.greenrobot.eventbus.a.b> j;
    g k;
    h l;

    /* renamed from: a, reason: collision with root package name */
    boolean f218a = true;
    boolean b = true;
    boolean c = true;
    boolean d = true;
    boolean f = true;
    ExecutorService i = m;

    d() {
    }

    g a() {
        return this.k != null ? this.k : g.a.a();
    }

    h b() {
        if (this.l != null) {
            return this.l;
        }
        if (org.greenrobot.eventbus.android.a.a()) {
            return org.greenrobot.eventbus.android.a.b().b;
        }
        return null;
    }
}
