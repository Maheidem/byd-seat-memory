package android.support.design.widget;

import android.R;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.text.TextDirectionHeuristicsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.TintTypedArray;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Interpolator;

/* loaded from: classes.dex */
final class CollapsingTextHelper {
    private static final boolean DEBUG_DRAW = false;
    private static final Paint DEBUG_DRAW_PAINT = null;
    private static final boolean USE_SCALING_TEXTURE;
    private boolean mBoundsChanged;
    private float mCollapsedDrawX;
    private float mCollapsedDrawY;
    private int mCollapsedShadowColor;
    private float mCollapsedShadowDx;
    private float mCollapsedShadowDy;
    private float mCollapsedShadowRadius;
    private ColorStateList mCollapsedTextColor;
    private Typeface mCollapsedTypeface;
    private float mCurrentDrawX;
    private float mCurrentDrawY;
    private float mCurrentTextSize;
    private Typeface mCurrentTypeface;
    private boolean mDrawTitle;
    private float mExpandedDrawX;
    private float mExpandedDrawY;
    private float mExpandedFraction;
    private int mExpandedShadowColor;
    private float mExpandedShadowDx;
    private float mExpandedShadowDy;
    private float mExpandedShadowRadius;
    private ColorStateList mExpandedTextColor;
    private Bitmap mExpandedTitleTexture;
    private Typeface mExpandedTypeface;
    private boolean mIsRtl;
    private Interpolator mPositionInterpolator;
    private float mScale;
    private int[] mState;
    private CharSequence mText;
    private Interpolator mTextSizeInterpolator;
    private CharSequence mTextToDraw;
    private float mTextureAscent;
    private float mTextureDescent;
    private Paint mTexturePaint;
    private boolean mUseTexture;
    private final View mView;
    private int mExpandedTextGravity = 16;
    private int mCollapsedTextGravity = 16;
    private float mExpandedTextSize = 15.0f;
    private float mCollapsedTextSize = 15.0f;
    private final TextPaint mTextPaint = new TextPaint(129);
    private final Rect mCollapsedBounds = new Rect();
    private final Rect mExpandedBounds = new Rect();
    private final RectF mCurrentBounds = new RectF();

    static {
        USE_SCALING_TEXTURE = Build.VERSION.SDK_INT < 18;
        if (DEBUG_DRAW_PAINT != null) {
            DEBUG_DRAW_PAINT.setAntiAlias(true);
            DEBUG_DRAW_PAINT.setColor(-65281);
        }
    }

    public CollapsingTextHelper(View view) {
        this.mView = view;
    }

