package br.com.rory.electro.c.a.a;

import android.content.Context;
import br.com.rory.electro.c.a.d;
import com.rory.electro.NativeLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/* loaded from: classes.dex */
public class a implements d.a {

    /* renamed from: a, reason: collision with root package name */
    private Context f93a;
    private d b;
    private b c;
    private ExecutorService d;
    private int e = 1;
    private boolean f = false;
    private String g;

    /* renamed from: br.com.rory.electro.c.a.a.a$1, reason: invalid class name */
    class AnonymousClass1 implements ThreadFactory {
        static {
            NativeLoader.classesInit0(169);
        }

        AnonymousClass1() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public native Thread newThread(Runnable runnable);
    }

    /* renamed from: br.com.rory.electro.c.a.a.a$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ byte[] f95a;
        final /* synthetic */ int b;
        final /* synthetic */ int c;
        final /* synthetic */ int d;
        final /* synthetic */ long e;

        static {
            NativeLoader.classesInit0(172);
        }

        AnonymousClass2(byte[] bArr, int i, int i2, int i3, long j) {
            this.f95a = bArr;
            this.b = i;
            this.c = i2;
            this.d = i3;
            this.e = j;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(146);
    }

    public a(Context context, String str) {
        this.f93a = context;
        this.g = str == null ? "default" : str;
        this.b = d.a();
        g();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native void a(byte[] bArr, int i, int i2, int i3, long j);

    private static native String f();

    private native void g();

    @Override // br.com.rory.electro.c.a.d.a
    public native String a();

    public native void a(int i);

    public native void a(b bVar);

    @Override // br.com.rory.electro.c.a.d.a
    public native void a(byte[] bArr, int i, int i2, int i3, int i4, long j);

    @Override // br.com.rory.electro.c.a.d.a
    public native int b();

    public native void c();

    public native void d();

    public native void e();
}
