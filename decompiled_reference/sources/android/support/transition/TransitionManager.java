package android.support.transition;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public class TransitionManager {
    private static TransitionManagerStaticsImpl sImpl;
    private TransitionManagerImpl mImpl;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        sImpl = Build.VERSION.SDK_INT < 19 ? new TransitionManagerStaticsIcs() : new TransitionManagerStaticsKitKat();
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public TransitionManager() {
        this.mImpl = Build.VERSION.SDK_INT < 19 ? new TransitionManagerIcs() : new TransitionManagerKitKat();
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup) {
        sImpl.beginDelayedTransition(viewGroup);
    }

    public static void beginDelayedTransition(@NonNull ViewGroup viewGroup, @Nullable Transition transition) {
        sImpl.beginDelayedTransition(viewGroup, transition == null ? null : transition.mImpl);
    }

    public static void go(@NonNull Scene scene) {
        sImpl.go(scene.mImpl);
    }

    public static void go(@NonNull Scene scene, @Nullable Transition transition) {
        sImpl.go(scene.mImpl, transition == null ? null : transition.mImpl);
    }

    public void setTransition(@NonNull Scene scene, @NonNull Scene scene2, @Nullable Transition transition) {
        this.mImpl.setTransition(scene.mImpl, scene2.mImpl, transition == null ? null : transition.mImpl);
    }

    public void setTransition(@NonNull Scene scene, @Nullable Transition transition) {
        this.mImpl.setTransition(scene.mImpl, transition == null ? null : transition.mImpl);
    }

    public void transitionTo(@NonNull Scene scene) {
        this.mImpl.transitionTo(scene.mImpl);
    }
}
