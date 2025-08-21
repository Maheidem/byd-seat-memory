package android.support.v4.widget;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.widget.EdgeEffect;

@RequiresApi(21)
@TargetApi(21)
/* loaded from: classes.dex */
class EdgeEffectCompatLollipop {
    EdgeEffectCompatLollipop() {
    }

    public static boolean onPull(Object obj, float f, float f2) {
        ((EdgeEffect) obj).onPull(f, f2);
        return true;
    }
}
