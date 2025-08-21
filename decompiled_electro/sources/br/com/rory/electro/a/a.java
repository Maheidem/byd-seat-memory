package br.com.rory.electro.a;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f37a;
    private Socket b;
    private Socket c;
    private CountDownLatch d;

    /* renamed from: br.com.rory.electro.a.a$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ InputStream f38a;
        final /* synthetic */ OutputStream b;

        static {
            NativeLoader.classesInit0(22);
        }

        AnonymousClass1(InputStream inputStream, OutputStream outputStream) {
            this.f38a = inputStream;
            this.b = outputStream;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.a.a$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ InputStream f39a;
        final /* synthetic */ OutputStream b;

        static {
            NativeLoader.classesInit0(32);
        }

        AnonymousClass2(InputStream inputStream, OutputStream outputStream) {
            this.f39a = inputStream;
            this.b = outputStream;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.a.a$3, reason: invalid class name */
    static class AnonymousClass3 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f40a;
        final /* synthetic */ boolean b;

        static {
            NativeLoader.classesInit0(33);
        }

        AnonymousClass3(Context context, boolean z) {
            this.f40a = context;
            this.b = z;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(221);
    }

    public static native void a(Context context, boolean z);

    /* JADX INFO: Access modifiers changed from: private */
    public native void a(InputStream inputStream, OutputStream outputStream);

    public static native boolean b();

    public static native boolean b(int i);

    private static native String c();

    private static native int d();

    private static native String e();

    private static native int f();

    public native void a();

    public native void a(Context context);

    public native boolean a(int i);
}
