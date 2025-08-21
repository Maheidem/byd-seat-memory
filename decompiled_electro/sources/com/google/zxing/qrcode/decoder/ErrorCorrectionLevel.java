package com.google.zxing.qrcode.decoder;

/* loaded from: classes.dex */
public enum ErrorCorrectionLevel {
    L(1),
    M(0),
    Q(3),
    H(2);

    private final int bits;
    private static final ErrorCorrectionLevel[] FOR_BITS = {M, L, H, Q};

    ErrorCorrectionLevel(int i) {
        this.bits = i;
    }

    public static ErrorCorrectionLevel forBits(int i) {
        if (i < 0 || i >= FOR_BITS.length) {
            throw new IllegalArgumentException();
        }
        return FOR_BITS[i];
    }

    public int getBits() {
        return this.bits;
    }
}
