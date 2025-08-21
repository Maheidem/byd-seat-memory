package br.com.rory.electro.b.a;

import android.content.Context;
import android.support.design.R;
import com.rory.electro.NativeLoader;
import okhttp3.MediaType;
import okhttp3.Request;

/* loaded from: classes.dex */
public class a {

    /* renamed from: br.com.rory.electro.b.a.a$1, reason: invalid class name */
    static class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f70a;

        static {
            NativeLoader.classesInit0(R.styleable.AppCompatTheme_windowActionModeOverlay);
        }

        AnonymousClass1(Context context) {
            this.f70a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(188);
    }

    public static native String a();

    public static native String a(Context context);

    public static native String a(Context context, int i);

    public static native String a(Context context, br.com.rory.electro.b.a.a.a aVar);

    public static native String a(Context context, boolean z);

    public static native String b(Context context);

    public static native String b(Context context, int i);

    private static native String b(Context context, br.com.rory.electro.b.a.a.a aVar);

    /* JADX INFO: Access modifiers changed from: private */
    public static native String b(Context context, Request request);

    public static native String c(Context context);

    /* JADX INFO: Access modifiers changed from: private */
    public static native String d();

    public static native void d(Context context);

    public static native String e(Context context);

    /* JADX INFO: Access modifiers changed from: private */
    public static native MediaType e();
}
