package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(19)
@TargetApi(19)
/* loaded from: classes.dex */
class SceneKitKat extends SceneWrapper {
    private static Field sEnterAction;
    private static Method sSetCurrentScene;
    private View mLayout;

    SceneKitKat() {
    }

    private void invokeEnterAction() {
        if (sEnterAction == null) {
            try {
                sEnterAction = android.transition.Scene.class.getDeclaredField("mEnterAction");
                sEnterAction.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Runnable runnable = (Runnable) sEnterAction.get(this.mScene);
            if (runnable != null) {
                runnable.run();
            }
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    private void updateCurrentScene(View view) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (sSetCurrentScene == null) {
            try {
                sSetCurrentScene = android.transition.Scene.class.getDeclaredMethod("setCurrentScene", View.class, android.transition.Scene.class);
                sSetCurrentScene.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            sSetCurrentScene.invoke(null, view, this.mScene);
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new RuntimeException(e2);
        }
    }

    @Override // android.support.transition.SceneImpl
    public void enter() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.mLayout == null) {
            this.mScene.enter();
            return;
        }
        ViewGroup sceneRoot = getSceneRoot();
        sceneRoot.removeAllViews();
        sceneRoot.addView(this.mLayout);
        invokeEnterAction();
        updateCurrentScene(sceneRoot);
    }

    @Override // android.support.transition.SceneImpl
    public void init(ViewGroup viewGroup) {
        this.mScene = new android.transition.Scene(viewGroup);
    }

    @Override // android.support.transition.SceneImpl
    public void init(ViewGroup viewGroup, View view) {
        if (view instanceof ViewGroup) {
            this.mScene = new android.transition.Scene(viewGroup, (ViewGroup) view);
        } else {
            this.mScene = new android.transition.Scene(viewGroup);
            this.mLayout = view;
        }
    }
}
