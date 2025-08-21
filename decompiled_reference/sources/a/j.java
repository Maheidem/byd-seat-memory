package a;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* loaded from: classes.dex */
public final class j implements s {
    private final e b;
    private final Inflater c;
    private final k d;

    /* renamed from: a, reason: collision with root package name */
    private int f8a = 0;
    private final CRC32 e = new CRC32();

    public j(s sVar) {
        if (sVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        this.c = new Inflater(true);
        this.b = l.a(sVar);
        this.d = new k(this.b, this.c);
    }

    private void a() throws IOException {
        this.b.a(10L);
        byte bC = this.b.b().c(3L);
        boolean z = ((bC >> 1) & 1) == 1;
        if (z) {
            a(this.b.b(), 0L, 10L);
        }
        a("ID1ID2", 8075, this.b.i());
        this.b.i(8L);
        if (((bC >> 2) & 1) == 1) {
            this.b.a(2L);
            if (z) {
                a(this.b.b(), 0L, 2L);
            }
            long jL = this.b.b().l();
            this.b.a(jL);
            if (z) {
                a(this.b.b(), 0L, jL);
            }
            this.b.i(jL);
        }
        if (((bC >> 3) & 1) == 1) {
            long jA = this.b.a((byte) 0);
            if (jA == -1) {
                throw new EOFException();
            }
            if (z) {
                a(this.b.b(), 0L, jA + 1);
            }
            this.b.i(jA + 1);
        }
        if (((bC >> 4) & 1) == 1) {
            long jA2 = this.b.a((byte) 0);
            if (jA2 == -1) {
                throw new EOFException();
            }
            if (z) {
                a(this.b.b(), 0L, jA2 + 1);
            }
            this.b.i(jA2 + 1);
        }
        if (z) {
            a("FHCRC", this.b.l(), (short) this.e.getValue());
            this.e.reset();
        }
    }

    private void a(c cVar, long j, long j2) {
        o oVar = cVar.f3a;
        while (j >= oVar.c - oVar.b) {
            j -= oVar.c - oVar.b;
            oVar = oVar.f;
        }
        while (j2 > 0) {
            int iMin = (int) Math.min(oVar.c - r6, j2);
            this.e.update(oVar.f17a, (int) (oVar.b + j), iMin);
            j2 -= iMin;
            oVar = oVar.f;
            j = 0;
        }
    }

    private void a(String str, int i, int i2) throws IOException {
        if (i2 != i) {
            throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", str, Integer.valueOf(i2), Integer.valueOf(i)));
        }
    }

    private void b() throws IOException {
        a("CRC", this.b.m(), (int) this.e.getValue());
        a("ISIZE", this.b.m(), (int) this.c.getBytesWritten());
    }

    @Override // a.s, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.d.close();
    }

    @Override // a.s
    public long read(c cVar, long j) throws DataFormatException, IOException {
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        }
        if (j == 0) {
            return 0L;
        }
        if (this.f8a == 0) {
            a();
            this.f8a = 1;
        }
        if (this.f8a == 1) {
            long j2 = cVar.b;
            long j3 = this.d.read(cVar, j);
            if (j3 != -1) {
                a(cVar, j2, j3);
                return j3;
            }
            this.f8a = 2;
        }
        if (this.f8a == 2) {
            b();
            this.f8a = 3;
            if (!this.b.e()) {
                throw new IOException("gzip finished without exhausting source");
            }
        }
        return -1L;
    }

    @Override // a.s
    public t timeout() {
        return this.b.timeout();
    }
}
