package org.greenrobot.eventbus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
class p {

    /* renamed from: a, reason: collision with root package name */
    private static final Map<Class<?>, List<o>> f225a = new ConcurrentHashMap();
    private static final a[] e = new a[4];
    private List<org.greenrobot.eventbus.a.b> b;
    private final boolean c;
    private final boolean d;

    static class a {

        /* renamed from: a, reason: collision with root package name */
        final List<o> f226a = new ArrayList();
        final Map<Class, Object> b = new HashMap();
        final Map<String, Class> c = new HashMap();
        final StringBuilder d = new StringBuilder(128);
        Class<?> e;
        Class<?> f;
        boolean g;
        org.greenrobot.eventbus.a.a h;

        a() {
        }

        private boolean b(Method method, Class<?> cls) {
            this.d.setLength(0);
            this.d.append(method.getName());
            StringBuilder sb = this.d;
            sb.append('>');
            sb.append(cls.getName());
            String string = this.d.toString();
            Class<?> declaringClass = method.getDeclaringClass();
            Class clsPut = this.c.put(string, declaringClass);
            if (clsPut == null || clsPut.isAssignableFrom(declaringClass)) {
                return true;
            }
            this.c.put(string, clsPut);
            return false;
        }

        void a() {
            this.f226a.clear();
            this.b.clear();
            this.c.clear();
            this.d.setLength(0);
            this.e = null;
            this.f = null;
            this.g = false;
            this.h = null;
        }

        void a(Class<?> cls) {
            this.f = cls;
            this.e = cls;
            this.g = false;
            this.h = null;
        }

        boolean a(Method method, Class<?> cls) {
            Object objPut = this.b.put(cls, method);
            if (objPut == null) {
                return true;
            }
            if (objPut instanceof Method) {
                if (!b((Method) objPut, cls)) {
                    throw new IllegalStateException();
                }
                this.b.put(cls, this);
            }
            return b(method, cls);
        }

        void b() {
            if (!this.g) {
                this.f = this.f.getSuperclass();
                String name = this.f.getName();
                if (!name.startsWith("java.") && !name.startsWith("javax.") && !name.startsWith("android.") && !name.startsWith("androidx.")) {
                    return;
                }
            }
            this.f = null;
        }
    }

    p(List<org.greenrobot.eventbus.a.b> list, boolean z, boolean z2) {
        this.b = list;
        this.c = z;
        this.d = z2;
    }

    private List<o> a(a aVar) {
        ArrayList arrayList = new ArrayList(aVar.f226a);
        aVar.a();
        synchronized (e) {
            int i = 0;
            while (true) {
                if (i >= 4) {
                    break;
                }
                try {
                    if (e[i] == null) {
                        e[i] = aVar;
                        break;
                    }
                    i++;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return arrayList;
    }

    private a a() {
        synchronized (e) {
            for (int i = 0; i < 4; i++) {
                try {
                    a aVar = e[i];
                    if (aVar != null) {
                        e[i] = null;
                        return aVar;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return new a();
        }
    }

    private List<o> b(Class<?> cls) throws SecurityException {
        a aVarA = a();
        aVarA.a(cls);
        while (aVarA.f != null) {
            aVarA.h = b(aVarA);
            if (aVarA.h != null) {
                for (o oVar : aVarA.h.b()) {
                    if (aVarA.a(oVar.f224a, oVar.c)) {
                        aVarA.f226a.add(oVar);
                    }
                }
            } else {
                c(aVarA);
            }
            aVarA.b();
        }
        return a(aVarA);
    }

    private org.greenrobot.eventbus.a.a b(a aVar) {
        if (aVar.h != null && aVar.h.c() != null) {
            org.greenrobot.eventbus.a.a aVarC = aVar.h.c();
            if (aVar.f == aVarC.a()) {
                return aVarC;
            }
        }
        if (this.b == null) {
            return null;
        }
        Iterator<org.greenrobot.eventbus.a.b> it = this.b.iterator();
        while (it.hasNext()) {
            org.greenrobot.eventbus.a.a aVarA = it.next().a(aVar.f);
            if (aVarA != null) {
                return aVarA;
            }
        }
        return null;
    }

    private List<o> c(Class<?> cls) throws SecurityException {
        a aVarA = a();
        aVarA.a(cls);
        while (aVarA.f != null) {
            c(aVarA);
            aVarA.b();
        }
        return a(aVarA);
    }

    private void c(a aVar) throws SecurityException {
        StringBuilder sb;
        String str;
        Method[] methods;
        try {
            try {
                methods = aVar.f.getDeclaredMethods();
            } catch (LinkageError e2) {
                String str2 = "Could not inspect methods of " + aVar.f.getName();
                if (this.d) {
                    sb = new StringBuilder();
                    sb.append(str2);
                    str = ". Please consider using EventBus annotation processor to avoid reflection.";
                } else {
                    sb = new StringBuilder();
                    sb.append(str2);
                    str = ". Please make this class visible to EventBus annotation processor to avoid reflection.";
                }
                sb.append(str);
                throw new e(sb.toString(), e2);
            }
        } catch (Throwable unused) {
            methods = aVar.f.getMethods();
            aVar.g = true;
        }
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if ((modifiers & 1) != 0 && (modifiers & 5192) == 0) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    m mVar = (m) method.getAnnotation(m.class);
                    if (mVar != null) {
                        Class<?> cls = parameterTypes[0];
                        if (aVar.a(method, cls)) {
                            aVar.f226a.add(new o(method, cls, mVar.a(), mVar.c(), mVar.b()));
                        }
                    }
                } else if (this.c && method.isAnnotationPresent(m.class)) {
                    throw new e("@Subscribe method " + (method.getDeclaringClass().getName() + "." + method.getName()) + "must have exactly 1 parameter but has " + parameterTypes.length);
                }
            } else if (this.c && method.isAnnotationPresent(m.class)) {
                throw new e((method.getDeclaringClass().getName() + "." + method.getName()) + " is a illegal @Subscribe method: must be public, non-static, and non-abstract");
            }
        }
    }

    List<o> a(Class<?> cls) {
        List<o> list = f225a.get(cls);
        if (list != null) {
            return list;
        }
        List<o> listC = this.d ? c(cls) : b(cls);
        if (!listC.isEmpty()) {
            f225a.put(cls, listC);
            return listC;
        }
        throw new e("Subscriber " + cls + " and its super classes have no public methods with the @Subscribe annotation");
    }
}
