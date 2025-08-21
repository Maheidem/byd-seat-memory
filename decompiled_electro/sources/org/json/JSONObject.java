package org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/* loaded from: classes.dex */
public class JSONObject {
    public static final Object NULL = new Null();
    private Map map;

    private static final class Null {
        private Null() {
        }

        protected final Object clone() {
            return this;
        }

        public boolean equals(Object obj) {
            return obj == null || obj == this;
        }

        public String toString() {
            return "null";
        }
    }

    public JSONObject() {
        this.map = new HashMap();
    }

    public JSONObject(Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this();
        populateInternalMap(obj, false);
    }

    public JSONObject(Object obj, boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this();
        populateInternalMap(obj, z);
    }

    public JSONObject(Object obj, String[] strArr) {
        this();
        Class<?> cls = obj.getClass();
        for (String str : strArr) {
            try {
                putOpt(str, cls.getField(str).get(obj));
            } catch (Exception unused) {
            }
        }
    }

    public JSONObject(String str) {
        this(new JSONTokener(str));
    }

    public JSONObject(Map map) {
        this.map = map == null ? new HashMap() : map;
    }

    public JSONObject(Map map, boolean z) {
        this.map = new HashMap();
        if (map != null) {
            for (Map.Entry entry : map.entrySet()) {
                this.map.put(entry.getKey(), new JSONObject(entry.getValue(), z));
            }
        }
    }

    public JSONObject(JSONObject jSONObject, String[] strArr) throws JSONException {
        this();
        for (int i = 0; i < strArr.length; i++) {
            putOnce(strArr[i], jSONObject.opt(strArr[i]));
        }
    }

    public JSONObject(JSONTokener jSONTokener) throws JSONException {
        this();
        if (jSONTokener.nextClean() != '{') {
            throw jSONTokener.syntaxError("A JSONObject text must begin with '{'");
        }
        while (true) {
            char cNextClean = jSONTokener.nextClean();
            if (cNextClean == 0) {
                throw jSONTokener.syntaxError("A JSONObject text must end with '}'");
            }
            if (cNextClean == '}') {
                return;
            }
            jSONTokener.back();
            String string = jSONTokener.nextValue().toString();
            char cNextClean2 = jSONTokener.nextClean();
            if (cNextClean2 == '=') {
                if (jSONTokener.next() != '>') {
                    jSONTokener.back();
                }
            } else if (cNextClean2 != ':') {
                throw jSONTokener.syntaxError("Expected a ':' after a key");
            }
            putOnce(string, jSONTokener.nextValue());
            char cNextClean3 = jSONTokener.nextClean();
            if (cNextClean3 != ',' && cNextClean3 != ';') {
                if (cNextClean3 != '}') {
                    throw jSONTokener.syntaxError("Expected a ',' or '}'");
                }
                return;
            } else if (jSONTokener.nextClean() == '}') {
                return;
            } else {
                jSONTokener.back();
            }
        }
    }

