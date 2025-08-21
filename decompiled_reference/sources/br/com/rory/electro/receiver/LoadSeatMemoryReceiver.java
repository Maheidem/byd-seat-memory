package br.com.rory.electro.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class LoadSeatMemoryReceiver extends BroadcastReceiver {

    /* renamed from: br.com.rory.electro.receiver.LoadSeatMemoryReceiver$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ Context f173a;
        final /* synthetic */ int b;

        static {
            NativeLoader.classesInit0(159);
        }

        AnonymousClass1(Context context, int i) {
            this.f173a = context;
            this.b = i;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(103);
    }

    @Override // android.content.BroadcastReceiver
    public native void onReceive(Context context, Intent intent);
}
