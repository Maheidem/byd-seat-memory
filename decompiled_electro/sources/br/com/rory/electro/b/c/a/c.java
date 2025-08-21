package br.com.rory.electro.b.c.a;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class c implements br.com.rory.electro.b.c.b<String> {

    /* renamed from: a, reason: collision with root package name */
    private static final Object f80a;

    static {
        NativeLoader.classesInit0(249);
        f80a = new Object();
    }

    @Override // br.com.rory.electro.b.c.b
    public native String a();

    @Override // br.com.rory.electro.b.c.b
    public native void a(Context context, PrintWriter printWriter, String str);
}
