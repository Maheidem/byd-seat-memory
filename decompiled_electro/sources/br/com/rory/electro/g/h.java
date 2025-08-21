package br.com.rory.electro.g;

import android.content.Context;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class h {

    /* renamed from: br.com.rory.electro.g.h$1, reason: invalid class name */
    static class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f144a;

        static {
            NativeLoader.classesInit0(195);
        }

        AnonymousClass1(Context context) {
            this.f144a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(62);
    }

    public static native void a();

    public static native void a(Context context);

    public static native void b(Context context);

    public static native void c(Context context);

    public static native void d(Context context);
}
