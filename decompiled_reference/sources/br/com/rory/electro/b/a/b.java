package br.com.rory.electro.b.a;

import android.content.Context;
import com.rory.electro.NativeLoader;
import okhttp3.Interceptor;
import okhttp3.Response;

/* loaded from: classes.dex */
public class b implements Interceptor {

    /* renamed from: a, reason: collision with root package name */
    private Context f72a;

    static {
        NativeLoader.classesInit0(189);
    }

    public b(Context context) {
        this.f72a = context;
    }

    @Override // okhttp3.Interceptor
    public native Response intercept(Interceptor.Chain chain);
}
