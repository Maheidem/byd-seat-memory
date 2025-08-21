package okhttp3.internal.ws;

import a.c;
import a.e;
import a.f;
import android.support.v4.media.TransportMediator;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
final class WebSocketReader {
    boolean closed;
    final FrameCallback frameCallback;
    long frameLength;
    final boolean isClient;
    boolean isControlFrame;
    boolean isFinalFrame;
    private final c.a maskCursor;
    private final byte[] maskKey;
    int opcode;
    final e source;
    private final c controlFrameBuffer = new c();
    private final c messageFrameBuffer = new c();

    public interface FrameCallback {
        void onReadClose(int i, String str);

        void onReadMessage(f fVar);

        void onReadMessage(String str);

        void onReadPing(f fVar);

        void onReadPong(f fVar);
    }

    WebSocketReader(boolean z, e eVar, FrameCallback frameCallback) {
        if (eVar == null) {
            throw new NullPointerException("source == null");
        }
        if (frameCallback == null) {
            throw new NullPointerException("frameCallback == null");
        }
        this.isClient = z;
        this.source = eVar;
        this.frameCallback = frameCallback;
        this.maskKey = z ? null : new byte[4];
        this.maskCursor = z ? null : new c.a();
    }

    private void readControlFrame() throws ProtocolException {
        if (this.frameLength > 0) {
            this.source.a(this.controlFrameBuffer, this.frameLength);
            if (!this.isClient) {
                this.controlFrameBuffer.a(this.maskCursor);
                this.maskCursor.a(0L);
                WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                this.maskCursor.close();
            }
        }
        switch (this.opcode) {
            case 8:
                short sI = 1005;
                String strQ = "";
                long jA = this.controlFrameBuffer.a();
                if (jA == 1) {
                    throw new ProtocolException("Malformed close payload length of 1.");
                }
                if (jA != 0) {
                    sI = this.controlFrameBuffer.i();
                    strQ = this.controlFrameBuffer.q();
                    String strCloseCodeExceptionMessage = WebSocketProtocol.closeCodeExceptionMessage(sI);
                    if (strCloseCodeExceptionMessage != null) {
                        throw new ProtocolException(strCloseCodeExceptionMessage);
                    }
                }
                this.frameCallback.onReadClose(sI, strQ);
                this.closed = true;
                return;
            case 9:
                this.frameCallback.onReadPing(this.controlFrameBuffer.p());
                return;
            case 10:
                this.frameCallback.onReadPong(this.controlFrameBuffer.p());
                return;
            default:
                throw new ProtocolException("Unknown control opcode: " + Integer.toHexString(this.opcode));
        }
    }

    /* JADX WARN: Finally extract failed */
    private void readHeader() throws IOException {
        if (this.closed) {
            throw new IOException("closed");
        }
        long jTimeoutNanos = this.source.timeout().timeoutNanos();
        this.source.timeout().clearTimeout();
        try {
            int iH = this.source.h() & 255;
            this.source.timeout().timeout(jTimeoutNanos, TimeUnit.NANOSECONDS);
            this.opcode = iH & 15;
            this.isFinalFrame = (iH & 128) != 0;
            this.isControlFrame = (iH & 8) != 0;
            if (this.isControlFrame && !this.isFinalFrame) {
                throw new ProtocolException("Control frames must be final.");
            }
            boolean z = (iH & 64) != 0;
            boolean z2 = (iH & 32) != 0;
            boolean z3 = (iH & 16) != 0;
            if (z || z2 || z3) {
                throw new ProtocolException("Reserved flags are unsupported.");
            }
            boolean z4 = ((this.source.h() & 255) & 128) != 0;
            if (z4 == this.isClient) {
                throw new ProtocolException(this.isClient ? "Server-sent frames must not be masked." : "Client-sent frames must be masked.");
            }
            this.frameLength = r0 & TransportMediator.KEYCODE_MEDIA_PAUSE;
            if (this.frameLength == 126) {
                this.frameLength = this.source.i() & 65535;
            } else if (this.frameLength == 127) {
                this.frameLength = this.source.k();
                if (this.frameLength < 0) {
                    throw new ProtocolException("Frame length 0x" + Long.toHexString(this.frameLength) + " > 0x7FFFFFFFFFFFFFFF");
                }
            }
            if (this.isControlFrame && this.frameLength > 125) {
                throw new ProtocolException("Control frame must be less than 125B.");
            }
            if (z4) {
                this.source.a(this.maskKey);
            }
        } catch (Throwable th) {
            this.source.timeout().timeout(jTimeoutNanos, TimeUnit.NANOSECONDS);
            throw th;
        }
    }

    private void readMessage() throws IOException {
        while (!this.closed) {
            if (this.frameLength > 0) {
                this.source.a(this.messageFrameBuffer, this.frameLength);
                if (!this.isClient) {
                    this.messageFrameBuffer.a(this.maskCursor);
                    this.maskCursor.a(this.messageFrameBuffer.a() - this.frameLength);
                    WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                    this.maskCursor.close();
                }
            }
            if (this.isFinalFrame) {
                return;
            }
            readUntilNonControlFrame();
            if (this.opcode != 0) {
                throw new ProtocolException("Expected continuation opcode. Got: " + Integer.toHexString(this.opcode));
            }
        }
        throw new IOException("closed");
    }

    private void readMessageFrame() throws IOException {
        int i = this.opcode;
        if (i != 1 && i != 2) {
            throw new ProtocolException("Unknown opcode: " + Integer.toHexString(i));
        }
        readMessage();
        if (i == 1) {
            this.frameCallback.onReadMessage(this.messageFrameBuffer.q());
        } else {
            this.frameCallback.onReadMessage(this.messageFrameBuffer.p());
        }
    }

    private void readUntilNonControlFrame() throws IOException {
        while (!this.closed) {
            readHeader();
            if (!this.isControlFrame) {
                return;
            } else {
                readControlFrame();
            }
        }
    }

    void processNextFrame() throws IOException {
        readHeader();
        if (this.isControlFrame) {
            readControlFrame();
        } else {
            readMessageFrame();
        }
    }
}
