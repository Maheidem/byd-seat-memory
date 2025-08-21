package br.com.rory.electro.service;

import android.app.Activity;
import android.os.Bundle;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class ServiceLauncherActivity extends Activity {
    static {
        NativeLoader.classesInit0(237);
    }

    @Override // android.app.Activity
    protected native void onCreate(Bundle bundle);
}
