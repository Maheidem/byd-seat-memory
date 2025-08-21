package android.support.v7.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
class ActivityChooserModel extends DataSetObservable {
    static final String ATTRIBUTE_ACTIVITY = "activity";
    static final String ATTRIBUTE_TIME = "time";
    static final String ATTRIBUTE_WEIGHT = "weight";
    static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    static final String LOG_TAG = "ActivityChooserModel";
    static final String TAG_HISTORICAL_RECORD = "historical-record";
    static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private OnChooseActivityListener mActivityChoserModelPolicy;
    final Context mContext;
    final String mHistoryFileName;
    private Intent mIntent;
    private static final Object sRegistryLock = new Object();
    private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
    private final Object mInstanceLock = new Object();
    private final List<ActivityResolveInfo> mActivities = new ArrayList();
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
    private ActivitySorter mActivitySorter = new DefaultSorter();
    private int mHistoryMaxSize = 50;
    boolean mCanReadHistoricalData = true;
    private boolean mReadShareHistoryCalled = false;
    private boolean mHistoricalRecordsChanged = true;
    private boolean mReloadActivities = false;

    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel);
    }

    public final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
        }

        @Override // java.lang.Comparable
        public int compareTo(ActivityResolveInfo activityResolveInfo) {
            return Float.floatToIntBits(activityResolveInfo.weight) - Float.floatToIntBits(this.weight);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && getClass() == obj.getClass() && Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj).weight);
        }

        public int hashCode() {
            return 31 + Float.floatToIntBits(this.weight);
        }

        public String toString() {
            return "[resolveInfo:" + this.resolveInfo.toString() + "; weight:" + new BigDecimal(this.weight) + "]";
        }
    }

    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    private final class DefaultSorter implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap = new HashMap();

        DefaultSorter() {
        }

        @Override // android.support.v7.widget.ActivityChooserModel.ActivitySorter
        public void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2) {
            Map<ComponentName, ActivityResolveInfo> map = this.mPackageNameToActivityMap;
            map.clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ActivityResolveInfo activityResolveInfo = list.get(i);
                activityResolveInfo.weight = 0.0f;
                map.put(new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), activityResolveInfo);
            }
            float f = ActivityChooserModel.DEFAULT_HISTORICAL_RECORD_WEIGHT;
            for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                HistoricalRecord historicalRecord = list2.get(size2);
                ActivityResolveInfo activityResolveInfo2 = map.get(historicalRecord.activity);
                if (activityResolveInfo2 != null) {
                    activityResolveInfo2.weight += historicalRecord.weight * f;
                    f *= WEIGHT_DECAY_COEFFICIENT;
                }
            }
            Collections.sort(list);
        }
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(ComponentName componentName, long j, float f) {
            this.activity = componentName;
            this.time = j;
            this.weight = f;
        }

        public HistoricalRecord(String str, long j, float f) {
            this(ComponentName.unflattenFromString(str), j, f);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            HistoricalRecord historicalRecord = (HistoricalRecord) obj;
            if (this.activity == null) {
                if (historicalRecord.activity != null) {
                    return false;
                }
            } else if (!this.activity.equals(historicalRecord.activity)) {
                return false;
            }
            return this.time == historicalRecord.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(historicalRecord.weight);
        }

        public int hashCode() {
            return (31 * ((((this.activity == null ? 0 : this.activity.hashCode()) + 31) * 31) + ((int) (this.time ^ (this.time >>> 32))))) + Float.floatToIntBits(this.weight);
        }

        public String toString() {
            return "[; activity:" + this.activity + "; time:" + this.time + "; weight:" + new BigDecimal(this.weight) + "]";
        }
    }

    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
        PersistHistoryAsyncTask() {
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Object... objArr) throws IOException {
            List list = (List) objArr[0];
            String str = (String) objArr[1];
            try {
                FileOutputStream fileOutputStreamOpenFileOutput = ActivityChooserModel.this.mContext.openFileOutput(str, 0);
                XmlSerializer xmlSerializerNewSerializer = Xml.newSerializer();
                try {
                    try {
                        try {
                            xmlSerializerNewSerializer.setOutput(fileOutputStreamOpenFileOutput, null);
                            xmlSerializerNewSerializer.startDocument("UTF-8", true);
                            xmlSerializerNewSerializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                            int size = list.size();
                            for (int i = 0; i < size; i++) {
                                HistoricalRecord historicalRecord = (HistoricalRecord) list.remove(0);
                                xmlSerializerNewSerializer.startTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                                xmlSerializerNewSerializer.attribute(null, ActivityChooserModel.ATTRIBUTE_ACTIVITY, historicalRecord.activity.flattenToString());
                                xmlSerializerNewSerializer.attribute(null, ActivityChooserModel.ATTRIBUTE_TIME, String.valueOf(historicalRecord.time));
                                xmlSerializerNewSerializer.attribute(null, ActivityChooserModel.ATTRIBUTE_WEIGHT, String.valueOf(historicalRecord.weight));
                                xmlSerializerNewSerializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORD);
                            }
                            xmlSerializerNewSerializer.endTag(null, ActivityChooserModel.TAG_HISTORICAL_RECORDS);
                            xmlSerializerNewSerializer.endDocument();
                            ActivityChooserModel.this.mCanReadHistoricalData = true;
                        } catch (Throwable th) {
                            ActivityChooserModel.this.mCanReadHistoricalData = true;
                            if (fileOutputStreamOpenFileOutput != null) {
                                try {
                                    fileOutputStreamOpenFileOutput.close();
                                } catch (IOException unused) {
                                }
                            }
                            throw th;
                        }
                    } catch (IllegalStateException e) {
                        Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, e);
                        ActivityChooserModel.this.mCanReadHistoricalData = true;
                        if (fileOutputStreamOpenFileOutput != null) {
                        }
                    }
                } catch (IOException e2) {
                    Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, e2);
                    ActivityChooserModel.this.mCanReadHistoricalData = true;
                    if (fileOutputStreamOpenFileOutput != null) {
                    }
                } catch (IllegalArgumentException e3) {
                    Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + ActivityChooserModel.this.mHistoryFileName, e3);
                    ActivityChooserModel.this.mCanReadHistoricalData = true;
                    if (fileOutputStreamOpenFileOutput != null) {
                    }
                }
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                        return null;
                    } catch (IOException unused2) {
                    }
                }
                return null;
            } catch (FileNotFoundException e4) {
                Log.e(ActivityChooserModel.LOG_TAG, "Error writing historical record file: " + str, e4);
                return null;
            }
        }
    }

    private ActivityChooserModel(Context context, String str) {
        this.mContext = context.getApplicationContext();
        if (TextUtils.isEmpty(str) || str.endsWith(HISTORY_FILE_EXTENSION)) {
            this.mHistoryFileName = str;
            return;
        }
        this.mHistoryFileName = str + HISTORY_FILE_EXTENSION;
    }

    private boolean addHistoricalRecord(HistoricalRecord historicalRecord) {
        boolean zAdd = this.mHistoricalRecords.add(historicalRecord);
        if (zAdd) {
            this.mHistoricalRecordsChanged = true;
            pruneExcessiveHistoricalRecordsIfNeeded();
            persistHistoricalDataIfNeeded();
            sortActivitiesIfNeeded();
            notifyChanged();
        }
        return zAdd;
    }

    private void ensureConsistentState() {
        boolean zLoadActivitiesIfNeeded = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if (zLoadActivitiesIfNeeded) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    public static ActivityChooserModel get(Context context, String str) {
        ActivityChooserModel activityChooserModel;
        synchronized (sRegistryLock) {
            activityChooserModel = sDataModelRegistry.get(str);
            if (activityChooserModel == null) {
                activityChooserModel = new ActivityChooserModel(context, str);
                sDataModelRegistry.put(str, activityChooserModel);
            }
        }
        return activityChooserModel;
    }

    private boolean loadActivitiesIfNeeded() {
        if (!this.mReloadActivities || this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        List<ResolveInfo> listQueryIntentActivities = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int size = listQueryIntentActivities.size();
        for (int i = 0; i < size; i++) {
            this.mActivities.add(new ActivityResolveInfo(listQueryIntentActivities.get(i)));
        }
        return true;
    }

    private void persistHistoricalDataIfNeeded() {
        if (!this.mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        }
        if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (TextUtils.isEmpty(this.mHistoryFileName)) {
                return;
            }
            AsyncTaskCompat.executeParallel(new PersistHistoryAsyncTask(), new ArrayList(this.mHistoricalRecords), this.mHistoryFileName);
        }
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() {
        int size = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if (size <= 0) {
            return;
        }
        this.mHistoricalRecordsChanged = true;
        for (int i = 0; i < size; i++) {
            this.mHistoricalRecords.remove(0);
        }
    }

    private boolean readHistoricalDataIfNeeded() throws IOException {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
            return false;
        }
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003e, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0041, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void readHistoricalDataImpl() throws java.io.IOException {
        /*
            r9 = this;
            android.content.Context r0 = r9.mContext     // Catch: java.io.FileNotFoundException -> Lc2
            java.lang.String r1 = r9.mHistoryFileName     // Catch: java.io.FileNotFoundException -> Lc2
            java.io.FileInputStream r0 = r0.openFileInput(r1)     // Catch: java.io.FileNotFoundException -> Lc2
            org.xmlpull.v1.XmlPullParser r1 = android.util.Xml.newPullParser()     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            java.lang.String r2 = "UTF-8"
            r1.setInput(r0, r2)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            r2 = 0
        L12:
            r3 = 1
            if (r2 == r3) goto L1d
            r4 = 2
            if (r2 == r4) goto L1d
            int r2 = r1.next()     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            goto L12
        L1d:
            java.lang.String r2 = "historical-records"
            java.lang.String r4 = r1.getName()     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            boolean r2 = r2.equals(r4)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            if (r2 != 0) goto L31
            org.xmlpull.v1.XmlPullParserException r1 = new org.xmlpull.v1.XmlPullParserException     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            java.lang.String r2 = "Share records file does not start with historical-records tag."
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            throw r1     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
        L31:
            java.util.List<android.support.v7.widget.ActivityChooserModel$HistoricalRecord> r2 = r9.mHistoricalRecords     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            r2.clear()     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
        L36:
            int r4 = r1.next()     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            if (r4 != r3) goto L42
            if (r0 == 0) goto Lbb
        L3e:
            r0.close()     // Catch: java.io.IOException -> Lbb
            return
        L42:
            r5 = 3
            if (r4 == r5) goto L36
            r5 = 4
            if (r4 != r5) goto L49
            goto L36
        L49:
            java.lang.String r4 = r1.getName()     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            java.lang.String r5 = "historical-record"
            boolean r4 = r5.equals(r4)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            if (r4 != 0) goto L5d
            org.xmlpull.v1.XmlPullParserException r1 = new org.xmlpull.v1.XmlPullParserException     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            java.lang.String r2 = "Share records file not well-formed."
            r1.<init>(r2)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            throw r1     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
        L5d:
            java.lang.String r4 = "activity"
            r5 = 0
            java.lang.String r4 = r1.getAttributeValue(r5, r4)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            java.lang.String r6 = "time"
            java.lang.String r6 = r1.getAttributeValue(r5, r6)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            long r6 = java.lang.Long.parseLong(r6)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            java.lang.String r8 = "weight"
            java.lang.String r5 = r1.getAttributeValue(r5, r8)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            float r5 = java.lang.Float.parseFloat(r5)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            android.support.v7.widget.ActivityChooserModel$HistoricalRecord r8 = new android.support.v7.widget.ActivityChooserModel$HistoricalRecord     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            r8.<init>(r4, r6, r5)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            r2.add(r8)     // Catch: java.lang.Throwable -> L81 java.io.IOException -> L83 org.xmlpull.v1.XmlPullParserException -> L9f
            goto L36
        L81:
            r9 = move-exception
            goto Lbc
        L83:
            r1 = move-exception
            java.lang.String r2 = android.support.v7.widget.ActivityChooserModel.LOG_TAG     // Catch: java.lang.Throwable -> L81
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L81
            r3.<init>()     // Catch: java.lang.Throwable -> L81
            java.lang.String r4 = "Error reading historical recrod file: "
            r3.append(r4)     // Catch: java.lang.Throwable -> L81
            java.lang.String r9 = r9.mHistoryFileName     // Catch: java.lang.Throwable -> L81
            r3.append(r9)     // Catch: java.lang.Throwable -> L81
            java.lang.String r9 = r3.toString()     // Catch: java.lang.Throwable -> L81
            android.util.Log.e(r2, r9, r1)     // Catch: java.lang.Throwable -> L81
            if (r0 == 0) goto Lbb
            goto L3e
        L9f:
            r1 = move-exception
            java.lang.String r2 = android.support.v7.widget.ActivityChooserModel.LOG_TAG     // Catch: java.lang.Throwable -> L81
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L81
            r3.<init>()     // Catch: java.lang.Throwable -> L81
            java.lang.String r4 = "Error reading historical recrod file: "
            r3.append(r4)     // Catch: java.lang.Throwable -> L81
            java.lang.String r9 = r9.mHistoryFileName     // Catch: java.lang.Throwable -> L81
            r3.append(r9)     // Catch: java.lang.Throwable -> L81
            java.lang.String r9 = r3.toString()     // Catch: java.lang.Throwable -> L81
            android.util.Log.e(r2, r9, r1)     // Catch: java.lang.Throwable -> L81
            if (r0 == 0) goto Lbb
            goto L3e
        Lbb:
            return
        Lbc:
            if (r0 == 0) goto Lc1
            r0.close()     // Catch: java.io.IOException -> Lc1
        Lc1:
            throw r9
        Lc2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActivityChooserModel.readHistoricalDataImpl():void");
    }

    private boolean sortActivitiesIfNeeded() {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false;
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    public Intent chooseActivity(int i) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == null) {
                return null;
            }
            ensureConsistentState();
            ActivityResolveInfo activityResolveInfo = this.mActivities.get(i);
            ComponentName componentName = new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name);
            Intent intent = new Intent(this.mIntent);
            intent.setComponent(componentName);
            if (this.mActivityChoserModelPolicy != null) {
                if (this.mActivityChoserModelPolicy.onChooseActivity(this, new Intent(intent))) {
                    return null;
                }
            }
            addHistoricalRecord(new HistoricalRecord(componentName, System.currentTimeMillis(), DEFAULT_HISTORICAL_RECORD_WEIGHT));
            return intent;
        }
    }

    public ResolveInfo getActivity(int i) {
        ResolveInfo resolveInfo;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            resolveInfo = this.mActivities.get(i).resolveInfo;
        }
        return resolveInfo;
    }

    public int getActivityCount() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mActivities.size();
        }
        return size;
    }

    public int getActivityIndex(ResolveInfo resolveInfo) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            List<ActivityResolveInfo> list = this.mActivities;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i).resolveInfo == resolveInfo) {
                    return i;
                }
            }
            return -1;
        }
    }

    public ResolveInfo getDefaultActivity() {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            if (this.mActivities.isEmpty()) {
                return null;
            }
            return this.mActivities.get(0).resolveInfo;
        }
    }

    public int getHistoryMaxSize() {
        int i;
        synchronized (this.mInstanceLock) {
            i = this.mHistoryMaxSize;
        }
        return i;
    }

    public int getHistorySize() {
        int size;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            size = this.mHistoricalRecords.size();
        }
        return size;
    }

    public Intent getIntent() {
        Intent intent;
        synchronized (this.mInstanceLock) {
            intent = this.mIntent;
        }
        return intent;
    }

    public void setActivitySorter(ActivitySorter activitySorter) {
        synchronized (this.mInstanceLock) {
            if (this.mActivitySorter == activitySorter) {
                return;
            }
            this.mActivitySorter = activitySorter;
            if (sortActivitiesIfNeeded()) {
                notifyChanged();
            }
        }
    }

    public void setDefaultActivity(int i) {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            ActivityResolveInfo activityResolveInfo = this.mActivities.get(i);
            ActivityResolveInfo activityResolveInfo2 = this.mActivities.get(0);
            addHistoricalRecord(new HistoricalRecord(new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), System.currentTimeMillis(), activityResolveInfo2 != null ? (activityResolveInfo2.weight - activityResolveInfo.weight) + 5.0f : DEFAULT_HISTORICAL_RECORD_WEIGHT));
        }
    }

    public void setHistoryMaxSize(int i) {
        synchronized (this.mInstanceLock) {
            if (this.mHistoryMaxSize == i) {
                return;
            }
            this.mHistoryMaxSize = i;
            pruneExcessiveHistoricalRecordsIfNeeded();
            if (sortActivitiesIfNeeded()) {
                notifyChanged();
            }
        }
    }

    public void setIntent(Intent intent) {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == intent) {
                return;
            }
            this.mIntent = intent;
            this.mReloadActivities = true;
            ensureConsistentState();
        }
    }

    public void setOnChooseActivityListener(OnChooseActivityListener onChooseActivityListener) {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = onChooseActivityListener;
        }
    }
}
