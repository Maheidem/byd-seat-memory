package a;

import javax.annotation.Nullable;

/* loaded from: classes.dex */
final class o {

    /* renamed from: a, reason: collision with root package name */
    final byte[] f17a;
    int b;
    int c;
    boolean d;
    boolean e;
    o f;
    o g;

    o() {
        this.f17a = new byte[8192];
        this.e = true;
        this.d = false;
    }

    o(byte[] bArr, int i, int i2, boolean z, boolean z2) {
        this.f17a = bArr;
        this.b = i;
        this.c = i2;
        this.d = z;
        this.e = z2;
    }

    final o a() {
        this.d = true;
        return new o(this.f17a, this.b, this.c, true, false);
    }

    public final o a(int i) {
        o oVarA;
        if (i <= 0 || i > this.c - this.b) {
            throw new IllegalArgumentException();
        }
        if (i >= 1024) {
            oVarA = a();
        } else {
            oVarA = p.a();
            System.arraycopy(this.f17a, this.b, oVarA.f17a, 0, i);
        }
        oVarA.c = oVarA.b + i;
        this.b += i;
        this.g.a(oVarA);
        return oVarA;
    }

    public final o a(o oVar) {
        oVar.g = this;
        oVar.f = this.f;
        this.f.g = oVar;
        this.f = oVar;
        return oVar;
    }

    public final void a(o oVar, int i) {
        if (!oVar.e) {
            throw new IllegalArgumentException();
        }
        if (oVar.c + i > 8192) {
            if (oVar.d) {
                throw new IllegalArgumentException();
            }
            if ((oVar.c + i) - oVar.b > 8192) {
                throw new IllegalArgumentException();
            }
            System.arraycopy(oVar.f17a, oVar.b, oVar.f17a, 0, oVar.c - oVar.b);
            oVar.c -= oVar.b;
            oVar.b = 0;
        }
        System.arraycopy(this.f17a, this.b, oVar.f17a, oVar.c, i);
        oVar.c += i;
        this.b += i;
    }

    final o b() {
        return new o((byte[]) this.f17a.clone(), this.b, this.c, false, true);
    }

    @Nullable
    public final o c() {
        o oVar = this.f != this ? this.f : null;
        this.g.f = this.f;
        this.f.g = this.g;
        this.f = null;
        this.g = null;
        return oVar;
    }

    public final void d() {
        if (this.g == this) {
            throw new IllegalStateException();
        }
        if (this.g.e) {
            int i = this.c - this.b;
            if (i > (8192 - this.g.c) + (this.g.d ? 0 : this.g.b)) {
                return;
            }
            a(this.g, i);
            c();
            p.a(this);
        }
    }
}
