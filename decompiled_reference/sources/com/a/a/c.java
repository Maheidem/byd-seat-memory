package com.a.a;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class c implements Closeable {

    /* renamed from: a, reason: collision with root package name */
    volatile OutputStream f202a;
    private Socket b;
    private volatile InputStream d;
    private volatile boolean f;
    private volatile boolean g;
    private volatile boolean h;
    private volatile boolean i;
    private volatile int j;
    private volatile d k;
    private boolean l;
    private volatile ConcurrentHashMap<Integer, f> m = new ConcurrentHashMap<>();
    private int c = 0;
    private volatile Thread e = b();

    private c() {
    }

    public static c a(Socket socket, d dVar) throws SocketException {
        c cVar = new c();
        cVar.k = dVar;
        cVar.b = socket;
        cVar.d = socket.getInputStream();
        cVar.f202a = socket.getOutputStream();
        socket.setTcpNoDelay(true);
        return cVar;
    }

    private boolean a(long j, TimeUnit timeUnit) {
        synchronized (this) {
            long jCurrentTimeMillis = System.currentTimeMillis() + timeUnit.toMillis(j);
            while (!this.i && this.f && jCurrentTimeMillis - System.currentTimeMillis() > 0) {
                wait(jCurrentTimeMillis - System.currentTimeMillis());
            }
            if (this.i) {
                return true;
            }
            if (this.f) {
                return false;
            }
            if (this.h) {
                throw new a();
            }
            throw new IOException("Connection failed");
        }
    }

    private Thread b() {
        return new Thread(new Runnable() { // from class: com.a.a.c.1
            /* JADX WARN: Removed duplicated region for block: B:67:0x0101 A[EXC_TOP_SPLITTER, SYNTHETIC] */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public void run() {
                /*
                    Method dump skipped, instructions count: 300
                    To view this dump add '--comments-level debug' option
                */
                throw new UnsupportedOperationException("Method not decompiled: com.a.a.c.AnonymousClass1.run():void");
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        Iterator<f> it = this.m.values().iterator();
        while (it.hasNext()) {
            try {
                it.next().close();
            } catch (IOException unused) {
            }
        }
        this.m.clear();
    }

    public f a(String str) throws ConnectException {
        int i = this.c + 1;
        this.c = i;
        if (!this.f) {
            throw new IllegalStateException("connect() must be called first");
        }
        a(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        f fVar = new f(this, i);
        this.m.put(Integer.valueOf(i), fVar);
        synchronized (this.f202a) {
            this.f202a.write(e.a(i, str));
            this.f202a.flush();
        }
        synchronized (fVar) {
            fVar.wait();
        }
        if (fVar.d()) {
            throw new ConnectException("Stream open actively rejected by remote peer");
        }
        return fVar;
    }

    public void a() {
        a(Long.MAX_VALUE, TimeUnit.MILLISECONDS, false);
    }

    public boolean a(long j, TimeUnit timeUnit, boolean z) {
        if (this.i) {
            throw new IllegalStateException("Already connected");
        }
        synchronized (this.f202a) {
            this.f202a.write(e.a());
            this.f202a.flush();
        }
        this.f = true;
        this.g = z;
        this.h = false;
        this.e.start();
        return a(j, timeUnit);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws InterruptedException, IOException {
        if (this.e == null) {
            return;
        }
        this.b.close();
        this.e.interrupt();
        try {
            this.e.join();
        } catch (InterruptedException unused) {
        }
    }
}
