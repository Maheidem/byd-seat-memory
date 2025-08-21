package android.support.v4.view;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@RequiresApi(18)
@TargetApi(18)
/* loaded from: classes.dex */
class ViewGroupCompatJellybeanMR2 {
    ViewGroupCompatJellybeanMR2() {
    }

    public static int getLayoutMode(ViewGroup viewGroup) {
        return viewGroup.getLayoutMode();
    }

    public static void setLayoutMode(ViewGroup viewGroup, int i) {
        viewGroup.setLayoutMode(i);
    }
}
