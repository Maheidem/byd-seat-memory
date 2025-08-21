package br.com.rory.electro;

import android.support.v7.app.AppCompatActivity;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class a extends AppCompatActivity {
    static {
        NativeLoader.classesInit0(205);
    }

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected native void onResume();
}
