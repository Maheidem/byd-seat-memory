package com.google.zxing.oned;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class OneDReader implements Reader {
    private Result doDecode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int width = binaryBitmap.getWidth();
        int height = binaryBitmap.getHeight();
        BitArray bitArray = new BitArray(width);
        int i7 = height >> 1;
        int i8 = 1;
        boolean z = map != null && map.containsKey(DecodeHintType.TRY_HARDER);
        int iMax = Math.max(1, height >> (z ? 8 : 5));
        int i9 = z ? height : 15;
        Map<DecodeHintType, ?> map2 = map;
        int i10 = 0;
        while (i10 < i9) {
            int i11 = i10 + 1;
            int i12 = i11 / 2;
            if (((i10 & 1) == 0 ? i8 : 0) == 0) {
                i12 = -i12;
            }
            int i13 = (i12 * iMax) + i7;
            if (i13 < 0 || i13 >= height) {
                break;
            }
            try {
                BitArray blackRow = binaryBitmap.getBlackRow(i13, bitArray);
                int i14 = 0;
                while (i14 < 2) {
                    if (i14 == i8) {
                        blackRow.reverse();
                        if (map2 != null && map2.containsKey(DecodeHintType.NEED_RESULT_POINT_CALLBACK)) {
                            EnumMap enumMap = new EnumMap(DecodeHintType.class);
                            enumMap.putAll(map2);
                            enumMap.remove(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
                            map2 = enumMap;
                        }
                    }
                    try {
                        Result resultDecodeRow = decodeRow(i13, blackRow, map2);
                        if (i14 == i8) {
                            try {
                                resultDecodeRow.putMetadata(ResultMetadataType.ORIENTATION, 180);
                                ResultPoint[] resultPoints = resultDecodeRow.getResultPoints();
                                if (resultPoints != null) {
                                    float f = width;
                                    i4 = width;
                                    try {
                                        i5 = height;
                                        try {
                                            resultPoints[0] = new ResultPoint((f - resultPoints[0].getX()) - 1.0f, resultPoints[0].getY());
                                            i6 = 1;
                                        } catch (ReaderException unused) {
                                            i6 = 1;
                                            i14++;
                                            i8 = i6;
                                            width = i4;
                                            height = i5;
                                        }
                                    } catch (ReaderException unused2) {
                                        i5 = height;
                                        i6 = 1;
                                        i14++;
                                        i8 = i6;
                                        width = i4;
                                        height = i5;
                                    }
                                    try {
                                        resultPoints[1] = new ResultPoint((f - resultPoints[1].getX()) - 1.0f, resultPoints[1].getY());
                                        return resultDecodeRow;
                                    } catch (ReaderException unused3) {
                                        continue;
                                        i14++;
                                        i8 = i6;
                                        width = i4;
                                        height = i5;
                                    }
                                }
                            } catch (ReaderException unused4) {
                                i4 = width;
                            }
                        }
                        return resultDecodeRow;
                    } catch (ReaderException unused5) {
                        i4 = width;
                        i5 = height;
                        i6 = i8;
                    }
                }
                i = width;
                i2 = height;
                i3 = i8;
                bitArray = blackRow;
            } catch (NotFoundException unused6) {
                i = width;
                i2 = height;
                i3 = i8;
            }
            i8 = i3;
            i10 = i11;
            width = i;
            height = i2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    protected static float patternMatchVariance(int[] iArr, int[] iArr2, float f) {
        int length = iArr.length;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            i += iArr[i3];
            i2 += iArr2[i3];
        }
        if (i < i2) {
            return Float.POSITIVE_INFINITY;
        }
        float f2 = i;
        float f3 = f2 / i2;
        float f4 = f * f3;
        float f5 = 0.0f;
        for (int i4 = 0; i4 < length; i4++) {
            float f6 = iArr2[i4] * f3;
            float f7 = iArr[i4];
            float f8 = f7 > f6 ? f7 - f6 : f6 - f7;
            if (f8 > f4) {
                return Float.POSITIVE_INFINITY;
            }
            f5 += f8;
        }
        return f5 / f2;
    }

    protected static void recordPattern(BitArray bitArray, int i, int[] iArr) throws NotFoundException {
        int length = iArr.length;
        Arrays.fill(iArr, 0, length, 0);
        int size = bitArray.getSize();
        if (i >= size) {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean z = !bitArray.get(i);
        int i2 = 0;
        while (i < size) {
            if (!(bitArray.get(i) ^ z)) {
                i2++;
                if (i2 == length) {
                    break;
                }
                iArr[i2] = 1;
                z = !z;
            } else {
                iArr[i2] = iArr[i2] + 1;
            }
            i++;
        }
        if (i2 != length) {
            if (i2 != length - 1 || i != size) {
                throw NotFoundException.getNotFoundInstance();
            }
        }
    }

    protected static void recordPatternInReverse(BitArray bitArray, int i, int[] iArr) throws NotFoundException {
        int length = iArr.length;
        boolean z = bitArray.get(i);
        while (i > 0 && length >= 0) {
            i--;
            if (bitArray.get(i) != z) {
                length--;
                z = !z;
            }
        }
        if (length >= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        recordPattern(bitArray, i + 1, iArr);
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) {
        return decode(binaryBitmap, null);
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        try {
            return doDecode(binaryBitmap, map);
        } catch (NotFoundException e) {
            if (!(map != null && map.containsKey(DecodeHintType.TRY_HARDER)) || !binaryBitmap.isRotateSupported()) {
                throw e;
            }
            BinaryBitmap binaryBitmapRotateCounterClockwise = binaryBitmap.rotateCounterClockwise();
            Result resultDoDecode = doDecode(binaryBitmapRotateCounterClockwise, map);
            Map<ResultMetadataType, Object> resultMetadata = resultDoDecode.getResultMetadata();
            int iIntValue = 270;
            if (resultMetadata != null && resultMetadata.containsKey(ResultMetadataType.ORIENTATION)) {
                iIntValue = (270 + ((Integer) resultMetadata.get(ResultMetadataType.ORIENTATION)).intValue()) % 360;
            }
            resultDoDecode.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(iIntValue));
            ResultPoint[] resultPoints = resultDoDecode.getResultPoints();
            if (resultPoints != null) {
                int height = binaryBitmapRotateCounterClockwise.getHeight();
                for (int i = 0; i < resultPoints.length; i++) {
                    resultPoints[i] = new ResultPoint((height - resultPoints[i].getY()) - 1.0f, resultPoints[i].getX());
                }
            }
            return resultDoDecode;
        }
    }

    public abstract Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map);

    @Override // com.google.zxing.Reader
    public void reset() {
    }
}
