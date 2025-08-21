package okhttp3.internal.ws;

import a.c;
import a.d;
import a.f;
import a.r;
import a.t;
import android.support.v4.media.TransportMediator;
import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import java.util.Random;

/* loaded from: classes.dex */
final class WebSocketWriter {
    boolean activeWriter;
    final c buffer = new c();
    final FrameSink frameSink = new FrameSink();
    final boolean isClient;
    private final c.a maskCursor;
    private final byte[] maskKey;
    final Random random;
    final d sink;
    final c sinkBuffer;
    boolean writerClosed;

    final class FrameSink implements r {
        boolean closed;
        long contentLength;
        int formatOpcode;
        boolean isFirstFrame;

        FrameSink() {
        }

        @Override // a.r, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            WebSocketWriter.this.writeMessageFrame(this.formatOpcode, WebSocketWriter.this.buffer.a(), this.isFirstFrame, true);
            this.closed = true;
            WebSocketWriter.this.activeWriter = false;
        }

        @Override // a.r, java.io.Flushable
        public void flush() throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            WebSocketWriter.this.writeMessageFrame(this.formatOpcode, WebSocketWriter.this.buffer.a(), this.isFirstFrame, false);
            this.isFirstFrame = false;
        }

        @Override // a.r
        public t timeout() {
            return WebSocketWriter.this.sink.timeout();
        }

        @Override // a.r
        public void write(c cVar, long j) throws IOException {
            if (this.closed) {
                throw new IOException("closed");
            }
            WebSocketWriter.this.buffer.write(cVar, j);
            boolean z = this.isFirstFrame && this.contentLength != -1 && WebSocketWriter.this.buffer.a() > this.contentLength - PlaybackStateCompat.ACTION_PLAY_FROM_URI;
            long jG = WebSocketWriter.this.buffer.g();
            if (jG <= 0 || z) {
                return;
            }
            WebSocketWriter.this.writeMessageFrame(this.formatOpcode, jG, this.isFirstFrame, false);
            this.isFirstFrame = false;
        }
    }

    WebSocketWriter(boolean z, d dVar, Random random) {
        if (dVar == null) {
            throw new NullPointerException("sink == null");
        }
        if (random == null) {
            throw new NullPointerException("random == null");
        }
        this.isClient = z;
        this.sink = dVar;
        this.sinkBuffer = dVar.b();
        this.random = random;
        this.maskKey = z ? new byte[4] : null;
        this.maskCursor = z ? new c.a() : null;
    }

    private void writeControlFrame(int i, f fVar) throws IOException {
        if (this.writerClosed) {
            throw new IOException("closed");
        }
        int iH = fVar.h();
        if (iH > 125) {
            throw new IllegalArgumentException("Payload size must be less than or equal to 125");
        }
        this.sinkBuffer.i(i | 128);
        if (this.isClient) {
            this.sinkBuffer.i(iH | 128);
            this.random.nextBytes(this.maskKey);
            this.sinkBuffer.c(this.maskKey);
            if (iH > 0) {
                long jA = this.sinkBuffer.a();
                this.sinkBuffer.c(fVar);
                this.sinkBuffer.a(this.maskCursor);
                this.maskCursor.a(jA);
                WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                this.maskCursor.close();
            }
        } else {
            this.sinkBuffer.i(iH);
            this.sinkBuffer.c(fVar);
        }
        this.sink.flush();
    }

    r newMessageSink(int i, long j) {
        if (this.activeWriter) {
            throw new IllegalStateException("Another message writer is active. Did you call close()?");
        }
        this.activeWriter = true;
        this.frameSink.formatOpcode = i;
        this.frameSink.contentLength = j;
        this.frameSink.isFirstFrame = true;
        this.frameSink.closed = false;
        return this.frameSink;
    }

    void writeClose(int i, f fVar) {
        f fVarP = f.b;
        if (i != 0 || fVar != null) {
            if (i != 0) {
                WebSocketProtocol.validateCloseCode(i);
            }
            c cVar = new c();
            cVar.h(i);
            if (fVar != null) {
                cVar.c(fVar);
            }
            fVarP = cVar.p();
        }
        try {
            writeControlFrame(8, fVarP);
        } finally {
            this.writerClosed = true;
        }
    }

    void writeMessageFrame(int i, long j, boolean z, boolean z2) throws IOException {
        if (this.writerClosed) {
            throw new IOException("closed");
        }
        if (!z) {
            i = 0;
        }
        if (z2) {
            i |= 128;
        }
        this.sinkBuffer.i(i);
        int i2 = this.isClient ? 128 : 0;
        if (j <= 125) {
            this.sinkBuffer.i(((int) j) | i2);
        } else if (j <= 65535) {
            this.sinkBuffer.i(i2 | TransportMediator.KEYCODE_MEDIA_PLAY);
            this.sinkBuffer.h((int) j);
        } else {
            this.sinkBuffer.i(i2 | TransportMediator.KEYCODE_MEDIA_PAUSE);
            this.sinkBuffer.j(j);
        }
        if (this.isClient) {
            this.random.nextBytes(this.maskKey);
            this.sinkBuffer.c(this.maskKey);
            if (j > 0) {
                long jA = this.sinkBuffer.a();
                this.sinkBuffer.write(this.buffer, j);
                this.sinkBuffer.a(this.maskCursor);
                this.maskCursor.a(jA);
                WebSocketProtocol.toggleMask(this.maskCursor, this.maskKey);
                this.maskCursor.close();
            }
        } else {
            this.sinkBuffer.write(this.buffer, j);
        }
        this.sink.d();
    }

    void writePing(f fVar) throws IOException {
        writeControlFrame(9, fVar);
    }

    void writePong(f fVar) throws IOException {
        writeControlFrame(10, fVar);
    }
}
