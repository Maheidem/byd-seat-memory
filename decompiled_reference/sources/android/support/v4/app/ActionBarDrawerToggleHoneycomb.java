package android.support.v4.app;

import android.R;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(11)
@TargetApi(11)
/* loaded from: classes.dex */
class ActionBarDrawerToggleHoneycomb {
    private static final String TAG = "ActionBarDrawerToggleHoneycomb";
    private static final int[] THEME_ATTRS = {R.attr.homeAsUpIndicator};

    private static class SetIndicatorInfo {
        public Method setHomeActionContentDescription;
        public Method setHomeAsUpIndicator;
        public ImageView upIndicatorView;

        SetIndicatorInfo(Activity activity) {
            try {
                this.setHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", Drawable.class);
                this.setHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", Integer.TYPE);
            } catch (NoSuchMethodException unused) {
                View viewFindViewById = activity.findViewById(R.id.home);
                if (viewFindViewById == null) {
                    return;
                }
                ViewGroup viewGroup = (ViewGroup) viewFindViewById.getParent();
                if (viewGroup.getChildCount() != 2) {
                    return;
                }
                View childAt = viewGroup.getChildAt(0);
                View childAt2 = childAt.getId() != 16908332 ? childAt : viewGroup.getChildAt(1);
                if (childAt2 instanceof ImageView) {
                    this.upIndicatorView = (ImageView) childAt2;
                }
            }
        }
    }

    ActionBarDrawerToggleHoneycomb() {
    }

    public static Drawable getThemeUpIndicator(Activity activity) {
        TypedArray typedArrayObtainStyledAttributes = activity.obtainStyledAttributes(THEME_ATTRS);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        typedArrayObtainStyledAttributes.recycle();
        return drawable;
    }

    public static Object setActionBarDescription(Object obj, Activity activity, int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (obj == null) {
            obj = new SetIndicatorInfo(activity);
        }
        SetIndicatorInfo setIndicatorInfo = (SetIndicatorInfo) obj;
        if (setIndicatorInfo.setHomeAsUpIndicator != null) {
            try {
                ActionBar actionBar = activity.getActionBar();
                setIndicatorInfo.setHomeActionContentDescription.invoke(actionBar, Integer.valueOf(i));
                if (Build.VERSION.SDK_INT <= 19) {
                    actionBar.setSubtitle(actionBar.getSubtitle());
                    return obj;
                }
            } catch (Exception e) {
                Log.w(TAG, "Couldn't set content description via JB-MR2 API", e);
            }
        }
        return obj;
    }

    public static Object setActionBarUpIndicator(Object obj, Activity activity, Drawable drawable, int i) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (obj == null) {
            obj = new SetIndicatorInfo(activity);
        }
        SetIndicatorInfo setIndicatorInfo = (SetIndicatorInfo) obj;
        if (setIndicatorInfo.setHomeAsUpIndicator == null) {
            if (setIndicatorInfo.upIndicatorView != null) {
                setIndicatorInfo.upIndicatorView.setImageDrawable(drawable);
                return obj;
            }
            Log.w(TAG, "Couldn't set home-as-up indicator");
            return obj;
        }
        try {
            ActionBar actionBar = activity.getActionBar();
            setIndicatorInfo.setHomeAsUpIndicator.invoke(actionBar, drawable);
            setIndicatorInfo.setHomeActionContentDescription.invoke(actionBar, Integer.valueOf(i));
            return obj;
        } catch (Exception e) {
            Log.w(TAG, "Couldn't set home-as-up indicator via JB-MR2 API", e);
            return obj;
        }
    }
}
