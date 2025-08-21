package okhttp3;

import a.c;
import a.e;
import a.f;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import javax.annotation.Nullable;
import okhttp3.internal.Util;

/* loaded from: classes.dex */
public abstract class ResponseBody implements Closeable {

    @Nullable
    private Reader reader;

    static final class BomAwareReader extends Reader {
        private final Charset charset;
        private boolean closed;

        @Nullable
        private Reader delegate;
        private final e source;

        BomAwareReader(e eVar, Charset charset) {
            this.source = eVar;
            this.charset = charset;
        }

        @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.closed = true;
            if (this.delegate != null) {
                this.delegate.close();
            } else {
                this.source.close();
            }
        }

        @Override // java.io.Reader
        public int read(char[] cArr, int i, int i2) throws IOException {
            if (this.closed) {
                throw new IOException("Stream closed");
            }
            Reader reader = this.delegate;
            if (reader == null) {
                InputStreamReader inputStreamReader = new InputStreamReader(this.source.f(), Util.bomAwareCharset(this.source, this.charset));
                this.delegate = inputStreamReader;
                reader = inputStreamReader;
            }
            return reader.read(cArr, i, i2);
        }
    }

    private Charset charset() {
        MediaType mediaTypeContentType = contentType();
        return mediaTypeContentType != null ? mediaTypeContentType.charset(Util.UTF_8) : Util.UTF_8;
    }

    public static ResponseBody create(@Nullable final MediaType mediaType, final long j, final e eVar) {
        if (eVar == null) {
            throw new NullPointerException("source == null");
        }
        return new ResponseBody() { // from class: okhttp3.ResponseBody.1
            @Override // okhttp3.ResponseBody
            public long contentLength() {
                return j;
            }

            @Override // okhttp3.ResponseBody
            @Nullable
            public MediaType contentType() {
                return mediaType;
            }

            @Override // okhttp3.ResponseBody
            public e source() {
                return eVar;
            }
        };
    }

    public static ResponseBody create(@Nullable MediaType mediaType, f fVar) {
        return create(mediaType, fVar.h(), new c().c(fVar));
    }

    public static ResponseBody create(@Nullable MediaType mediaType, String str) {
        Charset charset = Util.UTF_8;
        if (mediaType != null && (charset = mediaType.charset()) == null) {
            charset = Util.UTF_8;
            mediaType = MediaType.parse(mediaType + "; charset=utf-8");
        }
        c cVarA = new c().a(str, charset);
        return create(mediaType, cVarA.a(), cVarA);
    }

    public static ResponseBody create(@Nullable MediaType mediaType, byte[] bArr) {
        return create(mediaType, bArr.length, new c().c(bArr));
    }

    public final InputStream byteStream() {
        return source().f();
    }

    public final byte[] bytes() throws IOException {
        long jContentLength = contentLength();
        if (jContentLength > 2147483647L) {
            throw new IOException("Cannot buffer entire body for content length: " + jContentLength);
        }
        e eVarSource = source();
        try {
            byte[] bArrS = eVarSource.s();
            Util.closeQuietly(eVarSource);
            if (jContentLength == -1 || jContentLength == bArrS.length) {
                return bArrS;
            }
            throw new IOException("Content-Length (" + jContentLength + ") and stream length (" + bArrS.length + ") disagree");
        } catch (Throwable th) {
            Util.closeQuietly(eVarSource);
            throw th;
        }
    }

    public final Reader charStream() {
        Reader reader = this.reader;
        if (reader != null) {
            return reader;
        }
        BomAwareReader bomAwareReader = new BomAwareReader(source(), charset());
        this.reader = bomAwareReader;
        return bomAwareReader;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        Util.closeQuietly(source());
    }

    public abstract long contentLength();

    @Nullable
    public abstract MediaType contentType();

    public abstract e source();

    public final String string() throws IOException {
        e eVarSource = source();
        try {
            return eVarSource.a(Util.bomAwareCharset(eVarSource, charset()));
        } finally {
            Util.closeQuietly(eVarSource);
        }
    }
}
