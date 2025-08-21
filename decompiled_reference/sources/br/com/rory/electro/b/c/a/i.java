package br.com.rory.electro.b.c.a;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class i implements br.com.rory.electro.b.c.b<String> {

    /* renamed from: a, reason: collision with root package name */
    private static final String f85a = "i";

    /* renamed from: br.com.rory.electro.b.c.a.i$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f86a;

        static {
            NativeLoader.classesInit0(171);
        }

        AnonymousClass1(String str) {
            this.f86a = str;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(176);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native String b();

    @Override // br.com.rory.electro.b.c.b
    public native String a();

    @Override // br.com.rory.electro.b.c.b
    public native void a(Context context, PrintWriter printWriter, String str);
}
