package org.json;

import java.io.IOException;
import java.io.Writer;

/* loaded from: classes.dex */
public class JSONWriter {
    private static final int maxdepth = 20;
    private boolean comma = false;
    protected char mode = 'i';
    private JSONObject[] stack = new JSONObject[20];
    private int top = 0;
    protected Writer writer;

    public JSONWriter(Writer writer) {
        this.writer = writer;
    }

    private JSONWriter append(String str) throws JSONException, IOException {
        if (str == null) {
            throw new JSONException("Null pointer");
        }
        if (this.mode != 'o' && this.mode != 'a') {
            throw new JSONException("Value out of sequence.");
        }
        try {
            if (this.comma && this.mode == 'a') {
                this.writer.write(44);
            }
            this.writer.write(str);
            if (this.mode == 'o') {
                this.mode = 'k';
            }
            this.comma = true;
            return this;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    private JSONWriter end(char c, char c2) throws JSONException, IOException {
        if (this.mode != c) {
            throw new JSONException(c == 'o' ? "Misplaced endObject." : "Misplaced endArray.");
        }
        pop(c);
        try {
            this.writer.write(c2);
            this.comma = true;
            return this;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    private void pop(char c) throws JSONException {
        if (this.top <= 0) {
            throw new JSONException("Nesting error.");
        }
        char c2 = 'k';
        if ((this.stack[this.top + (-1)] == null ? 'a' : 'k') != c) {
            throw new JSONException("Nesting error.");
        }
        this.top--;
        if (this.top == 0) {
            c2 = 'd';
        } else if (this.stack[this.top - 1] == null) {
            c2 = 'a';
        }
        this.mode = c2;
    }

    private void push(JSONObject jSONObject) throws JSONException {
        if (this.top >= 20) {
            throw new JSONException("Nesting too deep.");
        }
        this.stack[this.top] = jSONObject;
        this.mode = jSONObject == null ? 'a' : 'k';
        this.top++;
    }

    public JSONWriter array() throws JSONException, IOException {
        if (this.mode != 'i' && this.mode != 'o' && this.mode != 'a') {
            throw new JSONException("Misplaced array.");
        }
        push(null);
        append("[");
        this.comma = false;
        return this;
    }

    public JSONWriter endArray() {
        return end('a', ']');
    }

    public JSONWriter endObject() {
        return end('k', '}');
    }

    public JSONWriter key(String str) throws JSONException, IOException {
        if (str == null) {
            throw new JSONException("Null key.");
        }
        if (this.mode != 'k') {
            throw new JSONException("Misplaced key.");
        }
        try {
            if (this.comma) {
                this.writer.write(44);
            }
            this.stack[this.top - 1].putOnce(str, Boolean.TRUE);
            this.writer.write(JSONObject.quote(str));
            this.writer.write(58);
            this.comma = false;
            this.mode = 'o';
            return this;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    public JSONWriter object() throws JSONException, IOException {
        if (this.mode == 'i') {
            this.mode = 'o';
        }
        if (this.mode != 'o' && this.mode != 'a') {
            throw new JSONException("Misplaced object.");
        }
        append("{");
        push(new JSONObject());
        this.comma = false;
        return this;
    }

    public JSONWriter value(double d) {
        return value(new Double(d));
    }

    public JSONWriter value(long j) {
        return append(Long.toString(j));
    }

    public JSONWriter value(Object obj) {
        return append(JSONObject.valueToString(obj));
    }

    public JSONWriter value(boolean z) {
        return append(z ? "true" : "false");
    }
}
