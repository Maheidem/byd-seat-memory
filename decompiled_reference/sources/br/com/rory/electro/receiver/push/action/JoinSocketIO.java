package br.com.rory.electro.receiver.push.action;

import android.content.Context;
import br.com.rory.electro.socket.io.d;
import com.rory.electro.NativeLoader;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.webrtc.PeerConnection;

/* loaded from: classes.dex */
public class JoinSocketIO implements PushAction {
    public static final Map<String, PeerConnection> rtcRooms;
    public static final Map<String, d> socketRooms;

    /* renamed from: br.com.rory.electro.receiver.push.action.JoinSocketIO$1, reason: invalid class name */
    class AnonymousClass1 implements Runnable {
        final /* synthetic */ Context val$context;
        final /* synthetic */ String val$room;

        static {
            NativeLoader.classesInit0(93);
        }

        AnonymousClass1(Context context, String str) {
            this.val$context = context;
            this.val$room = str;
        }

        @Override // java.lang.Runnable
        public native void run();
    }

    static {
        NativeLoader.classesInit0(104);
        socketRooms = new HashMap();
        rtcRooms = new HashMap();
    }

    private native synchronized boolean validateRTCConnections(String str);

    private native synchronized boolean validateSocketConnections(String str);

    @Override // br.com.rory.electro.receiver.push.action.PushAction
    public native void execute(Context context, JSONObject jSONObject);
}
