package br.com.rory.electro.g;

import android.content.Context;
import android.content.IntentSender;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class g {
    static {
        NativeLoader.classesInit0(61);
    }

    public static native int a(Context context);

    private static native IntentSender a(Context context, int i);

    public static native boolean a(Context context, String str);

    public static native boolean a(Context context, String str, String str2);

    public static native String b(Context context);

    public static native void b(Context context, String str);
}
