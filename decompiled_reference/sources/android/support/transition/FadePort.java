package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.transition.TransitionPort;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class FadePort extends VisibilityPort {
    private static boolean DBG = false;
    public static final int IN = 1;
    private static final String LOG_TAG = "Fade";
    public static final int OUT = 2;
    private static final String PROPNAME_SCREEN_X = "android:fade:screenX";
    private static final String PROPNAME_SCREEN_Y = "android:fade:screenY";
    private int mFadingMode;

    public FadePort() {
        this(3);
    }

    public FadePort(int i) {
        this.mFadingMode = i;
    }

    private void captureValues(TransitionValues transitionValues) {
        int[] iArr = new int[2];
        transitionValues.view.getLocationOnScreen(iArr);
        transitionValues.values.put(PROPNAME_SCREEN_X, Integer.valueOf(iArr[0]));
        transitionValues.values.put(PROPNAME_SCREEN_Y, Integer.valueOf(iArr[1]));
    }

    private Animator createAnimation(View view, float f, float f2, AnimatorListenerAdapter animatorListenerAdapter) {
        if (f == f2) {
            if (animatorListenerAdapter != null) {
                animatorListenerAdapter.onAnimationEnd(null);
            }
            return null;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, "alpha", f, f2);
        if (DBG) {
            Log.d(LOG_TAG, "Created animator " + objectAnimatorOfFloat);
        }
        if (animatorListenerAdapter != null) {
            objectAnimatorOfFloat.addListener(animatorListenerAdapter);
        }
        return objectAnimatorOfFloat;
    }

    @Override // android.support.transition.VisibilityPort, android.support.transition.TransitionPort
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    @Override // android.support.transition.VisibilityPort
    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        if ((this.mFadingMode & 1) != 1 || transitionValues2 == null) {
            return null;
        }
        final View view = transitionValues2.view;
        if (DBG) {
            Log.d(LOG_TAG, "Fade.onAppear: startView, startVis, endView, endVis = " + (transitionValues != null ? transitionValues.view : null) + ", " + i + ", " + view + ", " + i2);
        }
        view.setAlpha(0.0f);
        addListener(new TransitionPort.TransitionListenerAdapter() { // from class: android.support.transition.FadePort.1
            boolean mCanceled = false;
            float mPausedAlpha;

            @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
            public void onTransitionCancel(TransitionPort transitionPort) {
                view.setAlpha(1.0f);
                this.mCanceled = true;
            }

            @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
            public void onTransitionEnd(TransitionPort transitionPort) {
                if (this.mCanceled) {
                    return;
                }
                view.setAlpha(1.0f);
            }

            @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
            public void onTransitionPause(TransitionPort transitionPort) {
                this.mPausedAlpha = view.getAlpha();
                view.setAlpha(1.0f);
            }

            @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
            public void onTransitionResume(TransitionPort transitionPort) {
                view.setAlpha(this.mPausedAlpha);
            }
        });
        return createAnimation(view, 0.0f, 1.0f, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ee  */
    @Override // android.support.transition.VisibilityPort
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.animation.Animator onDisappear(final android.view.ViewGroup r11, android.support.transition.TransitionValues r12, int r13, android.support.transition.TransitionValues r14, final int r15) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.FadePort.onDisappear(android.view.ViewGroup, android.support.transition.TransitionValues, int, android.support.transition.TransitionValues, int):android.animation.Animator");
    }
}
