package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButtonImpl;
import android.support.design.widget.ValueAnimatorCompat;
import android.support.v4.view.ViewCompat;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class FloatingActionButtonIcs extends FloatingActionButtonGingerbread {
    private float mRotation;

    FloatingActionButtonIcs(VisibilityAwareImageButton visibilityAwareImageButton, ShadowViewDelegate shadowViewDelegate, ValueAnimatorCompat.Creator creator) {
        super(visibilityAwareImageButton, shadowViewDelegate, creator);
        this.mRotation = this.mView.getRotation();
    }

    private boolean shouldAnimateVisibilityChange() {
        return ViewCompat.isLaidOut(this.mView) && !this.mView.isInEditMode();
    }

    private void updateFromViewRotation() {
        VisibilityAwareImageButton visibilityAwareImageButton;
        int i;
        if (Build.VERSION.SDK_INT == 19) {
            if (this.mRotation % 90.0f != 0.0f) {
                i = 1;
                if (this.mView.getLayerType() != 1) {
                    visibilityAwareImageButton = this.mView;
                    visibilityAwareImageButton.setLayerType(i, null);
                }
            } else if (this.mView.getLayerType() != 0) {
                visibilityAwareImageButton = this.mView;
                i = 0;
                visibilityAwareImageButton.setLayerType(i, null);
            }
        }
        if (this.mShadowDrawable != null) {
            this.mShadowDrawable.setRotation(-this.mRotation);
        }
        if (this.mBorderDrawable != null) {
            this.mBorderDrawable.setRotation(-this.mRotation);
        }
    }

    @Override // android.support.design.widget.FloatingActionButtonGingerbread, android.support.design.widget.FloatingActionButtonImpl
    void hide(@Nullable final FloatingActionButtonImpl.InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean z) {
        if (isOrWillBeHidden()) {
            return;
        }
        this.mView.animate().cancel();
        if (shouldAnimateVisibilityChange()) {
            this.mAnimState = 1;
            this.mView.animate().scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setDuration(200L).setInterpolator(AnimationUtils.FAST_OUT_LINEAR_IN_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: android.support.design.widget.FloatingActionButtonIcs.1
                private boolean mCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    FloatingActionButtonIcs.this.mAnimState = 0;
                    if (this.mCancelled) {
                        return;
                    }
                    FloatingActionButtonIcs.this.mView.internalSetVisibility(z ? 8 : 4, z);
                    if (internalVisibilityChangedListener != null) {
                        internalVisibilityChangedListener.onHidden();
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    FloatingActionButtonIcs.this.mView.internalSetVisibility(0, z);
                    this.mCancelled = false;
                }
            });
        } else {
            this.mView.internalSetVisibility(z ? 8 : 4, z);
            if (internalVisibilityChangedListener != null) {
                internalVisibilityChangedListener.onHidden();
            }
        }
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    void onPreDraw() {
        float rotation = this.mView.getRotation();
        if (this.mRotation != rotation) {
            this.mRotation = rotation;
            updateFromViewRotation();
        }
    }

    @Override // android.support.design.widget.FloatingActionButtonImpl
    boolean requirePreDrawListener() {
        return true;
    }

    @Override // android.support.design.widget.FloatingActionButtonGingerbread, android.support.design.widget.FloatingActionButtonImpl
    void show(@Nullable final FloatingActionButtonImpl.InternalVisibilityChangedListener internalVisibilityChangedListener, final boolean z) {
        if (isOrWillBeShown()) {
            return;
        }
        this.mView.animate().cancel();
        if (shouldAnimateVisibilityChange()) {
            this.mAnimState = 2;
            if (this.mView.getVisibility() != 0) {
                this.mView.setAlpha(0.0f);
                this.mView.setScaleY(0.0f);
                this.mView.setScaleX(0.0f);
            }
            this.mView.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(200L).setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR).setListener(new AnimatorListenerAdapter() { // from class: android.support.design.widget.FloatingActionButtonIcs.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    FloatingActionButtonIcs.this.mAnimState = 0;
                    if (internalVisibilityChangedListener != null) {
                        internalVisibilityChangedListener.onShown();
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    FloatingActionButtonIcs.this.mView.internalSetVisibility(0, z);
                }
            });
            return;
        }
        this.mView.internalSetVisibility(0, z);
        this.mView.setAlpha(1.0f);
        this.mView.setScaleY(1.0f);
        this.mView.setScaleX(1.0f);
        if (internalVisibilityChangedListener != null) {
            internalVisibilityChangedListener.onShown();
        }
    }
}
