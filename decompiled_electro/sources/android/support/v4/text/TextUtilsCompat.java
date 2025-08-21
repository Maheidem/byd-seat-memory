package android.support.v4.text;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Locale;

/* loaded from: classes.dex */
public final class TextUtilsCompat {
    static String ARAB_SCRIPT_SUBTAG = "Arab";
    static String HEBR_SCRIPT_SUBTAG = "Hebr";
    private static final TextUtilsCompatImpl IMPL;
    public static final Locale ROOT;

    private static class TextUtilsCompatImpl {
        TextUtilsCompatImpl() {
        }

        private static int getLayoutDirectionFromFirstChar(@NonNull Locale locale) {
            switch (Character.getDirectionality(locale.getDisplayName(locale).charAt(0))) {
                case 1:
                case 2:
                    return 1;
                default:
                    return 0;
            }
        }

        public int getLayoutDirectionFromLocale(@Nullable Locale locale) {
            if (locale == null || locale.equals(TextUtilsCompat.ROOT)) {
                return 0;
            }
            String strMaximizeAndGetScript = ICUCompat.maximizeAndGetScript(locale);
            return strMaximizeAndGetScript == null ? getLayoutDirectionFromFirstChar(locale) : (strMaximizeAndGetScript.equalsIgnoreCase(TextUtilsCompat.ARAB_SCRIPT_SUBTAG) || strMaximizeAndGetScript.equalsIgnoreCase(TextUtilsCompat.HEBR_SCRIPT_SUBTAG)) ? 1 : 0;
        }

        @NonNull
        public String htmlEncode(@NonNull String str) {
            String str2;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char cCharAt = str.charAt(i);
                if (cCharAt == '\"') {
                    str2 = "&quot;";
                } else if (cCharAt == '<') {
                    str2 = "&lt;";
                } else if (cCharAt != '>') {
                    switch (cCharAt) {
                        case '&':
                            str2 = "&amp;";
                            break;
                        case '\'':
                            str2 = "&#39;";
                            break;
                        default:
                            sb.append(cCharAt);
                            continue;
                    }
                } else {
                    str2 = "&gt;";
                }
                sb.append(str2);
            }
            return sb.toString();
        }
    }

    private static class TextUtilsCompatJellybeanMr1Impl extends TextUtilsCompatImpl {
        TextUtilsCompatJellybeanMr1Impl() {
        }

        @Override // android.support.v4.text.TextUtilsCompat.TextUtilsCompatImpl
        public int getLayoutDirectionFromLocale(@Nullable Locale locale) {
            return TextUtilsCompatJellybeanMr1.getLayoutDirectionFromLocale(locale);
        }

        @Override // android.support.v4.text.TextUtilsCompat.TextUtilsCompatImpl
        @NonNull
        public String htmlEncode(@NonNull String str) {
            return TextUtilsCompatJellybeanMr1.htmlEncode(str);
        }
    }

    static {
        IMPL = Build.VERSION.SDK_INT >= 17 ? new TextUtilsCompatJellybeanMr1Impl() : new TextUtilsCompatImpl();
        ROOT = new Locale("", "");
    }

    private TextUtilsCompat() {
    }

    public static int getLayoutDirectionFromLocale(@Nullable Locale locale) {
        return IMPL.getLayoutDirectionFromLocale(locale);
    }

    @NonNull
    public static String htmlEncode(@NonNull String str) {
        return IMPL.htmlEncode(str);
    }
}
