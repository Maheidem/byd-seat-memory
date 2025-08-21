package com.google.zxing.datamatrix.encoder;

/* loaded from: classes.dex */
class C40Encoder implements Encoder {
    C40Encoder() {
    }

    private int backtrackOneCharacter(EncoderContext encoderContext, StringBuilder sb, StringBuilder sb2, int i) {
        int length = sb.length();
        sb.delete(length - i, length);
        encoderContext.pos--;
        int iEncodeChar = encodeChar(encoderContext.getCurrentChar(), sb2);
        encoderContext.resetSymbolInfo();
        return iEncodeChar;
    }

    private static String encodeToCodewords(CharSequence charSequence, int i) {
        int iCharAt = (charSequence.charAt(i) * 1600) + (charSequence.charAt(i + 1) * '(') + charSequence.charAt(i + 2) + 1;
        return new String(new char[]{(char) (iCharAt / 256), (char) (iCharAt % 256)});
    }

    static void writeNextTriplet(EncoderContext encoderContext, StringBuilder sb) {
        encoderContext.writeCodewords(encodeToCodewords(sb, 0));
        sb.delete(0, 3);
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public void encode(EncoderContext encoderContext) {
        int iLookAheadTest;
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!encoderContext.hasMoreCharacters()) {
                break;
            }
            char currentChar = encoderContext.getCurrentChar();
            encoderContext.pos++;
            int iEncodeChar = encodeChar(currentChar, sb);
            int codewordCount = encoderContext.getCodewordCount() + ((sb.length() / 3) << 1);
            encoderContext.updateSymbolInfo(codewordCount);
            int dataCapacity = encoderContext.getSymbolInfo().getDataCapacity() - codewordCount;
            if (!encoderContext.hasMoreCharacters()) {
                StringBuilder sb2 = new StringBuilder();
                if (sb.length() % 3 == 2 && (dataCapacity < 2 || dataCapacity > 2)) {
                    iEncodeChar = backtrackOneCharacter(encoderContext, sb, sb2, iEncodeChar);
                }
                while (sb.length() % 3 == 1) {
                    if (iEncodeChar <= 3 && dataCapacity != 1) {
                        iEncodeChar = backtrackOneCharacter(encoderContext, sb, sb2, iEncodeChar);
                    } else if (iEncodeChar <= 3) {
                        break;
                    } else {
                        iEncodeChar = backtrackOneCharacter(encoderContext, sb, sb2, iEncodeChar);
                    }
                }
            } else if (sb.length() % 3 == 0 && (iLookAheadTest = HighLevelEncoder.lookAheadTest(encoderContext.getMessage(), encoderContext.pos, getEncodingMode())) != getEncodingMode()) {
                encoderContext.signalEncoderChange(iLookAheadTest);
                break;
            }
        }
        handleEOD(encoderContext, sb);
    }

    int encodeChar(char c, StringBuilder sb) {
        int i;
        char c2;
        if (c == ' ') {
            c2 = 3;
        } else {
            if (c >= '0' && c <= '9') {
                i = (c - '0') + 4;
            } else {
                if (c < 'A' || c > 'Z') {
                    if (c >= 0 && c <= 31) {
                        sb.append((char) 0);
                        sb.append(c);
                        return 2;
                    }
                    if (c >= '!' && c <= '/') {
                        sb.append((char) 1);
                        sb.append((char) (c - '!'));
                        return 2;
                    }
                    if (c >= ':' && c <= '@') {
                        sb.append((char) 1);
                        sb.append((char) ((c - ':') + 15));
                        return 2;
                    }
                    if (c >= '[' && c <= '_') {
                        sb.append((char) 1);
                        sb.append((char) ((c - '[') + 22));
                        return 2;
                    }
                    if (c >= '`' && c <= 127) {
                        sb.append((char) 2);
                        sb.append((char) (c - '`'));
                        return 2;
                    }
                    if (c >= 128) {
                        sb.append("\u0001\u001e");
                        return 2 + encodeChar((char) (c - 128), sb);
                    }
                    throw new IllegalArgumentException("Illegal character: " + c);
                }
                i = (c - 'A') + 14;
            }
            c2 = (char) i;
        }
        sb.append(c2);
        return 1;
    }

    @Override // com.google.zxing.datamatrix.encoder.Encoder
    public int getEncodingMode() {
        return 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void handleEOD(com.google.zxing.datamatrix.encoder.EncoderContext r7, java.lang.StringBuilder r8) {
        /*
            r6 = this;
            int r6 = r8.length()
            r0 = 3
            int r6 = r6 / r0
            r1 = 1
            int r6 = r6 << r1
            int r2 = r8.length()
            int r2 = r2 % r0
            int r3 = r7.getCodewordCount()
            int r3 = r3 + r6
            r7.updateSymbolInfo(r3)
            com.google.zxing.datamatrix.encoder.SymbolInfo r6 = r7.getSymbolInfo()
            int r6 = r6.getDataCapacity()
            int r6 = r6 - r3
            r3 = 0
            r4 = 254(0xfe, float:3.56E-43)
            r5 = 2
            if (r2 != r5) goto L3b
            r8.append(r3)
        L27:
            int r6 = r8.length()
            if (r6 < r0) goto L31
            writeNextTriplet(r7, r8)
            goto L27
        L31:
            boolean r6 = r7.hasMoreCharacters()
            if (r6 == 0) goto L6d
        L37:
            r7.writeCodeword(r4)
            goto L6d
        L3b:
            if (r6 != r1) goto L58
            if (r2 != r1) goto L58
        L3f:
            int r6 = r8.length()
            if (r6 < r0) goto L49
            writeNextTriplet(r7, r8)
            goto L3f
        L49:
            boolean r6 = r7.hasMoreCharacters()
            if (r6 == 0) goto L52
            r7.writeCodeword(r4)
        L52:
            int r6 = r7.pos
            int r6 = r6 - r1
            r7.pos = r6
            goto L6d
        L58:
            if (r2 != 0) goto L71
        L5a:
            int r1 = r8.length()
            if (r1 < r0) goto L64
            writeNextTriplet(r7, r8)
            goto L5a
        L64:
            if (r6 > 0) goto L37
            boolean r6 = r7.hasMoreCharacters()
            if (r6 == 0) goto L6d
            goto L37
        L6d:
            r7.signalEncoderChange(r3)
            return
        L71:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "Unexpected case. Please report!"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.encoder.C40Encoder.handleEOD(com.google.zxing.datamatrix.encoder.EncoderContext, java.lang.StringBuilder):void");
    }
}
