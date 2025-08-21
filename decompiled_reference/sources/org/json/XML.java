package org.json;

import java.util.Iterator;

/* loaded from: classes.dex */
public class XML {
    public static final Character AMP = new Character('&');
    public static final Character APOS = new Character('\'');
    public static final Character BANG = new Character('!');
    public static final Character EQ = new Character('=');
    public static final Character GT = new Character('>');
    public static final Character LT = new Character('<');
    public static final Character QUEST = new Character('?');
    public static final Character QUOT = new Character('\"');
    public static final Character SLASH = new Character('/');

    public static String escape(String str) {
        String str2;
        StringBuffer stringBuffer = new StringBuffer();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\"') {
                str2 = "&quot;";
            } else if (cCharAt == '&') {
                str2 = "&amp;";
            } else if (cCharAt == '<') {
                str2 = "&lt;";
            } else if (cCharAt != '>') {
                stringBuffer.append(cCharAt);
            } else {
                str2 = "&gt;";
            }
            stringBuffer.append(str2);
        }
        return stringBuffer.toString();
    }

    public static void noSpace(String str) throws JSONException {
        int length = str.length();
        if (length == 0) {
            throw new JSONException("Empty string.");
        }
        for (int i = 0; i < length; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                throw new JSONException("'" + str + "' contains a space character.");
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x00ef, code lost:
    
        r5 = r7.nextToken();
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00f5, code lost:
    
        if ((r5 instanceof java.lang.String) != false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00fd, code lost:
    
        throw r7.syntaxError("Missing value");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean parse(org.json.XMLTokener r7, org.json.JSONObject r8, java.lang.String r9) throws org.json.JSONException {
        /*
            Method dump skipped, instructions count: 405
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.XML.parse(org.json.XMLTokener, org.json.JSONObject, java.lang.String):boolean");
    }

    public static JSONObject toJSONObject(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        XMLTokener xMLTokener = new XMLTokener(str);
        while (xMLTokener.more() && xMLTokener.skipPast("<")) {
            parse(xMLTokener, jSONObject, null);
        }
        return jSONObject;
    }

    public static String toString(Object obj) {
        return toString(obj, null);
    }

    public static String toString(Object obj, String str) throws JSONException {
        String strEscape;
        StringBuffer stringBuffer = new StringBuffer();
        if (!(obj instanceof JSONObject)) {
            if (obj instanceof JSONArray) {
                JSONArray jSONArray = (JSONArray) obj;
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    stringBuffer.append(toString(jSONArray.opt(i), str == null ? "array" : str));
                }
                return stringBuffer.toString();
            }
            String strEscape2 = obj == null ? "null" : escape(obj.toString());
            if (str == null) {
                return "\"" + strEscape2 + "\"";
            }
            if (strEscape2.length() == 0) {
                return "<" + str + "/>";
            }
            return "<" + str + ">" + strEscape2 + "</" + str + ">";
        }
        if (str != null) {
            stringBuffer.append('<');
            stringBuffer.append(str);
            stringBuffer.append('>');
        }
        JSONObject jSONObject = (JSONObject) obj;
        Iterator itKeys = jSONObject.keys();
        while (itKeys.hasNext()) {
            String string = itKeys.next().toString();
            Object objOpt = jSONObject.opt(string);
            if (objOpt == null) {
                objOpt = "";
            }
            if (objOpt instanceof String) {
            }
            if (string.equals("content")) {
                if (objOpt instanceof JSONArray) {
                    JSONArray jSONArray2 = (JSONArray) objOpt;
                    int length2 = jSONArray2.length();
                    for (int i2 = 0; i2 < length2; i2++) {
                        if (i2 > 0) {
                            stringBuffer.append('\n');
                        }
                        stringBuffer.append(escape(jSONArray2.get(i2).toString()));
                    }
                } else {
                    strEscape = escape(objOpt.toString());
                    stringBuffer.append(strEscape);
                }
            } else if (objOpt instanceof JSONArray) {
                JSONArray jSONArray3 = (JSONArray) objOpt;
                int length3 = jSONArray3.length();
                for (int i3 = 0; i3 < length3; i3++) {
                    Object obj2 = jSONArray3.get(i3);
                    if (obj2 instanceof JSONArray) {
                        stringBuffer.append('<');
                        stringBuffer.append(string);
                        stringBuffer.append('>');
                        stringBuffer.append(toString(obj2));
                        stringBuffer.append("</");
                        stringBuffer.append(string);
                        stringBuffer.append('>');
                    } else {
                        stringBuffer.append(toString(obj2, string));
                    }
                }
            } else {
                if (objOpt.equals("")) {
                    stringBuffer.append('<');
                    stringBuffer.append(string);
                    strEscape = "/>";
                } else {
                    strEscape = toString(objOpt, string);
                }
                stringBuffer.append(strEscape);
            }
        }
        if (str != null) {
            stringBuffer.append("</");
            stringBuffer.append(str);
            stringBuffer.append('>');
        }
        return stringBuffer.toString();
    }
}
