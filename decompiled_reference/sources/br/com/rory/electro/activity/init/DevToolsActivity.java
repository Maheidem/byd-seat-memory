package br.com.rory.electro.activity.init;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class DevToolsActivity extends AppCompatActivity {

    /* renamed from: br.com.rory.electro.activity.init.DevToolsActivity$1, reason: invalid class name */
    class AnonymousClass1 implements View.OnClickListener {
        static {
            NativeLoader.classesInit0(124);
        }

        AnonymousClass1() {
        }

        @Override // android.view.View.OnClickListener
        public native void onClick(View view);
    }

    static {
        NativeLoader.classesInit0(182);
    }

    public static native boolean a(Context context);

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.BaseFragmentActivityGingerbread, android.app.Activity
    public native void onCreate(Bundle bundle);

    @Override // android.support.v4.app.FragmentActivity, android.app.Activity
    protected native void onResume();
}
