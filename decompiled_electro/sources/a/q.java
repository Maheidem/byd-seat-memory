package a;

import java.util.Arrays;

/* loaded from: classes.dex */
final class q extends f {
    final transient byte[][] f;
    final transient int[] g;

    q(c cVar, int i) {
        super(null);
        u.a(cVar.b, 0L, i);
        int i2 = 0;
        o oVar = cVar.f3a;
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            if (oVar.c == oVar.b) {
                throw new AssertionError("s.limit == s.pos");
            }
            i3 += oVar.c - oVar.b;
            i4++;
            oVar = oVar.f;
        }
        this.f = new byte[i4][];
        this.g = new int[i4 * 2];
        o oVar2 = cVar.f3a;
        int i5 = 0;
        while (i2 < i) {
            this.f[i5] = oVar2.f17a;
            i2 += oVar2.c - oVar2.b;
            if (i2 > i) {
                i2 = i;
            }
            this.g[i5] = i2;
            this.g[this.f.length + i5] = oVar2.b;
            oVar2.d = true;
            i5++;
            oVar2 = oVar2.f;
        }
    }

    private int b(int i) {
        int iBinarySearch = Arrays.binarySearch(this.g, 0, this.f.length, i + 1);
        return iBinarySearch >= 0 ? iBinarySearch : ~iBinarySearch;
    }

    private f k() {
        return new f(i());
    }

    @Override // a.f
    public byte a(int i) {
        u.a(this.g[this.f.length - 1], i, 1L);
        int iB = b(i);
        return this.f[iB][(i - (iB == 0 ? 0 : this.g[iB - 1])) + this.g[this.f.length + iB]];
    }

    @Override // a.f
    public f a(int i, int i2) {
        return k().a(i, i2);
    }

    @Override // a.f
    public String a() {
        return k().a();
    }

    @Override // a.f
    void a(c cVar) {
        int length = this.f.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = this.g[length + i];
            int i4 = this.g[i];
            o oVar = new o(this.f[i], i3, (i3 + i4) - i2, true, false);
            if (cVar.f3a == null) {
                oVar.g = oVar;
                oVar.f = oVar;
                cVar.f3a = oVar;
            } else {
                cVar.f3a.g.a(oVar);
            }
            i++;
            i2 = i4;
        }
        cVar.b += i2;
    }

    @Override // a.f
    public boolean a(int i, f fVar, int i2, int i3) {
        if (i < 0 || i > h() - i3) {
            return false;
        }
        int iB = b(i);
        while (i3 > 0) {
            int i4 = iB == 0 ? 0 : this.g[iB - 1];
            int iMin = Math.min(i3, ((this.g[iB] - i4) + i4) - i);
            if (!fVar.a(i2, this.f[iB], (i - i4) + this.g[this.f.length + iB], iMin)) {
                return false;
            }
            i += iMin;
            i2 += iMin;
            i3 -= iMin;
            iB++;
        }
        return true;
    }

    @Override // a.f
    public boolean a(int i, byte[] bArr, int i2, int i3) {
        if (i < 0 || i > h() - i3 || i2 < 0 || i2 > bArr.length - i3) {
            return false;
        }
        int iB = b(i);
        while (i3 > 0) {
            int i4 = iB == 0 ? 0 : this.g[iB - 1];
            int iMin = Math.min(i3, ((this.g[iB] - i4) + i4) - i);
            if (!u.a(this.f[iB], (i - i4) + this.g[this.f.length + iB], bArr, i2, iMin)) {
                return false;
            }
            i += iMin;
            i2 += iMin;
            i3 -= iMin;
            iB++;
        }
        return true;
    }

    @Override // a.f
    public String b() {
        return k().b();
    }

    @Override // a.f
    public f c() {
        return k().c();
    }

    @Override // a.f
    public f d() {
        return k().d();
    }

    @Override // a.f
    public f e() {
        return k().e();
    }

    @Override // a.f
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof f) {
            f fVar = (f) obj;
            if (fVar.h() == h() && a(0, fVar, 0, h())) {
                return true;
            }
        }
        return false;
    }

    @Override // a.f
    public String f() {
        return k().f();
    }

    @Override // a.f
    public f g() {
        return k().g();
    }

    @Override // a.f
    public int h() {
        return this.g[this.f.length - 1];
    }

    @Override // a.f
    public int hashCode() {
        int i = this.d;
        if (i != 0) {
            return i;
        }
        int length = this.f.length;
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        while (i2 < length) {
            byte[] bArr = this.f[i2];
            int i5 = this.g[length + i2];
            int i6 = this.g[i2];
            int i7 = (i6 - i4) + i5;
            while (i5 < i7) {
                i3 = bArr[i5] + (31 * i3);
                i5++;
            }
            i2++;
            i4 = i6;
        }
        this.d = i3;
        return i3;
    }

    @Override // a.f
    public byte[] i() {
        byte[] bArr = new byte[this.g[this.f.length - 1]];
        int length = this.f.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = this.g[length + i];
            int i4 = this.g[i];
            System.arraycopy(this.f[i], i3, bArr, i2, i4 - i2);
            i++;
            i2 = i4;
        }
        return bArr;
    }

    @Override // a.f
    byte[] j() {
        return i();
    }

    @Override // a.f
    public String toString() {
        return k().toString();
    }
}
