package br.com.rory.electro.c.a;

import com.rory.electro.NativeLoader;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private static Class<?> f106a;
    private static Method b;
    private static Method c;
    private static Method d;
    private static Method e;
    private static Class<?> f;
    private static Method g;
    private static Class<?> h;
    private static Method i;

    public enum a {
        NORMAL,
        AVM;

        static {
            NativeLoader.classesInit0(259);
        }

        public static native a valueOf(String str);

        public static native a[] values();
    }

    static {
        NativeLoader.classesInit0(213);
        b();
    }

    public static native int a(String str);

    public static native g a(int i2, a aVar);

    private static native String a();

    public static native boolean a(int i2);

    private static native void b();
}
