package a;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class f implements Serializable, Comparable<f> {

    /* renamed from: a, reason: collision with root package name */
    static final char[] f6a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final f b = a(new byte[0]);
    final byte[] c;
    transient int d;
    transient String e;

    f(byte[] bArr) {
        this.c = bArr;
    }

    private static int a(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        char c2 = 'a';
        if (c < 'a' || c > 'f') {
            c2 = 'A';
            if (c < 'A' || c > 'F') {
                throw new IllegalArgumentException("Unexpected hex digit: " + c);
            }
        }
        return (c - c2) + 10;
    }

    static int a(String str, int i) {
        int length = str.length();
        int iCharCount = 0;
        int i2 = 0;
        while (iCharCount < length) {
            if (i2 == i) {
                return iCharCount;
            }
            int iCodePointAt = str.codePointAt(iCharCount);
            if ((Character.isISOControl(iCodePointAt) && iCodePointAt != 10 && iCodePointAt != 13) || iCodePointAt == 65533) {
                return -1;
            }
            i2++;
            iCharCount += Character.charCount(iCodePointAt);
        }
        return str.length();
    }

    public static f a(String str) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        }
        f fVar = new f(str.getBytes(u.f19a));
        fVar.e = str;
        return fVar;
    }

    public static f a(String str, Charset charset) {
        if (str == null) {
            throw new IllegalArgumentException("s == null");
        }
        if (charset == null) {
            throw new IllegalArgumentException("charset == null");
        }
        return new f(str.getBytes(charset));
    }

    public static f a(byte... bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("data == null");
        }
        return new f((byte[]) bArr.clone());
    }

    @Nullable
    public static f b(String str) {
        if (str == null) {
            throw new IllegalArgumentException("base64 == null");
        }
        byte[] bArrA = b.a(str);
        if (bArrA != null) {
            return new f(bArrA);
        }
        return null;
    }

    public static f c(String str) {
        if (str == null) {
            throw new IllegalArgumentException("hex == null");
        }
        if (str.length() % 2 != 0) {
            throw new IllegalArgumentException("Unexpected hex string: " + str);
        }
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) ((a(str.charAt(i2)) << 4) + a(str.charAt(i2 + 1)));
        }
        return a(bArr);
    }

    private f d(String str) {
        try {
            return a(MessageDigest.getInstance(str).digest(this.c));
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public byte a(int i) {
        return this.c[i];
    }

    public f a(int i, int i2) {
        if (i < 0) {
            throw new IllegalArgumentException("beginIndex < 0");
        }
        if (i2 > this.c.length) {
            throw new IllegalArgumentException("endIndex > length(" + this.c.length + ")");
        }
        int i3 = i2 - i;
        if (i3 < 0) {
            throw new IllegalArgumentException("endIndex < beginIndex");
        }
        if (i == 0 && i2 == this.c.length) {
            return this;
        }
        byte[] bArr = new byte[i3];
        System.arraycopy(this.c, i, bArr, 0, i3);
        return new f(bArr);
    }

    public String a() {
        String str = this.e;
        if (str != null) {
            return str;
        }
        String str2 = new String(this.c, u.f19a);
        this.e = str2;
        return str2;
    }

    void a(c cVar) {
        cVar.c(this.c, 0, this.c.length);
    }

    public boolean a(int i, f fVar, int i2, int i3) {
        return fVar.a(i2, this.c, i, i3);
    }

    public boolean a(int i, byte[] bArr, int i2, int i3) {
        return i >= 0 && i <= this.c.length - i3 && i2 >= 0 && i2 <= bArr.length - i3 && u.a(this.c, i, bArr, i2, i3);
    }

    public final boolean a(f fVar) {
        return a(0, fVar, 0, fVar.h());
    }

    @Override // java.lang.Comparable
    /* renamed from: b, reason: merged with bridge method [inline-methods] */
    public int compareTo(f fVar) {
        int iH = h();
        int iH2 = fVar.h();
        int iMin = Math.min(iH, iH2);
        for (int i = 0; i < iMin; i++) {
            int iA = a(i) & 255;
            int iA2 = fVar.a(i) & 255;
            if (iA != iA2) {
                return iA < iA2 ? -1 : 1;
            }
        }
        if (iH == iH2) {
            return 0;
        }
        return iH < iH2 ? -1 : 1;
    }

    public String b() {
        return b.a(this.c);
    }

    public f c() {
        return d("MD5");
    }

    public f d() {
        return d("SHA-1");
    }

    public f e() {
        return d("SHA-256");
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof f) {
            f fVar = (f) obj;
            if (fVar.h() == this.c.length && fVar.a(0, this.c, 0, this.c.length)) {
                return true;
            }
        }
        return false;
    }

    public String f() {
        char[] cArr = new char[this.c.length * 2];
        int i = 0;
        for (byte b2 : this.c) {
            int i2 = i + 1;
            cArr[i] = f6a[(b2 >> 4) & 15];
            i = i2 + 1;
            cArr[i2] = f6a[b2 & 15];
        }
        return new String(cArr);
    }

    public f g() {
        for (int i = 0; i < this.c.length; i++) {
            byte b2 = this.c[i];
            if (b2 >= 65 && b2 <= 90) {
                byte[] bArr = (byte[]) this.c.clone();
                bArr[i] = (byte) (b2 + 32);
                for (int i2 = i + 1; i2 < bArr.length; i2++) {
                    byte b3 = bArr[i2];
                    if (b3 >= 65 && b3 <= 90) {
                        bArr[i2] = (byte) (b3 + 32);
                    }
                }
                return new f(bArr);
            }
        }
        return this;
    }

    public int h() {
        return this.c.length;
    }

    public int hashCode() {
        int i = this.d;
        if (i != 0) {
            return i;
        }
        int iHashCode = Arrays.hashCode(this.c);
        this.d = iHashCode;
        return iHashCode;
    }

    public byte[] i() {
        return (byte[]) this.c.clone();
    }

    byte[] j() {
        return this.c;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public String toString() {
        StringBuilder sb;
        String str;
        if (this.c.length == 0) {
            return "[size=0]";
        }
        String strA = a();
        int iA = a(strA, 64);
        if (iA == -1) {
            if (this.c.length <= 64) {
                sb = new StringBuilder();
                sb.append("[hex=");
                sb.append(f());
                str = "]";
            } else {
                sb = new StringBuilder();
                sb.append("[size=");
                sb.append(this.c.length);
                sb.append(" hex=");
                sb.append(a(0, 64).f());
                str = "…]";
            }
            sb.append(str);
            return sb.toString();
        }
        String strReplace = strA.substring(0, iA).replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r");
        if (iA >= strA.length()) {
            return "[text=" + strReplace + "]";
        }
        return "[size=" + this.c.length + " text=" + strReplace + "…]";
    }
}
