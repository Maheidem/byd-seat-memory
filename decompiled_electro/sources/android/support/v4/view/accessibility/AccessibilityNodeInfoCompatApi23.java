package android.support.v4.view.accessibility;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;

@RequiresApi(23)
@TargetApi(23)
/* loaded from: classes.dex */
class AccessibilityNodeInfoCompatApi23 {
    AccessibilityNodeInfoCompatApi23() {
    }

    public static Object getActionContextClick() {
        return AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK;
    }

    public static Object getActionScrollDown() {
        return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN;
    }

    public static Object getActionScrollLeft() {
        return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT;
    }

    public static Object getActionScrollRight() {
        return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT;
    }

    public static Object getActionScrollToPosition() {
        return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION;
    }

    public static Object getActionScrollUp() {
        return AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP;
    }

    public static Object getActionShowOnScreen() {
        return AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN;
    }

    public static boolean isContextClickable(Object obj) {
        return ((AccessibilityNodeInfo) obj).isContextClickable();
    }

    public static void setContextClickable(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setContextClickable(z);
    }
}
