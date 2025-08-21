package a;

import java.nio.channels.WritableByteChannel;

/* loaded from: classes.dex */
public interface d extends r, WritableByteChannel {
    long a(s sVar);

    c b();

    d b(String str);

    d c(f fVar);

    d c(byte[] bArr);

    d c(byte[] bArr, int i, int i2);

    d d();

    @Override // a.r, java.io.Flushable
    void flush();

    d g(int i);

    d h(int i);

    d i(int i);

    d m(long j);

    d n(long j);

    d w();
}
