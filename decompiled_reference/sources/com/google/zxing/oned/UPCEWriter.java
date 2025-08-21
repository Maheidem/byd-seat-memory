package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* loaded from: classes.dex */
public final class UPCEWriter extends UPCEANWriter {
    private static final int CODE_WIDTH = 51;

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) {
        if (barcodeFormat == BarcodeFormat.UPC_E) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode UPC_E, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) throws NumberFormatException {
        if (str.length() != 8) {
            throw new IllegalArgumentException("Requested contents should be 8 digits long, but got " + str.length());
        }
        int i = UPCEReader.CHECK_DIGIT_ENCODINGS[Integer.parseInt(str.substring(7, 8))];
        boolean[] zArr = new boolean[51];
        int iAppendPattern = appendPattern(zArr, 0, UPCEANReader.START_END_PATTERN, true) + 0;
        int i2 = 1;
        while (i2 <= 6) {
            int i3 = i2 + 1;
            int i4 = Integer.parseInt(str.substring(i2, i3));
            if (((i >> (6 - i2)) & 1) == 1) {
                i4 += 10;
            }
            iAppendPattern += appendPattern(zArr, iAppendPattern, UPCEANReader.L_AND_G_PATTERNS[i4], false);
            i2 = i3;
        }
        appendPattern(zArr, iAppendPattern, UPCEANReader.END_PATTERN, false);
        return zArr;
    }
}
