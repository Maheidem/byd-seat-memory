package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@RequiresApi(19)
@TargetApi(19)
/* loaded from: classes.dex */
class ChangeBoundsKitKat extends TransitionKitKat implements ChangeBoundsInterface {
    public ChangeBoundsKitKat(TransitionInterface transitionInterface) {
        init(transitionInterface, new android.transition.ChangeBounds());
    }

    @Override // android.support.transition.ChangeBoundsInterface
    public void setResizeClip(boolean z) {
        ((android.transition.ChangeBounds) this.mTransition).setResizeClip(z);
    }
}
