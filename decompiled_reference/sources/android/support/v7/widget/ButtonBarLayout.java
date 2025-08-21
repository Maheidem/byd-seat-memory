package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.ConfigurationHelper;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class ButtonBarLayout extends LinearLayout {
    private static final int ALLOW_STACKING_MIN_HEIGHT_DP = 320;
    private static final int PEEK_BUTTON_DP = 16;
    private boolean mAllowStacking;
    private int mLastWidthSize;

    public ButtonBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLastWidthSize = -1;
        boolean z = ConfigurationHelper.getScreenHeightDp(getResources()) >= ALLOW_STACKING_MIN_HEIGHT_DP;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ButtonBarLayout);
        this.mAllowStacking = typedArrayObtainStyledAttributes.getBoolean(R.styleable.ButtonBarLayout_allowStacking, z);
        typedArrayObtainStyledAttributes.recycle();
    }

    private int getNextVisibleChildIndex(int i) {
        int childCount = getChildCount();
        while (i < childCount) {
            if (getChildAt(i).getVisibility() == 0) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private boolean isStacked() {
        return getOrientation() == 1;
    }

    private void setStacked(boolean z) {
        setOrientation(z ? 1 : 0);
        setGravity(z ? 5 : 80);
        View viewFindViewById = findViewById(R.id.spacer);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(z ? 8 : 4);
        }
        for (int childCount = getChildCount() - 2; childCount >= 0; childCount--) {
            bringChildToFront(getChildAt(childCount));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0051  */
    @Override // android.widget.LinearLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onMeasure(int r9, int r10) {
        /*
            r8 = this;
            int r0 = android.view.View.MeasureSpec.getSize(r9)
            boolean r1 = r8.mAllowStacking
            r2 = 0
            if (r1 == 0) goto L18
            int r1 = r8.mLastWidthSize
            if (r0 <= r1) goto L16
            boolean r1 = r8.isStacked()
            if (r1 == 0) goto L16
            r8.setStacked(r2)
        L16:
            r8.mLastWidthSize = r0
        L18:
            boolean r1 = r8.isStacked()
            r3 = 1
            if (r1 != 0) goto L2f
            int r1 = android.view.View.MeasureSpec.getMode(r9)
            r4 = 1073741824(0x40000000, float:2.0)
            if (r1 != r4) goto L2f
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r1)
            r4 = r3
            goto L31
        L2f:
            r1 = r9
            r4 = r2
        L31:
            super.onMeasure(r1, r10)
            boolean r1 = r8.mAllowStacking
            if (r1 == 0) goto L7a
            boolean r1 = r8.isStacked()
            if (r1 != 0) goto L7a
            int r1 = android.os.Build.VERSION.SDK_INT
            r5 = 11
            if (r1 < r5) goto L53
            int r0 = android.support.v4.view.ViewCompat.getMeasuredWidthAndState(r8)
            r1 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r0 = r0 & r1
            r1 = 16777216(0x1000000, float:2.3509887E-38)
            if (r0 != r1) goto L51
        L4f:
            r0 = r3
            goto L74
        L51:
            r0 = r2
            goto L74
        L53:
            int r1 = r8.getChildCount()
            r5 = r2
            r6 = r5
        L59:
            if (r5 >= r1) goto L67
            android.view.View r7 = r8.getChildAt(r5)
            int r7 = r7.getMeasuredWidth()
            int r6 = r6 + r7
            int r5 = r5 + 1
            goto L59
        L67:
            int r1 = r8.getPaddingLeft()
            int r6 = r6 + r1
            int r1 = r8.getPaddingRight()
            int r6 = r6 + r1
            if (r6 <= r0) goto L51
            goto L4f
        L74:
            if (r0 == 0) goto L7a
            r8.setStacked(r3)
            r4 = r3
        L7a:
            if (r4 == 0) goto L7f
            super.onMeasure(r9, r10)
        L7f:
            int r9 = r8.getNextVisibleChildIndex(r2)
            if (r9 < 0) goto Lcd
            android.view.View r10 = r8.getChildAt(r9)
            android.view.ViewGroup$LayoutParams r0 = r10.getLayoutParams()
            android.widget.LinearLayout$LayoutParams r0 = (android.widget.LinearLayout.LayoutParams) r0
            int r1 = r8.getPaddingTop()
            int r10 = r10.getMeasuredHeight()
            int r1 = r1 + r10
            int r10 = r0.topMargin
            int r1 = r1 + r10
            int r10 = r0.bottomMargin
            int r1 = r1 + r10
            int r2 = r2 + r1
            boolean r10 = r8.isStacked()
            if (r10 == 0) goto Lc8
            int r9 = r9 + r3
            int r9 = r8.getNextVisibleChildIndex(r9)
            if (r9 < 0) goto Lcd
            float r10 = (float) r2
            android.view.View r9 = r8.getChildAt(r9)
            int r9 = r9.getPaddingTop()
            float r9 = (float) r9
            r0 = 1098907648(0x41800000, float:16.0)
            android.content.res.Resources r1 = r8.getResources()
            android.util.DisplayMetrics r1 = r1.getDisplayMetrics()
            float r1 = r1.density
            float r0 = r0 * r1
            float r9 = r9 + r0
            float r10 = r10 + r9
            int r9 = (int) r10
            r2 = r9
            goto Lcd
        Lc8:
            int r9 = r8.getPaddingBottom()
            int r2 = r2 + r9
        Lcd:
            int r9 = android.support.v4.view.ViewCompat.getMinimumHeight(r8)
            if (r9 == r2) goto Ld6
            r8.setMinimumHeight(r2)
        Ld6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ButtonBarLayout.onMeasure(int, int):void");
    }

    public void setAllowStacking(boolean z) {
        if (this.mAllowStacking != z) {
            this.mAllowStacking = z;
            if (!this.mAllowStacking && getOrientation() == 1) {
                setStacked(false);
            }
            requestLayout();
        }
    }
}
