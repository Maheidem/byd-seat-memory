package br.com.rory.electro.c.b;

import android.content.Context;
import android.hardware.bydauto.bodywork.AbsBYDAutoBodyworkListener;
import android.hardware.bydauto.gearbox.AbsBYDAutoGearboxListener;
import com.rory.electro.NativeLoader;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private static Context f108a;
    private static final AtomicBoolean b;

    /* renamed from: br.com.rory.electro.c.b.b$1, reason: invalid class name */
    class AnonymousClass1 extends AbsBYDAutoGearboxListener {
        static {
            NativeLoader.classesInit0(17);
        }

        AnonymousClass1() {
        }

        public native void onGearboxAutoModeTypeChanged(int i);
    }

    /* renamed from: br.com.rory.electro.c.b.b$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        static {
            NativeLoader.classesInit0(18);
        }

        AnonymousClass2() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.c.b.b$3, reason: invalid class name */
    class AnonymousClass3 extends AbsBYDAutoBodyworkListener {

        /* renamed from: br.com.rory.electro.c.b.b$3$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ int f112a;
            final /* synthetic */ int b;

            static {
                NativeLoader.classesInit0(90);
            }

            AnonymousClass1(int i, int i2) {
                this.f112a = i;
                this.b = i2;
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        static {
            NativeLoader.classesInit0(19);
        }

        AnonymousClass3() {
        }

        public native void onDoorStateChanged(int i, int i2);

        public native void onPowerLevelChanged(int i);
    }

    static {
        NativeLoader.classesInit0(78);
        b = new AtomicBoolean(false);
    }

    public b(Context context) {
        a(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native void a(int i);

    private native void a(Context context);

    /* JADX INFO: Access modifiers changed from: private */
    public static native String d();

    private native void e();

    /* JADX INFO: Access modifiers changed from: private */
    public native void f();

    private native void g();
}
