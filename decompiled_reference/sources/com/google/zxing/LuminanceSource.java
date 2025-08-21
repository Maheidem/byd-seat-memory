package com.google.zxing;

/* loaded from: classes.dex */
public abstract class LuminanceSource {
    private final int height;
    private final int width;

    protected LuminanceSource(int i, int i2) {
        this.width = i;
        this.height = i2;
    }

    public LuminanceSource crop(int i, int i2, int i3, int i4) {
        throw new UnsupportedOperationException("This luminance source does not support cropping.");
    }

    public final int getHeight() {
        return this.height;
    }

    public abstract byte[] getMatrix();

    public abstract byte[] getRow(int i, byte[] bArr);

    public final int getWidth() {
        return this.width;
    }

    public LuminanceSource invert() {
        return new InvertedLuminanceSource(this);
    }

    public boolean isCropSupported() {
        return false;
    }

    public boolean isRotateSupported() {
        return false;
    }

    public LuminanceSource rotateCounterClockwise() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 90 degrees.");
    }

    public LuminanceSource rotateCounterClockwise45() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 45 degrees.");
    }

    public final String toString() {
        byte[] bArr = new byte[this.width];
        StringBuilder sb = new StringBuilder(this.height * (this.width + 1));
        byte[] row = bArr;
        for (int i = 0; i < this.height; i++) {
            row = getRow(i, row);
            for (int i2 = 0; i2 < this.width; i2++) {
                int i3 = row[i2] & 255;
                sb.append(i3 < 64 ? '#' : i3 < 128 ? '+' : i3 < 192 ? '.' : ' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
