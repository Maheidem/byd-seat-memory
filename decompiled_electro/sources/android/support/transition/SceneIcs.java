package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class SceneIcs extends SceneImpl {
    ScenePort mScene;

    SceneIcs() {
    }

    @Override // android.support.transition.SceneImpl
    public void enter() {
        this.mScene.enter();
    }

    @Override // android.support.transition.SceneImpl
    public void exit() {
        this.mScene.exit();
    }

    @Override // android.support.transition.SceneImpl
    public ViewGroup getSceneRoot() {
        return this.mScene.getSceneRoot();
    }

    @Override // android.support.transition.SceneImpl
    public void init(ViewGroup viewGroup) {
        this.mScene = new ScenePort(viewGroup);
    }

    @Override // android.support.transition.SceneImpl
    public void init(ViewGroup viewGroup, View view) {
        this.mScene = new ScenePort(viewGroup, view);
    }

    @Override // android.support.transition.SceneImpl
    public void setEnterAction(Runnable runnable) {
        this.mScene.setEnterAction(runnable);
    }

    @Override // android.support.transition.SceneImpl
    public void setExitAction(Runnable runnable) {
        this.mScene.setExitAction(runnable);
    }
}
