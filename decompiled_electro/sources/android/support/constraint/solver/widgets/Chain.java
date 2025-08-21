package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;

/* loaded from: classes.dex */
class Chain {
    private static final boolean DEBUG = false;

    Chain() {
    }

    static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem linearSystem, int i) {
        int i2;
        int i3;
        ConstraintWidget[] constraintWidgetArr;
        if (i == 0) {
            int i4 = constraintWidgetContainer.mHorizontalChainsSize;
            constraintWidgetArr = constraintWidgetContainer.mHorizontalChainsArray;
            i3 = i4;
            i2 = 0;
        } else {
            i2 = 2;
            i3 = constraintWidgetContainer.mVerticalChainsSize;
            constraintWidgetArr = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            ConstraintWidget constraintWidget = constraintWidgetArr[i5];
            if (!constraintWidgetContainer.optimizeFor(4) || !Optimizer.applyChainOptimized(constraintWidgetContainer, linearSystem, i, i2, constraintWidget)) {
                applyChainConstraints(constraintWidgetContainer, linearSystem, i, i2, constraintWidget);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x02f2  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x030d  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0357  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x048d  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x0492  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0497  */
    /* JADX WARN: Removed duplicated region for block: B:265:0x049d  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x04a0  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x005b A[PHI: r7 r8
      0x005b: PHI (r7v40 boolean) = (r7v2 boolean), (r7v43 boolean) binds: [B:47:0x007d, B:34:0x0059] A[DONT_GENERATE, DONT_INLINE]
      0x005b: PHI (r8v32 boolean) = (r8v2 boolean), (r8v35 boolean) binds: [B:47:0x007d, B:34:0x0059] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x005d A[PHI: r7 r8
      0x005d: PHI (r7v4 boolean) = (r7v2 boolean), (r7v43 boolean) binds: [B:47:0x007d, B:34:0x0059] A[DONT_GENERATE, DONT_INLINE]
      0x005d: PHI (r8v4 boolean) = (r8v2 boolean), (r8v35 boolean) binds: [B:47:0x007d, B:34:0x0059] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0160  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static void applyChainConstraints(android.support.constraint.solver.widgets.ConstraintWidgetContainer r39, android.support.constraint.solver.LinearSystem r40, int r41, int r42, android.support.constraint.solver.widgets.ConstraintWidget r43) {
        /*
            Method dump skipped, instructions count: 1229
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.Chain.applyChainConstraints(android.support.constraint.solver.widgets.ConstraintWidgetContainer, android.support.constraint.solver.LinearSystem, int, int, android.support.constraint.solver.widgets.ConstraintWidget):void");
    }
}
