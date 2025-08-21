package org.acra.jraf.android.util.activitylifecyclecallbackscompat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/* loaded from: classes.dex */
class ActivityLifecycleCallbacksWrapper implements Application.ActivityLifecycleCallbacks {
    private ActivityLifecycleCallbacksCompat mCallback;

    public ActivityLifecycleCallbacksWrapper(ActivityLifecycleCallbacksCompat activityLifecycleCallbacksCompat) {
        this.mCallback = activityLifecycleCallbacksCompat;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ActivityLifecycleCallbacksWrapper)) {
            return false;
        }
        ActivityLifecycleCallbacksWrapper activityLifecycleCallbacksWrapper = (ActivityLifecycleCallbacksWrapper) obj;
        return this.mCallback == null ? activityLifecycleCallbacksWrapper.mCallback == null : this.mCallback.equals(activityLifecycleCallbacksWrapper.mCallback);
    }

    public int hashCode() {
        if (this.mCallback != null) {
            return this.mCallback.hashCode();
        }
        return 0;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
        this.mCallback.onActivityCreated(activity, bundle);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
        this.mCallback.onActivityDestroyed(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        this.mCallback.onActivityPaused(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        this.mCallback.onActivityResumed(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.mCallback.onActivitySaveInstanceState(activity, bundle);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
        this.mCallback.onActivityStarted(activity);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
        this.mCallback.onActivityStopped(activity);
    }
}
