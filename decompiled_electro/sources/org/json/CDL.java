package org.json;

import java.io.IOException;

/* loaded from: classes.dex */
public class CDL {
    private static String getValue(JSONTokener jSONTokener) throws JSONException, IOException {
        char next;
        while (true) {
            next = jSONTokener.next();
            if (next != ' ' && next != '\t') {
                break;
            }
        }
        if (next == 0) {
            return null;
        }
        if (next == '\"' || next == '\'') {
            return jSONTokener.nextString(next);
        }
        if (next != ',') {
            jSONTokener.back();
            return jSONTokener.nextTo(',');
        }
        jSONTokener.back();
        return "";
    }

    public static JSONArray rowToJSONArray(JSONTokener jSONTokener) throws JSONException, IOException {
        char next;
        JSONArray jSONArray = new JSONArray();
        while (true) {
            String value = getValue(jSONTokener);
            if (value == null) {
                return null;
            }
            if (jSONArray.length() == 0 && value.length() == 0) {
                return null;
            }
            jSONArray.put(value);
            do {
                next = jSONTokener.next();
                if (next == ',') {
                    break;
                }
            } while (next == ' ');
            if (next == '\n' || next == '\r' || next == 0) {
                return jSONArray;
            }
            throw jSONTokener.syntaxError("Bad character '" + next + "' (" + ((int) next) + ").");
        }
    }

    public static JSONObject rowToJSONObject(JSONArray jSONArray, JSONTokener jSONTokener) throws JSONException, IOException {
        JSONArray jSONArrayRowToJSONArray = rowToJSONArray(jSONTokener);
        if (jSONArrayRowToJSONArray != null) {
            return jSONArrayRowToJSONArray.toJSONObject(jSONArray);
        }
        return null;
    }

    public static String rowToString(JSONArray jSONArray) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < jSONArray.length(); i++) {
            if (i > 0) {
                stringBuffer.append(',');
            }
            Object objOpt = jSONArray.opt(i);
            if (objOpt != null) {
                String string = objOpt.toString();
                if (string.indexOf(44) >= 0) {
                    char c = string.indexOf(34) >= 0 ? '\'' : '\"';
                    stringBuffer.append(c);
                    stringBuffer.append(string);
                    stringBuffer.append(c);
                } else {
                    stringBuffer.append(string);
                }
            }
        }
        stringBuffer.append('\n');
        return stringBuffer.toString();
    }

    public static JSONArray toJSONArray(String str) {
        return toJSONArray(new JSONTokener(str));
    }

    public static JSONArray toJSONArray(JSONArray jSONArray, String str) {
        return toJSONArray(jSONArray, new JSONTokener(str));
    }

    public static JSONArray toJSONArray(JSONArray jSONArray, JSONTokener jSONTokener) throws JSONException, IOException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        while (true) {
            JSONObject jSONObjectRowToJSONObject = rowToJSONObject(jSONArray, jSONTokener);
            if (jSONObjectRowToJSONObject == null) {
                break;
            }
            jSONArray2.put(jSONObjectRowToJSONObject);
        }
        if (jSONArray2.length() == 0) {
            return null;
        }
        return jSONArray2;
    }

    public static JSONArray toJSONArray(JSONTokener jSONTokener) {
        return toJSONArray(rowToJSONArray(jSONTokener), jSONTokener);
    }

    public static String toString(JSONArray jSONArray) {
        JSONArray jSONArrayNames;
        JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(0);
        if (jSONObjectOptJSONObject == null || (jSONArrayNames = jSONObjectOptJSONObject.names()) == null) {
            return null;
        }
        return rowToString(jSONArrayNames) + toString(jSONArrayNames, jSONArray);
    }

    public static String toString(JSONArray jSONArray, JSONArray jSONArray2) {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < jSONArray2.length(); i++) {
            JSONObject jSONObjectOptJSONObject = jSONArray2.optJSONObject(i);
            if (jSONObjectOptJSONObject != null) {
                stringBuffer.append(rowToString(jSONObjectOptJSONObject.toJSONArray(jSONArray)));
            }
        }
        return stringBuffer.toString();
    }
}
