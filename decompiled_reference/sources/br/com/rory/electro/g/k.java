package br.com.rory.electro.g;

import android.content.Context;
import android.os.PowerManager;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class k {

    /* renamed from: a, reason: collision with root package name */
    private static PowerManager f148a;

    static {
        NativeLoader.classesInit0(0);
    }

    public static native Boolean a(Context context);

    public static native void b(Context context);

    public static native void c(Context context);

    public static native void d(Context context);

    private static native PowerManager e(Context context);
}
