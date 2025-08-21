package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class GenericMultipleBarcodeReader implements MultipleBarcodeReader {
    private static final int MAX_DEPTH = 4;
    private static final int MIN_DIMENSION_TO_RECUR = 100;
    private final Reader delegate;

    public GenericMultipleBarcodeReader(Reader reader) {
        this.delegate = reader;
    }

    private void doDecodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, List<Result> list, int i, int i2, int i3) {
        boolean z;
        List<Result> list2;
        int i4;
        float f;
        float f2;
        float f3;
        int i5;
        int i6;
        if (i3 > 4) {
            return;
        }
        try {
            Result resultDecode = this.delegate.decode(binaryBitmap, map);
            Iterator<Result> it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                } else if (it.next().getText().equals(resultDecode.getText())) {
                    z = true;
                    break;
                }
            }
            if (z) {
                list2 = list;
            } else {
                list2 = list;
                list2.add(translateResultPoints(resultDecode, i, i2));
            }
            ResultPoint[] resultPoints = resultDecode.getResultPoints();
            if (resultPoints == null || resultPoints.length == 0) {
                return;
            }
            int width = binaryBitmap.getWidth();
            int height = binaryBitmap.getHeight();
            int length = resultPoints.length;
            float f4 = 0.0f;
            float f5 = 0.0f;
            float f6 = height;
            float f7 = width;
            int i7 = 0;
            while (i7 < length) {
                int i8 = length;
                ResultPoint resultPoint = resultPoints[i7];
                if (resultPoint != null) {
                    float x = resultPoint.getX();
                    float y = resultPoint.getY();
                    if (x < f7) {
                        f7 = x;
                    }
                    if (y < f6) {
                        f6 = y;
                    }
                    if (x > f4) {
                        f4 = x;
                    }
                    if (y > f5) {
                        f5 = y;
                    }
                }
                i7++;
                length = i8;
            }
            if (f7 > 100.0f) {
                f2 = f5;
                f3 = f6;
                i4 = height;
                f = f4;
                i5 = width;
                doDecodeMultiple(binaryBitmap.crop(0, 0, (int) f7, height), map, list2, i, i2, i3 + 1);
            } else {
                i4 = height;
                f = f4;
                f2 = f5;
                f3 = f6;
                i5 = width;
            }
            if (f3 > 100.0f) {
                doDecodeMultiple(binaryBitmap.crop(0, 0, i5, (int) f3), map, list2, i, i2, i3 + 1);
            }
            if (f < i5 - 100) {
                int i9 = (int) f;
                i6 = i4;
                doDecodeMultiple(binaryBitmap.crop(i9, 0, i5 - i9, i6), map, list2, i + i9, i2, i3 + 1);
            } else {
                i6 = i4;
            }
            if (f2 < i6 - 100) {
                int i10 = (int) f2;
                doDecodeMultiple(binaryBitmap.crop(0, i10, i5, i6 - i10), map, list2, i, i2 + i10, i3 + 1);
            }
        } catch (ReaderException unused) {
        }
    }

    private static Result translateResultPoints(Result result, int i, int i2) {
        ResultPoint[] resultPoints = result.getResultPoints();
        if (resultPoints == null) {
            return result;
        }
        ResultPoint[] resultPointArr = new ResultPoint[resultPoints.length];
        for (int i3 = 0; i3 < resultPoints.length; i3++) {
            ResultPoint resultPoint = resultPoints[i3];
            if (resultPoint != null) {
                resultPointArr[i3] = new ResultPoint(resultPoint.getX() + i, resultPoint.getY() + i2);
            }
        }
        Result result2 = new Result(result.getText(), result.getRawBytes(), result.getNumBits(), resultPointArr, result.getBarcodeFormat(), result.getTimestamp());
        result2.putAllMetadata(result.getResultMetadata());
        return result2;
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) {
        return decodeMultiple(binaryBitmap, null);
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList arrayList = new ArrayList();
        doDecodeMultiple(binaryBitmap, map, arrayList, 0, 0, 0);
        if (arrayList.isEmpty()) {
            throw NotFoundException.getNotFoundInstance();
        }
        return (Result[]) arrayList.toArray(new Result[arrayList.size()]);
    }
}
