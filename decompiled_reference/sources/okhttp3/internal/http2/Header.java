package okhttp3.internal.http2;

import a.f;
import okhttp3.Headers;
import okhttp3.internal.Util;

/* loaded from: classes.dex */
public final class Header {
    final int hpackSize;
    public final f name;
    public final f value;
    public static final f PSEUDO_PREFIX = f.a(":");
    public static final String RESPONSE_STATUS_UTF8 = ":status";
    public static final f RESPONSE_STATUS = f.a(RESPONSE_STATUS_UTF8);
    public static final String TARGET_METHOD_UTF8 = ":method";
    public static final f TARGET_METHOD = f.a(TARGET_METHOD_UTF8);
    public static final String TARGET_PATH_UTF8 = ":path";
    public static final f TARGET_PATH = f.a(TARGET_PATH_UTF8);
    public static final String TARGET_SCHEME_UTF8 = ":scheme";
    public static final f TARGET_SCHEME = f.a(TARGET_SCHEME_UTF8);
    public static final String TARGET_AUTHORITY_UTF8 = ":authority";
    public static final f TARGET_AUTHORITY = f.a(TARGET_AUTHORITY_UTF8);

    interface Listener {
        void onHeaders(Headers headers);
    }

    public Header(f fVar, f fVar2) {
        this.name = fVar;
        this.value = fVar2;
        this.hpackSize = 32 + fVar.h() + fVar2.h();
    }

    public Header(f fVar, String str) {
        this(fVar, f.a(str));
    }

    public Header(String str, String str2) {
        this(f.a(str), f.a(str2));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Header)) {
            return false;
        }
        Header header = (Header) obj;
        return this.name.equals(header.name) && this.value.equals(header.value);
    }

    public int hashCode() {
        return (31 * (527 + this.name.hashCode())) + this.value.hashCode();
    }

    public String toString() {
        return Util.format("%s: %s", this.name.a(), this.value.a());
    }
}
