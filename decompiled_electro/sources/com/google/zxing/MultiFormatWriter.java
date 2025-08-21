package com.google.zxing;

import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.Code93Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.oned.UPCEWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Map;

/* loaded from: classes.dex */
public final class MultiFormatWriter implements Writer {
    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2) {
        return encode(str, barcodeFormat, i, i2, null);
    }

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2, Map<EncodeHintType, ?> map) {
        Writer eAN8Writer;
        switch (barcodeFormat) {
            case EAN_8:
                eAN8Writer = new EAN8Writer();
                break;
            case UPC_E:
                eAN8Writer = new UPCEWriter();
                break;
            case EAN_13:
                eAN8Writer = new EAN13Writer();
                break;
            case UPC_A:
                eAN8Writer = new UPCAWriter();
                break;
            case QR_CODE:
                eAN8Writer = new QRCodeWriter();
                break;
            case CODE_39:
                eAN8Writer = new Code39Writer();
                break;
            case CODE_93:
                eAN8Writer = new Code93Writer();
                break;
            case CODE_128:
                eAN8Writer = new Code128Writer();
                break;
            case ITF:
                eAN8Writer = new ITFWriter();
                break;
            case PDF_417:
                eAN8Writer = new PDF417Writer();
                break;
            case CODABAR:
                eAN8Writer = new CodaBarWriter();
                break;
            case DATA_MATRIX:
                eAN8Writer = new DataMatrixWriter();
                break;
            case AZTEC:
                eAN8Writer = new AztecWriter();
                break;
            default:
                throw new IllegalArgumentException("No encoder available for format " + barcodeFormat);
        }
        return eAN8Writer.encode(str, barcodeFormat, i, i2, map);
    }
}
