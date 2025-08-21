package br.com.rory.electro.database.b;

import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private long f128a;
    private long b;
    private int c;
    private String d;

    static {
        NativeLoader.classesInit0(12);
    }

    public a(long j, long j2, int i, String str) {
        this.f128a = j;
        this.b = j2;
        this.c = i;
        this.d = str;
    }

    public native long a();

    public native int b();

    public native String c();
}
