package a;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
final class n implements e {

    /* renamed from: a, reason: collision with root package name */
    public final c f15a = new c();
    public final s b;
    boolean c;

    n(s sVar) {
        if (sVar == null) {
            throw new NullPointerException("source == null");
        }
        this.b = sVar;
    }

    @Override // a.e
    public long a(byte b) {
        return a(b, 0L, Long.MAX_VALUE);
    }

    public long a(byte b, long j, long j2) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        if (j < 0 || j2 < j) {
            throw new IllegalArgumentException(String.format("fromIndex=%s toIndex=%s", Long.valueOf(j), Long.valueOf(j2)));
        }
        while (j < j2) {
            long jA = this.f15a.a(b, j, j2);
            if (jA != -1) {
                return jA;
            }
            long j3 = this.f15a.b;
            if (j3 >= j2 || this.b.read(this.f15a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return -1L;
            }
            j = Math.max(j, j3);
        }
        return -1L;
    }

    @Override // a.e
    public String a(Charset charset) {
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        this.f15a.a(this.b);
        return this.f15a.a(charset);
    }

    @Override // a.e
    public void a(long j) throws EOFException {
        if (!b(j)) {
            throw new EOFException();
        }
    }

    @Override // a.e
    public void a(c cVar, long j) throws EOFException {
        try {
            a(j);
            this.f15a.a(cVar, j);
        } catch (EOFException e) {
            cVar.a(this.f15a);
            throw e;
        }
    }

    @Override // a.e
    public void a(byte[] bArr) throws EOFException {
        try {
            a(bArr.length);
            this.f15a.a(bArr);
        } catch (EOFException e) {
            int i = 0;
            while (this.f15a.b > 0) {
                int iA = this.f15a.a(bArr, i, (int) this.f15a.b);
                if (iA == -1) {
                    throw new AssertionError();
                }
                i += iA;
            }
            throw e;
        }
    }

    @Override // a.e
    public boolean a(long j, f fVar) {
        return a(j, fVar, 0, fVar.h());
    }

    public boolean a(long j, f fVar, int i, int i2) {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        if (j < 0 || i < 0 || i2 < 0 || fVar.h() - i < i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            long j2 = i3 + j;
            if (!b(1 + j2) || this.f15a.c(j2) != fVar.a(i + i3)) {
                return false;
            }
        }
        return true;
    }

    @Override // a.e
    public c b() {
        return this.f15a;
    }

    @Override // a.e
    public boolean b(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        }
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        while (this.f15a.b < j) {
            if (this.b.read(this.f15a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                return false;
            }
        }
        return true;
    }

