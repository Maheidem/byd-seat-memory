package br.com.rory.electro.g;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.io.File;

/* loaded from: classes.dex */
public class o {
    private static o b;

    /* renamed from: a, reason: collision with root package name */
    long f153a = 0;
    private String c;
    private int d;
    private String e;
    private String f;
    private Context g;

    /* renamed from: br.com.rory.electro.g.o$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        static {
            NativeLoader.classesInit0(144);
        }

        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.g.o$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        static {
            NativeLoader.classesInit0(155);
        }

        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.g.o$3, reason: invalid class name */
    class AnonymousClass3 implements Runnable {

        /* renamed from: br.com.rory.electro.g.o$3$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            static {
                NativeLoader.classesInit0(36);
            }

            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        static {
            NativeLoader.classesInit0(156);
        }

        AnonymousClass3() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        public String f158a;
        public int b;
        public String c;
        public String d;

        public a(String str, int i, String str2, String str3) {
            this.f158a = str;
            this.b = i;
            this.c = str2;
            this.d = str3;
        }
    }

    static {
        NativeLoader.classesInit0(5);
    }

    private o(Context context) {
        this.g = context;
    }

    public static native o a(Context context);

    public static native File b();

    /* JADX INFO: Access modifiers changed from: private */
    public static native String m();

    private native boolean n();

    private native String o();

    /* JADX INFO: Access modifiers changed from: private */
    public native void p();

    /* JADX INFO: Access modifiers changed from: private */
    public native void q();

    private static native String r();

    public native void a();

    public native void c();

    public native void d();

    public native a e();

    public native boolean f();

    public native void g();

    public native boolean h();

    public native void i();

    public native void j();

    public native void k();
}
