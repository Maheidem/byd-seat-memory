package br.com.rory.electro.socket.io;

import android.content.Context;
import com.rory.electro.NativeLoader;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private Socket f196a;
    private Context b;
    private String c;
    private int d;
    private br.com.rory.electro.i.c e;
    private volatile boolean f = true;

    /* renamed from: br.com.rory.electro.socket.io.d$1, reason: invalid class name */
    class AnonymousClass1 implements Emitter.Listener {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f197a;

        static {
            NativeLoader.classesInit0(165);
        }

        AnonymousClass1(String str) {
            this.f197a = str;
        }

        @Override // io.socket.emitter.Emitter.Listener
        public native void call(Object... objArr);
    }

    /* renamed from: br.com.rory.electro.socket.io.d$2, reason: invalid class name */
    class AnonymousClass2 implements Emitter.Listener {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ d f198a;
        final /* synthetic */ String b;

        static {
            NativeLoader.classesInit0(173);
        }

        AnonymousClass2(d dVar, String str) {
            this.f198a = dVar;
            this.b = str;
        }

        @Override // io.socket.emitter.Emitter.Listener
        public native void call(Object... objArr);
    }

    /* renamed from: br.com.rory.electro.socket.io.d$3, reason: invalid class name */
    class AnonymousClass3 implements Emitter.Listener {
        static {
            NativeLoader.classesInit0(174);
        }

        AnonymousClass3() {
        }

        @Override // io.socket.emitter.Emitter.Listener
        public native void call(Object... objArr);
    }

    /* renamed from: br.com.rory.electro.socket.io.d$4, reason: invalid class name */
    class AnonymousClass4 implements Emitter.Listener {
        static {
            NativeLoader.classesInit0(168);
        }

        AnonymousClass4() {
        }

        @Override // io.socket.emitter.Emitter.Listener
        public native void call(Object... objArr);
    }

    /* renamed from: br.com.rory.electro.socket.io.d$5, reason: invalid class name */
    class AnonymousClass5 implements Emitter.Listener {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ String f201a;

        static {
            NativeLoader.classesInit0(170);
        }

        AnonymousClass5(String str) {
            this.f201a = str;
        }

        @Override // io.socket.emitter.Emitter.Listener
        public native void call(Object... objArr);
    }

    static {
        NativeLoader.classesInit0(86);
    }

    public d(Context context) {
        this.b = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native void a(String str, Object obj);

    public native void a(String str);

    public native void a(String str, JSONObject jSONObject);

    public native boolean a();

    public native boolean a(JSONObject jSONObject);

    public native br.com.rory.electro.i.c b();

    public native void b(JSONObject jSONObject);

    public native void c();

    public native void c(JSONObject jSONObject);
}
