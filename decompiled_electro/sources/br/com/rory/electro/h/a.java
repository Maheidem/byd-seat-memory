package br.com.rory.electro.h;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.security.KeyPair;
import java.security.PublicKey;

/* loaded from: classes.dex */
public class a {

    /* renamed from: br.com.rory.electro.h.a$1, reason: invalid class name */
    static class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f163a;
        final /* synthetic */ KeyPair b;

        static {
            NativeLoader.classesInit0(235);
        }

        AnonymousClass1(Context context, KeyPair keyPair) {
            this.f163a = context;
            this.b = keyPair;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(260);
    }

    public static native String a();

    public static native String a(String str);

    public static native String a(PublicKey publicKey);

    public static native void a(Context context);

    public static native boolean b();

    public static native PublicKey c();
}
