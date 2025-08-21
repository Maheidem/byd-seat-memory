package br.com.rory.electro.g;

import android.content.Context;
import android.net.wifi.WifiManager;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class q {

    /* renamed from: a, reason: collision with root package name */
    private static WifiManager f162a;

    static {
        NativeLoader.classesInit0(13);
    }

    public static native boolean a(Context context);

    public static native void b(Context context);

    private static native WifiManager c(Context context);
}
