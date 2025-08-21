package org.acra;

import android.content.Context;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.acra.collector.CrashReportData;
import org.acra.sender.ReportSender;
import org.acra.sender.ReportSenderException;

/* loaded from: classes.dex */
final class SendWorker extends Thread {
    private final boolean approvePendingReports;
    private final Context context;
    private final CrashReportFileNameParser fileNameParser = new CrashReportFileNameParser();
    private final List<ReportSender> reportSenders;
    private final boolean sendOnlySilentReports;

    public SendWorker(Context context, List<ReportSender> list, boolean z, boolean z2) {
        this.context = context;
        this.reportSenders = list;
        this.sendOnlySilentReports = z;
        this.approvePendingReports = z2;
    }

    private void approvePendingReports() {
        Log.d(ACRA.LOG_TAG, "Mark all pending reports as approved.");
        for (String str : new CrashReportFinder(this.context).getCrashReportFiles()) {
            if (!this.fileNameParser.isApproved(str)) {
                File file = new File(this.context.getFilesDir(), str);
                File file2 = new File(this.context.getFilesDir(), str.replace(ACRAConstants.REPORTFILE_EXTENSION, "-approved.stacktrace"));
                if (!file.renameTo(file2)) {
                    Log.e(ACRA.LOG_TAG, "Could not rename approved report from " + file + " to " + file2);
                }
            }
        }
    }

    private void checkAndSendReports(Context context, boolean z) {
        String str;
        StringBuilder sb;
        String str2;
        Log.d(ACRA.LOG_TAG, "#checkAndSendReports - start");
        String[] crashReportFiles = new CrashReportFinder(context).getCrashReportFiles();
        Arrays.sort(crashReportFiles);
        int i = 0;
        for (String str3 : crashReportFiles) {
            if (!z || this.fileNameParser.isSilent(str3)) {
                if (i >= 5) {
                    break;
                }
                Log.i(ACRA.LOG_TAG, "Sending file " + str3);
                try {
                    sendCrashReport(new CrashReportPersister(context).load(str3));
                    deleteFile(context, str3);
                } catch (IOException e) {
                    e = e;
                    str = ACRA.LOG_TAG;
                    sb = new StringBuilder();
                    str2 = "Failed to load crash report for ";
                    sb.append(str2);
                    sb.append(str3);
                    Log.e(str, sb.toString(), e);
                    deleteFile(context, str3);
                    Log.d(ACRA.LOG_TAG, "#checkAndSendReports - finish");
                } catch (RuntimeException e2) {
                    e = e2;
                    str = ACRA.LOG_TAG;
                    sb = new StringBuilder();
                    str2 = "Failed to send crash reports for ";
                    sb.append(str2);
                    sb.append(str3);
                    Log.e(str, sb.toString(), e);
                    deleteFile(context, str3);
                    Log.d(ACRA.LOG_TAG, "#checkAndSendReports - finish");
                } catch (ReportSenderException e3) {
                    Log.e(ACRA.LOG_TAG, "Failed to send crash report for " + str3, e3);
                }
                i++;
            }
        }
        Log.d(ACRA.LOG_TAG, "#checkAndSendReports - finish");
    }

    private void deleteFile(Context context, String str) {
        if (context.deleteFile(str)) {
            return;
        }
        Log.w(ACRA.LOG_TAG, "Could not delete error report : " + str);
    }

    private void sendCrashReport(CrashReportData crashReportData) throws ReportSenderException {
        if (!ACRA.isDebuggable() || ACRA.getConfig().sendReportsInDevMode()) {
            boolean z = false;
            for (ReportSender reportSender : this.reportSenders) {
                try {
                    reportSender.send(this.context, crashReportData);
                    z = true;
                } catch (ReportSenderException e) {
                    if (!z) {
                        throw e;
                    }
                    Log.w(ACRA.LOG_TAG, "ReportSender of class " + reportSender.getClass().getName() + " failed but other senders completed their task. ACRA will not send this report again.");
                }
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        if (this.approvePendingReports) {
            approvePendingReports();
        }
        checkAndSendReports(this.context, this.sendOnlySilentReports);
    }
}
