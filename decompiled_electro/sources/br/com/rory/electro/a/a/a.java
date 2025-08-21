package br.com.rory.electro.a.a;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.a.a.b;
import com.a.a.c;
import com.a.a.d;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static a f41a;
    private c b;
    private Context d;
    private Handler c = new Handler(Looper.getMainLooper());
    private Runnable e = new Runnable() { // from class: br.com.rory.electro.a.a.a.2
        static {
            NativeLoader.classesInit0(51);
        }

        @Override // java.lang.Runnable
        public native void run();
    };

    /* renamed from: br.com.rory.electro.a.a.a$1, reason: invalid class name */
    class AnonymousClass1 implements b {
        static {
            NativeLoader.classesInit0(52);
        }

        AnonymousClass1() {
        }

        @Override // com.a.a.b
        public native String a(byte[] bArr);
    }

    static {
        NativeLoader.classesInit0(109);
    }

    private a(Context context) {
        this.d = context.getApplicationContext();
    }

    public static native synchronized a a(Context context);

    private native d a(String str, String str2);

    /* JADX INFO: Access modifiers changed from: private */
    public static native String c();

    private native b d();

    private native void e();

    private native void f();

    public native String a(String str);

    public native void a();
}
