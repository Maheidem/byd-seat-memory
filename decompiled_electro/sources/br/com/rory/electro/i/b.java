package br.com.rory.electro.i;

import com.rory.electro.NativeLoader;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

/* loaded from: classes.dex */
public class b implements SdpObserver {
    static {
        NativeLoader.classesInit0(227);
    }

    @Override // org.webrtc.SdpObserver
    public native void onCreateFailure(String str);

    @Override // org.webrtc.SdpObserver
    public native void onCreateSuccess(SessionDescription sessionDescription);

    @Override // org.webrtc.SdpObserver
    public native void onSetFailure(String str);

    @Override // org.webrtc.SdpObserver
    public native void onSetSuccess();
}
