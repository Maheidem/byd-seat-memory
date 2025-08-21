package android.support.v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.widget.PopupWindow;

@RequiresApi(23)
@TargetApi(23)
/* loaded from: classes.dex */
class PopupWindowCompatApi23 {
    PopupWindowCompatApi23() {
    }

    static boolean getOverlapAnchor(PopupWindow popupWindow) {
        return popupWindow.getOverlapAnchor();
    }

    static int getWindowLayoutType(PopupWindow popupWindow) {
        return popupWindow.getWindowLayoutType();
    }

    static void setOverlapAnchor(PopupWindow popupWindow, boolean z) {
        popupWindow.setOverlapAnchor(z);
    }

    static void setWindowLayoutType(PopupWindow popupWindow, int i) {
        popupWindow.setWindowLayoutType(i);
    }
}
