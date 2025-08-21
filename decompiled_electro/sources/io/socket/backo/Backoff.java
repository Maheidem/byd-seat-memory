package io.socket.backo;

import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes.dex */
public class Backoff {
    private int attempts;
    private double jitter;
    private long ms = 100;
    private long max = 10000;
    private int factor = 2;

    public long duration() {
        BigInteger bigIntegerValueOf = BigInteger.valueOf(this.ms);
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(this.factor);
        int i = this.attempts;
        this.attempts = i + 1;
        BigInteger bigIntegerMultiply = bigIntegerValueOf.multiply(bigIntegerValueOf2.pow(i));
        if (this.jitter != 0.0d) {
            double dRandom = Math.random();
            BigInteger bigInteger = BigDecimal.valueOf(dRandom).multiply(BigDecimal.valueOf(this.jitter)).multiply(new BigDecimal(bigIntegerMultiply)).toBigInteger();
            bigIntegerMultiply = (((int) Math.floor(dRandom * 10.0d)) & 1) == 0 ? bigIntegerMultiply.subtract(bigInteger) : bigIntegerMultiply.add(bigInteger);
        }
        return bigIntegerMultiply.min(BigInteger.valueOf(this.max)).max(BigInteger.valueOf(this.ms)).longValue();
    }

    public int getAttempts() {
        return this.attempts;
    }

    public void reset() {
        this.attempts = 0;
    }

    public Backoff setFactor(int i) {
        this.factor = i;
        return this;
    }

    public Backoff setJitter(double d) {
        if (!(d >= 0.0d && d < 1.0d)) {
            throw new IllegalArgumentException("jitter must be between 0 and 1");
        }
        this.jitter = d;
        return this;
    }

    public Backoff setMax(long j) {
        this.max = j;
        return this;
    }

    public Backoff setMin(long j) {
        this.ms = j;
        return this;
    }
}
