package android.support.transition;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class AutoTransitionPort extends TransitionSetPort {
    public AutoTransitionPort() {
        setOrdering(1);
        addTransition(new FadePort(2)).addTransition(new ChangeBoundsPort()).addTransition(new FadePort(1));
    }
}
