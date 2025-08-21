package org.acra;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import org.acra.annotation.ReportsCrashes;
import org.acra.log.ACRALog;
import org.acra.log.AndroidLogDelegate;

/* loaded from: classes.dex */
public class ACRA {
    public static final boolean DEV_LOGGING = false;
    public static final String LOG_TAG = "ACRA";
    public static final String PREF_ALWAYS_ACCEPT = "acra.alwaysaccept";
    public static final String PREF_DISABLE_ACRA = "acra.disable";
    public static final String PREF_ENABLE_ACRA = "acra.enable";
    public static final String PREF_ENABLE_DEVICE_ID = "acra.deviceid.enable";
    public static final String PREF_ENABLE_SYSTEM_LOGS = "acra.syslog.enable";
    public static final String PREF_LAST_VERSION_NR = "acra.lastVersionNr";
    public static final String PREF_USER_EMAIL_ADDRESS = "acra.user.email";
    private static ACRAConfiguration configProxy;
    private static ErrorReporter errorReporterSingleton;
    public static ACRALog log = new AndroidLogDelegate();
    private static Application mApplication;
    private static SharedPreferences.OnSharedPreferenceChangeListener mPrefListener;

    static void checkCrashResources(ReportsCrashes reportsCrashes) throws ACRAConfigurationException {
        switch (reportsCrashes.mode()) {
            case TOAST:
                if (reportsCrashes.resToastText() == 0) {
                    throw new ACRAConfigurationException("TOAST mode: you have to define the resToastText parameter in your application @ReportsCrashes() annotation.");
                }
                return;
            case NOTIFICATION:
                if (reportsCrashes.resNotifTickerText() == 0 || reportsCrashes.resNotifTitle() == 0 || reportsCrashes.resNotifText() == 0) {
                    throw new ACRAConfigurationException("NOTIFICATION mode: you have to define at least the resNotifTickerText, resNotifTitle, resNotifText parameters in your application @ReportsCrashes() annotation.");
                }
                if (CrashReportDialog.class.equals(reportsCrashes.reportDialogClass()) && reportsCrashes.resDialogText() == 0) {
                    throw new ACRAConfigurationException("NOTIFICATION mode: using the (default) CrashReportDialog requires you have to define the resDialogText parameter in your application @ReportsCrashes() annotation.");
                }
                return;
            case DIALOG:
                if (CrashReportDialog.class.equals(reportsCrashes.reportDialogClass()) && reportsCrashes.resDialogText() == 0) {
                    throw new ACRAConfigurationException("DIALOG mode: using the (default) CrashReportDialog requires you to define the resDialogText parameter in your application @ReportsCrashes() annotation.");
                }
                return;
            default:
                return;
        }
    }

    public static SharedPreferences getACRASharedPreferences() {
        ACRAConfiguration config = getConfig();
        return !"".equals(config.sharedPreferencesName()) ? mApplication.getSharedPreferences(config.sharedPreferencesName(), config.sharedPreferencesMode()) : PreferenceManager.getDefaultSharedPreferences(mApplication);
    }

    static Application getApplication() {
        return mApplication;
    }

    public static ACRAConfiguration getConfig() {
        if (configProxy == null) {
            if (mApplication == null) {
                log.w(LOG_TAG, "Calling ACRA.getConfig() before ACRA.init() gives you an empty configuration instance. You might prefer calling ACRA.getNewDefaultConfig(Application) to get an instance with default values taken from a @ReportsCrashes annotation.");
            }
            configProxy = getNewDefaultConfig(mApplication);
        }
        return configProxy;
    }

    public static ErrorReporter getErrorReporter() {
        if (errorReporterSingleton == null) {
            throw new IllegalStateException("Cannot access ErrorReporter before ACRA#init");
        }
        return errorReporterSingleton;
    }

    public static ACRAConfiguration getNewDefaultConfig(Application application) {
        return application != null ? new ACRAConfiguration((ReportsCrashes) application.getClass().getAnnotation(ReportsCrashes.class)) : new ACRAConfiguration(null);
    }

    public static void init(Application application) {
        ReportsCrashes reportsCrashes = (ReportsCrashes) application.getClass().getAnnotation(ReportsCrashes.class);
        if (reportsCrashes != null) {
            init(application, new ACRAConfiguration(reportsCrashes));
            return;
        }
        log.e(LOG_TAG, "ACRA#init called but no ReportsCrashes annotation on Application " + application.getPackageName());
    }

    public static void init(Application application, ACRAConfiguration aCRAConfiguration) {
        init(application, aCRAConfiguration, true);
    }

    public static void init(Application application, ACRAConfiguration aCRAConfiguration, boolean z) {
        if (mApplication != null) {
            log.w(LOG_TAG, "ACRA#init called more than once. Won't do anything more.");
            return;
        }
        mApplication = application;
        if (aCRAConfiguration == null) {
            log.e(LOG_TAG, "ACRA#init called but no ACRAConfiguration provided");
            return;
        }
        setConfig(aCRAConfiguration);
        SharedPreferences aCRASharedPreferences = getACRASharedPreferences();
        try {
            checkCrashResources(aCRAConfiguration);
            log.d(LOG_TAG, "ACRA is enabled for " + mApplication.getPackageName() + ", intializing...");
            ErrorReporter errorReporter = new ErrorReporter(mApplication, aCRASharedPreferences, shouldDisableACRA(aCRASharedPreferences) ^ true);
            errorReporter.setDefaultReportSenders();
            errorReporterSingleton = errorReporter;
            if (z) {
                errorReporter.checkReportsOnApplicationStart();
            }
        } catch (ACRAConfigurationException e) {
            log.w(LOG_TAG, "Error : ", e);
        }
        mPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: org.acra.ACRA.1
            @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
                if (ACRA.PREF_DISABLE_ACRA.equals(str) || ACRA.PREF_ENABLE_ACRA.equals(str)) {
                    ACRA.getErrorReporter().setEnabled(!ACRA.shouldDisableACRA(sharedPreferences));
                }
            }
        };
        aCRASharedPreferences.registerOnSharedPreferenceChangeListener(mPrefListener);
    }

    static boolean isDebuggable() {
        try {
            return (mApplication.getPackageManager().getApplicationInfo(mApplication.getPackageName(), 0).flags & 2) > 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static void setConfig(ACRAConfiguration aCRAConfiguration) {
        configProxy = aCRAConfiguration;
    }

    public static void setLog(ACRALog aCRALog) {
        log = aCRALog;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean shouldDisableACRA(SharedPreferences sharedPreferences) {
        try {
            return sharedPreferences.getBoolean(PREF_DISABLE_ACRA, !sharedPreferences.getBoolean(PREF_ENABLE_ACRA, true));
        } catch (Exception unused) {
            return false;
        }
    }
}
