package android.support.transition;

import android.annotation.TargetApi;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class WindowIdPort {
    private final IBinder mToken;

    private WindowIdPort(IBinder iBinder) {
        this.mToken = iBinder;
    }

    static WindowIdPort getWindowId(@NonNull View view) {
        return new WindowIdPort(view.getWindowToken());
    }

    public boolean equals(Object obj) {
        return (obj instanceof WindowIdPort) && ((WindowIdPort) obj).mToken.equals(this.mToken);
    }
}
