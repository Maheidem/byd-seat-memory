package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class TransitionManagerIcs extends TransitionManagerImpl {
    private final TransitionManagerPort mTransitionManager = new TransitionManagerPort();

    TransitionManagerIcs() {
    }

    @Override // android.support.transition.TransitionManagerImpl
    public void setTransition(SceneImpl sceneImpl, SceneImpl sceneImpl2, TransitionImpl transitionImpl) {
        this.mTransitionManager.setTransition(((SceneIcs) sceneImpl).mScene, ((SceneIcs) sceneImpl2).mScene, transitionImpl == null ? null : ((TransitionIcs) transitionImpl).mTransition);
    }

    @Override // android.support.transition.TransitionManagerImpl
    public void setTransition(SceneImpl sceneImpl, TransitionImpl transitionImpl) {
        this.mTransitionManager.setTransition(((SceneIcs) sceneImpl).mScene, transitionImpl == null ? null : ((TransitionIcs) transitionImpl).mTransition);
    }

    @Override // android.support.transition.TransitionManagerImpl
    public void transitionTo(SceneImpl sceneImpl) {
        this.mTransitionManager.transitionTo(((SceneIcs) sceneImpl).mScene);
    }
}
