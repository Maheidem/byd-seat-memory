package android.support.design.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.widget.ActivityChooserView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/* loaded from: classes.dex */
abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId;
    private Runnable mFlingRunnable;
    private boolean mIsBeingDragged;
    private int mLastMotionY;
    ScrollerCompat mScroller;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    private class FlingRunnable implements Runnable {
        private final V mLayout;
        private final CoordinatorLayout mParent;

        FlingRunnable(CoordinatorLayout coordinatorLayout, V v) {
            this.mParent = coordinatorLayout;
            this.mLayout = v;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mLayout == null || HeaderBehavior.this.mScroller == null) {
                return;
            }
            if (!HeaderBehavior.this.mScroller.computeScrollOffset()) {
                HeaderBehavior.this.onFlingFinished(this.mParent, this.mLayout);
            } else {
                HeaderBehavior.this.setHeaderTopBottomOffset(this.mParent, this.mLayout, HeaderBehavior.this.mScroller.getCurrY());
                ViewCompat.postOnAnimation(this.mLayout, this);
            }
        }
    }

    public HeaderBehavior() {
        this.mActivePointerId = -1;
        this.mTouchSlop = -1;
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mActivePointerId = -1;
        this.mTouchSlop = -1;
    }

    private void ensureVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    boolean canDragView(V v) {
        return false;
    }

    final boolean fling(CoordinatorLayout coordinatorLayout, V v, int i, int i2, float f) {
        if (this.mFlingRunnable != null) {
            v.removeCallbacks(this.mFlingRunnable);
            this.mFlingRunnable = null;
        }
        if (this.mScroller == null) {
            this.mScroller = ScrollerCompat.create(v.getContext());
        }
        this.mScroller.fling(0, getTopAndBottomOffset(), 0, Math.round(f), 0, 0, i, i2);
        if (!this.mScroller.computeScrollOffset()) {
            onFlingFinished(coordinatorLayout, v);
            return false;
        }
        this.mFlingRunnable = new FlingRunnable(coordinatorLayout, v);
        ViewCompat.postOnAnimation(v, this.mFlingRunnable);
        return true;
    }

    int getMaxDragOffset(V v) {
        return -v.getHeight();
    }

    int getScrollRangeForDragFling(V v) {
        return v.getHeight();
    }

    int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset();
    }

    void onFlingFinished(CoordinatorLayout coordinatorLayout, V v) {
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        int iFindPointerIndex;
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getAction() == 2 && this.mIsBeingDragged) {
            return true;
        }
        switch (MotionEventCompat.getActionMasked(motionEvent)) {
            case 0:
                this.mIsBeingDragged = false;
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                if (canDragView(v) && coordinatorLayout.isPointInChildBounds(v, x, y)) {
                    this.mLastMotionY = y;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    ensureVelocityTracker();
                    break;
                }
                break;
            case 1:
            case 3:
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                    break;
                }
                break;
            case 2:
                int i = this.mActivePointerId;
                if (i != -1 && (iFindPointerIndex = motionEvent.findPointerIndex(i)) != -1) {
                    int y2 = (int) motionEvent.getY(iFindPointerIndex);
                    if (Math.abs(y2 - this.mLastMotionY) > this.mTouchSlop) {
                        this.mIsBeingDragged = true;
                        this.mLastMotionY = y2;
                        break;
                    }
                }
                break;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(motionEvent);
        }
        return this.mIsBeingDragged;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b5  */
    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.support.design.widget.CoordinatorLayout r12, V r13, android.view.MotionEvent r14) {
        /*
            r11 = this;
            int r0 = r11.mTouchSlop
            if (r0 >= 0) goto L12
            android.content.Context r0 = r12.getContext()
            android.view.ViewConfiguration r0 = android.view.ViewConfiguration.get(r0)
            int r0 = r0.getScaledTouchSlop()
            r11.mTouchSlop = r0
        L12:
            int r0 = android.support.v4.view.MotionEventCompat.getActionMasked(r14)
            r1 = 1
            r2 = -1
            r3 = 0
            switch(r0) {
                case 0: goto L8e;
                case 1: goto L59;
                case 2: goto L1e;
                case 3: goto L7d;
                default: goto L1c;
            }
        L1c:
            goto Lb1
        L1e:
            int r0 = r11.mActivePointerId
            int r0 = r14.findPointerIndex(r0)
            if (r0 != r2) goto L27
            return r3
        L27:
            float r0 = r14.getY(r0)
            int r0 = (int) r0
            int r2 = r11.mLastMotionY
            int r2 = r2 - r0
            boolean r3 = r11.mIsBeingDragged
            if (r3 != 0) goto L46
            int r3 = java.lang.Math.abs(r2)
            int r4 = r11.mTouchSlop
            if (r3 <= r4) goto L46
            r11.mIsBeingDragged = r1
            if (r2 <= 0) goto L43
            int r3 = r11.mTouchSlop
            int r2 = r2 - r3
            goto L46
        L43:
            int r3 = r11.mTouchSlop
            int r2 = r2 + r3
        L46:
            r6 = r2
            boolean r2 = r11.mIsBeingDragged
            if (r2 == 0) goto Lb1
            r11.mLastMotionY = r0
            int r7 = r11.getMaxDragOffset(r13)
            r8 = 0
            r3 = r11
            r4 = r12
            r5 = r13
            r3.scroll(r4, r5, r6, r7, r8)
            goto Lb1
        L59:
            android.view.VelocityTracker r0 = r11.mVelocityTracker
            if (r0 == 0) goto L7d
            android.view.VelocityTracker r0 = r11.mVelocityTracker
            r0.addMovement(r14)
            android.view.VelocityTracker r0 = r11.mVelocityTracker
            r4 = 1000(0x3e8, float:1.401E-42)
            r0.computeCurrentVelocity(r4)
            android.view.VelocityTracker r0 = r11.mVelocityTracker
            int r4 = r11.mActivePointerId
            float r10 = android.support.v4.view.VelocityTrackerCompat.getYVelocity(r0, r4)
            int r0 = r11.getScrollRangeForDragFling(r13)
            int r8 = -r0
            r9 = 0
            r5 = r11
            r6 = r12
            r7 = r13
            r5.fling(r6, r7, r8, r9, r10)
        L7d:
            r11.mIsBeingDragged = r3
            r11.mActivePointerId = r2
            android.view.VelocityTracker r12 = r11.mVelocityTracker
            if (r12 == 0) goto Lb1
            android.view.VelocityTracker r12 = r11.mVelocityTracker
            r12.recycle()
            r12 = 0
            r11.mVelocityTracker = r12
            goto Lb1
        L8e:
            float r0 = r14.getX()
            int r0 = (int) r0
            float r2 = r14.getY()
            int r2 = (int) r2
            boolean r12 = r12.isPointInChildBounds(r13, r0, r2)
            if (r12 == 0) goto Lb0
            boolean r12 = r11.canDragView(r13)
            if (r12 == 0) goto Lb0
            r11.mLastMotionY = r2
            int r12 = r14.getPointerId(r3)
            r11.mActivePointerId = r12
            r11.ensureVelocityTracker()
            goto Lb1
        Lb0:
            return r3
        Lb1:
            android.view.VelocityTracker r12 = r11.mVelocityTracker
            if (r12 == 0) goto Lba
            android.view.VelocityTracker r11 = r11.mVelocityTracker
            r11.addMovement(r14)
        Lba:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.HeaderBehavior.onTouchEvent(android.support.design.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    final int scroll(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
        return setHeaderTopBottomOffset(coordinatorLayout, v, getTopBottomOffsetForScrollingSibling() - i, i2, i3);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int i) {
        return setHeaderTopBottomOffset(coordinatorLayout, v, i, Integer.MIN_VALUE, ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int i, int i2, int i3) {
        int iConstrain;
        int topAndBottomOffset = getTopAndBottomOffset();
        if (i2 == 0 || topAndBottomOffset < i2 || topAndBottomOffset > i3 || topAndBottomOffset == (iConstrain = MathUtils.constrain(i, i2, i3))) {
            return 0;
        }
        setTopAndBottomOffset(iConstrain);
        return topAndBottomOffset - iConstrain;
    }
}
