package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class TransitionSetIcs extends TransitionIcs implements TransitionSetImpl {
    private TransitionSetPort mTransitionSet = new TransitionSetPort();

    public TransitionSetIcs(TransitionInterface transitionInterface) {
        init(transitionInterface, this.mTransitionSet);
    }

    @Override // android.support.transition.TransitionSetImpl
    public TransitionSetIcs addTransition(TransitionImpl transitionImpl) {
        this.mTransitionSet.addTransition(((TransitionIcs) transitionImpl).mTransition);
        return this;
    }

    @Override // android.support.transition.TransitionSetImpl
    public int getOrdering() {
        return this.mTransitionSet.getOrdering();
    }

    @Override // android.support.transition.TransitionSetImpl
    public TransitionSetIcs removeTransition(TransitionImpl transitionImpl) {
        this.mTransitionSet.removeTransition(((TransitionIcs) transitionImpl).mTransition);
        return this;
    }

    @Override // android.support.transition.TransitionSetImpl
    public TransitionSetIcs setOrdering(int i) {
        this.mTransitionSet.setOrdering(i);
        return this;
    }
}
