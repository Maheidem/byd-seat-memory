package br.com.rory.electro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class BootReceiver extends BroadcastReceiver {

    /* renamed from: br.com.rory.electro.receiver.BootReceiver$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f172a;

        static {
            NativeLoader.classesInit0(264);
        }

        AnonymousClass1(Context context) {
            this.f172a = context;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(199);
    }

    @Override // android.content.BroadcastReceiver
    public native void onReceive(Context context, Intent intent);
}