    private static int blendColors(int i, int i2, float f) {
        float f2 = 1.0f - f;
        return Color.argb((int) ((Color.alpha(i) * f2) + (Color.alpha(i2) * f)), (int) ((Color.red(i) * f2) + (Color.red(i2) * f)), (int) ((Color.green(i) * f2) + (Color.green(i2) * f)), (int) ((Color.blue(i) * f2) + (Color.blue(i2) * f)));
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void calculateBaseOffsets() {
        /*
            Method dump skipped, instructions count: 261
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.CollapsingTextHelper.calculateBaseOffsets():void");
    }

    private void calculateCurrentOffsets() {
        calculateOffsets(this.mExpandedFraction);
    }

    private boolean calculateIsRtl(CharSequence charSequence) {
        return (ViewCompat.getLayoutDirection(this.mView) == 1 ? TextDirectionHeuristicsCompat.FIRSTSTRONG_RTL : TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR).isRtl(charSequence, 0, charSequence.length());
    }

    private void calculateOffsets(float f) {
        TextPaint textPaint;
        int currentCollapsedTextColor;
        interpolateBounds(f);
        this.mCurrentDrawX = lerp(this.mExpandedDrawX, this.mCollapsedDrawX, f, this.mPositionInterpolator);
        this.mCurrentDrawY = lerp(this.mExpandedDrawY, this.mCollapsedDrawY, f, this.mPositionInterpolator);
        setInterpolatedTextSize(lerp(this.mExpandedTextSize, this.mCollapsedTextSize, f, this.mTextSizeInterpolator));
        if (this.mCollapsedTextColor != this.mExpandedTextColor) {
            textPaint = this.mTextPaint;
            currentCollapsedTextColor = blendColors(getCurrentExpandedTextColor(), getCurrentCollapsedTextColor(), f);
        } else {
            textPaint = this.mTextPaint;
            currentCollapsedTextColor = getCurrentCollapsedTextColor();
        }
        textPaint.setColor(currentCollapsedTextColor);
        this.mTextPaint.setShadowLayer(lerp(this.mExpandedShadowRadius, this.mCollapsedShadowRadius, f, null), lerp(this.mExpandedShadowDx, this.mCollapsedShadowDx, f, null), lerp(this.mExpandedShadowDy, this.mCollapsedShadowDy, f, null), blendColors(this.mExpandedShadowColor, this.mCollapsedShadowColor, f));
        ViewCompat.postInvalidateOnAnimation(this.mView);
    }

    private void calculateUsingTextSize(float f) {
        float f2;
        boolean z;
        boolean z2;
        if (this.mText == null) {
            return;
        }
        float fWidth = this.mCollapsedBounds.width();
        float fWidth2 = this.mExpandedBounds.width();
        if (isClose(f, this.mCollapsedTextSize)) {
            float f3 = this.mCollapsedTextSize;
            this.mScale = 1.0f;
            if (this.mCurrentTypeface != this.mCollapsedTypeface) {
                this.mCurrentTypeface = this.mCollapsedTypeface;
                z2 = true;
            } else {
                z2 = false;
            }
            f2 = f3;
            z = z2;
        } else {
            f2 = this.mExpandedTextSize;
            if (this.mCurrentTypeface != this.mExpandedTypeface) {
                this.mCurrentTypeface = this.mExpandedTypeface;
                z = true;
            } else {
                z = false;
            }
            if (isClose(f, this.mExpandedTextSize)) {
                this.mScale = 1.0f;
            } else {
                this.mScale = f / this.mExpandedTextSize;
            }
            float f4 = this.mCollapsedTextSize / this.mExpandedTextSize;
            fWidth = fWidth2 * f4 > fWidth ? Math.min(fWidth / f4, fWidth2) : fWidth2;
        }
        if (fWidth > 0.0f) {
            z = this.mCurrentTextSize != f2 || this.mBoundsChanged || z;
            this.mCurrentTextSize = f2;
            this.mBoundsChanged = false;
        }
        if (this.mTextToDraw == null || z) {
            this.mTextPaint.setTextSize(this.mCurrentTextSize);
            this.mTextPaint.setTypeface(this.mCurrentTypeface);
            this.mTextPaint.setLinearText(this.mScale != 1.0f);
            CharSequence charSequenceEllipsize = TextUtils.ellipsize(this.mText, this.mTextPaint, fWidth, TextUtils.TruncateAt.END);
            if (TextUtils.equals(charSequenceEllipsize, this.mTextToDraw)) {
                return;
            }
            this.mTextToDraw = charSequenceEllipsize;
            this.mIsRtl = calculateIsRtl(this.mTextToDraw);
        }
    }

    private void clearTexture() {
        if (this.mExpandedTitleTexture != null) {
            this.mExpandedTitleTexture.recycle();
            this.mExpandedTitleTexture = null;
        }
    }

    private void ensureExpandedTexture() {
        if (this.mExpandedTitleTexture != null || this.mExpandedBounds.isEmpty() || TextUtils.isEmpty(this.mTextToDraw)) {
            return;
        }
        calculateOffsets(0.0f);
        this.mTextureAscent = this.mTextPaint.ascent();
        this.mTextureDescent = this.mTextPaint.descent();
        int iRound = Math.round(this.mTextPaint.measureText(this.mTextToDraw, 0, this.mTextToDraw.length()));
        int iRound2 = Math.round(this.mTextureDescent - this.mTextureAscent);
        if (iRound <= 0 || iRound2 <= 0) {
            return;
        }
        this.mExpandedTitleTexture = Bitmap.createBitmap(iRound, iRound2, Bitmap.Config.ARGB_8888);
        new Canvas(this.mExpandedTitleTexture).drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), 0.0f, iRound2 - this.mTextPaint.descent(), this.mTextPaint);
        if (this.mTexturePaint == null) {
            this.mTexturePaint = new Paint(3);
        }
    }

    @ColorInt
    private int getCurrentCollapsedTextColor() {
        return this.mState != null ? this.mCollapsedTextColor.getColorForState(this.mState, 0) : this.mCollapsedTextColor.getDefaultColor();
    }

    @ColorInt
    private int getCurrentExpandedTextColor() {
        return this.mState != null ? this.mExpandedTextColor.getColorForState(this.mState, 0) : this.mExpandedTextColor.getDefaultColor();
    }

    private void interpolateBounds(float f) {
        this.mCurrentBounds.left = lerp(this.mExpandedBounds.left, this.mCollapsedBounds.left, f, this.mPositionInterpolator);
        this.mCurrentBounds.top = lerp(this.mExpandedDrawY, this.mCollapsedDrawY, f, this.mPositionInterpolator);
        this.mCurrentBounds.right = lerp(this.mExpandedBounds.right, this.mCollapsedBounds.right, f, this.mPositionInterpolator);
        this.mCurrentBounds.bottom = lerp(this.mExpandedBounds.bottom, this.mCollapsedBounds.bottom, f, this.mPositionInterpolator);
    }

    private static boolean isClose(float f, float f2) {
        return Math.abs(f - f2) < 0.001f;
    }

    private static float lerp(float f, float f2, float f3, Interpolator interpolator) {
        if (interpolator != null) {
            f3 = interpolator.getInterpolation(f3);
        }
        return AnimationUtils.lerp(f, f2, f3);
    }

    private Typeface readFontFamilyTypeface(int i) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = this.mView.getContext().obtainStyledAttributes(i, new int[]{R.attr.fontFamily});
        try {
            String string = typedArrayObtainStyledAttributes.getString(0);
            if (string != null) {
                return Typeface.create(string, 0);
            }
            typedArrayObtainStyledAttributes.recycle();
            return null;
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private static boolean rectEquals(Rect rect, int i, int i2, int i3, int i4) {
        return rect.left == i && rect.top == i2 && rect.right == i3 && rect.bottom == i4;
    }

    private void setInterpolatedTextSize(float f) {
        calculateUsingTextSize(f);
        this.mUseTexture = USE_SCALING_TEXTURE && this.mScale != 1.0f;
        if (this.mUseTexture) {
            ensureExpandedTexture();
        }
        ViewCompat.postInvalidateOnAnimation(this.mView);
    }

    public void draw(Canvas canvas) {
        float fAscent;
        int iSave = canvas.save();
        if (this.mTextToDraw != null && this.mDrawTitle) {
            float f = this.mCurrentDrawX;
            float f2 = this.mCurrentDrawY;
            boolean z = this.mUseTexture && this.mExpandedTitleTexture != null;
            if (z) {
                fAscent = this.mTextureAscent * this.mScale;
                float f3 = this.mTextureDescent;
            } else {
                fAscent = this.mTextPaint.ascent() * this.mScale;
                this.mTextPaint.descent();
            }
            float f4 = this.mScale;
            if (z) {
                f2 += fAscent;
            }
            float f5 = f2;
            if (this.mScale != 1.0f) {
                canvas.scale(this.mScale, this.mScale, f, f5);
            }
            if (z) {
                canvas.drawBitmap(this.mExpandedTitleTexture, f, f5, this.mTexturePaint);
            } else {
                canvas.drawText(this.mTextToDraw, 0, this.mTextToDraw.length(), f, f5, this.mTextPaint);
            }
        }
        canvas.restoreToCount(iSave);
    }

    ColorStateList getCollapsedTextColor() {
        return this.mCollapsedTextColor;
    }

    int getCollapsedTextGravity() {
        return this.mCollapsedTextGravity;
    }

    float getCollapsedTextSize() {
        return this.mCollapsedTextSize;
    }

    Typeface getCollapsedTypeface() {
        return this.mCollapsedTypeface != null ? this.mCollapsedTypeface : Typeface.DEFAULT;
    }

    ColorStateList getExpandedTextColor() {
        return this.mExpandedTextColor;
    }

    int getExpandedTextGravity() {
        return this.mExpandedTextGravity;
    }

    float getExpandedTextSize() {
        return this.mExpandedTextSize;
    }

    Typeface getExpandedTypeface() {
        return this.mExpandedTypeface != null ? this.mExpandedTypeface : Typeface.DEFAULT;
    }

    float getExpansionFraction() {
        return this.mExpandedFraction;
    }

    CharSequence getText() {
        return this.mText;
    }

    final boolean isStateful() {
        if (this.mCollapsedTextColor == null || !this.mCollapsedTextColor.isStateful()) {
            return this.mExpandedTextColor != null && this.mExpandedTextColor.isStateful();
        }
        return true;
    }

    void onBoundsChanged() {
        this.mDrawTitle = this.mCollapsedBounds.width() > 0 && this.mCollapsedBounds.height() > 0 && this.mExpandedBounds.width() > 0 && this.mExpandedBounds.height() > 0;
    }

    public void recalculate() {
        if (this.mView.getHeight() <= 0 || this.mView.getWidth() <= 0) {
            return;
        }
        calculateBaseOffsets();
        calculateCurrentOffsets();
    }

    void setCollapsedBounds(int i, int i2, int i3, int i4) {
        if (rectEquals(this.mCollapsedBounds, i, i2, i3, i4)) {
            return;
        }
        this.mCollapsedBounds.set(i, i2, i3, i4);
        this.mBoundsChanged = true;
        onBoundsChanged();
    }

    void setCollapsedTextAppearance(int i) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), i, android.support.v7.appcompat.R.styleable.TextAppearance);
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor)) {
            this.mCollapsedTextColor = tintTypedArrayObtainStyledAttributes.getColorStateList(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor);
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.v7.appcompat.R.styleable.TextAppearance_android_textSize)) {
            this.mCollapsedTextSize = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.TextAppearance_android_textSize, (int) this.mCollapsedTextSize);
        }
        this.mCollapsedShadowColor = tintTypedArrayObtainStyledAttributes.getInt(android.support.v7.appcompat.R.styleable.TextAppearance_android_shadowColor, 0);
        this.mCollapsedShadowDx = tintTypedArrayObtainStyledAttributes.getFloat(android.support.v7.appcompat.R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.mCollapsedShadowDy = tintTypedArrayObtainStyledAttributes.getFloat(android.support.v7.appcompat.R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.mCollapsedShadowRadius = tintTypedArrayObtainStyledAttributes.getFloat(android.support.v7.appcompat.R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        tintTypedArrayObtainStyledAttributes.recycle();
        if (Build.VERSION.SDK_INT >= 16) {
            this.mCollapsedTypeface = readFontFamilyTypeface(i);
        }
        recalculate();
    }

    void setCollapsedTextColor(ColorStateList colorStateList) {
        if (this.mCollapsedTextColor != colorStateList) {
            this.mCollapsedTextColor = colorStateList;
            recalculate();
        }
    }

    void setCollapsedTextGravity(int i) {
        if (this.mCollapsedTextGravity != i) {
            this.mCollapsedTextGravity = i;
            recalculate();
        }
    }

    void setCollapsedTextSize(float f) {
        if (this.mCollapsedTextSize != f) {
            this.mCollapsedTextSize = f;
            recalculate();
        }
    }

    void setCollapsedTypeface(Typeface typeface) {
        if (this.mCollapsedTypeface != typeface) {
            this.mCollapsedTypeface = typeface;
            recalculate();
        }
    }

    void setExpandedBounds(int i, int i2, int i3, int i4) {
        if (rectEquals(this.mExpandedBounds, i, i2, i3, i4)) {
            return;
        }
        this.mExpandedBounds.set(i, i2, i3, i4);
        this.mBoundsChanged = true;
        onBoundsChanged();
    }

    void setExpandedTextAppearance(int i) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), i, android.support.v7.appcompat.R.styleable.TextAppearance);
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor)) {
            this.mExpandedTextColor = tintTypedArrayObtainStyledAttributes.getColorStateList(android.support.v7.appcompat.R.styleable.TextAppearance_android_textColor);
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(android.support.v7.appcompat.R.styleable.TextAppearance_android_textSize)) {
            this.mExpandedTextSize = tintTypedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.appcompat.R.styleable.TextAppearance_android_textSize, (int) this.mExpandedTextSize);
        }
        this.mExpandedShadowColor = tintTypedArrayObtainStyledAttributes.getInt(android.support.v7.appcompat.R.styleable.TextAppearance_android_shadowColor, 0);
        this.mExpandedShadowDx = tintTypedArrayObtainStyledAttributes.getFloat(android.support.v7.appcompat.R.styleable.TextAppearance_android_shadowDx, 0.0f);
        this.mExpandedShadowDy = tintTypedArrayObtainStyledAttributes.getFloat(android.support.v7.appcompat.R.styleable.TextAppearance_android_shadowDy, 0.0f);
        this.mExpandedShadowRadius = tintTypedArrayObtainStyledAttributes.getFloat(android.support.v7.appcompat.R.styleable.TextAppearance_android_shadowRadius, 0.0f);
        tintTypedArrayObtainStyledAttributes.recycle();
        if (Build.VERSION.SDK_INT >= 16) {
            this.mExpandedTypeface = readFontFamilyTypeface(i);
        }
        recalculate();
    }

    void setExpandedTextColor(ColorStateList colorStateList) {
        if (this.mExpandedTextColor != colorStateList) {
            this.mExpandedTextColor = colorStateList;
            recalculate();
        }
    }

    void setExpandedTextGravity(int i) {
        if (this.mExpandedTextGravity != i) {
            this.mExpandedTextGravity = i;
            recalculate();
        }
    }

    void setExpandedTextSize(float f) {
        if (this.mExpandedTextSize != f) {
            this.mExpandedTextSize = f;
            recalculate();
        }
    }

    void setExpandedTypeface(Typeface typeface) {
        if (this.mExpandedTypeface != typeface) {
            this.mExpandedTypeface = typeface;
            recalculate();
        }
    }

    void setExpansionFraction(float f) {
        float fConstrain = MathUtils.constrain(f, 0.0f, 1.0f);
        if (fConstrain != this.mExpandedFraction) {
            this.mExpandedFraction = fConstrain;
            calculateCurrentOffsets();
        }
    }

    void setPositionInterpolator(Interpolator interpolator) {
        this.mPositionInterpolator = interpolator;
        recalculate();
    }

    final boolean setState(int[] iArr) {
        this.mState = iArr;
        if (!isStateful()) {
            return false;
        }
        recalculate();
        return true;
    }

    void setText(CharSequence charSequence) {
        if (charSequence == null || !charSequence.equals(this.mText)) {
            this.mText = charSequence;
            this.mTextToDraw = null;
            clearTexture();
            recalculate();
        }
    }

    void setTextSizeInterpolator(Interpolator interpolator) {
        this.mTextSizeInterpolator = interpolator;
        recalculate();
    }

    void setTypefaces(Typeface typeface) {
        this.mExpandedTypeface = typeface;
        this.mCollapsedTypeface = typeface;
        recalculate();
    }
}
