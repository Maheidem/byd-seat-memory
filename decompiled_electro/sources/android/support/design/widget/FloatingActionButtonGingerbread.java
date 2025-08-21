package android.support.design.widget;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.design.widget.AnimationUtils;
import android.support.design.widget.FloatingActionButtonImpl;
import android.support.design.widget.ValueAnimatorCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.animation.Animation;

/* loaded from: classes.dex */
class FloatingActionButtonGingerbread extends FloatingActionButtonImpl {
    ShadowDrawableWrapper mShadowDrawable;
    private final StateListAnimator mStateListAnimator;

    private class DisabledElevationAnimation extends ShadowAnimatorImpl {
        DisabledElevationAnimation() {
            super();
        }

        @Override // android.support.design.widget.FloatingActionButtonGingerbread.ShadowAnimatorImpl
        protected float getTargetShadowSize() {
            return 0.0f;
        }
    }

    private class ElevateToTranslationZAnimation extends ShadowAnimatorImpl {
        ElevateToTranslationZAnimation() {
            super();
        }

        @Override // android.support.design.widget.FloatingActionButtonGingerbread.ShadowAnimatorImpl
        protected float getTargetShadowSize() {
            return FloatingActionButtonGingerbread.this.mElevation + FloatingActionButtonGingerbread.this.mPressedTranslationZ;
        }
    }

    private class ResetElevationAnimation extends ShadowAnimatorImpl {
        ResetElevationAnimation() {
            super();
        }

        @Override // android.support.design.widget.FloatingActionButtonGingerbread.ShadowAnimatorImpl
        protected float getTargetShadowSize() {
            return FloatingActionButtonGingerbread.this.mElevation;
        }
    }

    private abstract class ShadowAnimatorImpl extends ValueAnimatorCompat.AnimatorListenerAdapter implements ValueAnimatorCompat.AnimatorUpdateListener {
        private float mShadowSizeEnd;
        private float mShadowSizeStart;
        private boolean mValidValues;

        private ShadowAnimatorImpl() {
        }

        protected abstract float getTargetShadowSize();

        @Override // android.support.design.widget.ValueAnimatorCompat.AnimatorListenerAdapter, android.support.design.widget.ValueAnimatorCompat.AnimatorListener
        public void onAnimationEnd(ValueAnimatorCompat valueAnimatorCompat) {
            FloatingActionButtonGingerbread.this.mShadowDrawable.setShadowSize(this.mShadowSizeEnd);
            this.mValidValues = false;
        }

