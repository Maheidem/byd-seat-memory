package android.support.design.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class BottomSheetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    private static final float HIDE_FRICTION = 0.1f;
    private static final float HIDE_THRESHOLD = 0.5f;
    public static final int PEEK_HEIGHT_AUTO = -1;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_SETTLING = 2;
    int mActivePointerId;
    private BottomSheetCallback mCallback;
    private final ViewDragHelper.Callback mDragCallback;
    boolean mHideable;
    private boolean mIgnoreEvents;
    private int mInitialY;
    private int mLastNestedScrollDy;
    int mMaxOffset;
    private float mMaximumVelocity;
    int mMinOffset;
    private boolean mNestedScrolled;
    WeakReference<View> mNestedScrollingChildRef;
    int mParentHeight;
    private int mPeekHeight;
    private boolean mPeekHeightAuto;
    private int mPeekHeightMin;
    private boolean mSkipCollapsed;
    int mState;
    boolean mTouchingScrollingChild;
    private VelocityTracker mVelocityTracker;
    ViewDragHelper mViewDragHelper;
    WeakReference<V> mViewRef;

    public static abstract class BottomSheetCallback {
        public abstract void onSlide(@NonNull View view, float f);

        public abstract void onStateChanged(@NonNull View view, int i);
    }

    protected static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() { // from class: android.support.design.widget.BottomSheetBehavior.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.support.v4.os.ParcelableCompatCreatorCallbacks
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.support.v4.os.ParcelableCompatCreatorCallbacks
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        });
        final int state;

        public SavedState(Parcel parcel) {
            this(parcel, (ClassLoader) null);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.state = parcel.readInt();
        }

        public SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.state = i;
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.state);
        }
    }

    private class SettleRunnable implements Runnable {
        private final int mTargetState;
        private final View mView;

        SettleRunnable(View view, int i) {
            this.mView = view;
            this.mTargetState = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (BottomSheetBehavior.this.mViewDragHelper == null || !BottomSheetBehavior.this.mViewDragHelper.continueSettling(true)) {
                BottomSheetBehavior.this.setStateInternal(this.mTargetState);
            } else {
                ViewCompat.postOnAnimation(this.mView, this);
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface State {
    }

    public BottomSheetBehavior() {
        this.mState = 4;
        this.mDragCallback = new ViewDragHelper.Callback() { // from class: android.support.design.widget.BottomSheetBehavior.2
            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View view, int i, int i2) {
                return view.getLeft();
            }

            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View view, int i, int i2) {
                return MathUtils.constrain(i, BottomSheetBehavior.this.mMinOffset, BottomSheetBehavior.this.mHideable ? BottomSheetBehavior.this.mParentHeight : BottomSheetBehavior.this.mMaxOffset);
            }

            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View view) {
                return (BottomSheetBehavior.this.mHideable ? BottomSheetBehavior.this.mParentHeight : BottomSheetBehavior.this.mMaxOffset) - BottomSheetBehavior.this.mMinOffset;
            }

            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i) {
                if (i == 1) {
                    BottomSheetBehavior.this.setStateInternal(1);
                }
            }

            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
                BottomSheetBehavior.this.dispatchOnSlide(i2);
            }

            /* JADX WARN: Removed duplicated region for block: B:4:0x0007  */
            @Override // android.support.v4.widget.ViewDragHelper.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public void onViewReleased(android.view.View r4, float r5, float r6) {
                /*
                    r3 = this;
                    r5 = 0
                    int r0 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
                    r1 = 4
                    r2 = 3
                    if (r0 >= 0) goto Ld
                L7:
                    android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                    int r5 = r5.mMinOffset
                    r1 = r2
                    goto L43
                Ld:
                    android.support.design.widget.BottomSheetBehavior r0 = android.support.design.widget.BottomSheetBehavior.this
                    boolean r0 = r0.mHideable
                    if (r0 == 0) goto L21
                    android.support.design.widget.BottomSheetBehavior r0 = android.support.design.widget.BottomSheetBehavior.this
                    boolean r0 = r0.shouldHide(r4, r6)
                    if (r0 == 0) goto L21
                    android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                    int r5 = r5.mParentHeight
                    r1 = 5
                    goto L43
                L21:
                    int r5 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
                    if (r5 != 0) goto L3f
                    int r5 = r4.getTop()
                    android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                    int r6 = r6.mMinOffset
                    int r6 = r5 - r6
                    int r6 = java.lang.Math.abs(r6)
                    android.support.design.widget.BottomSheetBehavior r0 = android.support.design.widget.BottomSheetBehavior.this
                    int r0 = r0.mMaxOffset
                    int r5 = r5 - r0
                    int r5 = java.lang.Math.abs(r5)
                    if (r6 >= r5) goto L3f
                    goto L7
                L3f:
                    android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                    int r5 = r5.mMaxOffset
                L43:
                    android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                    android.support.v4.widget.ViewDragHelper r6 = r6.mViewDragHelper
                    int r0 = r4.getLeft()
                    boolean r5 = r6.settleCapturedViewAt(r0, r5)
                    if (r5 == 0) goto L62
                    android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                    r6 = 2
                    r5.setStateInternal(r6)
                    android.support.design.widget.BottomSheetBehavior$SettleRunnable r5 = new android.support.design.widget.BottomSheetBehavior$SettleRunnable
                    android.support.design.widget.BottomSheetBehavior r3 = android.support.design.widget.BottomSheetBehavior.this
                    r5.<init>(r4, r1)
                    android.support.v4.view.ViewCompat.postOnAnimation(r4, r5)
                    return
                L62:
                    android.support.design.widget.BottomSheetBehavior r3 = android.support.design.widget.BottomSheetBehavior.this
                    r3.setStateInternal(r1)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.AnonymousClass2.onViewReleased(android.view.View, float, float):void");
            }

            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i) {
                View view2;
                if (BottomSheetBehavior.this.mState == 1 || BottomSheetBehavior.this.mTouchingScrollingChild) {
                    return false;
                }
                if (BottomSheetBehavior.this.mState == 3 && BottomSheetBehavior.this.mActivePointerId == i && (view2 = BottomSheetBehavior.this.mNestedScrollingChildRef.get()) != null && ViewCompat.canScrollVertically(view2, -1)) {
                    return false;
                }
                return BottomSheetBehavior.this.mViewRef != null && BottomSheetBehavior.this.mViewRef.get() == view;
            }
        };
    }

    public BottomSheetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mState = 4;
        this.mDragCallback = new ViewDragHelper.Callback() { // from class: android.support.design.widget.BottomSheetBehavior.2
            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(View view, int i, int i2) {
                return view.getLeft();
            }

            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public int clampViewPositionVertical(View view, int i, int i2) {
                return MathUtils.constrain(i, BottomSheetBehavior.this.mMinOffset, BottomSheetBehavior.this.mHideable ? BottomSheetBehavior.this.mParentHeight : BottomSheetBehavior.this.mMaxOffset);
            }

            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public int getViewVerticalDragRange(View view) {
                return (BottomSheetBehavior.this.mHideable ? BottomSheetBehavior.this.mParentHeight : BottomSheetBehavior.this.mMaxOffset) - BottomSheetBehavior.this.mMinOffset;
            }

            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public void onViewDragStateChanged(int i) {
                if (i == 1) {
                    BottomSheetBehavior.this.setStateInternal(1);
                }
            }

            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(View view, int i, int i2, int i3, int i4) {
                BottomSheetBehavior.this.dispatchOnSlide(i2);
            }

            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public void onViewReleased(View v, float v2, float v3) {
                /*
                    this = this;
                    r5 = 0
                    int r0 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
                    r1 = 4
                    r2 = 3
                    if (r0 >= 0) goto Ld
                L7:
                    android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                    int r5 = r5.mMinOffset
                    r1 = r2
                    goto L43
                Ld:
                    android.support.design.widget.BottomSheetBehavior r0 = android.support.design.widget.BottomSheetBehavior.this
                    boolean r0 = r0.mHideable
                    if (r0 == 0) goto L21
                    android.support.design.widget.BottomSheetBehavior r0 = android.support.design.widget.BottomSheetBehavior.this
                    boolean r0 = r0.shouldHide(r4, r6)
                    if (r0 == 0) goto L21
                    android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                    int r5 = r5.mParentHeight
                    r1 = 5
                    goto L43
                L21:
                    int r5 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
                    if (r5 != 0) goto L3f
                    int r5 = r4.getTop()
                    android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                    int r6 = r6.mMinOffset
                    int r6 = r5 - r6
                    int r6 = java.lang.Math.abs(r6)
                    android.support.design.widget.BottomSheetBehavior r0 = android.support.design.widget.BottomSheetBehavior.this
                    int r0 = r0.mMaxOffset
                    int r5 = r5 - r0
                    int r5 = java.lang.Math.abs(r5)
                    if (r6 >= r5) goto L3f
                    goto L7
                L3f:
                    android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                    int r5 = r5.mMaxOffset
                L43:
                    android.support.design.widget.BottomSheetBehavior r6 = android.support.design.widget.BottomSheetBehavior.this
                    android.support.v4.widget.ViewDragHelper r6 = r6.mViewDragHelper
                    int r0 = r4.getLeft()
                    boolean r5 = r6.settleCapturedViewAt(r0, r5)
                    if (r5 == 0) goto L62
                    android.support.design.widget.BottomSheetBehavior r5 = android.support.design.widget.BottomSheetBehavior.this
                    r6 = 2
                    r5.setStateInternal(r6)
                    android.support.design.widget.BottomSheetBehavior$SettleRunnable r5 = new android.support.design.widget.BottomSheetBehavior$SettleRunnable
                    android.support.design.widget.BottomSheetBehavior r3 = android.support.design.widget.BottomSheetBehavior.this
                    r5.<init>(r4, r1)
                    android.support.v4.view.ViewCompat.postOnAnimation(r4, r5)
                    return
                L62:
                    android.support.design.widget.BottomSheetBehavior r3 = android.support.design.widget.BottomSheetBehavior.this
                    r3.setStateInternal(r1)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.AnonymousClass2.onViewReleased(android.view.View, float, float):void");
            }

            @Override // android.support.v4.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(View view, int i) {
                View view2;
                if (BottomSheetBehavior.this.mState == 1 || BottomSheetBehavior.this.mTouchingScrollingChild) {
                    return false;
                }
                if (BottomSheetBehavior.this.mState == 3 && BottomSheetBehavior.this.mActivePointerId == i && (view2 = BottomSheetBehavior.this.mNestedScrollingChildRef.get()) != null && ViewCompat.canScrollVertically(view2, -1)) {
                    return false;
                }
                return BottomSheetBehavior.this.mViewRef != null && BottomSheetBehavior.this.mViewRef.get() == view;
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomSheetBehavior_Layout);
        TypedValue typedValuePeekValue = typedArrayObtainStyledAttributes.peekValue(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight);
        setPeekHeight((typedValuePeekValue == null || typedValuePeekValue.data != -1) ? typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.BottomSheetBehavior_Layout_behavior_peekHeight, -1) : typedValuePeekValue.data);
        setHideable(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_hideable, false));
        setSkipCollapsed(typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomSheetBehavior_Layout_behavior_skipCollapsed, false));
        typedArrayObtainStyledAttributes.recycle();
        this.mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
    }

    private View findScrollingChild(View view) {
        if (view instanceof NestedScrollingChild) {
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View viewFindScrollingChild = findScrollingChild(viewGroup.getChildAt(i));
            if (viewFindScrollingChild != null) {
                return viewFindScrollingChild;
            }
        }
        return null;
    }

    public static <V extends View> BottomSheetBehavior<V> from(V v) {
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (!(layoutParams instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        }
        CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
        if (behavior instanceof BottomSheetBehavior) {
            return (BottomSheetBehavior) behavior;
        }
        throw new IllegalArgumentException("The view is not associated with BottomSheetBehavior");
    }

    private float getYVelocity() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        return VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
    }

    private void reset() {
        this.mActivePointerId = -1;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    void dispatchOnSlide(int i) {
        BottomSheetCallback bottomSheetCallback;
        float f;
        int i2;
        int i3;
        V v = this.mViewRef.get();
        if (v == null || this.mCallback == null) {
            return;
        }
        if (i > this.mMaxOffset) {
            bottomSheetCallback = this.mCallback;
            f = this.mMaxOffset - i;
            i2 = this.mParentHeight;
            i3 = this.mMaxOffset;
        } else {
            bottomSheetCallback = this.mCallback;
            f = this.mMaxOffset - i;
            i2 = this.mMaxOffset;
            i3 = this.mMinOffset;
        }
        bottomSheetCallback.onSlide(v, f / (i2 - i3));
    }

    public final int getPeekHeight() {
        if (this.mPeekHeightAuto) {
            return -1;
        }
        return this.mPeekHeight;
    }

    @VisibleForTesting
    int getPeekHeightMin() {
        return this.mPeekHeightMin;
    }

    public boolean getSkipCollapsed() {
        return this.mSkipCollapsed;
    }

    public final int getState() {
        return this.mState;
    }

    public boolean isHideable() {
        return this.mHideable;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0067  */
    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onInterceptTouchEvent(android.support.design.widget.CoordinatorLayout r8, V r9, android.view.MotionEvent r10) {
        /*
            r7 = this;
            boolean r0 = r9.isShown()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto Lb
            r7.mIgnoreEvents = r2
            return r1
        Lb:
            int r0 = android.support.v4.view.MotionEventCompat.getActionMasked(r10)
            if (r0 != 0) goto L14
            r7.reset()
        L14:
            android.view.VelocityTracker r3 = r7.mVelocityTracker
            if (r3 != 0) goto L1e
            android.view.VelocityTracker r3 = android.view.VelocityTracker.obtain()
            r7.mVelocityTracker = r3
        L1e:
            android.view.VelocityTracker r3 = r7.mVelocityTracker
            r3.addMovement(r10)
            r3 = 3
            r4 = -1
            if (r0 == r3) goto L67
            switch(r0) {
                case 0: goto L2b;
                case 1: goto L67;
                default: goto L2a;
            }
        L2a:
            goto L72
        L2b:
            float r3 = r10.getX()
            int r3 = (int) r3
            float r5 = r10.getY()
            int r5 = (int) r5
            r7.mInitialY = r5
            java.lang.ref.WeakReference<android.view.View> r5 = r7.mNestedScrollingChildRef
            java.lang.Object r5 = r5.get()
            android.view.View r5 = (android.view.View) r5
            if (r5 == 0) goto L55
            int r6 = r7.mInitialY
            boolean r5 = r8.isPointInChildBounds(r5, r3, r6)
            if (r5 == 0) goto L55
            int r5 = r10.getActionIndex()
            int r5 = r10.getPointerId(r5)
            r7.mActivePointerId = r5
            r7.mTouchingScrollingChild = r2
        L55:
            int r5 = r7.mActivePointerId
            if (r5 != r4) goto L63
            int r4 = r7.mInitialY
            boolean r9 = r8.isPointInChildBounds(r9, r3, r4)
            if (r9 != 0) goto L63
            r9 = r2
            goto L64
        L63:
            r9 = r1
        L64:
            r7.mIgnoreEvents = r9
            goto L72
        L67:
            r7.mTouchingScrollingChild = r1
            r7.mActivePointerId = r4
            boolean r9 = r7.mIgnoreEvents
            if (r9 == 0) goto L72
            r7.mIgnoreEvents = r1
            return r1
        L72:
            boolean r9 = r7.mIgnoreEvents
            if (r9 != 0) goto L7f
            android.support.v4.widget.ViewDragHelper r9 = r7.mViewDragHelper
            boolean r9 = r9.shouldInterceptTouchEvent(r10)
            if (r9 == 0) goto L7f
            return r2
        L7f:
            java.lang.ref.WeakReference<android.view.View> r9 = r7.mNestedScrollingChildRef
            java.lang.Object r9 = r9.get()
            android.view.View r9 = (android.view.View) r9
            r3 = 2
            if (r0 != r3) goto Lbc
            if (r9 == 0) goto Lbc
            boolean r0 = r7.mIgnoreEvents
            if (r0 != 0) goto Lbc
            int r0 = r7.mState
            if (r0 == r2) goto Lbc
            float r0 = r10.getX()
            int r0 = (int) r0
            float r3 = r10.getY()
            int r3 = (int) r3
            boolean r8 = r8.isPointInChildBounds(r9, r0, r3)
            if (r8 != 0) goto Lbc
            int r8 = r7.mInitialY
            float r8 = (float) r8
            float r9 = r10.getY()
            float r8 = r8 - r9
            float r8 = java.lang.Math.abs(r8)
            android.support.v4.widget.ViewDragHelper r7 = r7.mViewDragHelper
            int r7 = r7.getTouchSlop()
            float r7 = (float) r7
            int r7 = (r8 > r7 ? 1 : (r8 == r7 ? 0 : -1))
            if (r7 <= 0) goto Lbc
            r1 = r2
        Lbc:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.onInterceptTouchEvent(android.support.design.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0092  */
    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onLayoutChild(android.support.design.widget.CoordinatorLayout r6, V r7, int r8) {
        /*
            r5 = this;
            boolean r0 = android.support.v4.view.ViewCompat.getFitsSystemWindows(r6)
            r1 = 1
            if (r0 == 0) goto L10
            boolean r0 = android.support.v4.view.ViewCompat.getFitsSystemWindows(r7)
            if (r0 != 0) goto L10
            android.support.v4.view.ViewCompat.setFitsSystemWindows(r7, r1)
        L10:
            int r0 = r7.getTop()
            r6.onLayoutChild(r7, r8)
            int r8 = r6.getHeight()
            r5.mParentHeight = r8
            boolean r8 = r5.mPeekHeightAuto
            if (r8 == 0) goto L43
            int r8 = r5.mPeekHeightMin
            if (r8 != 0) goto L31
            android.content.res.Resources r8 = r6.getResources()
            int r2 = android.support.design.R.dimen.design_bottom_sheet_peek_height_min
            int r8 = r8.getDimensionPixelSize(r2)
            r5.mPeekHeightMin = r8
        L31:
            int r8 = r5.mPeekHeightMin
            int r2 = r5.mParentHeight
            int r3 = r6.getWidth()
            int r3 = r3 * 9
            int r3 = r3 / 16
            int r2 = r2 - r3
            int r8 = java.lang.Math.max(r8, r2)
            goto L45
        L43:
            int r8 = r5.mPeekHeight
        L45:
            r2 = 0
            int r3 = r5.mParentHeight
            int r4 = r7.getHeight()
            int r3 = r3 - r4
            int r2 = java.lang.Math.max(r2, r3)
            r5.mMinOffset = r2
            int r2 = r5.mParentHeight
            int r2 = r2 - r8
            int r8 = r5.mMinOffset
            int r8 = java.lang.Math.max(r2, r8)
            r5.mMaxOffset = r8
            int r8 = r5.mState
            r2 = 3
            if (r8 != r2) goto L69
            int r8 = r5.mMinOffset
        L65:
            android.support.v4.view.ViewCompat.offsetTopAndBottom(r7, r8)
            goto L8e
        L69:
            boolean r8 = r5.mHideable
            if (r8 == 0) goto L75
            int r8 = r5.mState
            r2 = 5
            if (r8 != r2) goto L75
            int r8 = r5.mParentHeight
            goto L65
        L75:
            int r8 = r5.mState
            r2 = 4
            if (r8 != r2) goto L7d
            int r8 = r5.mMaxOffset
            goto L65
        L7d:
            int r8 = r5.mState
            if (r8 == r1) goto L86
            int r8 = r5.mState
            r2 = 2
            if (r8 != r2) goto L8e
        L86:
            int r8 = r7.getTop()
            int r0 = r0 - r8
            android.support.v4.view.ViewCompat.offsetTopAndBottom(r7, r0)
        L8e:
            android.support.v4.widget.ViewDragHelper r8 = r5.mViewDragHelper
            if (r8 != 0) goto L9a
            android.support.v4.widget.ViewDragHelper$Callback r8 = r5.mDragCallback
            android.support.v4.widget.ViewDragHelper r6 = android.support.v4.widget.ViewDragHelper.create(r6, r8)
            r5.mViewDragHelper = r6
        L9a:
            java.lang.ref.WeakReference r6 = new java.lang.ref.WeakReference
            r6.<init>(r7)
            r5.mViewRef = r6
            java.lang.ref.WeakReference r6 = new java.lang.ref.WeakReference
            android.view.View r7 = r5.findScrollingChild(r7)
            r6.<init>(r7)
            r5.mNestedScrollingChildRef = r6
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.onLayoutChild(android.support.design.widget.CoordinatorLayout, android.view.View, int):boolean");
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, V v, View view, float f, float f2) {
        if (view == this.mNestedScrollingChildRef.get()) {
            return this.mState != 3 || super.onNestedPreFling(coordinatorLayout, v, view, f, f2);
        }
        return false;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr) {
        int i3;
        if (view != this.mNestedScrollingChildRef.get()) {
            return;
        }
        int top = v.getTop();
        int i4 = top - i2;
        if (i2 > 0) {
            if (i4 < this.mMinOffset) {
                iArr[1] = top - this.mMinOffset;
                ViewCompat.offsetTopAndBottom(v, -iArr[1]);
                i3 = 3;
                setStateInternal(i3);
            } else {
                iArr[1] = i2;
                ViewCompat.offsetTopAndBottom(v, -i2);
                setStateInternal(1);
            }
        } else if (i2 < 0 && !ViewCompat.canScrollVertically(view, -1)) {
            if (i4 <= this.mMaxOffset || this.mHideable) {
                iArr[1] = i2;
                ViewCompat.offsetTopAndBottom(v, -i2);
                setStateInternal(1);
            } else {
                iArr[1] = top - this.mMaxOffset;
                ViewCompat.offsetTopAndBottom(v, -iArr[1]);
                i3 = 4;
                setStateInternal(i3);
            }
        }
        dispatchOnSlide(v.getTop());
        this.mLastNestedScrollDy = i2;
        this.mNestedScrolled = true;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, V v, Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(coordinatorLayout, v, savedState.getSuperState());
        this.mState = (savedState.state == 1 || savedState.state == 2) ? 4 : savedState.state;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, V v) {
        return new SavedState(super.onSaveInstanceState(coordinatorLayout, v), this.mState);
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i) {
        this.mLastNestedScrollDy = 0;
        this.mNestedScrolled = false;
        return (i & 2) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001f  */
    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onStopNestedScroll(android.support.design.widget.CoordinatorLayout r4, V r5, android.view.View r6) {
        /*
            r3 = this;
            int r4 = r5.getTop()
            int r0 = r3.mMinOffset
            r1 = 3
            if (r4 != r0) goto Ld
            r3.setStateInternal(r1)
            return
        Ld:
            java.lang.ref.WeakReference<android.view.View> r4 = r3.mNestedScrollingChildRef
            java.lang.Object r4 = r4.get()
            if (r6 != r4) goto L70
            boolean r4 = r3.mNestedScrolled
            if (r4 != 0) goto L1a
            return
        L1a:
            int r4 = r3.mLastNestedScrollDy
            r6 = 4
            if (r4 <= 0) goto L22
        L1f:
            int r4 = r3.mMinOffset
            goto L51
        L22:
            boolean r4 = r3.mHideable
            if (r4 == 0) goto L34
            float r4 = r3.getYVelocity()
            boolean r4 = r3.shouldHide(r5, r4)
            if (r4 == 0) goto L34
            int r4 = r3.mParentHeight
            r1 = 5
            goto L51
        L34:
            int r4 = r3.mLastNestedScrollDy
            if (r4 != 0) goto L4e
            int r4 = r5.getTop()
            int r0 = r3.mMinOffset
            int r0 = r4 - r0
            int r0 = java.lang.Math.abs(r0)
            int r2 = r3.mMaxOffset
            int r4 = r4 - r2
            int r4 = java.lang.Math.abs(r4)
            if (r0 >= r4) goto L4e
            goto L1f
        L4e:
            int r4 = r3.mMaxOffset
            r1 = r6
        L51:
            android.support.v4.widget.ViewDragHelper r6 = r3.mViewDragHelper
            int r0 = r5.getLeft()
            boolean r4 = r6.smoothSlideViewTo(r5, r0, r4)
            if (r4 == 0) goto L6a
            r4 = 2
            r3.setStateInternal(r4)
            android.support.design.widget.BottomSheetBehavior$SettleRunnable r4 = new android.support.design.widget.BottomSheetBehavior$SettleRunnable
            r4.<init>(r5, r1)
            android.support.v4.view.ViewCompat.postOnAnimation(r5, r4)
            goto L6d
        L6a:
            r3.setStateInternal(r1)
        L6d:
            r4 = 0
            r3.mNestedScrolled = r4
        L70:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.onStopNestedScroll(android.support.design.widget.CoordinatorLayout, android.view.View, android.view.View):void");
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        if (!v.isShown()) {
            return false;
        }
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        if (this.mState == 1 && actionMasked == 0) {
            return true;
        }
        this.mViewDragHelper.processTouchEvent(motionEvent);
        if (actionMasked == 0) {
            reset();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        if (actionMasked == 2 && !this.mIgnoreEvents && Math.abs(this.mInitialY - motionEvent.getY()) > this.mViewDragHelper.getTouchSlop()) {
            this.mViewDragHelper.captureChildView(v, motionEvent.getPointerId(motionEvent.getActionIndex()));
        }
        return !this.mIgnoreEvents;
    }

    public void setBottomSheetCallback(BottomSheetCallback bottomSheetCallback) {
        this.mCallback = bottomSheetCallback;
    }

    public void setHideable(boolean z) {
        this.mHideable = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void setPeekHeight(int r4) {
        /*
            r3 = this;
            r0 = 1
            r1 = 0
            r2 = -1
            if (r4 != r2) goto Lc
            boolean r4 = r3.mPeekHeightAuto
            if (r4 != 0) goto L15
            r3.mPeekHeightAuto = r0
            goto L24
        Lc:
            boolean r2 = r3.mPeekHeightAuto
            if (r2 != 0) goto L17
            int r2 = r3.mPeekHeight
            if (r2 == r4) goto L15
            goto L17
        L15:
            r0 = r1
            goto L24
        L17:
            r3.mPeekHeightAuto = r1
            int r1 = java.lang.Math.max(r1, r4)
            r3.mPeekHeight = r1
            int r1 = r3.mParentHeight
            int r1 = r1 - r4
            r3.mMaxOffset = r1
        L24:
            if (r0 == 0) goto L3c
            int r4 = r3.mState
            r0 = 4
            if (r4 != r0) goto L3c
            java.lang.ref.WeakReference<V extends android.view.View> r4 = r3.mViewRef
            if (r4 == 0) goto L3c
            java.lang.ref.WeakReference<V extends android.view.View> r3 = r3.mViewRef
            java.lang.Object r3 = r3.get()
            android.view.View r3 = (android.view.View) r3
            if (r3 == 0) goto L3c
            r3.requestLayout()
        L3c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.BottomSheetBehavior.setPeekHeight(int):void");
    }

    public void setSkipCollapsed(boolean z) {
        this.mSkipCollapsed = z;
    }

    public final void setState(final int i) {
        if (i == this.mState) {
            return;
        }
        if (this.mViewRef == null) {
            if (i == 4 || i == 3 || (this.mHideable && i == 5)) {
                this.mState = i;
                return;
            }
            return;
        }
        final V v = this.mViewRef.get();
        if (v == null) {
            return;
        }
        ViewParent parent = v.getParent();
        if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(v)) {
            v.post(new Runnable() { // from class: android.support.design.widget.BottomSheetBehavior.1
                @Override // java.lang.Runnable
                public void run() {
                    BottomSheetBehavior.this.startSettlingAnimation(v, i);
                }
            });
        } else {
            startSettlingAnimation(v, i);
        }
    }

    void setStateInternal(int i) {
        if (this.mState == i) {
            return;
        }
        this.mState = i;
        V v = this.mViewRef.get();
        if (v == null || this.mCallback == null) {
            return;
        }
        this.mCallback.onStateChanged(v, i);
    }

    boolean shouldHide(View view, float f) {
        if (this.mSkipCollapsed) {
            return true;
        }
        return view.getTop() >= this.mMaxOffset && Math.abs((((float) view.getTop()) + (f * HIDE_FRICTION)) - ((float) this.mMaxOffset)) / ((float) this.mPeekHeight) > HIDE_THRESHOLD;
    }

    void startSettlingAnimation(View view, int i) {
        int i2;
        if (i == 4) {
            i2 = this.mMaxOffset;
        } else if (i == 3) {
            i2 = this.mMinOffset;
        } else {
            if (!this.mHideable || i != 5) {
                throw new IllegalArgumentException("Illegal state argument: " + i);
            }
            i2 = this.mParentHeight;
        }
        setStateInternal(2);
        if (this.mViewDragHelper.smoothSlideViewTo(view, view.getLeft(), i2)) {
            ViewCompat.postOnAnimation(view, new SettleRunnable(view, i));
        }
    }
}
