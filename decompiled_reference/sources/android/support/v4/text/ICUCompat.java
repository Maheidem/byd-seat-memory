package android.support.v4.text;

import android.os.Build;
import java.util.Locale;

/* loaded from: classes.dex */
public final class ICUCompat {
    private static final ICUCompatImpl IMPL;

    interface ICUCompatImpl {
        String maximizeAndGetScript(Locale locale);
    }

    static class ICUCompatImplBase implements ICUCompatImpl {
        ICUCompatImplBase() {
        }

        @Override // android.support.v4.text.ICUCompat.ICUCompatImpl
        public String maximizeAndGetScript(Locale locale) {
            return null;
        }
    }

    static class ICUCompatImplIcs implements ICUCompatImpl {
        ICUCompatImplIcs() {
        }

        @Override // android.support.v4.text.ICUCompat.ICUCompatImpl
        public String maximizeAndGetScript(Locale locale) {
            return ICUCompatIcs.maximizeAndGetScript(locale);
        }
    }

    static class ICUCompatImplLollipop implements ICUCompatImpl {
        ICUCompatImplLollipop() {
        }

        @Override // android.support.v4.text.ICUCompat.ICUCompatImpl
        public String maximizeAndGetScript(Locale locale) {
            return ICUCompatApi23.maximizeAndGetScript(locale);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        int i = Build.VERSION.SDK_INT;
        IMPL = i >= 21 ? new ICUCompatImplLollipop() : i >= 14 ? new ICUCompatImplIcs() : new ICUCompatImplBase();
    }

    private ICUCompat() {
    }

    public static String maximizeAndGetScript(Locale locale) {
        return IMPL.maximizeAndGetScript(locale);
    }
}
