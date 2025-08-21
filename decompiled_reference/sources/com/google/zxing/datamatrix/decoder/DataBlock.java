package com.google.zxing.datamatrix.decoder;

import com.google.zxing.datamatrix.decoder.Version;

/* loaded from: classes.dex */
final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int i, byte[] bArr) {
        this.numDataCodewords = i;
        this.codewords = bArr;
    }

    static DataBlock[] getDataBlocks(byte[] bArr, Version version) {
        Version.ECBlocks eCBlocks = version.getECBlocks();
        Version.ECB[] eCBlocks2 = eCBlocks.getECBlocks();
        int count = 0;
        for (Version.ECB ecb : eCBlocks2) {
            count += ecb.getCount();
        }
        DataBlock[] dataBlockArr = new DataBlock[count];
        int length = eCBlocks2.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            Version.ECB ecb2 = eCBlocks2[i];
            int i3 = i2;
            int i4 = 0;
            while (i4 < ecb2.getCount()) {
                int dataCodewords = ecb2.getDataCodewords();
                dataBlockArr[i3] = new DataBlock(dataCodewords, new byte[eCBlocks.getECCodewords() + dataCodewords]);
                i4++;
                i3++;
            }
            i++;
            i2 = i3;
        }
        int length2 = dataBlockArr[0].codewords.length - eCBlocks.getECCodewords();
        int i5 = length2 - 1;
        int i6 = 0;
        int i7 = 0;
        while (i6 < i5) {
            int i8 = i7;
            int i9 = 0;
            while (i9 < i2) {
                dataBlockArr[i9].codewords[i6] = bArr[i8];
                i9++;
                i8++;
            }
            i6++;
            i7 = i8;
        }
        boolean z = version.getVersionNumber() == 24;
        int i10 = z ? 8 : i2;
        int i11 = i7;
        int i12 = 0;
        while (i12 < i10) {
            dataBlockArr[i12].codewords[i5] = bArr[i11];
            i12++;
            i11++;
        }
        int length3 = dataBlockArr[0].codewords.length;
        while (length2 < length3) {
            int i13 = 0;
            while (i13 < i2) {
                int i14 = z ? (i13 + 8) % i2 : i13;
                dataBlockArr[i14].codewords[(!z || i14 <= 7) ? length2 : length2 - 1] = bArr[i11];
                i13++;
                i11++;
            }
            length2++;
        }
        if (i11 != bArr.length) {
            throw new IllegalArgumentException();
        }
        return dataBlockArr;
    }

    byte[] getCodewords() {
        return this.codewords;
    }

    int getNumDataCodewords() {
        return this.numDataCodewords;
    }
}
