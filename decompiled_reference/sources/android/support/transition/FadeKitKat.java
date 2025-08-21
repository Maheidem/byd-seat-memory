package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@RequiresApi(19)
@TargetApi(19)
/* loaded from: classes.dex */
class FadeKitKat extends TransitionKitKat implements VisibilityImpl {
    public FadeKitKat(TransitionInterface transitionInterface) {
        init(transitionInterface, new android.transition.Fade());
    }

    public FadeKitKat(TransitionInterface transitionInterface, int i) {
        init(transitionInterface, new android.transition.Fade(i));
    }

    @Override // android.support.transition.VisibilityImpl
    public boolean isVisible(TransitionValues transitionValues) {
        return ((android.transition.Fade) this.mTransition).isVisible(convertToPlatform(transitionValues));
    }

    @Override // android.support.transition.VisibilityImpl
    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((android.transition.Fade) this.mTransition).onAppear(viewGroup, convertToPlatform(transitionValues), i, convertToPlatform(transitionValues2), i2);
    }

    @Override // android.support.transition.VisibilityImpl
    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((android.transition.Fade) this.mTransition).onDisappear(viewGroup, convertToPlatform(transitionValues), i, convertToPlatform(transitionValues2), i2);
    }
}
