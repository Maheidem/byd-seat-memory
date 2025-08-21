package org.webrtc;

/* loaded from: classes.dex */
class FramerateBitrateAdjuster extends BaseBitrateAdjuster {
    private static final int DEFAULT_FRAMERATE_FPS = 30;

    FramerateBitrateAdjuster() {
    }

    @Override // org.webrtc.BaseBitrateAdjuster, org.webrtc.BitrateAdjuster
    public void setTargets(int i, double d) {
        this.targetFramerateFps = 30.0d;
        this.targetBitrateBps = (int) ((i * 30) / d);
    }
}
