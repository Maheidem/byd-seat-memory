package br.com.rory.electro.c;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f114a;
    private static Map<String, Object> b;

    static {
        NativeLoader.classesInit0(191);
        b = new HashMap();
    }

    public static native Object a(Context context, String str);

    private static native String a();

    public static native void a(Context context, String str, short s, String str2);

    public static native byte[] a(short s);

    public static native byte[] a(byte[] bArr, byte[] bArr2);
}
