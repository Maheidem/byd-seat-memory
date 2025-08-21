package android.support.transition;

import android.animation.Animator;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public class Fade extends Visibility {
    public static final int IN = 1;
    public static final int OUT = 2;

    public Fade() {
        this(-1);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public Fade(int i) {
        TransitionImpl fadeIcs;
        TransitionImpl fadeIcs2;
        super(true);
        if (Build.VERSION.SDK_INT >= 19) {
            if (i > 0) {
                fadeIcs2 = new FadeKitKat(this, i);
                this.mImpl = fadeIcs2;
            } else {
                fadeIcs = new FadeKitKat(this);
                this.mImpl = fadeIcs;
            }
        }
        if (i > 0) {
            fadeIcs2 = new FadeIcs(this, i);
            this.mImpl = fadeIcs2;
        } else {
            fadeIcs = new FadeIcs(this);
            this.mImpl = fadeIcs;
        }
    }

    @Override // android.support.transition.Visibility, android.support.transition.Transition, android.support.transition.TransitionInterface
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        this.mImpl.captureEndValues(transitionValues);
    }

    @Override // android.support.transition.Visibility, android.support.transition.Transition, android.support.transition.TransitionInterface
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        this.mImpl.captureStartValues(transitionValues);
    }

    @Override // android.support.transition.Transition, android.support.transition.TransitionInterface
    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @NonNull TransitionValues transitionValues, @NonNull TransitionValues transitionValues2) {
        return this.mImpl.createAnimator(viewGroup, transitionValues, transitionValues2);
    }
}
