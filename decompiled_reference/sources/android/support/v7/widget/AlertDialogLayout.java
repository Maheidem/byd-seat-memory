package android.support.v7.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class AlertDialogLayout extends LinearLayoutCompat {
    public AlertDialogLayout(@Nullable Context context) {
        super(context);
    }

    public AlertDialogLayout(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void forceUniformWidth(int i, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
        for (int i3 = 0; i3 < i; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) childAt.getLayoutParams();
                if (layoutParams.width == -1) {
                    int i4 = layoutParams.height;
                    layoutParams.height = childAt.getMeasuredHeight();
                    measureChildWithMargins(childAt, iMakeMeasureSpec, 0, i2, 0);
                    layoutParams.height = i4;
                }
            }
        }
    }

    private static int resolveMinimumHeight(View view) {
        int minimumHeight = ViewCompat.getMinimumHeight(view);
        if (minimumHeight > 0) {
            return minimumHeight;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (viewGroup.getChildCount() == 1) {
                return resolveMinimumHeight(viewGroup.getChildAt(0));
            }
        }
        return 0;
    }

    private void setChildFrame(View view, int i, int i2, int i3, int i4) {
        view.layout(i, i2, i3 + i, i4 + i2);
    }

    private boolean tryOnMeasure(int i, int i2) {
        int iCombineMeasuredStates;
        int iResolveMinimumHeight;
        int measuredHeight;
        int measuredHeight2;
        int childCount = getChildCount();
        View view = null;
        View view2 = null;
        View view3 = null;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                int id = childAt.getId();
                if (id == R.id.topPanel) {
                    view = childAt;
                } else if (id == R.id.buttonPanel) {
                    view2 = childAt;
                } else {
                    if ((id != R.id.contentPanel && id != R.id.customPanel) || view3 != null) {
                        return false;
                    }
                    view3 = childAt;
                }
            }
        }
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int mode2 = View.MeasureSpec.getMode(i);
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (view != null) {
            view.measure(i, 0);
            paddingTop += view.getMeasuredHeight();
            iCombineMeasuredStates = ViewCompat.combineMeasuredStates(0, ViewCompat.getMeasuredState(view));
        } else {
            iCombineMeasuredStates = 0;
        }
        if (view2 != null) {
            view2.measure(i, 0);
            iResolveMinimumHeight = resolveMinimumHeight(view2);
            measuredHeight = view2.getMeasuredHeight() - iResolveMinimumHeight;
            paddingTop += iResolveMinimumHeight;
            iCombineMeasuredStates = ViewCompat.combineMeasuredStates(iCombineMeasuredStates, ViewCompat.getMeasuredState(view2));
        } else {
            iResolveMinimumHeight = 0;
            measuredHeight = 0;
        }
        if (view3 != null) {
            view3.measure(i, mode == 0 ? 0 : View.MeasureSpec.makeMeasureSpec(Math.max(0, size - paddingTop), mode));
            measuredHeight2 = view3.getMeasuredHeight();
            paddingTop += measuredHeight2;
            iCombineMeasuredStates = ViewCompat.combineMeasuredStates(iCombineMeasuredStates, ViewCompat.getMeasuredState(view3));
        } else {
            measuredHeight2 = 0;
        }
        int i4 = size - paddingTop;
        if (view2 != null) {
            int i5 = paddingTop - iResolveMinimumHeight;
            int iMin = Math.min(i4, measuredHeight);
            if (iMin > 0) {
                i4 -= iMin;
                iResolveMinimumHeight += iMin;
            }
            view2.measure(i, View.MeasureSpec.makeMeasureSpec(iResolveMinimumHeight, 1073741824));
            paddingTop = i5 + view2.getMeasuredHeight();
            iCombineMeasuredStates = ViewCompat.combineMeasuredStates(iCombineMeasuredStates, ViewCompat.getMeasuredState(view2));
        }
        if (view3 != null && i4 > 0) {
            view3.measure(i, View.MeasureSpec.makeMeasureSpec(measuredHeight2 + i4, mode));
            paddingTop = (paddingTop - measuredHeight2) + view3.getMeasuredHeight();
            iCombineMeasuredStates = ViewCompat.combineMeasuredStates(iCombineMeasuredStates, ViewCompat.getMeasuredState(view3));
        }
        int iMax = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt2 = getChildAt(i6);
            if (childAt2.getVisibility() != 8) {
                iMax = Math.max(iMax, childAt2.getMeasuredWidth());
            }
        }
        setMeasuredDimension(ViewCompat.resolveSizeAndState(iMax + getPaddingLeft() + getPaddingRight(), i, iCombineMeasuredStates), ViewCompat.resolveSizeAndState(paddingTop, i2, 0));
        if (mode2 == 1073741824) {
            return true;
        }
        forceUniformWidth(childCount, i2);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00a5  */
    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onLayout(boolean r18, int r19, int r20, int r21, int r22) {
        /*
            r17 = this;
            r6 = r17
            int r7 = r17.getPaddingLeft()
            int r2 = r21 - r19
            int r3 = r17.getPaddingRight()
            int r8 = r2 - r3
            int r2 = r2 - r7
            int r3 = r17.getPaddingRight()
            int r9 = r2 - r3
            int r2 = r17.getMeasuredHeight()
            int r10 = r17.getChildCount()
            int r3 = r17.getGravity()
            r4 = r3 & 112(0x70, float:1.57E-43)
            r5 = 8388615(0x800007, float:1.1754953E-38)
            r11 = r3 & r5
            r3 = 16
            if (r4 == r3) goto L40
            r3 = 80
            if (r4 == r3) goto L35
            int r0 = r17.getPaddingTop()
            goto L4a
        L35:
            int r3 = r17.getPaddingTop()
            int r3 = r3 + r22
            int r3 = r3 - r20
            int r0 = r3 - r2
            goto L4a
        L40:
            int r3 = r17.getPaddingTop()
            int r0 = r22 - r20
            int r0 = r0 - r2
            int r0 = r0 / 2
            int r0 = r0 + r3
        L4a:
            android.graphics.drawable.Drawable r1 = r17.getDividerDrawable()
            r2 = 0
            if (r1 != 0) goto L53
            r12 = r2
            goto L58
        L53:
            int r1 = r1.getIntrinsicHeight()
            r12 = r1
        L58:
            r13 = r2
        L59:
            if (r13 >= r10) goto Lbb
            android.view.View r1 = r6.getChildAt(r13)
            if (r1 == 0) goto Lb8
            int r2 = r1.getVisibility()
            r3 = 8
            if (r2 == r3) goto Lb8
            int r4 = r1.getMeasuredWidth()
            int r14 = r1.getMeasuredHeight()
            android.view.ViewGroup$LayoutParams r2 = r1.getLayoutParams()
            r15 = r2
            android.support.v7.widget.LinearLayoutCompat$LayoutParams r15 = (android.support.v7.widget.LinearLayoutCompat.LayoutParams) r15
            int r2 = r15.gravity
            if (r2 >= 0) goto L7d
            r2 = r11
        L7d:
            int r3 = android.support.v4.view.ViewCompat.getLayoutDirection(r17)
            int r2 = android.support.v4.view.GravityCompat.getAbsoluteGravity(r2, r3)
            r2 = r2 & 7
            r3 = 1
            if (r2 == r3) goto L94
            r3 = 5
            if (r2 == r3) goto L91
            int r2 = r15.leftMargin
            int r2 = r2 + r7
            goto L9f
        L91:
            int r2 = r8 - r4
            goto L9c
        L94:
            int r2 = r9 - r4
            int r2 = r2 / 2
            int r2 = r2 + r7
            int r3 = r15.leftMargin
            int r2 = r2 + r3
        L9c:
            int r3 = r15.rightMargin
            int r2 = r2 - r3
        L9f:
            boolean r3 = r6.hasDividerBeforeChildAt(r13)
            if (r3 == 0) goto La6
            int r0 = r0 + r12
        La6:
            int r3 = r15.topMargin
            int r16 = r0 + r3
            r0 = r6
            r3 = r16
            r5 = r14
            r0.setChildFrame(r1, r2, r3, r4, r5)
            int r0 = r15.bottomMargin
            int r14 = r14 + r0
            int r16 = r16 + r14
            r0 = r16
        Lb8:
            int r13 = r13 + 1
            goto L59
        Lbb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AlertDialogLayout.onLayout(boolean, int, int, int, int):void");
    }

    @Override // android.support.v7.widget.LinearLayoutCompat, android.view.View
    protected void onMeasure(int i, int i2) {
        if (tryOnMeasure(i, i2)) {
            return;
        }
        super.onMeasure(i, i2);
    }
}
