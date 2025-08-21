package a;

import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class i extends t {

    /* renamed from: a, reason: collision with root package name */
    private t f7a;

    public i(t tVar) {
        if (tVar == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.f7a = tVar;
    }

    public final i a(t tVar) {
        if (tVar == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.f7a = tVar;
        return this;
    }

    public final t a() {
        return this.f7a;
    }

    @Override // a.t
    public t clearDeadline() {
        return this.f7a.clearDeadline();
    }

    @Override // a.t
    public t clearTimeout() {
        return this.f7a.clearTimeout();
    }

    @Override // a.t
    public long deadlineNanoTime() {
        return this.f7a.deadlineNanoTime();
    }

    @Override // a.t
    public t deadlineNanoTime(long j) {
        return this.f7a.deadlineNanoTime(j);
    }

    @Override // a.t
    public boolean hasDeadline() {
        return this.f7a.hasDeadline();
    }

    @Override // a.t
    public void throwIfReached() throws InterruptedIOException {
        this.f7a.throwIfReached();
    }

    @Override // a.t
    public t timeout(long j, TimeUnit timeUnit) {
        return this.f7a.timeout(j, timeUnit);
    }

    @Override // a.t
    public long timeoutNanos() {
        return this.f7a.timeoutNanos();
    }
}
