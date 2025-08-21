package a;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class a extends t {
    private static final long IDLE_TIMEOUT_MILLIS = TimeUnit.SECONDS.toMillis(60);
    private static final long IDLE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(IDLE_TIMEOUT_MILLIS);
    private static final int TIMEOUT_WRITE_SIZE = 65536;

    @Nullable
    static a head;
    private boolean inQueue;

    @Nullable
    private a next;
    private long timeoutAt;

    /* renamed from: a.a$a, reason: collision with other inner class name */
    private static final class C0000a extends Thread {
        C0000a() {
            super("Okio Watchdog");
            setDaemon(true);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0015, code lost:
        
            r0.timedOut();
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() {
            /*
                r2 = this;
            L0:
                java.lang.Class<a.a> r2 = a.a.class
                monitor-enter(r2)     // Catch: java.lang.InterruptedException -> L0
                a.a r0 = a.a.awaitTimeout()     // Catch: java.lang.Throwable -> L19
                if (r0 != 0) goto Lb
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L19
                goto L0
            Lb:
                a.a r1 = a.a.head     // Catch: java.lang.Throwable -> L19
                if (r0 != r1) goto L14
                r0 = 0
                a.a.head = r0     // Catch: java.lang.Throwable -> L19
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L19
                return
            L14:
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L19
                r0.timedOut()     // Catch: java.lang.InterruptedException -> L0
                goto L0
            L19:
                r0 = move-exception
                monitor-exit(r2)     // Catch: java.lang.Throwable -> L19
                throw r0     // Catch: java.lang.InterruptedException -> L0
            */
            throw new UnsupportedOperationException("Method not decompiled: a.a.C0000a.run():void");
        }
    }

    @Nullable
    static a awaitTimeout() throws InterruptedException {
        a aVar = head.next;
        if (aVar == null) {
            long jNanoTime = System.nanoTime();
            a.class.wait(IDLE_TIMEOUT_MILLIS);
            if (head.next != null || System.nanoTime() - jNanoTime < IDLE_TIMEOUT_NANOS) {
                return null;
            }
            return head;
        }
        long jRemainingNanos = aVar.remainingNanos(System.nanoTime());
        if (jRemainingNanos > 0) {
            long j = jRemainingNanos / 1000000;
            a.class.wait(j, (int) (jRemainingNanos - (1000000 * j)));
            return null;
        }
        head.next = aVar.next;
        aVar.next = null;
        return aVar;
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x000b, code lost:
    
        r1.next = r3.next;
        r3.next = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0012, code lost:
    
        r3 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static synchronized boolean cancelScheduledTimeout(a.a r3) {
        /*
            java.lang.Class<a.a> r0 = a.a.class
            monitor-enter(r0)
            a.a r1 = a.a.head     // Catch: java.lang.Throwable -> L1a
        L5:
            if (r1 == 0) goto L18
            a.a r2 = r1.next     // Catch: java.lang.Throwable -> L1a
            if (r2 != r3) goto L15
            a.a r2 = r3.next     // Catch: java.lang.Throwable -> L1a
            r1.next = r2     // Catch: java.lang.Throwable -> L1a
            r1 = 0
            r3.next = r1     // Catch: java.lang.Throwable -> L1a
            r3 = 0
        L13:
            monitor-exit(r0)
            return r3
        L15:
            a.a r1 = r1.next     // Catch: java.lang.Throwable -> L1a
            goto L5
        L18:
            r3 = 1
            goto L13
        L1a:
            r3 = move-exception
            monitor-exit(r0)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: a.a.cancelScheduledTimeout(a.a):boolean");
    }

    private long remainingNanos(long j) {
        return this.timeoutAt - j;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x005c A[Catch: all -> 0x0069, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0007, B:7:0x0016, B:10:0x0022, B:11:0x002b, B:16:0x003a, B:17:0x0040, B:19:0x0044, B:22:0x004f, B:23:0x0052, B:25:0x005c, B:15:0x0034, B:28:0x0063, B:29:0x0068), top: B:33:0x0003 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static synchronized void scheduleTimeout(a.a r5, long r6, boolean r8) {
        /*
            java.lang.Class<a.a> r0 = a.a.class
            monitor-enter(r0)
            a.a r1 = a.a.head     // Catch: java.lang.Throwable -> L69
            if (r1 != 0) goto L16
            a.a r1 = new a.a     // Catch: java.lang.Throwable -> L69
            r1.<init>()     // Catch: java.lang.Throwable -> L69
            a.a.head = r1     // Catch: java.lang.Throwable -> L69
            a.a$a r1 = new a.a$a     // Catch: java.lang.Throwable -> L69
            r1.<init>()     // Catch: java.lang.Throwable -> L69
            r1.start()     // Catch: java.lang.Throwable -> L69
        L16:
            long r1 = java.lang.System.nanoTime()     // Catch: java.lang.Throwable -> L69
            r3 = 0
            int r3 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r3 == 0) goto L2f
            if (r8 == 0) goto L2f
            long r3 = r5.deadlineNanoTime()     // Catch: java.lang.Throwable -> L69
            long r3 = r3 - r1
            long r6 = java.lang.Math.min(r6, r3)     // Catch: java.lang.Throwable -> L69
        L2b:
            long r6 = r6 + r1
            r5.timeoutAt = r6     // Catch: java.lang.Throwable -> L69
            goto L3a
        L2f:
            if (r3 == 0) goto L32
            goto L2b
        L32:
            if (r8 == 0) goto L63
            long r6 = r5.deadlineNanoTime()     // Catch: java.lang.Throwable -> L69
            r5.timeoutAt = r6     // Catch: java.lang.Throwable -> L69
        L3a:
            long r6 = r5.remainingNanos(r1)     // Catch: java.lang.Throwable -> L69
            a.a r8 = a.a.head     // Catch: java.lang.Throwable -> L69
        L40:
            a.a r3 = r8.next     // Catch: java.lang.Throwable -> L69
            if (r3 == 0) goto L52
            a.a r3 = r8.next     // Catch: java.lang.Throwable -> L69
            long r3 = r3.remainingNanos(r1)     // Catch: java.lang.Throwable -> L69
            int r3 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r3 >= 0) goto L4f
            goto L52
        L4f:
            a.a r8 = r8.next     // Catch: java.lang.Throwable -> L69
            goto L40
        L52:
            a.a r6 = r8.next     // Catch: java.lang.Throwable -> L69
            r5.next = r6     // Catch: java.lang.Throwable -> L69
            r8.next = r5     // Catch: java.lang.Throwable -> L69
            a.a r5 = a.a.head     // Catch: java.lang.Throwable -> L69
            if (r8 != r5) goto L61
            java.lang.Class<a.a> r5 = a.a.class
            r5.notify()     // Catch: java.lang.Throwable -> L69
        L61:
            monitor-exit(r0)
            return
        L63:
            java.lang.AssertionError r5 = new java.lang.AssertionError     // Catch: java.lang.Throwable -> L69
            r5.<init>()     // Catch: java.lang.Throwable -> L69
            throw r5     // Catch: java.lang.Throwable -> L69
        L69:
            r5 = move-exception
            monitor-exit(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: a.a.scheduleTimeout(a.a, long, boolean):void");
    }

    public final void enter() {
        if (this.inQueue) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }
        long jTimeoutNanos = timeoutNanos();
        boolean zHasDeadline = hasDeadline();
        if (jTimeoutNanos != 0 || zHasDeadline) {
            this.inQueue = true;
            scheduleTimeout(this, jTimeoutNanos, zHasDeadline);
        }
    }

    final IOException exit(IOException iOException) {
        return !exit() ? iOException : newTimeoutException(iOException);
    }

    final void exit(boolean z) throws IOException {
        if (exit() && z) {
            throw newTimeoutException(null);
        }
    }

    public final boolean exit() {
        if (!this.inQueue) {
            return false;
        }
        this.inQueue = false;
        return cancelScheduledTimeout(this);
    }

    protected IOException newTimeoutException(@Nullable IOException iOException) {
        InterruptedIOException interruptedIOException = new InterruptedIOException("timeout");
        if (iOException != null) {
            interruptedIOException.initCause(iOException);
        }
        return interruptedIOException;
    }

    public final r sink(final r rVar) {
        return new r() { // from class: a.a.1
            @Override // a.r, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                a.this.enter();
                try {
                    try {
                        rVar.close();
                        a.this.exit(true);
                    } catch (IOException e) {
                        throw a.this.exit(e);
                    }
                } catch (Throwable th) {
                    a.this.exit(false);
                    throw th;
                }
            }

            @Override // a.r, java.io.Flushable
            public void flush() throws IOException {
                a.this.enter();
                try {
                    try {
                        rVar.flush();
                        a.this.exit(true);
                    } catch (IOException e) {
                        throw a.this.exit(e);
                    }
                } catch (Throwable th) {
                    a.this.exit(false);
                    throw th;
                }
            }

            @Override // a.r
            public t timeout() {
                return a.this;
            }

            public String toString() {
                return "AsyncTimeout.sink(" + rVar + ")";
            }

            @Override // a.r
            public void write(c cVar, long j) throws IOException {
                u.a(cVar.b, 0L, j);
                while (true) {
                    long j2 = 0;
                    if (j <= 0) {
                        return;
                    }
                    o oVar = cVar.f3a;
                    while (true) {
                        if (j2 >= PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH) {
                            break;
                        }
                        j2 += oVar.c - oVar.b;
                        if (j2 >= j) {
                            j2 = j;
                            break;
                        }
                        oVar = oVar.f;
                    }
                    a.this.enter();
                    try {
                        try {
                            rVar.write(cVar, j2);
                            j -= j2;
                            a.this.exit(true);
                        } catch (IOException e) {
                            throw a.this.exit(e);
                        }
                    } catch (Throwable th) {
                        a.this.exit(false);
                        throw th;
                    }
                }
            }
        };
    }

    public final s source(final s sVar) {
        return new s() { // from class: a.a.2
            @Override // a.s, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                try {
                    try {
                        sVar.close();
                        a.this.exit(true);
                    } catch (IOException e) {
                        throw a.this.exit(e);
                    }
                } catch (Throwable th) {
                    a.this.exit(false);
                    throw th;
                }
            }

            @Override // a.s
            public long read(c cVar, long j) throws IOException {
                a.this.enter();
                try {
                    try {
                        long j2 = sVar.read(cVar, j);
                        a.this.exit(true);
                        return j2;
                    } catch (IOException e) {
                        throw a.this.exit(e);
                    }
                } catch (Throwable th) {
                    a.this.exit(false);
                    throw th;
                }
            }

            @Override // a.s
            public t timeout() {
                return a.this;
            }

            public String toString() {
                return "AsyncTimeout.source(" + sVar + ")";
            }
        };
    }

    protected void timedOut() {
    }
}
