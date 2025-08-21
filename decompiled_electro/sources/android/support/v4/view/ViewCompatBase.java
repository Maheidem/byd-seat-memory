package android.support.v4.view;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import java.lang.reflect.Field;

@RequiresApi(9)
@TargetApi(9)
/* loaded from: classes.dex */
class ViewCompatBase {
    private static final String TAG = "ViewCompatBase";
    private static Field sMinHeightField;
    private static boolean sMinHeightFieldFetched;
    private static Field sMinWidthField;
    private static boolean sMinWidthFieldFetched;

    ViewCompatBase() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    static ColorStateList getBackgroundTintList(View view) {
        if (view instanceof TintableBackgroundView) {
            return ((TintableBackgroundView) view).getSupportBackgroundTintList();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static PorterDuff.Mode getBackgroundTintMode(View view) {
        if (view instanceof TintableBackgroundView) {
            return ((TintableBackgroundView) view).getSupportBackgroundTintMode();
        }
        return null;
    }

    static Display getDisplay(View view) {
        if (isAttachedToWindow(view)) {
            return ((WindowManager) view.getContext().getSystemService("window")).getDefaultDisplay();
        }
        return null;
    }

    static int getMinimumHeight(View view) {
        if (!sMinHeightFieldFetched) {
            try {
                sMinHeightField = View.class.getDeclaredField("mMinHeight");
                sMinHeightField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
            }
            sMinHeightFieldFetched = true;
        }
        if (sMinHeightField == null) {
            return 0;
        }
        try {
            return ((Integer) sMinHeightField.get(view)).intValue();
        } catch (Exception unused2) {
            return 0;
        }
    }

    static int getMinimumWidth(View view) {
        if (!sMinWidthFieldFetched) {
            try {
                sMinWidthField = View.class.getDeclaredField("mMinWidth");
                sMinWidthField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
            }
            sMinWidthFieldFetched = true;
        }
        if (sMinWidthField == null) {
            return 0;
        }
        try {
            return ((Integer) sMinWidthField.get(view)).intValue();
        } catch (Exception unused2) {
            return 0;
        }
    }

    static boolean isAttachedToWindow(View view) {
        return view.getWindowToken() != null;
    }

    static boolean isLaidOut(View view) {
        return view.getWidth() > 0 && view.getHeight() > 0;
    }

    static void offsetLeftAndRight(View view, int i) {
        int left = view.getLeft();
        view.offsetLeftAndRight(i);
        if (i != 0) {
            Object parent = view.getParent();
            if (!(parent instanceof View)) {
                view.invalidate();
            } else {
                int iAbs = Math.abs(i);
                ((View) parent).invalidate(left - iAbs, view.getTop(), left + view.getWidth() + iAbs, view.getBottom());
            }
        }
    }

    static void offsetTopAndBottom(View view, int i) {
        int top = view.getTop();
        view.offsetTopAndBottom(i);
        if (i != 0) {
            Object parent = view.getParent();
            if (!(parent instanceof View)) {
                view.invalidate();
            } else {
                int iAbs = Math.abs(i);
                ((View) parent).invalidate(view.getLeft(), top - iAbs, view.getRight(), top + view.getHeight() + iAbs);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static void setBackgroundTintList(View view, ColorStateList colorStateList) {
        if (view instanceof TintableBackgroundView) {
            ((TintableBackgroundView) view).setSupportBackgroundTintList(colorStateList);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    static void setBackgroundTintMode(View view, PorterDuff.Mode mode) {
        if (view instanceof TintableBackgroundView) {
            ((TintableBackgroundView) view).setSupportBackgroundTintMode(mode);
        }
    }
}
