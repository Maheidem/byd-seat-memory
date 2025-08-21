package android.support.v7.widget;

/* loaded from: classes.dex */
class RtlSpacingHelper {
    public static final int UNDEFINED = Integer.MIN_VALUE;
    private int mLeft = 0;
    private int mRight = 0;
    private int mStart = Integer.MIN_VALUE;
    private int mEnd = Integer.MIN_VALUE;
    private int mExplicitLeft = 0;
    private int mExplicitRight = 0;
    private boolean mIsRtl = false;
    private boolean mIsRelative = false;

    RtlSpacingHelper() {
    }

    public int getEnd() {
        return this.mIsRtl ? this.mLeft : this.mRight;
    }

    public int getLeft() {
        return this.mLeft;
    }

    public int getRight() {
        return this.mRight;
    }

    public int getStart() {
        return this.mIsRtl ? this.mRight : this.mLeft;
    }

    public void setAbsolute(int i, int i2) {
        this.mIsRelative = false;
        if (i != Integer.MIN_VALUE) {
            this.mExplicitLeft = i;
            this.mLeft = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.mExplicitRight = i2;
            this.mRight = i2;
        }
    }

    public void setDirection(boolean z) {
        int i;
        if (z == this.mIsRtl) {
            return;
        }
        this.mIsRtl = z;
        if (this.mIsRelative) {
            if (z) {
                this.mLeft = this.mEnd != Integer.MIN_VALUE ? this.mEnd : this.mExplicitLeft;
                if (this.mStart != Integer.MIN_VALUE) {
                    i = this.mStart;
                }
            } else {
                this.mLeft = this.mStart != Integer.MIN_VALUE ? this.mStart : this.mExplicitLeft;
                if (this.mEnd != Integer.MIN_VALUE) {
                    i = this.mEnd;
                }
            }
            this.mRight = i;
        }
        this.mLeft = this.mExplicitLeft;
        i = this.mExplicitRight;
        this.mRight = i;
    }

    public void setRelative(int i, int i2) {
        this.mStart = i;
        this.mEnd = i2;
        this.mIsRelative = true;
        if (this.mIsRtl) {
            if (i2 != Integer.MIN_VALUE) {
                this.mLeft = i2;
            }
            if (i != Integer.MIN_VALUE) {
                this.mRight = i;
                return;
            }
            return;
        }
        if (i != Integer.MIN_VALUE) {
            this.mLeft = i;
        }
        if (i2 != Integer.MIN_VALUE) {
            this.mRight = i2;
        }
    }
}
