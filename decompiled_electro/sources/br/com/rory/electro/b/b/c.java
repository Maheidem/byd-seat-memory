package br.com.rory.electro.b.b;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.net.Socket;

/* loaded from: classes.dex */
public class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private static Context f75a;
    private static final Class<? extends b<?>>[] b;

    class a implements Runnable {
        private Socket b;

        static {
            NativeLoader.classesInit0(85);
        }

        public a(Socket socket) {
            this.b = socket;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(261);
        b = new Class[]{br.com.rory.electro.b.b.a.a.class};
    }

    public c(Context context) {
        f75a = context;
    }

    public static native int a();

    @Override // java.lang.Runnable
    public native void run();
}
