package br.com.rory.electro.g;

import com.rory.electro.NativeLoader;
import java.util.function.Function;

/* loaded from: classes.dex */
final /* synthetic */ class f implements Function {

    /* renamed from: a, reason: collision with root package name */
    static final Function f143a;

    static {
        NativeLoader.classesInit0(64);
        f143a = new f();
    }

    private f() {
    }

    @Override // java.util.function.Function
    public native Object apply(Object obj);
}
