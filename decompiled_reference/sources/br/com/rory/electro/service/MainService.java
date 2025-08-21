package br.com.rory.electro.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import br.com.rory.electro.f.e;
import br.com.rory.electro.g.l;
import com.rory.electro.NativeLoader;
import org.greenrobot.eventbus.m;

/* loaded from: classes.dex */
public class MainService extends Service {
    private static boolean c;
    private static boolean d;
    private static l e;

    /* renamed from: a, reason: collision with root package name */
    private PowerManager.WakeLock f176a;
    private WifiManager.WifiLock b;
    private boolean f = false;

    /* renamed from: br.com.rory.electro.service.MainService$1, reason: invalid class name */
    class AnonymousClass1 implements br.com.rory.electro.common.a<String> {
        AnonymousClass1() {
        }
    }

    /* renamed from: br.com.rory.electro.service.MainService$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ int f178a;

        static {
            NativeLoader.classesInit0(91);
        }

        AnonymousClass2(int i) {
            this.f178a = i;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(161);
    }

    public static native String a();

    public static native void a(Context context);

    public static native boolean b();

    public static native boolean c();

    private native Notification d();

    private native void e();

    private native void f();

    @Override // android.app.Service
    @Nullable
    public native IBinder onBind(Intent intent);

    @Override // android.app.Service
    public native void onCreate();

    @Override // android.app.Service
    public native void onDestroy();

    @m
    public native void onHBSInit(br.com.rory.electro.f.b bVar);

    @m
    public native void onProvisionKeysCreated(br.com.rory.electro.f.c cVar);

    @m
    public native void onSSBGSInit(e eVar);

    @Override // android.app.Service
    public native int onStartCommand(Intent intent, int i, int i2);
}
