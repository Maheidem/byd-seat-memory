package org.json;

import java.util.Iterator;

/* loaded from: classes.dex */
public class JSONML {
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0194, code lost:
    
        throw r7.syntaxError("Reserved attribute.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x019d, code lost:
    
        r5 = r7.nextToken();
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x01a3, code lost:
    
        if ((r5 instanceof java.lang.String) != false) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x01ab, code lost:
    
        throw r7.syntaxError("Missing value");
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0087, code lost:
    
        throw r7.syntaxError("Expected 'CDATA['");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.Object parse(org.json.XMLTokener r7, boolean r8, org.json.JSONArray r9) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 464
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONML.parse(org.json.XMLTokener, boolean, org.json.JSONArray):java.lang.Object");
    }

    public static JSONArray toJSONArray(String str) {
        return toJSONArray(new XMLTokener(str));
    }

    public static JSONArray toJSONArray(XMLTokener xMLTokener) {
        return (JSONArray) parse(xMLTokener, true, null);
    }

    public static JSONObject toJSONObject(String str) {
        return toJSONObject(new XMLTokener(str));
    }

    public static JSONObject toJSONObject(XMLTokener xMLTokener) {
        return (JSONObject) parse(xMLTokener, false, null);
    }

    public static String toString(JSONArray jSONArray) throws JSONException {
        int i;
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        String string2 = jSONArray.getString(0);
        XML.noSpace(string2);
        String strEscape = XML.escape(string2);
        stringBuffer.append('<');
        stringBuffer.append(strEscape);
        Object objOpt = jSONArray.opt(1);
        if (objOpt instanceof JSONObject) {
            i = 2;
            JSONObject jSONObject = (JSONObject) objOpt;
            Iterator itKeys = jSONObject.keys();
            while (itKeys.hasNext()) {
                String string3 = itKeys.next().toString();
                XML.noSpace(string3);
                String strOptString = jSONObject.optString(string3);
                if (strOptString != null) {
                    stringBuffer.append(' ');
                    stringBuffer.append(XML.escape(string3));
                    stringBuffer.append('=');
                    stringBuffer.append('\"');
                    stringBuffer.append(XML.escape(strOptString));
                    stringBuffer.append('\"');
                }
            }
        } else {
            i = 1;
        }
        int length = jSONArray.length();
        if (i >= length) {
            stringBuffer.append('/');
        } else {
            stringBuffer.append('>');
            do {
                Object obj = jSONArray.get(i);
                i++;
                if (obj != null) {
                    if (obj instanceof String) {
                        string = XML.escape(obj.toString());
                    } else if (obj instanceof JSONObject) {
                        string = toString((JSONObject) obj);
                    } else if (obj instanceof JSONArray) {
                        string = toString((JSONArray) obj);
                    }
                    stringBuffer.append(string);
                }
            } while (i < length);
            stringBuffer.append('<');
            stringBuffer.append('/');
            stringBuffer.append(strEscape);
        }
        stringBuffer.append('>');
        return stringBuffer.toString();
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        String string;
        StringBuffer stringBuffer = new StringBuffer();
        String strOptString = jSONObject.optString("tagName");
        if (strOptString == null) {
            return XML.escape(jSONObject.toString());
        }
        XML.noSpace(strOptString);
        String strEscape = XML.escape(strOptString);
        stringBuffer.append('<');
        stringBuffer.append(strEscape);
        Iterator itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String string2 = itKeys.next().toString();
            if (!string2.equals("tagName") && !string2.equals("childNodes")) {
                XML.noSpace(string2);
                String strOptString2 = jSONObject.optString(string2);
                if (strOptString2 != null) {
                    stringBuffer.append(' ');
                    stringBuffer.append(XML.escape(string2));
                    stringBuffer.append('=');
                    stringBuffer.append('\"');
                    stringBuffer.append(XML.escape(strOptString2));
                    stringBuffer.append('\"');
                }
            }
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("childNodes");
        if (jSONArrayOptJSONArray == null) {
            stringBuffer.append('/');
        } else {
            stringBuffer.append('>');
            int length = jSONArrayOptJSONArray.length();
            for (int i = 0; i < length; i++) {
                Object obj = jSONArrayOptJSONArray.get(i);
                if (obj != null) {
                    if (obj instanceof String) {
                        string = XML.escape(obj.toString());
                    } else if (obj instanceof JSONObject) {
                        string = toString((JSONObject) obj);
                    } else if (obj instanceof JSONArray) {
                        string = toString((JSONArray) obj);
                    }
                    stringBuffer.append(string);
                }
            }
            stringBuffer.append('<');
            stringBuffer.append('/');
            stringBuffer.append(strEscape);
        }
        stringBuffer.append('>');
        return stringBuffer.toString();
    }
}
