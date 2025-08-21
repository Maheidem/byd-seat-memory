package br.com.rory.electro.service;

import android.support.design.R;
import com.rory.electro.NativeLoader;
import java.net.Socket;

/* loaded from: classes.dex */
public class Loader {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f174a = true;

    /* renamed from: br.com.rory.electro.service.Loader$1, reason: invalid class name */
    static class AnonymousClass1 implements Runnable {
        static {
            NativeLoader.classesInit0(R.styleable.AppCompatTheme_windowMinWidthMajor);
        }

        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    private static class a implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        private Socket f175a;

        static {
            NativeLoader.classesInit0(162);
        }

        public a(Socket socket) {
            this.f175a = socket;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(157);
    }

    public static native int a();

    public static native void main(String[] strArr);
}
