package io.socket.engineio.parser;

import java.io.UnsupportedEncodingException;

/* loaded from: classes.dex */
public class Base64 {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CRLF = 4;
    public static final int DEFAULT = 0;
    public static final int NO_CLOSE = 16;
    public static final int NO_PADDING = 1;
    public static final int NO_WRAP = 2;
    public static final int URL_SAFE = 8;

    static abstract class Coder {
        public int op;
        public byte[] output;

        Coder() {
        }

        public abstract int maxOutputSize(int i);

        public abstract boolean process(byte[] bArr, int i, int i2, boolean z);
    }

    static class Decoder extends Coder {
        private static final int[] DECODE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int[] DECODE_WEBSAFE = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        private static final int EQUALS = -2;
        private static final int SKIP = -1;
        private final int[] alphabet;
        private int state;
        private int value;

        public Decoder(int i, byte[] bArr) {
            this.output = bArr;
            this.alphabet = (i & 8) == 0 ? DECODE : DECODE_WEBSAFE;
            this.state = 0;
            this.value = 0;
        }

        @Override // io.socket.engineio.parser.Base64.Coder
        public int maxOutputSize(int i) {
            return ((i * 3) / 4) + 10;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Removed duplicated region for block: B:55:0x00de  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x00e5  */
        @Override // io.socket.engineio.parser.Base64.Coder
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean process(byte[] r12, int r13, int r14, boolean r15) {
            /*
                Method dump skipped, instructions count: 296
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: io.socket.engineio.parser.Base64.Decoder.process(byte[], int, int, boolean):boolean");
        }
    }

    static class Encoder extends Coder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final byte[] ENCODE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        private static final byte[] ENCODE_WEBSAFE = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        public static final int LINE_GROUPS = 19;
        private final byte[] alphabet;
        private int count;
        public final boolean do_cr;
        public final boolean do_newline;
        public final boolean do_padding;
        private final byte[] tail;
        int tailLen;

        public Encoder(int i, byte[] bArr) {
            this.output = bArr;
            this.do_padding = (i & 1) == 0;
            this.do_newline = (i & 2) == 0;
            this.do_cr = (i & 4) != 0;
            this.alphabet = (i & 8) == 0 ? ENCODE : ENCODE_WEBSAFE;
            this.tail = new byte[2];
            this.tailLen = 0;
            this.count = this.do_newline ? 19 : -1;
        }

        @Override // io.socket.engineio.parser.Base64.Coder
        public int maxOutputSize(int i) {
            return ((i * 8) / 5) + 10;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // io.socket.engineio.parser.Base64.Coder
        public boolean process(byte[] bArr, int i, int i2, boolean z) {
            int i3;
            int i4;
            int i5;
            int i6;
            boolean z2;
            int i7;
            byte b;
            byte b2;
            int i8;
            byte b3;
            int i9;
            int i10;
            int i11;
            byte[] bArr2 = this.alphabet;
            byte[] bArr3 = this.output;
            int i12 = this.count;
            int i13 = i2 + i;
            int i14 = 0;
            switch (this.tailLen) {
                case 0:
                default:
                    i3 = i;
                    i4 = -1;
                    break;
                case 1:
                    if (i + 2 <= i13) {
                        int i15 = i + 1;
                        i3 = i15 + 1;
                        i4 = ((bArr[i] & 255) << 8) | ((this.tail[0] & 255) << 16) | (bArr[i15] & 255);
                        this.tailLen = 0;
                        break;
                    }
                    i3 = i;
                    i4 = -1;
                    break;
                case 2:
                    i3 = i + 1;
                    if (i3 <= i13) {
                        i4 = (bArr[i] & 255) | ((this.tail[0] & 255) << 16) | ((this.tail[1] & 255) << 8);
                        this.tailLen = 0;
                        break;
                    }
                    i3 = i;
                    i4 = -1;
                    break;
            }
            if (i4 != -1) {
                bArr3[0] = bArr2[(i4 >> 18) & 63];
                bArr3[1] = bArr2[(i4 >> 12) & 63];
                bArr3[2] = bArr2[(i4 >> 6) & 63];
                bArr3[3] = bArr2[i4 & 63];
                int i16 = i12 - 1;
                if (i16 == 0) {
                    if (this.do_cr) {
                        i11 = 5;
                        bArr3[4] = 13;
                    } else {
                        i11 = 4;
                    }
                    i6 = i11 + 1;
                    bArr3[i11] = 10;
                    i5 = 19;
                } else {
                    i5 = i16;
                    i6 = 4;
                }
            } else {
                i5 = i12;
                i6 = 0;
            }
            while (true) {
                int i17 = i3 + 3;
                if (i17 > i13) {
                    if (z) {
                        if (i3 - this.tailLen == i13 - 1) {
                            if (this.tailLen > 0) {
                                b3 = this.tail[0];
                                i14 = 1;
                            } else {
                                b3 = bArr[i3];
                            }
                            int i18 = (b3 & 255) << 4;
                            this.tailLen -= i14;
                            int i19 = i6 + 1;
                            bArr3[i6] = bArr2[(i18 >> 6) & 63];
                            i6 = i19 + 1;
                            bArr3[i19] = bArr2[i18 & 63];
                            if (this.do_padding) {
                                int i20 = i6 + 1;
                                bArr3[i6] = 61;
                                i6 = i20 + 1;
                                bArr3[i20] = 61;
                            }
                            if (this.do_newline) {
                                if (this.do_cr) {
                                    i9 = i6 + 1;
                                    bArr3[i6] = 13;
                                } else {
                                    i9 = i6;
                                }
                                i6 = i9 + 1;
                                bArr3[i9] = 10;
                            }
                        } else if (i3 - this.tailLen == i13 - 2) {
                            if (this.tailLen > 1) {
                                b = this.tail[0];
                                i14 = 1;
                            } else {
                                int i21 = i3 + 1;
                                byte b4 = bArr[i3];
                                i3 = i21;
                                b = b4;
                            }
                            int i22 = (b & 255) << 10;
                            if (this.tailLen > 0) {
                                b2 = this.tail[i14];
                                i14++;
                            } else {
                                b2 = bArr[i3];
                            }
                            int i23 = ((b2 & 255) << 2) | i22;
                            this.tailLen -= i14;
                            int i24 = i6 + 1;
                            bArr3[i6] = bArr2[(i23 >> 12) & 63];
                            int i25 = i24 + 1;
                            bArr3[i24] = bArr2[(i23 >> 6) & 63];
                            int i26 = i25 + 1;
                            bArr3[i25] = bArr2[i23 & 63];
                            if (this.do_padding) {
                                i8 = i26 + 1;
                                bArr3[i26] = 61;
                            } else {
                                i8 = i26;
                            }
                            if (this.do_newline) {
                                if (this.do_cr) {
                                    bArr3[i8] = 13;
                                    i8++;
                                }
                                bArr3[i8] = 10;
                                i8++;
                            }
                            i6 = i8;
                        } else if (this.do_newline && i6 > 0 && i5 != 19) {
                            if (this.do_cr) {
                                i7 = i6 + 1;
                                bArr3[i6] = 13;
                            } else {
                                i7 = i6;
                            }
                            bArr3[i7] = 10;
                            i6 = i7 + 1;
                        }
                    } else {
                        if (i3 != i13 - 1) {
                            if (i3 == i13 - 2) {
                                byte[] bArr4 = this.tail;
                                int i27 = this.tailLen;
                                this.tailLen = i27 + 1;
                                bArr4[i27] = bArr[i3];
                                byte[] bArr5 = this.tail;
                                int i28 = this.tailLen;
                                this.tailLen = i28 + 1;
                                z2 = true;
                                bArr5[i28] = bArr[i3 + 1];
                            }
                            this.op = i6;
                            this.count = i5;
                            return z2;
                        }
                        byte[] bArr6 = this.tail;
                        int i29 = this.tailLen;
                        this.tailLen = i29 + 1;
                        bArr6[i29] = bArr[i3];
                    }
                    z2 = true;
                    this.op = i6;
                    this.count = i5;
                    return z2;
                }
                int i30 = (bArr[i3 + 2] & 255) | ((bArr[i3 + 1] & 255) << 8) | ((bArr[i3] & 255) << 16);
                bArr3[i6] = bArr2[(i30 >> 18) & 63];
                bArr3[i6 + 1] = bArr2[(i30 >> 12) & 63];
                bArr3[i6 + 2] = bArr2[(i30 >> 6) & 63];
                bArr3[i6 + 3] = bArr2[i30 & 63];
                i6 += 4;
                i5--;
                if (i5 == 0) {
                    if (this.do_cr) {
                        i10 = i6 + 1;
                        bArr3[i6] = 13;
                    } else {
                        i10 = i6;
                    }
                    i6 = i10 + 1;
                    bArr3[i10] = 10;
                    i3 = i17;
                    i5 = 19;
                } else {
                    i3 = i17;
                }
            }
        }
    }

    private Base64() {
    }

    public static byte[] decode(String str, int i) {
        return decode(str.getBytes(), i);
    }

    public static byte[] decode(byte[] bArr, int i) {
        return decode(bArr, 0, bArr.length, i);
    }

    public static byte[] decode(byte[] bArr, int i, int i2, int i3) {
        Decoder decoder = new Decoder(i3, new byte[(i2 * 3) / 4]);
        if (!decoder.process(bArr, i, i2, true)) {
            throw new IllegalArgumentException("bad base-64");
        }
        if (decoder.op == decoder.output.length) {
            return decoder.output;
        }
        byte[] bArr2 = new byte[decoder.op];
        System.arraycopy(decoder.output, 0, bArr2, 0, decoder.op);
        return bArr2;
    }

    public static byte[] encode(byte[] bArr, int i) {
        return encode(bArr, 0, bArr.length, i);
    }

    public static byte[] encode(byte[] bArr, int i, int i2, int i3) {
        Encoder encoder = new Encoder(i3, null);
        int i4 = (i2 / 3) * 4;
        if (!encoder.do_padding) {
            switch (i2 % 3) {
                case 1:
                    i4 += 2;
                    break;
                case 2:
                    i4 += 3;
                    break;
            }
        } else if (i2 % 3 > 0) {
            i4 += 4;
        }
        if (encoder.do_newline && i2 > 0) {
            i4 += (((i2 - 1) / 57) + 1) * (encoder.do_cr ? 2 : 1);
        }
        encoder.output = new byte[i4];
        encoder.process(bArr, i, i2, true);
        return encoder.output;
    }

    public static String encodeToString(byte[] bArr, int i) {
        try {
            return new String(encode(bArr, i), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public static String encodeToString(byte[] bArr, int i, int i2, int i3) {
        try {
            return new String(encode(bArr, i, i2, i3), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
