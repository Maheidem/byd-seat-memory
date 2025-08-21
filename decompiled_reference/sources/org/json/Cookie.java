package org.json;

import java.io.IOException;

/* loaded from: classes.dex */
public class Cookie {
    public static String escape(String str) {
        String strTrim = str.trim();
        StringBuffer stringBuffer = new StringBuffer();
        int length = strTrim.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = strTrim.charAt(i);
            if (cCharAt < ' ' || cCharAt == '+' || cCharAt == '%' || cCharAt == '=' || cCharAt == ';') {
                stringBuffer.append('%');
                stringBuffer.append(Character.forDigit((char) ((cCharAt >>> 4) & 15), 16));
                cCharAt = Character.forDigit((char) (cCharAt & 15), 16);
            }
            stringBuffer.append(cCharAt);
        }
        return stringBuffer.toString();
    }

    public static JSONObject toJSONObject(String str) throws JSONException, IOException {
        Object objUnescape;
        JSONObject jSONObject = new JSONObject();
        JSONTokener jSONTokener = new JSONTokener(str);
        jSONObject.put("name", jSONTokener.nextTo('='));
        jSONTokener.next('=');
        jSONObject.put("value", jSONTokener.nextTo(';'));
        jSONTokener.next();
        while (jSONTokener.more()) {
            String strUnescape = unescape(jSONTokener.nextTo("=;"));
            if (jSONTokener.next() == '=') {
                objUnescape = unescape(jSONTokener.nextTo(';'));
                jSONTokener.next();
            } else {
                if (!strUnescape.equals("secure")) {
                    throw jSONTokener.syntaxError("Missing '=' in cookie parameter.");
                }
                objUnescape = Boolean.TRUE;
            }
            jSONObject.put(strUnescape, objUnescape);
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(escape(jSONObject.getString("name")));
        stringBuffer.append("=");
        stringBuffer.append(escape(jSONObject.getString("value")));
        if (jSONObject.has("expires")) {
            stringBuffer.append(";expires=");
            stringBuffer.append(jSONObject.getString("expires"));
        }
        if (jSONObject.has("domain")) {
            stringBuffer.append(";domain=");
            stringBuffer.append(escape(jSONObject.getString("domain")));
        }
        if (jSONObject.has("path")) {
            stringBuffer.append(";path=");
            stringBuffer.append(escape(jSONObject.getString("path")));
        }
        if (jSONObject.optBoolean("secure")) {
            stringBuffer.append(";secure");
        }
        return stringBuffer.toString();
    }

    public static String unescape(String str) {
        int i;
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer();
        int i2 = 0;
        while (i2 < length) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == '+') {
                cCharAt = ' ';
            } else if (cCharAt == '%' && (i = i2 + 2) < length) {
                int iDehexchar = JSONTokener.dehexchar(str.charAt(i2 + 1));
                int iDehexchar2 = JSONTokener.dehexchar(str.charAt(i));
                if (iDehexchar >= 0 && iDehexchar2 >= 0) {
                    cCharAt = (char) ((iDehexchar * 16) + iDehexchar2);
                    i2 = i;
                }
            }
            stringBuffer.append(cCharAt);
            i2++;
        }
        return stringBuffer.toString();
    }
}
