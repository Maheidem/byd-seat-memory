package org.json;

import android.support.v7.widget.ActivityChooserView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/* loaded from: classes.dex */
public class JSONTokener {
    private int index;
    private char lastChar;
    private Reader reader;
    private boolean useLastChar;

    public JSONTokener(Reader reader) {
        this.reader = reader.markSupported() ? reader : new BufferedReader(reader);
        this.useLastChar = false;
        this.index = 0;
    }

    public JSONTokener(String str) {
        this(new StringReader(str));
    }

    public static int dehexchar(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c - '7';
        }
        if (c < 'a' || c > 'f') {
            return -1;
        }
        return c - 'W';
    }

    public void back() {
        if (this.useLastChar || this.index <= 0) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        this.index--;
        this.useLastChar = true;
    }

    public boolean more() {
        if (next() == 0) {
            return false;
        }
        back();
        return true;
    }

    public char next() throws JSONException, IOException {
        if (this.useLastChar) {
            this.useLastChar = false;
            if (this.lastChar != 0) {
                this.index++;
            }
            return this.lastChar;
        }
        try {
            int i = this.reader.read();
            if (i <= 0) {
                this.lastChar = (char) 0;
                return (char) 0;
            }
            this.index++;
            this.lastChar = (char) i;
            return this.lastChar;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    public char next(char c) throws JSONException, IOException {
        char next = next();
        if (next == c) {
            return next;
        }
        throw syntaxError("Expected '" + c + "' and instead saw '" + next + "'");
    }

    public String next(int i) throws JSONException, IOException {
        if (i == 0) {
            return "";
        }
        char[] cArr = new char[i];
        int i2 = 0;
        if (this.useLastChar) {
            this.useLastChar = false;
            cArr[0] = this.lastChar;
            i2 = 1;
        }
        while (i2 < i) {
            try {
                int i3 = this.reader.read(cArr, i2, i - i2);
                if (i3 == -1) {
                    break;
                }
                i2 += i3;
            } catch (IOException e) {
                throw new JSONException(e);
            }
        }
        this.index += i2;
        if (i2 < i) {
            throw syntaxError("Substring bounds error");
        }
        this.lastChar = cArr[i - 1];
        return new String(cArr);
    }

    public char nextClean() {
        char next;
        do {
            next = next();
            if (next == 0) {
                break;
            }
        } while (next <= ' ');
        return next;
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0064, code lost:
    
        throw syntaxError("Unterminated string");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String nextString(char r6) throws org.json.JSONException, java.io.IOException {
        /*
            r5 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
        L5:
            char r1 = r5.next()
            if (r1 == 0) goto L5e
            r2 = 10
            if (r1 == r2) goto L5e
            r3 = 13
            if (r1 == r3) goto L5e
            r4 = 92
            if (r1 == r4) goto L22
            if (r1 != r6) goto L1e
            java.lang.String r5 = r0.toString()
            return r5
        L1e:
            r0.append(r1)
            goto L5
        L22:
            char r1 = r5.next()
            r4 = 98
            if (r1 == r4) goto L5b
            r4 = 102(0x66, float:1.43E-43)
            if (r1 == r4) goto L58
            r4 = 110(0x6e, float:1.54E-43)
            if (r1 == r4) goto L54
            r2 = 114(0x72, float:1.6E-43)
            if (r1 == r2) goto L50
            r2 = 120(0x78, float:1.68E-43)
            r3 = 16
            if (r1 == r2) goto L4e
            switch(r1) {
                case 116: goto L4b;
                case 117: goto L40;
                default: goto L3f;
            }
        L3f:
            goto L1e
        L40:
            r1 = 4
        L41:
            java.lang.String r1 = r5.next(r1)
            int r1 = java.lang.Integer.parseInt(r1, r3)
            char r1 = (char) r1
            goto L1e
        L4b:
            r1 = 9
            goto L1e
        L4e:
            r1 = 2
            goto L41
        L50:
            r0.append(r3)
            goto L5
        L54:
            r0.append(r2)
            goto L5
        L58:
            r1 = 12
            goto L1e
        L5b:
            r1 = 8
            goto L1e
        L5e:
            java.lang.String r6 = "Unterminated string"
            org.json.JSONException r5 = r5.syntaxError(r6)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.json.JSONTokener.nextString(char):java.lang.String");
    }

    public String nextTo(char c) throws JSONException, IOException {
        char next;
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            next = next();
            if (next == c || next == 0 || next == '\n' || next == '\r') {
                break;
            }
            stringBuffer.append(next);
        }
        if (next != 0) {
            back();
        }
        return stringBuffer.toString().trim();
    }

    public String nextTo(String str) throws JSONException, IOException {
        char next;
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            next = next();
            if (str.indexOf(next) >= 0 || next == 0 || next == '\n' || next == '\r') {
                break;
            }
            stringBuffer.append(next);
        }
        if (next != 0) {
            back();
        }
        return stringBuffer.toString().trim();
    }

    public Object nextValue() {
        char cNextClean = nextClean();
        if (cNextClean != '\"') {
            if (cNextClean != '[') {
                if (cNextClean == '{') {
                    back();
                    return new JSONObject(this);
                }
                switch (cNextClean) {
                    case '\'':
                        break;
                    case '(':
                        break;
                    default:
                        StringBuffer stringBuffer = new StringBuffer();
                        while (cNextClean >= ' ' && ",:]}/\\\"[{;=#".indexOf(cNextClean) < 0) {
                            stringBuffer.append(cNextClean);
                            cNextClean = next();
                        }
                        back();
                        String strTrim = stringBuffer.toString().trim();
                        if (strTrim.equals("")) {
                            throw syntaxError("Missing value");
                        }
                        return JSONObject.stringToValue(strTrim);
                }
            }
            back();
            return new JSONArray(this);
        }
        return nextString(cNextClean);
    }

    public char skipTo(char c) throws JSONException, IOException {
        char next;
        try {
            int i = this.index;
            this.reader.mark(ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
            do {
                next = next();
                if (next == 0) {
                    this.reader.reset();
                    this.index = i;
                    return next;
                }
            } while (next != c);
            back();
            return next;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    public JSONException syntaxError(String str) {
        return new JSONException(str + toString());
    }

    public String toString() {
        return " at character " + this.index;
    }
}
