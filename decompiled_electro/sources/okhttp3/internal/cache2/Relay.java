package okhttp3.internal.cache2;

import a.c;
import a.f;
import a.s;
import a.t;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import okhttp3.internal.Util;

/* loaded from: classes.dex */
final class Relay {
    private static final long FILE_HEADER_SIZE = 32;
    static final f PREFIX_CLEAN = f.a("OkHttp cache v1\n");
    static final f PREFIX_DIRTY = f.a("OkHttp DIRTY :(\n");
    private static final int SOURCE_FILE = 2;
    private static final int SOURCE_UPSTREAM = 1;
    final long bufferMaxSize;
    boolean complete;
    RandomAccessFile file;
    private final f metadata;
    int sourceCount;
    s upstream;
    long upstreamPos;
    Thread upstreamReader;
    final c upstreamBuffer = new c();
    final c buffer = new c();

    class RelaySource implements s {
        private FileOperator fileOperator;
        private long sourcePos;
        private final t timeout = new t();

        RelaySource() {
            this.fileOperator = new FileOperator(Relay.this.file.getChannel());
        }

        @Override // a.s, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            if (this.fileOperator == null) {
                return;
            }
            RandomAccessFile randomAccessFile = null;
            this.fileOperator = null;
            synchronized (Relay.this) {
                Relay relay = Relay.this;
                relay.sourceCount--;
                if (Relay.this.sourceCount == 0) {
                    RandomAccessFile randomAccessFile2 = Relay.this.file;
                    Relay.this.file = null;
                    randomAccessFile = randomAccessFile2;
                }
            }
            if (randomAccessFile != null) {
                Util.closeQuietly(randomAccessFile);
            }
        }

        @Override // a.s
        public long read(c cVar, long j) throws IOException {
            long j2;
            char c;
            if (this.fileOperator == null) {
                throw new IllegalStateException("closed");
            }
            synchronized (Relay.this) {
                while (true) {
                    long j3 = this.sourcePos;
                    j2 = Relay.this.upstreamPos;
                    if (j3 != j2) {
                        long jA = j2 - Relay.this.buffer.a();
                        if (this.sourcePos >= jA) {
                            long jMin = Math.min(j, j2 - this.sourcePos);
                            Relay.this.buffer.a(cVar, this.sourcePos - jA, jMin);
                            this.sourcePos += jMin;
                            return jMin;
                        }
                        c = 2;
                    } else if (!Relay.this.complete) {
                        if (Relay.this.upstreamReader == null) {
                            Relay.this.upstreamReader = Thread.currentThread();
                            c = 1;
                            break;
                        }
                        this.timeout.waitUntilNotified(Relay.this);
                    } else {
                        return -1L;
                    }
                }
                if (c == 2) {
                    long jMin2 = Math.min(j, j2 - this.sourcePos);
                    this.fileOperator.read(32 + this.sourcePos, cVar, jMin2);
                    this.sourcePos += jMin2;
                    return jMin2;
                }
                try {
                    long j4 = Relay.this.upstream.read(Relay.this.upstreamBuffer, Relay.this.bufferMaxSize);
                    if (j4 == -1) {
                        Relay.this.commit(j2);
                        synchronized (Relay.this) {
                            Relay.this.upstreamReader = null;
                            Relay.this.notifyAll();
                        }
                        return -1L;
                    }
                    long jMin3 = Math.min(j4, j);
                    Relay.this.upstreamBuffer.a(cVar, 0L, jMin3);
                    this.sourcePos += jMin3;
                    this.fileOperator.write(32 + j2, Relay.this.upstreamBuffer.clone(), j4);
                    synchronized (Relay.this) {
                        Relay.this.buffer.write(Relay.this.upstreamBuffer, j4);
                        if (Relay.this.buffer.a() > Relay.this.bufferMaxSize) {
                            Relay.this.buffer.i(Relay.this.buffer.a() - Relay.this.bufferMaxSize);
                        }
                        Relay.this.upstreamPos += j4;
                    }
                    synchronized (Relay.this) {
                        Relay.this.upstreamReader = null;
                        Relay.this.notifyAll();
                    }
                    return jMin3;
                } catch (Throwable th) {
                    synchronized (Relay.this) {
                        Relay.this.upstreamReader = null;
                        Relay.this.notifyAll();
                        throw th;
                    }
                }
            }
        }

        @Override // a.s
        public t timeout() {
            return this.timeout;
        }
    }

    private Relay(RandomAccessFile randomAccessFile, s sVar, long j, f fVar, long j2) {
        this.file = randomAccessFile;
        this.upstream = sVar;
        this.complete = sVar == null;
        this.upstreamPos = j;
        this.metadata = fVar;
        this.bufferMaxSize = j2;
    }

    public static Relay edit(File file, s sVar, f fVar, long j) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        Relay relay = new Relay(randomAccessFile, sVar, 0L, fVar, j);
        randomAccessFile.setLength(0L);
        relay.writeHeader(PREFIX_DIRTY, -1L, -1L);
        return relay;
    }

    public static Relay read(File file) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
        FileOperator fileOperator = new FileOperator(randomAccessFile.getChannel());
        c cVar = new c();
        fileOperator.read(0L, cVar, 32L);
        if (!cVar.d(PREFIX_CLEAN.h()).equals(PREFIX_CLEAN)) {
            throw new IOException("unreadable cache file");
        }
        long jK = cVar.k();
        long jK2 = cVar.k();
        c cVar2 = new c();
        fileOperator.read(32 + jK, cVar2, jK2);
        return new Relay(randomAccessFile, null, jK, cVar2.p(), 0L);
    }

    private void writeHeader(f fVar, long j, long j2) throws IOException {
        c cVar = new c();
        cVar.c(fVar);
        cVar.j(j);
        cVar.j(j2);
        if (cVar.a() != 32) {
            throw new IllegalArgumentException();
        }
        new FileOperator(this.file.getChannel()).write(0L, cVar, 32L);
    }

    private void writeMetadata(long j) throws IOException {
        c cVar = new c();
        cVar.c(this.metadata);
        new FileOperator(this.file.getChannel()).write(32 + j, cVar, this.metadata.h());
    }

    void commit(long j) throws IOException {
        writeMetadata(j);
        this.file.getChannel().force(false);
        writeHeader(PREFIX_CLEAN, j, this.metadata.h());
        this.file.getChannel().force(false);
        synchronized (this) {
            this.complete = true;
        }
        Util.closeQuietly(this.upstream);
        this.upstream = null;
    }

    boolean isClosed() {
        return this.file == null;
    }

    public f metadata() {
        return this.metadata;
    }

    public s newSource() {
        synchronized (this) {
            if (this.file == null) {
                return null;
            }
            this.sourceCount++;
            return new RelaySource();
        }
    }
}
