package a;

import java.io.Closeable;

/* loaded from: classes.dex */
public interface s extends Closeable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close();

    long read(c cVar, long j);

    t timeout();
}
