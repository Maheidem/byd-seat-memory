package android.support.v7.widget;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.RestrictTo;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.ImageView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class AppCompatImageHelper {
    private final ImageView mView;

    public AppCompatImageHelper(ImageView imageView) {
        this.mView = imageView;
    }

    boolean hasOverlappingRendering() {
        return Build.VERSION.SDK_INT < 21 || !(this.mView.getBackground() instanceof RippleDrawable);
    }

    public void loadFromAttributes(AttributeSet attributeSet, int i) throws Throwable {
        TintTypedArray tintTypedArrayObtainStyledAttributes;
        TintTypedArray tintTypedArray = null;
        try {
            Drawable drawable = this.mView.getDrawable();
            if (drawable == null) {
                tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attributeSet, R.styleable.AppCompatImageView, i, 0);
                try {
                    int resourceId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatImageView_srcCompat, -1);
                    if (resourceId != -1 && (drawable = AppCompatResources.getDrawable(this.mView.getContext(), resourceId)) != null) {
                        this.mView.setImageDrawable(drawable);
                    }
                    tintTypedArray = tintTypedArrayObtainStyledAttributes;
                } catch (Throwable th) {
                    th = th;
                    if (tintTypedArrayObtainStyledAttributes != null) {
                        tintTypedArrayObtainStyledAttributes.recycle();
                    }
                    throw th;
                }
            }
            if (drawable != null) {
                DrawableUtils.fixDrawable(drawable);
            }
            if (tintTypedArray != null) {
                tintTypedArray.recycle();
            }
        } catch (Throwable th2) {
            th = th2;
            tintTypedArrayObtainStyledAttributes = tintTypedArray;
        }
    }

    public void setImageResource(int i) {
        ImageView imageView;
        Drawable drawable;
        if (i != 0) {
            drawable = AppCompatResources.getDrawable(this.mView.getContext(), i);
            if (drawable != null) {
                DrawableUtils.fixDrawable(drawable);
            }
            imageView = this.mView;
        } else {
            imageView = this.mView;
            drawable = null;
        }
        imageView.setImageDrawable(drawable);
    }
}
