package br.com.rory.electro.b.d;

import android.content.Context;
import br.com.rory.electro.b.d.b.d;
import br.com.rory.electro.b.d.b.e;
import br.com.rory.electro.b.d.b.f;
import com.rory.electro.NativeLoader;
import java.net.Socket;

/* loaded from: classes.dex */
public class c implements Runnable {
    private static final Class<? extends b>[] b;

    /* renamed from: a, reason: collision with root package name */
    private Context f91a;

    class a implements Runnable {
        private Socket b;

        static {
            NativeLoader.classesInit0(219);
        }

        public a(Socket socket) {
            this.b = socket;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(101);
        b = new Class[]{br.com.rory.electro.b.d.b.a.class, br.com.rory.electro.b.d.b.b.class, br.com.rory.electro.b.d.b.c.class, d.class, e.class, f.class};
    }

    public c(Context context) {
        this.f91a = context;
    }

    public static native int a();

    @Override // java.lang.Runnable
    public native void run();
}
