package a;

import java.io.EOFException;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

/* loaded from: classes.dex */
public final class k implements s {

    /* renamed from: a, reason: collision with root package name */
    private final e f9a;
    private final Inflater b;
    private int c;
    private boolean d;

    k(e eVar, Inflater inflater) {
        if (eVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (inflater == null) {
            throw new IllegalArgumentException("inflater == null");
        }
        this.f9a = eVar;
        this.b = inflater;
    }

    private void b() {
        if (this.c == 0) {
            return;
        }
        int remaining = this.c - this.b.getRemaining();
        this.c -= remaining;
        this.f9a.i(remaining);
    }

    public final boolean a() {
        if (!this.b.needsInput()) {
            return false;
        }
        b();
        if (this.b.getRemaining() != 0) {
            throw new IllegalStateException("?");
        }
        if (this.f9a.e()) {
            return true;
        }
        o oVar = this.f9a.b().f3a;
        this.c = oVar.c - oVar.b;
        this.b.setInput(oVar.f17a, oVar.b, this.c);
        return false;
    }

    @Override // a.s, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.d) {
            return;
        }
        this.b.end();
        this.d = true;
        this.f9a.close();
    }

    @Override // a.s
    public long read(c cVar, long j) throws DataFormatException, IOException {
        boolean zA;
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        }
        if (this.d) {
            throw new IllegalStateException("closed");
        }
        if (j == 0) {
            return 0L;
        }
        do {
            zA = a();
            try {
                o oVarE = cVar.e(1);
                int iInflate = this.b.inflate(oVarE.f17a, oVarE.c, (int) Math.min(j, 8192 - oVarE.c));
                if (iInflate > 0) {
                    oVarE.c += iInflate;
                    long j2 = iInflate;
                    cVar.b += j2;
                    return j2;
                }
                if (!this.b.finished() && !this.b.needsDictionary()) {
                }
                b();
                if (oVarE.b != oVarE.c) {
                    return -1L;
                }
                cVar.f3a = oVarE.c();
                p.a(oVarE);
                return -1L;
            } catch (DataFormatException e) {
                throw new IOException(e);
            }
        } while (!zA);
        throw new EOFException("source exhausted prematurely");
    }

    @Override // a.s
    public t timeout() {
        return this.f9a.timeout();
    }
}
