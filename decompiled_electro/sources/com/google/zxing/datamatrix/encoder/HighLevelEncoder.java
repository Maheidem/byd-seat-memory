package com.google.zxing.datamatrix.encoder;

import android.support.v7.widget.ActivityChooserView;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class HighLevelEncoder {
    static final int ASCII_ENCODATION = 0;
    static final int BASE256_ENCODATION = 5;
    static final int C40_ENCODATION = 1;
    static final char C40_UNLATCH = 254;
    static final int EDIFACT_ENCODATION = 4;
    static final char LATCH_TO_ANSIX12 = 238;
    static final char LATCH_TO_BASE256 = 231;
    static final char LATCH_TO_C40 = 230;
    static final char LATCH_TO_EDIFACT = 240;
    static final char LATCH_TO_TEXT = 239;
    private static final char MACRO_05 = 236;
    private static final String MACRO_05_HEADER = "[)>\u001e05\u001d";
    private static final char MACRO_06 = 237;
    private static final String MACRO_06_HEADER = "[)>\u001e06\u001d";
    private static final String MACRO_TRAILER = "\u001e\u0004";
    private static final char PAD = 129;
    static final int TEXT_ENCODATION = 2;
    static final char UPPER_SHIFT = 235;
    static final int X12_ENCODATION = 3;
    static final char X12_UNLATCH = 254;

    private HighLevelEncoder() {
    }

    public static int determineConsecutiveDigitCount(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        if (i < length) {
            loop0: while (true) {
                char cCharAt = charSequence.charAt(i);
                while (isDigit(cCharAt) && i < length) {
                    i2++;
                    i++;
                    if (i < length) {
                        break;
                    }
                }
            }
        }
        return i2;
    }

    public static String encodeHighLevel(String str) {
        return encodeHighLevel(str, SymbolShapeHint.FORCE_NONE, null, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b8 A[LOOP:1: B:26:0x00b2->B:28:0x00b8, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String encodeHighLevel(java.lang.String r6, com.google.zxing.datamatrix.encoder.SymbolShapeHint r7, com.google.zxing.Dimension r8, com.google.zxing.Dimension r9) {
        /*
            r0 = 6
            com.google.zxing.datamatrix.encoder.Encoder[] r0 = new com.google.zxing.datamatrix.encoder.Encoder[r0]
            com.google.zxing.datamatrix.encoder.ASCIIEncoder r1 = new com.google.zxing.datamatrix.encoder.ASCIIEncoder
            r1.<init>()
            r2 = 0
            r0[r2] = r1
            com.google.zxing.datamatrix.encoder.C40Encoder r1 = new com.google.zxing.datamatrix.encoder.C40Encoder
            r1.<init>()
            r3 = 1
            r0[r3] = r1
            com.google.zxing.datamatrix.encoder.TextEncoder r1 = new com.google.zxing.datamatrix.encoder.TextEncoder
            r1.<init>()
            r4 = 2
            r0[r4] = r1
            com.google.zxing.datamatrix.encoder.X12Encoder r1 = new com.google.zxing.datamatrix.encoder.X12Encoder
            r1.<init>()
            r5 = 3
            r0[r5] = r1
            com.google.zxing.datamatrix.encoder.EdifactEncoder r1 = new com.google.zxing.datamatrix.encoder.EdifactEncoder
            r1.<init>()
            r5 = 4
            r0[r5] = r1
            com.google.zxing.datamatrix.encoder.Base256Encoder r1 = new com.google.zxing.datamatrix.encoder.Base256Encoder
            r1.<init>()
            r5 = 5
            r0[r5] = r1
            com.google.zxing.datamatrix.encoder.EncoderContext r1 = new com.google.zxing.datamatrix.encoder.EncoderContext
            r1.<init>(r6)
            r1.setSymbolShape(r7)
            r1.setSizeConstraints(r8, r9)
            java.lang.String r7 = "[)>\u001e05\u001d"
            boolean r7 = r6.startsWith(r7)
            if (r7 == 0) goto L5d
            java.lang.String r7 = "\u001e\u0004"
            boolean r7 = r6.endsWith(r7)
            if (r7 == 0) goto L5d
            r6 = 236(0xec, float:3.31E-43)
        L50:
            r1.writeCodeword(r6)
            r1.setSkipAtEnd(r4)
            int r6 = r1.pos
            int r6 = r6 + 7
            r1.pos = r6
            goto L70
        L5d:
            java.lang.String r7 = "[)>\u001e06\u001d"
            boolean r7 = r6.startsWith(r7)
            if (r7 == 0) goto L70
            java.lang.String r7 = "\u001e\u0004"
            boolean r6 = r6.endsWith(r7)
            if (r6 == 0) goto L70
            r6 = 237(0xed, float:3.32E-43)
            goto L50
        L70:
            boolean r6 = r1.hasMoreCharacters()
            if (r6 == 0) goto L89
            r6 = r0[r2]
            r6.encode(r1)
            int r6 = r1.getNewEncoding()
            if (r6 < 0) goto L70
            int r2 = r1.getNewEncoding()
            r1.resetEncoderSignal()
            goto L70
        L89:
            int r6 = r1.getCodewordCount()
            r1.updateSymbolInfo()
            com.google.zxing.datamatrix.encoder.SymbolInfo r7 = r1.getSymbolInfo()
            int r7 = r7.getDataCapacity()
            if (r6 >= r7) goto La3
            if (r2 == 0) goto La3
            if (r2 == r5) goto La3
            r6 = 254(0xfe, float:3.56E-43)
            r1.writeCodeword(r6)
        La3:
            java.lang.StringBuilder r6 = r1.getCodewords()
            int r8 = r6.length()
            r9 = 129(0x81, float:1.81E-43)
            if (r8 >= r7) goto Lb2
            r6.append(r9)
        Lb2:
            int r8 = r6.length()
            if (r8 >= r7) goto Lc5
            int r8 = r6.length()
            int r8 = r8 + r3
            char r8 = randomize253State(r9, r8)
            r6.append(r8)
            goto Lb2
        Lc5:
            java.lang.StringBuilder r6 = r1.getCodewords()
            java.lang.String r6 = r6.toString()
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.encoder.HighLevelEncoder.encodeHighLevel(java.lang.String, com.google.zxing.datamatrix.encoder.SymbolShapeHint, com.google.zxing.Dimension, com.google.zxing.Dimension):java.lang.String");
    }

    private static int findMinimums(float[] fArr, int[] iArr, int i, byte[] bArr) {
        Arrays.fill(bArr, (byte) 0);
        int i2 = i;
        for (int i3 = 0; i3 < 6; i3++) {
            iArr[i3] = (int) Math.ceil(fArr[i3]);
            int i4 = iArr[i3];
            if (i2 > i4) {
                Arrays.fill(bArr, (byte) 0);
                i2 = i4;
            }
            if (i2 == i4) {
                bArr[i3] = (byte) (bArr[i3] + 1);
            }
        }
        return i2;
    }

    private static int getMinimumCount(byte[] bArr) {
        int i = 0;
        for (int i2 = 0; i2 < 6; i2++) {
            i += bArr[i2];
        }
        return i;
    }

    static void illegalCharacter(char c) {
        String hexString = Integer.toHexString(c);
        throw new IllegalArgumentException("Illegal character: " + c + " (0x" + ("0000".substring(0, 4 - hexString.length()) + hexString) + ')');
    }

    static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    static boolean isExtendedASCII(char c) {
        return c >= 128 && c <= 255;
    }

    private static boolean isNativeC40(char c) {
        if (c == ' ') {
            return true;
        }
        if (c < '0' || c > '9') {
            return c >= 'A' && c <= 'Z';
        }
        return true;
    }

    private static boolean isNativeEDIFACT(char c) {
        return c >= ' ' && c <= '^';
    }

    private static boolean isNativeText(char c) {
        if (c == ' ') {
            return true;
        }
        if (c < '0' || c > '9') {
            return c >= 'a' && c <= 'z';
        }
        return true;
    }

    private static boolean isNativeX12(char c) {
        if (isX12TermSep(c) || c == ' ') {
            return true;
        }
        if (c < '0' || c > '9') {
            return c >= 'A' && c <= 'Z';
        }
        return true;
    }

    private static boolean isSpecialB256(char c) {
        return false;
    }

    private static boolean isX12TermSep(char c) {
        return c == '\r' || c == '*' || c == '>';
    }

    static int lookAheadTest(CharSequence charSequence, int i, int i2) {
        float[] fArr;
        char c;
        if (i >= charSequence.length()) {
            return i2;
        }
        if (i2 == 0) {
            fArr = new float[]{0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.25f};
        } else {
            float[] fArr2 = {1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.25f};
            fArr2[i2] = 0.0f;
            fArr = fArr2;
        }
        int i3 = 0;
        while (true) {
            int i4 = i + i3;
            if (i4 == charSequence.length()) {
                byte[] bArr = new byte[6];
                int[] iArr = new int[6];
                int iFindMinimums = findMinimums(fArr, iArr, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, bArr);
                int minimumCount = getMinimumCount(bArr);
                if (iArr[0] == iFindMinimums) {
                    return 0;
                }
                if (minimumCount == 1 && bArr[5] > 0) {
                    return 5;
                }
                if (minimumCount == 1 && bArr[4] > 0) {
                    return 4;
                }
                if (minimumCount != 1 || bArr[2] <= 0) {
                    return (minimumCount != 1 || bArr[3] <= 0) ? 1 : 3;
                }
                return 2;
            }
            char cCharAt = charSequence.charAt(i4);
            i3++;
            if (isDigit(cCharAt)) {
                fArr[0] = fArr[0] + 0.5f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[0] = (float) Math.ceil(fArr[0]);
                fArr[0] = fArr[0] + 2.0f;
            } else {
                fArr[0] = (float) Math.ceil(fArr[0]);
                fArr[0] = fArr[0] + 1.0f;
            }
            if (isNativeC40(cCharAt)) {
                fArr[1] = fArr[1] + 0.6666667f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[1] = fArr[1] + 2.6666667f;
            } else {
                fArr[1] = fArr[1] + 1.3333334f;
            }
            if (isNativeText(cCharAt)) {
                fArr[2] = fArr[2] + 0.6666667f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[2] = fArr[2] + 2.6666667f;
            } else {
                fArr[2] = fArr[2] + 1.3333334f;
            }
            if (isNativeX12(cCharAt)) {
                fArr[3] = fArr[3] + 0.6666667f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[3] = fArr[3] + 4.3333335f;
            } else {
                fArr[3] = fArr[3] + 3.3333333f;
            }
            if (isNativeEDIFACT(cCharAt)) {
                fArr[4] = fArr[4] + 0.75f;
            } else if (isExtendedASCII(cCharAt)) {
                fArr[4] = fArr[4] + 4.25f;
            } else {
                fArr[4] = fArr[4] + 3.25f;
            }
            if (isSpecialB256(cCharAt)) {
                c = 5;
                fArr[5] = fArr[5] + 4.0f;
            } else {
                c = 5;
                fArr[5] = fArr[5] + 1.0f;
            }
            if (i3 >= 4) {
                int[] iArr2 = new int[6];
                byte[] bArr2 = new byte[6];
                findMinimums(fArr, iArr2, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, bArr2);
                int minimumCount2 = getMinimumCount(bArr2);
                if (iArr2[0] < iArr2[c] && iArr2[0] < iArr2[1] && iArr2[0] < iArr2[2] && iArr2[0] < iArr2[3] && iArr2[0] < iArr2[4]) {
                    return 0;
                }
                if (iArr2[5] < iArr2[0] || bArr2[1] + bArr2[2] + bArr2[3] + bArr2[4] == 0) {
                    return 5;
                }
                if (minimumCount2 == 1 && bArr2[4] > 0) {
                    return 4;
                }
                if (minimumCount2 == 1 && bArr2[2] > 0) {
                    return 2;
                }
                if (minimumCount2 == 1 && bArr2[3] > 0) {
                    return 3;
                }
                if (iArr2[1] + 1 < iArr2[0] && iArr2[1] + 1 < iArr2[5] && iArr2[1] + 1 < iArr2[4] && iArr2[1] + 1 < iArr2[2]) {
                    if (iArr2[1] < iArr2[3]) {
                        return 1;
                    }
                    if (iArr2[1] == iArr2[3]) {
                        for (int i5 = i + i3 + 1; i5 < charSequence.length(); i5++) {
                            char cCharAt2 = charSequence.charAt(i5);
                            if (isX12TermSep(cCharAt2)) {
                                return 3;
                            }
                            if (!isNativeX12(cCharAt2)) {
                                break;
                            }
                        }
                        return 1;
                    }
                }
            }
        }
    }

    private static char randomize253State(char c, int i) {
        int i2 = c + ((i * 149) % 253) + 1;
        if (i2 > 254) {
            i2 -= 254;
        }
        return (char) i2;
    }
}
