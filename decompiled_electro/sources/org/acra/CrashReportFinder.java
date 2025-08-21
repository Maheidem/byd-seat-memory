package org.acra;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.FilenameFilter;

/* loaded from: classes.dex */
final class CrashReportFinder {
    private final Context context;

    public CrashReportFinder(Context context) {
        this.context = context;
    }

    public String[] getCrashReportFiles() {
        if (this.context == null) {
            Log.e(ACRA.LOG_TAG, "Trying to get ACRA reports but ACRA is not initialized.");
        } else {
            File filesDir = this.context.getFilesDir();
            if (filesDir != null) {
                Log.d(ACRA.LOG_TAG, "Looking for error files in " + filesDir.getAbsolutePath());
                String[] list = filesDir.list(new FilenameFilter() { // from class: org.acra.CrashReportFinder.1
                    @Override // java.io.FilenameFilter
                    public boolean accept(File file, String str) {
                        return str.endsWith(ACRAConstants.REPORTFILE_EXTENSION);
                    }
                });
                return list == null ? new String[0] : list;
            }
            Log.w(ACRA.LOG_TAG, "Application files directory does not exist! The application may not be installed correctly. Please try reinstalling.");
        }
        return new String[0];
    }
}
