package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@RequiresApi(21)
@TargetApi(21)
/* loaded from: classes.dex */
class SceneStaticsApi21 extends SceneStaticsImpl {
    SceneStaticsApi21() {
    }

    @Override // android.support.transition.SceneStaticsImpl
    public SceneImpl getSceneForLayout(ViewGroup viewGroup, int i, Context context) {
        SceneApi21 sceneApi21 = new SceneApi21();
        sceneApi21.mScene = android.transition.Scene.getSceneForLayout(viewGroup, i, context);
        return sceneApi21;
    }
}
