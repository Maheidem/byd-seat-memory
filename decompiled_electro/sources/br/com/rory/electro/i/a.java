package br.com.rory.electro.i;

import com.rory.electro.NativeLoader;
import org.webrtc.CandidatePairChangeEvent;
import org.webrtc.DataChannel;
import org.webrtc.IceCandidate;
import org.webrtc.MediaStream;
import org.webrtc.PeerConnection;
import org.webrtc.RtpReceiver;
import org.webrtc.RtpTransceiver;

/* loaded from: classes.dex */
public class a implements PeerConnection.Observer {
    static {
        NativeLoader.classesInit0(226);
    }

    @Override // org.webrtc.PeerConnection.Observer
    public native void onAddStream(MediaStream mediaStream);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreamArr);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onConnectionChange(PeerConnection.PeerConnectionState peerConnectionState);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onDataChannel(DataChannel dataChannel);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onIceCandidate(IceCandidate iceCandidate);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onIceCandidatesRemoved(IceCandidate[] iceCandidateArr);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onIceConnectionReceivingChange(boolean z);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onRemoveStream(MediaStream mediaStream);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onRemoveTrack(RtpReceiver rtpReceiver);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onRenegotiationNeeded();

    @Override // org.webrtc.PeerConnection.Observer
    public native void onSelectedCandidatePairChanged(CandidatePairChangeEvent candidatePairChangeEvent);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onSignalingChange(PeerConnection.SignalingState signalingState);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onStandardizedIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState);

    @Override // org.webrtc.PeerConnection.Observer
    public native void onTrack(RtpTransceiver rtpTransceiver);
}
