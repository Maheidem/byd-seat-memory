package org.json;

import java.util.Iterator;

/* loaded from: classes.dex */
public class HTTP {
    public static final String CRLF = "\r\n";

    /* JADX WARN: Removed duplicated region for block: B:10:0x0064 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0053  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:9:0x0053 -> B:5:0x0032). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static org.json.JSONObject toJSONObject(java.lang.String r4) throws org.json.JSONException {
        /*
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            org.json.HTTPTokener r1 = new org.json.HTTPTokener
            r1.<init>(r4)
            java.lang.String r4 = r1.nextToken()
            java.lang.String r2 = r4.toUpperCase()
            java.lang.String r3 = "HTTP"
            boolean r2 = r2.startsWith(r3)
            r3 = 0
            if (r2 == 0) goto L36
            java.lang.String r2 = "HTTP-Version"
            r0.put(r2, r4)
            java.lang.String r4 = "Status-Code"
            java.lang.String r2 = r1.nextToken()
            r0.put(r4, r2)
            java.lang.String r4 = "Reason-Phrase"
            java.lang.String r2 = r1.nextTo(r3)
            r0.put(r4, r2)
        L32:
            r1.next()
            goto L4d
        L36:
            java.lang.String r2 = "Method"
            r0.put(r2, r4)
            java.lang.String r4 = "Request-URI"
            java.lang.String r2 = r1.nextToken()
            r0.put(r4, r2)
            java.lang.String r4 = "HTTP-Version"
            java.lang.String r2 = r1.nextToken()
            r0.put(r4, r2)
        L4d:
            boolean r4 = r1.more()
            if (r4 == 0) goto L64
            r4 = 58
            java.lang.String r2 = r1.nextTo(r4)
            r1.next(r4)
            java.lang.String r4 = r1.nextTo(r3)
            r0.put(r2, r4)
            goto L32
        L64:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.HTTP.toJSONObject(java.lang.String):org.json.JSONObject");
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        String string;
        Iterator itKeys = jSONObject.keys();
        StringBuffer stringBuffer = new StringBuffer();
        if (jSONObject.has("Status-Code") && jSONObject.has("Reason-Phrase")) {
            stringBuffer.append(jSONObject.getString("HTTP-Version"));
            stringBuffer.append(' ');
            stringBuffer.append(jSONObject.getString("Status-Code"));
            stringBuffer.append(' ');
            string = "Reason-Phrase";
        } else {
            if (!jSONObject.has("Method") || !jSONObject.has("Request-URI")) {
                throw new JSONException("Not enough material for an HTTP header.");
            }
            stringBuffer.append(jSONObject.getString("Method"));
            stringBuffer.append(' ');
            stringBuffer.append('\"');
            stringBuffer.append(jSONObject.getString("Request-URI"));
            stringBuffer.append('\"');
            stringBuffer.append(' ');
            string = "HTTP-Version";
        }
        while (true) {
            stringBuffer.append(jSONObject.getString(string));
            stringBuffer.append(CRLF);
            while (itKeys.hasNext()) {
                string = itKeys.next().toString();
                if (string.equals("HTTP-Version") || string.equals("Status-Code") || string.equals("Reason-Phrase") || string.equals("Method") || string.equals("Request-URI") || jSONObject.isNull(string)) {
                }
            }
            stringBuffer.append(CRLF);
            return stringBuffer.toString();
            stringBuffer.append(string);
            stringBuffer.append(": ");
        }
    }
}
