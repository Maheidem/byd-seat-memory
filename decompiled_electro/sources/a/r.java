package a;

import java.io.Closeable;
import java.io.Flushable;

/* loaded from: classes.dex */
public interface r extends Closeable, Flushable {
    @Override // java.io.Closeable, java.lang.AutoCloseable
    void close();

    void flush();

    t timeout();

    void write(c cVar, long j);
}
