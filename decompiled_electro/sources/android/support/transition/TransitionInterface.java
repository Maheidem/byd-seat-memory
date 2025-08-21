package android.support.transition;

import android.animation.Animator;
import android.view.ViewGroup;

/* loaded from: classes.dex */
interface TransitionInterface {
    void captureEndValues(TransitionValues transitionValues);

    void captureStartValues(TransitionValues transitionValues);

    Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2);
}
