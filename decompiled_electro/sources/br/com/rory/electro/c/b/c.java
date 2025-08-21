package br.com.rory.electro.c.b;

import android.content.Context;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class c {

    /* renamed from: br.com.rory.electro.c.b.c$1, reason: invalid class name */
    static class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f113a;

        static {
            NativeLoader.classesInit0(83);
        }

        AnonymousClass1(Context context) {
            this.f113a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(79);
    }

    public static native void a(Context context);

    /* JADX INFO: Access modifiers changed from: private */
    public static native String b();

    public static native void b(Context context);
}
