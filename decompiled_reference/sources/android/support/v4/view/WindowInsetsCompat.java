package android.support.v4.view;

import android.graphics.Rect;
import android.os.Build;

/* loaded from: classes.dex */
public class WindowInsetsCompat {
    private static final WindowInsetsCompatImpl IMPL;
    private final Object mInsets;

    private static class WindowInsetsCompatApi20Impl extends WindowInsetsCompatBaseImpl {
        WindowInsetsCompatApi20Impl() {
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public WindowInsetsCompat consumeSystemWindowInsets(Object obj) {
            return new WindowInsetsCompat(WindowInsetsCompatApi20.consumeSystemWindowInsets(obj));
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public Object getSourceWindowInsets(Object obj) {
            return WindowInsetsCompatApi20.getSourceWindowInsets(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getSystemWindowInsetBottom(Object obj) {
            return WindowInsetsCompatApi20.getSystemWindowInsetBottom(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getSystemWindowInsetLeft(Object obj) {
            return WindowInsetsCompatApi20.getSystemWindowInsetLeft(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getSystemWindowInsetRight(Object obj) {
            return WindowInsetsCompatApi20.getSystemWindowInsetRight(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getSystemWindowInsetTop(Object obj) {
            return WindowInsetsCompatApi20.getSystemWindowInsetTop(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public boolean hasInsets(Object obj) {
            return WindowInsetsCompatApi20.hasInsets(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public boolean hasSystemWindowInsets(Object obj) {
            return WindowInsetsCompatApi20.hasSystemWindowInsets(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public boolean isRound(Object obj) {
            return WindowInsetsCompatApi20.isRound(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public WindowInsetsCompat replaceSystemWindowInsets(Object obj, int i, int i2, int i3, int i4) {
            return new WindowInsetsCompat(WindowInsetsCompatApi20.replaceSystemWindowInsets(obj, i, i2, i3, i4));
        }
    }

    private static class WindowInsetsCompatApi21Impl extends WindowInsetsCompatApi20Impl {
        WindowInsetsCompatApi21Impl() {
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public WindowInsetsCompat consumeStableInsets(Object obj) {
            return new WindowInsetsCompat(WindowInsetsCompatApi21.consumeStableInsets(obj));
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getStableInsetBottom(Object obj) {
            return WindowInsetsCompatApi21.getStableInsetBottom(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getStableInsetLeft(Object obj) {
            return WindowInsetsCompatApi21.getStableInsetLeft(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getStableInsetRight(Object obj) {
            return WindowInsetsCompatApi21.getStableInsetRight(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getStableInsetTop(Object obj) {
            return WindowInsetsCompatApi21.getStableInsetTop(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public boolean hasStableInsets(Object obj) {
            return WindowInsetsCompatApi21.hasStableInsets(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public boolean isConsumed(Object obj) {
            return WindowInsetsCompatApi21.isConsumed(obj);
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatBaseImpl, android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public WindowInsetsCompat replaceSystemWindowInsets(Object obj, Rect rect) {
            return new WindowInsetsCompat(WindowInsetsCompatApi21.replaceSystemWindowInsets(obj, rect));
        }
    }

    private static class WindowInsetsCompatBaseImpl implements WindowInsetsCompatImpl {
        WindowInsetsCompatBaseImpl() {
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public WindowInsetsCompat consumeStableInsets(Object obj) {
            return null;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public WindowInsetsCompat consumeSystemWindowInsets(Object obj) {
            return null;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public Object getSourceWindowInsets(Object obj) {
            return null;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getStableInsetBottom(Object obj) {
            return 0;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getStableInsetLeft(Object obj) {
            return 0;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getStableInsetRight(Object obj) {
            return 0;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getStableInsetTop(Object obj) {
            return 0;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getSystemWindowInsetBottom(Object obj) {
            return 0;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getSystemWindowInsetLeft(Object obj) {
            return 0;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getSystemWindowInsetRight(Object obj) {
            return 0;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public int getSystemWindowInsetTop(Object obj) {
            return 0;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public boolean hasInsets(Object obj) {
            return false;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public boolean hasStableInsets(Object obj) {
            return false;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public boolean hasSystemWindowInsets(Object obj) {
            return false;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public boolean isConsumed(Object obj) {
            return false;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public boolean isRound(Object obj) {
            return false;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public WindowInsetsCompat replaceSystemWindowInsets(Object obj, int i, int i2, int i3, int i4) {
            return null;
        }

        @Override // android.support.v4.view.WindowInsetsCompat.WindowInsetsCompatImpl
        public WindowInsetsCompat replaceSystemWindowInsets(Object obj, Rect rect) {
            return null;
        }
    }

    private interface WindowInsetsCompatImpl {
        WindowInsetsCompat consumeStableInsets(Object obj);

        WindowInsetsCompat consumeSystemWindowInsets(Object obj);

        Object getSourceWindowInsets(Object obj);

        int getStableInsetBottom(Object obj);

        int getStableInsetLeft(Object obj);

        int getStableInsetRight(Object obj);

        int getStableInsetTop(Object obj);

        int getSystemWindowInsetBottom(Object obj);

        int getSystemWindowInsetLeft(Object obj);

        int getSystemWindowInsetRight(Object obj);

        int getSystemWindowInsetTop(Object obj);

        boolean hasInsets(Object obj);

        boolean hasStableInsets(Object obj);

        boolean hasSystemWindowInsets(Object obj);

        boolean isConsumed(Object obj);

        boolean isRound(Object obj);

        WindowInsetsCompat replaceSystemWindowInsets(Object obj, int i, int i2, int i3, int i4);

        WindowInsetsCompat replaceSystemWindowInsets(Object obj, Rect rect);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    static {
        int i = Build.VERSION.SDK_INT;
        IMPL = i >= 21 ? new WindowInsetsCompatApi21Impl() : i >= 20 ? new WindowInsetsCompatApi20Impl() : new WindowInsetsCompatBaseImpl();
    }

    public WindowInsetsCompat(WindowInsetsCompat windowInsetsCompat) {
        this.mInsets = windowInsetsCompat == null ? null : IMPL.getSourceWindowInsets(windowInsetsCompat.mInsets);
    }

    WindowInsetsCompat(Object obj) {
        this.mInsets = obj;
    }

    static Object unwrap(WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat == null) {
            return null;
        }
        return windowInsetsCompat.mInsets;
    }

    static WindowInsetsCompat wrap(Object obj) {
        if (obj == null) {
            return null;
        }
        return new WindowInsetsCompat(obj);
    }

    public WindowInsetsCompat consumeStableInsets() {
        return IMPL.consumeStableInsets(this.mInsets);
    }

    public WindowInsetsCompat consumeSystemWindowInsets() {
        return IMPL.consumeSystemWindowInsets(this.mInsets);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        WindowInsetsCompat windowInsetsCompat = (WindowInsetsCompat) obj;
        return this.mInsets == null ? windowInsetsCompat.mInsets == null : this.mInsets.equals(windowInsetsCompat.mInsets);
    }

    public int getStableInsetBottom() {
        return IMPL.getStableInsetBottom(this.mInsets);
    }

    public int getStableInsetLeft() {
        return IMPL.getStableInsetLeft(this.mInsets);
    }

    public int getStableInsetRight() {
        return IMPL.getStableInsetRight(this.mInsets);
    }

    public int getStableInsetTop() {
        return IMPL.getStableInsetTop(this.mInsets);
    }

    public int getSystemWindowInsetBottom() {
        return IMPL.getSystemWindowInsetBottom(this.mInsets);
    }

    public int getSystemWindowInsetLeft() {
        return IMPL.getSystemWindowInsetLeft(this.mInsets);
    }

    public int getSystemWindowInsetRight() {
        return IMPL.getSystemWindowInsetRight(this.mInsets);
    }

    public int getSystemWindowInsetTop() {
        return IMPL.getSystemWindowInsetTop(this.mInsets);
    }

    public boolean hasInsets() {
        return IMPL.hasInsets(this.mInsets);
    }

    public boolean hasStableInsets() {
        return IMPL.hasStableInsets(this.mInsets);
    }

    public boolean hasSystemWindowInsets() {
        return IMPL.hasSystemWindowInsets(this.mInsets);
    }

    public int hashCode() {
        if (this.mInsets == null) {
            return 0;
        }
        return this.mInsets.hashCode();
    }

    public boolean isConsumed() {
        return IMPL.isConsumed(this.mInsets);
    }

    public boolean isRound() {
        return IMPL.isRound(this.mInsets);
    }

    public WindowInsetsCompat replaceSystemWindowInsets(int i, int i2, int i3, int i4) {
        return IMPL.replaceSystemWindowInsets(this.mInsets, i, i2, i3, i4);
    }

    public WindowInsetsCompat replaceSystemWindowInsets(Rect rect) {
        return IMPL.replaceSystemWindowInsets(this.mInsets, rect);
    }
}
