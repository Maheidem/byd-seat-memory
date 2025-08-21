package br.com.rory.electro.c.a;

import br.com.rory.electro.c.a.h;
import com.rory.electro.NativeLoader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class g {
    private Method A;
    private Class<?> B;

    /* renamed from: a, reason: collision with root package name */
    private Object f104a;
    private h.a b;
    private Method c;
    private Method d;
    private Method e;
    private Method f;
    private Method g;
    private Method h;
    private Method i;
    private Method j;
    private Method k;
    private Method l;
    private Method m;
    private Method n;
    private Method o;
    private Method p;
    private Method q;
    private Method r;
    private Method s;
    private Method t;
    private Method u;
    private Method v;
    private Method w;
    private Method x;
    private Method y;
    private Method z;

    /* renamed from: br.com.rory.electro.c.a.g$1, reason: invalid class name */
    class AnonymousClass1 implements InvocationHandler {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ b f105a;

        static {
            NativeLoader.classesInit0(241);
        }

        AnonymousClass1(b bVar) {
            this.f105a = bVar;
        }

        @Override // java.lang.reflect.InvocationHandler
        public native Object invoke(Object obj, Method method, Object[] objArr);
    }

    static {
        NativeLoader.classesInit0(211);
    }

    public g(Object obj, h.a aVar) {
        a(obj, aVar);
    }

    private native void a(Object obj, h.a aVar);

    private static native String d();

    public native void a();

    public native void a(b bVar);

    public native boolean a(int i);

    public native boolean a(int... iArr);

    public native boolean b();

    public native boolean b(int... iArr);

    public native boolean c();
}
