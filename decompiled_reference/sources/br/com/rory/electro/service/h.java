package br.com.rory.electro.service;

import android.content.Context;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class h {

    private static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private Context f195a;

        static {
            NativeLoader.classesInit0(224);
        }

        public a(Context context) {
            this.f195a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    private static class b implements Runnable {
        static {
            NativeLoader.classesInit0(225);
        }

        private b() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(116);
    }

    public static native Context a();

    /* JADX INFO: Access modifiers changed from: private */
    public static native String c();

    public static native void main(String[] strArr);
}
