package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import java.util.Map;

/* loaded from: classes.dex */
public interface MultipleBarcodeReader {
    Result[] decodeMultiple(BinaryBitmap binaryBitmap);

    Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map);
}
