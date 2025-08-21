package io.socket.hasbinary;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class HasBinary {
    private static final Logger logger = Logger.getLogger(HasBinary.class.getName());

    private HasBinary() {
    }

    private static boolean _hasBinary(Object obj) {
        Logger logger2;
        Level level;
        String str;
        if (obj == null) {
            return false;
        }
        if (obj instanceof byte[]) {
            return true;
        }
        if (!(obj instanceof JSONArray)) {
            if (obj instanceof JSONObject) {
                JSONObject jSONObject = (JSONObject) obj;
                Iterator itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    try {
                        if (_hasBinary(jSONObject.get((String) itKeys.next()))) {
                            return true;
                        }
                    } catch (JSONException e) {
                        e = e;
                        logger2 = logger;
                        level = Level.WARNING;
                        str = "An error occured while retrieving data from JSONObject";
                        logger2.log(level, str, (Throwable) e);
                        return false;
                    }
                }
            }
            return false;
        }
        JSONArray jSONArray = (JSONArray) obj;
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            try {
                if (_hasBinary(jSONArray.isNull(i) ? null : jSONArray.get(i))) {
                    return true;
                }
            } catch (JSONException e2) {
                e = e2;
                logger2 = logger;
                level = Level.WARNING;
                str = "An error occured while retrieving data from JSONArray";
                logger2.log(level, str, (Throwable) e);
                return false;
            }
        }
        return false;
    }

    public static boolean hasBinary(Object obj) {
        return _hasBinary(obj);
    }
}
