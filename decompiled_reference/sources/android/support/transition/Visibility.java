package android.support.transition;

import android.animation.Animator;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public abstract class Visibility extends Transition implements VisibilityInterface {
    public Visibility() {
        this(false);
    }

    Visibility(boolean z) {
        super(true);
        if (z) {
            return;
        }
        this.mImpl = Build.VERSION.SDK_INT >= 19 ? new VisibilityKitKat() : new VisibilityIcs();
        this.mImpl.init(this);
    }

    @Override // android.support.transition.Transition, android.support.transition.TransitionInterface
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        this.mImpl.captureEndValues(transitionValues);
    }

    @Override // android.support.transition.Transition, android.support.transition.TransitionInterface
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        this.mImpl.captureStartValues(transitionValues);
    }

    @Override // android.support.transition.VisibilityInterface
    public boolean isVisible(TransitionValues transitionValues) {
        return ((VisibilityImpl) this.mImpl).isVisible(transitionValues);
    }

    @Override // android.support.transition.VisibilityInterface
    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((VisibilityImpl) this.mImpl).onAppear(viewGroup, transitionValues, i, transitionValues2, i2);
    }

    @Override // android.support.transition.VisibilityInterface
    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return ((VisibilityImpl) this.mImpl).onDisappear(viewGroup, transitionValues, i, transitionValues2, i2);
    }
}
