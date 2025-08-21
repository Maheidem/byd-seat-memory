package android.support.v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.PopupWindow;

@RequiresApi(19)
@TargetApi(19)
/* loaded from: classes.dex */
class PopupWindowCompatKitKat {
    PopupWindowCompatKitKat() {
    }

    public static void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3) {
        popupWindow.showAsDropDown(view, i, i2, i3);
    }
}
