package org.webrtc;

import androidx.annotation.Nullable;
import java.util.List;

/* loaded from: classes.dex */
public class RtpSender {

    @Nullable
    private MediaStreamTrack cachedTrack;

    @Nullable
    private final DtmfSender dtmfSender;
    private long nativeRtpSender;
    private boolean ownsTrack = true;

    @CalledByNative
    public RtpSender(long j) {
        this.nativeRtpSender = j;
        this.cachedTrack = MediaStreamTrack.createMediaStreamTrack(nativeGetTrack(j));
        long jNativeGetDtmfSender = nativeGetDtmfSender(j);
        this.dtmfSender = jNativeGetDtmfSender != 0 ? new DtmfSender(jNativeGetDtmfSender) : null;
    }

    private void checkRtpSenderExists() {
        if (this.nativeRtpSender == 0) {
            throw new IllegalStateException("RtpSender has been disposed.");
        }
    }

    private static native long nativeGetDtmfSender(long j);

    private static native String nativeGetId(long j);

    private static native RtpParameters nativeGetParameters(long j);

    private static native List<String> nativeGetStreams(long j);

    private static native long nativeGetTrack(long j);

    private static native void nativeSetFrameEncryptor(long j, long j2);

    private static native boolean nativeSetParameters(long j, RtpParameters rtpParameters);

    private static native void nativeSetStreams(long j, List<String> list);

    private static native boolean nativeSetTrack(long j, long j2);

    public void dispose() {
        checkRtpSenderExists();
        if (this.dtmfSender != null) {
            this.dtmfSender.dispose();
        }
        if (this.cachedTrack != null && this.ownsTrack) {
            this.cachedTrack.dispose();
        }
        JniCommon.nativeReleaseRef(this.nativeRtpSender);
        this.nativeRtpSender = 0L;
    }

    @Nullable
    public DtmfSender dtmf() {
        return this.dtmfSender;
    }

    long getNativeRtpSender() {
        checkRtpSenderExists();
        return this.nativeRtpSender;
    }

    public RtpParameters getParameters() {
        checkRtpSenderExists();
        return nativeGetParameters(this.nativeRtpSender);
    }

    public List<String> getStreams() {
        checkRtpSenderExists();
        return nativeGetStreams(this.nativeRtpSender);
    }

    public String id() {
        checkRtpSenderExists();
        return nativeGetId(this.nativeRtpSender);
    }

    public void setFrameEncryptor(FrameEncryptor frameEncryptor) {
        checkRtpSenderExists();
        nativeSetFrameEncryptor(this.nativeRtpSender, frameEncryptor.getNativeFrameEncryptor());
    }

    public boolean setParameters(RtpParameters rtpParameters) {
        checkRtpSenderExists();
        return nativeSetParameters(this.nativeRtpSender, rtpParameters);
    }

    public void setStreams(List<String> list) {
        checkRtpSenderExists();
        nativeSetStreams(this.nativeRtpSender, list);
    }

    public boolean setTrack(@Nullable MediaStreamTrack mediaStreamTrack, boolean z) {
        checkRtpSenderExists();
        if (!nativeSetTrack(this.nativeRtpSender, mediaStreamTrack == null ? 0L : mediaStreamTrack.getNativeMediaStreamTrack())) {
            return false;
        }
        if (this.cachedTrack != null && this.ownsTrack) {
            this.cachedTrack.dispose();
        }
        this.cachedTrack = mediaStreamTrack;
        this.ownsTrack = z;
        return true;
    }

    @Nullable
    public MediaStreamTrack track() {
        return this.cachedTrack;
    }
}
