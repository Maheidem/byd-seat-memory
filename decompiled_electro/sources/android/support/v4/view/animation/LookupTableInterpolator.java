package android.support.v4.view.animation;

import android.view.animation.Interpolator;

/* loaded from: classes.dex */
abstract class LookupTableInterpolator implements Interpolator {
    private final float mStepSize;
    private final float[] mValues;

    public LookupTableInterpolator(float[] fArr) {
        this.mValues = fArr;
        this.mStepSize = 1.0f / (this.mValues.length - 1);
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        if (f >= 1.0f) {
            return 1.0f;
        }
        if (f <= 0.0f) {
            return 0.0f;
        }
        int iMin = Math.min((int) ((this.mValues.length - 1) * f), this.mValues.length - 2);
        return this.mValues[iMin] + (((f - (iMin * this.mStepSize)) / this.mStepSize) * (this.mValues[iMin + 1] - this.mValues[iMin]));
    }
}
