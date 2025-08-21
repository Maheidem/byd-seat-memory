package io.socket.engineio.parser;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class Parser {
    public static final int PROTOCOL = 4;
    private static final char SEPARATOR = 30;
    private static final Packet<String> err;
    private static final Map<String, Integer> packets = new HashMap<String, Integer>() { // from class: io.socket.engineio.parser.Parser.1
        {
            put("open", 0);
            put("close", 1);
            put("ping", 2);
            put("pong", 3);
            put("message", 4);
            put("upgrade", 5);
            put(Packet.NOOP, 6);
        }
    };
    private static final Map<Integer, String> packetslist = new HashMap();

    public interface DecodePayloadCallback<T> {
        boolean call(Packet<T> packet, int i, int i2);
    }

    public interface EncodeCallback<T> {
        void call(T t);
    }

    static {
        for (Map.Entry<String, Integer> entry : packets.entrySet()) {
            packetslist.put(entry.getValue(), entry.getKey());
        }
        err = new Packet<>("error", "parser error");
    }

    private Parser() {
    }

    public static Packet decodeBase64Packet(String str) {
        return str == null ? err : str.charAt(0) == 'b' ? new Packet("message", Base64.decode(str.substring(1), 0)) : decodePacket(str);
    }

    public static Packet<String> decodePacket(String str) {
        int numericValue;
        if (str == null) {
            return err;
        }
        try {
            numericValue = Character.getNumericValue(str.charAt(0));
        } catch (IndexOutOfBoundsException unused) {
            numericValue = -1;
        }
        return (numericValue < 0 || numericValue >= packetslist.size()) ? err : str.length() > 1 ? new Packet<>(packetslist.get(Integer.valueOf(numericValue)), str.substring(1)) : new Packet<>(packetslist.get(Integer.valueOf(numericValue)));
    }

    public static Packet<byte[]> decodePacket(byte[] bArr) {
        return new Packet<>("message", bArr);
    }

    public static void decodePayload(String str, DecodePayloadCallback<String> decodePayloadCallback) {
        if (str != null && str.length() != 0) {
            String[] strArrSplit = str.split(String.valueOf(SEPARATOR));
            int length = strArrSplit.length;
            for (int i = 0; i < length; i++) {
                Packet<String> packetDecodeBase64Packet = decodeBase64Packet(strArrSplit[i]);
                if (!err.type.equals(packetDecodeBase64Packet.type) || !err.data.equals(packetDecodeBase64Packet.data)) {
                    if (!decodePayloadCallback.call(packetDecodeBase64Packet, i, length)) {
                        return;
                    }
                }
            }
            return;
        }
        decodePayloadCallback.call(err, 0, 1);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public static void encodePacket(Packet packet, EncodeCallback encodeCallback) {
        Object obj;
        if (packet.data instanceof byte[]) {
            obj = packet.data;
        } else {
            obj = String.valueOf(packets.get(packet.type)) + (packet.data != 0 ? String.valueOf(packet.data) : "");
        }
        encodeCallback.call(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void encodePacketAsBase64(Packet packet, EncodeCallback<String> encodeCallback) {
        if (!(packet.data instanceof byte[])) {
            encodePacket(packet, encodeCallback);
            return;
        }
        encodeCallback.call("b" + Base64.encodeToString((byte[]) packet.data, 0));
    }

    public static void encodePayload(Packet[] packetArr, EncodeCallback<String> encodeCallback) {
        String string;
        if (packetArr.length == 0) {
            string = "0:";
        } else {
            final StringBuilder sb = new StringBuilder();
            int length = packetArr.length;
            int i = 0;
            while (i < length) {
                final boolean z = i == length + (-1);
                encodePacketAsBase64(packetArr[i], new EncodeCallback<String>() { // from class: io.socket.engineio.parser.Parser.2
                    @Override // io.socket.engineio.parser.Parser.EncodeCallback
                    public void call(String str) {
                        sb.append(str);
                        if (z) {
                            return;
                        }
                        sb.append(Parser.SEPARATOR);
                    }
                });
                i++;
            }
            string = sb.toString();
        }
        encodeCallback.call(string);
    }
}
