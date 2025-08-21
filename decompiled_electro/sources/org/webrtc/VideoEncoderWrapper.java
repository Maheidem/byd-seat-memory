package org.webrtc;

import androidx.annotation.Nullable;
import org.webrtc.VideoEncoder;

/* loaded from: classes.dex */
class VideoEncoderWrapper {
    VideoEncoderWrapper() {
    }

    @CalledByNative
    static VideoEncoder.Callback createEncoderCallback(final long j) {
        return new VideoEncoder.Callback(j) { // from class: org.webrtc.VideoEncoderWrapper$$Lambda$0
            private final long arg$1;

            {
                this.arg$1 = j;
            }

            @Override // org.webrtc.VideoEncoder.Callback
            public void onEncodedFrame(EncodedImage encodedImage, VideoEncoder.CodecSpecificInfo codecSpecificInfo) {
                VideoEncoderWrapper.nativeOnEncodedFrame(this.arg$1, encodedImage);
            }
        };
    }

    @Nullable
    @CalledByNative
    static Integer getScalingSettingsHigh(VideoEncoder.ScalingSettings scalingSettings) {
        return scalingSettings.high;
    }

    @Nullable
    @CalledByNative
    static Integer getScalingSettingsLow(VideoEncoder.ScalingSettings scalingSettings) {
        return scalingSettings.low;
    }

    @CalledByNative
    static boolean getScalingSettingsOn(VideoEncoder.ScalingSettings scalingSettings) {
        return scalingSettings.on;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeOnEncodedFrame(long j, EncodedImage encodedImage);
}
