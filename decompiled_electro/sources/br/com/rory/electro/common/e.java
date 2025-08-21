package br.com.rory.electro.common;

import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private String f117a;
    private String b;
    private StackTraceElement[] c;
    private e d;

    static {
        NativeLoader.classesInit0(55);
    }

    public e() {
    }

    public e(Throwable th) {
        this.f117a = th.getClass().getName();
        this.b = th.getMessage();
        this.c = th.getStackTrace();
        if (th.getCause() != null) {
            this.d = new e(th.getCause());
        }
    }

    public static native e a(String str);

    public native Exception a();

    public native String b();
}
