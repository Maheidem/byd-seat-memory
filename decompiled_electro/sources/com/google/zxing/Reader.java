package com.google.zxing;

import java.util.Map;

/* loaded from: classes.dex */
public interface Reader {
    Result decode(BinaryBitmap binaryBitmap);

    Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map);

    void reset();
}
