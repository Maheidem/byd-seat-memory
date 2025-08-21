package br.com.rory.electro.c.a;

import com.rory.electro.NativeLoader;
import java.util.function.ToIntFunction;

/* loaded from: classes.dex */
final /* synthetic */ class e implements ToIntFunction {

    /* renamed from: a, reason: collision with root package name */
    static final ToIntFunction f102a;

    static {
        NativeLoader.classesInit0(207);
        f102a = new e();
    }

    private e() {
    }

    @Override // java.util.function.ToIntFunction
    public native int applyAsInt(Object obj);
}
