package br.com.rory.electro.i;

import android.content.Context;
import br.com.rory.electro.socket.io.d;
import com.rory.electro.NativeLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import org.json.JSONArray;
import org.webrtc.DataChannel;
import org.webrtc.EglBase;
import org.webrtc.IceCandidate;
import org.webrtc.MediaConstraints;
import org.webrtc.PeerConnection;
import org.webrtc.PeerConnectionFactory;
import org.webrtc.SessionDescription;
import org.webrtc.SurfaceTextureHelper;
import org.webrtc.VideoSource;
import org.webrtc.VideoTrack;

/* loaded from: classes.dex */
public class c implements br.com.rory.electro.c.a.a.b {

    /* renamed from: a, reason: collision with root package name */
    private PeerConnectionFactory f164a;
    private PeerConnection b;
    private MediaConstraints c;
    private DataChannel d;
    private EglBase e;
    private SurfaceTextureHelper f;
    private VideoSource g;
    private VideoTrack h;
    private ExecutorService i;
    private br.com.rory.electro.c.a.a.a j;
    private Context k;
    private d l;
    private String m;
    private boolean n = false;
    private int o = 1;
    private boolean p = false;

    /* renamed from: br.com.rory.electro.i.c$1, reason: invalid class name */
    class AnonymousClass1 implements ThreadFactory {
        static {
            NativeLoader.classesInit0(139);
        }

        AnonymousClass1() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public native Thread newThread(Runnable runnable);
    }

    /* renamed from: br.com.rory.electro.i.c$2, reason: invalid class name */
    class AnonymousClass2 extends a {
        static {
            NativeLoader.classesInit0(137);
        }

        AnonymousClass2() {
        }

        @Override // br.com.rory.electro.i.a, org.webrtc.PeerConnection.Observer
        public native void onConnectionChange(PeerConnection.PeerConnectionState peerConnectionState);

        @Override // br.com.rory.electro.i.a, org.webrtc.PeerConnection.Observer
        public native void onIceCandidate(IceCandidate iceCandidate);

        @Override // br.com.rory.electro.i.a, org.webrtc.PeerConnection.Observer
        public native void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState);
    }

    /* renamed from: br.com.rory.electro.i.c$3, reason: invalid class name */
    class AnonymousClass3 extends b {
        static {
            NativeLoader.classesInit0(138);
        }

        AnonymousClass3() {
        }

        @Override // br.com.rory.electro.i.b, org.webrtc.SdpObserver
        public native void onCreateSuccess(SessionDescription sessionDescription);
    }

    /* renamed from: br.com.rory.electro.i.c$4, reason: invalid class name */
    class AnonymousClass4 extends b {
        static {
            NativeLoader.classesInit0(134);
        }

        AnonymousClass4() {
        }

        @Override // br.com.rory.electro.i.b, org.webrtc.SdpObserver
        public native void onCreateSuccess(SessionDescription sessionDescription);
    }

    /* renamed from: br.com.rory.electro.i.c$5, reason: invalid class name */
    class AnonymousClass5 implements DataChannel.Observer {
        static {
            NativeLoader.classesInit0(136);
        }

        AnonymousClass5() {
        }

        @Override // org.webrtc.DataChannel.Observer
        public native void onBufferedAmountChange(long j);

        @Override // org.webrtc.DataChannel.Observer
        public native void onMessage(DataChannel.Buffer buffer);

        @Override // org.webrtc.DataChannel.Observer
        public native void onStateChange();
    }

    /* renamed from: br.com.rory.electro.i.c$6, reason: invalid class name */
    class AnonymousClass6 implements Runnable {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ byte[] f170a;
        final /* synthetic */ int b;
        final /* synthetic */ int c;
        final /* synthetic */ long d;

        static {
            NativeLoader.classesInit0(132);
        }

        AnonymousClass6(byte[] bArr, int i, int i2, long j) {
            this.f170a = bArr;
            this.b = i;
            this.c = i2;
            this.d = j;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    /* renamed from: br.com.rory.electro.i.c$7, reason: invalid class name */
    class AnonymousClass7 implements Runnable {
        static {
            NativeLoader.classesInit0(133);
        }

        AnonymousClass7() {
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(229);
    }

    public c(Context context, d dVar, String str) {
        this.k = context;
        this.l = dVar;
        this.m = str;
        this.j = new br.com.rory.electro.c.a.a.a(context, str);
        this.j.a(this);
        a(br.com.rory.electro.g.a.a());
    }

    private native void a(EglBase eglBase);

    /* JADX INFO: Access modifiers changed from: private */
    public native void b(IceCandidate iceCandidate);

    /* JADX INFO: Access modifiers changed from: private */
    public native void b(SessionDescription sessionDescription);

    /* JADX INFO: Access modifiers changed from: private */
    public native void b(byte[] bArr, int i, int i2, long j);

    /* JADX INFO: Access modifiers changed from: private */
    public static native String h();

    private native void i();

    private native void j();

    public native void a();

    public native void a(int i);

    @Override // br.com.rory.electro.c.a.a.b
    public native void a(String str);

    public native void a(JSONArray jSONArray);

    public native void a(IceCandidate iceCandidate);

    public native void a(SessionDescription sessionDescription);

    @Override // br.com.rory.electro.c.a.a.b
    public native void a(byte[] bArr, int i, int i2, long j);

    public native void b();

    public native void b(String str);

    public native boolean c();

    public native void d();

    public native void e();

    public native void f();
}
