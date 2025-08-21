package br.com.rory.electro.g;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    private static int f146a = 15000;
    private static Timer b;

    /* renamed from: br.com.rory.electro.g.j$1, reason: invalid class name */
    static class AnonymousClass1 extends TimerTask {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f147a;

        static {
            NativeLoader.classesInit0(223);
        }

        AnonymousClass1(Context context) {
            this.f147a = context;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(4);
    }

    public static native void a();

    public static native void a(Context context);

    public static native void b();

    /* JADX INFO: Access modifiers changed from: private */
    public static native void c(Context context);
}
