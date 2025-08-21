package com.google.zxing.aztec;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.Reader;
import com.google.zxing.Result;

/* loaded from: classes.dex */
public final class AztecReader implements Reader {
    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) {
        return decode(binaryBitmap, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x005c A[LOOP:0: B:31:0x005a->B:32:0x005c, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x008d  */
    @Override // com.google.zxing.Reader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.google.zxing.Result decode(com.google.zxing.BinaryBitmap r12, java.util.Map<com.google.zxing.DecodeHintType, ?> r13) throws com.google.zxing.NotFoundException, com.google.zxing.FormatException {
        /*
            r11 = this;
            com.google.zxing.aztec.detector.Detector r11 = new com.google.zxing.aztec.detector.Detector
            com.google.zxing.common.BitMatrix r12 = r12.getBlackMatrix()
            r11.<init>(r12)
            r12 = 0
            r0 = 0
            com.google.zxing.aztec.AztecDetectorResult r1 = r11.detect(r12)     // Catch: com.google.zxing.FormatException -> L25 com.google.zxing.NotFoundException -> L2b
            com.google.zxing.ResultPoint[] r2 = r1.getPoints()     // Catch: com.google.zxing.FormatException -> L25 com.google.zxing.NotFoundException -> L2b
            com.google.zxing.aztec.decoder.Decoder r3 = new com.google.zxing.aztec.decoder.Decoder     // Catch: com.google.zxing.FormatException -> L21 com.google.zxing.NotFoundException -> L23
            r3.<init>()     // Catch: com.google.zxing.FormatException -> L21 com.google.zxing.NotFoundException -> L23
            com.google.zxing.common.DecoderResult r1 = r3.decode(r1)     // Catch: com.google.zxing.FormatException -> L21 com.google.zxing.NotFoundException -> L23
            r3 = r2
            r2 = r0
            r0 = r1
            r1 = r2
            goto L2f
        L21:
            r1 = move-exception
            goto L27
        L23:
            r1 = move-exception
            goto L2d
        L25:
            r1 = move-exception
            r2 = r0
        L27:
            r3 = r2
            r2 = r1
            r1 = r0
            goto L2f
        L2b:
            r1 = move-exception
            r2 = r0
        L2d:
            r3 = r2
            r2 = r0
        L2f:
            if (r0 != 0) goto L4c
            r0 = 1
            com.google.zxing.aztec.AztecDetectorResult r11 = r11.detect(r0)     // Catch: java.lang.Throwable -> L44
            com.google.zxing.ResultPoint[] r3 = r11.getPoints()     // Catch: java.lang.Throwable -> L44
            com.google.zxing.aztec.decoder.Decoder r0 = new com.google.zxing.aztec.decoder.Decoder     // Catch: java.lang.Throwable -> L44
            r0.<init>()     // Catch: java.lang.Throwable -> L44
            com.google.zxing.common.DecoderResult r0 = r0.decode(r11)     // Catch: java.lang.Throwable -> L44
            goto L4c
        L44:
            r11 = move-exception
            if (r1 == 0) goto L48
            throw r1
        L48:
            if (r2 == 0) goto L4b
            throw r2
        L4b:
            throw r11
        L4c:
            r7 = r3
            if (r13 == 0) goto L64
            com.google.zxing.DecodeHintType r11 = com.google.zxing.DecodeHintType.NEED_RESULT_POINT_CALLBACK
            java.lang.Object r11 = r13.get(r11)
            com.google.zxing.ResultPointCallback r11 = (com.google.zxing.ResultPointCallback) r11
            if (r11 == 0) goto L64
            int r13 = r7.length
        L5a:
            if (r12 >= r13) goto L64
            r1 = r7[r12]
            r11.foundPossibleResultPoint(r1)
            int r12 = r12 + 1
            goto L5a
        L64:
            com.google.zxing.Result r11 = new com.google.zxing.Result
            java.lang.String r4 = r0.getText()
            byte[] r5 = r0.getRawBytes()
            int r6 = r0.getNumBits()
            com.google.zxing.BarcodeFormat r8 = com.google.zxing.BarcodeFormat.AZTEC
            long r9 = java.lang.System.currentTimeMillis()
            r3 = r11
            r3.<init>(r4, r5, r6, r7, r8, r9)
            java.util.List r12 = r0.getByteSegments()
            if (r12 == 0) goto L87
            com.google.zxing.ResultMetadataType r13 = com.google.zxing.ResultMetadataType.BYTE_SEGMENTS
            r11.putMetadata(r13, r12)
        L87:
            java.lang.String r12 = r0.getECLevel()
            if (r12 == 0) goto L92
            com.google.zxing.ResultMetadataType r13 = com.google.zxing.ResultMetadataType.ERROR_CORRECTION_LEVEL
            r11.putMetadata(r13, r12)
        L92:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.aztec.AztecReader.decode(com.google.zxing.BinaryBitmap, java.util.Map):com.google.zxing.Result");
    }

    @Override // com.google.zxing.Reader
    public void reset() {
    }
}
