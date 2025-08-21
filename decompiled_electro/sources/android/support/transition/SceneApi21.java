package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

@RequiresApi(21)
@TargetApi(21)
/* loaded from: classes.dex */
class SceneApi21 extends SceneWrapper {
    SceneApi21() {
    }

    @Override // android.support.transition.SceneImpl
    public void enter() {
        this.mScene.enter();
    }

    @Override // android.support.transition.SceneImpl
    public void init(ViewGroup viewGroup) {
        this.mScene = new android.transition.Scene(viewGroup);
    }

    @Override // android.support.transition.SceneImpl
    public void init(ViewGroup viewGroup, View view) {
        this.mScene = new android.transition.Scene(viewGroup, view);
    }
}
