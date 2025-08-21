package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.BitArray;
import java.util.Collection;
import java.util.Map;

/* loaded from: classes.dex */
public final class MultiFormatUPCEANReader extends OneDReader {
    private final UPCEANReader[] readers;

    /* JADX WARN: Removed duplicated region for block: B:17:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public MultiFormatUPCEANReader(java.util.Map<com.google.zxing.DecodeHintType, ?> r3) {
        /*
            r2 = this;
            r2.<init>()
            if (r3 != 0) goto L7
            r3 = 0
            goto Lf
        L7:
            com.google.zxing.DecodeHintType r0 = com.google.zxing.DecodeHintType.POSSIBLE_FORMATS
            java.lang.Object r3 = r3.get(r0)
            java.util.Collection r3 = (java.util.Collection) r3
        Lf:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            if (r3 == 0) goto L55
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.EAN_13
            boolean r1 = r3.contains(r1)
            if (r1 == 0) goto L27
            com.google.zxing.oned.EAN13Reader r1 = new com.google.zxing.oned.EAN13Reader
            r1.<init>()
        L23:
            r0.add(r1)
            goto L35
        L27:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.UPC_A
            boolean r1 = r3.contains(r1)
            if (r1 == 0) goto L35
            com.google.zxing.oned.UPCAReader r1 = new com.google.zxing.oned.UPCAReader
            r1.<init>()
            goto L23
        L35:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.EAN_8
            boolean r1 = r3.contains(r1)
            if (r1 == 0) goto L45
            com.google.zxing.oned.EAN8Reader r1 = new com.google.zxing.oned.EAN8Reader
            r1.<init>()
            r0.add(r1)
        L45:
            com.google.zxing.BarcodeFormat r1 = com.google.zxing.BarcodeFormat.UPC_E
            boolean r3 = r3.contains(r1)
            if (r3 == 0) goto L55
            com.google.zxing.oned.UPCEReader r3 = new com.google.zxing.oned.UPCEReader
            r3.<init>()
            r0.add(r3)
        L55:
            boolean r3 = r0.isEmpty()
            if (r3 == 0) goto L73
            com.google.zxing.oned.EAN13Reader r3 = new com.google.zxing.oned.EAN13Reader
            r3.<init>()
            r0.add(r3)
            com.google.zxing.oned.EAN8Reader r3 = new com.google.zxing.oned.EAN8Reader
            r3.<init>()
            r0.add(r3)
            com.google.zxing.oned.UPCEReader r3 = new com.google.zxing.oned.UPCEReader
            r3.<init>()
            r0.add(r3)
        L73:
            int r3 = r0.size()
            com.google.zxing.oned.UPCEANReader[] r3 = new com.google.zxing.oned.UPCEANReader[r3]
            java.lang.Object[] r3 = r0.toArray(r3)
            com.google.zxing.oned.UPCEANReader[] r3 = (com.google.zxing.oned.UPCEANReader[]) r3
            r2.readers = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.MultiFormatUPCEANReader.<init>(java.util.Map):void");
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        int[] iArrFindStartGuardPattern = UPCEANReader.findStartGuardPattern(bitArray);
        for (UPCEANReader uPCEANReader : this.readers) {
            try {
                Result resultDecodeRow = uPCEANReader.decodeRow(i, bitArray, iArrFindStartGuardPattern, map);
                boolean z = resultDecodeRow.getBarcodeFormat() == BarcodeFormat.EAN_13 && resultDecodeRow.getText().charAt(0) == '0';
                Collection collection = map == null ? null : (Collection) map.get(DecodeHintType.POSSIBLE_FORMATS);
                boolean z2 = collection == null || collection.contains(BarcodeFormat.UPC_A);
                if (!z || !z2) {
                    return resultDecodeRow;
                }
                Result result = new Result(resultDecodeRow.getText().substring(1), resultDecodeRow.getRawBytes(), resultDecodeRow.getResultPoints(), BarcodeFormat.UPC_A);
                result.putAllMetadata(resultDecodeRow.getResultMetadata());
                return result;
            } catch (ReaderException unused) {
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    @Override // com.google.zxing.oned.OneDReader, com.google.zxing.Reader
    public void reset() {
        for (UPCEANReader uPCEANReader : this.readers) {
            uPCEANReader.reset();
        }
    }
}
