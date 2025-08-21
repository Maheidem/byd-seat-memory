package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class TransitionManagerStaticsIcs extends TransitionManagerStaticsImpl {
    TransitionManagerStaticsIcs() {
    }

    @Override // android.support.transition.TransitionManagerStaticsImpl
    public void beginDelayedTransition(ViewGroup viewGroup) {
        TransitionManagerPort.beginDelayedTransition(viewGroup);
    }

    @Override // android.support.transition.TransitionManagerStaticsImpl
    public void beginDelayedTransition(ViewGroup viewGroup, TransitionImpl transitionImpl) {
        TransitionManagerPort.beginDelayedTransition(viewGroup, transitionImpl == null ? null : ((TransitionIcs) transitionImpl).mTransition);
    }

    @Override // android.support.transition.TransitionManagerStaticsImpl
    public void go(SceneImpl sceneImpl) {
        TransitionManagerPort.go(((SceneIcs) sceneImpl).mScene);
    }

    @Override // android.support.transition.TransitionManagerStaticsImpl
    public void go(SceneImpl sceneImpl, TransitionImpl transitionImpl) {
        TransitionManagerPort.go(((SceneIcs) sceneImpl).mScene, transitionImpl == null ? null : ((TransitionIcs) transitionImpl).mTransition);
    }
}
