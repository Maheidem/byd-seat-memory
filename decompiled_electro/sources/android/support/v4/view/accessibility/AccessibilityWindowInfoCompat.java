package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build;

/* loaded from: classes.dex */
public class AccessibilityWindowInfoCompat {
    private static final AccessibilityWindowInfoImpl IMPL;
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    private static final int UNDEFINED = -1;
    private Object mInfo;

    private static class AccessibilityWindowInfoApi21Impl extends AccessibilityWindowInfoStubImpl {
        AccessibilityWindowInfoApi21Impl() {
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public void getBoundsInScreen(Object obj, Rect rect) {
            AccessibilityWindowInfoCompatApi21.getBoundsInScreen(obj, rect);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object getChild(Object obj, int i) {
            return AccessibilityWindowInfoCompatApi21.getChild(obj, i);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public int getChildCount(Object obj) {
            return AccessibilityWindowInfoCompatApi21.getChildCount(obj);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public int getId(Object obj) {
            return AccessibilityWindowInfoCompatApi21.getId(obj);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public int getLayer(Object obj) {
            return AccessibilityWindowInfoCompatApi21.getLayer(obj);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object getParent(Object obj) {
            return AccessibilityWindowInfoCompatApi21.getParent(obj);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object getRoot(Object obj) {
            return AccessibilityWindowInfoCompatApi21.getRoot(obj);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public int getType(Object obj) {
            return AccessibilityWindowInfoCompatApi21.getType(obj);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public boolean isAccessibilityFocused(Object obj) {
            return AccessibilityWindowInfoCompatApi21.isAccessibilityFocused(obj);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public boolean isActive(Object obj) {
            return AccessibilityWindowInfoCompatApi21.isActive(obj);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public boolean isFocused(Object obj) {
            return AccessibilityWindowInfoCompatApi21.isFocused(obj);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object obtain() {
            return AccessibilityWindowInfoCompatApi21.obtain();
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object obtain(Object obj) {
            return AccessibilityWindowInfoCompatApi21.obtain(obj);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public void recycle(Object obj) {
            AccessibilityWindowInfoCompatApi21.recycle(obj);
        }
    }

    private static class AccessibilityWindowInfoApi24Impl extends AccessibilityWindowInfoApi21Impl {
        AccessibilityWindowInfoApi24Impl() {
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object getAnchor(Object obj) {
            return AccessibilityWindowInfoCompatApi24.getAnchor(obj);
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoStubImpl, android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public CharSequence getTitle(Object obj) {
            return AccessibilityWindowInfoCompatApi24.getTitle(obj);
        }
    }

    private interface AccessibilityWindowInfoImpl {
        Object getAnchor(Object obj);

        void getBoundsInScreen(Object obj, Rect rect);

        Object getChild(Object obj, int i);

        int getChildCount(Object obj);

        int getId(Object obj);

        int getLayer(Object obj);

        Object getParent(Object obj);

        Object getRoot(Object obj);

        CharSequence getTitle(Object obj);

        int getType(Object obj);

        boolean isAccessibilityFocused(Object obj);

        boolean isActive(Object obj);

        boolean isFocused(Object obj);

        Object obtain();

        Object obtain(Object obj);

        void recycle(Object obj);
    }

    private static class AccessibilityWindowInfoStubImpl implements AccessibilityWindowInfoImpl {
        AccessibilityWindowInfoStubImpl() {
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object getAnchor(Object obj) {
            return null;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public void getBoundsInScreen(Object obj, Rect rect) {
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object getChild(Object obj, int i) {
            return null;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public int getChildCount(Object obj) {
            return 0;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public int getId(Object obj) {
            return -1;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public int getLayer(Object obj) {
            return -1;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object getParent(Object obj) {
            return null;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object getRoot(Object obj) {
            return null;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public CharSequence getTitle(Object obj) {
            return null;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public int getType(Object obj) {
            return -1;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public boolean isAccessibilityFocused(Object obj) {
            return true;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public boolean isActive(Object obj) {
            return true;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public boolean isFocused(Object obj) {
            return true;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object obtain() {
            return null;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public Object obtain(Object obj) {
            return null;
        }

        @Override // android.support.v4.view.accessibility.AccessibilityWindowInfoCompat.AccessibilityWindowInfoImpl
        public void recycle(Object obj) {
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        IMPL = Build.VERSION.SDK_INT >= 24 ? new AccessibilityWindowInfoApi24Impl() : Build.VERSION.SDK_INT >= 21 ? new AccessibilityWindowInfoApi21Impl() : new AccessibilityWindowInfoStubImpl();
    }

    private AccessibilityWindowInfoCompat(Object obj) {
        this.mInfo = obj;
    }

    public static AccessibilityWindowInfoCompat obtain() {
        return wrapNonNullInstance(IMPL.obtain());
    }

    public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat accessibilityWindowInfoCompat) {
        if (accessibilityWindowInfoCompat == null) {
            return null;
        }
        return wrapNonNullInstance(IMPL.obtain(accessibilityWindowInfoCompat.mInfo));
    }

    private static String typeToString(int i) {
        switch (i) {
            case 1:
                return "TYPE_APPLICATION";
            case 2:
                return "TYPE_INPUT_METHOD";
            case 3:
                return "TYPE_SYSTEM";
            case 4:
                return "TYPE_ACCESSIBILITY_OVERLAY";
            default:
                return "<UNKNOWN>";
        }
    }

    static AccessibilityWindowInfoCompat wrapNonNullInstance(Object obj) {
        if (obj != null) {
            return new AccessibilityWindowInfoCompat(obj);
        }
        return null;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityWindowInfoCompat accessibilityWindowInfoCompat = (AccessibilityWindowInfoCompat) obj;
        if (this.mInfo == null) {
            if (accessibilityWindowInfoCompat.mInfo != null) {
                return false;
            }
        } else if (!this.mInfo.equals(accessibilityWindowInfoCompat.mInfo)) {
            return false;
        }
        return true;
    }

    public AccessibilityNodeInfoCompat getAnchor() {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.getAnchor(this.mInfo));
    }

    public void getBoundsInScreen(Rect rect) {
        IMPL.getBoundsInScreen(this.mInfo, rect);
    }

    public AccessibilityWindowInfoCompat getChild(int i) {
        return wrapNonNullInstance(IMPL.getChild(this.mInfo, i));
    }

    public int getChildCount() {
        return IMPL.getChildCount(this.mInfo);
    }

    public int getId() {
        return IMPL.getId(this.mInfo);
    }

    public int getLayer() {
        return IMPL.getLayer(this.mInfo);
    }

    public AccessibilityWindowInfoCompat getParent() {
        return wrapNonNullInstance(IMPL.getParent(this.mInfo));
    }

    public AccessibilityNodeInfoCompat getRoot() {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.getRoot(this.mInfo));
    }

    public CharSequence getTitle() {
        return IMPL.getTitle(this.mInfo);
    }

    public int getType() {
        return IMPL.getType(this.mInfo);
    }

    public int hashCode() {
        if (this.mInfo == null) {
            return 0;
        }
        return this.mInfo.hashCode();
    }

    public boolean isAccessibilityFocused() {
        return IMPL.isAccessibilityFocused(this.mInfo);
    }

    public boolean isActive() {
        return IMPL.isActive(this.mInfo);
    }

    public boolean isFocused() {
        return IMPL.isFocused(this.mInfo);
    }

    public void recycle() {
        IMPL.recycle(this.mInfo);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Rect rect = new Rect();
        getBoundsInScreen(rect);
        sb.append("AccessibilityWindowInfo[");
        sb.append("id=");
        sb.append(getId());
        sb.append(", type=");
        sb.append(typeToString(getType()));
        sb.append(", layer=");
        sb.append(getLayer());
        sb.append(", bounds=");
        sb.append(rect);
        sb.append(", focused=");
        sb.append(isFocused());
        sb.append(", active=");
        sb.append(isActive());
        sb.append(", hasParent=");
        sb.append(getParent() != null);
        sb.append(", hasChildren=");
        sb.append(getChildCount() > 0);
        sb.append(']');
        return sb.toString();
    }
}
