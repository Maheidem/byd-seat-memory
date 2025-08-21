package br.com.rory.electro.c.a;

import br.com.rory.electro.g.p;
import com.rory.electro.NativeLoader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static d f97a;
    private static final Object b;
    private g e;
    private final List<a> c = new CopyOnWriteArrayList();
    private final Map<String, ExecutorService> d = new ConcurrentHashMap();
    private boolean f = false;
    private int g = 5;
    private long h = 0;
    private int i = 0;

    /* renamed from: br.com.rory.electro.c.a.d$1, reason: invalid class name */
    class AnonymousClass1 implements Predicate<a> {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ a f98a;

        static {
            NativeLoader.classesInit0(30);
        }

        AnonymousClass1(a aVar) {
            this.f98a = aVar;
        }

        @Override // java.util.function.Predicate
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public native boolean test(a aVar);
    }

    /* renamed from: br.com.rory.electro.c.a.d$2, reason: invalid class name */
    class AnonymousClass2 implements b {
        static {
            NativeLoader.classesInit0(31);
        }

        AnonymousClass2() {
        }

        @Override // br.com.rory.electro.c.a.b
        public native void a(g gVar, byte[] bArr, int i, int i2, int i3, int i4, int i5, long j);

        @Override // br.com.rory.electro.c.a.b
        public native void a(g gVar, byte[] bArr, int i, int i2, int i3, int i4, long j);
    }

    /* renamed from: br.com.rory.electro.c.a.d$3, reason: invalid class name */
    class AnonymousClass3 implements Predicate<ExecutorService> {
        static {
            NativeLoader.classesInit0(38);
        }

        AnonymousClass3() {
        }

        @Override // java.util.function.Predicate
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public native boolean test(ExecutorService executorService);
    }

    /* renamed from: br.com.rory.electro.c.a.d$4, reason: invalid class name */
    class AnonymousClass4 implements ThreadFactory {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f101a;

        static {
            NativeLoader.classesInit0(42);
        }

        AnonymousClass4(String str) {
            this.f101a = str;
        }

        @Override // java.util.concurrent.ThreadFactory
        public native Thread newThread(Runnable runnable);
    }

    public interface a {
        String a();

        void a(byte[] bArr, int i, int i2, int i3, int i4, long j);

        int b();
    }

    static {
        NativeLoader.classesInit0(210);
        b = new Object();
    }

    private d() {
    }

    public static native d a();

    static final /* synthetic */ void a(a aVar, byte[] bArr, int i, int i2, int i3, int i4, long j) {
        try {
            aVar.a(bArr, i, i2, i3, i4, j);
        } catch (Exception e) {
            p.a(b(), "Error dispatching frame to consumer " + aVar.a() + ": " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native void a(byte[] bArr, int i, int i2, int i3, int i4, long j);

    private static native String b();

    private native void c();

    private native void c(a aVar);

    private native void d();

    private native void d(a aVar);

    private native void e();

    public native synchronized void a(a aVar);

    public native synchronized void b(a aVar);
}
