package org.webrtc;

import java.util.concurrent.CountDownLatch;

/* loaded from: classes.dex */
final /* synthetic */ class SurfaceEglRenderer$$Lambda$0 implements Runnable {
    private final CountDownLatch arg$1;

    private SurfaceEglRenderer$$Lambda$0(CountDownLatch countDownLatch) {
        this.arg$1 = countDownLatch;
    }

    static Runnable get$Lambda(CountDownLatch countDownLatch) {
        return new SurfaceEglRenderer$$Lambda$0(countDownLatch);
    }

    @Override // java.lang.Runnable
    public void run() {
        this.arg$1.countDown();
    }
}
