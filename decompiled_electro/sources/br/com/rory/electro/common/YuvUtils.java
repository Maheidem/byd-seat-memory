package br.com.rory.electro.common;

/* loaded from: classes.dex */
public class YuvUtils {
    static {
        System.loadLibrary("native-lib");
    }

    public static native int cropNV21ToI420(byte[] bArr, int i, int i2, int i3, int i4, int i5, int i6, byte[] bArr2);
}
