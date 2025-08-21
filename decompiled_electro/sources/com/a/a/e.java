package com.a.a;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    public static byte[] f205a;

    static final class a {

        /* renamed from: a, reason: collision with root package name */
        public int f206a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public byte[] g;

        a() {
        }

        public static a a(InputStream inputStream) throws IOException {
            a aVar = new a();
            ByteBuffer byteBufferOrder = ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN);
            int i = 0;
            int i2 = 0;
            do {
                int i3 = inputStream.read(byteBufferOrder.array(), i2, 24 - i2);
                if (i3 < 0) {
                    throw new IOException("Stream closed");
                }
                i2 += i3;
            } while (i2 < 24);
            aVar.f206a = byteBufferOrder.getInt();
            aVar.b = byteBufferOrder.getInt();
            aVar.c = byteBufferOrder.getInt();
            aVar.d = byteBufferOrder.getInt();
            aVar.e = byteBufferOrder.getInt();
            aVar.f = byteBufferOrder.getInt();
            if (aVar.d != 0) {
                aVar.g = new byte[aVar.d];
                do {
                    int i4 = inputStream.read(aVar.g, i, aVar.d - i);
                    if (i4 < 0) {
                        throw new IOException("Stream closed");
                    }
                    i += i4;
                } while (i < aVar.d);
            }
            return aVar;
        }
    }

    static {
        try {
            f205a = "host::\u0000".getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
        }
    }

    private static int a(byte[] bArr) {
        int i = 0;
        for (int i2 : bArr) {
            if (i2 < 0) {
                i2 += 256;
            }
            i += i2;
        }
        return i;
    }

    public static boolean a(a aVar) {
        if (aVar.f206a != (~aVar.f)) {
            return false;
        }
        return aVar.d == 0 || a(aVar.g) == aVar.e;
    }

    public static byte[] a() {
        return a(1314410051, 16777216, 4096, f205a);
    }

    public static byte[] a(int i, int i2) {
        return a(1163086915, i, i2, null);
    }

    public static byte[] a(int i, int i2, int i3, byte[] bArr) {
        int iA;
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(bArr != null ? 24 + bArr.length : 24).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.putInt(i);
        byteBufferOrder.putInt(i2);
        byteBufferOrder.putInt(i3);
        if (bArr != null) {
            byteBufferOrder.putInt(bArr.length);
            iA = a(bArr);
        } else {
            iA = 0;
            byteBufferOrder.putInt(0);
        }
        byteBufferOrder.putInt(iA);
        byteBufferOrder.putInt(~i);
        if (bArr != null) {
            byteBufferOrder.put(bArr);
        }
        return byteBufferOrder.array();
    }

    public static byte[] a(int i, String str) {
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(str.length() + 1);
        byteBufferAllocate.put(str.getBytes("UTF-8"));
        byteBufferAllocate.put((byte) 0);
        return a(1313165391, i, 0, byteBufferAllocate.array());
    }

    public static byte[] a(int i, byte[] bArr) {
        return a(1213486401, i, 0, bArr);
    }

    public static byte[] b(int i, int i2) {
        return a(1497451343, i, i2, null);
    }
}
