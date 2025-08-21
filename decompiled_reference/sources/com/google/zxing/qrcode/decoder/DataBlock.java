package com.google.zxing.qrcode.decoder;

import com.google.zxing.qrcode.decoder.Version;

/* loaded from: classes.dex */
final class DataBlock {
    private final byte[] codewords;
    private final int numDataCodewords;

    private DataBlock(int i, byte[] bArr) {
        this.numDataCodewords = i;
        this.codewords = bArr;
    }

    static DataBlock[] getDataBlocks(byte[] bArr, Version version, ErrorCorrectionLevel errorCorrectionLevel) {
        if (bArr.length != version.getTotalCodewords()) {
            throw new IllegalArgumentException();
        }
        Version.ECBlocks eCBlocksForLevel = version.getECBlocksForLevel(errorCorrectionLevel);
        Version.ECB[] eCBlocks = eCBlocksForLevel.getECBlocks();
        int count = 0;
        for (Version.ECB ecb : eCBlocks) {
            count += ecb.getCount();
        }
        DataBlock[] dataBlockArr = new DataBlock[count];
        int length = eCBlocks.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            Version.ECB ecb2 = eCBlocks[i];
            int i3 = i2;
            int i4 = 0;
            while (i4 < ecb2.getCount()) {
                int dataCodewords = ecb2.getDataCodewords();
                dataBlockArr[i3] = new DataBlock(dataCodewords, new byte[eCBlocksForLevel.getECCodewordsPerBlock() + dataCodewords]);
                i4++;
                i3++;
            }
            i++;
            i2 = i3;
        }
        int length2 = dataBlockArr[0].codewords.length;
        int length3 = dataBlockArr.length - 1;
        while (length3 >= 0 && dataBlockArr[length3].codewords.length != length2) {
            length3--;
        }
        int i5 = length3 + 1;
        int eCCodewordsPerBlock = length2 - eCBlocksForLevel.getECCodewordsPerBlock();
        int i6 = 0;
        int i7 = 0;
        while (i6 < eCCodewordsPerBlock) {
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
        int i10 = i5;
        while (i10 < i2) {
            dataBlockArr[i10].codewords[eCCodewordsPerBlock] = bArr[i7];
            i10++;
            i7++;
        }
        int length4 = dataBlockArr[0].codewords.length;
        while (eCCodewordsPerBlock < length4) {
            int i11 = i7;
            int i12 = 0;
            while (i12 < i2) {
                dataBlockArr[i12].codewords[i12 < i5 ? eCCodewordsPerBlock : eCCodewordsPerBlock + 1] = bArr[i11];
                i12++;
                i11++;
            }
            eCCodewordsPerBlock++;
            i7 = i11;
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
