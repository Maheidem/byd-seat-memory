package android.support.transition;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.view.ViewGroup;

@RequiresApi(19)
@TargetApi(19)
/* loaded from: classes.dex */
class SceneStaticsKitKat extends SceneStaticsImpl {
    SceneStaticsKitKat() {
    }

    @Override // android.support.transition.SceneStaticsImpl
    public SceneImpl getSceneForLayout(ViewGroup viewGroup, int i, Context context) {
        SceneKitKat sceneKitKat = new SceneKitKat();
        sceneKitKat.mScene = android.transition.Scene.getSceneForLayout(viewGroup, i, context);
        return sceneKitKat;
    }
}
