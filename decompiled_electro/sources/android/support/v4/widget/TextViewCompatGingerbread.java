package android.support.v4.widget;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.TextView;
import java.lang.reflect.Field;

@RequiresApi(9)
@TargetApi(9)
/* loaded from: classes.dex */
class TextViewCompatGingerbread {
    private static final int LINES = 1;
    private static final String LOG_TAG = "TextViewCompatGingerbread";
    private static Field sMaxModeField;
    private static boolean sMaxModeFieldFetched;
    private static Field sMaximumField;
    private static boolean sMaximumFieldFetched;
    private static Field sMinModeField;
    private static boolean sMinModeFieldFetched;
    private static Field sMinimumField;
    private static boolean sMinimumFieldFetched;

    TextViewCompatGingerbread() {
    }

    static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        return textView.getCompoundDrawables();
    }

    static int getMaxLines(TextView textView) {
        if (!sMaxModeFieldFetched) {
            sMaxModeField = retrieveField("mMaxMode");
            sMaxModeFieldFetched = true;
        }
        if (sMaxModeField == null || retrieveIntFromField(sMaxModeField, textView) != 1) {
            return -1;
        }
        if (!sMaximumFieldFetched) {
            sMaximumField = retrieveField("mMaximum");
            sMaximumFieldFetched = true;
        }
        if (sMaximumField != null) {
            return retrieveIntFromField(sMaximumField, textView);
        }
        return -1;
    }

    static int getMinLines(TextView textView) {
        if (!sMinModeFieldFetched) {
            sMinModeField = retrieveField("mMinMode");
            sMinModeFieldFetched = true;
        }
        if (sMinModeField == null || retrieveIntFromField(sMinModeField, textView) != 1) {
            return -1;
        }
        if (!sMinimumFieldFetched) {
            sMinimumField = retrieveField("mMinimum");
            sMinimumFieldFetched = true;
        }
        if (sMinimumField != null) {
            return retrieveIntFromField(sMinimumField, textView);
        }
        return -1;
    }

    private static Field retrieveField(String str) throws NoSuchFieldException {
        Field declaredField;
        try {
            declaredField = TextView.class.getDeclaredField(str);
            try {
                declaredField.setAccessible(true);
                return declaredField;
            } catch (NoSuchFieldException unused) {
                Log.e(LOG_TAG, "Could not retrieve " + str + " field.");
                return declaredField;
            }
        } catch (NoSuchFieldException unused2) {
            declaredField = null;
        }
    }

    private static int retrieveIntFromField(Field field, TextView textView) {
        try {
            return field.getInt(textView);
        } catch (IllegalAccessException unused) {
            Log.d(LOG_TAG, "Could not retrieve value of " + field.getName() + " field.");
            return -1;
        }
    }

    static void setTextAppearance(TextView textView, int i) {
        textView.setTextAppearance(textView.getContext(), i);
    }
}
