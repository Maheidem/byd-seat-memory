package com.google.gson.stream;

import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.bind.JsonTreeReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

/* loaded from: classes.dex */
public class JsonReader implements Closeable {
    private static final long MIN_INCOMPLETE_INTEGER = -922337203685477580L;
    private static final char[] NON_EXECUTE_PREFIX = ")]}'\n".toCharArray();
    private static final int NUMBER_CHAR_DECIMAL = 3;
    private static final int NUMBER_CHAR_DIGIT = 2;
    private static final int NUMBER_CHAR_EXP_DIGIT = 7;
    private static final int NUMBER_CHAR_EXP_E = 5;
    private static final int NUMBER_CHAR_EXP_SIGN = 6;
    private static final int NUMBER_CHAR_FRACTION_DIGIT = 4;
    private static final int NUMBER_CHAR_NONE = 0;
    private static final int NUMBER_CHAR_SIGN = 1;
    private static final int PEEKED_BEGIN_ARRAY = 3;
    private static final int PEEKED_BEGIN_OBJECT = 1;
    private static final int PEEKED_BUFFERED = 11;
    private static final int PEEKED_DOUBLE_QUOTED = 9;
    private static final int PEEKED_DOUBLE_QUOTED_NAME = 13;
    private static final int PEEKED_END_ARRAY = 4;
    private static final int PEEKED_END_OBJECT = 2;
    private static final int PEEKED_EOF = 17;
    private static final int PEEKED_FALSE = 6;
    private static final int PEEKED_LONG = 15;
    private static final int PEEKED_NONE = 0;
    private static final int PEEKED_NULL = 7;
    private static final int PEEKED_NUMBER = 16;
    private static final int PEEKED_SINGLE_QUOTED = 8;
    private static final int PEEKED_SINGLE_QUOTED_NAME = 12;
    private static final int PEEKED_TRUE = 5;
    private static final int PEEKED_UNQUOTED = 10;
    private static final int PEEKED_UNQUOTED_NAME = 14;
    private final Reader in;
    private int[] pathIndices;
    private String[] pathNames;
    private long peekedLong;
    private int peekedNumberLength;
    private String peekedString;
    private int stackSize;
    private boolean lenient = false;
    private final char[] buffer = new char[1024];
    private int pos = 0;
    private int limit = 0;
    private int lineNumber = 0;
    private int lineStart = 0;
    int peeked = 0;
    private int[] stack = new int[32];

    static {
        JsonReaderInternalAccess.INSTANCE = new JsonReaderInternalAccess() { // from class: com.google.gson.stream.JsonReader.1
            /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
            @Override // com.google.gson.internal.JsonReaderInternalAccess
            public void promoteNameToValue(JsonReader jsonReader) throws IOException {
                int i;
                if (jsonReader instanceof JsonTreeReader) {
                    ((JsonTreeReader) jsonReader).promoteNameToValue();
                    return;
                }
                int iDoPeek = jsonReader.peeked;
                if (iDoPeek == 0) {
                    iDoPeek = jsonReader.doPeek();
                }
                if (iDoPeek == 13) {
                    i = 9;
                } else if (iDoPeek == 12) {
                    i = 8;
                } else {
                    if (iDoPeek != 14) {
                        throw new IllegalStateException("Expected a name but was " + jsonReader.peek() + jsonReader.locationString());
                    }
                    i = 10;
                }
                jsonReader.peeked = i;
            }
        };
    }

    public JsonReader(Reader reader) {
        this.stackSize = 0;
        int[] iArr = this.stack;
        int i = this.stackSize;
        this.stackSize = i + 1;
        iArr[i] = 6;
        this.pathNames = new String[32];
        this.pathIndices = new int[32];
        if (reader == null) {
            throw new NullPointerException("in == null");
        }
        this.in = reader;
    }

    private void checkLenient() throws IOException {
        if (!this.lenient) {
            throw syntaxError("Use JsonReader.setLenient(true) to accept malformed JSON");
        }
    }

    private void consumeNonExecutePrefix() throws IOException {
        nextNonWhitespace(true);
        this.pos--;
        if (this.pos + NON_EXECUTE_PREFIX.length <= this.limit || fillBuffer(NON_EXECUTE_PREFIX.length)) {
            for (int i = 0; i < NON_EXECUTE_PREFIX.length; i++) {
                if (this.buffer[this.pos + i] != NON_EXECUTE_PREFIX[i]) {
                    return;
                }
            }
            this.pos += NON_EXECUTE_PREFIX.length;
        }
    }