    @Override // a.s, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.c) {
            return;
        }
        this.c = true;
        this.b.close();
        this.f15a.t();
    }

    @Override // a.e
    public f d(long j) throws EOFException {
        a(j);
        return this.f15a.d(j);
    }

    @Override // a.e
    public boolean e() {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        return this.f15a.e() && this.b.read(this.f15a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1;
    }

    @Override // a.e
    public InputStream f() {
        return new InputStream() { // from class: a.n.1
            @Override // java.io.InputStream
            public int available() throws IOException {
                if (n.this.c) {
                    throw new IOException("closed");
                }
                return (int) Math.min(n.this.f15a.b, 2147483647L);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
                n.this.close();
            }

            @Override // java.io.InputStream
            public int read() throws IOException {
                if (n.this.c) {
                    throw new IOException("closed");
                }
                if (n.this.f15a.b == 0 && n.this.b.read(n.this.f15a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                    return -1;
                }
                return n.this.f15a.h() & 255;
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i, int i2) throws IOException {
                if (n.this.c) {
                    throw new IOException("closed");
                }
                u.a(bArr.length, i, i2);
                if (n.this.f15a.b == 0 && n.this.b.read(n.this.f15a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                    return -1;
                }
                return n.this.f15a.a(bArr, i, i2);
            }

            public String toString() {
                return n.this + ".inputStream()";
            }
        };
    }

    @Override // a.e
    public String f(long j) throws EOFException {
        if (j < 0) {
            throw new IllegalArgumentException("limit < 0: " + j);
        }
        long j2 = j == Long.MAX_VALUE ? Long.MAX_VALUE : j + 1;
        long jA = a((byte) 10, 0L, j2);
        if (jA != -1) {
            return this.f15a.g(jA);
        }
        if (j2 < Long.MAX_VALUE && b(j2) && this.f15a.c(j2 - 1) == 13 && b(1 + j2) && this.f15a.c(j2) == 10) {
            return this.f15a.g(j2);
        }
        c cVar = new c();
        this.f15a.a(cVar, 0L, Math.min(32L, this.f15a.a()));
        throw new EOFException("\\n not found: limit=" + Math.min(this.f15a.a(), j) + " content=" + cVar.p().f() + (char) 8230);
    }

    @Override // a.e
    public byte h() throws EOFException {
        a(1L);
        return this.f15a.h();
    }

    @Override // a.e
    public byte[] h(long j) throws EOFException {
        a(j);
        return this.f15a.h(j);
    }

    @Override // a.e
    public short i() throws EOFException {
        a(2L);
        return this.f15a.i();
    }

    @Override // a.e
    public void i(long j) throws EOFException {
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        while (j > 0) {
            if (this.f15a.b == 0 && this.b.read(this.f15a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
                throw new EOFException();
            }
            long jMin = Math.min(j, this.f15a.a());
            this.f15a.i(jMin);
            j -= jMin;
        }
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return !this.c;
    }

    @Override // a.e
    public int j() throws EOFException {
        a(4L);
        return this.f15a.j();
    }

    @Override // a.e
    public long k() throws EOFException {
        a(8L);
        return this.f15a.k();
    }

    @Override // a.e
    public short l() throws EOFException {
        a(2L);
        return this.f15a.l();
    }

    @Override // a.e
    public int m() throws EOFException {
        a(4L);
        return this.f15a.m();
    }

    @Override // a.e
    public long n() throws EOFException {
        byte bC;
        a(1L);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!b(i2)) {
                break;
            }
            bC = this.f15a.c(i);
            if ((bC < 48 || bC > 57) && !(i == 0 && bC == 45)) {
                break;
            }
            i = i2;
        }
        if (i == 0) {
            throw new NumberFormatException(String.format("Expected leading [0-9] or '-' character but was %#x", Byte.valueOf(bC)));
        }
        return this.f15a.n();
    }

    @Override // a.e
    public long o() throws EOFException {
        byte bC;
        a(1L);
        int i = 0;
        while (true) {
            int i2 = i + 1;
            if (!b(i2)) {
                break;
            }
            bC = this.f15a.c(i);
            if ((bC < 48 || bC > 57) && ((bC < 97 || bC > 102) && (bC < 65 || bC > 70))) {
                break;
            }
            i = i2;
        }
        if (i == 0) {
            throw new NumberFormatException(String.format("Expected leading [0-9a-fA-F] character but was %#x", Byte.valueOf(bC)));
        }
        return this.f15a.o();
    }

    @Override // a.e
    public String r() {
        return f(Long.MAX_VALUE);
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) {
        if (this.f15a.b == 0 && this.b.read(this.f15a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1;
        }
        return this.f15a.read(byteBuffer);
    }

    @Override // a.s
    public long read(c cVar, long j) {
        if (cVar == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        }
        if (this.c) {
            throw new IllegalStateException("closed");
        }
        if (this.f15a.b == 0 && this.b.read(this.f15a, PlaybackStateCompat.ACTION_PLAY_FROM_URI) == -1) {
            return -1L;
        }
        return this.f15a.read(cVar, Math.min(j, this.f15a.b));
    }

    @Override // a.e
    public byte[] s() {
        this.f15a.a(this.b);
        return this.f15a.s();
    }

    @Override // a.s
    public t timeout() {
        return this.b.timeout();
    }

    public String toString() {
        return "buffer(" + this.b + ")";
    }
}
