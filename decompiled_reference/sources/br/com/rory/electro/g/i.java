package br.com.rory.electro.g;

import android.content.Context;
import com.rory.electro.NativeLoader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static final ScheduledExecutorService f145a;

    static {
        NativeLoader.classesInit0(3);
        f145a = Executors.newScheduledThreadPool(1);
    }

    public static native void a(Context context, br.com.rory.electro.common.a<String> aVar);

    public static native void a(Context context, String str);
}
