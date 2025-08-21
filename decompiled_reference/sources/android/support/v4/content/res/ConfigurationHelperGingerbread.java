package android.support.v4.content.res;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

@RequiresApi(9)
@TargetApi(9)
/* loaded from: classes.dex */
class ConfigurationHelperGingerbread {
    ConfigurationHelperGingerbread() {
    }

    static int getDensityDpi(@NonNull Resources resources) {
        return resources.getDisplayMetrics().densityDpi;
    }

    static int getScreenHeightDp(@NonNull Resources resources) {
        return (int) (r1.heightPixels / resources.getDisplayMetrics().density);
    }

    static int getScreenWidthDp(@NonNull Resources resources) {
        return (int) (r1.widthPixels / resources.getDisplayMetrics().density);
    }

    static int getSmallestScreenWidthDp(@NonNull Resources resources) {
        return Math.min(getScreenWidthDp(resources), getScreenHeightDp(resources));
    }
}
