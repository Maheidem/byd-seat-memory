package okhttp3;

import a.f;
import java.nio.charset.Charset;
import okhttp3.internal.Util;

/* loaded from: classes.dex */
public final class Credentials {
    private Credentials() {
    }

    public static String basic(String str, String str2) {
        return basic(str, str2, Util.ISO_8859_1);
    }

    public static String basic(String str, String str2, Charset charset) {
        return "Basic " + f.a(str + ":" + str2, charset).b();
    }
}
