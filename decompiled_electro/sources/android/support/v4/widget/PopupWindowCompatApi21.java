package android.support.v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.PopupWindow;
import java.lang.reflect.Field;

@RequiresApi(21)
@TargetApi(21)
/* loaded from: classes.dex */
class PopupWindowCompatApi21 {
    private static final String TAG = "PopupWindowCompatApi21";
    private static Field sOverlapAnchorField;

    static {
        try {
            sOverlapAnchorField = PopupWindow.class.getDeclaredField("mOverlapAnchor");
            sOverlapAnchorField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.i(TAG, "Could not fetch mOverlapAnchor field from PopupWindow", e);
        }
    }

    PopupWindowCompatApi21() {
    }

    static boolean getOverlapAnchor(PopupWindow popupWindow) {
        if (sOverlapAnchorField == null) {
            return false;
        }
        try {
            return ((Boolean) sOverlapAnchorField.get(popupWindow)).booleanValue();
        } catch (IllegalAccessException e) {
            Log.i(TAG, "Could not get overlap anchor field in PopupWindow", e);
            return false;
        }
    }

    static void setOverlapAnchor(PopupWindow popupWindow, boolean z) throws IllegalAccessException, IllegalArgumentException {
        if (sOverlapAnchorField != null) {
            try {
                sOverlapAnchorField.set(popupWindow, Boolean.valueOf(z));
            } catch (IllegalAccessException e) {
                Log.i(TAG, "Could not set overlap anchor field in PopupWindow", e);
            }
        }
    }
}
