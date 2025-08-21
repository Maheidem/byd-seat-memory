package br.com.rory.electro.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.R;
import br.com.rory.electro.database.a.d;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static c f191a;
    private final br.com.rory.electro.database.a.c b;
    private final d c;
    private Thread d;
    private Context h;
    private boolean e = false;
    private volatile boolean f = false;
    private long g = -1;
    private final Object i = new Object();

    /* renamed from: br.com.rory.electro.service.c$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        static {
            NativeLoader.classesInit0(R.styleable.AppCompatTheme_windowMinWidthMinor);
        }

        AnonymousClass1() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.service.c$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        static {
            NativeLoader.classesInit0(115);
        }

        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    private class a implements Runnable {
        static {
            NativeLoader.classesInit0(163);
        }

        private a() {
        }

        /* synthetic */ a(c cVar, AnonymousClass1 anonymousClass1) {
            this();
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(R.styleable.AppCompatTheme_windowNoTitle);
    }

    private c(Context context) {
        SQLiteDatabase sQLiteDatabaseA = br.com.rory.electro.database.b.a();
        this.b = new br.com.rory.electro.database.a.c(sQLiteDatabaseA);
        this.c = new d(sQLiteDatabaseA);
        this.h = context;
    }

    public static native synchronized c a(Context context);

    public native boolean a();

    public native boolean b();
}
