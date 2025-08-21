package okhttp3.internal.http2;

import a.c;
import a.e;
import a.f;
import a.s;
import a.t;
import android.support.v7.widget.ActivityChooserView;
import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.internal.Util;
import okhttp3.internal.http2.Hpack;

/* loaded from: classes.dex */
final class Http2Reader implements Closeable {
    static final Logger logger = Logger.getLogger(Http2.class.getName());
    private final boolean client;
    private final ContinuationSource continuation;
    final Hpack.Reader hpackReader;
    private final e source;

    static final class ContinuationSource implements s {
        byte flags;
        int left;
        int length;
        short padding;
        private final e source;
        int streamId;

        ContinuationSource(e eVar) {
            this.source = eVar;
        }

        private void readContinuationHeader() throws IOException {
            int i = this.streamId;
            int medium = Http2Reader.readMedium(this.source);
            this.left = medium;
            this.length = medium;
            byte bH = (byte) (this.source.h() & 255);
            this.flags = (byte) (this.source.h() & 255);
            if (Http2Reader.logger.isLoggable(Level.FINE)) {
                Http2Reader.logger.fine(Http2.frameLog(true, this.streamId, this.length, bH, this.flags));
            }
            this.streamId = this.source.j() & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            if (bH != 9) {
                throw Http2.ioException("%s != TYPE_CONTINUATION", Byte.valueOf(bH));
            }
            if (this.streamId != i) {
                throw Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
            }
        }

        @Override // a.s, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
        }

        @Override // a.s
        public long read(c cVar, long j) throws IOException {
            while (this.left == 0) {
                this.source.i(this.padding);
                this.padding = (short) 0;
                if ((this.flags & 4) != 0) {
                    return -1L;
                }
                readContinuationHeader();
            }
            long j2 = this.source.read(cVar, Math.min(j, this.left));
            if (j2 == -1) {
                return -1L;
            }
            this.left = (int) (this.left - j2);
            return j2;
        }

