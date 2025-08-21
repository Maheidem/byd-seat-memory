package br.com.rory.electro.b.c.a;

import android.content.Context;
import android.support.v4.view.InputDeviceCompat;
import com.rory.electro.NativeLoader;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class g implements br.com.rory.electro.b.c.b<String> {

    /* renamed from: a, reason: collision with root package name */
    private static String f83a;
    private static final Object b;

    static {
        NativeLoader.classesInit0(InputDeviceCompat.SOURCE_KEYBOARD);
        b = new Object();
    }

    public static native String b();

    @Override // br.com.rory.electro.b.c.b
    public native String a();

    public native String a(br.com.rory.electro.b.c.a aVar);

    @Override // br.com.rory.electro.b.c.b
    public native void a(Context context, PrintWriter printWriter, String str);
}
