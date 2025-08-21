package org.webrtc;

import org.webrtc.VideoDecoder;

/* loaded from: classes.dex */
class VideoDecoderWrapper {
    VideoDecoderWrapper() {
    }

    @CalledByNative
    static VideoDecoder.Callback createDecoderCallback(final long j) {
        return new VideoDecoder.Callback(j) { // from class: org.webrtc.VideoDecoderWrapper$$Lambda$0
            private final long arg$1;

            {
                this.arg$1 = j;
            }

            @Override // org.webrtc.VideoDecoder.Callback
            public void onDecodedFrame(VideoFrame videoFrame, Integer num, Integer num2) {
                VideoDecoderWrapper.nativeOnDecodedFrame(this.arg$1, videoFrame, num, num2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static native void nativeOnDecodedFrame(long j, VideoFrame videoFrame, Integer num, Integer num2);
}
