package io.socket.client;

import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes.dex */
public abstract class AckWithTimeout implements Ack {
    private final long timeout;
    private final Timer timer = new Timer();

    public AckWithTimeout(long j) {
        this.timeout = j;
    }

    @Override // io.socket.client.Ack
    public final void call(Object... objArr) {
        this.timer.cancel();
        onSuccess(objArr);
    }

    public final void cancelTimer() {
        this.timer.cancel();
    }

    public abstract void onSuccess(Object... objArr);

    public abstract void onTimeout();

    public final void schedule(TimerTask timerTask) {
        this.timer.schedule(timerTask, this.timeout);
    }
}
