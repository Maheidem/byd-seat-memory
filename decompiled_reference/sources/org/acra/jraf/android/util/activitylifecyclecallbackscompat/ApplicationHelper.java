package org.acra.jraf.android.util.activitylifecyclecallbackscompat;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;

/* loaded from: classes.dex */
public class ApplicationHelper {
    public static final boolean PRE_ICS;

    static {
        PRE_ICS = Build.VERSION.SDK_INT < 14;
    }

    @TargetApi(14)
    private static void postIcsRegisterActivityLifecycleCallbacks(Application application, ActivityLifecycleCallbacksCompat activityLifecycleCallbacksCompat) {
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacksWrapper(activityLifecycleCallbacksCompat));
    }

    @TargetApi(14)
    private static void postIcsUnregisterActivityLifecycleCallbacks(Application application, ActivityLifecycleCallbacksCompat activityLifecycleCallbacksCompat) {
        application.unregisterActivityLifecycleCallbacks(new ActivityLifecycleCallbacksWrapper(activityLifecycleCallbacksCompat));
    }

    private static void preIcsRegisterActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat activityLifecycleCallbacksCompat) {
        MainLifecycleDispatcher.get().registerActivityLifecycleCallbacks(activityLifecycleCallbacksCompat);
    }

    private static void preIcsUnregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacksCompat activityLifecycleCallbacksCompat) {
        MainLifecycleDispatcher.get().unregisterActivityLifecycleCallbacks(activityLifecycleCallbacksCompat);
    }

    public static void registerActivityLifecycleCallbacks(Application application, ActivityLifecycleCallbacksCompat activityLifecycleCallbacksCompat) {
        if (PRE_ICS) {
            preIcsRegisterActivityLifecycleCallbacks(activityLifecycleCallbacksCompat);
        } else {
            postIcsRegisterActivityLifecycleCallbacks(application, activityLifecycleCallbacksCompat);
        }
    }

    public void unregisterActivityLifecycleCallbacks(Application application, ActivityLifecycleCallbacksCompat activityLifecycleCallbacksCompat) {
        if (PRE_ICS) {
            preIcsUnregisterActivityLifecycleCallbacks(activityLifecycleCallbacksCompat);
        } else {
            postIcsUnregisterActivityLifecycleCallbacks(application, activityLifecycleCallbacksCompat);
        }
    }
}
