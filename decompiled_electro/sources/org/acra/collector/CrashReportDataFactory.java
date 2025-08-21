package org.acra.collector;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.acra.ACRA;
import org.acra.ACRAConfiguration;
import org.acra.ACRAConstants;
import org.acra.ReportField;
import org.acra.util.Installation;
import org.acra.util.PackageManagerWrapper;
import org.acra.util.ReportUtils;

/* loaded from: classes.dex */
public final class CrashReportDataFactory {
    private final Time appStartDate;
    private final Context context;
    private final Map<String, String> customParameters = new LinkedHashMap();
    private final String initialConfiguration;
    private final SharedPreferences prefs;

    public CrashReportDataFactory(Context context, SharedPreferences sharedPreferences, Time time, String str) {
        this.context = context;
        this.prefs = sharedPreferences;
        this.appStartDate = time;
        this.initialConfiguration = str;
    }

    private String createCustomInfoString(Map<String, String> map) {
        Map<String, String> map2 = this.customParameters;
        if (map != null) {
            HashMap map3 = new HashMap(map2);
            map3.putAll(map);
            map2 = map3;
        }
        StringBuilder sb = new StringBuilder();
        for (String str : map2.keySet()) {
            String strReplaceAll = map2.get(str);
            sb.append(str);
            sb.append(" = ");
            if (strReplaceAll != null) {
                strReplaceAll = strReplaceAll.replaceAll("\n", "\\\\n");
            }
            sb.append(strReplaceAll);
            sb.append("\n");
        }
        return sb.toString();
    }

