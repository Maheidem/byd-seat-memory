package org.webrtc;

import org.webrtc.VideoFrame;

/* loaded from: classes.dex */
final /* synthetic */ class JavaI420Buffer$$Lambda$1 implements Runnable {
    private final VideoFrame.I420Buffer arg$1;

    private JavaI420Buffer$$Lambda$1(VideoFrame.I420Buffer i420Buffer) {
        this.arg$1 = i420Buffer;
    }

    static Runnable get$Lambda(VideoFrame.I420Buffer i420Buffer) {
        return new JavaI420Buffer$$Lambda$1(i420Buffer);
    }

    @Override // java.lang.Runnable
    public void run() {
        this.arg$1.release();
    }
}
