package br.com.rory.electro.b.c.a;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class h implements br.com.rory.electro.b.c.b<String> {

    /* renamed from: br.com.rory.electro.b.c.a.h$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f84a;

        static {
            NativeLoader.classesInit0(125);
        }

        AnonymousClass1(String str) {
            this.f84a = str;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(175);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native String b();

    @Override // br.com.rory.electro.b.c.b
    public native String a();

    public native String a(br.com.rory.electro.b.c.a aVar, String str, Throwable th);

    @Override // br.com.rory.electro.b.c.b
    public native void a(Context context, PrintWriter printWriter, String str);
}
