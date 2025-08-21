package org.acra;

import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Process;
import android.text.format.Time;
import android.util.Log;
import java.io.File;
import java.lang.Thread;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.acra.collector.Compatibility;
import org.acra.collector.ConfigurationCollector;
import org.acra.collector.CrashReportData;
import org.acra.collector.CrashReportDataFactory;
import org.acra.jraf.android.util.activitylifecyclecallbackscompat.ActivityLifecycleCallbacksCompat;
import org.acra.jraf.android.util.activitylifecyclecallbackscompat.ApplicationHelper;
import org.acra.sender.EmailIntentSender;
import org.acra.sender.HttpSender;
import org.acra.sender.ReportSender;
import org.acra.util.PackageManagerWrapper;
import org.acra.util.ToastSender;

/* loaded from: classes.dex */
public class ErrorReporter implements Thread.UncaughtExceptionHandler {
    private static final ExceptionHandlerInitializer NULL_EXCEPTION_HANDLER_INITIALIZER = new ExceptionHandlerInitializer() { // from class: org.acra.ErrorReporter.1
        @Override // org.acra.ExceptionHandlerInitializer
        public void initializeExceptionHandler(ErrorReporter errorReporter) {
        }
    };
    private static int mNotificationCounter = 0;
    private static boolean toastWaitEnded = true;
    private final CrashReportDataFactory crashReportDataFactory;
    private boolean enabled;
    private final Application mContext;
    private final Thread.UncaughtExceptionHandler mDfltExceptionHandler;
    private final SharedPreferences prefs;
    private final List<ReportSender> mReportSenders = new ArrayList();
    private final CrashReportFileNameParser fileNameParser = new CrashReportFileNameParser();
    private WeakReference<Activity> lastActivityCreated = new WeakReference<>(null);
    private volatile ExceptionHandlerInitializer exceptionHandlerInitializer = NULL_EXCEPTION_HANDLER_INITIALIZER;

    public final class ReportBuilder {
        private Map<String, String> mCustomData;
        private Throwable mException;
        private String mMessage;
        private Thread mUncaughtExceptionThread;
        private boolean mForceSilent = false;
        private boolean mEndsApplication = false;

        public ReportBuilder() {
        }

