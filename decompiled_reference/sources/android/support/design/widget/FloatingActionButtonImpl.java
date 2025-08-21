package android.support.design.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.ValueAnimatorCompat;
import android.support.v4.content.ContextCompat;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;

/* loaded from: classes.dex */
abstract class FloatingActionButtonImpl {
    static final int ANIM_STATE_HIDING = 1;
    static final int ANIM_STATE_NONE = 0;
    static final int ANIM_STATE_SHOWING = 2;
    static final long PRESSED_ANIM_DELAY = 100;
    static final long PRESSED_ANIM_DURATION = 100;
    static final int SHOW_HIDE_ANIM_DURATION = 200;
    final ValueAnimatorCompat.Creator mAnimatorCreator;
    CircularBorderDrawable mBorderDrawable;
    Drawable mContentBackground;
    float mElevation;
    private ViewTreeObserver.OnPreDrawListener mPreDrawListener;
    float mPressedTranslationZ;
    Drawable mRippleDrawable;
    final ShadowViewDelegate mShadowViewDelegate;
    Drawable mShapeDrawable;
    final VisibilityAwareImageButton mView;
    static final Interpolator ANIM_INTERPOLATOR = AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR;
    static final int[] PRESSED_ENABLED_STATE_SET = {R.attr.state_pressed, R.attr.state_enabled};
    static final int[] FOCUSED_ENABLED_STATE_SET = {R.attr.state_focused, R.attr.state_enabled};
    static final int[] ENABLED_STATE_SET = {R.attr.state_enabled};
    static final int[] EMPTY_STATE_SET = new int[0];
    int mAnimState = 0;
    private final Rect mTmpRect = new Rect();

    interface InternalVisibilityChangedListener {
        void onHidden();

        void onShown();
    }

    FloatingActionButtonImpl(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate, ValueAnimatorCompat.Creator creator) {
        this.mView = visibilityAwareImageButton;
        this.mShadowViewDelegate = shadowViewDelegate;
        this.mAnimatorCreator = creator;
    }

    private void ensurePreDrawListener() {
        if (this.mPreDrawListener == null) {
            this.mPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: android.support.design.widget.FloatingActionButtonImpl.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    FloatingActionButtonImpl.this.onPreDraw();
                    return true;
                }
            };
        }
    }

    CircularBorderDrawable createBorderDrawable(int i, ColorStateList colorStateList) {
        Context context = this.mView.getContext();
        CircularBorderDrawable circularBorderDrawableNewCircularDrawable = newCircularDrawable();
        circularBorderDrawableNewCircularDrawable.setGradientColors(ContextCompat.getColor(context, android.support.design.R.color.design_fab_stroke_top_outer_color), ContextCompat.getColor(context, android.support.design.R.color.design_fab_stroke_top_inner_color), ContextCompat.getColor(context, android.support.design.R.color.design_fab_stroke_end_inner_color), ContextCompat.getColor(context, android.support.design.R.color.design_fab_stroke_end_outer_color));
        circularBorderDrawableNewCircularDrawable.setBorderWidth(i);
        circularBorderDrawableNewCircularDrawable.setBorderTint(colorStateList);
        return circularBorderDrawableNewCircularDrawable;
    }

    GradientDrawable createShapeDrawable() {
        GradientDrawable gradientDrawableNewGradientDrawableForShape = newGradientDrawableForShape();
        gradientDrawableNewGradientDrawableForShape.setShape(1);
        gradientDrawableNewGradientDrawableForShape.setColor(-1);
        return gradientDrawableNewGradientDrawableForShape;
    }

    final Drawable getContentBackground() {
        return this.mContentBackground;
    }

    abstract float getElevation();

    abstract void getPadding(Rect rect);

    abstract void hide(@Nullable InternalVisibilityChangedListener internalVisibilityChangedListener, boolean z);

    boolean isOrWillBeHidden() {
        return this.mView.getVisibility() == 0 ? this.mAnimState == 1 : this.mAnimState != 2;
    }

    boolean isOrWillBeShown() {
        return this.mView.getVisibility() != 0 ? this.mAnimState == 2 : this.mAnimState != 1;
    }

    abstract void jumpDrawableToCurrentState();

    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawable();
    }

    GradientDrawable newGradientDrawableForShape() {
        return new GradientDrawable();
    }

    void onAttachedToWindow() {
        if (requirePreDrawListener()) {
            ensurePreDrawListener();
            this.mView.getViewTreeObserver().addOnPreDrawListener(this.mPreDrawListener);
        }
    }

    abstract void onCompatShadowChanged();

    void onDetachedFromWindow() {
        if (this.mPreDrawListener != null) {
            this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mPreDrawListener);
            this.mPreDrawListener = null;
        }
    }

    abstract void onDrawableStateChanged(int[] iArr);

    abstract void onElevationsChanged(float f, float f2);

    void onPaddingUpdated(Rect rect) {
    }

    void onPreDraw() {
    }

    boolean requirePreDrawListener() {
        return false;
    }

    abstract void setBackgroundDrawable(ColorStateList colorStateList, PorterDuff.Mode mode, int i, int i2);

    abstract void setBackgroundTintList(ColorStateList colorStateList);

    abstract void setBackgroundTintMode(PorterDuff.Mode mode);

    final void setElevation(float f) {
        if (this.mElevation != f) {
            this.mElevation = f;
            onElevationsChanged(f, this.mPressedTranslationZ);
        }
    }

    final void setPressedTranslationZ(float f) {
        if (this.mPressedTranslationZ != f) {
            this.mPressedTranslationZ = f;
            onElevationsChanged(this.mElevation, f);
        }
    }

    abstract void setRippleColor(int i);

    abstract void show(@Nullable InternalVisibilityChangedListener internalVisibilityChangedListener, boolean z);

    final void updatePadding() {
        Rect rect = this.mTmpRect;
        getPadding(rect);
        onPaddingUpdated(rect);
        this.mShadowViewDelegate.setShadowPadding(rect.left, rect.top, rect.right, rect.bottom);
    }
}
