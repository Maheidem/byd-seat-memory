package com.google.zxing.aztec.encoder;

import android.support.design.R;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;

/* loaded from: classes.dex */
public final class Encoder {
    public static final int DEFAULT_AZTEC_LAYERS = 0;
    public static final int DEFAULT_EC_PERCENT = 33;
    private static final int MAX_NB_BITS = 32;
    private static final int MAX_NB_BITS_COMPACT = 4;
    private static final int[] WORD_SIZE = {4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

    private Encoder() {
    }

    private static int[] bitsToWords(BitArray bitArray, int i, int i2) {
        int[] iArr = new int[i2];
        int size = bitArray.getSize() / i;
        for (int i3 = 0; i3 < size; i3++) {
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                i4 |= bitArray.get((i3 * i) + i5) ? 1 << ((i - i5) - 1) : 0;
            }
            iArr[i3] = i4;
        }
        return iArr;
    }

    private static void drawBullsEye(BitMatrix bitMatrix, int i, int i2) {
        for (int i3 = 0; i3 < i2; i3 += 2) {
            int i4 = i - i3;
            int i5 = i4;
            while (true) {
                int i6 = i + i3;
                if (i5 <= i6) {
                    bitMatrix.set(i5, i4);
                    bitMatrix.set(i5, i6);
                    bitMatrix.set(i4, i5);
                    bitMatrix.set(i6, i5);
                    i5++;
                }
            }
        }
        int i7 = i - i2;
        bitMatrix.set(i7, i7);
        int i8 = i7 + 1;
        bitMatrix.set(i8, i7);
        bitMatrix.set(i7, i8);
        int i9 = i + i2;
        bitMatrix.set(i9, i7);
        bitMatrix.set(i9, i8);
        bitMatrix.set(i9, i9 - 1);
    }

    private static void drawModeMessage(BitMatrix bitMatrix, boolean z, int i, BitArray bitArray) {
        int i2 = i / 2;
        int i3 = 0;
        if (z) {
            while (i3 < 7) {
                int i4 = (i2 - 3) + i3;
                if (bitArray.get(i3)) {
                    bitMatrix.set(i4, i2 - 5);
                }
                if (bitArray.get(i3 + 7)) {
                    bitMatrix.set(i2 + 5, i4);
                }
                if (bitArray.get(20 - i3)) {
                    bitMatrix.set(i4, i2 + 5);
                }
                if (bitArray.get(27 - i3)) {
                    bitMatrix.set(i2 - 5, i4);
                }
                i3++;
            }
            return;
        }
        while (i3 < 10) {
            int i5 = (i2 - 5) + i3 + (i3 / 5);
            if (bitArray.get(i3)) {
                bitMatrix.set(i5, i2 - 7);
            }
            if (bitArray.get(i3 + 10)) {
                bitMatrix.set(i2 + 7, i5);
            }
            if (bitArray.get(29 - i3)) {
                bitMatrix.set(i5, i2 + 7);
            }
            if (bitArray.get(39 - i3)) {
                bitMatrix.set(i2 - 7, i5);
            }
            i3++;
        }
    }

