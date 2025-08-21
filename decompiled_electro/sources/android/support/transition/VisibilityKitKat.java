package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@RequiresApi(19)
@TargetApi(19)
/* loaded from: classes.dex */
class VisibilityKitKat extends TransitionKitKat implements VisibilityImpl {

    private static class VisibilityWrapper extends android.transition.Visibility {
        private final VisibilityInterface mVisibility;

        VisibilityWrapper(VisibilityInterface visibilityInterface) {
            this.mVisibility = visibilityInterface;
        }

        @Override // android.transition.Visibility, android.transition.Transition
        public void captureEndValues(android.transition.TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureEndValues(this.mVisibility, transitionValues);
        }

        @Override // android.transition.Visibility, android.transition.Transition
        public void captureStartValues(android.transition.TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureStartValues(this.mVisibility, transitionValues);
        }

        @Override // android.transition.Visibility, android.transition.Transition
        public Animator createAnimator(ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
            return this.mVisibility.createAnimator(viewGroup, TransitionKitKat.convertToSupport(transitionValues), TransitionKitKat.convertToSupport(transitionValues2));
        }

        @Override // android.transition.Visibility
        public boolean isVisible(android.transition.TransitionValues transitionValues) {
            if (transitionValues == null) {
                return false;
            }
            TransitionValues transitionValues2 = new TransitionValues();
            TransitionKitKat.copyValues(transitionValues, transitionValues2);
            return this.mVisibility.isVisible(transitionValues2);
        }

        @Override // android.transition.Visibility
        public Animator onAppear(ViewGroup viewGroup, android.transition.TransitionValues transitionValues, int i, android.transition.TransitionValues transitionValues2, int i2) {
            return this.mVisibility.onAppear(viewGroup, TransitionKitKat.convertToSupport(transitionValues), i, TransitionKitKat.convertToSupport(transitionValues2), i2);
        }

        @Override // android.transition.Visibility
        public Animator onDisappear(ViewGroup viewGroup, android.transition.TransitionValues transitionValues, int i, android.transition.TransitionValues transitionValues2, int i2) {
            return this.mVisibility.onDisappear(viewGroup, TransitionKitKat.convertToSupport(transitionValues), i, TransitionKitKat.convertToSupport(transitionValues2), i2);
        }
    }

    VisibilityKitKat() {
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.support.transition.TransitionKitKat, android.support.transition.TransitionImpl
    public void init(TransitionInterface transitionInterface, Object obj) {
        this.mExternalTransition = transitionInterface;
        this.mTransition = obj == null ? new VisibilityWrapper((VisibilityInterface) transitionInterface) : (android.transition.Visibility) obj;
    }

    @Override // android.support.transition.VisibilityImpl
    public boolean isVisible(TransitionValues transitionValues) {
        return ((android.transition.Visibility) this.mTransition).isVisible(convertToPlatform(transitionValues));
    }

    @Override // android.support.transition.VisibilityImpl
    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((android.transition.Visibility) this.mTransition).onAppear(viewGroup, convertToPlatform(transitionValues), i, convertToPlatform(transitionValues2), i2);
    }

    @Override // android.support.transition.VisibilityImpl
    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((android.transition.Visibility) this.mTransition).onDisappear(viewGroup, convertToPlatform(transitionValues), i, convertToPlatform(transitionValues2), i2);
    }
}