        @Override // android.support.design.widget.ValueAnimatorCompat.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimatorCompat valueAnimatorCompat) {
            if (!this.mValidValues) {
                this.mShadowSizeStart = FloatingActionButtonGingerbread.this.mShadowDrawable.getShadowSize();
                this.mShadowSizeEnd = getTargetShadowSize();
                this.mValidValues = true;
            }
            FloatingActionButtonGingerbread.this.mShadowDrawable.setShadowSize(this.mShadowSizeStart + ((this.mShadowSizeEnd - this.mShadowSizeStart) * valueAnimatorCompat.getAnimatedFraction()));
        }
    }

    FloatingActionButtonGingerbread(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate, ValueAnimatorCompat.Creator creator) {
        super(visibilityAwareImageButton, shadowViewDelegate, creator);
        this.mStateListAnimator = new StateListAnimator();
        this.mStateListAnimator.addState(PRESSED_ENABLED_STATE_SET, createAnimator(new ElevateToTranslationZAnimation()));
        this.mStateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, createAnimator(new ElevateToTranslationZAnimation()));
        this.mStateListAnimator.addState(ENABLED_STATE_SET, createAnimator(new ResetElevationAnimation()));
        this.mStateListAnimator.addState(EMPTY_STATE_SET, createAnimator(new DisabledElevationAnimation()));
    }

    private ValueAnimatorCompat createAnimator(@NonNull ShadowAnimatorImpl shadowAnimatorImpl) {
        ValueAnimatorCompat valueAnimatorCompatCreateAnimator = this.mAnimatorCreator.createAnimator();
        valueAnimatorCompatCreateAnimator.setInterpolator(ANIM_INTERPOLATOR);
        valueAnimatorCompatCreateAnimator.setDuration(100L);
        valueAnimatorCompatCreateAnimator.addListener(shadowAnimatorImpl);
        valueAnimatorCompatCreateAnimator.addUpdateListener(shadowAnimatorImpl);
        valueAnimatorCompatCreateAnimator.setFloatValues(0.0f, 1.0f);
        return valueAnimatorCompatCreateAnimator;
    }

    private static ColorStateList createColorStateList(int i) {
        return new ColorStateList(new int[][]{FOCUSED_ENABLED_STATE_SET, PRESSED_ENABLED_STATE_SET, new int[0]}, new int[]{i, i, 0});
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    float getElevation() {
        return this.mElevation;
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void getPadding(Rect rect) {
        this.mShadowDrawable.getPadding(rect);
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void hide(@Nullable final FloatingActionButtonImpl.InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean z) throws Resources.NotFoundException {
        if (isOrWillBeHidden()) {
            return;
        }
        this.mAnimState = 1;
        Animation animationLoadAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.design_fab_out);
        animationLoadAnimation.setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR);
        animationLoadAnimation.setDuration(200L);
        animationLoadAnimation.setAnimationListener(new AnimationUtils.AnimationListenerAdapter() { // from class: android.support.design.widget.FloatingActionButtonGingerbread.1
            @Override // android.support.design.widget.AnimationUtils.AnimationListenerAdapter, android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                FloatingActionButtonGingerbread.this.mAnimState = 0;
                FloatingActionButtonGingerbread.this.mView.internalSetVisibility(z ? 8 : 4, z);
                if (internalVisibilityChangedListener != null) {
                    internalVisibilityChangedListener.onHidden();
                }
            }
        });
        this.mView.startAnimation(animationLoadAnimation);
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void jumpDrawableToCurrentState() {
        this.mStateListAnimator.jumpToCurrentState();
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void onCompatShadowChanged() {
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void onDrawableStateChanged(int[] iArr) {
        this.mStateListAnimator.setState(iArr);
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void onElevationsChanged(float f, float f2) {
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setShadowSize(f, this.mPressedTranslationZ + f);
            updatePadding();
        }
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void setBackgroundDrawable(ColorStateList colorStateList, PorterDuff.Mode mode, int i, int i2) {
        Drawable[] drawableArr;
        this.mShapeDrawable = DrawableCompat.wrap(createShapeDrawable());
        DrawableCompat.setTintList(this.mShapeDrawable, colorStateList);
        if (mode != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, mode);
        }
        this.mRippleDrawable = DrawableCompat.wrap(createShapeDrawable());
        DrawableCompat.setTintList(this.mRippleDrawable, createColorStateList(i));
        if (i2 > 0) {
            this.mBorderDrawable = createBorderDrawable(i2, colorStateList);
            drawableArr = new Drawable[]{this.mBorderDrawable, this.mShapeDrawable, this.mRippleDrawable};
        } else {
            this.mBorderDrawable = null;
            drawableArr = new Drawable[]{this.mShapeDrawable, this.mRippleDrawable};
        }
        this.mContentBackground = new LayerDrawable(drawableArr);
        this.mShadowDrawable = new ShadowDrawableWrapper(this.mView.getContext(), this.mContentBackground, this.mShadowViewDelegate.getRadius(), this.mElevation, this.mElevation + this.mPressedTranslationZ);
        this.mShadowDrawable.setAddPaddingForCorners(false);
        this.mShadowViewDelegate.setBackgroundDrawable(this.mShadowDrawable);
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintList(this.mShapeDrawable, colorStateList);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setBorderTint(colorStateList);
        }
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void setBackgroundTintMode(PorterDuff.Mode mode) {
        if (this.mShapeDrawable != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, mode);
        }
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void setRippleColor(int i) {
        if (this.mRippleDrawable != null) {
            DrawableCompat.setTintList(this.mRippleDrawable, createColorStateList(i));
        }
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void show(@Nullable final FloatingActionButtonImpl.InternalVisibilityChangedListener internalVisibilityChangedListener, boolean z) throws Resources.NotFoundException {
        if (isOrWillBeShown()) {
            return;
        }
        this.mAnimState = 2;
        this.mView.internalSetVisibility(0, z);
        Animation animationLoadAnimation = android.view.animation.AnimationUtils.loadAnimation(this.mView.getContext(), R.anim.design_fab_in);
        animationLoadAnimation.setDuration(200L);
        animationLoadAnimation.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        animationLoadAnimation.setAnimationListener(new AnimationUtils.AnimationListenerAdapter() { // from class: android.support.design.widget.FloatingActionButtonGingerbread.2
            @Override // android.support.design.widget.AnimationUtils.AnimationListenerAdapter, android.view.animation.Animation.AnimationListener
            public void onAnimationEnd(Animation animation) {
                FloatingActionButtonGingerbread.this.mAnimState = 0;
                if (internalVisibilityChangedListener != null) {
                    internalVisibilityChangedListener.onShown();
                }
            }
        });
        this.mView.startAnimation(animationLoadAnimation);
    }
}
