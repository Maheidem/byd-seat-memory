package com.google.zxing.common;

/* loaded from: classes.dex */
public final class BitSource {
    private int bitOffset;
    private int byteOffset;
    private final byte[] bytes;

    public BitSource(byte[] bArr) {
        this.bytes = bArr;
    }

    public int available() {
        return (8 * (this.bytes.length - this.byteOffset)) - this.bitOffset;
    }

    public int getBitOffset() {
        return this.bitOffset;
    }

    public int getByteOffset() {
        return this.byteOffset;
    }

    public int readBits(int i) {
        int i2;
        if (i <= 0 || i > 32 || i > available()) {
            throw new IllegalArgumentException(String.valueOf(i));
        }
        if (this.bitOffset > 0) {
            int i3 = 8 - this.bitOffset;
            int i4 = i < i3 ? i : i3;
            int i5 = i3 - i4;
            i2 = (((255 >> (8 - i4)) << i5) & this.bytes[this.byteOffset]) >> i5;
            i -= i4;
            this.bitOffset += i4;
            if (this.bitOffset == 8) {
                this.bitOffset = 0;
                this.byteOffset++;
            }
        } else {
            i2 = 0;
        }
        if (i <= 0) {
            return i2;
        }
        while (i >= 8) {
            i2 = (i2 << 8) | (this.bytes[this.byteOffset] & 255);
            this.byteOffset++;
            i -= 8;
        }
        if (i <= 0) {
            return i2;
        }
        int i6 = 8 - i;
        int i7 = (i2 << i) | ((((255 >> i6) << i6) & this.bytes[this.byteOffset]) >> i6);
        this.bitOffset += i;
        return i7;
    }
}
