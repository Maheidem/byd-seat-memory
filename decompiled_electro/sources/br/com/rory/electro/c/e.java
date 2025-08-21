package br.com.rory.electro.c;

import android.content.Context;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class e {

    /* renamed from: br.com.rory.electro.c.e$1, reason: invalid class name */
    static class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f116a;

        static {
            NativeLoader.classesInit0(8);
        }

        AnonymousClass1(Context context) {
            this.f116a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(197);
    }

    public static native short a();

    public static native void a(Context context);

    public static native void a(Context context, int i);

    public static native short b();

    public static native void b(Context context);

    public static native void b(Context context, int i);

    public static native short c();

    public static native void c(Context context);

    public static native short d();

    public static native void d(Context context);

    public static native String e();

    public static native void e(Context context);

    public static native String f();

    public static native void f(Context context);

    public static native void g(Context context);

    public static native void h(Context context);

    public static native void i(Context context);
}
