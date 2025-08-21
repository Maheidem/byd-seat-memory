package io.socket.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class Binary {
    private static final String KEY_NUM = "num";
    private static final String KEY_PLACEHOLDER = "_placeholder";
    private static final Logger logger = Logger.getLogger(Binary.class.getName());

    public static class DeconstructedPacket {
        public byte[][] buffers;
        public Packet packet;
    }

    private static Object _deconstructPacket(Object obj, List<byte[]> list) {
        Logger logger2;
        Level level;
        String str;
        if (obj == null) {
            return null;
        }
        try {
            if (obj instanceof byte[]) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put(KEY_PLACEHOLDER, true);
                jSONObject.put(KEY_NUM, list.size());
                list.add((byte[]) obj);
                return jSONObject;
            }
            if (!(obj instanceof JSONArray)) {
                if (!(obj instanceof JSONObject)) {
                    return obj;
                }
                JSONObject jSONObject2 = new JSONObject();
                JSONObject jSONObject3 = (JSONObject) obj;
                Iterator itKeys = jSONObject3.keys();
                while (itKeys.hasNext()) {
                    String str2 = (String) itKeys.next();
                    jSONObject2.put(str2, _deconstructPacket(jSONObject3.get(str2), list));
                }
                return jSONObject2;
            }
            JSONArray jSONArray = new JSONArray();
            JSONArray jSONArray2 = (JSONArray) obj;
            int length = jSONArray2.length();
            for (int i = 0; i < length; i++) {
                try {
                    jSONArray.put(i, _deconstructPacket(jSONArray2.get(i), list));
                } catch (JSONException e) {
                    e = e;
                    logger2 = logger;
                    level = Level.WARNING;
                    str = "An error occured while putting packet data to JSONObject";
                    logger2.log(level, str, (Throwable) e);
                    return null;
                }
            }
            return jSONArray;
        } catch (JSONException e2) {
            e = e2;
            logger2 = logger;
            level = Level.WARNING;
            str = "An error occured while putting data to JSONObject";
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v2, types: [org.json.JSONObject] */
    private static Object _reconstructPacket(Object obj, byte[][] bArr) {
        Logger logger2;
        Level level;
        String str;
        if (obj instanceof JSONArray) {
            JSONArray jSONArray = (JSONArray) obj;
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                try {
                    jSONArray.put(i, _reconstructPacket(jSONArray.get(i), bArr));
                } catch (JSONException e) {
                    e = e;
                    logger2 = logger;
                    level = Level.WARNING;
                    str = "An error occured while putting packet data to JSONObject";
                }
            }
            return jSONArray;
        }
        if (obj instanceof JSONObject) {
            obj = (JSONObject) obj;
            if (obj.optBoolean(KEY_PLACEHOLDER)) {
                int iOptInt = obj.optInt(KEY_NUM, -1);
                if (iOptInt < 0 || iOptInt >= bArr.length) {
                    return null;
                }
                return bArr[iOptInt];
            }
            Iterator itKeys = obj.keys();
            while (itKeys.hasNext()) {
                String str2 = (String) itKeys.next();
                try {
                    obj.put(str2, _reconstructPacket(obj.get(str2), bArr));
                } catch (JSONException e2) {
                    e = e2;
                    logger2 = logger;
                    level = Level.WARNING;
                    str = "An error occured while putting data to JSONObject";
                }
            }
        }
        return obj;
        logger2.log(level, str, (Throwable) e);
        return null;
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [T, java.lang.Object] */
    public static DeconstructedPacket deconstructPacket(Packet packet) {
        ArrayList arrayList = new ArrayList();
        packet.data = _deconstructPacket(packet.data, arrayList);
        packet.attachments = arrayList.size();
        DeconstructedPacket deconstructedPacket = new DeconstructedPacket();
        deconstructedPacket.packet = packet;
        deconstructedPacket.buffers = (byte[][]) arrayList.toArray(new byte[arrayList.size()][]);
        return deconstructedPacket;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [T, java.lang.Object] */
    public static Packet reconstructPacket(Packet packet, byte[][] bArr) {
        packet.data = _reconstructPacket(packet.data, bArr);
        packet.attachments = -1;
        return packet;
    }
}