        @Override // a.s
        public t timeout() {
            return this.source.timeout();
        }
    }

    interface Handler {
        void ackSettings();

        void alternateService(int i, String str, f fVar, String str2, int i2, long j);

        void data(boolean z, int i, e eVar, int i2);

        void goAway(int i, ErrorCode errorCode, f fVar);

        void headers(boolean z, int i, int i2, List<Header> list);

        void ping(boolean z, int i, int i2);

        void priority(int i, int i2, int i3, boolean z);

        void pushPromise(int i, int i2, List<Header> list);

        void rstStream(int i, ErrorCode errorCode);

        void settings(boolean z, Settings settings);

        void windowUpdate(int i, long j);
    }

    Http2Reader(e eVar, boolean z) {
        this.source = eVar;
        this.client = z;
        this.continuation = new ContinuationSource(this.source);
        this.hpackReader = new Hpack.Reader(4096, this.continuation);
    }

    static int lengthWithoutPadding(int i, byte b, short s) throws IOException {
        if ((b & 8) != 0) {
            i--;
        }
        if (s > i) {
            throw Http2.ioException("PROTOCOL_ERROR padding %s > remaining length %s", Short.valueOf(s), Integer.valueOf(i));
        }
        return (short) (i - s);
    }

    private void readData(Handler handler, int i, byte b, int i2) throws IOException {
        if (i2 == 0) {
            throw Http2.ioException("PROTOCOL_ERROR: TYPE_DATA streamId == 0", new Object[0]);
        }
        boolean z = (b & 1) != 0;
        if ((b & 32) != 0) {
            throw Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
        }
        short sH = (b & 8) != 0 ? (short) (this.source.h() & 255) : (short) 0;
        handler.data(z, i2, this.source, lengthWithoutPadding(i, b, sH));
        this.source.i(sH);
    }

    private void readGoAway(Handler handler, int i, byte b, int i2) throws IOException {
        if (i < 8) {
            throw Http2.ioException("TYPE_GOAWAY length < 8: %s", Integer.valueOf(i));
        }
        if (i2 != 0) {
            throw Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
        }
        int iJ = this.source.j();
        int iJ2 = this.source.j();
        int i3 = i - 8;
        ErrorCode errorCodeFromHttp2 = ErrorCode.fromHttp2(iJ2);
        if (errorCodeFromHttp2 == null) {
            throw Http2.ioException("TYPE_GOAWAY unexpected error code: %d", Integer.valueOf(iJ2));
        }
        f fVarD = f.b;
        if (i3 > 0) {
            fVarD = this.source.d(i3);
        }
        handler.goAway(iJ, errorCodeFromHttp2, fVarD);
    }

    private List<Header> readHeaderBlock(int i, short s, byte b, int i2) throws IOException {
        ContinuationSource continuationSource = this.continuation;
        this.continuation.left = i;
        continuationSource.length = i;
        this.continuation.padding = s;
        this.continuation.flags = b;
        this.continuation.streamId = i2;
        this.hpackReader.readHeaders();
        return this.hpackReader.getAndResetHeaderList();
    }

    private void readHeaders(Handler handler, int i, byte b, int i2) throws IOException {
        if (i2 == 0) {
            throw Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
        }
        boolean z = (b & 1) != 0;
        short sH = (b & 8) != 0 ? (short) (this.source.h() & 255) : (short) 0;
        if ((b & 32) != 0) {
            readPriority(handler, i2);
            i -= 5;
        }
        handler.headers(z, i2, -1, readHeaderBlock(lengthWithoutPadding(i, b, sH), sH, b, i2));
    }

    static int readMedium(e eVar) {
        return (eVar.h() & 255) | ((eVar.h() & 255) << 16) | ((eVar.h() & 255) << 8);
    }

    private void readPing(Handler handler, int i, byte b, int i2) throws IOException {
        if (i != 8) {
            throw Http2.ioException("TYPE_PING length != 8: %s", Integer.valueOf(i));
        }
        if (i2 != 0) {
            throw Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
        }
        handler.ping((b & 1) != 0, this.source.j(), this.source.j());
    }

    private void readPriority(Handler handler, int i) {
        int iJ = this.source.j();
        handler.priority(i, iJ & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, (this.source.h() & 255) + 1, (Integer.MIN_VALUE & iJ) != 0);
    }

    private void readPriority(Handler handler, int i, byte b, int i2) throws IOException {
        if (i != 5) {
            throw Http2.ioException("TYPE_PRIORITY length: %d != 5", Integer.valueOf(i));
        }
        if (i2 == 0) {
            throw Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
        }
        readPriority(handler, i2);
    }

    private void readPushPromise(Handler handler, int i, byte b, int i2) throws IOException {
        if (i2 == 0) {
            throw Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
        }
        short sH = (b & 8) != 0 ? (short) (this.source.h() & 255) : (short) 0;
        handler.pushPromise(i2, this.source.j() & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED, readHeaderBlock(lengthWithoutPadding(i - 4, b, sH), sH, b, i2));
    }

    private void readRstStream(Handler handler, int i, byte b, int i2) throws IOException {
        if (i != 4) {
            throw Http2.ioException("TYPE_RST_STREAM length: %d != 4", Integer.valueOf(i));
        }
        if (i2 == 0) {
            throw Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
        }
        int iJ = this.source.j();
        ErrorCode errorCodeFromHttp2 = ErrorCode.fromHttp2(iJ);
        if (errorCodeFromHttp2 == null) {
            throw Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", Integer.valueOf(iJ));
        }
        handler.rstStream(i2, errorCodeFromHttp2);
    }

    private void readSettings(Handler handler, int i, byte b, int i2) throws IOException {
        if (i2 != 0) {
            throw Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
        }
        if ((b & 1) != 0) {
            if (i != 0) {
                throw Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
            }
            handler.ackSettings();
            return;
        }
        if (i % 6 != 0) {
            throw Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", Integer.valueOf(i));
        }
        Settings settings = new Settings();
        for (int i3 = 0; i3 < i; i3 += 6) {
            int i4 = this.source.i() & 65535;
            int iJ = this.source.j();
            switch (i4) {
                case 2:
                    if (iJ != 0 && iJ != 1) {
                        throw Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
                    }
                    break;
                    break;
                case 3:
                    i4 = 4;
                    break;
                case 4:
                    i4 = 7;
                    if (iJ < 0) {
                        throw Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
                    }
                    break;
                case 5:
                    if (iJ < 16384 || iJ > 16777215) {
                        throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", Integer.valueOf(iJ));
                    }
                    break;
                    break;
            }
            settings.set(i4, iJ);
        }
        handler.settings(false, settings);
    }

    private void readWindowUpdate(Handler handler, int i, byte b, int i2) throws IOException {
        if (i != 4) {
            throw Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", Integer.valueOf(i));
        }
        long j = this.source.j() & 2147483647L;
        if (j == 0) {
            throw Http2.ioException("windowSizeIncrement was 0", Long.valueOf(j));
        }
        handler.windowUpdate(i2, j);
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.source.close();
    }

    public boolean nextFrame(boolean z, Handler handler) throws IOException {
        try {
            this.source.a(9L);
            int medium = readMedium(this.source);
            if (medium < 0 || medium > 16384) {
                throw Http2.ioException("FRAME_SIZE_ERROR: %s", Integer.valueOf(medium));
            }
            byte bH = (byte) (this.source.h() & 255);
            if (z && bH != 4) {
                throw Http2.ioException("Expected a SETTINGS frame but was %s", Byte.valueOf(bH));
            }
            byte bH2 = (byte) (this.source.h() & 255);
            int iJ = this.source.j() & ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(Http2.frameLog(true, iJ, medium, bH, bH2));
            }
            switch (bH) {
                case 0:
                    readData(handler, medium, bH2, iJ);
                    return true;
                case 1:
                    readHeaders(handler, medium, bH2, iJ);
                    return true;
                case 2:
                    readPriority(handler, medium, bH2, iJ);
                    return true;
                case 3:
                    readRstStream(handler, medium, bH2, iJ);
                    return true;
                case 4:
                    readSettings(handler, medium, bH2, iJ);
                    return true;
                case 5:
                    readPushPromise(handler, medium, bH2, iJ);
                    return true;
                case 6:
                    readPing(handler, medium, bH2, iJ);
                    return true;
                case 7:
                    readGoAway(handler, medium, bH2, iJ);
                    return true;
                case 8:
                    readWindowUpdate(handler, medium, bH2, iJ);
                    return true;
                default:
                    this.source.i(medium);
                    return true;
            }
        } catch (IOException unused) {
            return false;
        }
    }

    public void readConnectionPreface(Handler handler) throws IOException {
        if (this.client) {
            if (!nextFrame(true, handler)) {
                throw Http2.ioException("Required SETTINGS preface not received", new Object[0]);
            }
            return;
        }
        f fVarD = this.source.d(Http2.CONNECTION_PREFACE.h());
        if (logger.isLoggable(Level.FINE)) {
            logger.fine(Util.format("<< CONNECTION %s", fVarD.f()));
        }
        if (!Http2.CONNECTION_PREFACE.equals(fVarD)) {
            throw Http2.ioException("Expected a connection header but was %s", fVarD.a());
        }
    }
}
