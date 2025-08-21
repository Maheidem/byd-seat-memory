package io.socket.parseqs;

import io.socket.global.Global;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class ParseQS {
    private ParseQS() {
    }

    public static Map<String, String> decode(String str) {
        HashMap map = new HashMap();
        for (String str2 : str.split("&")) {
            String[] strArrSplit = str2.split("=");
            map.put(Global.decodeURIComponent(strArrSplit[0]), strArrSplit.length > 1 ? Global.decodeURIComponent(strArrSplit[1]) : "");
        }
        return map;
    }

    public static String encode(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(Global.encodeURIComponent(entry.getKey()));
            sb.append("=");
            sb.append(Global.encodeURIComponent(entry.getValue()));
        }
        return sb.toString();
    }
}
