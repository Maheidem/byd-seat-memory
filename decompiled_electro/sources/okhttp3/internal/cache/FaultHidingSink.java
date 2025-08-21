package okhttp3.internal.cache;

import a.c;
import a.g;
import a.r;
import java.io.EOFException;
import java.io.IOException;

/* loaded from: classes.dex */
class FaultHidingSink extends g {
    private boolean hasErrors;

    FaultHidingSink(r rVar) {
        super(rVar);
    }

    @Override // a.g, a.r, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.hasErrors) {
            return;
        }
        try {
            super.close();
        } catch (IOException e) {
            this.hasErrors = true;
            onException(e);
        }
    }

    @Override // a.g, a.r, java.io.Flushable
    public void flush() {
        if (this.hasErrors) {
            return;
        }
        try {
            super.flush();
        } catch (IOException e) {
            this.hasErrors = true;
            onException(e);
        }
    }

    protected void onException(IOException iOException) {
    }

    @Override // a.g, a.r
    public void write(c cVar, long j) throws EOFException {
        if (this.hasErrors) {
            cVar.i(j);
            return;
        }
        try {
            super.write(cVar, j);
        } catch (IOException e) {
            this.hasErrors = true;
            onException(e);
        }
    }
}
