package com.google.zxing.datamatrix.decoder;

import android.support.v7.widget.helper.ItemTouchHelper;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

/* loaded from: classes.dex */
final class DecodedBitStreamParser {
    private static final char[] C40_BASIC_SET_CHARS = {'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] C40_SHIFT2_SET_CHARS = {'!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_'};
    private static final char[] TEXT_BASIC_SET_CHARS = {'*', '*', '*', ' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] TEXT_SHIFT2_SET_CHARS = C40_SHIFT2_SET_CHARS;
    private static final char[] TEXT_SHIFT3_SET_CHARS = {'`', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '{', '|', '}', '~', 127};

    private enum Mode {
        PAD_ENCODE,
        ASCII_ENCODE,
        C40_ENCODE,
        TEXT_ENCODE,
        ANSIX12_ENCODE,
        EDIFACT_ENCODE,
        BASE256_ENCODE
    }

    private DecodedBitStreamParser() {
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static com.google.zxing.common.DecoderResult decode(byte[] r6) throws com.google.zxing.FormatException {
        /*
            com.google.zxing.common.BitSource r0 = new com.google.zxing.common.BitSource
            r0.<init>(r6)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r2 = 100
            r1.<init>(r2)
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 0
            r2.<init>(r3)
            java.util.ArrayList r3 = new java.util.ArrayList
            r4 = 1
            r3.<init>(r4)
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r4 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ASCII_ENCODE
        L1a:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r5 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ASCII_ENCODE
            if (r4 != r5) goto L23
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r4 = decodeAsciiSegment(r0, r1, r2)
            goto L48
        L23:
            int[] r5 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.AnonymousClass1.$SwitchMap$com$google$zxing$datamatrix$decoder$DecodedBitStreamParser$Mode
            int r4 = r4.ordinal()
            r4 = r5[r4]
            switch(r4) {
                case 1: goto L43;
                case 2: goto L3f;
                case 3: goto L3b;
                case 4: goto L37;
                case 5: goto L33;
                default: goto L2e;
            }
        L2e:
            com.google.zxing.FormatException r6 = com.google.zxing.FormatException.getFormatInstance()
            throw r6
        L33:
            decodeBase256Segment(r0, r1, r3)
            goto L46
        L37:
            decodeEdifactSegment(r0, r1)
            goto L46
        L3b:
            decodeAnsiX12Segment(r0, r1)
            goto L46
        L3f:
            decodeTextSegment(r0, r1)
            goto L46
        L43:
            decodeC40Segment(r0, r1)
        L46:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r4 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.ASCII_ENCODE
        L48:
            com.google.zxing.datamatrix.decoder.DecodedBitStreamParser$Mode r5 = com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.Mode.PAD_ENCODE
            if (r4 == r5) goto L52
            int r5 = r0.available()
            if (r5 > 0) goto L1a
        L52:
            int r0 = r2.length()
            if (r0 <= 0) goto L5b
            r1.append(r2)
        L5b:
            com.google.zxing.common.DecoderResult r0 = new com.google.zxing.common.DecoderResult
            java.lang.String r1 = r1.toString()
            boolean r2 = r3.isEmpty()
            r4 = 0
            if (r2 == 0) goto L69
            r3 = r4
        L69:
            r0.<init>(r6, r1, r3, r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.decoder.DecodedBitStreamParser.decode(byte[]):com.google.zxing.common.DecoderResult");
    }

    private static void decodeAnsiX12Segment(BitSource bitSource, StringBuilder sb) throws FormatException {
        int bits;
        int i;
        char c;
        int[] iArr = new int[3];
        while (bitSource.available() != 8 && (bits = bitSource.readBits(8)) != 254) {
            parseTwoBytes(bits, bitSource.readBits(8), iArr);
            for (int i2 = 0; i2 < 3; i2++) {
                int i3 = iArr[i2];
                if (i3 == 0) {
                    c = '\r';
                } else if (i3 == 1) {
                    c = '*';
                } else if (i3 == 2) {
                    c = '>';
                } else if (i3 == 3) {
                    c = ' ';
                } else {
                    if (i3 < 14) {
                        i = i3 + 44;
                    } else {
                        if (i3 >= 40) {
                            throw FormatException.getFormatInstance();
                        }
                        i = i3 + 51;
                    }
                    c = (char) i;
                }
                sb.append(c);
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static Mode decodeAsciiSegment(BitSource bitSource, StringBuilder sb, StringBuilder sb2) throws FormatException {
        String str;
        boolean z = false;
        do {
            int bits = bitSource.readBits(8);
            if (bits == 0) {
                throw FormatException.getFormatInstance();
            }
            if (bits <= 128) {
                if (z) {
                    bits += 128;
                }
                sb.append((char) (bits - 1));
                return Mode.ASCII_ENCODE;
            }
            if (bits == 129) {
                return Mode.PAD_ENCODE;
            }
            if (bits <= 229) {
                int i = bits - 130;
                if (i < 10) {
                    sb.append('0');
                }
                sb.append(i);
            } else {
                if (bits == 230) {
                    return Mode.C40_ENCODE;
                }
                if (bits == 231) {
                    return Mode.BASE256_ENCODE;
                }
                if (bits == 232) {
                    sb.append((char) 29);
                } else if (bits != 233 && bits != 234) {
                    if (bits == 235) {
                        z = true;
                    } else {
                        if (bits == 236) {
                            str = "[)>\u001e05\u001d";
                        } else if (bits == 237) {
                            str = "[)>\u001e06\u001d";
                        } else {
                            if (bits == 238) {
                                return Mode.ANSIX12_ENCODE;
                            }
                            if (bits == 239) {
                                return Mode.TEXT_ENCODE;
                            }
                            if (bits == 240) {
                                return Mode.EDIFACT_ENCODE;
                            }
                            if (bits != 241 && bits >= 242 && (bits != 254 || bitSource.available() != 0)) {
                                throw FormatException.getFormatInstance();
                            }
                        }
                        sb.append(str);
                        sb2.insert(0, "\u001e\u0004");
                    }
                }
            }
        } while (bitSource.available() > 0);
        return Mode.ASCII_ENCODE;
    }

    private static void decodeBase256Segment(BitSource bitSource, StringBuilder sb, Collection<byte[]> collection) throws FormatException {
        int byteOffset = 1 + bitSource.getByteOffset();
        int i = byteOffset + 1;
        int iUnrandomize255State = unrandomize255State(bitSource.readBits(8), byteOffset);
        if (iUnrandomize255State == 0) {
            iUnrandomize255State = bitSource.available() / 8;
        } else if (iUnrandomize255State >= 250) {
            iUnrandomize255State = unrandomize255State(bitSource.readBits(8), i) + (ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION * (iUnrandomize255State - 249));
            i++;
        }
        if (iUnrandomize255State < 0) {
            throw FormatException.getFormatInstance();
        }
        byte[] bArr = new byte[iUnrandomize255State];
        int i2 = 0;
        while (i2 < iUnrandomize255State) {
            if (bitSource.available() < 8) {
                throw FormatException.getFormatInstance();
            }
            bArr[i2] = (byte) unrandomize255State(bitSource.readBits(8), i);
            i2++;
            i++;
        }
        collection.add(bArr);
        try {
            sb.append(new String(bArr, "ISO8859_1"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Platform does not support required encoding: " + e);
        }
    }

    private static void decodeC40Segment(BitSource bitSource, StringBuilder sb) throws FormatException {
        int bits;
        int i;
        boolean z;
        char c;
        int[] iArr = new int[3];
        boolean z2 = false;
        int i2 = 0;
        while (bitSource.available() != 8 && (bits = bitSource.readBits(8)) != 254) {
            parseTwoBytes(bits, bitSource.readBits(8), iArr);
            boolean z3 = z2;
            for (int i3 = 0; i3 < 3; i3++) {
                int i4 = iArr[i3];
                switch (i2) {
                    case 0:
                        if (i4 < 3) {
                            i2 = i4 + 1;
                        } else {
                            if (i4 >= C40_BASIC_SET_CHARS.length) {
                                throw FormatException.getFormatInstance();
                            }
                            char c2 = C40_BASIC_SET_CHARS[i4];
                            if (z3) {
                                sb.append((char) (c2 + 128));
                                z3 = false;
                            } else {
                                sb.append(c2);
                            }
                        }
                    case 1:
                        if (z3) {
                            i = i4 + 128;
                            sb.append((char) i);
                            z3 = false;
                            i2 = 0;
                        }
                        sb.append((char) i4);
                        i2 = 0;
                    case 2:
                        if (i4 < C40_SHIFT2_SET_CHARS.length) {
                            c = C40_SHIFT2_SET_CHARS[i4];
                            if (z3) {
                                sb.append((char) (c + 128));
                                z = false;
                            } else {
                                sb.append(c);
                                z = z3;
                            }
                            z3 = z;
                            i2 = 0;
                        } else if (i4 == 27) {
                            c = 29;
                            sb.append(c);
                            z = z3;
                            z3 = z;
                            i2 = 0;
                        } else {
                            if (i4 != 30) {
                                throw FormatException.getFormatInstance();
                            }
                            z = true;
                            z3 = z;
                            i2 = 0;
                        }
                    case 3:
                        if (z3) {
                            i = i4 + 224;
                            sb.append((char) i);
                            z3 = false;
                            i2 = 0;
                        } else {
                            i4 += 96;
                            sb.append((char) i4);
                            i2 = 0;
                        }
                    default:
                        throw FormatException.getFormatInstance();
                }
            }
            if (bitSource.available() <= 0) {
                return;
            } else {
                z2 = z3;
            }
        }
    }

    private static void decodeEdifactSegment(BitSource bitSource, StringBuilder sb) {
        while (bitSource.available() > 16) {
            for (int i = 0; i < 4; i++) {
                int bits = bitSource.readBits(6);
                if (bits == 31) {
                    int bitOffset = 8 - bitSource.getBitOffset();
                    if (bitOffset != 8) {
                        bitSource.readBits(bitOffset);
                        return;
                    }
                    return;
                }
                if ((bits & 32) == 0) {
                    bits |= 64;
                }
                sb.append((char) bits);
            }
            if (bitSource.available() <= 0) {
                return;
            }
        }
    }

    private static void decodeTextSegment(BitSource bitSource, StringBuilder sb) throws FormatException {
        int bits;
        char c;
        char c2;
        boolean z;
        char c3;
        int[] iArr = new int[3];
        boolean z2 = false;
        int i = 0;
        while (bitSource.available() != 8 && (bits = bitSource.readBits(8)) != 254) {
            parseTwoBytes(bits, bitSource.readBits(8), iArr);
            boolean z3 = z2;
            for (int i2 = 0; i2 < 3; i2++) {
                int i3 = iArr[i2];
                switch (i) {
                    case 0:
                        if (i3 < 3) {
                            i = i3 + 1;
                        } else {
                            if (i3 >= TEXT_BASIC_SET_CHARS.length) {
                                throw FormatException.getFormatInstance();
                            }
                            char c4 = TEXT_BASIC_SET_CHARS[i3];
                            if (z3) {
                                sb.append((char) (c4 + 128));
                                z3 = false;
                            } else {
                                sb.append(c4);
                            }
                        }
                    case 1:
                        if (z3) {
                            c2 = (char) (i3 + 128);
                            sb.append(c2);
                            z3 = false;
                            i = 0;
                        } else {
                            c = (char) i3;
                            sb.append(c);
                            i = 0;
                        }
                    case 2:
                        if (i3 < TEXT_SHIFT2_SET_CHARS.length) {
                            c3 = TEXT_SHIFT2_SET_CHARS[i3];
                            if (z3) {
                                sb.append((char) (c3 + 128));
                                z = false;
                            } else {
                                sb.append(c3);
                                z = z3;
                            }
                            z3 = z;
                            i = 0;
                        } else if (i3 == 27) {
                            c3 = 29;
                            sb.append(c3);
                            z = z3;
                            z3 = z;
                            i = 0;
                        } else {
                            if (i3 != 30) {
                                throw FormatException.getFormatInstance();
                            }
                            z = true;
                            z3 = z;
                            i = 0;
                        }
                    case 3:
                        if (i3 >= TEXT_SHIFT3_SET_CHARS.length) {
                            throw FormatException.getFormatInstance();
                        }
                        c = TEXT_SHIFT3_SET_CHARS[i3];
                        if (z3) {
                            c2 = (char) (c + 128);
                            sb.append(c2);
                            z3 = false;
                            i = 0;
                        }
                        sb.append(c);
                        i = 0;
                    default:
                        throw FormatException.getFormatInstance();
                }
            }
            if (bitSource.available() <= 0) {
                return;
            } else {
                z2 = z3;
            }
        }
    }

    private static void parseTwoBytes(int i, int i2, int[] iArr) {
        int i3 = ((i << 8) + i2) - 1;
        int i4 = i3 / 1600;
        iArr[0] = i4;
        int i5 = i3 - (i4 * 1600);
        int i6 = i5 / 40;
        iArr[1] = i6;
        iArr[2] = i5 - (i6 * 40);
    }

    private static int unrandomize255State(int i, int i2) {
        int i3 = i - (((i2 * 149) % 255) + 1);
        return i3 >= 0 ? i3 : i3 + 256;
    }
}
