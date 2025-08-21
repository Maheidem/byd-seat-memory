package android.support.constraint;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/* loaded from: classes.dex */
public class Barrier extends ConstraintHelper {
    public static final int BOTTOM = 3;
    public static final int END = 6;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int START = 5;
    public static final int TOP = 2;
    private android.support.constraint.solver.widgets.Barrier mBarrier;
    private int mIndicatedType;
    private int mResolvedType;

    public Barrier(Context context) {
        super(context);
        this.mIndicatedType = 0;
        this.mResolvedType = 0;
        super.setVisibility(8);
    }

    public Barrier(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIndicatedType = 0;
        this.mResolvedType = 0;
        super.setVisibility(8);
    }

    public Barrier(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIndicatedType = 0;
        this.mResolvedType = 0;
        super.setVisibility(8);
    }

    public int getType() {
        return this.mIndicatedType;
    }

    @Override // android.support.constraint.ConstraintHelper
    protected void init(AttributeSet attributeSet) throws IllegalAccessException, IllegalArgumentException {
        super.init(attributeSet);
        this.mBarrier = new android.support.constraint.solver.widgets.Barrier();
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.ConstraintLayout_Layout);
            int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = typedArrayObtainStyledAttributes.getIndex(i);
                if (index == R.styleable.ConstraintLayout_Layout_barrierDirection) {
                    setType(typedArrayObtainStyledAttributes.getInt(index, 0));
                } else if (index == R.styleable.ConstraintLayout_Layout_barrierAllowsGoneWidgets) {
                    this.mBarrier.setAllowsGoneWidget(typedArrayObtainStyledAttributes.getBoolean(index, true));
                }
            }
        }
        this.mHelperWidget = this.mBarrier;
        validateParams();
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0012  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setType(int r6) {
        /*
            r5 = this;
            r5.mIndicatedType = r6
            r5.mResolvedType = r6
            int r6 = android.os.Build.VERSION.SDK_INT
            r0 = 6
            r1 = 5
            r2 = 0
            r3 = 1
            r4 = 17
            if (r6 >= r4) goto L1c
            int r6 = r5.mIndicatedType
            if (r6 != r1) goto L15
        L12:
            r5.mResolvedType = r2
            goto L43
        L15:
            int r6 = r5.mIndicatedType
            if (r6 != r0) goto L43
        L19:
            r5.mResolvedType = r3
            goto L43
        L1c:
            android.content.res.Resources r6 = r5.getResources()
            android.content.res.Configuration r6 = r6.getConfiguration()
            int r6 = r6.getLayoutDirection()
            if (r3 != r6) goto L2c
            r6 = r3
            goto L2d
        L2c:
            r6 = r2
        L2d:
            if (r6 == 0) goto L39
            int r6 = r5.mIndicatedType
            if (r6 != r1) goto L34
            goto L19
        L34:
            int r6 = r5.mIndicatedType
            if (r6 != r0) goto L43
            goto L12
        L39:
            int r6 = r5.mIndicatedType
            if (r6 != r1) goto L3e
            goto L12
        L3e:
            int r6 = r5.mIndicatedType
            if (r6 != r0) goto L43
            goto L19
        L43:
            android.support.constraint.solver.widgets.Barrier r6 = r5.mBarrier
            int r5 = r5.mResolvedType
            r6.setBarrierType(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.Barrier.setType(int):void");
    }
}
