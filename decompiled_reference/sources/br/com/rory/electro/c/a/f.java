package br.com.rory.electro.c.a;

import br.com.rory.electro.c.a.d;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
final /* synthetic */ class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    private final d.a f103a;
    private final byte[] b;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final long g;

    static {
        NativeLoader.classesInit0(208);
    }

    f(d.a aVar, byte[] bArr, int i, int i2, int i3, int i4, long j) {
        this.f103a = aVar;
        this.b = bArr;
        this.c = i;
        this.d = i2;
        this.e = i3;
        this.f = i4;
        this.g = j;
    }

    @Override // java.lang.Runnable
    public native void run();
}
