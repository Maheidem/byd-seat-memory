package a;

/* loaded from: classes.dex */
public abstract class g implements r {
    private final r delegate;

    public g(r rVar) {
        if (rVar == null) {
            throw new IllegalArgumentException("delegate == null");
        }
        this.delegate = rVar;
    }

    @Override // a.r, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.delegate.close();
    }

    public final r delegate() {
        return this.delegate;
    }

    @Override // a.r, java.io.Flushable
    public void flush() {
        this.delegate.flush();
    }

    @Override // a.r
    public t timeout() {
        return this.delegate.timeout();
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this.delegate.toString() + ")";
    }

    @Override // a.r
    public void write(c cVar, long j) {
        this.delegate.write(cVar, j);
    }
}
