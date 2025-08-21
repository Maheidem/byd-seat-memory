package org.acra;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import org.acra.collector.CrashReportData;
import org.acra.util.ToastSender;

/* loaded from: classes.dex */
public abstract class BaseCrashReportDialog extends Activity {
    private String mReportFileName;

    protected void cancelReports() {
        ACRA.getErrorReporter().deletePendingNonApprovedReports(false);
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ACRA.log.d(ACRA.LOG_TAG, "CrashReportDialog extras=" + getIntent().getExtras());
        if (getIntent().getBooleanExtra("FORCE_CANCEL", false)) {
            ACRA.log.d(ACRA.LOG_TAG, "Forced reports deletion.");
            cancelReports();
            finish();
            return;
        }
        this.mReportFileName = getIntent().getStringExtra(ACRAConstants.EXTRA_REPORT_FILE_NAME);
        Log.d(ACRA.LOG_TAG, "Opening CrashReportDialog for " + this.mReportFileName);
        if (this.mReportFileName == null) {
            finish();
        }
    }

    protected void sendCrash(String str, String str2) {
        CrashReportPersister crashReportPersister = new CrashReportPersister(getApplicationContext());
        try {
            Log.d(ACRA.LOG_TAG, "Add user comment to " + this.mReportFileName);
            CrashReportData crashReportDataLoad = crashReportPersister.load(this.mReportFileName);
            ReportField reportField = ReportField.USER_COMMENT;
            if (str == null) {
                str = "";
            }
            crashReportDataLoad.put((CrashReportData) reportField, (ReportField) str);
            ReportField reportField2 = ReportField.USER_EMAIL;
            if (str2 == null) {
                str2 = "";
            }
            crashReportDataLoad.put((CrashReportData) reportField2, (ReportField) str2);
            crashReportPersister.store(crashReportDataLoad, this.mReportFileName);
        } catch (IOException e) {
            Log.w(ACRA.LOG_TAG, "User comment not added: ", e);
        }
        Log.v(ACRA.LOG_TAG, "About to start SenderWorker from CrashReportDialog");
        ACRA.getErrorReporter().startSendingReports(false, true);
        int iResDialogOkToast = ACRA.getConfig().resDialogOkToast();
        if (iResDialogOkToast != 0) {
            ToastSender.sendToast(getApplicationContext(), iResDialogOkToast, 1);
        }
    }
}
