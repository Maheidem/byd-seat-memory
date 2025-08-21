package android.support.v4.animation;

import android.os.Build;
import android.support.annotation.RestrictTo;
import android.view.View;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class AnimatorCompatHelper {
    private static final AnimatorProvider IMPL;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        IMPL = Build.VERSION.SDK_INT >= 12 ? new HoneycombMr1AnimatorCompatProvider() : new GingerbreadAnimatorCompatProvider();
    }

    private AnimatorCompatHelper() {
    }

    public static void clearInterpolator(View view) {
        IMPL.clearInterpolator(view);
    }

    public static ValueAnimatorCompat emptyValueAnimator() {
        return IMPL.emptyValueAnimator();
    }
}
