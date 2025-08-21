package br.com.rory.electro;

import android.app.Application;
import android.content.Context;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class MyApplication extends Application {
    static {
        NativeLoader.classesInit0(100);
    }

    @Override // android.content.ContextWrapper
    protected native void attachBaseContext(Context context);
}
