package br.com.rory.electro.service;

import android.content.Context;
import android.support.design.R;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    Context f185a;

    static {
        NativeLoader.classesInit0(R.styleable.AppCompatTheme_windowFixedWidthMajor);
    }

    public a(Context context) {
        this.f185a = context;
    }

    private static native String a();

    public static native void a(Context context);

    @Override // java.lang.Runnable
    public native void run();
}
