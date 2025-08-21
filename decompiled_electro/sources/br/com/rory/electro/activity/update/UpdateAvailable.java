package br.com.rory.electro.activity.update;

import android.os.Bundle;
import android.support.design.R;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class UpdateAvailable extends AppCompatActivity {

    /* renamed from: br.com.rory.electro.activity.update.UpdateAvailable$1, reason: invalid class name */
    class AnonymousClass1 implements View.OnClickListener {
        static {
            NativeLoader.classesInit0(97);
        }

        AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public native void onClick(View view);
    }

    static {
        NativeLoader.classesInit0(R.styleable.AppCompatTheme_windowActionBar);
    }

    private static native String a();

    /* JADX INFO: Access modifiers changed from: private */
    public native void b();

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.BaseFragmentActivityGingerbread, android.app.Activity
    protected native void onCreate(Bundle bundle);

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected native void onResume();
}
