package br.com.rory.electro.service;

import android.content.Context;
import android.support.design.R;
import com.rory.electro.NativeLoader;
import java.io.BufferedReader;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static final Object f186a;
    private static boolean b;
    private static int c;
    private static Thread d;

    /* renamed from: br.com.rory.electro.service.b$1, reason: invalid class name */
    static class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f187a;

        static {
            NativeLoader.classesInit0(145);
        }

        AnonymousClass1(Context context) {
            this.f187a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.service.b$2, reason: invalid class name */
    static class AnonymousClass2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f188a;
        final /* synthetic */ a b;

        static {
            NativeLoader.classesInit0(143);
        }

        AnonymousClass2(Context context, a aVar) {
            this.f188a = context;
            this.b = aVar;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.service.b$3, reason: invalid class name */
    static class AnonymousClass3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f189a;

        /* renamed from: br.com.rory.electro.service.b$3$1, reason: invalid class name */
        class AnonymousClass1 implements a {
            static {
                NativeLoader.classesInit0(201);
            }

            AnonymousClass1() {
            }

            @Override // br.com.rory.electro.service.b.a
            public native void a(PrintWriter printWriter, BufferedReader bufferedReader);
        }

        static {
            NativeLoader.classesInit0(150);
        }

        AnonymousClass3(Context context) {
            this.f189a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    private interface a {
        void a(PrintWriter printWriter, BufferedReader bufferedReader);
    }

    static {
        NativeLoader.classesInit0(R.styleable.AppCompatTheme_windowFixedWidthMinor);
        f186a = new Object();
    }

    public static native void a();

    public static native void a(Context context);

    private static native void b(Context context);

    /* JADX INFO: Access modifiers changed from: private */
    public static native String c();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void c(Context context, a aVar);

    /* JADX INFO: Access modifiers changed from: private */
    public static native void d(Context context, a aVar);
}
