package com.google.zxing.oned.rss.expanded.decoders;

/* loaded from: classes.dex */
final class CurrentParsingState {
    private int position = 0;
    private State encoding = State.NUMERIC;

    private enum State {
        NUMERIC,
        ALPHA,
        ISO_IEC_646
    }

    CurrentParsingState() {
    }

    int getPosition() {
        return this.position;
    }

    void incrementPosition(int i) {
        this.position += i;
    }

    boolean isAlpha() {
        return this.encoding == State.ALPHA;
    }

    boolean isIsoIec646() {
        return this.encoding == State.ISO_IEC_646;
    }

    boolean isNumeric() {
        return this.encoding == State.NUMERIC;
    }

    void setAlpha() {
        this.encoding = State.ALPHA;
    }

    void setIsoIec646() {
        this.encoding = State.ISO_IEC_646;
    }

    void setNumeric() {
        this.encoding = State.NUMERIC;
    }

    void setPosition(int i) {
        this.position = i;
    }
}
