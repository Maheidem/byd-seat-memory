package br.com.rory.electro.e;

import android.content.Context;
import android.content.SharedPreferences;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static a f132a;
    private SharedPreferences b;

    static {
        NativeLoader.classesInit0(120);
    }

    private a(Context context) {
        this.b = context.getSharedPreferences("electro_app_config", 0);
    }

    public static native synchronized a a(Context context);

    public native void a(boolean z);

    public native boolean a();

    public native void b(boolean z);

    public native boolean b();
}
