package io.socket.engineio.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class HandshakeData {
    public long pingInterval;
    public long pingTimeout;
    public String sid;
    public String[] upgrades;

    HandshakeData(String str) {
        this(new JSONObject(str));
    }

    HandshakeData(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray = jSONObject.getJSONArray("upgrades");
        int length = jSONArray.length();
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = jSONArray.getString(i);
        }
        this.sid = jSONObject.getString("sid");
        this.upgrades = strArr;
        this.pingInterval = jSONObject.getLong("pingInterval");
        this.pingTimeout = jSONObject.getLong("pingTimeout");
    }
}
