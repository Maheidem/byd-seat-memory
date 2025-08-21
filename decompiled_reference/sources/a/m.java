package a;

import android.support.v4.media.session.PlaybackStateCompat;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
final class m implements d {

    /* renamed from: a, reason: collision with root package name */
    public final c f14a = new c();
    public final r b;
    boolean c;

    m(r rVar) {
        if (rVar == null) {
            throw new NullPointerException("sink == null");
        }
        this.b = rVar;
    }

    @Override // a.d
    public long a(s sVar) {
        if (sVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long j2 = sVar.read(this.f14a, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
            if (j2 == -1) {
                return j;
            }
            j += j2;
            w();
        }
    }

    @Override // a.d, a.e
    public c b() {
        return this.f14a;
    }

    @Override // a.d
    public d b(String str) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.f14a.b(str);
        return w();
    }

    @Override // a.d
    public d c(f fVar) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.f14a.c(fVar);
        return w();
    }

    @Override // a.d
    public d c(byte[] bArr) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.f14a.c(bArr);
        return w();
    }

    @Override // a.d
    public d c(byte[] bArr, int i, int i2) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.f14a.c(bArr, i, i2);
        return w();
    }

    @Override // a.r, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws Throwable {
        if (this.c) {
            return;
        }
        Throwable th = null;
        try {
            if (this.f14a.b > 0) {
                this.b.write(this.f14a, this.f14a.b);
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            this.b.close();
        } catch (Throwable th3) {
            if (th == null) {
                th = th3;
            }
        }
        this.c = true;
        if (th != null) {
            u.a(th);
        }
    }

    @Override // a.d
    public d d() {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        long jA = this.f14a.a();
        if (jA > 0) {
            this.b.write(this.f14a, jA);
        }
        return this;
    }

    @Override // a.d, a.r, java.io.Flushable
    public void flush() {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        if (this.f14a.b > 0) {
            this.b.write(this.f14a, this.f14a.b);
        }
        this.b.flush();
    }

    @Override // a.d
    public d g(int i) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.f14a.g(i);
        return w();
    }

    @Override // a.d
    public d h(int i) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.f14a.h(i);
        return w();
    }

    @Override // a.d
    public d i(int i) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.f14a.i(i);
        return w();
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.c;
    }

    @Override // a.d
    public d m(long j) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.f14a.m(j);
        return w();
    }

    @Override // a.d
    public d n(long j) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.f14a.n(j);
        return w();
    }

    @Override // a.r
    public t timeout() {
        return this.b.timeout();
    }

    public String toString() {
        return "buffer(" + this.b + ")";
    }

    @Override // a.d
    public d w() {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        long jG = this.f14a.g();
        if (jG > 0) {
            this.b.write(this.f14a, jG);
        }
        return this;
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer byteBuffer) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        int iWrite = this.f14a.write(byteBuffer);
        w();
        return iWrite;
    }

    @Override // a.r
    public void write(c cVar, long j) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        this.f14a.write(cVar, j);
        w();
    }
}
