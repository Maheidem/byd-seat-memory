package org.webrtc;

import androidx.annotation.Nullable;

/* loaded from: classes.dex */
public interface VideoDecoderFactory {
    @Nullable
    @CalledByNative
    VideoDecoder createDecoder(VideoCodecInfo videoCodecInfo);

    @CalledByNative
    default VideoCodecInfo[] getSupportedCodecs() {
        return new VideoCodecInfo[0];
    }
}
