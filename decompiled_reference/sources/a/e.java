package a;

import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.Charset;

/* loaded from: classes.dex */
public interface e extends s, ReadableByteChannel {
    long a(byte b);

    String a(Charset charset);

    void a(long j);

    void a(c cVar, long j);

    void a(byte[] bArr);

    boolean a(long j, f fVar);

    c b();

    boolean b(long j);

    f d(long j);

    boolean e();

    InputStream f();

    String f(long j);

    byte h();

    byte[] h(long j);

    short i();

    void i(long j);

    int j();

    long k();

    short l();

    int m();

    long n();

    long o();

    String r();

    byte[] s();
}
