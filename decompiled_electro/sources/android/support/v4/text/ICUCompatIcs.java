package android.support.v4.text;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class ICUCompatIcs {
    private static final String TAG = "ICUCompatIcs";
    private static Method sAddLikelySubtagsMethod;
    private static Method sGetScriptMethod;

    static {
        try {
            Class<?> cls = Class.forName("libcore.icu.ICU");
            if (cls != null) {
                sGetScriptMethod = cls.getMethod("getScript", String.class);
                sAddLikelySubtagsMethod = cls.getMethod("addLikelySubtags", String.class);
            }
        } catch (Exception e) {
            sGetScriptMethod = null;
            sAddLikelySubtagsMethod = null;
            Log.w(TAG, e);
        }
    }

    ICUCompatIcs() {
    }

    private static String addLikelySubtags(Locale locale) {
        String string = locale.toString();
        try {
            if (sAddLikelySubtagsMethod != null) {
                return (String) sAddLikelySubtagsMethod.invoke(null, string);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            Log.w(TAG, e);
        }
        return string;
    }

    private static String getScript(String str) {
        try {
            if (sGetScriptMethod != null) {
                return (String) sGetScriptMethod.invoke(null, str);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            Log.w(TAG, e);
        }
        return null;
    }

    public static String maximizeAndGetScript(Locale locale) {
        String strAddLikelySubtags = addLikelySubtags(locale);
        if (strAddLikelySubtags != null) {
            return getScript(strAddLikelySubtags);
        }
        return null;
    }
}
