package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public final class Code128Writer extends OneDimensionalCodeWriter {
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_B = 100;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final char ESCAPE_FNC_1 = 241;
    private static final char ESCAPE_FNC_2 = 242;
    private static final char ESCAPE_FNC_3 = 243;
    private static final char ESCAPE_FNC_4 = 244;

    private enum CType {
        UNCODABLE,
        ONE_DIGIT,
        TWO_DIGITS,
        FNC_1
    }

    private static int chooseCode(CharSequence charSequence, int i, int i2) {
        CType cTypeFindCType;
        CType cTypeFindCType2;
        CType cTypeFindCType3 = findCType(charSequence, i);
        if (cTypeFindCType3 == CType.UNCODABLE || cTypeFindCType3 == CType.ONE_DIGIT) {
            return 100;
        }
        if (i2 == 99) {
            return i2;
        }
        if (i2 != 100) {
            if (cTypeFindCType3 == CType.FNC_1) {
                cTypeFindCType3 = findCType(charSequence, i + 1);
            }
            return cTypeFindCType3 == CType.TWO_DIGITS ? 99 : 100;
        }
        if (cTypeFindCType3 == CType.FNC_1 || (cTypeFindCType = findCType(charSequence, i + 2)) == CType.UNCODABLE || cTypeFindCType == CType.ONE_DIGIT) {
            return i2;
        }
        if (cTypeFindCType == CType.FNC_1) {
            return findCType(charSequence, i + 3) == CType.TWO_DIGITS ? 99 : 100;
        }
        int i3 = i + 4;
        while (true) {
            cTypeFindCType2 = findCType(charSequence, i3);
            if (cTypeFindCType2 != CType.TWO_DIGITS) {
                break;
            }
            i3 += 2;
        }
        return cTypeFindCType2 == CType.ONE_DIGIT ? 100 : 99;
    }

    private static CType findCType(CharSequence charSequence, int i) {
        int length = charSequence.length();
        if (i >= length) {
            return CType.UNCODABLE;
        }
        char cCharAt = charSequence.charAt(i);
        if (cCharAt == 241) {
            return CType.FNC_1;
        }
        if (cCharAt < '0' || cCharAt > '9') {
            return CType.UNCODABLE;
        }
        int i2 = i + 1;
        if (i2 >= length) {
            return CType.ONE_DIGIT;
        }
        char cCharAt2 = charSequence.charAt(i2);
        return (cCharAt2 < '0' || cCharAt2 > '9') ? CType.ONE_DIGIT : CType.TWO_DIGITS;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) {
        if (barcodeFormat == BarcodeFormat.CODE_128) {
            return super.encode(str, barcodeFormat, i, i2, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_128, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) throws NumberFormatException {
        int length = str.length();
        if (length <= 0 || length > 80) {
            throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + length);
        }
        int iAppendPattern = 0;
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt < ' ' || cCharAt > '~') {
                switch (cCharAt) {
                    case 241:
                    case 242:
                    case 243:
                    case 244:
                        break;
                    default:
                        throw new IllegalArgumentException("Bad character in input: " + cCharAt);
                }
            }
        }
        ArrayList<int[]> arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1;
        while (i2 < length) {
            int iChooseCode = chooseCode(str, i2, i3);
            int iCharAt = 100;
            if (iChooseCode == i3) {
                switch (str.charAt(i2)) {
                    case 241:
                        iCharAt = 102;
                        break;
                    case 242:
                        iCharAt = 97;
                        break;
                    case 243:
                        iCharAt = 96;
                        break;
                    case 244:
                        break;
                    default:
                        if (i3 == 100) {
                            iCharAt = str.charAt(i2) - ' ';
                            break;
                        } else {
                            iCharAt = Integer.parseInt(str.substring(i2, i2 + 2));
                            i2++;
                            break;
                        }
                }
                i2++;
            } else {
                iCharAt = i3 == 0 ? iChooseCode == 100 ? 104 : 105 : iChooseCode;
                i3 = iChooseCode;
            }
            arrayList.add(Code128Reader.CODE_PATTERNS[iCharAt]);
            i4 += iCharAt * i5;
            if (i2 != 0) {
                i5++;
            }
        }
        arrayList.add(Code128Reader.CODE_PATTERNS[i4 % 103]);
        arrayList.add(Code128Reader.CODE_PATTERNS[106]);
        int i6 = 0;
        for (int[] iArr : arrayList) {
            int i7 = i6;
            for (int i8 : iArr) {
                i7 += i8;
            }
            i6 = i7;
        }
        boolean[] zArr = new boolean[i6];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            iAppendPattern += appendPattern(zArr, iAppendPattern, (int[]) it.next(), true);
        }
        return zArr;
    }
}
