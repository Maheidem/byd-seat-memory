package a;

import android.support.v4.media.session.PlaybackStateCompat;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
final class p {

    /* renamed from: a, reason: collision with root package name */
    @Nullable
    static o f18a;
    static long b;

    private p() {
    }

    static o a() {
        synchronized (p.class) {
            if (f18a == null) {
                return new o();
            }
            o oVar = f18a;
            f18a = oVar.f;
            oVar.f = null;
            b -= PlaybackStateCompat.ACTION_PLAY_FROM_URI;
            return oVar;
        }
    }

    static void a(o oVar) {
        if (oVar.f != null || oVar.g != null) {
            throw new IllegalArgumentException();
        }
        if (oVar.d) {
            return;
        }
        synchronized (p.class) {
            if (b + PlaybackStateCompat.ACTION_PLAY_FROM_URI > PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH) {
                return;
            }
            b += PlaybackStateCompat.ACTION_PLAY_FROM_URI;
            oVar.f = f18a;
            oVar.c = 0;
            oVar.b = 0;
            f18a = oVar;
        }
    }
}
