package org.json;

import java.util.HashMap;

/* loaded from: classes.dex */
public class XMLTokener extends JSONTokener {
    public static final HashMap entity = new HashMap(8);

    static {
        entity.put("amp", XML.AMP);
        entity.put("apos", XML.APOS);
        entity.put("gt", XML.GT);
        entity.put("lt", XML.LT);
        entity.put("quot", XML.QUOT);
    }

    public XMLTokener(String str) {
        super(str);
    }

    public String nextCDATA() throws JSONException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            char next = next();
            if (next == 0) {
                throw syntaxError("Unclosed CDATA");
            }
            stringBuffer.append(next);
            int length = stringBuffer.length() - 3;
            if (length >= 0 && stringBuffer.charAt(length) == ']' && stringBuffer.charAt(length + 1) == ']' && stringBuffer.charAt(length + 2) == '>') {
                stringBuffer.setLength(length);
                return stringBuffer.toString();
            }
        }
    }

    public Object nextContent() {
        char next;
        do {
            next = next();
        } while (Character.isWhitespace(next));
        if (next == 0) {
            return null;
        }
        if (next == '<') {
            return XML.LT;
        }
        StringBuffer stringBuffer = new StringBuffer();
        while (next != '<' && next != 0) {
            if (next == '&') {
                stringBuffer.append(nextEntity(next));
            } else {
                stringBuffer.append(next);
            }
            next = next();
        }
        back();
        return stringBuffer.toString().trim();
    }

    public Object nextEntity(char c) throws JSONException {
        char next;
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            next = next();
            if (!Character.isLetterOrDigit(next) && next != '#') {
                break;
            }
            stringBuffer.append(Character.toLowerCase(next));
        }
        if (next != ';') {
            throw syntaxError("Missing ';' in XML entity: &" + ((Object) stringBuffer));
        }
        String string = stringBuffer.toString();
        Object obj = entity.get(string);
        if (obj != null) {
            return obj;
        }
        return c + string + ";";
    }

    public Object nextMeta() throws JSONException {
        char next;
        char next2;
        do {
            next = next();
        } while (Character.isWhitespace(next));
        if (next == 0) {
            throw syntaxError("Misshaped meta tag");
        }
        if (next != '\'') {
            if (next == '/') {
                return XML.SLASH;
            }
            switch (next) {
                case '!':
                    return XML.BANG;
                case '\"':
                    break;
                default:
                    switch (next) {
                        case '<':
                            return XML.LT;
                        case '=':
                            return XML.EQ;
                        case '>':
                            return XML.GT;
                        case '?':
                            return XML.QUEST;
                    }
                    while (true) {
                        char next3 = next();
                        if (Character.isWhitespace(next3)) {
                            return Boolean.TRUE;
                        }
                        if (next3 != 0 && next3 != '\'' && next3 != '/') {
                            switch (next3) {
                                case '!':
                                case '\"':
                                    break;
                                default:
                                    switch (next3) {
                                    }
                            }
                        }
                    }
                    back();
                    return Boolean.TRUE;
            }
        }
        do {
            next2 = next();
            if (next2 == 0) {
                throw syntaxError("Unterminated string");
            }
        } while (next2 != next);
        return Boolean.TRUE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0046, code lost:
    
        back();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x004d, code lost:
    
        return r3.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0054, code lost:
    
        throw syntaxError("Bad character in a name");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.Object nextToken() throws org.json.JSONException {
        /*
            r5 = this;
        L0:
            char r0 = r5.next()
            boolean r1 = java.lang.Character.isWhitespace(r0)
            if (r1 != 0) goto L0
            if (r0 == 0) goto L99
            r1 = 39
            if (r0 == r1) goto L70
            r2 = 47
            if (r0 == r2) goto L6d
            switch(r0) {
                case 33: goto L6a;
                case 34: goto L70;
                default: goto L17;
            }
        L17:
            switch(r0) {
                case 60: goto L63;
                case 61: goto L60;
                case 62: goto L5d;
                case 63: goto L5a;
                default: goto L1a;
            }
        L1a:
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            r3.<init>()
        L1f:
            r3.append(r0)
            char r0 = r5.next()
            boolean r4 = java.lang.Character.isWhitespace(r0)
            if (r4 == 0) goto L31
            java.lang.String r5 = r3.toString()
            return r5
        L31:
            if (r0 == 0) goto L55
            if (r0 == r1) goto L4e
            if (r0 == r2) goto L46
            r4 = 91
            if (r0 == r4) goto L46
            r4 = 93
            if (r0 == r4) goto L46
            switch(r0) {
                case 33: goto L46;
                case 34: goto L4e;
                default: goto L42;
            }
        L42:
            switch(r0) {
                case 60: goto L4e;
                case 61: goto L46;
                case 62: goto L46;
                case 63: goto L46;
                default: goto L45;
            }
        L45:
            goto L1f
        L46:
            r5.back()
            java.lang.String r5 = r3.toString()
            return r5
        L4e:
            java.lang.String r0 = "Bad character in a name"
            org.json.JSONException r5 = r5.syntaxError(r0)
            throw r5
        L55:
            java.lang.String r5 = r3.toString()
            return r5
        L5a:
            java.lang.Character r5 = org.json.XML.QUEST
            return r5
        L5d:
            java.lang.Character r5 = org.json.XML.GT
            return r5
        L60:
            java.lang.Character r5 = org.json.XML.EQ
            return r5
        L63:
            java.lang.String r0 = "Misplaced '<'"
            org.json.JSONException r5 = r5.syntaxError(r0)
            throw r5
        L6a:
            java.lang.Character r5 = org.json.XML.BANG
            return r5
        L6d:
            java.lang.Character r5 = org.json.XML.SLASH
            return r5
        L70:
            java.lang.StringBuffer r1 = new java.lang.StringBuffer
            r1.<init>()
        L75:
            char r2 = r5.next()
            if (r2 != 0) goto L82
            java.lang.String r0 = "Unterminated string"
            org.json.JSONException r5 = r5.syntaxError(r0)
            throw r5
        L82:
            if (r2 != r0) goto L89
            java.lang.String r5 = r1.toString()
            return r5
        L89:
            r3 = 38
            if (r2 != r3) goto L95
            java.lang.Object r2 = r5.nextEntity(r2)
            r1.append(r2)
            goto L75
        L95:
            r1.append(r2)
            goto L75
        L99:
            java.lang.String r0 = "Misshaped element"
            org.json.JSONException r5 = r5.syntaxError(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.XMLTokener.nextToken():java.lang.Object");
    }

    public boolean skipPast(String str) {
        boolean z;
        int length = str.length();
        char[] cArr = new char[length];
        for (int i = 0; i < length; i++) {
            char next = next();
            if (next == 0) {
                return false;
            }
            cArr[i] = next;
        }
        int i2 = 0;
        while (true) {
            int i3 = 0;
            int i4 = i2;
            while (true) {
                if (i3 >= length) {
                    z = true;
                    break;
                }
                if (cArr[i4] != str.charAt(i3)) {
                    z = false;
                    break;
                }
                i4++;
                if (i4 >= length) {
                    i4 -= length;
                }
                i3++;
            }
            if (z) {
                return true;
            }
            char next2 = next();
            if (next2 == 0) {
                return false;
            }
            cArr[i2] = next2;
            i2++;
            if (i2 >= length) {
                i2 -= length;
            }
        }
    }
}
