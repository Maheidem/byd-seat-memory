package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class VisibilityIcs extends TransitionIcs implements VisibilityImpl {

    private static class VisibilityWrapper extends VisibilityPort {
        private VisibilityInterface mVisibility;

        VisibilityWrapper(VisibilityInterface visibilityInterface) {
            this.mVisibility = visibilityInterface;
        }

        @Override // android.support.transition.VisibilityPort, android.support.transition.TransitionPort
        public void captureEndValues(TransitionValues transitionValues) {
            this.mVisibility.captureEndValues(transitionValues);
        }

        @Override // android.support.transition.VisibilityPort, android.support.transition.TransitionPort
        public void captureStartValues(TransitionValues transitionValues) {
            this.mVisibility.captureStartValues(transitionValues);
        }

        @Override // android.support.transition.VisibilityPort, android.support.transition.TransitionPort
        public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            return this.mVisibility.createAnimator(viewGroup, transitionValues, transitionValues2);
        }

        @Override // android.support.transition.VisibilityPort
        public boolean isVisible(TransitionValues transitionValues) {
            return this.mVisibility.isVisible(transitionValues);
        }

        @Override // android.support.transition.VisibilityPort
        public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
            return this.mVisibility.onAppear(viewGroup, transitionValues, i, transitionValues2, i2);
        }

        @Override // android.support.transition.VisibilityPort
        public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
            return this.mVisibility.onDisappear(viewGroup, transitionValues, i, transitionValues2, i2);
        }
    }

    VisibilityIcs() {
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.support.transition.TransitionIcs, android.support.transition.TransitionImpl
    public void init(TransitionInterface transitionInterface, Object obj) {
        this.mExternalTransition = transitionInterface;
        this.mTransition = obj == null ? new VisibilityWrapper((VisibilityInterface) transitionInterface) : (VisibilityPort) obj;
    }

    @Override // android.support.transition.VisibilityImpl
    public boolean isVisible(TransitionValues transitionValues) {
        return ((VisibilityPort) this.mTransition).isVisible(transitionValues);
    }

    @Override // android.support.transition.VisibilityImpl
    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((VisibilityPort) this.mTransition).onAppear(viewGroup, transitionValues, i, transitionValues2, i2);
    }

    @Override // android.support.transition.VisibilityImpl
    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((VisibilityPort) this.mTransition).onDisappear(viewGroup, transitionValues, i, transitionValues2, i2);
    }
}
