package org.webrtc;

import androidx.annotation.Nullable;
import org.webrtc.VideoProcessor;

/* loaded from: classes.dex */
public class VideoSource extends MediaSource {
    private final CapturerObserver capturerObserver;
    private boolean isCapturerRunning;
    private final NativeAndroidVideoTrackSource nativeAndroidVideoTrackSource;

    @Nullable
    private VideoProcessor videoProcessor;
    private final Object videoProcessorLock;

    public static class AspectRatio {
        public static final AspectRatio UNDEFINED = new AspectRatio(0, 0);
        public final int height;
        public final int width;

        public AspectRatio(int i, int i2) {
            this.width = i;
            this.height = i2;
        }
    }

    public VideoSource(long j) {
        super(j);
        this.videoProcessorLock = new Object();
        this.capturerObserver = new CapturerObserver() { // from class: org.webrtc.VideoSource.1
            @Override // org.webrtc.CapturerObserver
            public void onCapturerStarted(boolean z) {
                VideoSource.this.nativeAndroidVideoTrackSource.setState(z);
                synchronized (VideoSource.this.videoProcessorLock) {
                    VideoSource.this.isCapturerRunning = z;
                    if (VideoSource.this.videoProcessor != null) {
                        VideoSource.this.videoProcessor.onCapturerStarted(z);
                    }
                }
            }

            @Override // org.webrtc.CapturerObserver
            public void onCapturerStopped() {
                VideoSource.this.nativeAndroidVideoTrackSource.setState(false);
                synchronized (VideoSource.this.videoProcessorLock) {
                    VideoSource.this.isCapturerRunning = false;
                    if (VideoSource.this.videoProcessor != null) {
                        VideoSource.this.videoProcessor.onCapturerStopped();
                    }
                }
            }

            @Override // org.webrtc.CapturerObserver
            public void onFrameCaptured(VideoFrame videoFrame) {
                VideoProcessor.FrameAdaptationParameters frameAdaptationParametersAdaptFrame = VideoSource.this.nativeAndroidVideoTrackSource.adaptFrame(videoFrame);
                synchronized (VideoSource.this.videoProcessorLock) {
                    if (VideoSource.this.videoProcessor != null) {
                        VideoSource.this.videoProcessor.onFrameCaptured(videoFrame, frameAdaptationParametersAdaptFrame);
                        return;
                    }
                    VideoFrame videoFrameApplyFrameAdaptationParameters = VideoProcessor.applyFrameAdaptationParameters(videoFrame, frameAdaptationParametersAdaptFrame);
                    if (videoFrameApplyFrameAdaptationParameters != null) {
                        VideoSource.this.nativeAndroidVideoTrackSource.onFrameCaptured(videoFrameApplyFrameAdaptationParameters);
                        videoFrameApplyFrameAdaptationParameters.release();
                    }
                }
            }
        };
        this.nativeAndroidVideoTrackSource = new NativeAndroidVideoTrackSource(j);
    }

    public void adaptOutputFormat(int i, int i2, int i3) {
        int iMax = Math.max(i, i2);
        int iMin = Math.min(i, i2);
        adaptOutputFormat(iMax, iMin, iMin, iMax, i3);
    }

    public void adaptOutputFormat(int i, int i2, int i3, int i4, int i5) {
        adaptOutputFormat(new AspectRatio(i, i2), Integer.valueOf(i * i2), new AspectRatio(i3, i4), Integer.valueOf(i3 * i4), Integer.valueOf(i5));
    }

    public void adaptOutputFormat(AspectRatio aspectRatio, @Nullable Integer num, AspectRatio aspectRatio2, @Nullable Integer num2, @Nullable Integer num3) {
        this.nativeAndroidVideoTrackSource.adaptOutputFormat(aspectRatio, num, aspectRatio2, num2, num3);
    }

    @Override // org.webrtc.MediaSource
    public void dispose() {
        setVideoProcessor(null);
        super.dispose();
    }

    public CapturerObserver getCapturerObserver() {
        return this.capturerObserver;
    }

    long getNativeVideoTrackSource() {
        return getNativeMediaSource();
    }

    final /* synthetic */ void lambda$setVideoProcessor$0$VideoSource(VideoFrame videoFrame) {
        this.nativeAndroidVideoTrackSource.onFrameCaptured(videoFrame);
    }

    final /* synthetic */ void lambda$setVideoProcessor$1$VideoSource(final VideoFrame videoFrame) {
        runWithReference(new Runnable(this, videoFrame) { // from class: org.webrtc.VideoSource$$Lambda$1
            private final VideoSource arg$1;
            private final VideoFrame arg$2;

            {
                this.arg$1 = this;
                this.arg$2 = videoFrame;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.arg$1.lambda$setVideoProcessor$0$VideoSource(this.arg$2);
            }
        });
    }

    public void setIsScreencast(boolean z) {
        this.nativeAndroidVideoTrackSource.setIsScreencast(z);
    }

    public void setVideoProcessor(@Nullable VideoProcessor videoProcessor) {
        synchronized (this.videoProcessorLock) {
            if (this.videoProcessor != null) {
                this.videoProcessor.setSink(null);
                if (this.isCapturerRunning) {
                    this.videoProcessor.onCapturerStopped();
                }
            }
            this.videoProcessor = videoProcessor;
            if (videoProcessor != null) {
                videoProcessor.setSink(new VideoSink(this) { // from class: org.webrtc.VideoSource$$Lambda$0
                    private final VideoSource arg$1;

                    {
                        this.arg$1 = this;
                    }

                    @Override // org.webrtc.VideoSink
                    public void onFrame(VideoFrame videoFrame) {
                        this.arg$1.lambda$setVideoProcessor$1$VideoSource(videoFrame);
                    }
                });
                if (this.isCapturerRunning) {
                    videoProcessor.onCapturerStarted(true);
                }
            }
        }
    }
}
