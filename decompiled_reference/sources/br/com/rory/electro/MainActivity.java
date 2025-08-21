package br.com.rory.electro;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import br.com.rory.electro.d.a.a.a;
import br.com.rory.electro.f.d;
import com.rory.electro.NativeLoader;
import java.util.List;
import org.greenrobot.eventbus.m;

/* loaded from: classes.dex */
public class MainActivity extends a {

    /* renamed from: a, reason: collision with root package name */
    br.com.rory.electro.d.a.a.a f20a;
    List<br.com.rory.electro.d.a.a.b> b;
    TextView c;
    ProgressBar d;
    RecyclerView e;
    LinearLayout f;
    LinearLayout g;
    TextView h;

    /* renamed from: br.com.rory.electro.MainActivity$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Button f21a;
        final /* synthetic */ ImageView b;
        final /* synthetic */ TextView c;
        final /* synthetic */ ProgressBar d;

        /* renamed from: br.com.rory.electro.MainActivity$1$1, reason: invalid class name and collision with other inner class name */
        class RunnableC00031 implements Runnable {
            static {
                NativeLoader.classesInit0(40);
            }

            RunnableC00031() {
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        /* renamed from: br.com.rory.electro.MainActivity$1$2, reason: invalid class name */
        class AnonymousClass2 implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ Bitmap f23a;
            final /* synthetic */ String b;

            static {
                NativeLoader.classesInit0(44);
            }

            AnonymousClass2(Bitmap bitmap, String str) {
                this.f23a = bitmap;
                this.b = str;
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        /* renamed from: br.com.rory.electro.MainActivity$1$3, reason: invalid class name */
        class AnonymousClass3 implements Runnable {
            static {
                NativeLoader.classesInit0(45);
            }

            AnonymousClass3() {
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        /* renamed from: br.com.rory.electro.MainActivity$1$4, reason: invalid class name */
        class AnonymousClass4 implements Runnable {
            static {
                NativeLoader.classesInit0(47);
            }

            AnonymousClass4() {
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        static {
            NativeLoader.classesInit0(15);
        }

        AnonymousClass1(Button button, ImageView imageView, TextView textView, ProgressBar progressBar) {
            this.f21a = button;
            this.b = imageView;
            this.c = textView;
            this.d = progressBar;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.MainActivity$2, reason: invalid class name */
    class AnonymousClass2 implements View.OnClickListener {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Runnable f26a;

        static {
            NativeLoader.classesInit0(16);
        }

        AnonymousClass2(Runnable runnable) {
            this.f26a = runnable;
        }

        @Override // android.view.View.OnClickListener
        public native void onClick(View view);
    }

    /* renamed from: br.com.rory.electro.MainActivity$3, reason: invalid class name */
    class AnonymousClass3 implements View.OnClickListener {
        static {
            NativeLoader.classesInit0(28);
        }

        AnonymousClass3() {
        }

        @Override // android.view.View.OnClickListener
        public native void onClick(View view);
    }

    /* renamed from: br.com.rory.electro.MainActivity$4, reason: invalid class name */
    class AnonymousClass4 implements a.InterfaceC0009a {

        /* renamed from: br.com.rory.electro.MainActivity$4$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {

            /* renamed from: a, reason: collision with root package name */
            final /* synthetic */ int f29a;

            /* renamed from: br.com.rory.electro.MainActivity$4$1$1, reason: invalid class name and collision with other inner class name */
            class RunnableC00041 implements Runnable {
                static {
                    NativeLoader.classesInit0(135);
                }

                RunnableC00041() {
                }

                @Override // java.lang.Runnable
                public native void run();
            }

            static {
                NativeLoader.classesInit0(154);
            }

            AnonymousClass1(int i) {
                this.f29a = i;
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        static {
            NativeLoader.classesInit0(29);
        }

        AnonymousClass4() {
        }

        @Override // br.com.rory.electro.d.a.a.a.InterfaceC0009a
        public native void a(int i);
    }

    /* renamed from: br.com.rory.electro.MainActivity$5, reason: invalid class name */
    class AnonymousClass5 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ MainActivity f31a;

        /* renamed from: br.com.rory.electro.MainActivity$5$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            static {
                NativeLoader.classesInit0(186);
            }

            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        static {
            NativeLoader.classesInit0(26);
        }

        AnonymousClass5(MainActivity mainActivity) {
            this.f31a = mainActivity;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.MainActivity$6, reason: invalid class name */
    class AnonymousClass6 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ boolean f33a;

        static {
            NativeLoader.classesInit0(27);
        }

        AnonymousClass6(boolean z) {
            this.f33a = z;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.MainActivity$7, reason: invalid class name */
    class AnonymousClass7 implements Runnable {

        /* renamed from: br.com.rory.electro.MainActivity$7$1, reason: invalid class name */
        class AnonymousClass1 implements Runnable {
            static {
                NativeLoader.classesInit0(216);
            }

            AnonymousClass1() {
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        /* renamed from: br.com.rory.electro.MainActivity$7$2, reason: invalid class name */
        class AnonymousClass2 implements Runnable {
            static {
                NativeLoader.classesInit0(217);
            }

            AnonymousClass2() {
            }

            @Override // java.lang.Runnable
            public native void run();
        }

        static {
            NativeLoader.classesInit0(24);
        }

        AnonymousClass7() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(214);
    }

    private native void a();

    public static native void a(Activity activity);

    /* JADX INFO: Access modifiers changed from: private */
    public native void a(boolean z);

    private native void b();

    private native void c();

    private native void d();

    /* JADX INFO: Access modifiers changed from: private */
    public native void e();

    private native void f();

    @m
    public native void onCheckIfInitializing(br.com.rory.electro.f.a aVar);

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.support.v4.app.BaseFragmentActivityGingerbread, android.app.Activity
    protected native void onCreate(Bundle bundle);

    @Override // android.support.v7.app.AppCompatActivity, android.support.v4.app.FragmentActivity, android.app.Activity
    protected native void onDestroy();

    @m
    public native void onRefreshUserListEvent(d dVar);

    @Override // br.com.rory.electro.a, android.support.v4.app.FragmentActivity, android.app.Activity
    protected native void onResume();
}
