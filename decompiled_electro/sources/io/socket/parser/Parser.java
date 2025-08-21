package io.socket.parser;

/* loaded from: classes.dex */
public interface Parser {
    public static final int ACK = 3;
    public static final int BINARY_ACK = 6;
    public static final int BINARY_EVENT = 5;
    public static final int CONNECT = 0;
    public static final int CONNECT_ERROR = 4;
    public static final int DISCONNECT = 1;
    public static final int EVENT = 2;
    public static final int protocol = 5;
    public static final String[] types = {"CONNECT", "DISCONNECT", "EVENT", "ACK", "ERROR", "BINARY_EVENT", "BINARY_ACK"};

    public interface Decoder {

        public interface Callback {
            void call(Packet packet);
        }

        void add(String str);

        void add(byte[] bArr);

        void destroy();

        void onDecoded(Callback callback);
    }

    public interface Encoder {

        public interface Callback {
            void call(Object[] objArr);
        }

        void encode(Packet packet, Callback callback);
    }
}
