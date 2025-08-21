package android.support.v4.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompatApi21;
import android.view.View;
import java.util.List;
import java.util.Map;

@RequiresApi(23)
@TargetApi(23)
/* loaded from: classes.dex */
class ActivityCompatApi23 {

    public interface OnSharedElementsReadyListenerBridge {
        void onSharedElementsReady();
    }

    public interface RequestPermissionsRequestCodeValidator {
        void validateRequestPermissionsRequestCode(int i);
    }

    public static abstract class SharedElementCallback23 extends ActivityCompatApi21.SharedElementCallback21 {
        public abstract void onSharedElementsArrived(List<String> list, List<View> list2, OnSharedElementsReadyListenerBridge onSharedElementsReadyListenerBridge);
    }

    private static class SharedElementCallbackImpl extends android.app.SharedElementCallback {
        private SharedElementCallback23 mCallback;

        public SharedElementCallbackImpl(SharedElementCallback23 sharedElementCallback23) {
            this.mCallback = sharedElementCallback23;
        }

        @Override // android.app.SharedElementCallback
        public Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) {
            return this.mCallback.onCaptureSharedElementSnapshot(view, matrix, rectF);
        }

        @Override // android.app.SharedElementCallback
        public View onCreateSnapshotView(Context context, Parcelable parcelable) {
            return this.mCallback.onCreateSnapshotView(context, parcelable);
        }

        @Override // android.app.SharedElementCallback
        public void onMapSharedElements(List<String> list, Map<String, View> map) {
            this.mCallback.onMapSharedElements(list, map);
        }

        @Override // android.app.SharedElementCallback
        public void onRejectSharedElements(List<View> list) {
            this.mCallback.onRejectSharedElements(list);
        }

        @Override // android.app.SharedElementCallback
        public void onSharedElementEnd(List<String> list, List<View> list2, List<View> list3) {
            this.mCallback.onSharedElementEnd(list, list2, list3);
        }

        @Override // android.app.SharedElementCallback
        public void onSharedElementStart(List<String> list, List<View> list2, List<View> list3) {
            this.mCallback.onSharedElementStart(list, list2, list3);
        }

        @Override // android.app.SharedElementCallback
        public void onSharedElementsArrived(List<String> list, List<View> list2, final SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
            this.mCallback.onSharedElementsArrived(list, list2, new OnSharedElementsReadyListenerBridge() { // from class: android.support.v4.app.ActivityCompatApi23.SharedElementCallbackImpl.1
                @Override // android.support.v4.app.ActivityCompatApi23.OnSharedElementsReadyListenerBridge
                public void onSharedElementsReady() {
                    onSharedElementsReadyListener.onSharedElementsReady();
                }
            });
        }
    }

    ActivityCompatApi23() {
    }

    private static android.app.SharedElementCallback createCallback(SharedElementCallback23 sharedElementCallback23) {
        if (sharedElementCallback23 != null) {
            return new SharedElementCallbackImpl(sharedElementCallback23);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void requestPermissions(Activity activity, String[] strArr, int i) {
        if (activity instanceof RequestPermissionsRequestCodeValidator) {
            ((RequestPermissionsRequestCodeValidator) activity).validateRequestPermissionsRequestCode(i);
        }
        activity.requestPermissions(strArr, i);
    }

    public static void setEnterSharedElementCallback(Activity activity, SharedElementCallback23 sharedElementCallback23) {
        activity.setEnterSharedElementCallback(createCallback(sharedElementCallback23));
    }

    public static void setExitSharedElementCallback(Activity activity, SharedElementCallback23 sharedElementCallback23) {
        activity.setExitSharedElementCallback(createCallback(sharedElementCallback23));
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String str) {
        return activity.shouldShowRequestPermissionRationale(str);
    }
}