    private boolean fillBuffer(int i) throws IOException {
        char[] cArr = this.buffer;
        this.lineStart -= this.pos;
        if (this.limit != this.pos) {
            this.limit -= this.pos;
            System.arraycopy(cArr, this.pos, cArr, 0, this.limit);
        } else {
            this.limit = 0;
        }
        this.pos = 0;
        do {
            int i2 = this.in.read(cArr, this.limit, cArr.length - this.limit);
            if (i2 == -1) {
                return false;
            }
            this.limit += i2;
            if (this.lineNumber == 0 && this.lineStart == 0 && this.limit > 0 && cArr[0] == 65279) {
                this.pos++;
                this.lineStart++;
                i++;
            }
        } while (this.limit < i);
        return true;
    }

    private boolean isLiteral(char c) throws IOException {
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
            case ',':
            case ':':
            case '[':
            case ']':
            case '{':
            case '}':
                return false;
            case '#':
            case '/':
            case ';':
            case '=':
            case '\\':
                checkLenient();
                return false;
            default:
                return true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0053, code lost:
    
        if (r1 != '/') goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0055, code lost:
    
        r7.pos = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0058, code lost:
    
        if (r4 != r2) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005a, code lost:
    
        r7.pos--;
        r2 = fillBuffer(2);
        r7.pos++;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0068, code lost:
    
        if (r2 != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x006a, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x006b, code lost:
    
        checkLenient();
        r2 = r0[r7.pos];
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0074, code lost:
    
        if (r2 == '*') goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0076, code lost:
    
        if (r2 == '/') goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0078, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0079, code lost:
    
        r7.pos++;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0082, code lost:
    
        r7.pos++;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x008d, code lost:
    
        if (skipTo("*\/") != false) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0095, code lost:
    
        throw syntaxError("Unterminated comment");
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x009d, code lost:
    
        if (r1 != '#') goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x009f, code lost:
    
        r7.pos = r4;
        checkLenient();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00a5, code lost:
    
        r7.pos = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00a7, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int nextNonWhitespace(boolean r8) throws java.io.IOException {
        /*
            r7 = this;
            char[] r0 = r7.buffer
        L2:
            int r1 = r7.pos
        L4:
            int r2 = r7.limit
        L6:
            r3 = 1
            if (r1 != r2) goto L34
            r7.pos = r1
            boolean r1 = r7.fillBuffer(r3)
            if (r1 != 0) goto L30
            if (r8 == 0) goto L2e
            java.io.EOFException r8 = new java.io.EOFException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "End of input"
            r0.append(r1)
            java.lang.String r7 = r7.locationString()
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            r8.<init>(r7)
            throw r8
        L2e:
            r7 = -1
            return r7
        L30:
            int r1 = r7.pos
            int r2 = r7.limit
        L34:
            int r4 = r1 + 1
            char r1 = r0[r1]
            r5 = 10
            if (r1 != r5) goto L44
            int r1 = r7.lineNumber
            int r1 = r1 + r3
            r7.lineNumber = r1
            r7.lineStart = r4
            goto La8
        L44:
            r5 = 32
            if (r1 == r5) goto La8
            r5 = 13
            if (r1 == r5) goto La8
            r5 = 9
            if (r1 != r5) goto L51
            goto La8
        L51:
            r5 = 47
            if (r1 != r5) goto L9b
            r7.pos = r4
            r6 = 2
            if (r4 != r2) goto L6b
            int r2 = r7.pos
            int r2 = r2 - r3
            r7.pos = r2
            boolean r2 = r7.fillBuffer(r6)
            int r4 = r7.pos
            int r4 = r4 + r3
            r7.pos = r4
            if (r2 != 0) goto L6b
            return r1
        L6b:
            r7.checkLenient()
            int r2 = r7.pos
            char r2 = r0[r2]
            r4 = 42
            if (r2 == r4) goto L82
            if (r2 == r5) goto L79
            return r1
        L79:
            int r1 = r7.pos
            int r1 = r1 + r3
            r7.pos = r1
        L7e:
            r7.skipToEndOfLine()
            goto L2
        L82:
            int r1 = r7.pos
            int r1 = r1 + r3
            r7.pos = r1
        */
        //  java.lang.String r1 = "*/"
        /*
            boolean r1 = r7.skipTo(r1)
            if (r1 != 0) goto L96
            java.lang.String r8 = "Unterminated comment"
            java.io.IOException r7 = r7.syntaxError(r8)
            throw r7
        L96:
            int r1 = r7.pos
            int r1 = r1 + r6
            goto L4
        L9b:
            r2 = 35
            if (r1 != r2) goto La5
            r7.pos = r4
            r7.checkLenient()
            goto L7e
        La5:
            r7.pos = r4
            return r1
        La8:
            r1 = r4
            goto L6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.stream.JsonReader.nextNonWhitespace(boolean):int");
    }

    private String nextQuotedValue(char c) throws IOException {
        char[] cArr = this.buffer;
        StringBuilder sb = null;
        while (true) {
            int i = this.pos;
            int i2 = this.limit;
            while (true) {
                if (i < i2) {
                    int i3 = i + 1;
                    char c2 = cArr[i];
                    if (c2 == c) {
                        this.pos = i3;
                        int i4 = (i3 - i) - 1;
                        if (sb == null) {
                            return new String(cArr, i, i4);
                        }
                        sb.append(cArr, i, i4);
                        return sb.toString();
                    }
                    if (c2 == '\\') {
                        this.pos = i3;
                        int i5 = (i3 - i) - 1;
                        if (sb == null) {
                            sb = new StringBuilder(Math.max((i5 + 1) * 2, 16));
                        }
                        sb.append(cArr, i, i5);
                        sb.append(readEscapeCharacter());
                    } else {
                        if (c2 == '\n') {
                            this.lineNumber++;
                            this.lineStart = i3;
                        }
                        i = i3;
                    }
                } else {
                    if (sb == null) {
                        sb = new StringBuilder(Math.max((i - i) * 2, 16));
                    }
                    sb.append(cArr, i, i - i);
                    this.pos = i;
                    if (!fillBuffer(1)) {
                        throw syntaxError("Unterminated string");
                    }
                }
            }
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:7:0x0012. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:21:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0058  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.String nextUnquotedValue() throws java.io.IOException {
        /*
            r5 = this;
            r0 = 0
            r1 = 0
            r2 = r1
        L3:
            r1 = r0
        L4:
            int r3 = r5.pos
            int r3 = r3 + r1
            int r4 = r5.limit
            if (r3 >= r4) goto L1c
            char[] r3 = r5.buffer
            int r4 = r5.pos
            int r4 = r4 + r1
            char r3 = r3[r4]
            switch(r3) {
                case 9: goto L2a;
                case 10: goto L2a;
                case 12: goto L2a;
                case 13: goto L2a;
                case 32: goto L2a;
                case 35: goto L18;
                case 44: goto L2a;
                case 47: goto L18;
                case 58: goto L2a;
                case 59: goto L18;
                case 61: goto L18;
                case 91: goto L2a;
                case 92: goto L18;
                case 93: goto L2a;
                case 123: goto L2a;
                case 125: goto L2a;
                default: goto L15;
            }
        L15:
            int r1 = r1 + 1
            goto L4
        L18:
            r5.checkLenient()
            goto L2a
        L1c:
            char[] r3 = r5.buffer
            int r3 = r3.length
            if (r1 >= r3) goto L2c
            int r3 = r1 + 1
            boolean r3 = r5.fillBuffer(r3)
            if (r3 == 0) goto L2a
            goto L4
        L2a:
            r0 = r1
            goto L4c
        L2c:
            if (r2 != 0) goto L39
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 16
            int r3 = java.lang.Math.max(r1, r3)
            r2.<init>(r3)
        L39:
            char[] r3 = r5.buffer
            int r4 = r5.pos
            r2.append(r3, r4, r1)
            int r3 = r5.pos
            int r3 = r3 + r1
            r5.pos = r3
            r1 = 1
            boolean r1 = r5.fillBuffer(r1)
            if (r1 != 0) goto L3
        L4c:
            if (r2 != 0) goto L58
            java.lang.String r1 = new java.lang.String
            char[] r2 = r5.buffer
            int r3 = r5.pos
            r1.<init>(r2, r3, r0)
            goto L63
        L58:
            char[] r1 = r5.buffer
            int r3 = r5.pos
            r2.append(r1, r3, r0)
            java.lang.String r1 = r2.toString()
        L63:
            int r2 = r5.pos
            int r2 = r2 + r0
            r5.pos = r2
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.stream.JsonReader.nextUnquotedValue():java.lang.String");
    }

    private int peekKeyword() {
        String str;
        String str2;
        int i;
        char c = this.buffer[this.pos];
        if (c == 't' || c == 'T') {
            str = "true";
            str2 = "TRUE";
            i = 5;
        } else if (c == 'f' || c == 'F') {
            str = "false";
            str2 = "FALSE";
            i = 6;
        } else {
            if (c != 'n' && c != 'N') {
                return 0;
            }
            str = "null";
            str2 = "NULL";
            i = 7;
        }
        int length = str.length();
        for (int i2 = 1; i2 < length; i2++) {
            if (this.pos + i2 >= this.limit && !fillBuffer(i2 + 1)) {
                return 0;
            }
            char c2 = this.buffer[this.pos + i2];
            if (c2 != str.charAt(i2) && c2 != str2.charAt(i2)) {
                return 0;
            }
        }
        if ((this.pos + length < this.limit || fillBuffer(length + 1)) && isLiteral(this.buffer[this.pos + length])) {
            return 0;
        }
        this.pos += length;
        this.peeked = i;
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:51:0x008f, code lost:
    
        if (isLiteral(r14) != false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0091, code lost:
    
        if (r9 != 2) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0093, code lost:
    
        if (r10 == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0099, code lost:
    
        if (r11 != Long.MIN_VALUE) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x009b, code lost:
    
        if (r13 == false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00a1, code lost:
    
        if (r11 != 0) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00a3, code lost:
    
        if (r13 != false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00a5, code lost:
    
        if (r13 == false) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00a8, code lost:
    
        r11 = -r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00a9, code lost:
    
        r19.peekedLong = r11;
        r19.pos += r3;
        r1 = 15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00b2, code lost:
    
        r19.peeked = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00b4, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00b5, code lost:
    
        if (r9 == 2) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00b8, code lost:
    
        if (r9 == 4) goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00bb, code lost:
    
        if (r9 != 7) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00be, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00c0, code lost:
    
        r19.peekedNumberLength = r3;
        r1 = 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00c5, code lost:
    
        return 0;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00e7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int peekNumber() {
        /*
            Method dump skipped, instructions count: 252
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.stream.JsonReader.peekNumber():int");
    }

    private void push(int i) {
        if (this.stackSize == this.stack.length) {
            int[] iArr = new int[this.stackSize * 2];
            int[] iArr2 = new int[this.stackSize * 2];
            String[] strArr = new String[this.stackSize * 2];
            System.arraycopy(this.stack, 0, iArr, 0, this.stackSize);
            System.arraycopy(this.pathIndices, 0, iArr2, 0, this.stackSize);
            System.arraycopy(this.pathNames, 0, strArr, 0, this.stackSize);
            this.stack = iArr;
            this.pathIndices = iArr2;
            this.pathNames = strArr;
        }
        int[] iArr3 = this.stack;
        int i2 = this.stackSize;
        this.stackSize = i2 + 1;
        iArr3[i2] = i;
    }

    private char readEscapeCharacter() throws IOException {
        int i;
        int i2;
        if (this.pos == this.limit && !fillBuffer(1)) {
            throw syntaxError("Unterminated escape sequence");
        }
        char[] cArr = this.buffer;
        int i3 = this.pos;
        this.pos = i3 + 1;
        char c = cArr[i3];
        if (c == '\n') {
            this.lineNumber++;
            this.lineStart = this.pos;
        } else if (c != '\"' && c != '\'' && c != '/' && c != '\\') {
            if (c == 'b') {
                return '\b';
            }
            if (c == 'f') {
                return '\f';
            }
            if (c == 'n') {
                return '\n';
            }
            if (c == 'r') {
                return '\r';
            }
            switch (c) {
                case 't':
                    return '\t';
                case 'u':
                    if (this.pos + 4 > this.limit && !fillBuffer(4)) {
                        throw syntaxError("Unterminated escape sequence");
                    }
                    char c2 = 0;
                    int i4 = this.pos;
                    int i5 = i4 + 4;
                    while (i4 < i5) {
                        char c3 = this.buffer[i4];
                        char c4 = (char) (c2 << 4);
                        if (c3 < '0' || c3 > '9') {
                            if (c3 >= 'a' && c3 <= 'f') {
                                i = c3 - 'a';
                            } else {
                                if (c3 < 'A' || c3 > 'F') {
                                    throw new NumberFormatException("\\u" + new String(this.buffer, this.pos, 4));
                                }
                                i = c3 - 'A';
                            }
                            i2 = i + 10;
                        } else {
                            i2 = c3 - '0';
                        }
                        c2 = (char) (c4 + i2);
                        i4++;
                    }
                    this.pos += 4;
                    return c2;
                default:
                    throw syntaxError("Invalid escape sequence");
            }
        }
        return c;
    }

    private void skipQuotedValue(char c) throws IOException {
        char[] cArr = this.buffer;
        while (true) {
            int i = this.pos;
            int i2 = this.limit;
            while (true) {
                if (i < i2) {
                    int i3 = i + 1;
                    char c2 = cArr[i];
                    if (c2 == c) {
                        this.pos = i3;
                        return;
                    }
                    if (c2 == '\\') {
                        this.pos = i3;
                        readEscapeCharacter();
                        break;
                    } else {
                        if (c2 == '\n') {
                            this.lineNumber++;
                            this.lineStart = i3;
                        }
                        i = i3;
                    }
                } else {
                    this.pos = i;
                    if (!fillBuffer(1)) {
                        throw syntaxError("Unterminated string");
                    }
                }
            }
        }
    }

    private boolean skipTo(String str) {
        int length = str.length();
        while (true) {
            if (this.pos + length > this.limit && !fillBuffer(length)) {
                return false;
            }
            if (this.buffer[this.pos] != '\n') {
                for (int i = 0; i < length; i++) {
                    if (this.buffer[this.pos + i] != str.charAt(i)) {
                        break;
                    }
                }
                return true;
            }
            this.lineNumber++;
            this.lineStart = this.pos + 1;
            this.pos++;
        }
    }

    private void skipToEndOfLine() {
        char c;
        do {
            if (this.pos >= this.limit && !fillBuffer(1)) {
                return;
            }
            char[] cArr = this.buffer;
            int i = this.pos;
            this.pos = i + 1;
            c = cArr[i];
            if (c == '\n') {
                this.lineNumber++;
                this.lineStart = this.pos;
                return;
            }
        } while (c != '\r');
    }

    private void skipUnquotedValue() throws IOException {
        do {
            int i = 0;
            while (this.pos + i < this.limit) {
                switch (this.buffer[this.pos + i]) {
                    case '\t':
                    case '\n':
                    case '\f':
                    case '\r':
                    case ' ':
                    case ',':
                    case ':':
                    case '[':
                    case ']':
                    case '{':
                    case '}':
                        break;
                    case '#':
                    case '/':
                    case ';':
                    case '=':
                    case '\\':
                        checkLenient();
                        break;
                    default:
                        i++;
                }
                this.pos += i;
                return;
            }
            this.pos += i;
        } while (fillBuffer(1));
    }

    private IOException syntaxError(String str) throws MalformedJsonException {
        throw new MalformedJsonException(str + locationString());
    }

    public void beginArray() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 3) {
            push(1);
            this.pathIndices[this.stackSize - 1] = 0;
            this.peeked = 0;
        } else {
            throw new IllegalStateException("Expected BEGIN_ARRAY but was " + peek() + locationString());
        }
    }

    public void beginObject() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 1) {
            push(3);
            this.peeked = 0;
        } else {
            throw new IllegalStateException("Expected BEGIN_OBJECT but was " + peek() + locationString());
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.peeked = 0;
        this.stack[0] = 8;
        this.stackSize = 1;
        this.in.close();
    }

    int doPeek() throws IOException {
        int i;
        int iNextNonWhitespace;
        int i2;
        int i3 = this.stack[this.stackSize - 1];
        if (i3 == 1) {
            this.stack[this.stackSize - 1] = 2;
        } else {
            if (i3 != 2) {
                if (i3 == 3 || i3 == 5) {
                    this.stack[this.stackSize - 1] = 4;
                    if (i3 == 5 && (iNextNonWhitespace = nextNonWhitespace(true)) != 44) {
                        if (iNextNonWhitespace != 59) {
                            if (iNextNonWhitespace != 125) {
                                throw syntaxError("Unterminated object");
                            }
                            this.peeked = 2;
                            return 2;
                        }
                        checkLenient();
                    }
                    int iNextNonWhitespace2 = nextNonWhitespace(true);
                    if (iNextNonWhitespace2 == 34) {
                        i = 13;
                    } else if (iNextNonWhitespace2 == 39) {
                        checkLenient();
                        i = 12;
                    } else {
                        if (iNextNonWhitespace2 == 125) {
                            if (i3 == 5) {
                                throw syntaxError("Expected name");
                            }
                            this.peeked = 2;
                            return 2;
                        }
                        checkLenient();
                        this.pos--;
                        if (!isLiteral((char) iNextNonWhitespace2)) {
                            throw syntaxError("Expected name");
                        }
                        i = 14;
                    }
                } else if (i3 == 4) {
                    this.stack[this.stackSize - 1] = 5;
                    int iNextNonWhitespace3 = nextNonWhitespace(true);
                    if (iNextNonWhitespace3 != 58) {
                        if (iNextNonWhitespace3 != 61) {
                            throw syntaxError("Expected ':'");
                        }
                        checkLenient();
                        if ((this.pos < this.limit || fillBuffer(1)) && this.buffer[this.pos] == '>') {
                            i2 = this.pos + 1;
                            this.pos = i2;
                        }
                    }
                } else if (i3 == 6) {
                    if (this.lenient) {
                        consumeNonExecutePrefix();
                    }
                    this.stack[this.stackSize - 1] = 7;
                } else if (i3 == 7) {
                    if (nextNonWhitespace(false) == -1) {
                        i = 17;
                    } else {
                        checkLenient();
                        i2 = this.pos - 1;
                        this.pos = i2;
                    }
                } else if (i3 == 8) {
                    throw new IllegalStateException("JsonReader is closed");
                }
                this.peeked = i;
                return i;
            }
            int iNextNonWhitespace4 = nextNonWhitespace(true);
            if (iNextNonWhitespace4 != 44) {
                if (iNextNonWhitespace4 != 59) {
                    if (iNextNonWhitespace4 != 93) {
                        throw syntaxError("Unterminated array");
                    }
                    this.peeked = 4;
                    return 4;
                }
                checkLenient();
            }
        }
        int iNextNonWhitespace5 = nextNonWhitespace(true);
        if (iNextNonWhitespace5 != 34) {
            if (iNextNonWhitespace5 == 39) {
                checkLenient();
                this.peeked = 8;
                return 8;
            }
            if (iNextNonWhitespace5 != 44 && iNextNonWhitespace5 != 59) {
                if (iNextNonWhitespace5 == 91) {
                    this.peeked = 3;
                    return 3;
                }
                if (iNextNonWhitespace5 != 93) {
                    if (iNextNonWhitespace5 == 123) {
                        this.peeked = 1;
                        return 1;
                    }
                    this.pos--;
                    int iPeekKeyword = peekKeyword();
                    if (iPeekKeyword != 0) {
                        return iPeekKeyword;
                    }
                    int iPeekNumber = peekNumber();
                    if (iPeekNumber != 0) {
                        return iPeekNumber;
                    }
                    if (!isLiteral(this.buffer[this.pos])) {
                        throw syntaxError("Expected value");
                    }
                    checkLenient();
                    i = 10;
                } else if (i3 == 1) {
                    this.peeked = 4;
                    return 4;
                }
            }
            if (i3 != 1 && i3 != 2) {
                throw syntaxError("Unexpected value");
            }
            checkLenient();
            this.pos--;
            this.peeked = 7;
            return 7;
        }
        i = 9;
        this.peeked = i;
        return i;
    }

    public void endArray() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek != 4) {
            throw new IllegalStateException("Expected END_ARRAY but was " + peek() + locationString());
        }
        this.stackSize--;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        this.peeked = 0;
    }

    public void endObject() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek != 2) {
            throw new IllegalStateException("Expected END_OBJECT but was " + peek() + locationString());
        }
        this.stackSize--;
        this.pathNames[this.stackSize] = null;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        this.peeked = 0;
    }

    public String getPath() {
        StringBuilder sb = new StringBuilder();
        sb.append('$');
        int i = this.stackSize;
        for (int i2 = 0; i2 < i; i2++) {
            switch (this.stack[i2]) {
                case 1:
                case 2:
                    sb.append('[');
                    sb.append(this.pathIndices[i2]);
                    sb.append(']');
                    break;
                case 3:
                case 4:
                case 5:
                    sb.append('.');
                    if (this.pathNames[i2] != null) {
                        sb.append(this.pathNames[i2]);
                        break;
                    } else {
                        break;
                    }
            }
        }
        return sb.toString();
    }

    public boolean hasNext() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        return (iDoPeek == 2 || iDoPeek == 4) ? false : true;
    }

    public final boolean isLenient() {
        return this.lenient;
    }

    String locationString() {
        return " at line " + (this.lineNumber + 1) + " column " + ((this.pos - this.lineStart) + 1) + " path " + getPath();
    }

    public boolean nextBoolean() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 5) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return true;
        }
        if (iDoPeek == 6) {
            this.peeked = 0;
            int[] iArr2 = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr2[i2] = iArr2[i2] + 1;
            return false;
        }
        throw new IllegalStateException("Expected a boolean but was " + peek() + locationString());
    }

    public double nextDouble() throws IOException, NumberFormatException {
        String strNextQuotedValue;
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 15) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return this.peekedLong;
        }
        if (iDoPeek == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (iDoPeek == 8 || iDoPeek == 9) {
                strNextQuotedValue = nextQuotedValue(iDoPeek == 8 ? '\'' : '\"');
            } else if (iDoPeek == 10) {
                strNextQuotedValue = nextUnquotedValue();
            } else if (iDoPeek != 11) {
                throw new IllegalStateException("Expected a double but was " + peek() + locationString());
            }
            this.peekedString = strNextQuotedValue;
        }
        this.peeked = 11;
        double d = Double.parseDouble(this.peekedString);
        if (!this.lenient && (Double.isNaN(d) || Double.isInfinite(d))) {
            throw new MalformedJsonException("JSON forbids NaN and infinities: " + d + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr2 = this.pathIndices;
        int i2 = this.stackSize - 1;
        iArr2[i2] = iArr2[i2] + 1;
        return d;
    }

    public int nextInt() throws IOException, NumberFormatException {
        String strNextQuotedValue;
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 15) {
            int i = (int) this.peekedLong;
            if (this.peekedLong != i) {
                throw new NumberFormatException("Expected an int but was " + this.peekedLong + locationString());
            }
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i2 = this.stackSize - 1;
            iArr[i2] = iArr[i2] + 1;
            return i;
        }
        if (iDoPeek == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (iDoPeek != 8 && iDoPeek != 9 && iDoPeek != 10) {
                throw new IllegalStateException("Expected an int but was " + peek() + locationString());
            }
            if (iDoPeek == 10) {
                strNextQuotedValue = nextUnquotedValue();
            } else {
                strNextQuotedValue = nextQuotedValue(iDoPeek == 8 ? '\'' : '\"');
            }
            this.peekedString = strNextQuotedValue;
            try {
                int i3 = Integer.parseInt(this.peekedString);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i4 = this.stackSize - 1;
                iArr2[i4] = iArr2[i4] + 1;
                return i3;
            } catch (NumberFormatException unused) {
            }
        }
        this.peeked = 11;
        double d = Double.parseDouble(this.peekedString);
        int i5 = (int) d;
        if (i5 != d) {
            throw new NumberFormatException("Expected an int but was " + this.peekedString + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr3 = this.pathIndices;
        int i6 = this.stackSize - 1;
        iArr3[i6] = iArr3[i6] + 1;
        return i5;
    }

    public long nextLong() throws IOException, NumberFormatException {
        String strNextQuotedValue;
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 15) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return this.peekedLong;
        }
        if (iDoPeek == 16) {
            this.peekedString = new String(this.buffer, this.pos, this.peekedNumberLength);
            this.pos += this.peekedNumberLength;
        } else {
            if (iDoPeek != 8 && iDoPeek != 9 && iDoPeek != 10) {
                throw new IllegalStateException("Expected a long but was " + peek() + locationString());
            }
            if (iDoPeek == 10) {
                strNextQuotedValue = nextUnquotedValue();
            } else {
                strNextQuotedValue = nextQuotedValue(iDoPeek == 8 ? '\'' : '\"');
            }
            this.peekedString = strNextQuotedValue;
            try {
                long j = Long.parseLong(this.peekedString);
                this.peeked = 0;
                int[] iArr2 = this.pathIndices;
                int i2 = this.stackSize - 1;
                iArr2[i2] = iArr2[i2] + 1;
                return j;
            } catch (NumberFormatException unused) {
            }
        }
        this.peeked = 11;
        double d = Double.parseDouble(this.peekedString);
        long j2 = (long) d;
        if (j2 != d) {
            throw new NumberFormatException("Expected a long but was " + this.peekedString + locationString());
        }
        this.peekedString = null;
        this.peeked = 0;
        int[] iArr3 = this.pathIndices;
        int i3 = this.stackSize - 1;
        iArr3[i3] = iArr3[i3] + 1;
        return j2;
    }

    public String nextName() throws IOException {
        char c;
        String strNextQuotedValue;
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 14) {
            strNextQuotedValue = nextUnquotedValue();
        } else {
            if (iDoPeek == 12) {
                c = '\'';
            } else {
                if (iDoPeek != 13) {
                    throw new IllegalStateException("Expected a name but was " + peek() + locationString());
                }
                c = '\"';
            }
            strNextQuotedValue = nextQuotedValue(c);
        }
        this.peeked = 0;
        this.pathNames[this.stackSize - 1] = strNextQuotedValue;
        return strNextQuotedValue;
    }

    public void nextNull() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 7) {
            this.peeked = 0;
            int[] iArr = this.pathIndices;
            int i = this.stackSize - 1;
            iArr[i] = iArr[i] + 1;
            return;
        }
        throw new IllegalStateException("Expected null but was " + peek() + locationString());
    }

    public String nextString() throws IOException {
        String str;
        char c;
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        if (iDoPeek == 10) {
            str = nextUnquotedValue();
        } else {
            if (iDoPeek == 8) {
                c = '\'';
            } else if (iDoPeek == 9) {
                c = '\"';
            } else if (iDoPeek == 11) {
                str = this.peekedString;
                this.peekedString = null;
            } else if (iDoPeek == 15) {
                str = Long.toString(this.peekedLong);
            } else {
                if (iDoPeek != 16) {
                    throw new IllegalStateException("Expected a string but was " + peek() + locationString());
                }
                str = new String(this.buffer, this.pos, this.peekedNumberLength);
                this.pos += this.peekedNumberLength;
            }
            str = nextQuotedValue(c);
        }
        this.peeked = 0;
        int[] iArr = this.pathIndices;
        int i = this.stackSize - 1;
        iArr[i] = iArr[i] + 1;
        return str;
    }

    public JsonToken peek() throws IOException {
        int iDoPeek = this.peeked;
        if (iDoPeek == 0) {
            iDoPeek = doPeek();
        }
        switch (iDoPeek) {
            case 1:
                return JsonToken.BEGIN_OBJECT;
            case 2:
                return JsonToken.END_OBJECT;
            case 3:
                return JsonToken.BEGIN_ARRAY;
            case 4:
                return JsonToken.END_ARRAY;
            case 5:
            case 6:
                return JsonToken.BOOLEAN;
            case 7:
                return JsonToken.NULL;
            case 8:
            case 9:
            case 10:
            case 11:
                return JsonToken.STRING;
            case 12:
            case 13:
            case 14:
                return JsonToken.NAME;
            case 15:
            case 16:
                return JsonToken.NUMBER;
            case 17:
                return JsonToken.END_DOCUMENT;
            default:
                throw new AssertionError();
        }
    }

    public final void setLenient(boolean z) {
        this.lenient = z;
    }

    public void skipValue() throws IOException {
        char c;
        int i = 0;
        do {
            int iDoPeek = this.peeked;
            if (iDoPeek == 0) {
                iDoPeek = doPeek();
            }
            if (iDoPeek == 3) {
                push(1);
            } else if (iDoPeek == 1) {
                push(3);
            } else {
                if (iDoPeek == 4 || iDoPeek == 2) {
                    this.stackSize--;
                    i--;
                } else if (iDoPeek == 14 || iDoPeek == 10) {
                    skipUnquotedValue();
                } else {
                    if (iDoPeek == 8 || iDoPeek == 12) {
                        c = '\'';
                    } else if (iDoPeek == 9 || iDoPeek == 13) {
                        c = '\"';
                    } else if (iDoPeek == 16) {
                        this.pos += this.peekedNumberLength;
                    }
                    skipQuotedValue(c);
                }
                this.peeked = 0;
            }
            i++;
            this.peeked = 0;
        } while (i != 0);
        int[] iArr = this.pathIndices;
        int i2 = this.stackSize - 1;
        iArr[i2] = iArr[i2] + 1;
        this.pathNames[this.stackSize - 1] = "null";
    }

    public String toString() {
        return getClass().getSimpleName() + locationString();
    }
}
