package br.com.rory.electro.g;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.net.Socket;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    private Context f149a;
    private Socket b;
    private Thread c;
    private Thread d;
    private long f = 0;
    private boolean e = false;

    /* renamed from: br.com.rory.electro.g.l$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {

        /* renamed from: br.com.rory.electro.g.l$1$1, reason: invalid class name and collision with other inner class name */
        class RunnableC00101 implements Runnable {
            static {
                NativeLoader.classesInit0(164);
            }

            RunnableC00101() {
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        static {
            NativeLoader.classesInit0(39);
        }

        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.g.l$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        static {
            NativeLoader.classesInit0(43);
        }

        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(1);
    }

    public l(Context context) {
        this.f149a = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native String f();

    /* JADX INFO: Access modifiers changed from: private */
    public static native String g();

    /* JADX INFO: Access modifiers changed from: private */
    public static native int h();

    /* JADX INFO: Access modifiers changed from: private */
    public native synchronized void i();

    /* JADX INFO: Access modifiers changed from: private */
    public native synchronized void j();

    /* JADX INFO: Access modifiers changed from: private */
    public native synchronized void k();

    /* JADX INFO: Access modifiers changed from: private */
    public native synchronized void l();

    public native synchronized void a();

    public native void a(String str, JSONObject jSONObject);

    public native synchronized void b();
}
