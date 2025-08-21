package android.support.design.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.ValueAnimatorCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Property;
import android.view.View;

@RequiresApi(21)
@TargetApi(21)
/* loaded from: classes.dex */
class FloatingActionButtonLollipop extends FloatingActionButtonIcs {
    private InsetDrawable mInsetDrawable;

    static class AlwaysStatefulGradientDrawable extends GradientDrawable {
        AlwaysStatefulGradientDrawable() {
        }

        @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
        public boolean isStateful() {
            return true;
        }
    }

    FloatingActionButtonLollipop(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate, ValueAnimatorCompat.Creator creator) {
        super(visibilityAwareImageButton, shadowViewDelegate, creator);
    }

    @Override // android.support.design.widget.FloatingActionButtonGingerbread, android.support.design.widget.FloatingActionButtonImpl
    public float getElevation() {
        return this.mView.getElevation();
    }

    @Override // android.support.design.widget.FloatingActionButtonGingerbread, android.support.design.widget.FloatingActionButtonImpl
    void getPadding(Rect rect) {
        if (!this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            rect.set(0, 0, 0, 0);
            return;
        }
        float radius = this.mShadowViewDelegate.getRadius();
        float elevation = getElevation() + this.mPressedTranslationZ;
        int iCeil = (int) Math.ceil(ShadowDrawableWrapper.calculateHorizontalPadding(elevation, radius, false));
        int iCeil2 = (int) Math.ceil(ShadowDrawableWrapper.calculateVerticalPadding(elevation, radius, false));
        rect.set(iCeil, iCeil2, iCeil, iCeil2);
    }

    @Override // android.support.design.widget.FloatingActionButtonGingerbread, android.support.design.widget.FloatingActionButtonImpl
    void jumpDrawableToCurrentState() {
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawableLollipop();
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    GradientDrawable newGradientDrawableForShape() {
        return new AlwaysStatefulGradientDrawable();
    }

    @Override // android.support.design.widget.FloatingActionButtonGingerbread, android.support.design.widget.FloatingActionButtonImpl
    void onCompatShadowChanged() {
        updatePadding();
    }

    @Override // android.support.design.widget.FloatingActionButtonGingerbread, android.support.design.widget.FloatingActionButtonImpl
    void onDrawableStateChanged(int[] iArr) {
    }

    @Override // android.support.design.widget.FloatingActionButtonGingerbread, android.support.design.widget.FloatingActionButtonImpl
    void onElevationsChanged(float f, float f2) {
        if (Build.VERSION.SDK_INT == 21) {
            if (this.mView.isEnabled()) {
                this.mView.setElevation(f);
                if (this.mView.isFocused() || this.mView.isPressed()) {
                    this.mView.setTranslationZ(f2);
                }
            } else {
                this.mView.setElevation(0.0f);
            }
            this.mView.setTranslationZ(0.0f);
        } else {
            android.animation.StateListAnimator stateListAnimator = new android.animation.StateListAnimator();
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.play(ObjectAnimator.ofFloat(this.mView, "elevation", f).setDuration(0L)).with(ObjectAnimator.ofFloat(this.mView, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, f2).setDuration(100L));
            animatorSet.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, animatorSet);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.play(ObjectAnimator.ofFloat(this.mView, "elevation", f).setDuration(0L)).with(ObjectAnimator.ofFloat(this.mView, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, f2).setDuration(100L));
            animatorSet2.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, animatorSet2);
            AnimatorSet animatorSet3 = new AnimatorSet();
            animatorSet3.playSequentially(ObjectAnimator.ofFloat(this.mView, "elevation", f).setDuration(0L), ObjectAnimator.ofFloat(this.mView, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, this.mView.getTranslationZ()).setDuration(100L), ObjectAnimator.ofFloat(this.mView, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, 0.0f).setDuration(100L));
            animatorSet3.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(ENABLED_STATE_SET, animatorSet3);
            AnimatorSet animatorSet4 = new AnimatorSet();
            animatorSet4.play(ObjectAnimator.ofFloat(this.mView, "elevation", 0.0f).setDuration(0L)).with(ObjectAnimator.ofFloat(this.mView, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, 0.0f).setDuration(0L));
            animatorSet4.setInterpolator(ANIM_INTERPOLATOR);
            stateListAnimator.addState(EMPTY_STATE_SET, animatorSet4);
            this.mView.setStateListAnimator(stateListAnimator);
        }
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            updatePadding();
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.support.design.widget.FloatingActionButtonImpl
    void onPaddingUpdated(Rect rect) {
        ShadowViewDelegate shadowViewDelegate;
        Drawable drawable;
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            this.mInsetDrawable = new InsetDrawable(this.mRippleDrawable, rect.left, rect.top, rect.right, rect.bottom);
            shadowViewDelegate = this.mShadowViewDelegate;
            drawable = this.mInsetDrawable;
        } else {
            shadowViewDelegate = this.mShadowViewDelegate;
            drawable = this.mRippleDrawable;
        }
        shadowViewDelegate.setBackgroundDrawable(drawable);
    }

    @Override // android.support.design.widget.FloatingActionButtonIcs, android.support.design.widget.FloatingActionButtonImpl
    boolean requirePreDrawListener() {
        return false;
    }

    @Override // android.support.design.widget.FloatingActionButtonGingerbread, android.support.design.widget.FloatingActionButtonImpl
    void setBackgroundDrawable(ColorStateList colorStateList, PorterDuff.Mode mode, int i, int i2) {
        Drawable layerDrawable;
        this.mShapeDrawable = DrawableCompat.wrap(createShapeDrawable());
        DrawableCompat.setTintList(this.mShapeDrawable, colorStateList);
        if (mode != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, mode);
        }
        if (i2 > 0) {
            this.mBorderDrawable = createBorderDrawable(i2, colorStateList);
            layerDrawable = new LayerDrawable(new Drawable[]{this.mBorderDrawable, this.mShapeDrawable});
        } else {
            this.mBorderDrawable = null;
            layerDrawable = this.mShapeDrawable;
        }
        this.mRippleDrawable = new RippleDrawable(ColorStateList.valueOf(i), layerDrawable, null);
        this.mContentBackground = this.mRippleDrawable;
        this.mShadowViewDelegate.setBackgroundDrawable(this.mRippleDrawable);
    }

    @Override // android.support.design.widget.FloatingActionButtonGingerbread, android.support.design.widget.FloatingActionButtonImpl
    void setRippleColor(int i) {
        if (this.mRippleDrawable instanceof RippleDrawable) {
            ((RippleDrawable) this.mRippleDrawable).setColor(ColorStateList.valueOf(i));
        } else {
            super.setRippleColor(i);
        }
    }
}
