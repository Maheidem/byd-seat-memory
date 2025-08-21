package android.support.design.widget;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.design.widget.ValueAnimatorCompat;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import java.util.ArrayList;

/* loaded from: classes.dex */
class ValueAnimatorCompatImplGingerbread extends ValueAnimatorCompat.Impl {
    private static final int DEFAULT_DURATION = 200;
    private static final int HANDLER_DELAY = 10;
    private static final Handler sHandler = new Handler(Looper.getMainLooper());
    private float mAnimatedFraction;
    private Interpolator mInterpolator;
    private boolean mIsRunning;
    private ArrayList<ValueAnimatorCompat.Impl.AnimatorListenerProxy> mListeners;
    private long mStartTime;
    private ArrayList<ValueAnimatorCompat.Impl.AnimatorUpdateListenerProxy> mUpdateListeners;
    private final int[] mIntValues = new int[2];
    private final float[] mFloatValues = new float[2];
    private long mDuration = 200;
    private final Runnable mRunnable = new Runnable() { // from class: android.support.design.widget.ValueAnimatorCompatImplGingerbread.1
        @Override // java.lang.Runnable
        public void run() {
            ValueAnimatorCompatImplGingerbread.this.update();
        }
    };

    ValueAnimatorCompatImplGingerbread() {
    }

    private void dispatchAnimationCancel() {
        if (this.mListeners != null) {
            int size = this.mListeners.size();
            for (int i = 0; i < size; i++) {
                this.mListeners.get(i).onAnimationCancel();
            }
        }
    }

    private void dispatchAnimationEnd() {
        if (this.mListeners != null) {
            int size = this.mListeners.size();
            for (int i = 0; i < size; i++) {
                this.mListeners.get(i).onAnimationEnd();
            }
        }
    }

    private void dispatchAnimationStart() {
        if (this.mListeners != null) {
            int size = this.mListeners.size();
            for (int i = 0; i < size; i++) {
                this.mListeners.get(i).onAnimationStart();
            }
        }
    }

    private void dispatchAnimationUpdate() {
        if (this.mUpdateListeners != null) {
            int size = this.mUpdateListeners.size();
            for (int i = 0; i < size; i++) {
                this.mUpdateListeners.get(i).onAnimationUpdate();
            }
        }
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void addListener(ValueAnimatorCompat.Impl.AnimatorListenerProxy animatorListenerProxy) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList<>();
        }
        this.mListeners.add(animatorListenerProxy);
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void addUpdateListener(ValueAnimatorCompat.Impl.AnimatorUpdateListenerProxy animatorUpdateListenerProxy) {
        if (this.mUpdateListeners == null) {
            this.mUpdateListeners = new ArrayList<>();
        }
        this.mUpdateListeners.add(animatorUpdateListenerProxy);
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void cancel() {
        this.mIsRunning = false;
        sHandler.removeCallbacks(this.mRunnable);
        dispatchAnimationCancel();
        dispatchAnimationEnd();
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void end() {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            sHandler.removeCallbacks(this.mRunnable);
            this.mAnimatedFraction = 1.0f;
            dispatchAnimationUpdate();
            dispatchAnimationEnd();
        }
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public float getAnimatedFloatValue() {
        return AnimationUtils.lerp(this.mFloatValues[0], this.mFloatValues[1], getAnimatedFraction());
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public float getAnimatedFraction() {
        return this.mAnimatedFraction;
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public int getAnimatedIntValue() {
        return AnimationUtils.lerp(this.mIntValues[0], this.mIntValues[1], getAnimatedFraction());
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public long getDuration() {
        return this.mDuration;
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public boolean isRunning() {
        return this.mIsRunning;
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void setDuration(long j) {
        this.mDuration = j;
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void setFloatValues(float f, float f2) {
        this.mFloatValues[0] = f;
        this.mFloatValues[1] = f2;
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void setIntValues(int i, int i2) {
        this.mIntValues[0] = i;
        this.mIntValues[1] = i2;
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    @Override // android.support.design.widget.ValueAnimatorCompat.Impl
    public void start() {
        if (this.mIsRunning) {
            return;
        }
        if (this.mInterpolator == null) {
            this.mInterpolator = new AccelerateDecelerateInterpolator();
        }
        this.mIsRunning = true;
        this.mAnimatedFraction = 0.0f;
        startInternal();
    }

    final void startInternal() {
        this.mStartTime = SystemClock.uptimeMillis();
        dispatchAnimationUpdate();
        dispatchAnimationStart();
        sHandler.postDelayed(this.mRunnable, 10L);
    }

    final void update() {
        if (this.mIsRunning) {
            float fConstrain = MathUtils.constrain((SystemClock.uptimeMillis() - this.mStartTime) / this.mDuration, 0.0f, 1.0f);
            if (this.mInterpolator != null) {
                fConstrain = this.mInterpolator.getInterpolation(fConstrain);
            }
            this.mAnimatedFraction = fConstrain;
            dispatchAnimationUpdate();
            if (SystemClock.uptimeMillis() >= this.mStartTime + this.mDuration) {
                this.mIsRunning = false;
                dispatchAnimationEnd();
            }
        }
        if (this.mIsRunning) {
            sHandler.postDelayed(this.mRunnable, 10L);
        }
    }
}