    public static String doubleToString(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d)) {
            return "null";
        }
        String string = Double.toString(d);
        if (string.indexOf(46) <= 0 || string.indexOf(101) >= 0 || string.indexOf(69) >= 0) {
            return string;
        }
        while (string.endsWith("0")) {
            string = string.substring(0, string.length() - 1);
        }
        return string.endsWith(".") ? string.substring(0, string.length() - 1) : string;
    }

    public static String[] getNames(Object obj) {
        Field[] fields;
        int length;
        if (obj == null || (length = (fields = obj.getClass().getFields()).length) == 0) {
            return null;
        }
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = fields[i].getName();
        }
        return strArr;
    }

    public static String[] getNames(JSONObject jSONObject) {
        int length = jSONObject.length();
        if (length == 0) {
            return null;
        }
        Iterator itKeys = jSONObject.keys();
        String[] strArr = new String[length];
        int i = 0;
        while (itKeys.hasNext()) {
            strArr[i] = (String) itKeys.next();
            i++;
        }
        return strArr;
    }

    private boolean isStandardProperty(Class cls) {
        return cls.isPrimitive() || cls.isAssignableFrom(Byte.class) || cls.isAssignableFrom(Short.class) || cls.isAssignableFrom(Integer.class) || cls.isAssignableFrom(Long.class) || cls.isAssignableFrom(Float.class) || cls.isAssignableFrom(Double.class) || cls.isAssignableFrom(Character.class) || cls.isAssignableFrom(String.class) || cls.isAssignableFrom(Boolean.class);
    }

    public static String numberToString(Number number) throws JSONException {
        if (number == null) {
            throw new JSONException("Null pointer");
        }
        testValidity(number);
        String string = number.toString();
        if (string.indexOf(46) <= 0 || string.indexOf(101) >= 0 || string.indexOf(69) >= 0) {
            return string;
        }
        while (string.endsWith("0")) {
            string = string.substring(0, string.length() - 1);
        }
        return string.endsWith(".") ? string.substring(0, string.length() - 1) : string;
    }

    private void populateInternalMap(Object obj, boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map map;
        Map map2;
        Object jSONObject;
        Class<?> cls = obj.getClass();
        if (cls.getClassLoader() == null) {
            z = false;
        }
        for (Method method : z ? cls.getMethods() : cls.getDeclaredMethods()) {
            try {
                String name = method.getName();
                String strSubstring = "";
                if (name.startsWith("get")) {
                    strSubstring = name.substring(3);
                } else if (name.startsWith("is")) {
                    strSubstring = name.substring(2);
                }
                if (strSubstring.length() > 0 && Character.isUpperCase(strSubstring.charAt(0)) && method.getParameterTypes().length == 0) {
                    if (strSubstring.length() == 1) {
                        strSubstring = strSubstring.toLowerCase();
                    } else if (!Character.isUpperCase(strSubstring.charAt(1))) {
                        strSubstring = strSubstring.substring(0, 1).toLowerCase() + strSubstring.substring(1);
                    }
                    Object objInvoke = method.invoke(obj, (Object[]) null);
                    if (objInvoke == null) {
                        this.map.put(strSubstring, NULL);
                    } else {
                        if (objInvoke.getClass().isArray()) {
                            map2 = this.map;
                            jSONObject = new JSONArray(objInvoke, z);
                        } else if (objInvoke instanceof Collection) {
                            map2 = this.map;
                            jSONObject = new JSONArray((Collection) objInvoke, z);
                        } else if (objInvoke instanceof Map) {
                            map2 = this.map;
                            jSONObject = new JSONObject((Map) objInvoke, z);
                        } else {
                            if (isStandardProperty(objInvoke.getClass())) {
                                map = this.map;
                            } else {
                                if (!objInvoke.getClass().getPackage().getName().startsWith("java") && objInvoke.getClass().getClassLoader() != null) {
                                    map2 = this.map;
                                    jSONObject = new JSONObject(objInvoke, z);
                                }
                                map = this.map;
                                objInvoke = objInvoke.toString();
                            }
                            map.put(strSubstring, objInvoke);
                        }
                        map2.put(strSubstring, jSONObject);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:13:0x002c. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:14:0x002f. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:34:0x008d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String quote(java.lang.String r8) {
        /*
            if (r8 == 0) goto L9f
            int r0 = r8.length()
            if (r0 != 0) goto La
            goto L9f
        La:
            int r0 = r8.length()
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            int r2 = r0 + 4
            r1.<init>(r2)
            r2 = 34
            r1.append(r2)
            r3 = 0
            r4 = r3
        L1c:
            if (r3 >= r0) goto L97
            char r5 = r8.charAt(r3)
            r6 = 92
            if (r5 == r2) goto L8d
            r7 = 47
            if (r5 == r7) goto L89
            if (r5 == r6) goto L8d
            switch(r5) {
                case 8: goto L83;
                case 9: goto L80;
                case 10: goto L7d;
                default: goto L2f;
            }
        L2f:
            switch(r5) {
                case 12: goto L7a;
                case 13: goto L77;
                default: goto L32;
            }
        L32:
            r4 = 32
            if (r5 < r4) goto L46
            r4 = 128(0x80, float:1.8E-43)
            if (r5 < r4) goto L3e
            r4 = 160(0xa0, float:2.24E-43)
            if (r5 < r4) goto L46
        L3e:
            r4 = 8192(0x2000, float:1.148E-41)
            if (r5 < r4) goto L90
            r4 = 8448(0x2100, float:1.1838E-41)
            if (r5 >= r4) goto L90
        L46:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r6 = "000"
            r4.append(r6)
            java.lang.String r6 = java.lang.Integer.toHexString(r5)
            r4.append(r6)
            java.lang.String r4 = r4.toString()
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "\\u"
            r6.append(r7)
            int r7 = r4.length()
            int r7 = r7 + (-4)
            java.lang.String r4 = r4.substring(r7)
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            goto L85
        L77:
            java.lang.String r4 = "\\r"
            goto L85
        L7a:
            java.lang.String r4 = "\\f"
            goto L85
        L7d:
            java.lang.String r4 = "\\n"
            goto L85
        L80:
            java.lang.String r4 = "\\t"
            goto L85
        L83:
            java.lang.String r4 = "\\b"
        L85:
            r1.append(r4)
            goto L93
        L89:
            r7 = 60
            if (r4 != r7) goto L90
        L8d:
            r1.append(r6)
        L90:
            r1.append(r5)
        L93:
            int r3 = r3 + 1
            r4 = r5
            goto L1c
        L97:
            r1.append(r2)
            java.lang.String r8 = r1.toString()
            return r8
        L9f:
            java.lang.String r8 = "\"\""
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONObject.quote(java.lang.String):java.lang.String");
    }

    public static Object stringToValue(String str) {
        if (str.equals("")) {
            return str;
        }
        if (str.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (str.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (str.equalsIgnoreCase("null")) {
            return NULL;
        }
        char cCharAt = str.charAt(0);
        if ((cCharAt >= '0' && cCharAt <= '9') || cCharAt == '.' || cCharAt == '-' || cCharAt == '+') {
            if (cCharAt == '0') {
                try {
                    return (str.length() <= 2 || !(str.charAt(1) == 'x' || str.charAt(1) == 'X')) ? new Integer(Integer.parseInt(str, 8)) : new Integer(Integer.parseInt(str.substring(2), 16));
                } catch (Exception unused) {
                }
            }
            try {
                try {
                    try {
                        return new Integer(str);
                    } catch (Exception unused2) {
                        return new Long(str);
                    }
                } catch (Exception unused3) {
                }
            } catch (Exception unused4) {
                return new Double(str);
            }
        }
        return str;
    }

    static void testValidity(Object obj) throws JSONException {
        if (obj != null) {
            if (obj instanceof Double) {
                Double d = (Double) obj;
                if (d.isInfinite() || d.isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
                return;
            }
            if (obj instanceof Float) {
                Float f = (Float) obj;
                if (f.isInfinite() || f.isNaN()) {
                    throw new JSONException("JSON does not allow non-finite numbers.");
                }
            }
        }
    }

    static String valueToString(Object obj) throws JSONException {
        if (obj == null || obj.equals(null)) {
            return "null";
        }
        if (!(obj instanceof JSONString)) {
            return obj instanceof Number ? numberToString((Number) obj) : ((obj instanceof Boolean) || (obj instanceof JSONObject) || (obj instanceof JSONArray)) ? obj.toString() : obj instanceof Map ? new JSONObject((Map) obj).toString() : obj instanceof Collection ? new JSONArray((Collection) obj).toString() : obj.getClass().isArray() ? new JSONArray(obj).toString() : quote(obj.toString());
        }
        try {
            String jSONString = ((JSONString) obj).toJSONString();
            if (jSONString instanceof String) {
                return jSONString;
            }
            throw new JSONException("Bad value from toJSONString: " + ((Object) jSONString));
        } catch (Exception e) {
            throw new JSONException(e);
        }
    }

    static String valueToString(Object obj, int i, int i2) {
        if (obj == null || obj.equals(null)) {
            return "null";
        }
        try {
            if (obj instanceof JSONString) {
                String jSONString = ((JSONString) obj).toJSONString();
                if (jSONString instanceof String) {
                    return jSONString;
                }
            }
        } catch (Exception unused) {
        }
        return obj instanceof Number ? numberToString((Number) obj) : obj instanceof Boolean ? obj.toString() : obj instanceof JSONObject ? ((JSONObject) obj).toString(i, i2) : obj instanceof JSONArray ? ((JSONArray) obj).toString(i, i2) : obj instanceof Map ? new JSONObject((Map) obj).toString(i, i2) : obj instanceof Collection ? new JSONArray((Collection) obj).toString(i, i2) : obj.getClass().isArray() ? new JSONArray(obj).toString(i, i2) : quote(obj.toString());
    }

    public JSONObject accumulate(String str, Object obj) throws JSONException {
        JSONArray jSONArrayPut;
        testValidity(obj);
        Object objOpt = opt(str);
        if (objOpt == null) {
            if (obj instanceof JSONArray) {
                jSONArrayPut = new JSONArray();
            }
            put(str, obj);
            return this;
        }
        if (objOpt instanceof JSONArray) {
            ((JSONArray) objOpt).put(obj);
            return this;
        }
        jSONArrayPut = new JSONArray().put(objOpt);
        obj = jSONArrayPut.put(obj);
        put(str, obj);
        return this;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    public JSONObject append(String str, Object obj) throws JSONException {
        JSONArray jSONArray;
        testValidity(obj);
        Object objOpt = opt(str);
        if (objOpt == null) {
            jSONArray = new JSONArray();
        } else {
            if (!(objOpt instanceof JSONArray)) {
                throw new JSONException("JSONObject[" + str + "] is not a JSONArray.");
            }
            jSONArray = (JSONArray) objOpt;
        }
        put(str, jSONArray.put(obj));
        return this;
    }

    public Object get(String str) throws JSONException {
        Object objOpt = opt(str);
        if (objOpt != null) {
            return objOpt;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] not found.");
    }

    public boolean getBoolean(String str) throws JSONException {
        Object obj = get(str);
        if (obj.equals(Boolean.FALSE)) {
            return false;
        }
        boolean z = obj instanceof String;
        if (z && ((String) obj).equalsIgnoreCase("false")) {
            return false;
        }
        if (obj.equals(Boolean.TRUE)) {
            return true;
        }
        if (z && ((String) obj).equalsIgnoreCase("true")) {
            return true;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] is not a Boolean.");
    }

    public double getDouble(String str) throws JSONException {
        Object obj = get(str);
        try {
            return obj instanceof Number ? ((Number) obj).doubleValue() : Double.valueOf((String) obj).doubleValue();
        } catch (Exception unused) {
            throw new JSONException("JSONObject[" + quote(str) + "] is not a number.");
        }
    }

    public int getInt(String str) throws JSONException {
        Object obj = get(str);
        return obj instanceof Number ? ((Number) obj).intValue() : (int) getDouble(str);
    }

    public JSONArray getJSONArray(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof JSONArray) {
            return (JSONArray) obj;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] is not a JSONArray.");
    }

    public JSONObject getJSONObject(String str) throws JSONException {
        Object obj = get(str);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        throw new JSONException("JSONObject[" + quote(str) + "] is not a JSONObject.");
    }

    public long getLong(String str) throws JSONException {
        Object obj = get(str);
        return obj instanceof Number ? ((Number) obj).longValue() : (long) getDouble(str);
    }

    public String getString(String str) {
        return get(str).toString();
    }

    public boolean has(String str) {
        return this.map.containsKey(str);
    }

    public boolean isNull(String str) {
        return NULL.equals(opt(str));
    }

    public Iterator keys() {
        return this.map.keySet().iterator();
    }

    public int length() {
        return this.map.size();
    }

    public JSONArray names() {
        JSONArray jSONArray = new JSONArray();
        Iterator itKeys = keys();
        while (itKeys.hasNext()) {
            jSONArray.put(itKeys.next());
        }
        if (jSONArray.length() == 0) {
            return null;
        }
        return jSONArray;
    }

    public Object opt(String str) {
        if (str == null) {
            return null;
        }
        return this.map.get(str);
    }

    public boolean optBoolean(String str) {
        return optBoolean(str, false);
    }

    public boolean optBoolean(String str, boolean z) {
        try {
            return getBoolean(str);
        } catch (Exception unused) {
            return z;
        }
    }

    public double optDouble(String str) {
        return optDouble(str, Double.NaN);
    }

    public double optDouble(String str, double d) {
        try {
            Object objOpt = opt(str);
            return objOpt instanceof Number ? ((Number) objOpt).doubleValue() : new Double((String) objOpt).doubleValue();
        } catch (Exception unused) {
            return d;
        }
    }

    public int optInt(String str) {
        return optInt(str, 0);
    }

    public int optInt(String str, int i) {
        try {
            return getInt(str);
        } catch (Exception unused) {
            return i;
        }
    }

    public JSONArray optJSONArray(String str) {
        Object objOpt = opt(str);
        if (objOpt instanceof JSONArray) {
            return (JSONArray) objOpt;
        }
        return null;
    }

    public JSONObject optJSONObject(String str) {
        Object objOpt = opt(str);
        if (objOpt instanceof JSONObject) {
            return (JSONObject) objOpt;
        }
        return null;
    }

    public long optLong(String str) {
        return optLong(str, 0L);
    }

    public long optLong(String str, long j) {
        try {
            return getLong(str);
        } catch (Exception unused) {
            return j;
        }
    }

    public String optString(String str) {
        return optString(str, "");
    }

    public String optString(String str, String str2) {
        Object objOpt = opt(str);
        return objOpt != null ? objOpt.toString() : str2;
    }

    public JSONObject put(String str, double d) throws JSONException {
        put(str, new Double(d));
        return this;
    }

    public JSONObject put(String str, int i) throws JSONException {
        put(str, new Integer(i));
        return this;
    }

    public JSONObject put(String str, long j) throws JSONException {
        put(str, new Long(j));
        return this;
    }

    public JSONObject put(String str, Object obj) throws JSONException {
        if (str == null) {
            throw new JSONException("Null key.");
        }
        if (obj == null) {
            remove(str);
            return this;
        }
        testValidity(obj);
        this.map.put(str, obj);
        return this;
    }

    public JSONObject put(String str, Collection collection) throws JSONException {
        put(str, new JSONArray(collection));
        return this;
    }

    public JSONObject put(String str, Map map) throws JSONException {
        put(str, new JSONObject(map));
        return this;
    }

    public JSONObject put(String str, boolean z) throws JSONException {
        put(str, z ? Boolean.TRUE : Boolean.FALSE);
        return this;
    }

    public JSONObject putOnce(String str, Object obj) throws JSONException {
        if (str != null && obj != null) {
            if (opt(str) != null) {
                throw new JSONException("Duplicate key \"" + str + "\"");
            }
            put(str, obj);
        }
        return this;
    }

    public JSONObject putOpt(String str, Object obj) throws JSONException {
        if (str != null && obj != null) {
            put(str, obj);
        }
        return this;
    }

    public Object remove(String str) {
        return this.map.remove(str);
    }

    public Iterator sortedKeys() {
        return new TreeSet(this.map.keySet()).iterator();
    }

    public JSONArray toJSONArray(JSONArray jSONArray) {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        for (int i = 0; i < jSONArray.length(); i++) {
            jSONArray2.put(opt(jSONArray.getString(i)));
        }
        return jSONArray2;
    }

    public String toString() {
        try {
            Iterator itKeys = keys();
            StringBuffer stringBuffer = new StringBuffer("{");
            while (itKeys.hasNext()) {
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(',');
                }
                Object next = itKeys.next();
                stringBuffer.append(quote(next.toString()));
                stringBuffer.append(':');
                stringBuffer.append(valueToString(this.map.get(next)));
            }
            stringBuffer.append('}');
            return stringBuffer.toString();
        } catch (Exception unused) {
            return null;
        }
    }

    public String toString(int i) {
        return toString(i, 0);
    }

    String toString(int i, int i2) {
        int i3;
        int length = length();
        if (length == 0) {
            return "{}";
        }
        Iterator itSortedKeys = sortedKeys();
        StringBuffer stringBuffer = new StringBuffer("{");
        int i4 = i2 + i;
        if (length == 1) {
            Object next = itSortedKeys.next();
            stringBuffer.append(quote(next.toString()));
            stringBuffer.append(": ");
            stringBuffer.append(valueToString(this.map.get(next), i, i2));
        } else {
            while (true) {
                i3 = 0;
                if (!itSortedKeys.hasNext()) {
                    break;
                }
                Object next2 = itSortedKeys.next();
                if (stringBuffer.length() > 1) {
                    stringBuffer.append(",\n");
                } else {
                    stringBuffer.append('\n');
                }
                while (i3 < i4) {
                    stringBuffer.append(' ');
                    i3++;
                }
                stringBuffer.append(quote(next2.toString()));
                stringBuffer.append(": ");
                stringBuffer.append(valueToString(this.map.get(next2), i, i4));
            }
            if (stringBuffer.length() > 1) {
                stringBuffer.append('\n');
                while (i3 < i2) {
                    stringBuffer.append(' ');
                    i3++;
                }
            }
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    public Writer write(Writer writer) throws JSONException, IOException {
        boolean z = false;
        try {
            Iterator itKeys = keys();
            writer.write(123);
            while (itKeys.hasNext()) {
                if (z) {
                    writer.write(44);
                }
                Object next = itKeys.next();
                writer.write(quote(next.toString()));
                writer.write(58);
                Object obj = this.map.get(next);
                if (obj instanceof JSONObject) {
                    ((JSONObject) obj).write(writer);
                } else if (obj instanceof JSONArray) {
                    ((JSONArray) obj).write(writer);
                } else {
                    writer.write(valueToString(obj));
                }
                z = true;
            }
            writer.write(125);
            return writer;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }
}
