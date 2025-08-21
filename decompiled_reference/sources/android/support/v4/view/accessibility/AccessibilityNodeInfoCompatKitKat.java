package android.support.v4.view.accessibility;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;

@RequiresApi(19)
@TargetApi(19)
/* loaded from: classes.dex */
class AccessibilityNodeInfoCompatKitKat {
    private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";
    private static final String TRAITS_KEY = "android.view.accessibility.AccessibilityNodeInfo.traits";
    private static final long TRAIT_HAS_IMAGE = 1;
    private static final byte TRAIT_UNSET = -1;

    static class CollectionInfo {
        CollectionInfo() {
        }

        static int getColumnCount(Object obj) {
            return ((AccessibilityNodeInfo.CollectionInfo) obj).getColumnCount();
        }

        static int getRowCount(Object obj) {
            return ((AccessibilityNodeInfo.CollectionInfo) obj).getRowCount();
        }

        static boolean isHierarchical(Object obj) {
            return ((AccessibilityNodeInfo.CollectionInfo) obj).isHierarchical();
        }
    }

    static class CollectionItemInfo {
        CollectionItemInfo() {
        }

        static int getColumnIndex(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).getColumnIndex();
        }

        static int getColumnSpan(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).getColumnSpan();
        }

        static int getRowIndex(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).getRowIndex();
        }

        static int getRowSpan(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).getRowSpan();
        }

        static boolean isHeading(Object obj) {
            return ((AccessibilityNodeInfo.CollectionItemInfo) obj).isHeading();
        }
    }

    static class RangeInfo {
        RangeInfo() {
        }

        static float getCurrent(Object obj) {
            return ((AccessibilityNodeInfo.RangeInfo) obj).getCurrent();
        }

        static float getMax(Object obj) {
            return ((AccessibilityNodeInfo.RangeInfo) obj).getMax();
        }

        static float getMin(Object obj) {
            return ((AccessibilityNodeInfo.RangeInfo) obj).getMin();
        }

        static int getType(Object obj) {
            return ((AccessibilityNodeInfo.RangeInfo) obj).getType();
        }
    }

    AccessibilityNodeInfoCompatKitKat() {
    }

    public static boolean canOpenPopup(Object obj) {
        return ((AccessibilityNodeInfo) obj).canOpenPopup();
    }

    static Object getCollectionInfo(Object obj) {
        return ((AccessibilityNodeInfo) obj).getCollectionInfo();
    }

    static Object getCollectionItemInfo(Object obj) {
        return ((AccessibilityNodeInfo) obj).getCollectionItemInfo();
    }

    public static Bundle getExtras(Object obj) {
        return ((AccessibilityNodeInfo) obj).getExtras();
    }

    public static int getInputType(Object obj) {
        return ((AccessibilityNodeInfo) obj).getInputType();
    }

    static int getLiveRegion(Object obj) {
        return ((AccessibilityNodeInfo) obj).getLiveRegion();
    }

    static Object getRangeInfo(Object obj) {
        return ((AccessibilityNodeInfo) obj).getRangeInfo();
    }

    public static CharSequence getRoleDescription(Object obj) {
        return getExtras(obj).getCharSequence(ROLE_DESCRIPTION_KEY);
    }

    private static long getTraits(Object obj) {
        return getExtras(obj).getLong(TRAITS_KEY, -1L);
    }

    public static boolean isContentInvalid(Object obj) {
        return ((AccessibilityNodeInfo) obj).isContentInvalid();
    }

    public static boolean isDismissable(Object obj) {
        return ((AccessibilityNodeInfo) obj).isDismissable();
    }

    public static boolean isMultiLine(Object obj) {
        return ((AccessibilityNodeInfo) obj).isMultiLine();
    }

    public static Object obtainCollectionInfo(int i, int i2, boolean z) {
        return AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z);
    }

    public static Object obtainCollectionInfo(int i, int i2, boolean z, int i3) {
        return AccessibilityNodeInfo.CollectionInfo.obtain(i, i2, z);
    }

    public static Object obtainCollectionItemInfo(int i, int i2, int i3, int i4, boolean z) {
        return AccessibilityNodeInfo.CollectionItemInfo.obtain(i, i2, i3, i4, z);
    }

    public static Object obtainRangeInfo(int i, float f, float f2, float f3) {
        return AccessibilityNodeInfo.RangeInfo.obtain(i, f, f2, f3);
    }

    public static void setCanOpenPopup(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setCanOpenPopup(z);
    }

    public static void setCollectionInfo(Object obj, Object obj2) {
        ((AccessibilityNodeInfo) obj).setCollectionInfo((AccessibilityNodeInfo.CollectionInfo) obj2);
    }

    public static void setCollectionItemInfo(Object obj, Object obj2) {
        ((AccessibilityNodeInfo) obj).setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo) obj2);
    }

    public static void setContentInvalid(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setContentInvalid(z);
    }

    public static void setDismissable(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setDismissable(z);
    }

    public static void setInputType(Object obj, int i) {
        ((AccessibilityNodeInfo) obj).setInputType(i);
    }

    static void setLiveRegion(Object obj, int i) {
        ((AccessibilityNodeInfo) obj).setLiveRegion(i);
    }

    public static void setMultiLine(Object obj, boolean z) {
        ((AccessibilityNodeInfo) obj).setMultiLine(z);
    }

    public static void setRangeInfo(Object obj, Object obj2) {
        ((AccessibilityNodeInfo) obj).setRangeInfo((AccessibilityNodeInfo.RangeInfo) obj2);
    }

    public static void setRoleDescription(Object obj, CharSequence charSequence) {
        getExtras(obj).putCharSequence(ROLE_DESCRIPTION_KEY, charSequence);
    }

    private static void setTrait(Object obj, long j) {
        Bundle extras = getExtras(obj);
        extras.putLong(TRAITS_KEY, j | extras.getLong(TRAITS_KEY, 0L));
    }
}
