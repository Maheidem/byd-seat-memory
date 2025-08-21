package a;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.Closeable;
import java.io.EOFException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class c implements d, e, Cloneable, ByteChannel {
    private static final byte[] c = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

    /* renamed from: a, reason: collision with root package name */
    @Nullable
    o f3a;
    long b;

    public static final class a implements Closeable {

        /* renamed from: a, reason: collision with root package name */
        public c f5a;
        public boolean b;
        public byte[] d;
        private o g;
        public long c = -1;
        public int e = -1;
        public int f = -1;

        public final int a() {
            if (this.c == this.f5a.b) {
                throw new IllegalStateException();
            }
            return a(this.c == -1 ? 0L : this.c + (this.f - this.e));
        }

        public final int a(long j) {
            if (j < -1 || j > this.f5a.b) {
                throw new ArrayIndexOutOfBoundsException(String.format("offset=%s > size=%s", Long.valueOf(j), Long.valueOf(this.f5a.b)));
            }
            if (j == -1 || j == this.f5a.b) {
                this.g = null;
                this.c = j;
                this.d = null;
                this.e = -1;
                this.f = -1;
                return -1;
            }
            long j2 = 0;
            long j3 = this.f5a.b;
            o oVarA = this.f5a.f3a;
            o oVar = this.f5a.f3a;
            if (this.g != null) {
                long j4 = this.c - (this.e - this.g.b);
                if (j4 > j) {
                    oVar = this.g;
                    j3 = j4;
                } else {
                    oVarA = this.g;
                    j2 = j4;
                }
            }
            if (j3 - j > j - j2) {
                while (j >= (oVarA.c - oVarA.b) + j2) {
                    j2 += oVarA.c - oVarA.b;
                    oVarA = oVarA.f;
                }
            } else {
                j2 = j3;
                oVarA = oVar;
                while (j2 > j) {
                    oVarA = oVarA.g;
                    j2 -= oVarA.c - oVarA.b;
                }
            }
            if (this.b && oVarA.d) {
                o oVarB = oVarA.b();
                if (this.f5a.f3a == oVarA) {
                    this.f5a.f3a = oVarB;
                }
                oVarA = oVarA.a(oVarB);
                oVarA.g.c();
            }
            this.g = oVarA;
            this.c = j;
            this.d = oVarA.f17a;
            this.e = oVarA.b + ((int) (j - j2));
            this.f = oVarA.c;
            return this.f - this.e;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.f5a == null) {
                throw new IllegalStateException("not attached to a buffer");
            }
            this.f5a = null;
            this.g = null;
            this.c = -1L;
            this.d = null;
            this.e = -1;
            this.f = -1;
        }
    }

    public int a(byte[] bArr, int i, int i2) {
        u.a(bArr.length, i, i2);
        o oVar = this.f3a;
        if (oVar == null) {
            return -1;
        }
        int iMin = Math.min(i2, oVar.c - oVar.b);
        System.arraycopy(oVar.f17a, oVar.b, bArr, i, iMin);
        oVar.b += iMin;
        this.b -= iMin;
        if (oVar.b == oVar.c) {
            this.f3a = oVar.c();
            p.a(oVar);
        }
        return iMin;
    }

    public final long a() {
        return this.b;
    }

    @Override // a.e
    public long a(byte b) {
        return a(b, 0L, Long.MAX_VALUE);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x004a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public long a(byte r10, long r11, long r13) {
        /*
            r9 = this;
            r0 = 0
            int r2 = (r11 > r0 ? 1 : (r11 == r0 ? 0 : -1))
            if (r2 < 0) goto L7a
            int r2 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r2 >= 0) goto Lc
            goto L7a
        Lc:
            long r2 = r9.b
            int r2 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r2 <= 0) goto L14
            long r13 = r9.b
        L14:
            int r2 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            r3 = -1
            if (r2 != 0) goto L1b
            return r3
        L1b:
            a.o r2 = r9.f3a
            if (r2 != 0) goto L20
            return r3
        L20:
            long r5 = r9.b
            long r5 = r5 - r11
            int r5 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r5 >= 0) goto L37
            long r0 = r9.b
        L29:
            int r9 = (r0 > r11 ? 1 : (r0 == r11 ? 0 : -1))
            if (r9 <= 0) goto L46
            a.o r2 = r2.g
            int r9 = r2.c
            int r5 = r2.b
            int r9 = r9 - r5
            long r5 = (long) r9
            long r0 = r0 - r5
            goto L29
        L37:
            int r9 = r2.c
            int r5 = r2.b
            int r9 = r9 - r5
            long r5 = (long) r9
            long r5 = r5 + r0
            int r9 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r9 >= 0) goto L46
            a.o r2 = r2.f
            r0 = r5
            goto L37
        L46:
            int r9 = (r0 > r13 ? 1 : (r0 == r13 ? 0 : -1))
            if (r9 >= 0) goto L79
            byte[] r9 = r2.f17a
            int r5 = r2.c
            long r5 = (long) r5
            int r7 = r2.b
            long r7 = (long) r7
            long r7 = r7 + r13
            long r7 = r7 - r0
            long r5 = java.lang.Math.min(r5, r7)
            int r5 = (int) r5
            int r6 = r2.b
            long r6 = (long) r6
            long r6 = r6 + r11
            long r6 = r6 - r0
            int r11 = (int) r6
        L5f:
            if (r11 >= r5) goto L6e
            r12 = r9[r11]
            if (r12 != r10) goto L6b
            int r9 = r2.b
            int r11 = r11 - r9
            long r9 = (long) r11
            long r9 = r9 + r0
            return r9
        L6b:
            int r11 = r11 + 1
            goto L5f
        L6e:
            int r9 = r2.c
            int r11 = r2.b
            int r9 = r9 - r11
            long r11 = (long) r9
            long r11 = r11 + r0
            a.o r2 = r2.f
            r0 = r11
            goto L46
        L79:
            return r3
        L7a:
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "size=%s fromIndex=%s toIndex=%s"
            r1 = 3
            java.lang.Object[] r1 = new java.lang.Object[r1]
            r2 = 0
            long r3 = r9.b
            java.lang.Long r9 = java.lang.Long.valueOf(r3)
            r1[r2] = r9
            java.lang.Long r9 = java.lang.Long.valueOf(r11)
            r11 = 1
            r1[r11] = r9
            r9 = 2
            java.lang.Long r11 = java.lang.Long.valueOf(r13)
            r1[r9] = r11
            java.lang.String r9 = java.lang.String.format(r0, r1)
            r10.<init>(r9)
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: a.c.a(byte, long, long):long");
    }

    public long a(f fVar, long j) {
        int i;
        long j2 = 0;
        if (j < 0) {
            throw new IllegalArgumentException("fromIndex < 0");
        }
        o oVar = this.f3a;
        if (oVar == null) {
            return -1L;
        }
        if (this.b - j >= j) {
            while (true) {
                long j3 = (oVar.c - oVar.b) + j2;
                if (j3 >= j) {
                    break;
                }
                oVar = oVar.f;
                j2 = j3;
            }
        } else {
            j2 = this.b;
            while (j2 > j) {
                oVar = oVar.g;
                j2 -= oVar.c - oVar.b;
            }
        }
        if (fVar.h() == 2) {
            byte bA = fVar.a(0);
            byte bA2 = fVar.a(1);
            while (j2 < this.b) {
                byte[] bArr = oVar.f17a;
                i = (int) ((oVar.b + j) - j2);
                int i2 = oVar.c;
                while (i < i2) {
                    byte b = bArr[i];
                    if (b == bA || b == bA2) {
                        return (i - oVar.b) + j2;
                    }
                    i++;
                }
                j = (oVar.c - oVar.b) + j2;
                oVar = oVar.f;
                j2 = j;
            }
            return -1L;
        }
        byte[] bArrJ = fVar.j();
        while (j2 < this.b) {
            byte[] bArr2 = oVar.f17a;
            i = (int) ((oVar.b + j) - j2);
            int i3 = oVar.c;
            while (i < i3) {
                byte b2 = bArr2[i];
                for (byte b3 : bArrJ) {
                    if (b2 == b3) {
                        return (i - oVar.b) + j2;
                    }
                }
                i++;
            }
            j = (oVar.c - oVar.b) + j2;
            oVar = oVar.f;
            j2 = j;
        }
        return -1L;
    }

    @Override // a.d
    public long a(s sVar) {
        if (sVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = 0;
        while (true) {
            long j2 = sVar.read(this, PlaybackStateCompat.ACTION_PLAY_FROM_URI);
            if (j2 == -1) {
                return j;
            }
            j += j2;
        }
    }

    public final a a(a aVar) {
        if (aVar.f5a != null) {
            throw new IllegalStateException("already attached to a buffer");
        }
        aVar.f5a = this;
        aVar.b = true;
        return aVar;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public c a(int i) {
        int i2;
        int i3;
        if (i >= 128) {
            if (i < 2048) {
                i3 = (i >> 6) | 192;
            } else {
                if (i < 65536) {
                    if (i >= 55296 && i <= 57343) {
                        i(63);
                        return this;
                    }
                    i2 = (i >> 12) | 224;
                } else {
                    if (i > 1114111) {
                        throw new IllegalArgumentException("Unexpected code point: " + Integer.toHexString(i));
                    }
                    i((i >> 18) | 240);
                    i2 = ((i >> 12) & 63) | 128;
                }
                i(i2);
                i3 = ((i >> 6) & 63) | 128;
            }
            i(i3);
            i = (i & 63) | 128;
        }
        i(i);
        return this;
    }

    public final c a(c cVar, long j, long j2) {
        if (cVar == null) {
            throw new IllegalArgumentException("out == null");
        }
        u.a(this.b, j, j2);
        if (j2 == 0) {
            return this;
        }
        cVar.b += j2;
        o oVar = this.f3a;
        while (j >= oVar.c - oVar.b) {
            j -= oVar.c - oVar.b;
            oVar = oVar.f;
        }
        while (j2 > 0) {
            o oVarA = oVar.a();
            oVarA.b = (int) (oVarA.b + j);
            oVarA.c = Math.min(oVarA.b + ((int) j2), oVarA.c);
            if (cVar.f3a == null) {
                oVarA.g = oVarA;
                oVarA.f = oVarA;
                cVar.f3a = oVarA;
            } else {
                cVar.f3a.g.a(oVarA);
            }
            j2 -= oVarA.c - oVarA.b;
            oVar = oVar.f;
            j = 0;
        }
        return this;
    }

    @Override // a.d
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public c c(f fVar) {
        if (fVar == null) {
            throw new IllegalArgumentException("byteString == null");
        }
        fVar.a(this);
        return this;
    }

    @Override // a.d
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public c b(String str) {
        return a(str, 0, str.length());
    }

    public c a(String str, int i, int i2) {
        int i3;
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("beginIndex < 0: " + i);
        }
        if (i2 < i) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
        }
        if (i2 > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + i2 + " > " + str.length());
        }
        while (i < i2) {
            char cCharAt = str.charAt(i);
            if (cCharAt < 128) {
                o oVarE = e(1);
                byte[] bArr = oVarE.f17a;
                int i4 = oVarE.c - i;
                int iMin = Math.min(i2, 8192 - i4);
                int i5 = i + 1;
                bArr[i + i4] = (byte) cCharAt;
                while (i5 < iMin) {
                    char cCharAt2 = str.charAt(i5);
                    if (cCharAt2 >= 128) {
                        break;
                    }
                    bArr[i5 + i4] = (byte) cCharAt2;
                    i5++;
                }
                int i6 = (i4 + i5) - oVarE.c;
                oVarE.c += i6;
                this.b += i6;
                i = i5;
            } else {
                if (cCharAt < 2048) {
                    i3 = (cCharAt >> 6) | 192;
                } else if (cCharAt < 55296 || cCharAt > 57343) {
                    i((cCharAt >> '\f') | 224);
                    i3 = ((cCharAt >> 6) & 63) | 128;
                } else {
                    int i7 = i + 1;
                    char cCharAt3 = i7 < i2 ? str.charAt(i7) : (char) 0;
                    if (cCharAt > 56319 || cCharAt3 < 56320 || cCharAt3 > 57343) {
                        i(63);
                        i = i7;
                    } else {
                        int i8 = 65536 + (((cCharAt & 10239) << 10) | (9215 & cCharAt3));
                        i((i8 >> 18) | 240);
                        i(((i8 >> 12) & 63) | 128);
                        i(((i8 >> 6) & 63) | 128);
                        i((i8 & 63) | 128);
                        i += 2;
                    }
                }
                i(i3);
                i((cCharAt & '?') | 128);
                i++;
            }
        }
        return this;
    }

    public c a(String str, int i, int i2, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("string == null");
        }
        if (i < 0) {
            throw new IllegalAccessError("beginIndex < 0: " + i);
        }
        if (i2 < i) {
            throw new IllegalArgumentException("endIndex < beginIndex: " + i2 + " < " + i);
        }
        if (i2 > str.length()) {
            throw new IllegalArgumentException("endIndex > string.length: " + i2 + " > " + str.length());
        }
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (charset.equals(u.f19a)) {
            return a(str, i, i2);
        }
        byte[] bytes = str.substring(i, i2).getBytes(charset);
        return c(bytes, 0, bytes.length);
    }

    public c a(String str, Charset charset) {
        return a(str, 0, str.length(), charset);
    }

    public String a(long j, Charset charset) {
        u.a(this.b, 0L, j);
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        if (j > 2147483647L) {
            throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
        }
        if (j == 0) {
            return "";
        }
        o oVar = this.f3a;
        if (oVar.b + j > oVar.c) {
            return new String(h(j), charset);
        }
        String str = new String(oVar.f17a, oVar.b, (int) j, charset);
        oVar.b = (int) (oVar.b + j);
        this.b -= j;
        if (oVar.b == oVar.c) {
            this.f3a = oVar.c();
            p.a(oVar);
        }
        return str;
    }

    @Override // a.e
    public String a(Charset charset) {
        try {
            return a(this.b, charset);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    @Override // a.e
    public void a(long j) throws EOFException {
        if (this.b < j) {
            throw new EOFException();
        }
    }

    @Override // a.e
    public void a(c cVar, long j) throws EOFException {
        if (this.b < j) {
            cVar.write(this, this.b);
            throw new EOFException();
        }
        cVar.write(this, j);
    }

    @Override // a.e
    public void a(byte[] bArr) throws EOFException {
        int i = 0;
        while (i < bArr.length) {
            int iA = a(bArr, i, bArr.length - i);
            if (iA == -1) {
                throw new EOFException();
            }
            i += iA;
        }
    }

    @Override // a.e
    public boolean a(long j, f fVar) {
        return a(j, fVar, 0, fVar.h());
    }

    public boolean a(long j, f fVar, int i, int i2) {
        if (j < 0 || i < 0 || i2 < 0 || this.b - j < i2 || fVar.h() - i < i2) {
            return false;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            if (c(i3 + j) != fVar.a(i + i3)) {
                return false;
            }
        }
        return true;
    }

    public long b(f fVar) {
        return a(fVar, 0L);
    }

    @Override // a.d, a.e
    public c b() {
        return this;
    }

    @Override // a.d
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public c i(int i) {
        o oVarE = e(1);
        byte[] bArr = oVarE.f17a;
        int i2 = oVarE.c;
        oVarE.c = i2 + 1;
        bArr[i2] = (byte) i;
        this.b++;
        return this;
    }

    @Override // a.d
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public c c(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        return c(bArr, 0, bArr.length);
    }

    @Override // a.d
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public c c(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new IllegalArgumentException("source == null");
        }
        long j = i2;
        u.a(bArr.length, i, j);
        int i3 = i2 + i;
        while (i < i3) {
            o oVarE = e(1);
            int iMin = Math.min(i3 - i, 8192 - oVarE.c);
            System.arraycopy(bArr, i, oVarE.f17a, oVarE.c, iMin);
            i += iMin;
            oVarE.c += iMin;
        }
        this.b += j;
        return this;
    }

    @Override // a.e
    public boolean b(long j) {
        return this.b >= j;
    }

    public final byte c(long j) {
        u.a(this.b, j, 1L);
        if (this.b - j <= j) {
            long j2 = j - this.b;
            o oVar = this.f3a;
            do {
                oVar = oVar.g;
                j2 += oVar.c - oVar.b;
            } while (j2 < 0);
            return oVar.f17a[oVar.b + ((int) j2)];
        }
        o oVar2 = this.f3a;
        while (true) {
            long j3 = oVar2.c - oVar2.b;
            if (j < j3) {
                return oVar2.f17a[oVar2.b + ((int) j)];
            }
            j -= j3;
            oVar2 = oVar2.f;
        }
    }

    @Override // a.d
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public c w() {
        return this;
    }

    @Override // a.d
    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public c h(int i) {
        o oVarE = e(2);
        byte[] bArr = oVarE.f17a;
        int i2 = oVarE.c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 8) & 255);
        bArr[i3] = (byte) (i & 255);
        oVarE.c = i3 + 1;
        this.b += 2;
        return this;
    }

    @Override // a.r, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    @Override // a.d
    /* renamed from: d, reason: merged with bridge method [inline-methods] */
    public c g(int i) {
        o oVarE = e(4);
        byte[] bArr = oVarE.f17a;
        int i2 = oVarE.c;
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((i >>> 24) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 16) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((i >>> 8) & 255);
        bArr[i5] = (byte) (i & 255);
        oVarE.c = i5 + 1;
        this.b += 4;
        return this;
    }

    @Override // a.d
    public d d() {
        return this;
    }

    @Override // a.e
    public f d(long j) {
        return new f(h(j));
    }

    o e(int i) {
        if (i < 1 || i > 8192) {
            throw new IllegalArgumentException();
        }
        if (this.f3a != null) {
            o oVar = this.f3a.g;
            return (oVar.c + i > 8192 || !oVar.e) ? oVar.a(p.a()) : oVar;
        }
        this.f3a = p.a();
        o oVar2 = this.f3a;
        o oVar3 = this.f3a;
        o oVar4 = this.f3a;
        oVar3.g = oVar4;
        oVar2.f = oVar4;
        return oVar4;
    }

    public String e(long j) {
        return a(j, u.f19a);
    }

    @Override // a.e
    public boolean e() {
        return this.b == 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof c)) {
            return false;
        }
        c cVar = (c) obj;
        if (this.b != cVar.b) {
            return false;
        }
        long j = 0;
        if (this.b == 0) {
            return true;
        }
        o oVar = this.f3a;
        o oVar2 = cVar.f3a;
        int i = oVar.b;
        int i2 = oVar2.b;
        while (j < this.b) {
            long jMin = Math.min(oVar.c - i, oVar2.c - i2);
            int i3 = i2;
            int i4 = i;
            int i5 = 0;
            while (i5 < jMin) {
                int i6 = i4 + 1;
                int i7 = i3 + 1;
                if (oVar.f17a[i4] != oVar2.f17a[i3]) {
                    return false;
                }
                i5++;
                i4 = i6;
                i3 = i7;
            }
            if (i4 == oVar.c) {
                oVar = oVar.f;
                i = oVar.b;
            } else {
                i = i4;
            }
            if (i3 == oVar2.c) {
                oVar2 = oVar2.f;
                i2 = oVar2.b;
            } else {
                i2 = i3;
            }
            j += jMin;
        }
        return true;
    }

    public final f f(int i) {
        return i == 0 ? f.b : new q(this, i);
    }

    @Override // a.e
    public InputStream f() {
        return new InputStream() { // from class: a.c.1
            @Override // java.io.InputStream
            public int available() {
                return (int) Math.min(c.this.b, 2147483647L);
            }

            @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // java.io.InputStream
            public int read() {
                if (c.this.b > 0) {
                    return c.this.h() & 255;
                }
                return -1;
            }

            @Override // java.io.InputStream
            public int read(byte[] bArr, int i, int i2) {
                return c.this.a(bArr, i, i2);
            }

            public String toString() {
                return c.this + ".inputStream()";
            }
        };
    }

    @Override // a.e
    public String f(long j) throws EOFException {
        if (j < 0) {
            throw new IllegalArgumentException("limit < 0: " + j);
        }
        long j2 = j != Long.MAX_VALUE ? j + 1 : Long.MAX_VALUE;
        long jA = a((byte) 10, 0L, j2);
        if (jA != -1) {
            return g(jA);
        }
        if (j2 < a() && c(j2 - 1) == 13 && c(j2) == 10) {
            return g(j2);
        }
        c cVar = new c();
        a(cVar, 0L, Math.min(32L, a()));
        throw new EOFException("\\n not found: limit=" + Math.min(a(), j) + " content=" + cVar.p().f() + (char) 8230);
    }

    @Override // a.d, a.r, java.io.Flushable
    public void flush() {
    }

    public final long g() {
        long j = this.b;
        if (j == 0) {
            return 0L;
        }
        o oVar = this.f3a.g;
        return (oVar.c >= 8192 || !oVar.e) ? j : j - (oVar.c - oVar.b);
    }

    String g(long j) throws EOFException {
        if (j > 0) {
            long j2 = j - 1;
            if (c(j2) == 13) {
                String strE = e(j2);
                i(2L);
                return strE;
            }
        }
        String strE2 = e(j);
        i(1L);
        return strE2;
    }

    @Override // a.e
    public byte h() {
        if (this.b == 0) {
            throw new IllegalStateException("size == 0");
        }
        o oVar = this.f3a;
        int i = oVar.b;
        int i2 = oVar.c;
        int i3 = i + 1;
        byte b = oVar.f17a[i];
        this.b--;
        if (i3 != i2) {
            oVar.b = i3;
            return b;
        }
        this.f3a = oVar.c();
        p.a(oVar);
        return b;
    }

    @Override // a.e
    public byte[] h(long j) throws EOFException {
        u.a(this.b, 0L, j);
        if (j <= 2147483647L) {
            byte[] bArr = new byte[(int) j];
            a(bArr);
            return bArr;
        }
        throw new IllegalArgumentException("byteCount > Integer.MAX_VALUE: " + j);
    }

    public int hashCode() {
        o oVar = this.f3a;
        if (oVar == null) {
            return 0;
        }
        int i = 1;
        do {
            int i2 = oVar.c;
            for (int i3 = oVar.b; i3 < i2; i3++) {
                i = oVar.f17a[i3] + (31 * i);
            }
            oVar = oVar.f;
        } while (oVar != this.f3a);
        return i;
    }

    @Override // a.e
    public short i() {
        if (this.b < 2) {
            throw new IllegalStateException("size < 2: " + this.b);
        }
        o oVar = this.f3a;
        int i = oVar.b;
        int i2 = oVar.c;
        if (i2 - i < 2) {
            return (short) ((h() & 255) | ((h() & 255) << 8));
        }
        byte[] bArr = oVar.f17a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i] & 255) << 8) | (bArr[i3] & 255);
        this.b -= 2;
        if (i4 == i2) {
            this.f3a = oVar.c();
            p.a(oVar);
        } else {
            oVar.b = i4;
        }
        return (short) i5;
    }

    @Override // a.e
    public void i(long j) throws EOFException {
        while (j > 0) {
            if (this.f3a == null) {
                throw new EOFException();
            }
            int iMin = (int) Math.min(j, this.f3a.c - this.f3a.b);
            long j2 = iMin;
            this.b -= j2;
            j -= j2;
            this.f3a.b += iMin;
            if (this.f3a.b == this.f3a.c) {
                o oVar = this.f3a;
                this.f3a = oVar.c();
                p.a(oVar);
            }
        }
    }

    @Override // java.nio.channels.Channel
    public boolean isOpen() {
        return true;
    }

    @Override // a.e
    public int j() {
        if (this.b < 4) {
            throw new IllegalStateException("size < 4: " + this.b);
        }
        o oVar = this.f3a;
        int i = oVar.b;
        int i2 = oVar.c;
        if (i2 - i < 4) {
            return (h() & 255) | ((h() & 255) << 24) | ((h() & 255) << 16) | ((h() & 255) << 8);
        }
        byte[] bArr = oVar.f17a;
        int i3 = i + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i] & 255) << 24) | ((bArr[i3] & 255) << 16);
        int i6 = i4 + 1;
        int i7 = i5 | ((bArr[i4] & 255) << 8);
        int i8 = i6 + 1;
        int i9 = i7 | (bArr[i6] & 255);
        this.b -= 4;
        if (i8 != i2) {
            oVar.b = i8;
            return i9;
        }
        this.f3a = oVar.c();
        p.a(oVar);
        return i9;
    }

    public c j(long j) {
        o oVarE = e(8);
        byte[] bArr = oVarE.f17a;
        int i = oVarE.c;
        int i2 = i + 1;
        bArr[i] = (byte) ((j >>> 56) & 255);
        int i3 = i2 + 1;
        bArr[i2] = (byte) ((j >>> 48) & 255);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((j >>> 40) & 255);
        int i5 = i4 + 1;
        bArr[i4] = (byte) ((j >>> 32) & 255);
        int i6 = i5 + 1;
        bArr[i5] = (byte) ((j >>> 24) & 255);
        int i7 = i6 + 1;
        bArr[i6] = (byte) ((j >>> 16) & 255);
        int i8 = i7 + 1;
        bArr[i7] = (byte) ((j >>> 8) & 255);
        bArr[i8] = (byte) (j & 255);
        oVarE.c = i8 + 1;
        this.b += 8;
        return this;
    }

    @Override // a.e
    public long k() {
        if (this.b < 8) {
            throw new IllegalStateException("size < 8: " + this.b);
        }
        o oVar = this.f3a;
        int i = oVar.b;
        int i2 = oVar.c;
        if (i2 - i < 8) {
            return ((j() & 4294967295L) << 32) | (4294967295L & j());
        }
        byte[] bArr = oVar.f17a;
        long j = (bArr[i] & 255) << 56;
        int i3 = i + 1 + 1 + 1;
        long j2 = j | ((bArr[r8] & 255) << 48) | ((bArr[r1] & 255) << 40);
        long j3 = j2 | ((bArr[i3] & 255) << 32) | ((bArr[r1] & 255) << 24);
        long j4 = j3 | ((bArr[r6] & 255) << 16);
        long j5 = j4 | ((bArr[r1] & 255) << 8);
        int i4 = i3 + 1 + 1 + 1 + 1 + 1;
        long j6 = (bArr[r6] & 255) | j5;
        this.b -= 8;
        if (i4 != i2) {
            oVar.b = i4;
            return j6;
        }
        this.f3a = oVar.c();
        p.a(oVar);
        return j6;
    }

    @Override // a.d
    /* renamed from: k, reason: merged with bridge method [inline-methods] */
    public c n(long j) {
        if (j == 0) {
            return i(48);
        }
        boolean z = false;
        int i = 1;
        if (j < 0) {
            j = -j;
            if (j < 0) {
                return b("-9223372036854775808");
            }
            z = true;
        }
        if (j >= 100000000) {
            i = j < 1000000000000L ? j < 10000000000L ? j < 1000000000 ? 9 : 10 : j < 100000000000L ? 11 : 12 : j < 1000000000000000L ? j < 10000000000000L ? 13 : j < 100000000000000L ? 14 : 15 : j < 100000000000000000L ? j < 10000000000000000L ? 16 : 17 : j < 1000000000000000000L ? 18 : 19;
        } else if (j >= 10000) {
            i = j < 1000000 ? j < 100000 ? 5 : 6 : j < 10000000 ? 7 : 8;
        } else if (j >= 100) {
            i = j < 1000 ? 3 : 4;
        } else if (j >= 10) {
            i = 2;
        }
        if (z) {
            i++;
        }
        o oVarE = e(i);
        byte[] bArr = oVarE.f17a;
        int i2 = oVarE.c + i;
        while (j != 0) {
            i2--;
            bArr[i2] = c[(int) (j % 10)];
            j /= 10;
        }
        if (z) {
            bArr[i2 - 1] = 45;
        }
        oVarE.c += i;
        this.b += i;
        return this;
    }

    @Override // a.d
    /* renamed from: l, reason: merged with bridge method [inline-methods] */
    public c m(long j) {
        if (j == 0) {
            return i(48);
        }
        int iNumberOfTrailingZeros = (Long.numberOfTrailingZeros(Long.highestOneBit(j)) / 4) + 1;
        o oVarE = e(iNumberOfTrailingZeros);
        byte[] bArr = oVarE.f17a;
        int i = oVarE.c;
        for (int i2 = (oVarE.c + iNumberOfTrailingZeros) - 1; i2 >= i; i2--) {
            bArr[i2] = c[(int) (15 & j)];
            j >>>= 4;
        }
        oVarE.c += iNumberOfTrailingZeros;
        this.b += iNumberOfTrailingZeros;
        return this;
    }

    @Override // a.e
    public short l() {
        return u.a(i());
    }

    @Override // a.e
    public int m() {
        return u.a(j());
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b2 A[EDGE_INSN: B:48:0x00b2->B:40:0x00b2 BREAK  A[LOOP:0: B:7:0x0018->B:50:?], SYNTHETIC] */
    @Override // a.e
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public long n() {
        /*
            r18 = this;
            r0 = r18
            long r1 = r0.b
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 != 0) goto L12
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "size == 0"
            r0.<init>(r1)
            throw r0
        L12:
            r5 = -7
            r7 = 0
            r8 = r5
            r5 = r7
            r6 = r5
        L18:
            a.o r10 = r0.f3a
            byte[] r11 = r10.f17a
            int r12 = r10.b
            int r13 = r10.c
        L20:
            if (r12 >= r13) goto L9e
            r15 = r11[r12]
            r14 = 48
            if (r15 < r14) goto L71
            r1 = 57
            if (r15 > r1) goto L71
            int r14 = r14 - r15
            r1 = -922337203685477580(0xf333333333333334, double:-8.390303882365713E246)
            int r16 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r16 < 0) goto L44
            if (r16 != 0) goto L3e
            long r1 = (long) r14
            int r1 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r1 >= 0) goto L3e
            goto L44
        L3e:
            r1 = 10
            long r3 = r3 * r1
            long r1 = (long) r14
            long r3 = r3 + r1
            goto L7b
        L44:
            a.c r0 = new a.c
            r0.<init>()
            a.c r0 = r0.n(r3)
            a.c r0 = r0.i(r15)
            if (r5 != 0) goto L56
            r0.h()
        L56:
            java.lang.NumberFormatException r1 = new java.lang.NumberFormatException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Number too large: "
            r2.append(r3)
            java.lang.String r0 = r0.q()
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L71:
            r1 = 45
            if (r15 != r1) goto L80
            if (r7 != 0) goto L80
            r1 = 1
            long r8 = r8 - r1
            r5 = 1
        L7b:
            int r12 = r12 + 1
            int r7 = r7 + 1
            goto L20
        L80:
            if (r7 != 0) goto L9d
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Expected leading [0-9] or '-' character but was 0x"
            r1.append(r2)
            java.lang.String r2 = java.lang.Integer.toHexString(r15)
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L9d:
            r6 = 1
        L9e:
            if (r12 != r13) goto Laa
            a.o r1 = r10.c()
            r0.f3a = r1
            a.p.a(r10)
            goto Lac
        Laa:
            r10.b = r12
        Lac:
            if (r6 != 0) goto Lb2
            a.o r1 = r0.f3a
            if (r1 != 0) goto L18
        Lb2:
            long r1 = r0.b
            long r6 = (long) r7
            long r1 = r1 - r6
            r0.b = r1
            if (r5 == 0) goto Lbb
            return r3
        Lbb:
            long r3 = -r3
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: a.c.n():long");
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ac A[EDGE_INSN: B:44:0x00ac->B:40:0x00ac BREAK  A[LOOP:0: B:7:0x0013->B:46:?], SYNTHETIC] */
    @Override // a.e
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public long o() {
        /*
            r14 = this;
            long r0 = r14.b
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 != 0) goto L10
            java.lang.IllegalStateException r14 = new java.lang.IllegalStateException
            java.lang.String r0 = "size == 0"
            r14.<init>(r0)
            throw r14
        L10:
            r0 = 0
            r1 = r0
            r4 = r2
        L13:
            a.o r6 = r14.f3a
            byte[] r7 = r6.f17a
            int r8 = r6.b
            int r9 = r6.c
        L1b:
            if (r8 >= r9) goto L98
            r10 = r7[r8]
            r11 = 48
            if (r10 < r11) goto L2a
            r11 = 57
            if (r10 > r11) goto L2a
            int r11 = r10 + (-48)
            goto L42
        L2a:
            r11 = 97
            if (r10 < r11) goto L37
            r11 = 102(0x66, float:1.43E-43)
            if (r10 > r11) goto L37
            int r11 = r10 + (-97)
        L34:
            int r11 = r11 + 10
            goto L42
        L37:
            r11 = 65
            if (r10 < r11) goto L7a
            r11 = 70
            if (r10 > r11) goto L7a
            int r11 = r10 + (-65)
            goto L34
        L42:
            r12 = -1152921504606846976(0xf000000000000000, double:-3.105036184601418E231)
            long r12 = r12 & r4
            int r12 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r12 == 0) goto L71
            a.c r14 = new a.c
            r14.<init>()
            a.c r14 = r14.m(r4)
            a.c r14 = r14.i(r10)
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Number too large: "
            r1.append(r2)
            java.lang.String r14 = r14.q()
            r1.append(r14)
            java.lang.String r14 = r1.toString()
            r0.<init>(r14)
            throw r0
        L71:
            r10 = 4
            long r4 = r4 << r10
            long r10 = (long) r11
            long r4 = r4 | r10
            int r8 = r8 + 1
            int r0 = r0 + 1
            goto L1b
        L7a:
            if (r0 != 0) goto L97
            java.lang.NumberFormatException r14 = new java.lang.NumberFormatException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Expected leading [0-9a-fA-F] character but was 0x"
            r0.append(r1)
            java.lang.String r1 = java.lang.Integer.toHexString(r10)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r14.<init>(r0)
            throw r14
        L97:
            r1 = 1
        L98:
            if (r8 != r9) goto La4
            a.o r7 = r6.c()
            r14.f3a = r7
            a.p.a(r6)
            goto La6
        La4:
            r6.b = r8
        La6:
            if (r1 != 0) goto Lac
            a.o r6 = r14.f3a
            if (r6 != 0) goto L13
        Lac:
            long r1 = r14.b
            long r6 = (long) r0
            long r1 = r1 - r6
            r14.b = r1
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: a.c.o():long");
    }

    public f p() {
        return new f(s());
    }

    public String q() {
        try {
            return a(this.b, u.f19a);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    @Override // a.e
    public String r() {
        return f(Long.MAX_VALUE);
    }

    @Override // java.nio.channels.ReadableByteChannel
    public int read(ByteBuffer byteBuffer) {
        o oVar = this.f3a;
        if (oVar == null) {
            return -1;
        }
        int iMin = Math.min(byteBuffer.remaining(), oVar.c - oVar.b);
        byteBuffer.put(oVar.f17a, oVar.b, iMin);
        oVar.b += iMin;
        this.b -= iMin;
        if (oVar.b == oVar.c) {
            this.f3a = oVar.c();
            p.a(oVar);
        }
        return iMin;
    }

    @Override // a.s
    public long read(c cVar, long j) {
        if (cVar == null) {
            throw new IllegalArgumentException("sink == null");
        }
        if (j < 0) {
            throw new IllegalArgumentException("byteCount < 0: " + j);
        }
        if (this.b == 0) {
            return -1L;
        }
        if (j > this.b) {
            j = this.b;
        }
        cVar.write(this, j);
        return j;
    }

    @Override // a.e
    public byte[] s() {
        try {
            return h(this.b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    public final void t() {
        try {
            i(this.b);
        } catch (EOFException e) {
            throw new AssertionError(e);
        }
    }

    @Override // a.r
    public t timeout() {
        return t.NONE;
    }

    public String toString() {
        return v().toString();
    }

    /* renamed from: u, reason: merged with bridge method [inline-methods] */
    public c clone() {
        c cVar = new c();
        if (this.b == 0) {
            return cVar;
        }
        cVar.f3a = this.f3a.a();
        o oVar = cVar.f3a;
        o oVar2 = cVar.f3a;
        o oVar3 = cVar.f3a;
        oVar2.g = oVar3;
        oVar.f = oVar3;
        o oVar4 = this.f3a;
        while (true) {
            oVar4 = oVar4.f;
            if (oVar4 == this.f3a) {
                cVar.b = this.b;
                return cVar;
            }
            cVar.f3a.g.a(oVar4.a());
        }
    }

    public final f v() {
        if (this.b <= 2147483647L) {
            return f((int) this.b);
        }
        throw new IllegalArgumentException("size > Integer.MAX_VALUE: " + this.b);
    }

    @Override // java.nio.channels.WritableByteChannel
    public int write(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            throw new IllegalArgumentException("source == null");
        }
        int iRemaining = byteBuffer.remaining();
        int i = iRemaining;
        while (i > 0) {
            o oVarE = e(1);
            int iMin = Math.min(i, 8192 - oVarE.c);
            byteBuffer.get(oVarE.f17a, oVarE.c, iMin);
            i -= iMin;
            oVarE.c += iMin;
        }
        this.b += iRemaining;
        return iRemaining;
    }

    @Override // a.r
    public void write(c cVar, long j) {
        if (cVar == null) {
            throw new IllegalArgumentException("source == null");
        }
        if (cVar == this) {
            throw new IllegalArgumentException("source == this");
        }
        u.a(cVar.b, 0L, j);
        while (j > 0) {
            if (j < cVar.f3a.c - cVar.f3a.b) {
                o oVar = this.f3a != null ? this.f3a.g : null;
                if (oVar != null && oVar.e) {
                    if ((oVar.c + j) - (oVar.d ? 0 : oVar.b) <= PlaybackStateCompat.ACTION_PLAY_FROM_URI) {
                        cVar.f3a.a(oVar, (int) j);
                        cVar.b -= j;
                        this.b += j;
                        return;
                    }
                }
                cVar.f3a = cVar.f3a.a((int) j);
            }
            o oVar2 = cVar.f3a;
            long j2 = oVar2.c - oVar2.b;
            cVar.f3a = oVar2.c();
            if (this.f3a == null) {
                this.f3a = oVar2;
                o oVar3 = this.f3a;
                o oVar4 = this.f3a;
                o oVar5 = this.f3a;
                oVar4.g = oVar5;
                oVar3.f = oVar5;
            } else {
                this.f3a.g.a(oVar2).d();
            }
            cVar.b -= j2;
            this.b += j2;
            j -= j2;
        }
    }
}
