package a;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class l {

    /* renamed from: a, reason: collision with root package name */
    static final Logger f10a = Logger.getLogger(l.class.getName());

    private l() {
    }

    public static d a(r rVar) {
        return new m(rVar);
    }

    public static e a(s sVar) {
        return new n(sVar);
    }

    public static r a() {
        return new r() { // from class: a.l.3
            @Override // a.r, java.io.Closeable, java.lang.AutoCloseable
            public void close() {
            }

            @Override // a.r, java.io.Flushable
            public void flush() {
            }

            @Override // a.r
            public t timeout() {
                return t.NONE;
            }

            @Override // a.r
            public void write(c cVar, long j) throws EOFException {
                cVar.i(j);
            }
        };
    }

    public static r a(OutputStream outputStream) {
        return a(outputStream, new t());
    }

    private static r a(final OutputStream outputStream, final t tVar) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        if (tVar == null) {
            throw new IllegalArgumentException("timeout == null");
        }
        return new r() { // from class: a.l.1
            @Override // a.r, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                outputStream.close();
            }

            @Override // a.r, java.io.Flushable
            public void flush() throws IOException {
                outputStream.flush();
            }

            @Override // a.r
            public t timeout() {
                return tVar;
            }

            public String toString() {
                return "sink(" + outputStream + ")";
            }

            @Override // a.r
            public void write(c cVar, long j) throws IOException {
                u.a(cVar.b, 0L, j);
                while (j > 0) {
                    tVar.throwIfReached();
                    o oVar = cVar.f3a;
                    int iMin = (int) Math.min(j, oVar.c - oVar.b);
                    outputStream.write(oVar.f17a, oVar.b, iMin);
                    oVar.b += iMin;
                    long j2 = iMin;
                    j -= j2;
                    cVar.b -= j2;
                    if (oVar.b == oVar.c) {
                        cVar.f3a = oVar.c();
                        p.a(oVar);
                    }
                }
            }
        };
    }

    public static r a(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        if (socket.getOutputStream() == null) {
            throw new IOException("socket's output stream == null");
        }
        a aVarC = c(socket);
        return aVarC.sink(a(socket.getOutputStream(), aVarC));
    }

    public static s a(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        return a(new FileInputStream(file));
    }

    public static s a(InputStream inputStream) {
        return a(inputStream, new t());
    }

    private static s a(final InputStream inputStream, final t tVar) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (tVar == null) {
            throw new IllegalArgumentException("timeout == null");
        }
        return new s() { // from class: a.l.2
            @Override // a.s, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                inputStream.close();
            }

            @Override // a.s
            public long read(c cVar, long j) throws IOException {
                if (j < 0) {
                    throw new IllegalArgumentException("byteCount < 0: " + j);
                }
                if (j == 0) {
                    return 0L;
                }
                try {
                    tVar.throwIfReached();
                    o oVarE = cVar.e(1);
                    int i = inputStream.read(oVarE.f17a, oVarE.c, (int) Math.min(j, 8192 - oVarE.c));
                    if (i == -1) {
                        return -1L;
                    }
                    oVarE.c += i;
                    long j2 = i;
                    cVar.b += j2;
                    return j2;
                } catch (AssertionError e) {
                    if (l.a(e)) {
                        throw new IOException(e);
                    }
                    throw e;
                }
            }

            @Override // a.s
            public t timeout() {
                return tVar;
            }

            public String toString() {
                return "source(" + inputStream + ")";
            }
        };
    }

    static boolean a(AssertionError assertionError) {
        return (assertionError.getCause() == null || assertionError.getMessage() == null || !assertionError.getMessage().contains("getsockname failed")) ? false : true;
    }

    public static r b(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        return a(new FileOutputStream(file));
    }

    public static s b(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        if (socket.getInputStream() == null) {
            throw new IOException("socket's input stream == null");
        }
        a aVarC = c(socket);
        return aVarC.source(a(socket.getInputStream(), aVarC));
    }

    private static a c(final Socket socket) {
        return new a() { // from class: a.l.4
            @Override // a.a
            protected IOException newTimeoutException(@Nullable IOException iOException) {
                SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
                if (iOException != null) {
                    socketTimeoutException.initCause(iOException);
                }
                return socketTimeoutException;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // a.a
            protected void timedOut() throws IOException {
                Level level;
                StringBuilder sb;
                Logger logger;
                Exception exc;
                try {
                    socket.close();
                } catch (AssertionError e) {
                    if (!l.a(e)) {
                        throw e;
                    }
                    Logger logger2 = l.f10a;
                    level = Level.WARNING;
                    sb = new StringBuilder();
                    exc = e;
                    logger = logger2;
                    sb.append("Failed to close timed out socket ");
                    sb.append(socket);
                    logger.log(level, sb.toString(), (Throwable) exc);
                } catch (Exception e2) {
                    Logger logger3 = l.f10a;
                    level = Level.WARNING;
                    sb = new StringBuilder();
                    exc = e2;
                    logger = logger3;
                    sb.append("Failed to close timed out socket ");
                    sb.append(socket);
                    logger.log(level, sb.toString(), (Throwable) exc);
                }
            }
        };
    }

    public static r c(File file) {
        if (file == null) {
            throw new IllegalArgumentException("file == null");
        }
        return a(new FileOutputStream(file, true));
    }
}
