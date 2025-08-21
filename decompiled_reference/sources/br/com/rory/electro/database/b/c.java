package br.com.rory.electro.database.b;

import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private long f130a;
    private long b;
    private long c;
    private double d;
    private double e;
    private double f;
    private double g;
    private int h;

    static {
        NativeLoader.classesInit0(10);
    }

    public c(long j, long j2, long j3, double d, double d2, double d3, double d4, int i) {
        this.f130a = j;
        this.b = j2;
        this.c = j3;
        this.d = d;
        this.e = d2;
        this.f = d3;
        this.g = d4;
        this.h = i;
    }

    public native long a();

    public native long b();

    public native long c();

    public native double d();

    public native double e();

    public native double f();

    public native double g();

    public native int h();
}
