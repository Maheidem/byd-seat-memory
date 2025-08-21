package org.acra.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.collector.CollectorUtil;
import org.acra.collector.CrashReportData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class JSONReportBuilder {

    public static class JSONReportException extends Exception {
        private static final long serialVersionUID = -694684023635442219L;

        public JSONReportException(String str, Throwable th) {
            super(str, th);
        }
    }

    private static void addJSONFromProperty(JSONObject jSONObject, String str) throws JSONException {
        int iIndexOf = str.indexOf(61);
        if (iIndexOf <= 0) {
            jSONObject.put(str.trim(), true);
            return;
        }
        String strTrim = str.substring(0, iIndexOf).trim();
        Object objGuessType = guessType(str.substring(iIndexOf + 1).trim());
        if (objGuessType instanceof String) {
            objGuessType = ((String) objGuessType).replaceAll("\\\\n", "\n");
        }
        String[] strArrSplit = strTrim.split("\\.");
        if (strArrSplit.length > 1) {
            addJSONSubTree(jSONObject, strArrSplit, objGuessType);
        } else {
            jSONObject.accumulate(strTrim, objGuessType);
        }
    }

    private static void addJSONSubTree(JSONObject jSONObject, String[] strArr, Object obj) throws JSONException {
        JSONObject jSONObject2 = jSONObject;
        for (int i = 0; i < strArr.length; i++) {
            String str = strArr[i];
            if (i < strArr.length - 1) {
                JSONObject jSONObjectOptJSONObject = null;
                if (jSONObject2.isNull(str)) {
                    jSONObjectOptJSONObject = new JSONObject();
                    jSONObject2.accumulate(str, jSONObjectOptJSONObject);
                } else {
                    Object obj2 = jSONObject2.get(str);
                    if (obj2 instanceof JSONObject) {
                        jSONObjectOptJSONObject = jSONObject2.getJSONObject(str);
                    } else if (obj2 instanceof JSONArray) {
                        JSONArray jSONArray = jSONObject2.getJSONArray(str);
                        for (int i2 = 0; i2 < jSONArray.length() && (jSONObjectOptJSONObject = jSONArray.optJSONObject(i2)) == null; i2++) {
                        }
                    }
                    if (jSONObjectOptJSONObject == null) {
                        ACRA.log.e(ACRA.LOG_TAG, "Unknown json subtree type, see issue #186");
                        return;
                    }
                }
                jSONObject2 = jSONObjectOptJSONObject;
            } else {
                jSONObject2.accumulate(str, obj);
            }
        }
    }

    public static JSONObject buildJSONReport(CrashReportData crashReportData) throws Throwable {
        JSONObject jSONObject = new JSONObject();
        BufferedReader bufferedReader = null;
        for (ReportField reportField : crashReportData.keySet()) {
            try {
                try {
                    if (reportField.containsKeyValuePairs()) {
                        JSONObject jSONObject2 = new JSONObject();
                        BufferedReader bufferedReader2 = new BufferedReader(new StringReader(crashReportData.getProperty(reportField)), 1024);
                        while (true) {
                            try {
                                try {
                                    String line = bufferedReader2.readLine();
                                    if (line == null) {
                                        break;
                                    }
                                    addJSONFromProperty(jSONObject2, line);
                                } catch (IOException e) {
                                    ACRA.log.e(ACRA.LOG_TAG, "Error while converting " + reportField.name() + " to JSON.", e);
                                }
                            } catch (JSONException e2) {
                                e = e2;
                                bufferedReader = bufferedReader2;
                                throw new JSONReportException("Could not create JSON object for key " + reportField, e);
                            } catch (Throwable th) {
                                th = th;
                                bufferedReader = bufferedReader2;
                                CollectorUtil.safeClose(bufferedReader);
                                throw th;
                            }
                        }
                        jSONObject.accumulate(reportField.name(), jSONObject2);
                        bufferedReader = bufferedReader2;
                    } else {
                        jSONObject.accumulate(reportField.name(), guessType(crashReportData.getProperty(reportField)));
                    }
                    CollectorUtil.safeClose(bufferedReader);
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (JSONException e3) {
                e = e3;
            }
        }
        return jSONObject;
    }

    private static Object guessType(String str) {
        boolean z;
        if (str.equalsIgnoreCase("true")) {
            z = true;
        } else {
            if (!str.equalsIgnoreCase("false")) {
                if (str.matches("(?:^|\\s)([1-9](?:\\d*|(?:\\d{0,2})(?:,\\d{3})*)(?:\\.\\d*[1-9])?|0?\\.\\d*[1-9]|0)(?:\\s|$)")) {
                    try {
                        return NumberFormat.getInstance(Locale.US).parse(str);
                    } catch (ParseException unused) {
                    }
                }
                return str;
            }
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
