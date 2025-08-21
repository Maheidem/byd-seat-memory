package android.support.design.widget;

import android.annotation.TargetApi;
import android.graphics.Outline;
import android.support.annotation.RequiresApi;

@RequiresApi(21)
@TargetApi(21)
/* loaded from: classes.dex */
class CircularBorderDrawableLollipop extends CircularBorderDrawable {
    CircularBorderDrawableLollipop() {
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        copyBounds(this.mRect);
        outline.setOval(this.mRect);
    }
}
