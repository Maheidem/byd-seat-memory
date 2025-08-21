package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.design.widget.ValueAnimatorCompat;
import android.view.animation.Interpolator;

@RequiresApi(12)
@TargetApi(12)
/* loaded from: classes.dex */
class ValueAnimatorCompatImplHoneycombMr1 extends ValueAnimatorCompat.Impl {
    private final ValueAnimator mValueAnimator = new ValueAnimator();

    ValueAnimatorCompatImplHoneycombMr1() {
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void addListener(final ValueAnimatorCompat.Impl.AnimatorListenerProxy animatorListenerProxy) {
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() { // from class: android.support.design.widget.ValueAnimatorCompatImplHoneycombMr1.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                animatorListenerProxy.onAnimationCancel();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                animatorListenerProxy.onAnimationEnd();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                animatorListenerProxy.onAnimationStart();
            }
        });
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void addUpdateListener(final ValueAnimatorCompat.Impl.AnimatorUpdateListenerProxy animatorUpdateListenerProxy) {
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.support.design.widget.ValueAnimatorCompatImplHoneycombMr1.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatorUpdateListenerProxy.onAnimationUpdate();
            }
        });
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void cancel() {
        this.mValueAnimator.cancel();
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void end() {
        this.mValueAnimator.end();
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public float getAnimatedFloatValue() {
        return ((Float) this.mValueAnimator.getAnimatedValue()).floatValue();
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public float getAnimatedFraction() {
        return this.mValueAnimator.getAnimatedFraction();
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public int getAnimatedIntValue() {
        return ((Integer) this.mValueAnimator.getAnimatedValue()).intValue();
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public long getDuration() {
        return this.mValueAnimator.getDuration();
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public boolean isRunning() {
        return this.mValueAnimator.isRunning();
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void setDuration(long j) {
        this.mValueAnimator.setDuration(j);
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void setFloatValues(float f, float f2) {
        this.mValueAnimator.setFloatValues(f, f2);
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void setIntValues(int i, int i2) {
        this.mValueAnimator.setIntValues(i, i2);
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void setInterpolator(Interpolator interpolator) {
        this.mValueAnimator.setInterpolator(interpolator);
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void start() {
        this.mValueAnimator.start();
    }
}
