package org.greenrobot.eventbus;

import java.util.logging.Level;

/* loaded from: classes.dex */
public interface g {

    public static class a {
        public static g a() {
            return org.greenrobot.eventbus.android.a.a() ? org.greenrobot.eventbus.android.a.b().f211a : new b();
        }
    }

    public static class b implements g {
        @Override // org.greenrobot.eventbus.g
        public void a(Level level, String str) {
            System.out.println("[" + level + "] " + str);
        }

        @Override // org.greenrobot.eventbus.g
        public void a(Level level, String str, Throwable th) {
            System.out.println("[" + level + "] " + str);
            th.printStackTrace(System.out);
        }
    }

    void a(Level level, String str);

    void a(Level level, String str, Throwable th);
}
