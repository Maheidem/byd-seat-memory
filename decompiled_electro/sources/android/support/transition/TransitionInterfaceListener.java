package android.support.transition;

import android.support.transition.TransitionInterface;

/* loaded from: classes.dex */
interface TransitionInterfaceListener<TransitionT extends TransitionInterface> {
    void onTransitionCancel(TransitionT transitiont);

    void onTransitionEnd(TransitionT transitiont);

    void onTransitionPause(TransitionT transitiont);

    void onTransitionResume(TransitionT transitiont);

    void onTransitionStart(TransitionT transitiont);
}
