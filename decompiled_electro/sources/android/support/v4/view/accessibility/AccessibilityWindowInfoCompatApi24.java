package android.support.v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityWindowInfo;

@RequiresApi(24)
@TargetApi(24)
/* loaded from: classes.dex */
class AccessibilityWindowInfoCompatApi24 {
    AccessibilityWindowInfoCompatApi24() {
    }

    public static Object getAnchor(Object obj) {
        return ((AccessibilityWindowInfo) obj).getAnchor();
    }

    public static CharSequence getTitle(Object obj) {
        return ((AccessibilityWindowInfo) obj).getTitle();
    }
}
