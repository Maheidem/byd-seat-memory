package android.support.transition;

import android.content.Context;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public class Scene {
    private static SceneStaticsImpl sImpl;
    SceneImpl mImpl;

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        sImpl = Build.VERSION.SDK_INT >= 21 ? new SceneStaticsApi21() : Build.VERSION.SDK_INT >= 19 ? new SceneStaticsKitKat() : new SceneStaticsIcs();
    }

    private Scene(SceneImpl sceneImpl) {
        this.mImpl = sceneImpl;
    }

    public Scene(@NonNull ViewGroup viewGroup) {
        this.mImpl = createSceneImpl();
        this.mImpl.init(viewGroup);
    }

    public Scene(@NonNull ViewGroup viewGroup, @NonNull View view) {
        this.mImpl = createSceneImpl();
        this.mImpl.init(viewGroup, view);
    }

    private SceneImpl createSceneImpl() {
        return Build.VERSION.SDK_INT >= 21 ? new SceneApi21() : Build.VERSION.SDK_INT >= 19 ? new SceneKitKat() : new SceneIcs();
    }

    @NonNull
    public static Scene getSceneForLayout(@NonNull ViewGroup viewGroup, @LayoutRes int i, @NonNull Context context) {
        SparseArray sparseArray = (SparseArray) viewGroup.getTag(R.id.transition_scene_layoutid_cache);
        if (sparseArray == null) {
            sparseArray = new SparseArray();
            viewGroup.setTag(R.id.transition_scene_layoutid_cache, sparseArray);
        }
        Scene scene = (Scene) sparseArray.get(i);
        if (scene != null) {
            return scene;
        }
        Scene scene2 = new Scene(sImpl.getSceneForLayout(viewGroup, i, context));
        sparseArray.put(i, scene2);
        return scene2;
    }

    public void enter() {
        this.mImpl.enter();
    }

    public void exit() {
        this.mImpl.exit();
    }

    @NonNull
    public ViewGroup getSceneRoot() {
        return this.mImpl.getSceneRoot();
    }

    public void setEnterAction(@Nullable Runnable runnable) {
        this.mImpl.setEnterAction(runnable);
    }

    public void setExitAction(@Nullable Runnable runnable) {
        this.mImpl.setExitAction(runnable);
    }
}
