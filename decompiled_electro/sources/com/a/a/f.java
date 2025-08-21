package com.a.a;

import java.io.Closeable;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes.dex */
public class f implements Closeable {

    /* renamed from: a, reason: collision with root package name */
    private final c f207a;
    private final int b;
    private volatile int c;
    private volatile boolean g;
    private final Queue<byte[]> e = new ConcurrentLinkedQueue();
    private final AtomicBoolean d = new AtomicBoolean(false);
    private volatile boolean f = false;

    public f(c cVar, int i) {
        this.f207a = cVar;
        this.b = i;
    }

    void a() {
        byte[] bArrB = e.b(this.b, this.c);
        synchronized (this.f207a.f202a) {
            this.f207a.f202a.write(bArrB);
            this.f207a.f202a.flush();
        }
    }

    void a(int i) {
        this.c = i;
    }

    void a(boolean z) {
        if (!z || this.e.isEmpty()) {
            this.f = true;
        } else {
            this.g = true;
        }
        synchronized (this) {
            notifyAll();
        }
        synchronized (this.e) {
            this.e.notifyAll();
        }
    }

    void a(byte[] bArr) {
        synchronized (this.e) {
            this.e.add(bArr);
            this.e.notifyAll();
        }
    }

    void b() {
        this.d.set(true);
    }

    public byte[] c() {
        byte[] bArrPoll;
        synchronized (this.e) {
            while (true) {
                bArrPoll = this.e.poll();
                if (bArrPoll != null || this.f) {
                    break;
                }
                this.e.wait();
            }
            if (this.f) {
                throw new IOException("Stream closed");
            }
            if (this.g && this.e.isEmpty()) {
                this.f = true;
            }
        }
        return bArrPoll;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        synchronized (this) {
            if (this.f) {
                return;
            }
            a(false);
            byte[] bArrA = e.a(this.b, this.c);
            synchronized (this.f207a.f202a) {
                this.f207a.f202a.write(bArrA);
                this.f207a.f202a.flush();
            }
        }
    }

    public boolean d() {
        return this.f;
    }
}
