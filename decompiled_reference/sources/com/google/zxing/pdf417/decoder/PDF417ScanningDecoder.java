package com.google.zxing.pdf417.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.pdf417.PDF417Common;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Formatter;

/* loaded from: classes.dex */
public final class PDF417ScanningDecoder {
    private static final int CODEWORD_SKEW_SIZE = 2;
    private static final int MAX_EC_CODEWORDS = 512;
    private static final int MAX_ERRORS = 3;
    private static final ErrorCorrection errorCorrection = new ErrorCorrection();

    private PDF417ScanningDecoder() {
    }

    private static BoundingBox adjustBoundingBox(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn) {
        int[] rowHeights;
        if (detectionResultRowIndicatorColumn == null || (rowHeights = detectionResultRowIndicatorColumn.getRowHeights()) == null) {
            return null;
        }
        int max = getMax(rowHeights);
        int i = 0;
        int i2 = 0;
        for (int i3 : rowHeights) {
            i2 += max - i3;
            if (i3 > 0) {
                break;
            }
        }
        Codeword[] codewords = detectionResultRowIndicatorColumn.getCodewords();
        for (int i4 = 0; i2 > 0 && codewords[i4] == null; i4++) {
            i2--;
        }
        for (int length = rowHeights.length - 1; length >= 0; length--) {
            i += max - rowHeights[length];
            if (rowHeights[length] > 0) {
                break;
            }
        }
        for (int length2 = codewords.length - 1; i > 0 && codewords[length2] == null; length2--) {
            i--;
        }
        return detectionResultRowIndicatorColumn.getBoundingBox().addMissingRows(i2, i, detectionResultRowIndicatorColumn.isLeft());
    }

