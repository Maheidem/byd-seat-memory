package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@RequiresApi(19)
@TargetApi(19)
/* loaded from: classes.dex */
class TransitionSetKitKat extends TransitionKitKat implements TransitionSetImpl {
    private android.transition.TransitionSet mTransitionSet = new android.transition.TransitionSet();

    public TransitionSetKitKat(TransitionInterface transitionInterface) {
        init(transitionInterface, this.mTransitionSet);
    }

    @Override // android.support.transition.TransitionSetImpl
    public TransitionSetKitKat addTransition(TransitionImpl transitionImpl) {
        this.mTransitionSet.addTransition(((TransitionKitKat) transitionImpl).mTransition);
        return this;
    }

    @Override // android.support.transition.TransitionSetImpl
    public int getOrdering() {
        return this.mTransitionSet.getOrdering();
    }

    @Override // android.support.transition.TransitionSetImpl
    public TransitionSetKitKat removeTransition(TransitionImpl transitionImpl) {
        this.mTransitionSet.removeTransition(((TransitionKitKat) transitionImpl).mTransition);
        return this;
    }

    @Override // android.support.transition.TransitionSetImpl
    public TransitionSetKitKat setOrdering(int i) {
        this.mTransitionSet.setOrdering(i);
        return this;
    }
}
