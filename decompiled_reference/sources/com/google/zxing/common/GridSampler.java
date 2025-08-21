package com.google.zxing.common;

/* loaded from: classes.dex */
public abstract class GridSampler {
    private static GridSampler gridSampler = new DefaultGridSampler();

    /* JADX WARN: Removed duplicated region for block: B:19:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0077  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected static void checkAndNudgePoints(com.google.zxing.common.BitMatrix r9, float[] r10) throws com.google.zxing.NotFoundException {
        /*
            int r0 = r9.getWidth()
            int r9 = r9.getHeight()
            r1 = 0
            r2 = 1
            r3 = r1
            r4 = r2
        Lc:
            int r5 = r10.length
            r6 = 0
            r7 = -1
            if (r3 >= r5) goto L49
            if (r4 == 0) goto L49
            r4 = r10[r3]
            int r4 = (int) r4
            int r5 = r3 + 1
            r8 = r10[r5]
            int r8 = (int) r8
            if (r4 < r7) goto L44
            if (r4 > r0) goto L44
            if (r8 < r7) goto L44
            if (r8 <= r9) goto L24
            goto L44
        L24:
            if (r4 != r7) goto L2a
            r10[r3] = r6
        L28:
            r4 = r2
            goto L33
        L2a:
            if (r4 != r0) goto L32
            int r4 = r0 + (-1)
            float r4 = (float) r4
            r10[r3] = r4
            goto L28
        L32:
            r4 = r1
        L33:
            if (r8 != r7) goto L39
            r10[r5] = r6
        L37:
            r4 = r2
            goto L41
        L39:
            if (r8 != r9) goto L41
            int r4 = r9 + (-1)
            float r4 = (float) r4
            r10[r5] = r4
            goto L37
        L41:
            int r3 = r3 + 2
            goto Lc
        L44:
            com.google.zxing.NotFoundException r9 = com.google.zxing.NotFoundException.getNotFoundInstance()
            throw r9
        L49:
            int r3 = r10.length
            int r3 = r3 + (-2)
            r4 = r2
        L4d:
            if (r3 < 0) goto L87
            if (r4 == 0) goto L87
            r4 = r10[r3]
            int r4 = (int) r4
            int r5 = r3 + 1
            r8 = r10[r5]
            int r8 = (int) r8
            if (r4 < r7) goto L82
            if (r4 > r0) goto L82
            if (r8 < r7) goto L82
            if (r8 <= r9) goto L62
            goto L82
        L62:
            if (r4 != r7) goto L68
            r10[r3] = r6
        L66:
            r4 = r2
            goto L71
        L68:
            if (r4 != r0) goto L70
            int r4 = r0 + (-1)
            float r4 = (float) r4
            r10[r3] = r4
            goto L66
        L70:
            r4 = r1
        L71:
            if (r8 != r7) goto L77
            r10[r5] = r6
        L75:
            r4 = r2
            goto L7f
        L77:
            if (r8 != r9) goto L7f
            int r4 = r9 + (-1)
            float r4 = (float) r4
            r10[r5] = r4
            goto L75
        L7f:
            int r3 = r3 + (-2)
            goto L4d
        L82:
            com.google.zxing.NotFoundException r9 = com.google.zxing.NotFoundException.getNotFoundInstance()
            throw r9
        L87:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.common.GridSampler.checkAndNudgePoints(com.google.zxing.common.BitMatrix, float[]):void");
    }

    public static GridSampler getInstance() {
        return gridSampler;
    }

    public static void setGridSampler(GridSampler gridSampler2) {
        gridSampler = gridSampler2;
    }

    public abstract BitMatrix sampleGrid(BitMatrix bitMatrix, int i, int i2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, float f16);

    public abstract BitMatrix sampleGrid(BitMatrix bitMatrix, int i, int i2, PerspectiveTransform perspectiveTransform);
}
