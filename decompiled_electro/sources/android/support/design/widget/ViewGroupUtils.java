package android.support.design.widget;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
class ViewGroupUtils {
    private static final ViewGroupUtilsImpl IMPL;

    private interface ViewGroupUtilsImpl {
        void offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect);
    }

    private static class ViewGroupUtilsImplBase implements ViewGroupUtilsImpl {
        ViewGroupUtilsImplBase() {
        }

        @Override // android.support.design.widget.ViewGroupUtils.ViewGroupUtilsImpl
        public void offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
            viewGroup.offsetDescendantRectToMyCoords(view, rect);
            rect.offset(view.getScrollX(), view.getScrollY());
        }
    }

    private static class ViewGroupUtilsImplHoneycomb implements ViewGroupUtilsImpl {
        ViewGroupUtilsImplHoneycomb() {
        }

        @Override // android.support.design.widget.ViewGroupUtils.ViewGroupUtilsImpl
        public void offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
            ViewGroupUtilsHoneycomb.offsetDescendantRect(viewGroup, view, rect);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        IMPL = Build.VERSION.SDK_INT >= 11 ? new ViewGroupUtilsImplHoneycomb() : new ViewGroupUtilsImplBase();
    }

    ViewGroupUtils() {
    }

    static void getDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
        rect.set(0, 0, view.getWidth(), view.getHeight());
        offsetDescendantRect(viewGroup, view, rect);
    }

    static void offsetDescendantRect(ViewGroup viewGroup, View view, Rect rect) {
        IMPL.offsetDescendantRect(viewGroup, view, rect);
    }
}
