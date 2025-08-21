package br.com.rory.electro.g;

import android.app.Activity;
import android.content.Context;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class p {

    /* renamed from: br.com.rory.electro.g.p$1, reason: invalid class name */
    static class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f159a;

        static {
            NativeLoader.classesInit0(185);
        }

        AnonymousClass1(Context context) {
            this.f159a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.g.p$2, reason: invalid class name */
    static class AnonymousClass2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Exception f160a;

        static {
            NativeLoader.classesInit0(187);
        }

        AnonymousClass2(Exception exc) {
            this.f160a = exc;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.g.p$3, reason: invalid class name */
    static class AnonymousClass3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Exception f161a;

        static {
            NativeLoader.classesInit0(183);
        }

        AnonymousClass3(Exception exc) {
            this.f161a = exc;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(6);
    }

    public static native int a(String[] strArr, String str);

    public static native String a(Context context);

    public static native void a(long j);

    public static native void a(Context context, Activity activity);

    public static native void a(Context context, Exception exc, String str);

    public static native void a(Exception exc);

    public static native void a(Exception exc, String str);

    public static native void a(String str, String str2);

    public static native String b(Context context);

    public static native void b(Exception exc);

    public static native String c(Context context);

    public static native String d(Context context);

    public static native void e(Context context);

    public static native void f(Context context);
}
