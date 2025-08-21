package okhttp3.internal.http;

import a.e;
import javax.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.ResponseBody;

/* loaded from: classes.dex */
public final class RealResponseBody extends ResponseBody {
    private final long contentLength;

    @Nullable
    private final String contentTypeString;
    private final e source;

    public RealResponseBody(@Nullable String str, long j, e eVar) {
        this.contentTypeString = str;
        this.contentLength = j;
        this.source = eVar;
    }

    @Override // okhttp3.ResponseBody
    public long contentLength() {
        return this.contentLength;
    }

    @Override // okhttp3.ResponseBody
    public MediaType contentType() {
        if (this.contentTypeString != null) {
            return MediaType.parse(this.contentTypeString);
        }
        return null;
    }

    @Override // okhttp3.ResponseBody
    public e source() {
        return this.source;
    }
}
