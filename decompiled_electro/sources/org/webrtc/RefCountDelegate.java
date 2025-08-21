package org.webrtc;

import androidx.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
class RefCountDelegate implements RefCounted {
    private final AtomicInteger refCount = new AtomicInteger(1);

    @Nullable
    private final Runnable releaseCallback;

    public RefCountDelegate(@Nullable Runnable runnable) {
        this.releaseCallback = runnable;
    }

    @Override // org.webrtc.RefCounted
    public void release() {
        int iDecrementAndGet = this.refCount.decrementAndGet();
        if (iDecrementAndGet < 0) {
            throw new IllegalStateException("release() called on an object with refcount < 1");
        }
        if (iDecrementAndGet != 0 || this.releaseCallback == null) {
            return;
        }
        this.releaseCallback.run();
    }

    @Override // org.webrtc.RefCounted
    public void retain() {
        if (this.refCount.incrementAndGet() < 2) {
            throw new IllegalStateException("retain() called on an object with refcount < 1");
        }
    }

    boolean safeRetain() {
        int i;
        do {
            i = this.refCount.get();
            if (i == 0) {
                return false;
            }
        } while (!this.refCount.weakCompareAndSet(i, i + 1));
        return true;
    }
}
