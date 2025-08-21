package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* loaded from: classes.dex */
public class Code93Writer extends OneDimensionalCodeWriter {
    protected static int appendPattern(boolean[] zArr, int i, int[] iArr, boolean z) {
        int length = iArr.length;
        int i2 = i;
        int i3 = 0;
        while (i3 < length) {
            int i4 = i2 + 1;
            zArr[i2] = iArr[i3] != 0;
            i3++;
            i2 = i4;
        }
        return 9;
    }

    private static int computeChecksumIndex(String str, int i) {
        int iIndexOf = 0;
        int i2 = 1;
        for (int length = str.length() - 1; length >= 0; length--) {
            iIndexOf += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(length)) * i2;
            i2++;
            if (i2 > i) {
                i2 = 1;
            }
        }
        return iIndexOf % 47;
    }

    private static void toIntArray(int i, int[] iArr) {
        for (int i2 = 0; i2 < 9; i2++) {
            int i3 = 1;
            if (((1 << (8 - i2)) & i) == 0) {
                i3 = 0;
            }
            iArr[i2] = i3;
        }
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) {
        if (barcodeFormat == BarcodeFormat.CODE_93) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_93, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) {
        int length = str.length();
        if (length > 80) {
            throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
        }
        int[] iArr = new int[9];
        boolean[] zArr = new boolean[((str.length() + 2 + 2) * 9) + 1];
        toIntArray(Code93Reader.CHARACTER_ENCODINGS[47], iArr);
        int iAppendPattern = appendPattern(zArr, 0, iArr, true);
        for (int i = 0; i < length; i++) {
            toIntArray(Code93Reader.CHARACTER_ENCODINGS["0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(str.charAt(i))], iArr);
            iAppendPattern += appendPattern(zArr, iAppendPattern, iArr, true);
        }
        int iComputeChecksumIndex = computeChecksumIndex(str, 20);
        toIntArray(Code93Reader.CHARACTER_ENCODINGS[iComputeChecksumIndex], iArr);
        int iAppendPattern2 = iAppendPattern + appendPattern(zArr, iAppendPattern, iArr, true);
        toIntArray(Code93Reader.CHARACTER_ENCODINGS[computeChecksumIndex(str + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".charAt(iComputeChecksumIndex), 15)], iArr);
        int iAppendPattern3 = iAppendPattern2 + appendPattern(zArr, iAppendPattern2, iArr, true);
        toIntArray(Code93Reader.CHARACTER_ENCODINGS[47], iArr);
        zArr[iAppendPattern3 + appendPattern(zArr, iAppendPattern3, iArr, true)] = true;
        return zArr;
    }
}
