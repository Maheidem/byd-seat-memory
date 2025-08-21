package android.support.v4.animation;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(9)
@TargetApi(9)
/* loaded from: classes.dex */
class GingerbreadAnimatorCompatProvider implements AnimatorProvider {

    private static class GingerbreadFloatValueAnimator implements ValueAnimatorCompat {
        private long mStartTime;
        View mTarget;
        List<AnimatorListenerCompat> mListeners = new ArrayList();
        List<AnimatorUpdateListenerCompat> mUpdateListeners = new ArrayList();
        private long mDuration = 200;
        private float mFraction = 0.0f;
        private boolean mStarted = false;
        private boolean mEnded = false;
        private Runnable mLoopRunnable = new Runnable() { // from class: android.support.v4.animation.GingerbreadAnimatorCompatProvider.GingerbreadFloatValueAnimator.1
            @Override // java.lang.Runnable
            public void run() {
                float time = ((GingerbreadFloatValueAnimator.this.getTime() - GingerbreadFloatValueAnimator.this.mStartTime) * 1.0f) / GingerbreadFloatValueAnimator.this.mDuration;
                if (time > 1.0f || GingerbreadFloatValueAnimator.this.mTarget.getParent() == null) {
                    time = 1.0f;
                }
                GingerbreadFloatValueAnimator.this.mFraction = time;
                GingerbreadFloatValueAnimator.this.notifyUpdateListeners();
                if (GingerbreadFloatValueAnimator.this.mFraction >= 1.0f) {
                    GingerbreadFloatValueAnimator.this.dispatchEnd();
                } else {
                    GingerbreadFloatValueAnimator.this.mTarget.postDelayed(GingerbreadFloatValueAnimator.this.mLoopRunnable, 16L);
                }
            }
        };

        private void dispatchCancel() {
            for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                this.mListeners.get(size).onAnimationCancel(this);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void dispatchEnd() {
            for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                this.mListeners.get(size).onAnimationEnd(this);
            }
        }

        private void dispatchStart() {
            for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                this.mListeners.get(size).onAnimationStart(this);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public long getTime() {
            return this.mTarget.getDrawingTime();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void notifyUpdateListeners() {
            for (int size = this.mUpdateListeners.size() - 1; size >= 0; size--) {
                this.mUpdateListeners.get(size).onAnimationUpdate(this);
            }
        }

        @Override // android.support.v4.animation.ValueAnimatorCompat
        public void addListener(AnimatorListenerCompat animatorListenerCompat) {
            this.mListeners.add(animatorListenerCompat);
        }

        @Override // android.support.v4.animation.ValueAnimatorCompat
        public void addUpdateListener(AnimatorUpdateListenerCompat animatorUpdateListenerCompat) {
            this.mUpdateListeners.add(animatorUpdateListenerCompat);
        }

        @Override // android.support.v4.animation.ValueAnimatorCompat
        public void cancel() {
            if (this.mEnded) {
                return;
            }
            this.mEnded = true;
            if (this.mStarted) {
                dispatchCancel();
            }
            dispatchEnd();
        }

        @Override // android.support.v4.animation.ValueAnimatorCompat
        public float getAnimatedFraction() {
            return this.mFraction;
        }

        @Override // android.support.v4.animation.ValueAnimatorCompat
        public void setDuration(long j) {
            if (this.mStarted) {
                return;
            }
            this.mDuration = j;
        }

        @Override // android.support.v4.animation.ValueAnimatorCompat
        public void setTarget(View view) {
            this.mTarget = view;
        }

        @Override // android.support.v4.animation.ValueAnimatorCompat
        public void start() {
            if (this.mStarted) {
                return;
            }
            this.mStarted = true;
            dispatchStart();
            this.mFraction = 0.0f;
            this.mStartTime = getTime();
            this.mTarget.postDelayed(this.mLoopRunnable, 16L);
        }
    }

    GingerbreadAnimatorCompatProvider() {
    }

    @Override // android.support.v4.animation.AnimatorProvider
    public void clearInterpolator(View view) {
    }

    @Override // android.support.v4.animation.AnimatorProvider
    public ValueAnimatorCompat emptyValueAnimator() {
        return new GingerbreadFloatValueAnimator();
    }
}
