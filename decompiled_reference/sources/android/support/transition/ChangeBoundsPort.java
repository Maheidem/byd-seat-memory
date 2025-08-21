package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.RequiresApi;
import android.support.transition.TransitionPort;
import android.view.View;
import android.view.ViewGroup;
import java.util.Map;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class ChangeBoundsPort extends TransitionPort {
    private static final String LOG_TAG = "ChangeBounds";
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static final String[] sTransitionProperties = {PROPNAME_BOUNDS, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
    private static RectEvaluator sRectEvaluator = new RectEvaluator();
    int[] tempLocation = new int[2];
    boolean mResizeClip = false;
    boolean mReparent = false;

    ChangeBoundsPort() {
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        transitionValues.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        transitionValues.values.put(PROPNAME_PARENT, transitionValues.view.getParent());
        transitionValues.view.getLocationInWindow(this.tempLocation);
        transitionValues.values.put(PROPNAME_WINDOW_X, Integer.valueOf(this.tempLocation[0]));
        transitionValues.values.put(PROPNAME_WINDOW_Y, Integer.valueOf(this.tempLocation[1]));
    }

    @Override // android.support.transition.TransitionPort
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.support.transition.TransitionPort
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.support.transition.TransitionPort
    public Animator createAnimator(final ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        int i;
        char c;
        char c2;
        char c3;
        int i2;
        char c4;
        char c5;
        int i3;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        Map<String, Object> map = transitionValues.values;
        Map<String, Object> map2 = transitionValues2.values;
        ViewGroup viewGroup2 = (ViewGroup) map.get(PROPNAME_PARENT);
        ViewGroup viewGroup3 = (ViewGroup) map2.get(PROPNAME_PARENT);
        if (viewGroup2 == null || viewGroup3 == null) {
            return null;
        }
        final View view = transitionValues2.view;
        int i4 = 0;
        boolean z = viewGroup2 == viewGroup3 || viewGroup2.getId() == viewGroup3.getId();
        if (this.mReparent && !z) {
            int iIntValue = ((Integer) transitionValues.values.get(PROPNAME_WINDOW_X)).intValue();
            int iIntValue2 = ((Integer) transitionValues.values.get(PROPNAME_WINDOW_Y)).intValue();
            int iIntValue3 = ((Integer) transitionValues2.values.get(PROPNAME_WINDOW_X)).intValue();
            int iIntValue4 = ((Integer) transitionValues2.values.get(PROPNAME_WINDOW_Y)).intValue();
            if (iIntValue == iIntValue3 && iIntValue2 == iIntValue4) {
                return null;
            }
            viewGroup.getLocationInWindow(this.tempLocation);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
            view.draw(new Canvas(bitmapCreateBitmap));
            final BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmapCreateBitmap);
            view.setVisibility(4);
            ViewOverlay.createFrom(viewGroup).add(bitmapDrawable);
            ObjectAnimator objectAnimatorOfObject = ObjectAnimator.ofObject(bitmapDrawable, "bounds", sRectEvaluator, new Rect(iIntValue - this.tempLocation[0], iIntValue2 - this.tempLocation[1], (iIntValue - this.tempLocation[0]) + view.getWidth(), (iIntValue2 - this.tempLocation[1]) + view.getHeight()), new Rect(iIntValue3 - this.tempLocation[0], iIntValue4 - this.tempLocation[1], (iIntValue3 - this.tempLocation[0]) + view.getWidth(), (iIntValue4 - this.tempLocation[1]) + view.getHeight()));
            objectAnimatorOfObject.addListener(new AnimatorListenerAdapter() { // from class: android.support.transition.ChangeBoundsPort.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ViewOverlay.createFrom(viewGroup).remove(bitmapDrawable);
                    view.setVisibility(0);
                }
            });
            return objectAnimatorOfObject;
        }
        Rect rect = (Rect) transitionValues.values.get(PROPNAME_BOUNDS);
        Rect rect2 = (Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
        int i5 = rect.left;
        int i6 = rect2.left;
        int i7 = rect.top;
        int i8 = rect2.top;
        int i9 = rect.right;
        int i10 = rect2.right;
        int i11 = rect.bottom;
        int i12 = rect2.bottom;
        int i13 = i9 - i5;
        int i14 = i11 - i7;
        int i15 = i10 - i6;
        int i16 = i12 - i8;
        if (i13 != 0 && i14 != 0 && i15 != 0 && i16 != 0) {
            int i17 = i5 != i6 ? 1 : 0;
            if (i7 != i8) {
                i17++;
            }
            if (i9 != i10) {
                i17++;
            }
            if (i11 != i12) {
                i17++;
            }
            i4 = i17;
        }
        if (i4 <= 0) {
            return null;
        }
        if (!this.mResizeClip) {
            PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[i4];
            if (i5 != i6) {
                view.setLeft(i5);
            }
            if (i7 != i8) {
                view.setTop(i7);
            }
            if (i9 != i10) {
                view.setRight(i9);
            }
            if (i11 != i12) {
                view.setBottom(i11);
            }
            if (i5 != i6) {
                i2 = 2;
                c5 = 0;
                c4 = 1;
                propertyValuesHolderArr[0] = PropertyValuesHolder.ofInt("left", i5, i6);
                i3 = 1;
            } else {
                i2 = 2;
                c4 = 1;
                c5 = 0;
                i3 = 0;
            }
            if (i7 != i8) {
                int[] iArr = new int[i2];
                iArr[c5] = i7;
                iArr[c4] = i8;
                propertyValuesHolderArr[i3] = PropertyValuesHolder.ofInt("top", iArr);
                i3++;
            }
            if (i9 != i10) {
                int[] iArr2 = new int[i2];
                iArr2[c5] = i9;
                iArr2[c4] = i10;
                propertyValuesHolderArr[i3] = PropertyValuesHolder.ofInt("right", iArr2);
                i3++;
            }
            if (i11 != i12) {
                int[] iArr3 = new int[i2];
                iArr3[c5] = i11;
                iArr3[c4] = i12;
                propertyValuesHolderArr[i3] = PropertyValuesHolder.ofInt("bottom", iArr3);
            }
            ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolderArr);
            if (view.getParent() instanceof ViewGroup) {
                addListener(new TransitionPort.TransitionListenerAdapter() { // from class: android.support.transition.ChangeBoundsPort.1
                    boolean mCanceled = false;

                    @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
                    public void onTransitionCancel(TransitionPort transitionPort) {
                        this.mCanceled = true;
                    }

                    @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
                    public void onTransitionEnd(TransitionPort transitionPort) {
                        boolean z2 = this.mCanceled;
                    }

                    @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
                    public void onTransitionPause(TransitionPort transitionPort) {
                    }

                    @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
                    public void onTransitionResume(TransitionPort transitionPort) {
                    }
                });
            }
            return objectAnimatorOfPropertyValuesHolder;
        }
        if (i13 != i15) {
            view.setRight(Math.max(i13, i15) + i6);
        }
        if (i14 != i16) {
            view.setBottom(Math.max(i14, i16) + i8);
        }
        if (i5 != i6) {
            view.setTranslationX(i5 - i6);
        }
        if (i7 != i8) {
            view.setTranslationY(i7 - i8);
        }
        float f = i6 - i5;
        float f2 = i8 - i7;
        int i18 = i15 - i13;
        int i19 = i16 - i14;
        int i20 = f != 0.0f ? 1 : 0;
        if (f2 != 0.0f) {
            i20++;
        }
        if (i18 != 0 || i19 != 0) {
            i20++;
        }
        PropertyValuesHolder[] propertyValuesHolderArr2 = new PropertyValuesHolder[i20];
        if (f != 0.0f) {
            i = 2;
            c2 = 0;
            c = 1;
            propertyValuesHolderArr2[0] = PropertyValuesHolder.ofFloat("translationX", view.getTranslationX(), 0.0f);
            c3 = 1;
        } else {
            i = 2;
            c = 1;
            c2 = 0;
            c3 = 0;
        }
        if (f2 != 0.0f) {
            float[] fArr = new float[i];
            fArr[c2] = view.getTranslationY();
            fArr[c] = 0.0f;
            propertyValuesHolderArr2[c3] = PropertyValuesHolder.ofFloat("translationY", fArr);
        }
        if (i18 != 0 || i19 != 0) {
            new Rect(0, 0, i13, i14);
            new Rect(0, 0, i15, i16);
        }
        ObjectAnimator objectAnimatorOfPropertyValuesHolder2 = ObjectAnimator.ofPropertyValuesHolder(view, propertyValuesHolderArr2);
        if (view.getParent() instanceof ViewGroup) {
            addListener(new TransitionPort.TransitionListenerAdapter() { // from class: android.support.transition.ChangeBoundsPort.2
                boolean mCanceled = false;

                @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
                public void onTransitionCancel(TransitionPort transitionPort) {
                    this.mCanceled = true;
                }

                @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
                public void onTransitionEnd(TransitionPort transitionPort) {
                    boolean z2 = this.mCanceled;
                }

                @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
                public void onTransitionPause(TransitionPort transitionPort) {
                }

                @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
                public void onTransitionResume(TransitionPort transitionPort) {
                }
            });
        }
        objectAnimatorOfPropertyValuesHolder2.addListener(new AnimatorListenerAdapter() { // from class: android.support.transition.ChangeBoundsPort.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
            }
        });
        return objectAnimatorOfPropertyValuesHolder2;
    }

    @Override // android.support.transition.TransitionPort
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setReparent(boolean z) {
        this.mReparent = z;
    }

    public void setResizeClip(boolean z) {
        this.mResizeClip = z;
    }
}
