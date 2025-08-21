package okhttp3.internal.http2;

import a.f;
import java.io.IOException;
import okhttp3.internal.Util;

/* loaded from: classes.dex */
public final class Http2 {
    static final byte FLAG_ACK = 1;
    static final byte FLAG_COMPRESSED = 32;
    static final byte FLAG_END_HEADERS = 4;
    static final byte FLAG_END_PUSH_PROMISE = 4;
    static final byte FLAG_END_STREAM = 1;
    static final byte FLAG_NONE = 0;
    static final byte FLAG_PADDED = 8;
    static final byte FLAG_PRIORITY = 32;
    static final int INITIAL_MAX_FRAME_SIZE = 16384;
    static final byte TYPE_CONTINUATION = 9;
    static final byte TYPE_DATA = 0;
    static final byte TYPE_GOAWAY = 7;
    static final byte TYPE_HEADERS = 1;
    static final byte TYPE_PING = 6;
    static final byte TYPE_PRIORITY = 2;
    static final byte TYPE_PUSH_PROMISE = 5;
    static final byte TYPE_RST_STREAM = 3;
    static final byte TYPE_SETTINGS = 4;
    static final byte TYPE_WINDOW_UPDATE = 8;
    static final f CONNECTION_PREFACE = f.a("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
    private static final String[] FRAME_NAMES = {"DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION"};
    static final String[] FLAGS = new String[64];
    static final String[] BINARY = new String[256];

    static {
        for (int i = 0; i < BINARY.length; i++) {
            BINARY[i] = Util.format("%8s", Integer.toBinaryString(i)).replace(' ', '0');
        }
        FLAGS[0] = "";
        FLAGS[1] = "END_STREAM";
        int[] iArr = {1};
        FLAGS[8] = "PADDED";
        for (int i2 : iArr) {
            FLAGS[i2 | 8] = FLAGS[i2] + "|PADDED";
        }
        FLAGS[4] = "END_HEADERS";
        FLAGS[32] = "PRIORITY";
        FLAGS[36] = "END_HEADERS|PRIORITY";
        for (int i3 : new int[]{4, 32, 36}) {
            for (int i4 : iArr) {
                int i5 = i4 | i3;
                FLAGS[i5] = FLAGS[i4] + '|' + FLAGS[i3];
                FLAGS[i5 | 8] = FLAGS[i4] + '|' + FLAGS[i3] + "|PADDED";
            }
        }
        for (int i6 = 0; i6 < FLAGS.length; i6++) {
            if (FLAGS[i6] == null) {
                FLAGS[i6] = BINARY[i6];
            }
        }
    }

    private Http2() {
    }

    static String formatFlags(byte b, byte b2) {
        if (b2 == 0) {
            return "";
        }
        switch (b) {
            case 2:
            case 3:
            case 7:
            case 8:
                break;
            case 4:
            case 6:
                if (b2 != 1) {
                    break;
                }
                break;
            case 5:
            default:
                String str = b2 < FLAGS.length ? FLAGS[b2] : BINARY[b2];
                if (b == 5 && (b2 & 4) != 0) {
                    break;
                } else if (b == 0 && (b2 & 32) != 0) {
                    break;
                }
                break;
        }
        return BINARY[b2];
    }

    static String frameLog(boolean z, int i, int i2, byte b, byte b2) {
        String str = b < FRAME_NAMES.length ? FRAME_NAMES[b] : Util.format("0x%02x", Byte.valueOf(b));
        String flags = formatFlags(b, b2);
        Object[] objArr = new Object[5];
        objArr[0] = z ? "<<" : ">>";
        objArr[1] = Integer.valueOf(i);
        objArr[2] = Integer.valueOf(i2);
        objArr[3] = str;
        objArr[4] = flags;
        return Util.format("%s 0x%08x %5d %-13s %s", objArr);
    }

    static IllegalArgumentException illegalArgument(String str, Object... objArr) {
        throw new IllegalArgumentException(Util.format(str, objArr));
    }

    static IOException ioException(String str, Object... objArr) throws IOException {
        throw new IOException(Util.format(str, objArr));
    }
}
