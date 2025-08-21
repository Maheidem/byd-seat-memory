package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class DrawableCompat {
    static final DrawableImpl IMPL;

    static class BaseDrawableImpl implements DrawableImpl {
        BaseDrawableImpl() {
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void applyTheme(Drawable drawable, Resources.Theme theme) {
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public boolean canApplyTheme(Drawable drawable) {
            return false;
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void clearColorFilter(Drawable drawable) {
            drawable.clearColorFilter();
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public int getAlpha(Drawable drawable) {
            return 0;
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public ColorFilter getColorFilter(Drawable drawable) {
            return null;
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public int getLayoutDirection(Drawable drawable) {
            return 0;
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void inflate(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
            DrawableCompatBase.inflate(drawable, resources, xmlPullParser, attributeSet, theme);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public boolean isAutoMirrored(Drawable drawable) {
            return false;
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void jumpToCurrentState(Drawable drawable) {
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setAutoMirrored(Drawable drawable, boolean z) {
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setHotspot(Drawable drawable, float f, float f2) {
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4) {
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public boolean setLayoutDirection(Drawable drawable, int i) {
            return false;
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setTint(Drawable drawable, int i) {
            DrawableCompatBase.setTint(drawable, i);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setTintList(Drawable drawable, ColorStateList colorStateList) {
            DrawableCompatBase.setTintList(drawable, colorStateList);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setTintMode(Drawable drawable, PorterDuff.Mode mode) {
            DrawableCompatBase.setTintMode(drawable, mode);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public Drawable wrap(Drawable drawable) {
            return DrawableCompatBase.wrapForTinting(drawable);
        }
    }

    interface DrawableImpl {
        void applyTheme(Drawable drawable, Resources.Theme theme);

        boolean canApplyTheme(Drawable drawable);

        void clearColorFilter(Drawable drawable);

        int getAlpha(Drawable drawable);

        ColorFilter getColorFilter(Drawable drawable);

        int getLayoutDirection(Drawable drawable);

        void inflate(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme);

        boolean isAutoMirrored(Drawable drawable);

        void jumpToCurrentState(Drawable drawable);

        void setAutoMirrored(Drawable drawable, boolean z);

        void setHotspot(Drawable drawable, float f, float f2);

        void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4);

        boolean setLayoutDirection(Drawable drawable, int i);

        void setTint(Drawable drawable, int i);

        void setTintList(Drawable drawable, ColorStateList colorStateList);

        void setTintMode(Drawable drawable, PorterDuff.Mode mode);

        Drawable wrap(Drawable drawable);
    }

    static class HoneycombDrawableImpl extends BaseDrawableImpl {
        HoneycombDrawableImpl() {
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void jumpToCurrentState(Drawable drawable) {
            DrawableCompatHoneycomb.jumpToCurrentState(drawable);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public Drawable wrap(Drawable drawable) {
            return DrawableCompatHoneycomb.wrapForTinting(drawable);
        }
    }

    static class JellybeanMr1DrawableImpl extends HoneycombDrawableImpl {
        JellybeanMr1DrawableImpl() {
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public int getLayoutDirection(Drawable drawable) {
            int layoutDirection = DrawableCompatJellybeanMr1.getLayoutDirection(drawable);
            if (layoutDirection >= 0) {
                return layoutDirection;
            }
            return 0;
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public boolean setLayoutDirection(Drawable drawable, int i) {
            return DrawableCompatJellybeanMr1.setLayoutDirection(drawable, i);
        }
    }

    static class KitKatDrawableImpl extends JellybeanMr1DrawableImpl {
        KitKatDrawableImpl() {
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public int getAlpha(Drawable drawable) {
            return DrawableCompatKitKat.getAlpha(drawable);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public boolean isAutoMirrored(Drawable drawable) {
            return DrawableCompatKitKat.isAutoMirrored(drawable);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setAutoMirrored(Drawable drawable, boolean z) {
            DrawableCompatKitKat.setAutoMirrored(drawable, z);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.HoneycombDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public Drawable wrap(Drawable drawable) {
            return DrawableCompatKitKat.wrapForTinting(drawable);
        }
    }

    static class LollipopDrawableImpl extends KitKatDrawableImpl {
        LollipopDrawableImpl() {
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void applyTheme(Drawable drawable, Resources.Theme theme) {
            DrawableCompatLollipop.applyTheme(drawable, theme);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public boolean canApplyTheme(Drawable drawable) {
            return DrawableCompatLollipop.canApplyTheme(drawable);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void clearColorFilter(Drawable drawable) {
            DrawableCompatLollipop.clearColorFilter(drawable);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public ColorFilter getColorFilter(Drawable drawable) {
            return DrawableCompatLollipop.getColorFilter(drawable);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void inflate(Drawable drawable, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
            DrawableCompatLollipop.inflate(drawable, resources, xmlPullParser, attributeSet, theme);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setHotspot(Drawable drawable, float f, float f2) {
            DrawableCompatLollipop.setHotspot(drawable, f, f2);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4) {
            DrawableCompatLollipop.setHotspotBounds(drawable, i, i2, i3, i4);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setTint(Drawable drawable, int i) {
            DrawableCompatLollipop.setTint(drawable, i);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setTintList(Drawable drawable, ColorStateList colorStateList) {
            DrawableCompatLollipop.setTintList(drawable, colorStateList);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void setTintMode(Drawable drawable, PorterDuff.Mode mode) {
            DrawableCompatLollipop.setTintMode(drawable, mode);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.KitKatDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.HoneycombDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public Drawable wrap(Drawable drawable) {
            return DrawableCompatLollipop.wrapForTinting(drawable);
        }
    }

    static class MDrawableImpl extends LollipopDrawableImpl {
        MDrawableImpl() {
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.LollipopDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public void clearColorFilter(Drawable drawable) {
            drawable.clearColorFilter();
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.JellybeanMr1DrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public int getLayoutDirection(Drawable drawable) {
            return DrawableCompatApi23.getLayoutDirection(drawable);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.JellybeanMr1DrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public boolean setLayoutDirection(Drawable drawable, int i) {
            return DrawableCompatApi23.setLayoutDirection(drawable, i);
        }

        @Override // android.support.v4.graphics.drawable.DrawableCompat.LollipopDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.KitKatDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.HoneycombDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.BaseDrawableImpl, android.support.v4.graphics.drawable.DrawableCompat.DrawableImpl
        public Drawable wrap(Drawable drawable) {
            return drawable;
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        int i = Build.VERSION.SDK_INT;
        IMPL = i >= 23 ? new MDrawableImpl() : i >= 21 ? new LollipopDrawableImpl() : i >= 19 ? new KitKatDrawableImpl() : i >= 17 ? new JellybeanMr1DrawableImpl() : i >= 11 ? new HoneycombDrawableImpl() : new BaseDrawableImpl();
    }

    private DrawableCompat() {
    }

    public static void applyTheme(@NonNull Drawable drawable, @NonNull Resources.Theme theme) {
        IMPL.applyTheme(drawable, theme);
    }

    public static boolean canApplyTheme(@NonNull Drawable drawable) {
        return IMPL.canApplyTheme(drawable);
    }

    public static void clearColorFilter(@NonNull Drawable drawable) {
        IMPL.clearColorFilter(drawable);
    }

    public static int getAlpha(@NonNull Drawable drawable) {
        return IMPL.getAlpha(drawable);
    }

    public static ColorFilter getColorFilter(@NonNull Drawable drawable) {
        return IMPL.getColorFilter(drawable);
    }

    public static int getLayoutDirection(@NonNull Drawable drawable) {
        return IMPL.getLayoutDirection(drawable);
    }

    public static void inflate(@NonNull Drawable drawable, @NonNull Resources resources, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Resources.Theme theme) {
        IMPL.inflate(drawable, resources, xmlPullParser, attributeSet, theme);
    }

    public static boolean isAutoMirrored(@NonNull Drawable drawable) {
        return IMPL.isAutoMirrored(drawable);
    }

    public static void jumpToCurrentState(@NonNull Drawable drawable) {
        IMPL.jumpToCurrentState(drawable);
    }

    public static void setAutoMirrored(@NonNull Drawable drawable, boolean z) {
        IMPL.setAutoMirrored(drawable, z);
    }

    public static void setHotspot(@NonNull Drawable drawable, float f, float f2) {
        IMPL.setHotspot(drawable, f, f2);
    }

    public static void setHotspotBounds(@NonNull Drawable drawable, int i, int i2, int i3, int i4) {
        IMPL.setHotspotBounds(drawable, i, i2, i3, i4);
    }

    public static boolean setLayoutDirection(@NonNull Drawable drawable, int i) {
        return IMPL.setLayoutDirection(drawable, i);
    }

    public static void setTint(@NonNull Drawable drawable, @ColorInt int i) {
        IMPL.setTint(drawable, i);
    }

    public static void setTintList(@NonNull Drawable drawable, @Nullable ColorStateList colorStateList) {
        IMPL.setTintList(drawable, colorStateList);
    }

    public static void setTintMode(@NonNull Drawable drawable, @Nullable PorterDuff.Mode mode) {
        IMPL.setTintMode(drawable, mode);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T extends Drawable> T unwrap(@NonNull Drawable drawable) {
        return drawable instanceof DrawableWrapper ? (T) ((DrawableWrapper) drawable).getWrappedDrawable() : drawable;
    }

    public static Drawable wrap(@NonNull Drawable drawable) {
        return IMPL.wrap(drawable);
    }
}