    public static AztecCode encode(byte[] bArr) {
        return encode(bArr, 33, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static AztecCode encode(byte[] bArr, int i, int i2) {
        int i3;
        int i4;
        int i5;
        BitArray bitArrayStuffBits;
        boolean z;
        int i6;
        BitArray bitArrayEncode = new HighLevelEncoder(bArr).encode();
        int size = ((bitArrayEncode.getSize() * i) / 100) + 11;
        int size2 = bitArrayEncode.getSize() + size;
        int i7 = 0;
        if (i2 == 0) {
            BitArray bitArrayStuffBits2 = null;
            int i8 = 0;
            int i9 = 0;
            while (i8 <= 32) {
                boolean z2 = i8 <= 3 ? 1 : i7;
                i3 = z2 != 0 ? i8 + 1 : i8;
                i4 = totalBitsInLayer(i3, z2);
                if (size2 <= i4) {
                    if (i9 != WORD_SIZE[i3]) {
                        i9 = WORD_SIZE[i3];
                        bitArrayStuffBits2 = stuffBits(bitArrayEncode, i9);
                    }
                    int i10 = i4 - (i4 % i9);
                    if ((z2 == 0 || bitArrayStuffBits2.getSize() <= (i9 << 6)) && bitArrayStuffBits2.getSize() + size <= i10) {
                        i5 = i9;
                        bitArrayStuffBits = bitArrayStuffBits2;
                        z = z2;
                    }
                }
                i8++;
                i7 = 0;
            }
            throw new IllegalArgumentException("Data too large for an Aztec code");
        }
        z = i2 < 0;
        int iAbs = Math.abs(i2);
        if (iAbs > (z ? 4 : 32)) {
            throw new IllegalArgumentException(String.format("Illegal value %s for layers", Integer.valueOf(i2)));
        }
        int i11 = totalBitsInLayer(iAbs, z);
        i5 = WORD_SIZE[iAbs];
        int i12 = i11 - (i11 % i5);
        bitArrayStuffBits = stuffBits(bitArrayEncode, i5);
        if (bitArrayStuffBits.getSize() + size > i12) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        if (z && bitArrayStuffBits.getSize() > (i5 << 6)) {
            throw new IllegalArgumentException("Data to large for user specified layer");
        }
        i4 = i11;
        i3 = iAbs;
        BitArray bitArrayGenerateCheckWords = generateCheckWords(bitArrayStuffBits, i4, i5);
        int size3 = bitArrayStuffBits.getSize() / i5;
        BitArray bitArrayGenerateModeMessage = generateModeMessage(z, i3, size3);
        int i13 = (z ? 11 : 14) + (i3 << 2);
        int[] iArr = new int[i13];
        int i14 = 2;
        if (z) {
            for (int i15 = i7; i15 < iArr.length; i15++) {
                iArr[i15] = i15;
            }
            i6 = i13;
        } else {
            int i16 = i13 / 2;
            i6 = i13 + 1 + (((i16 - 1) / 15) * 2);
            int i17 = i6 / 2;
            for (int i18 = i7; i18 < i16; i18++) {
                iArr[(i16 - i18) - 1] = (i17 - r14) - 1;
                iArr[i16 + i18] = (i18 / 15) + i18 + i17 + 1;
            }
        }
        BitMatrix bitMatrix = new BitMatrix(i6);
        int i19 = i7;
        int i20 = i19;
        while (i19 < i3) {
            int i21 = ((i3 - i19) << i14) + (z ? 9 : 12);
            int i22 = i7;
            while (i22 < i21) {
                int i23 = i22 << 1;
                while (i7 < i14) {
                    if (bitArrayGenerateCheckWords.get(i20 + i23 + i7)) {
                        int i24 = i19 << 1;
                        bitMatrix.set(iArr[i24 + i7], iArr[i24 + i22]);
                    }
                    if (bitArrayGenerateCheckWords.get((i21 << 1) + i20 + i23 + i7)) {
                        int i25 = i19 << 1;
                        bitMatrix.set(iArr[i25 + i22], iArr[((i13 - 1) - i25) - i7]);
                    }
                    if (bitArrayGenerateCheckWords.get((i21 << 2) + i20 + i23 + i7)) {
                        int i26 = (i13 - 1) - (i19 << 1);
                        bitMatrix.set(iArr[i26 - i7], iArr[i26 - i22]);
                    }
                    if (bitArrayGenerateCheckWords.get((i21 * 6) + i20 + i23 + i7)) {
                        int i27 = i19 << 1;
                        bitMatrix.set(iArr[((i13 - 1) - i27) - i22], iArr[i27 + i7]);
                    }
                    i7++;
                    i14 = 2;
                }
                i22++;
                i7 = 0;
                i14 = 2;
            }
            i20 += i21 << 3;
            i19++;
            i7 = 0;
            i14 = 2;
        }
        drawModeMessage(bitMatrix, z, i6, bitArrayGenerateModeMessage);
        if (z) {
            drawBullsEye(bitMatrix, i6 / 2, 5);
        } else {
            int i28 = i6 / 2;
            drawBullsEye(bitMatrix, i28, 7);
            int i29 = 0;
            int i30 = 0;
            while (i29 < (i13 / 2) - 1) {
                for (int i31 = i28 & 1; i31 < i6; i31 += 2) {
                    int i32 = i28 - i30;
                    bitMatrix.set(i32, i31);
                    int i33 = i28 + i30;
                    bitMatrix.set(i33, i31);
                    bitMatrix.set(i31, i32);
                    bitMatrix.set(i31, i33);
                }
                i29 += 15;
                i30 += 16;
            }
        }
        AztecCode aztecCode = new AztecCode();
        aztecCode.setCompact(z);
        aztecCode.setSize(i6);
        aztecCode.setLayers(i3);
        aztecCode.setCodeWords(size3);
        aztecCode.setMatrix(bitMatrix);
        return aztecCode;
    }

    private static BitArray generateCheckWords(BitArray bitArray, int i, int i2) {
        int size = bitArray.getSize() / i2;
        ReedSolomonEncoder reedSolomonEncoder = new ReedSolomonEncoder(getGF(i2));
        int i3 = i / i2;
        int[] iArrBitsToWords = bitsToWords(bitArray, i2, i3);
        reedSolomonEncoder.encode(iArrBitsToWords, i3 - size);
        BitArray bitArray2 = new BitArray();
        bitArray2.appendBits(0, i % i2);
        for (int i4 : iArrBitsToWords) {
            bitArray2.appendBits(i4, i2);
        }
        return bitArray2;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static BitArray generateModeMessage(boolean z, int i, int i2) {
        int i3;
        BitArray bitArray = new BitArray();
        if (z) {
            bitArray.appendBits(i - 1, 2);
            bitArray.appendBits(i2 - 1, 6);
            i3 = 28;
        } else {
            bitArray.appendBits(i - 1, 5);
            bitArray.appendBits(i2 - 1, 11);
            i3 = 40;
        }
        return generateCheckWords(bitArray, i3, 4);
    }

    private static GenericGF getGF(int i) {
        if (i == 4) {
            return GenericGF.AZTEC_PARAM;
        }
        if (i == 6) {
            return GenericGF.AZTEC_DATA_6;
        }
        if (i == 8) {
            return GenericGF.AZTEC_DATA_8;
        }
        if (i == 10) {
            return GenericGF.AZTEC_DATA_10;
        }
        if (i == 12) {
            return GenericGF.AZTEC_DATA_12;
        }
        throw new IllegalArgumentException("Unsupported word size " + i);
    }

    static BitArray stuffBits(BitArray bitArray, int i) {
        BitArray bitArray2 = new BitArray();
        int size = bitArray.getSize();
        int i2 = (1 << i) - 2;
        int i3 = 0;
        while (i3 < size) {
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int i6 = i3 + i5;
                if (i6 >= size || bitArray.get(i6)) {
                    i4 |= 1 << ((i - 1) - i5);
                }
            }
            int i7 = i4 & i2;
            if (i7 == i2) {
                bitArray2.appendBits(i7, i);
                i3--;
            } else if (i7 == 0) {
                i7 = i4 | 1;
                bitArray2.appendBits(i7, i);
                i3--;
            } else {
                bitArray2.appendBits(i4, i);
            }
            i3 += i;
        }
        return bitArray2;
    }

    private static int totalBitsInLayer(int i, boolean z) {
        return ((z ? 88 : R.styleable.AppCompatTheme_windowMinWidthMajor) + (i << 4)) * i;
    }
}