    private Class<?> getBuildConfigClass() throws ClassNotFoundException {
        Class<?> clsBuildConfigClass = ACRA.getConfig().buildConfigClass();
        if (clsBuildConfigClass != null && !clsBuildConfigClass.equals(Object.class)) {
            return clsBuildConfigClass;
        }
        String str = this.context.getClass().getPackage().getName() + ".BuildConfig";
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            Log.e(ACRA.LOG_TAG, "Not adding buildConfig to log. Class Not found : " + str + ". Please configure 'buildConfigClass' in your ACRA config");
            throw e;
        }
    }

    private List<ReportField> getReportFields() {
        ACRAConfiguration config = ACRA.getConfig();
        ReportField[] reportFieldArrCustomReportContent = config.customReportContent();
        if (reportFieldArrCustomReportContent.length != 0) {
            Log.d(ACRA.LOG_TAG, "Using custom Report Fields");
        } else if (config.mailTo() == null || "".equals(config.mailTo())) {
            Log.d(ACRA.LOG_TAG, "Using default Report Fields");
            reportFieldArrCustomReportContent = ACRAConstants.DEFAULT_REPORT_FIELDS;
        } else {
            Log.d(ACRA.LOG_TAG, "Using default Mail Report Fields");
            reportFieldArrCustomReportContent = ACRAConstants.DEFAULT_MAIL_REPORT_FIELDS;
        }
        return Arrays.asList(reportFieldArrCustomReportContent);
    }

    private String getStackTrace(String str, Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        if (str != null && !str.isEmpty()) {
            printWriter.println(str);
        }
        while (th != null) {
            th.printStackTrace(printWriter);
            th = th.getCause();
        }
        String string = stringWriter.toString();
        printWriter.close();
        return string;
    }

    private String getStackTraceHash(Throwable th) {
        StringBuilder sb = new StringBuilder();
        while (th != null) {
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                sb.append(stackTraceElement.getClassName());
                sb.append(stackTraceElement.getMethodName());
            }
            th = th.getCause();
        }
        return Integer.toHexString(sb.toString().hashCode());
    }

    public void clearCustomData() {
        this.customParameters.clear();
    }

    public CrashReportData createCrashData(String str, Throwable th, Map<String, String> map, boolean z, Thread thread) {
        String deviceId;
        CrashReportData crashReportData = new CrashReportData();
        try {
            List<ReportField> reportFields = getReportFields();
            crashReportData.put((CrashReportData) ReportField.STACK_TRACE, (ReportField) getStackTrace(str, th));
            crashReportData.put((CrashReportData) ReportField.USER_APP_START_DATE, (ReportField) ReportUtils.getTimeString(this.appStartDate));
            if (z) {
                crashReportData.put((CrashReportData) ReportField.IS_SILENT, (ReportField) "true");
            }
            if (reportFields.contains(ReportField.STACK_TRACE_HASH)) {
                crashReportData.put((CrashReportData) ReportField.STACK_TRACE_HASH, (ReportField) getStackTraceHash(th));
            }
            if (reportFields.contains(ReportField.REPORT_ID)) {
                crashReportData.put((CrashReportData) ReportField.REPORT_ID, (ReportField) UUID.randomUUID().toString());
            }
            if (reportFields.contains(ReportField.INSTALLATION_ID)) {
                crashReportData.put((CrashReportData) ReportField.INSTALLATION_ID, (ReportField) Installation.id(this.context));
            }
            if (reportFields.contains(ReportField.INITIAL_CONFIGURATION)) {
                crashReportData.put((CrashReportData) ReportField.INITIAL_CONFIGURATION, (ReportField) this.initialConfiguration);
            }
            if (reportFields.contains(ReportField.CRASH_CONFIGURATION)) {
                crashReportData.put((CrashReportData) ReportField.CRASH_CONFIGURATION, (ReportField) ConfigurationCollector.collectConfiguration(this.context));
            }
            if (!(th instanceof OutOfMemoryError) && reportFields.contains(ReportField.DUMPSYS_MEMINFO)) {
                crashReportData.put((CrashReportData) ReportField.DUMPSYS_MEMINFO, (ReportField) DumpSysCollector.collectMemInfo());
            }
            if (reportFields.contains(ReportField.PACKAGE_NAME)) {
                crashReportData.put((CrashReportData) ReportField.PACKAGE_NAME, (ReportField) this.context.getPackageName());
            }
            if (reportFields.contains(ReportField.BUILD)) {
                crashReportData.put((CrashReportData) ReportField.BUILD, (ReportField) (ReflectionCollector.collectConstants(Build.class) + ReflectionCollector.collectConstants(Build.VERSION.class, "VERSION")));
            }
            if (reportFields.contains(ReportField.PHONE_MODEL)) {
                crashReportData.put((CrashReportData) ReportField.PHONE_MODEL, (ReportField) Build.MODEL);
            }
            if (reportFields.contains(ReportField.ANDROID_VERSION)) {
                crashReportData.put((CrashReportData) ReportField.ANDROID_VERSION, (ReportField) Build.VERSION.RELEASE);
            }
            if (reportFields.contains(ReportField.BRAND)) {
                crashReportData.put((CrashReportData) ReportField.BRAND, (ReportField) Build.BRAND);
            }
            if (reportFields.contains(ReportField.PRODUCT)) {
                crashReportData.put((CrashReportData) ReportField.PRODUCT, (ReportField) Build.PRODUCT);
            }
            if (reportFields.contains(ReportField.TOTAL_MEM_SIZE)) {
                crashReportData.put((CrashReportData) ReportField.TOTAL_MEM_SIZE, (ReportField) Long.toString(ReportUtils.getTotalInternalMemorySize()));
            }
            if (reportFields.contains(ReportField.AVAILABLE_MEM_SIZE)) {
                crashReportData.put((CrashReportData) ReportField.AVAILABLE_MEM_SIZE, (ReportField) Long.toString(ReportUtils.getAvailableInternalMemorySize()));
            }
            if (reportFields.contains(ReportField.FILE_PATH)) {
                crashReportData.put((CrashReportData) ReportField.FILE_PATH, (ReportField) ReportUtils.getApplicationFilePath(this.context));
            }
            if (reportFields.contains(ReportField.DISPLAY)) {
                crashReportData.put((CrashReportData) ReportField.DISPLAY, (ReportField) DisplayManagerCollector.collectDisplays(this.context));
            }
            if (reportFields.contains(ReportField.USER_CRASH_DATE)) {
                Time time = new Time();
                time.setToNow();
                crashReportData.put((CrashReportData) ReportField.USER_CRASH_DATE, (ReportField) ReportUtils.getTimeString(time));
            }
            if (reportFields.contains(ReportField.CUSTOM_DATA)) {
                crashReportData.put((CrashReportData) ReportField.CUSTOM_DATA, (ReportField) createCustomInfoString(map));
            }
            if (reportFields.contains(ReportField.BUILD_CONFIG)) {
                try {
                    crashReportData.put((CrashReportData) ReportField.BUILD_CONFIG, (ReportField) ReflectionCollector.collectConstants(getBuildConfigClass()));
                } catch (ClassNotFoundException unused) {
                }
            }
            if (reportFields.contains(ReportField.USER_EMAIL)) {
                crashReportData.put((CrashReportData) ReportField.USER_EMAIL, (ReportField) this.prefs.getString(ACRA.PREF_USER_EMAIL_ADDRESS, "N/A"));
            }
            if (reportFields.contains(ReportField.DEVICE_FEATURES)) {
                crashReportData.put((CrashReportData) ReportField.DEVICE_FEATURES, (ReportField) DeviceFeaturesCollector.getFeatures(this.context));
            }
            if (reportFields.contains(ReportField.ENVIRONMENT)) {
                crashReportData.put((CrashReportData) ReportField.ENVIRONMENT, (ReportField) ReflectionCollector.collectStaticGettersResults(Environment.class));
            }
            if (reportFields.contains(ReportField.SETTINGS_SYSTEM)) {
                crashReportData.put((CrashReportData) ReportField.SETTINGS_SYSTEM, (ReportField) SettingsCollector.collectSystemSettings(this.context));
            }
            if (reportFields.contains(ReportField.SETTINGS_SECURE)) {
                crashReportData.put((CrashReportData) ReportField.SETTINGS_SECURE, (ReportField) SettingsCollector.collectSecureSettings(this.context));
            }
            if (reportFields.contains(ReportField.SETTINGS_GLOBAL)) {
                crashReportData.put((CrashReportData) ReportField.SETTINGS_GLOBAL, (ReportField) SettingsCollector.collectGlobalSettings(this.context));
            }
            if (reportFields.contains(ReportField.SHARED_PREFERENCES)) {
                crashReportData.put((CrashReportData) ReportField.SHARED_PREFERENCES, (ReportField) SharedPreferencesCollector.collect(this.context));
            }
            PackageManagerWrapper packageManagerWrapper = new PackageManagerWrapper(this.context);
            PackageInfo packageInfo = packageManagerWrapper.getPackageInfo();
            if (packageInfo != null) {
                if (reportFields.contains(ReportField.APP_VERSION_CODE)) {
                    crashReportData.put((CrashReportData) ReportField.APP_VERSION_CODE, (ReportField) Integer.toString(packageInfo.versionCode));
                }
                if (reportFields.contains(ReportField.APP_VERSION_NAME)) {
                    crashReportData.put((CrashReportData) ReportField.APP_VERSION_NAME, (ReportField) (packageInfo.versionName != null ? packageInfo.versionName : "not set"));
                }
            } else {
                crashReportData.put((CrashReportData) ReportField.APP_VERSION_NAME, (ReportField) "Package info unavailable");
            }
            if (reportFields.contains(ReportField.DEVICE_ID) && this.prefs.getBoolean(ACRA.PREF_ENABLE_DEVICE_ID, true) && packageManagerWrapper.hasPermission("android.permission.READ_PHONE_STATE") && (deviceId = ReportUtils.getDeviceId(this.context)) != null) {
                crashReportData.put((CrashReportData) ReportField.DEVICE_ID, (ReportField) deviceId);
            }
            if (!(this.prefs.getBoolean(ACRA.PREF_ENABLE_SYSTEM_LOGS, true) && packageManagerWrapper.hasPermission("android.permission.READ_LOGS")) && Compatibility.getAPILevel() < 16) {
                Log.i(ACRA.LOG_TAG, "READ_LOGS not allowed. ACRA will not include LogCat and DropBox data.");
            } else {
                Log.i(ACRA.LOG_TAG, "READ_LOGS granted! ACRA can include LogCat and DropBox data.");
                if (reportFields.contains(ReportField.LOGCAT)) {
                    crashReportData.put((CrashReportData) ReportField.LOGCAT, (ReportField) LogCatCollector.collectLogCat(null));
                }
                if (reportFields.contains(ReportField.EVENTSLOG)) {
                    crashReportData.put((CrashReportData) ReportField.EVENTSLOG, (ReportField) LogCatCollector.collectLogCat("events"));
                }
                if (reportFields.contains(ReportField.RADIOLOG)) {
                    crashReportData.put((CrashReportData) ReportField.RADIOLOG, (ReportField) LogCatCollector.collectLogCat("radio"));
                }
                if (reportFields.contains(ReportField.DROPBOX)) {
                    crashReportData.put((CrashReportData) ReportField.DROPBOX, (ReportField) DropBoxCollector.read(this.context, ACRA.getConfig().additionalDropBoxTags()));
                }
            }
            if (reportFields.contains(ReportField.APPLICATION_LOG)) {
                try {
                    crashReportData.put((CrashReportData) ReportField.APPLICATION_LOG, (ReportField) LogFileCollector.collectLogFile(this.context, ACRA.getConfig().applicationLogFile(), ACRA.getConfig().applicationLogFileLines()));
                } catch (IOException e) {
                    Log.e(ACRA.LOG_TAG, "Error while reading application log file " + ACRA.getConfig().applicationLogFile(), e);
                }
            }
            if (reportFields.contains(ReportField.MEDIA_CODEC_LIST)) {
                crashReportData.put((CrashReportData) ReportField.MEDIA_CODEC_LIST, (ReportField) MediaCodecListCollector.collecMediaCodecList());
            }
            if (reportFields.contains(ReportField.THREAD_DETAILS)) {
                crashReportData.put((CrashReportData) ReportField.THREAD_DETAILS, (ReportField) ThreadCollector.collect(thread));
            }
            if (reportFields.contains(ReportField.USER_IP)) {
                crashReportData.put((CrashReportData) ReportField.USER_IP, (ReportField) ReportUtils.getLocalIpAddress());
                return crashReportData;
            }
        } catch (RuntimeException e2) {
            Log.e(ACRA.LOG_TAG, "Error while retrieving crash data", e2);
        }
        return crashReportData;
    }

    public String getCustomData(String str) {
        return this.customParameters.get(str);
    }

    public String putCustomData(String str, String str2) {
        return this.customParameters.put(str, str2);
    }

    public String removeCustomData(String str) {
        return this.customParameters.remove(str);
    }
}