        private void initCustomData() {
            if (this.mCustomData == null) {
                this.mCustomData = new HashMap();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public ReportBuilder uncaughtExceptionThread(Thread thread) {
            this.mUncaughtExceptionThread = thread;
            return this;
        }

        public ReportBuilder customData(String str, String str2) {
            initCustomData();
            this.mCustomData.put(str, str2);
            return this;
        }

        public ReportBuilder customData(Map<String, String> map) {
            initCustomData();
            this.mCustomData.putAll(map);
            return this;
        }

        public ReportBuilder endsApplication() {
            this.mEndsApplication = true;
            return this;
        }

        public ReportBuilder exception(Throwable th) {
            this.mException = th;
            return this;
        }

        public ReportBuilder forceSilent() {
            this.mForceSilent = true;
            return this;
        }

        public ReportBuilder message(String str) {
            this.mMessage = str;
            return this;
        }

        public void send() {
            if (this.mMessage == null && this.mException == null) {
                this.mMessage = "Report requested by developer";
            }
            ErrorReporter.this.report(this);
        }
    }

    private static class TimeHelper {
        private Long initialTimeMillis;

        private TimeHelper() {
        }

        public long getElapsedTime() {
            if (this.initialTimeMillis == null) {
                return 0L;
            }
            return System.currentTimeMillis() - this.initialTimeMillis.longValue();
        }

        public void setInitialTimeMillis(long j) {
            this.initialTimeMillis = Long.valueOf(j);
        }
    }

    ErrorReporter(Application application, SharedPreferences sharedPreferences, boolean z) {
        this.enabled = false;
        this.mContext = application;
        this.prefs = sharedPreferences;
        this.enabled = z;
        String strCollectConfiguration = ConfigurationCollector.collectConfiguration(this.mContext);
        Time time = new Time();
        time.setToNow();
        if (Compatibility.getAPILevel() >= 14) {
            ApplicationHelper.registerActivityLifecycleCallbacks(application, new ActivityLifecycleCallbacksCompat() { // from class: org.acra.ErrorReporter.2
                @Override // org.acra.jraf.android.util.activitylifecyclecallbackscompat.ActivityLifecycleCallbacksCompat
                public void onActivityCreated(Activity activity, Bundle bundle) {
                    if (activity instanceof BaseCrashReportDialog) {
                        return;
                    }
                    ErrorReporter.this.lastActivityCreated = new WeakReference(activity);
                }

                @Override // org.acra.jraf.android.util.activitylifecyclecallbackscompat.ActivityLifecycleCallbacksCompat
                public void onActivityDestroyed(Activity activity) {
                }

                @Override // org.acra.jraf.android.util.activitylifecyclecallbackscompat.ActivityLifecycleCallbacksCompat
                public void onActivityPaused(Activity activity) {
                }

                @Override // org.acra.jraf.android.util.activitylifecyclecallbackscompat.ActivityLifecycleCallbacksCompat
                public void onActivityResumed(Activity activity) {
                }

                @Override // org.acra.jraf.android.util.activitylifecyclecallbackscompat.ActivityLifecycleCallbacksCompat
                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                @Override // org.acra.jraf.android.util.activitylifecyclecallbackscompat.ActivityLifecycleCallbacksCompat
                public void onActivityStarted(Activity activity) {
                }

                @Override // org.acra.jraf.android.util.activitylifecyclecallbackscompat.ActivityLifecycleCallbacksCompat
                public void onActivityStopped(Activity activity) {
                }
            });
        }
        this.crashReportDataFactory = new CrashReportDataFactory(this.mContext, sharedPreferences, time, strCollectConfiguration);
        this.mDfltExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    private boolean containsOnlySilentOrApprovedReports(String[] strArr) {
        for (String str : strArr) {
            if (!this.fileNameParser.isApproved(str)) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Intent createCrashReportDialogIntent(String str, ReportBuilder reportBuilder) {
        Log.d(ACRA.LOG_TAG, "Creating DialogIntent for " + str + " exception=" + reportBuilder.mException);
        Intent intent = new Intent(this.mContext, ACRA.getConfig().reportDialogClass());
        intent.putExtra(ACRAConstants.EXTRA_REPORT_FILE_NAME, str);
        intent.putExtra(ACRAConstants.EXTRA_REPORT_EXCEPTION, reportBuilder.mException);
        return intent;
    }

    private void createNotification(String str, ReportBuilder reportBuilder) {
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        ACRAConfiguration config = ACRA.getConfig();
        Notification notification = new Notification(config.resNotifIcon(), this.mContext.getText(config.resNotifTickerText()), System.currentTimeMillis());
        CharSequence text = this.mContext.getText(config.resNotifTitle());
        CharSequence text2 = this.mContext.getText(config.resNotifText());
        Log.d(ACRA.LOG_TAG, "Creating Notification for " + str);
        Intent intentCreateCrashReportDialogIntent = createCrashReportDialogIntent(str, reportBuilder);
        Application application = this.mContext;
        int i = mNotificationCounter;
        mNotificationCounter = i + 1;
        notification.setLatestEventInfo(this.mContext, text, text2, PendingIntent.getActivity(application, i, intentCreateCrashReportDialogIntent, 134217728));
        notification.flags |= 16;
        Intent intentCreateCrashReportDialogIntent2 = createCrashReportDialogIntent(str, reportBuilder);
        intentCreateCrashReportDialogIntent2.putExtra("FORCE_CANCEL", true);
        notification.deleteIntent = PendingIntent.getActivity(this.mContext, -1, intentCreateCrashReportDialogIntent2, 0);
        notificationManager.notify(666, notification);
    }

    private void deletePendingReports(boolean z, boolean z2, int i) {
        String[] crashReportFiles = new CrashReportFinder(this.mContext).getCrashReportFiles();
        Arrays.sort(crashReportFiles);
        for (int i2 = 0; i2 < crashReportFiles.length - i; i2++) {
            String str = crashReportFiles[i2];
            boolean zIsApproved = this.fileNameParser.isApproved(str);
            if ((zIsApproved && z) || (!zIsApproved && z2)) {
                File file = new File(this.mContext.getFilesDir(), str);
                ACRA.log.d(ACRA.LOG_TAG, "Deleting file " + str);
                if (!file.delete()) {
                    Log.e(ACRA.LOG_TAG, "Could not delete report : " + file);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void endApplication(Thread thread, Throwable th) {
        boolean z = ACRA.getConfig().mode() == ReportingInteractionMode.SILENT || (ACRA.getConfig().mode() == ReportingInteractionMode.TOAST && ACRA.getConfig().forceCloseDialogAfterToast());
        if ((thread != null) && z && this.mDfltExceptionHandler != null) {
            Log.d(ACRA.LOG_TAG, "Handing Exception on to default ExceptionHandler");
            this.mDfltExceptionHandler.uncaughtException(thread, th);
            return;
        }
        Log.e(ACRA.LOG_TAG, this.mContext.getPackageName() + " fatal error : " + th.getMessage(), th);
        Activity activity = this.lastActivityCreated.get();
        if (activity != null) {
            Log.i(ACRA.LOG_TAG, "Finishing the last Activity prior to killing the Process");
            activity.finish();
            Log.i(ACRA.LOG_TAG, "Finished " + activity.getClass());
            this.lastActivityCreated.clear();
        }
        Process.killProcess(Process.myPid());
        System.exit(10);
    }

    @Deprecated
    public static ErrorReporter getInstance() {
        return ACRA.getErrorReporter();
    }

    private String getReportFileName(CrashReportData crashReportData) {
        Time time = new Time();
        time.setToNow();
        long millis = time.toMillis(false);
        String property = crashReportData.getProperty(ReportField.IS_SILENT);
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(millis);
        sb.append(property != null ? ACRAConstants.SILENT_SUFFIX : "");
        sb.append(ACRAConstants.REPORTFILE_EXTENSION);
        return sb.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0104  */
    /* JADX WARN: Type inference failed for: r0v5, types: [org.acra.ErrorReporter$5] */
    /* JADX WARN: Type inference failed for: r3v8, types: [org.acra.ErrorReporter$4] */
    /* JADX WARN: Type inference failed for: r6v0, types: [org.acra.ErrorReporter$1] */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r7v11, types: [org.acra.ErrorReporter$3] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void report(final org.acra.ErrorReporter.ReportBuilder r15) {
        /*
            Method dump skipped, instructions count: 273
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.acra.ErrorReporter.report(org.acra.ErrorReporter$ReportBuilder):void");
    }

    private void saveCrashReportFile(String str, CrashReportData crashReportData) {
        try {
            Log.d(ACRA.LOG_TAG, "Writing crash report file " + str + ".");
            new CrashReportPersister(this.mContext).store(crashReportData, str);
        } catch (Exception e) {
            Log.e(ACRA.LOG_TAG, "An error occurred while writing the report file...", e);
        }
    }

    @Deprecated
    public void addCustomData(String str, String str2) {
        this.crashReportDataFactory.putCustomData(str, str2);
    }

    public void addReportSender(ReportSender reportSender) {
        this.mReportSenders.add(reportSender);
    }

    public void checkReportsOnApplicationStart() {
        if (ACRA.getConfig().deleteOldUnsentReportsOnApplicationStart()) {
            long j = this.prefs.getInt(ACRA.PREF_LAST_VERSION_NR, 0);
            PackageInfo packageInfo = new PackageManagerWrapper(this.mContext).getPackageInfo();
            if (packageInfo != null) {
                if (((long) packageInfo.versionCode) > j) {
                    deletePendingReports();
                }
                SharedPreferences.Editor editorEdit = this.prefs.edit();
                editorEdit.putInt(ACRA.PREF_LAST_VERSION_NR, packageInfo.versionCode);
                editorEdit.commit();
            }
        }
        ReportingInteractionMode reportingInteractionModeMode = ACRA.getConfig().mode();
        if ((reportingInteractionModeMode == ReportingInteractionMode.NOTIFICATION || reportingInteractionModeMode == ReportingInteractionMode.DIALOG) && ACRA.getConfig().deleteUnapprovedReportsOnApplicationStart()) {
            deletePendingNonApprovedReports(true);
        }
        String[] crashReportFiles = new CrashReportFinder(this.mContext).getCrashReportFiles();
        if (crashReportFiles == null || crashReportFiles.length <= 0) {
            return;
        }
        boolean zContainsOnlySilentOrApprovedReports = containsOnlySilentOrApprovedReports(crashReportFiles);
        if (reportingInteractionModeMode != ReportingInteractionMode.SILENT && reportingInteractionModeMode != ReportingInteractionMode.TOAST) {
            if (!zContainsOnlySilentOrApprovedReports) {
                return;
            }
            if (reportingInteractionModeMode != ReportingInteractionMode.NOTIFICATION && reportingInteractionModeMode != ReportingInteractionMode.DIALOG) {
                return;
            }
        }
        if (reportingInteractionModeMode == ReportingInteractionMode.TOAST && !zContainsOnlySilentOrApprovedReports) {
            ToastSender.sendToast(this.mContext, ACRA.getConfig().resToastText(), 1);
        }
        Log.v(ACRA.LOG_TAG, "About to start ReportSenderWorker from #checkReportOnApplicationStart");
        startSendingReports(false, false);
    }

    public void clearCustomData() {
        this.crashReportDataFactory.clearCustomData();
    }

    void deletePendingNonApprovedReports(boolean z) {
        deletePendingReports(false, true, z ? 1 : 0);
    }

    void deletePendingReports() {
        deletePendingReports(true, true, 0);
    }

    public String getCustomData(String str) {
        return this.crashReportDataFactory.getCustomData(str);
    }

    public void handleException(Throwable th) {
        reportBuilder().exception(th).send();
    }

    public void handleException(Throwable th, boolean z) {
        ReportBuilder reportBuilderException = reportBuilder().exception(th);
        if (z) {
            reportBuilderException.endsApplication();
        }
        reportBuilderException.send();
    }

    public void handleSilentException(Throwable th) {
        String str;
        String str2;
        if (this.enabled) {
            reportBuilder().exception(th).forceSilent().send();
            str = ACRA.LOG_TAG;
            str2 = "ACRA sent Silent report.";
        } else {
            str = ACRA.LOG_TAG;
            str2 = "ACRA is disabled. Silent report not sent.";
        }
        Log.d(str, str2);
    }

    public String putCustomData(String str, String str2) {
        return this.crashReportDataFactory.putCustomData(str, str2);
    }

    public void removeAllReportSenders() {
        this.mReportSenders.clear();
    }

    public String removeCustomData(String str) {
        return this.crashReportDataFactory.removeCustomData(str);
    }

    public void removeReportSender(ReportSender reportSender) {
        this.mReportSenders.remove(reportSender);
    }

    public void removeReportSenders(Class<?> cls) {
        if (ReportSender.class.isAssignableFrom(cls)) {
            for (ReportSender reportSender : this.mReportSenders) {
                if (cls.isInstance(reportSender)) {
                    this.mReportSenders.remove(reportSender);
                }
            }
        }
    }

    public ReportBuilder reportBuilder() {
        return new ReportBuilder();
    }

    public void setDefaultReportSenders() {
        ACRAConfiguration config = ACRA.getConfig();
        Application application = ACRA.getApplication();
        removeAllReportSenders();
        if (!"".equals(config.mailTo())) {
            Log.w(ACRA.LOG_TAG, application.getPackageName() + " reports will be sent by email (if accepted by user).");
            setReportSender(new EmailIntentSender(application));
            return;
        }
        if (new PackageManagerWrapper(application).hasPermission("android.permission.INTERNET")) {
            if (config.formUri() == null || "".equals(config.formUri())) {
                return;
            }
            setReportSender(new HttpSender(ACRA.getConfig().httpMethod(), ACRA.getConfig().reportType(), null));
            return;
        }
        Log.e(ACRA.LOG_TAG, application.getPackageName() + " should be granted permission android.permission.INTERNET if you want your crash reports to be sent. If you don't want to add this permission to your application you can also enable sending reports by email. If this is your will then provide your email address in @ReportsCrashes(mailTo=\"your.account@domain.com\"");
    }

    public void setEnabled(boolean z) {
        String str = ACRA.LOG_TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("ACRA is ");
        sb.append(z ? "enabled" : "disabled");
        sb.append(" for ");
        sb.append(this.mContext.getPackageName());
        Log.i(str, sb.toString());
        this.enabled = z;
    }

    public void setExceptionHandlerInitializer(ExceptionHandlerInitializer exceptionHandlerInitializer) {
        if (exceptionHandlerInitializer == null) {
            exceptionHandlerInitializer = NULL_EXCEPTION_HANDLER_INITIALIZER;
        }
        this.exceptionHandlerInitializer = exceptionHandlerInitializer;
    }

    public void setReportSender(ReportSender reportSender) {
        removeAllReportSenders();
        addReportSender(reportSender);
    }

    SendWorker startSendingReports(boolean z, boolean z2) {
        SendWorker sendWorker = new SendWorker(this.mContext, this.mReportSenders, z, z2);
        sendWorker.start();
        return sendWorker;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        try {
            if (this.enabled) {
                Log.e(ACRA.LOG_TAG, "ACRA caught a " + th.getClass().getSimpleName() + " for " + this.mContext.getPackageName(), th);
                Log.d(ACRA.LOG_TAG, "Building report");
                reportBuilder().uncaughtExceptionThread(thread).exception(th).endsApplication().send();
                return;
            }
            if (this.mDfltExceptionHandler != null) {
                Log.e(ACRA.LOG_TAG, "ACRA is disabled for " + this.mContext.getPackageName() + " - forwarding uncaught Exception on to default ExceptionHandler");
                this.mDfltExceptionHandler.uncaughtException(thread, th);
                return;
            }
            Log.e(ACRA.LOG_TAG, "ACRA is disabled for " + this.mContext.getPackageName() + " - no default ExceptionHandler");
            Log.e(ACRA.LOG_TAG, "ACRA caught a " + th.getClass().getSimpleName() + " for " + this.mContext.getPackageName(), th);
        } catch (Throwable unused) {
            if (this.mDfltExceptionHandler != null) {
                this.mDfltExceptionHandler.uncaughtException(thread, th);
            }
        }
    }
}
