package org.webrtc;

import androidx.annotation.Nullable;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes.dex */
public class RtpReceiver {

    @Nullable
    private MediaStreamTrack cachedTrack;
    private long nativeObserver;
    private long nativeRtpReceiver;

    public interface Observer {
        @CalledByNative("Observer")
        void onFirstPacketReceived(MediaStreamTrack.MediaType mediaType);
    }

    @CalledByNative
    public RtpReceiver(long j) {
        this.nativeRtpReceiver = j;
        this.cachedTrack = MediaStreamTrack.createMediaStreamTrack(nativeGetTrack(j));
    }

    private void checkRtpReceiverExists() {
        if (this.nativeRtpReceiver == 0) {
            throw new IllegalStateException("RtpReceiver has been disposed.");
        }
    }

    private static native String nativeGetId(long j);

    private static native RtpParameters nativeGetParameters(long j);

    private static native long nativeGetTrack(long j);

    private static native void nativeSetFrameDecryptor(long j, long j2);

    private static native long nativeSetObserver(long j, Observer observer);

    private static native void nativeUnsetObserver(long j, long j2);

    public void SetObserver(Observer observer) {
        checkRtpReceiverExists();
        if (this.nativeObserver != 0) {
            nativeUnsetObserver(this.nativeRtpReceiver, this.nativeObserver);
        }
        this.nativeObserver = nativeSetObserver(this.nativeRtpReceiver, observer);
    }

    @CalledByNative
    public void dispose() {
        checkRtpReceiverExists();
        this.cachedTrack.dispose();
        if (this.nativeObserver != 0) {
            nativeUnsetObserver(this.nativeRtpReceiver, this.nativeObserver);
            this.nativeObserver = 0L;
        }
        JniCommon.nativeReleaseRef(this.nativeRtpReceiver);
        this.nativeRtpReceiver = 0L;
    }

    public RtpParameters getParameters() {
        checkRtpReceiverExists();
        return nativeGetParameters(this.nativeRtpReceiver);
    }

    public String id() {
        checkRtpReceiverExists();
        return nativeGetId(this.nativeRtpReceiver);
    }

    public void setFrameDecryptor(FrameDecryptor frameDecryptor) {
        checkRtpReceiverExists();
        nativeSetFrameDecryptor(this.nativeRtpReceiver, frameDecryptor.getNativeFrameDecryptor());
    }

    @Nullable
    public MediaStreamTrack track() {
        return this.cachedTrack;
    }
}
