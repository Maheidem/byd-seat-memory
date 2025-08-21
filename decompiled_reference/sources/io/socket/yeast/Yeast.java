package io.socket.yeast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class Yeast {
    private static char[] alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_".toCharArray();
    private static int length = alphabet.length;
    private static Map<Character, Integer> map = new HashMap(length);
    private static String prev;
    private static int seed;

    static {
        for (int i = 0; i < length; i++) {
            map.put(Character.valueOf(alphabet[i]), Integer.valueOf(i));
        }
    }

    private Yeast() {
    }

    public static long decode(String str) {
        long jIntValue = 0;
        for (int i = 0; i < str.toCharArray().length; i++) {
            jIntValue = (jIntValue * length) + map.get(Character.valueOf(r7[i])).intValue();
        }
        return jIntValue;
    }

    public static String encode(long j) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.insert(0, alphabet[(int) (j % length)]);
            j /= length;
        } while (j > 0);
        return sb.toString();
    }

    public static String yeast() {
        String strEncode = encode(new Date().getTime());
        if (!strEncode.equals(prev)) {
            seed = 0;
            prev = strEncode;
            return strEncode;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(strEncode);
        sb.append(".");
        int i = seed;
        seed = i + 1;
        sb.append(encode(i));
        return sb.toString();
    }
}
