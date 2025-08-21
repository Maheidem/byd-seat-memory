package android.support.v4.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* loaded from: classes.dex */
final class SwipeProgressBar {
    private static final int ANIMATION_DURATION_MS = 2000;
    private static final int COLOR1 = -1291845632;
    private static final int COLOR2 = Integer.MIN_VALUE;
    private static final int COLOR3 = 1291845632;
    private static final int COLOR4 = 436207616;
    private static final int FINISH_ANIMATION_DURATION_MS = 1000;
    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private long mFinishTime;
    private View mParent;
    private boolean mRunning;
    private long mStartTime;
    private float mTriggerPercentage;
    private final Paint mPaint = new Paint();
    private final RectF mClipRect = new RectF();
    private Rect mBounds = new Rect();
    private int mColor1 = COLOR1;
    private int mColor2 = Integer.MIN_VALUE;
    private int mColor3 = COLOR3;
    private int mColor4 = COLOR4;

    SwipeProgressBar(View view) {
        this.mParent = view;
    }

    private void drawCircle(Canvas canvas, float f, float f2, int i, float f3) {
        this.mPaint.setColor(i);
        canvas.save();
        canvas.translate(f, f2);
        float interpolation = INTERPOLATOR.getInterpolation(f3);
        canvas.scale(interpolation, interpolation);
        canvas.drawCircle(0.0f, 0.0f, f, this.mPaint);
        canvas.restore();
    }

    private void drawTrigger(Canvas canvas, int i, int i2) {
        this.mPaint.setColor(this.mColor1);
        float f = i;
        canvas.drawCircle(f, i2, this.mTriggerPercentage * f, this.mPaint);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x009d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void draw(android.graphics.Canvas r22) {
        /*
            Method dump skipped, instructions count: 362
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.SwipeProgressBar.draw(android.graphics.Canvas):void");
    }

    boolean isRunning() {
        return this.mRunning || this.mFinishTime > 0;
    }

    void setBounds(int i, int i2, int i3, int i4) {
        this.mBounds.left = i;
        this.mBounds.top = i2;
        this.mBounds.right = i3;
        this.mBounds.bottom = i4;
    }

    void setColorScheme(int i, int i2, int i3, int i4) {
        this.mColor1 = i;
        this.mColor2 = i2;
        this.mColor3 = i3;
        this.mColor4 = i4;
    }

    void setTriggerPercentage(float f) {
        this.mTriggerPercentage = f;
        this.mStartTime = 0L;
        ViewCompat.postInvalidateOnAnimation(this.mParent, this.mBounds.left, this.mBounds.top, this.mBounds.right, this.mBounds.bottom);
    }

    void start() {
        if (this.mRunning) {
            return;
        }
        this.mTriggerPercentage = 0.0f;
        this.mStartTime = AnimationUtils.currentAnimationTimeMillis();
        this.mRunning = true;
        this.mParent.postInvalidate();
    }

    void stop() {
        if (this.mRunning) {
            this.mTriggerPercentage = 0.0f;
            this.mFinishTime = AnimationUtils.currentAnimationTimeMillis();
            this.mRunning = false;
            this.mParent.postInvalidate();
        }
    }
}
