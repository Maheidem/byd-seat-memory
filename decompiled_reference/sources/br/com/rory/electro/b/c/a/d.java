package br.com.rory.electro.b.c.a;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class d implements br.com.rory.electro.b.c.b<String> {

    /* renamed from: br.com.rory.electro.b.c.a.d$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f81a;

        static {
            NativeLoader.classesInit0(230);
        }

        AnonymousClass1(Context context) {
            this.f81a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(251);
    }

    @Override // br.com.rory.electro.b.c.b
    public native String a();

    public native String a(br.com.rory.electro.b.c.a aVar);

    @Override // br.com.rory.electro.b.c.b
    public native void a(Context context, PrintWriter printWriter, String str);
}
