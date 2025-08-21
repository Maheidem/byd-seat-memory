package org.acra;

/* loaded from: classes.dex */
public enum ReportField {
    REPORT_ID,
    APP_VERSION_CODE,
    APP_VERSION_NAME,
    PACKAGE_NAME,
    FILE_PATH,
    PHONE_MODEL,
    ANDROID_VERSION,
    BUILD { // from class: org.acra.ReportField.1
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    BRAND,
    PRODUCT,
    TOTAL_MEM_SIZE,
    AVAILABLE_MEM_SIZE,
    BUILD_CONFIG { // from class: org.acra.ReportField.2
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    CUSTOM_DATA { // from class: org.acra.ReportField.3
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    STACK_TRACE,
    STACK_TRACE_HASH,
    INITIAL_CONFIGURATION { // from class: org.acra.ReportField.4
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    CRASH_CONFIGURATION { // from class: org.acra.ReportField.5
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    DISPLAY { // from class: org.acra.ReportField.6
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    USER_COMMENT,
    USER_APP_START_DATE,
    USER_CRASH_DATE,
    DUMPSYS_MEMINFO,
    DROPBOX,
    LOGCAT,
    EVENTSLOG,
    RADIOLOG,
    IS_SILENT,
    DEVICE_ID,
    INSTALLATION_ID,
    USER_EMAIL,
    DEVICE_FEATURES { // from class: org.acra.ReportField.7
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    ENVIRONMENT { // from class: org.acra.ReportField.8
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    SETTINGS_SYSTEM { // from class: org.acra.ReportField.9
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    SETTINGS_SECURE { // from class: org.acra.ReportField.10
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    SETTINGS_GLOBAL { // from class: org.acra.ReportField.11
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    SHARED_PREFERENCES { // from class: org.acra.ReportField.12
        @Override // org.acra.ReportField
        public boolean containsKeyValuePairs() {
            return true;
        }
    },
    APPLICATION_LOG,
    MEDIA_CODEC_LIST,
    THREAD_DETAILS,
    USER_IP;

    public boolean containsKeyValuePairs() {
        return false;
    }
}