    private static void adjustCodewordCount(DetectionResult detectionResult, BarcodeValue[][] barcodeValueArr) throws NotFoundException {
        int[] value = barcodeValueArr[0][1].getValue();
        int barcodeColumnCount = (detectionResult.getBarcodeColumnCount() * detectionResult.getBarcodeRowCount()) - getNumberOfECCodeWords(detectionResult.getBarcodeECLevel());
        if (value.length != 0) {
            if (value[0] != barcodeColumnCount) {
                barcodeValueArr[0][1].setValue(barcodeColumnCount);
            }
        } else {
            if (barcodeColumnCount <= 0 || barcodeColumnCount > 928) {
                throw NotFoundException.getNotFoundInstance();
            }
            barcodeValueArr[0][1].setValue(barcodeColumnCount);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int adjustCodewordStartColumn(com.google.zxing.common.BitMatrix r7, int r8, int r9, boolean r10, int r11, int r12) {
        /*
            r0 = 1
            if (r10 == 0) goto L5
            r1 = -1
            goto L6
        L5:
            r1 = r0
        L6:
            r2 = 0
            r4 = r10
            r3 = r1
            r10 = r2
            r1 = r11
        Lb:
            r5 = 2
            if (r10 >= r5) goto L2f
        Le:
            if (r4 == 0) goto L13
            if (r1 < r8) goto L26
            goto L15
        L13:
            if (r1 >= r9) goto L26
        L15:
            boolean r6 = r7.get(r1, r12)
            if (r4 != r6) goto L26
            int r6 = r11 - r1
            int r6 = java.lang.Math.abs(r6)
            if (r6 <= r5) goto L24
            return r11
        L24:
            int r1 = r1 + r3
            goto Le
        L26:
            int r3 = -r3
            if (r4 != 0) goto L2b
            r4 = r0
            goto L2c
        L2b:
            r4 = r2
        L2c:
            int r10 = r10 + 1
            goto Lb
        L2f:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.adjustCodewordStartColumn(com.google.zxing.common.BitMatrix, int, int, boolean, int, int):int");
    }

    private static boolean checkCodewordSkew(int i, int i2, int i3) {
        return i2 + (-2) <= i && i <= i3 + 2;
    }

    private static int correctErrors(int[] iArr, int[] iArr2, int i) throws ChecksumException {
        if ((iArr2 == null || iArr2.length <= (i / 2) + 3) && i >= 0 && i <= 512) {
            return errorCorrection.decode(iArr, i, iArr2);
        }
        throw ChecksumException.getChecksumInstance();
    }

    private static BarcodeValue[][] createBarcodeMatrix(DetectionResult detectionResult) {
        int rowNumber;
        BarcodeValue[][] barcodeValueArr = (BarcodeValue[][]) Array.newInstance((Class<?>) BarcodeValue.class, detectionResult.getBarcodeRowCount(), detectionResult.getBarcodeColumnCount() + 2);
        for (int i = 0; i < barcodeValueArr.length; i++) {
            for (int i2 = 0; i2 < barcodeValueArr[i].length; i2++) {
                barcodeValueArr[i][i2] = new BarcodeValue();
            }
        }
        int i3 = 0;
        for (DetectionResultColumn detectionResultColumn : detectionResult.getDetectionResultColumns()) {
            if (detectionResultColumn != null) {
                for (Codeword codeword : detectionResultColumn.getCodewords()) {
                    if (codeword != null && (rowNumber = codeword.getRowNumber()) >= 0 && rowNumber < barcodeValueArr.length) {
                        barcodeValueArr[rowNumber][i3].setValue(codeword.getValue());
                    }
                }
            }
            i3++;
        }
        return barcodeValueArr;
    }

    private static DecoderResult createDecoderResult(DetectionResult detectionResult) throws NotFoundException {
        BarcodeValue[][] barcodeValueArrCreateBarcodeMatrix = createBarcodeMatrix(detectionResult);
        adjustCodewordCount(detectionResult, barcodeValueArrCreateBarcodeMatrix);
        ArrayList arrayList = new ArrayList();
        int[] iArr = new int[detectionResult.getBarcodeRowCount() * detectionResult.getBarcodeColumnCount()];
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (int i = 0; i < detectionResult.getBarcodeRowCount(); i++) {
            int i2 = 0;
            while (i2 < detectionResult.getBarcodeColumnCount()) {
                int i3 = i2 + 1;
                int[] value = barcodeValueArrCreateBarcodeMatrix[i][i3].getValue();
                int barcodeColumnCount = (detectionResult.getBarcodeColumnCount() * i) + i2;
                if (value.length == 0) {
                    arrayList.add(Integer.valueOf(barcodeColumnCount));
                } else if (value.length == 1) {
                    iArr[barcodeColumnCount] = value[0];
                } else {
                    arrayList3.add(Integer.valueOf(barcodeColumnCount));
                    arrayList2.add(value);
                }
                i2 = i3;
            }
        }
        int[][] iArr2 = new int[arrayList2.size()][];
        for (int i4 = 0; i4 < iArr2.length; i4++) {
            iArr2[i4] = (int[]) arrayList2.get(i4);
        }
        return createDecoderResultFromAmbiguousValues(detectionResult.getBarcodeECLevel(), iArr, PDF417Common.toIntArray(arrayList), PDF417Common.toIntArray(arrayList3), iArr2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x004b, code lost:
    
        r1 = r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static com.google.zxing.common.DecoderResult createDecoderResultFromAmbiguousValues(int r7, int[] r8, int[] r9, int[] r10, int[][] r11) throws com.google.zxing.ChecksumException {
        /*
            int r0 = r10.length
            int[] r0 = new int[r0]
            r1 = 100
        L5:
            int r2 = r1 + (-1)
            if (r1 <= 0) goto L4d
            r1 = 0
            r3 = r1
        Lb:
            int r4 = r0.length
            if (r3 >= r4) goto L1b
            r4 = r10[r3]
            r5 = r11[r3]
            r6 = r0[r3]
            r5 = r5[r6]
            r8[r4] = r5
            int r3 = r3 + 1
            goto Lb
        L1b:
            com.google.zxing.common.DecoderResult r3 = decodeCodewords(r8, r7, r9)     // Catch: com.google.zxing.ChecksumException -> L20
            return r3
        L20:
            int r3 = r0.length
            if (r3 != 0) goto L28
            com.google.zxing.ChecksumException r7 = com.google.zxing.ChecksumException.getChecksumInstance()
            throw r7
        L28:
            r3 = r1
        L29:
            int r4 = r0.length
            if (r3 >= r4) goto L4b
            r4 = r0[r3]
            r5 = r11[r3]
            int r5 = r5.length
            int r5 = r5 + (-1)
            if (r4 >= r5) goto L3c
            r1 = r0[r3]
            int r1 = r1 + 1
            r0[r3] = r1
            goto L4b
        L3c:
            r0[r3] = r1
            int r4 = r0.length
            int r4 = r4 + (-1)
            if (r3 != r4) goto L48
            com.google.zxing.ChecksumException r7 = com.google.zxing.ChecksumException.getChecksumInstance()
            throw r7
        L48:
            int r3 = r3 + 1
            goto L29
        L4b:
            r1 = r2
            goto L5
        L4d:
            com.google.zxing.ChecksumException r7 = com.google.zxing.ChecksumException.getChecksumInstance()
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.createDecoderResultFromAmbiguousValues(int, int[], int[], int[], int[][]):com.google.zxing.common.DecoderResult");
    }

    public static DecoderResult decode(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i, int i2) throws NotFoundException {
        DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn;
        int i3;
        int i4;
        DetectionResultRowIndicatorColumn rowIndicatorColumn = null;
        DetectionResult detectionResultMerge = null;
        DetectionResultRowIndicatorColumn rowIndicatorColumn2 = null;
        int i5 = 0;
        BoundingBox boundingBox = new BoundingBox(bitMatrix, resultPoint, resultPoint2, resultPoint3, resultPoint4);
        while (i5 < 2) {
            if (resultPoint != null) {
                rowIndicatorColumn = getRowIndicatorColumn(bitMatrix, boundingBox, resultPoint, true, i, i2);
            }
            detectionResultRowIndicatorColumn = rowIndicatorColumn;
            if (resultPoint3 != null) {
                rowIndicatorColumn2 = getRowIndicatorColumn(bitMatrix, boundingBox, resultPoint3, false, i, i2);
            }
            detectionResultMerge = merge(detectionResultRowIndicatorColumn, rowIndicatorColumn2);
            if (detectionResultMerge == null) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (i5 != 0 || detectionResultMerge.getBoundingBox() == null || (detectionResultMerge.getBoundingBox().getMinY() >= boundingBox.getMinY() && detectionResultMerge.getBoundingBox().getMaxY() <= boundingBox.getMaxY())) {
                detectionResultMerge.setBoundingBox(boundingBox);
                break;
            }
            boundingBox = detectionResultMerge.getBoundingBox();
            i5++;
            rowIndicatorColumn = detectionResultRowIndicatorColumn;
        }
        detectionResultRowIndicatorColumn = rowIndicatorColumn;
        int barcodeColumnCount = detectionResultMerge.getBarcodeColumnCount() + 1;
        detectionResultMerge.setDetectionResultColumn(0, detectionResultRowIndicatorColumn);
        detectionResultMerge.setDetectionResultColumn(barcodeColumnCount, rowIndicatorColumn2);
        boolean z = detectionResultRowIndicatorColumn != null;
        int i6 = i;
        int i7 = i2;
        for (int i8 = 1; i8 <= barcodeColumnCount; i8++) {
            int i9 = z ? i8 : barcodeColumnCount - i8;
            if (detectionResultMerge.getDetectionResultColumn(i9) == null) {
                DetectionResultColumn detectionResultRowIndicatorColumn2 = (i9 == 0 || i9 == barcodeColumnCount) ? new DetectionResultRowIndicatorColumn(boundingBox, i9 == 0) : new DetectionResultColumn(boundingBox);
                detectionResultMerge.setDetectionResultColumn(i9, detectionResultRowIndicatorColumn2);
                int iMax = i7;
                int iMin = i6;
                int i10 = -1;
                for (int minY = boundingBox.getMinY(); minY <= boundingBox.getMaxY(); minY++) {
                    int startColumn = getStartColumn(detectionResultMerge, i9, minY, z);
                    if (startColumn >= 0 && startColumn <= boundingBox.getMaxX()) {
                        i4 = startColumn;
                    } else if (i10 != -1) {
                        i4 = i10;
                    } else {
                        i3 = iMax;
                        iMax = i3;
                    }
                    int i11 = iMax;
                    Codeword codewordDetectCodeword = detectCodeword(bitMatrix, boundingBox.getMinX(), boundingBox.getMaxX(), z, i4, minY, iMin, i11);
                    if (codewordDetectCodeword != null) {
                        detectionResultRowIndicatorColumn2.setCodeword(minY, codewordDetectCodeword);
                        iMin = Math.min(iMin, codewordDetectCodeword.getWidth());
                        iMax = Math.max(i11, codewordDetectCodeword.getWidth());
                        i10 = i4;
                    } else {
                        i3 = i11;
                        iMax = i3;
                    }
                }
                i6 = iMin;
                i7 = iMax;
            }
        }
        return createDecoderResult(detectionResultMerge);
    }

    private static DecoderResult decodeCodewords(int[] iArr, int i, int[] iArr2) throws ChecksumException, FormatException {
        if (iArr.length == 0) {
            throw FormatException.getFormatInstance();
        }
        int i2 = 1 << (i + 1);
        int iCorrectErrors = correctErrors(iArr, iArr2, i2);
        verifyCodewordCount(iArr, i2);
        DecoderResult decoderResultDecode = DecodedBitStreamParser.decode(iArr, String.valueOf(i));
        decoderResultDecode.setErrorsCorrected(Integer.valueOf(iCorrectErrors));
        decoderResultDecode.setErasures(Integer.valueOf(iArr2.length));
        return decoderResultDecode;
    }

    private static Codeword detectCodeword(BitMatrix bitMatrix, int i, int i2, boolean z, int i3, int i4, int i5, int i6) {
        int i7;
        int decodedValue;
        int codeword;
        int iAdjustCodewordStartColumn = adjustCodewordStartColumn(bitMatrix, i, i2, z, i3, i4);
        int[] moduleBitCount = getModuleBitCount(bitMatrix, i, i2, z, iAdjustCodewordStartColumn, i4);
        if (moduleBitCount == null) {
            return null;
        }
        int iSum = MathUtils.sum(moduleBitCount);
        if (z) {
            iAdjustCodewordStartColumn += iSum;
            i7 = iAdjustCodewordStartColumn;
        } else {
            for (int i8 = 0; i8 < moduleBitCount.length / 2; i8++) {
                int i9 = moduleBitCount[i8];
                moduleBitCount[i8] = moduleBitCount[(moduleBitCount.length - 1) - i8];
                moduleBitCount[(moduleBitCount.length - 1) - i8] = i9;
            }
            i7 = iAdjustCodewordStartColumn - iSum;
        }
        if (checkCodewordSkew(iSum, i5, i6) && (codeword = PDF417Common.getCodeword((decodedValue = PDF417CodewordDecoder.getDecodedValue(moduleBitCount)))) != -1) {
            return new Codeword(i7, iAdjustCodewordStartColumn, getCodewordBucketNumber(decodedValue), codeword);
        }
        return null;
    }

    private static BarcodeMetadata getBarcodeMetadata(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn, DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn2) {
        BarcodeMetadata barcodeMetadata;
        BarcodeMetadata barcodeMetadata2;
        if (detectionResultRowIndicatorColumn == null || (barcodeMetadata = detectionResultRowIndicatorColumn.getBarcodeMetadata()) == null) {
            if (detectionResultRowIndicatorColumn2 == null) {
                return null;
            }
            return detectionResultRowIndicatorColumn2.getBarcodeMetadata();
        }
        if (detectionResultRowIndicatorColumn2 == null || (barcodeMetadata2 = detectionResultRowIndicatorColumn2.getBarcodeMetadata()) == null || barcodeMetadata.getColumnCount() == barcodeMetadata2.getColumnCount() || barcodeMetadata.getErrorCorrectionLevel() == barcodeMetadata2.getErrorCorrectionLevel() || barcodeMetadata.getRowCount() == barcodeMetadata2.getRowCount()) {
            return barcodeMetadata;
        }
        return null;
    }

    private static int[] getBitCountForCodeword(int i) {
        int[] iArr = new int[8];
        int i2 = 0;
        int i3 = 7;
        while (true) {
            int i4 = i & 1;
            if (i4 != i2) {
                i3--;
                if (i3 < 0) {
                    return iArr;
                }
                i2 = i4;
            }
            iArr[i3] = iArr[i3] + 1;
            i >>= 1;
        }
    }

    private static int getCodewordBucketNumber(int i) {
        return getCodewordBucketNumber(getBitCountForCodeword(i));
    }

    private static int getCodewordBucketNumber(int[] iArr) {
        return ((((iArr[0] - iArr[2]) + iArr[4]) - iArr[6]) + 9) % 9;
    }

    private static int getMax(int[] iArr) {
        int iMax = -1;
        for (int i : iArr) {
            iMax = Math.max(iMax, i);
        }
        return iMax;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0014  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static int[] getModuleBitCount(com.google.zxing.common.BitMatrix r8, int r9, int r10, boolean r11, int r12, int r13) {
        /*
            r0 = 8
            int[] r1 = new int[r0]
            r2 = 1
            if (r11 == 0) goto L9
            r3 = r2
            goto La
        L9:
            r3 = -1
        La:
            r4 = 0
            r6 = r11
            r5 = r4
        Ld:
            if (r11 == 0) goto L12
            if (r12 >= r10) goto L2b
            goto L14
        L12:
            if (r12 < r9) goto L2b
        L14:
            if (r5 >= r0) goto L2b
            boolean r7 = r8.get(r12, r13)
            if (r7 != r6) goto L23
            r7 = r1[r5]
            int r7 = r7 + r2
            r1[r5] = r7
            int r12 = r12 + r3
            goto Ld
        L23:
            int r5 = r5 + 1
            if (r6 != 0) goto L29
            r6 = r2
            goto Ld
        L29:
            r6 = r4
            goto Ld
        L2b:
            if (r5 == r0) goto L38
            if (r11 == 0) goto L30
            r9 = r10
        L30:
            if (r12 != r9) goto L36
            r8 = 7
            if (r5 != r8) goto L36
            return r1
        L36:
            r8 = 0
            return r8
        L38:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.getModuleBitCount(com.google.zxing.common.BitMatrix, int, int, boolean, int, int):int[]");
    }

    private static int getNumberOfECCodeWords(int i) {
        return 2 << i;
    }

    private static DetectionResultRowIndicatorColumn getRowIndicatorColumn(BitMatrix bitMatrix, BoundingBox boundingBox, ResultPoint resultPoint, boolean z, int i, int i2) {
        DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn = new DetectionResultRowIndicatorColumn(boundingBox, z);
        int i3 = 0;
        while (i3 < 2) {
            int i4 = i3 == 0 ? 1 : -1;
            int x = (int) resultPoint.getX();
            for (int y = (int) resultPoint.getY(); y <= boundingBox.getMaxY() && y >= boundingBox.getMinY(); y += i4) {
                Codeword codewordDetectCodeword = detectCodeword(bitMatrix, 0, bitMatrix.getWidth(), z, x, y, i, i2);
                if (codewordDetectCodeword != null) {
                    detectionResultRowIndicatorColumn.setCodeword(y, codewordDetectCodeword);
                    x = z ? codewordDetectCodeword.getStartX() : codewordDetectCodeword.getEndX();
                }
            }
            i3++;
        }
        return detectionResultRowIndicatorColumn;
    }

    private static int getStartColumn(DetectionResult detectionResult, int i, int i2, boolean z) {
        int i3 = z ? 1 : -1;
        int i4 = i - i3;
        Codeword codeword = isValidBarcodeColumn(detectionResult, i4) ? detectionResult.getDetectionResultColumn(i4).getCodeword(i2) : null;
        if (codeword != null) {
            return z ? codeword.getEndX() : codeword.getStartX();
        }
        Codeword codewordNearby = detectionResult.getDetectionResultColumn(i).getCodewordNearby(i2);
        if (codewordNearby != null) {
            return z ? codewordNearby.getStartX() : codewordNearby.getEndX();
        }
        if (isValidBarcodeColumn(detectionResult, i4)) {
            codewordNearby = detectionResult.getDetectionResultColumn(i4).getCodewordNearby(i2);
        }
        if (codewordNearby != null) {
            return z ? codewordNearby.getEndX() : codewordNearby.getStartX();
        }
        int i5 = 0;
        while (true) {
            i -= i3;
            if (!isValidBarcodeColumn(detectionResult, i)) {
                return z ? detectionResult.getBoundingBox().getMinX() : detectionResult.getBoundingBox().getMaxX();
            }
            for (Codeword codeword2 : detectionResult.getDetectionResultColumn(i).getCodewords()) {
                if (codeword2 != null) {
                    return (z ? codeword2.getEndX() : codeword2.getStartX()) + (i3 * i5 * (codeword2.getEndX() - codeword2.getStartX()));
                }
            }
            i5++;
        }
    }

    private static boolean isValidBarcodeColumn(DetectionResult detectionResult, int i) {
        return i >= 0 && i <= detectionResult.getBarcodeColumnCount() + 1;
    }

    private static DetectionResult merge(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn, DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn2) {
        BarcodeMetadata barcodeMetadata;
        if ((detectionResultRowIndicatorColumn == null && detectionResultRowIndicatorColumn2 == null) || (barcodeMetadata = getBarcodeMetadata(detectionResultRowIndicatorColumn, detectionResultRowIndicatorColumn2)) == null) {
            return null;
        }
        return new DetectionResult(barcodeMetadata, BoundingBox.merge(adjustBoundingBox(detectionResultRowIndicatorColumn), adjustBoundingBox(detectionResultRowIndicatorColumn2)));
    }

    public static String toString(BarcodeValue[][] barcodeValueArr) {
        Formatter formatter = new Formatter();
        for (int i = 0; i < barcodeValueArr.length; i++) {
            formatter.format("Row %2d: ", Integer.valueOf(i));
            for (int i2 = 0; i2 < barcodeValueArr[i].length; i2++) {
                BarcodeValue barcodeValue = barcodeValueArr[i][i2];
                if (barcodeValue.getValue().length == 0) {
                    formatter.format("        ", null);
                } else {
                    formatter.format("%4d(%2d)", Integer.valueOf(barcodeValue.getValue()[0]), barcodeValue.getConfidence(barcodeValue.getValue()[0]));
                }
            }
            formatter.format("%n", new Object[0]);
        }
        String string = formatter.toString();
        formatter.close();
        return string;
    }

    private static void verifyCodewordCount(int[] iArr, int i) throws FormatException {
        if (iArr.length < 4) {
            throw FormatException.getFormatInstance();
        }
        int i2 = iArr[0];
        if (i2 > iArr.length) {
            throw FormatException.getFormatInstance();
        }
        if (i2 == 0) {
            if (i >= iArr.length) {
                throw FormatException.getFormatInstance();
            }
            iArr[0] = iArr.length - i;
        }
    }
}
