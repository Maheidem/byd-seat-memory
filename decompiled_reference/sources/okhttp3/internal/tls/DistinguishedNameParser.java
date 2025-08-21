package okhttp3.internal.tls;

import javax.security.auth.x500.X500Principal;

/* loaded from: classes.dex */
final class DistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;
    private final String dn;
    private int end;
    private final int length;
    private int pos;

    DistinguishedNameParser(X500Principal x500Principal) {
        this.dn = x500Principal.getName("RFC2253");
        this.length = this.dn.length();
    }

    private String escapedAV() {
        this.beg = this.pos;
        this.end = this.pos;
        while (this.pos < this.length) {
            char c = this.chars[this.pos];
            if (c != ' ') {
                if (c != ';') {
                    if (c != '\\') {
                        switch (c) {
                            case '+':
                            case ',':
                                break;
                            default:
                                char[] cArr = this.chars;
                                int i = this.end;
                                this.end = i + 1;
                                cArr[i] = this.chars[this.pos];
                                break;
                        }
                        return new String(this.chars, this.beg, this.end - this.beg);
                    }
                    char[] cArr2 = this.chars;
                    int i2 = this.end;
                    this.end = i2 + 1;
                    cArr2[i2] = getEscaped();
                    this.pos++;
                }
                return new String(this.chars, this.beg, this.end - this.beg);
            }
            this.cur = this.end;
            this.pos++;
            char[] cArr3 = this.chars;
            int i3 = this.end;
            this.end = i3 + 1;
            cArr3[i3] = ' ';
            while (this.pos < this.length && this.chars[this.pos] == ' ') {
                char[] cArr4 = this.chars;
                int i4 = this.end;
                this.end = i4 + 1;
                cArr4[i4] = ' ';
                this.pos++;
            }
            if (this.pos == this.length || this.chars[this.pos] == ',' || this.chars[this.pos] == '+' || this.chars[this.pos] == ';') {
                return new String(this.chars, this.beg, this.cur - this.beg);
            }
        }
        return new String(this.chars, this.beg, this.end - this.beg);
    }

    private int getByte(int i) {
        int i2;
        int i3;
        int i4 = i + 1;
        if (i4 >= this.length) {
            throw new IllegalStateException("Malformed DN: " + this.dn);
        }
        char c = this.chars[i];
        if (c >= '0' && c <= '9') {
            i2 = c - '0';
        } else if (c >= 'a' && c <= 'f') {
            i2 = c - 'W';
        } else {
            if (c < 'A' || c > 'F') {
                throw new IllegalStateException("Malformed DN: " + this.dn);
            }
            i2 = c - '7';
        }
        char c2 = this.chars[i4];
        if (c2 >= '0' && c2 <= '9') {
            i3 = c2 - '0';
        } else if (c2 >= 'a' && c2 <= 'f') {
            i3 = c2 - 'W';
        } else {
            if (c2 < 'A' || c2 > 'F') {
                throw new IllegalStateException("Malformed DN: " + this.dn);
            }
            i3 = c2 - '7';
        }
        return (i2 << 4) + i3;
    }

    private char getEscaped() {
        this.pos++;
        if (this.pos == this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        char c = this.chars[this.pos];
        if (c != ' ' && c != '%' && c != '\\' && c != '_') {
            switch (c) {
                case '\"':
                case '#':
                    break;
                default:
                    switch (c) {
                        case '*':
                        case '+':
                        case ',':
                            break;
                        default:
                            switch (c) {
                                case ';':
                                case '<':
                                case '=':
                                case '>':
                                    break;
                                default:
                                    return getUTF8();
                            }
                    }
            }
        }
        return this.chars[this.pos];
    }

    private char getUTF8() {
        int i;
        int i2;
        int i3 = getByte(this.pos);
        this.pos++;
        if (i3 < 128) {
            return (char) i3;
        }
        if (i3 < 192 || i3 > 247) {
            return '?';
        }
        if (i3 <= 223) {
            i2 = i3 & 31;
            i = 1;
        } else if (i3 <= 239) {
            i = 2;
            i2 = i3 & 15;
        } else {
            i = 3;
            i2 = i3 & 7;
        }
        for (int i4 = 0; i4 < i; i4++) {
            this.pos++;
            if (this.pos == this.length || this.chars[this.pos] != '\\') {
                return '?';
            }
            this.pos++;
            int i5 = getByte(this.pos);
            this.pos++;
            if ((i5 & 192) != 128) {
                return '?';
            }
            i2 = (i2 << 6) + (i5 & 63);
        }
        return (char) i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0094, code lost:
    
        r5.end = r5.pos;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.String hexAV() {
        /*
            Method dump skipped, instructions count: 224
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.tls.DistinguishedNameParser.hexAV():java.lang.String");
    }

    private String nextAT() {
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            this.pos++;
        }
        if (this.pos == this.length) {
            return null;
        }
        this.beg = this.pos;
        do {
            this.pos++;
            if (this.pos >= this.length || this.chars[this.pos] == '=') {
                break;
            }
        } while (this.chars[this.pos] != ' ');
        if (this.pos >= this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        this.end = this.pos;
        if (this.chars[this.pos] == ' ') {
            while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] == ' ') {
                this.pos++;
            }
            if (this.chars[this.pos] != '=' || this.pos == this.length) {
                throw new IllegalStateException("Unexpected end of DN: " + this.dn);
            }
        }
        do {
            this.pos++;
            if (this.pos >= this.length) {
                break;
            }
        } while (this.chars[this.pos] == ' ');
        if (this.end - this.beg > 4 && this.chars[this.beg + 3] == '.' && ((this.chars[this.beg] == 'O' || this.chars[this.beg] == 'o') && ((this.chars[this.beg + 1] == 'I' || this.chars[this.beg + 1] == 'i') && (this.chars[this.beg + 2] == 'D' || this.chars[this.beg + 2] == 'd')))) {
            this.beg += 4;
        }
        return new String(this.chars, this.beg, this.end - this.beg);
    }

    private String quotedAV() {
        this.pos++;
        this.beg = this.pos;
        int i = this.beg;
        while (true) {
            this.end = i;
            if (this.pos == this.length) {
                throw new IllegalStateException("Unexpected end of DN: " + this.dn);
            }
            if (this.chars[this.pos] == '\"') {
                do {
                    this.pos++;
                    if (this.pos >= this.length) {
                        break;
                    }
                } while (this.chars[this.pos] == ' ');
                return new String(this.chars, this.beg, this.end - this.beg);
            }
            if (this.chars[this.pos] == '\\') {
                this.chars[this.end] = getEscaped();
            } else {
                this.chars[this.end] = this.chars[this.pos];
            }
            this.pos++;
            i = this.end + 1;
        }
    }

    public String findMostSpecific(String str) {
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.dn.toCharArray();
        String strNextAT = nextAT();
        if (strNextAT == null) {
            return null;
        }
        do {
            String strQuotedAV = "";
            if (this.pos == this.length) {
                return null;
            }
            switch (this.chars[this.pos]) {
                case '\"':
                    strQuotedAV = quotedAV();
                    break;
                case '#':
                    strQuotedAV = hexAV();
                    break;
                case '+':
                case ',':
                case ';':
                    break;
                default:
                    strQuotedAV = escapedAV();
                    break;
            }
            if (str.equalsIgnoreCase(strNextAT)) {
                return strQuotedAV;
            }
            if (this.pos >= this.length) {
                return null;
            }
            if (this.chars[this.pos] != ',' && this.chars[this.pos] != ';' && this.chars[this.pos] != '+') {
                throw new IllegalStateException("Malformed DN: " + this.dn);
            }
            this.pos++;
            strNextAT = nextAT();
        } while (strNextAT != null);
        throw new IllegalStateException("Malformed DN: " + this.dn);
    }
}
