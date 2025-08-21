package android.support.v4.graphics;

import android.graphics.Bitmap;
import android.os.Build;

/* loaded from: classes.dex */
public final class BitmapCompat {
    static final BitmapImpl IMPL;

    static class BaseBitmapImpl implements BitmapImpl {
        BaseBitmapImpl() {
        }

        @Override // android.support.v4.graphics.BitmapCompat.BitmapImpl
        public int getAllocationByteCount(Bitmap bitmap) {
            return bitmap.getRowBytes() * bitmap.getHeight();
        }

        @Override // android.support.v4.graphics.BitmapCompat.BitmapImpl
        public boolean hasMipMap(Bitmap bitmap) {
            return false;
        }

        @Override // android.support.v4.graphics.BitmapCompat.BitmapImpl
        public void setHasMipMap(Bitmap bitmap, boolean z) {
        }
    }

    interface BitmapImpl {
        int getAllocationByteCount(Bitmap bitmap);

        boolean hasMipMap(Bitmap bitmap);

        void setHasMipMap(Bitmap bitmap, boolean z);
    }

    static class HcMr1BitmapCompatImpl extends BaseBitmapImpl {
        HcMr1BitmapCompatImpl() {
        }

        @Override // android.support.v4.graphics.BitmapCompat.BaseBitmapImpl, android.support.v4.graphics.BitmapCompat.BitmapImpl
        public int getAllocationByteCount(Bitmap bitmap) {
            return BitmapCompatHoneycombMr1.getAllocationByteCount(bitmap);
        }
    }

    static class JbMr2BitmapCompatImpl extends HcMr1BitmapCompatImpl {
        JbMr2BitmapCompatImpl() {
        }

        @Override // android.support.v4.graphics.BitmapCompat.BaseBitmapImpl, android.support.v4.graphics.BitmapCompat.BitmapImpl
        public boolean hasMipMap(Bitmap bitmap) {
            return BitmapCompatJellybeanMR2.hasMipMap(bitmap);
        }

        @Override // android.support.v4.graphics.BitmapCompat.BaseBitmapImpl, android.support.v4.graphics.BitmapCompat.BitmapImpl
        public void setHasMipMap(Bitmap bitmap, boolean z) {
            BitmapCompatJellybeanMR2.setHasMipMap(bitmap, z);
        }
    }

    static class KitKatBitmapCompatImpl extends JbMr2BitmapCompatImpl {
        KitKatBitmapCompatImpl() {
        }

        @Override // android.support.v4.graphics.BitmapCompat.HcMr1BitmapCompatImpl, android.support.v4.graphics.BitmapCompat.BaseBitmapImpl, android.support.v4.graphics.BitmapCompat.BitmapImpl
        public int getAllocationByteCount(Bitmap bitmap) {
            return BitmapCompatKitKat.getAllocationByteCount(bitmap);
        }
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        int i = Build.VERSION.SDK_INT;
        IMPL = i >= 19 ? new KitKatBitmapCompatImpl() : i >= 18 ? new JbMr2BitmapCompatImpl() : i >= 12 ? new HcMr1BitmapCompatImpl() : new BaseBitmapImpl();
    }

    private BitmapCompat() {
    }

    public static int getAllocationByteCount(Bitmap bitmap) {
        return IMPL.getAllocationByteCount(bitmap);
    }

    public static boolean hasMipMap(Bitmap bitmap) {
        return IMPL.hasMipMap(bitmap);
    }

    public static void setHasMipMap(Bitmap bitmap, boolean z) {
        IMPL.setHasMipMap(bitmap, z);
    }
}
