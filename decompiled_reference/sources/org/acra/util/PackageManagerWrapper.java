package org.acra.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import org.acra.ACRA;

/* loaded from: classes.dex */
public final class PackageManagerWrapper {
    private final Context context;

    public PackageManagerWrapper(Context context) {
        this.context = context;
    }

    public PackageInfo getPackageInfo() {
        PackageManager packageManager = this.context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            return packageManager.getPackageInfo(this.context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.v(ACRA.LOG_TAG, "Failed to find PackageInfo for current App : " + this.context.getPackageName());
            return null;
        } catch (RuntimeException unused2) {
            return null;
        }
    }

    public boolean hasPermission(String str) {
        PackageManager packageManager = this.context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        try {
            return packageManager.checkPermission(str, this.context.getPackageName()) == 0;
        } catch (RuntimeException unused) {
            return false;
        }
    }
}
