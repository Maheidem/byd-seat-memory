package org.acra.collector;

import android.content.Context;
import android.os.Build;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public final class Compatibility {
    public static int getAPILevel() {
        try {
            return Build.VERSION.class.getField("SDK_INT").getInt(null);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException unused) {
            return Integer.parseInt(Build.VERSION.SDK);
        }
    }

    public static String getDropBoxServiceName() throws NoSuchFieldException {
        Field field = Context.class.getField("DROPBOX_SERVICE");
        if (field != null) {
            return (String) field.get(null);
        }
        return null;
    }
}
