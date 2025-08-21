package io.socket.parser;

import io.socket.hasbinary.HasBinary;
import io.socket.parser.Binary;
import io.socket.parser.Parser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public final class IOParser implements Parser {
    private static final Logger logger = Logger.getLogger(IOParser.class.getName());

    static class BinaryReconstructor {
        List<byte[]> buffers = new ArrayList();
        public Packet reconPack;

        BinaryReconstructor(Packet packet) {
            this.reconPack = packet;
        }

        public void finishReconstruction() {
            this.reconPack = null;
            this.buffers = new ArrayList();
        }

        public Packet takeBinaryData(byte[] bArr) {
            this.buffers.add(bArr);
            if (this.buffers.size() != this.reconPack.attachments) {
                return null;
            }
            Packet packetReconstructPacket = Binary.reconstructPacket(this.reconPack, (byte[][]) this.buffers.toArray(new byte[this.buffers.size()][]));
            finishReconstruction();
            return packetReconstructPacket;
        }
    }

    public static final class Decoder implements Parser.Decoder {
        private Parser.Decoder.Callback onDecodedCallback;
        BinaryReconstructor reconstructor = null;

        /* JADX WARN: Code restructure failed: missing block: B:44:0x00b7, code lost:
        
            r1.id = java.lang.Integer.parseInt(r3.toString());
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00c9, code lost:
        
            throw new io.socket.parser.DecodingException("invalid payload");
         */
        /* JADX WARN: Type inference failed for: r0v7, types: [T, java.lang.Object] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        private static io.socket.parser.Packet decodeString(java.lang.String r8) {
            /*
                Method dump skipped, instructions count: 327
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: io.socket.parser.IOParser.Decoder.decodeString(java.lang.String):io.socket.parser.Packet");
        }

        private static boolean isPayloadValid(int i, Object obj) {
            switch (i) {
                case 0:
                case 4:
                    return obj instanceof JSONObject;
                case 1:
                    return obj == null;
                case 2:
                case 5:
                    if (obj instanceof JSONArray) {
                        JSONArray jSONArray = (JSONArray) obj;
                        if (jSONArray.length() > 0 && !jSONArray.isNull(0)) {
                            return true;
                        }
                    }
                    return false;
                case 3:
                case 6:
                    return obj instanceof JSONArray;
                default:
                    return false;
            }
        }

        @Override // io.socket.parser.Parser.Decoder
        public void add(String str) {
            Packet packetDecodeString = decodeString(str);
            if (5 == packetDecodeString.type || 6 == packetDecodeString.type) {
                this.reconstructor = new BinaryReconstructor(packetDecodeString);
                if (this.reconstructor.reconPack.attachments != 0 || this.onDecodedCallback == null) {
                    return;
                }
            } else if (this.onDecodedCallback == null) {
                return;
            }
            this.onDecodedCallback.call(packetDecodeString);
        }

        @Override // io.socket.parser.Parser.Decoder
        public void add(byte[] bArr) {
            if (this.reconstructor == null) {
                throw new RuntimeException("got binary data when not reconstructing a packet");
            }
            Packet packetTakeBinaryData = this.reconstructor.takeBinaryData(bArr);
            if (packetTakeBinaryData != null) {
                this.reconstructor = null;
                if (this.onDecodedCallback != null) {
                    this.onDecodedCallback.call(packetTakeBinaryData);
                }
            }
        }

        @Override // io.socket.parser.Parser.Decoder
        public void destroy() {
            if (this.reconstructor != null) {
                this.reconstructor.finishReconstruction();
            }
            this.onDecodedCallback = null;
        }

        @Override // io.socket.parser.Parser.Decoder
        public void onDecoded(Parser.Decoder.Callback callback) {
            this.onDecodedCallback = callback;
        }
    }

    public static final class Encoder implements Parser.Encoder {
        private void encodeAsBinary(Packet packet, Parser.Encoder.Callback callback) {
            Binary.DeconstructedPacket deconstructedPacketDeconstructPacket = Binary.deconstructPacket(packet);
            String strEncodeAsString = encodeAsString(deconstructedPacketDeconstructPacket.packet);
            ArrayList arrayList = new ArrayList(Arrays.asList(deconstructedPacketDeconstructPacket.buffers));
            arrayList.add(0, strEncodeAsString);
            callback.call(arrayList.toArray());
        }

        private String encodeAsString(Packet packet) {
            StringBuilder sb = new StringBuilder("" + packet.type);
            if (5 == packet.type || 6 == packet.type) {
                sb.append(packet.attachments);
                sb.append("-");
            }
            if (packet.nsp != null && packet.nsp.length() != 0 && !"/".equals(packet.nsp)) {
                sb.append(packet.nsp);
                sb.append(",");
            }
            if (packet.id >= 0) {
                sb.append(packet.id);
            }
            if (packet.data != 0) {
                sb.append(packet.data);
            }
            if (IOParser.logger.isLoggable(Level.FINE)) {
                IOParser.logger.fine(String.format("encoded %s as %s", packet, sb));
            }
            return sb.toString();
        }

        @Override // io.socket.parser.Parser.Encoder
        public void encode(Packet packet, Parser.Encoder.Callback callback) {
            if ((packet.type == 2 || packet.type == 3) && HasBinary.hasBinary(packet.data)) {
                packet.type = packet.type == 2 ? 5 : 6;
            }
            if (IOParser.logger.isLoggable(Level.FINE)) {
                IOParser.logger.fine(String.format("encoding packet %s", packet));
            }
            if (5 == packet.type || 6 == packet.type) {
                encodeAsBinary(packet, callback);
            } else {
                callback.call(new String[]{encodeAsString(packet)});
            }
        }
    }

    private IOParser() {
    }
}
