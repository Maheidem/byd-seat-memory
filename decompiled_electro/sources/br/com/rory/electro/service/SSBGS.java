package br.com.rory.electro.service;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.lang.Thread;
import java.net.Socket;

/* loaded from: classes.dex */
public class SSBGS {

    /* renamed from: a, reason: collision with root package name */
    private static String f179a;
    private static Thread b;

    /* renamed from: br.com.rory.electro.service.SSBGS$1, reason: invalid class name */
    static class AnonymousClass1 implements br.com.rory.electro.common.a<String> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f180a;

        AnonymousClass1(Context context) {
            this.f180a = context;
        }
    }

    /* renamed from: br.com.rory.electro.service.SSBGS$2, reason: invalid class name */
    static class AnonymousClass2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f181a;

        static {
            NativeLoader.classesInit0(141);
        }

        AnonymousClass2(Context context) {
            this.f181a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.service.SSBGS$3, reason: invalid class name */
    static class AnonymousClass3 implements Runnable {
        static {
            NativeLoader.classesInit0(149);
        }

        AnonymousClass3() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.service.SSBGS$4, reason: invalid class name */
    static class AnonymousClass4 implements Runnable {
        static {
            NativeLoader.classesInit0(148);
        }

        AnonymousClass4() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.service.SSBGS$5, reason: invalid class name */
    static class AnonymousClass5 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f182a;

        static {
            NativeLoader.classesInit0(153);
        }

        AnonymousClass5(Context context) {
            this.f182a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    private static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private Socket f183a;
        private Context b;

        /* renamed from: br.com.rory.electro.service.SSBGS$a$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            static {
                NativeLoader.classesInit0(142);
            }

            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        static {
            NativeLoader.classesInit0(180);
        }

        public a(Socket socket, Context context) {
            this.f183a = socket;
            this.b = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    private static class b implements Thread.UncaughtExceptionHandler {
        static {
            NativeLoader.classesInit0(179);
        }

        private b() {
        }

        /* synthetic */ b(AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public native void uncaughtException(Thread thread, Throwable th);
    }

    static {
        NativeLoader.classesInit0(177);
    }

    public static native int a();

    private static native void a(Context context);

    public static native Context b();

    public static native void c();

    public static native String d();

    private static native void e();

    public static native void main(String[] strArr);
}
