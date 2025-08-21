package android.support.v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityManager;
import java.util.List;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class AccessibilityManagerCompatIcs {

    interface AccessibilityStateChangeListenerBridge {
        void onAccessibilityStateChanged(boolean z);
    }

    public static class AccessibilityStateChangeListenerWrapper implements AccessibilityManager.AccessibilityStateChangeListener {
        Object mListener;
        AccessibilityStateChangeListenerBridge mListenerBridge;

        public AccessibilityStateChangeListenerWrapper(Object obj, AccessibilityStateChangeListenerBridge accessibilityStateChangeListenerBridge) {
            this.mListener = obj;
            this.mListenerBridge = accessibilityStateChangeListenerBridge;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            AccessibilityStateChangeListenerWrapper accessibilityStateChangeListenerWrapper = (AccessibilityStateChangeListenerWrapper) obj;
            return this.mListener == null ? accessibilityStateChangeListenerWrapper.mListener == null : this.mListener.equals(accessibilityStateChangeListenerWrapper.mListener);
        }

        public int hashCode() {
            if (this.mListener == null) {
                return 0;
            }
            return this.mListener.hashCode();
        }

        @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
        public void onAccessibilityStateChanged(boolean z) {
            this.mListenerBridge.onAccessibilityStateChanged(z);
        }
    }

    AccessibilityManagerCompatIcs() {
    }

    public static boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerWrapper accessibilityStateChangeListenerWrapper) {
        return accessibilityManager.addAccessibilityStateChangeListener(accessibilityStateChangeListenerWrapper);
    }

    public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager accessibilityManager, int i) {
        return accessibilityManager.getEnabledAccessibilityServiceList(i);
    }

    public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager accessibilityManager) {
        return accessibilityManager.getInstalledAccessibilityServiceList();
    }

    public static boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager) {
        return accessibilityManager.isTouchExplorationEnabled();
    }

    public static boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerWrapper accessibilityStateChangeListenerWrapper) {
        return accessibilityManager.removeAccessibilityStateChangeListener(accessibilityStateChangeListenerWrapper);
    }
}
