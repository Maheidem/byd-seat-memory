package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Map;

/* loaded from: classes.dex */
public class Detector {
    private final BitMatrix image;
    private ResultPointCallback resultPointCallback;

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private float calculateModuleSizeOneWay(ResultPoint resultPoint, ResultPoint resultPoint2) {
        float fSizeOfBlackWhiteBlackRunBothWays = sizeOfBlackWhiteBlackRunBothWays((int) resultPoint.getX(), (int) resultPoint.getY(), (int) resultPoint2.getX(), (int) resultPoint2.getY());
        float fSizeOfBlackWhiteBlackRunBothWays2 = sizeOfBlackWhiteBlackRunBothWays((int) resultPoint2.getX(), (int) resultPoint2.getY(), (int) resultPoint.getX(), (int) resultPoint.getY());
        return Float.isNaN(fSizeOfBlackWhiteBlackRunBothWays) ? fSizeOfBlackWhiteBlackRunBothWays2 / 7.0f : Float.isNaN(fSizeOfBlackWhiteBlackRunBothWays2) ? fSizeOfBlackWhiteBlackRunBothWays / 7.0f : (fSizeOfBlackWhiteBlackRunBothWays + fSizeOfBlackWhiteBlackRunBothWays2) / 14.0f;
    }

    private static int computeDimension(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, float f) throws NotFoundException {
        int iRound = ((MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2) / f) + MathUtils.round(ResultPoint.distance(resultPoint, resultPoint3) / f)) / 2) + 7;
        int i = iRound & 3;
        if (i == 0) {
            return iRound + 1;
        }
        switch (i) {
            case 2:
                return iRound - 1;
            case 3:
                throw NotFoundException.getNotFoundInstance();
            default:
                return iRound;
        }
    }

    private static PerspectiveTransform createTransform(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i) {
        float x;
        float y;
        float f;
        float f2 = i - 3.5f;
        if (resultPoint4 != null) {
            float x2 = resultPoint4.getX();
            y = resultPoint4.getY();
            x = x2;
            f = f2 - 3.0f;
        } else {
            x = (resultPoint2.getX() - resultPoint.getX()) + resultPoint3.getX();
            y = (resultPoint2.getY() - resultPoint.getY()) + resultPoint3.getY();
            f = f2;
        }
        return PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, f2, 3.5f, f, f, 3.5f, f2, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), x, y, resultPoint3.getX(), resultPoint3.getY());
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, PerspectiveTransform perspectiveTransform, int i) {
        return GridSampler.getInstance().sampleGrid(bitMatrix, i, i, perspectiveTransform);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0086, code lost:
    
        if (r14 != r1) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x008e, code lost:
    
        return com.google.zxing.common.detector.MathUtils.distance(r17, r4, r0, r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x008f, code lost:
    
        return Float.NaN;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private float sizeOfBlackWhiteBlackRun(int r20, int r21, int r22, int r23) {
        /*
            r19 = this;
            int r2 = r23 - r21
            int r2 = java.lang.Math.abs(r2)
            int r5 = r22 - r20
            int r5 = java.lang.Math.abs(r5)
            r7 = 1
            if (r2 <= r5) goto L11
            r2 = r7
            goto L12
        L11:
            r2 = 0
        L12:
            if (r2 == 0) goto L1d
            r3 = r20
            r0 = r21
            r4 = r22
            r1 = r23
            goto L25
        L1d:
            r0 = r20
            r3 = r21
            r1 = r22
            r4 = r23
        L25:
            int r5 = r1 - r0
            int r5 = java.lang.Math.abs(r5)
            int r8 = r4 - r3
            int r8 = java.lang.Math.abs(r8)
            int r9 = -r5
            r10 = 2
            int r9 = r9 / r10
            r11 = -1
            if (r0 >= r1) goto L39
            r12 = r7
            goto L3a
        L39:
            r12 = r11
        L3a:
            if (r3 >= r4) goto L3d
            r11 = r7
        L3d:
            int r1 = r1 + r12
            r13 = r3
            r15 = r9
            r14 = 0
            r9 = r0
        L42:
            if (r9 == r1) goto L83
            if (r2 == 0) goto L48
            r6 = r13
            goto L49
        L48:
            r6 = r9
        L49:
            if (r2 == 0) goto L4d
            r10 = r9
            goto L4e
        L4d:
            r10 = r13
        L4e:
            if (r14 != r7) goto L58
            r17 = r1
            r16 = r2
            r2 = r7
            r7 = r19
            goto L5f
        L58:
            r7 = r19
            r17 = r1
            r16 = r2
            r2 = 0
        L5f:
            com.google.zxing.common.BitMatrix r1 = r7.image
            boolean r1 = r1.get(r6, r10)
            if (r2 != r1) goto L71
            r1 = 2
            if (r14 != r1) goto L6f
            float r0 = com.google.zxing.common.detector.MathUtils.distance(r9, r13, r0, r3)
            return r0
        L6f:
            int r14 = r14 + 1
        L71:
            int r15 = r15 + r8
            if (r15 <= 0) goto L7b
            if (r13 == r4) goto L79
            int r13 = r13 + r11
            int r15 = r15 - r5
            goto L7b
        L79:
            r1 = 2
            goto L86
        L7b:
            int r9 = r9 + r12
            r2 = r16
            r1 = r17
            r7 = 1
            r10 = 2
            goto L42
        L83:
            r17 = r1
            r1 = r10
        L86:
            if (r14 != r1) goto L8f
            r1 = r17
            float r0 = com.google.zxing.common.detector.MathUtils.distance(r1, r4, r0, r3)
            return r0
        L8f:
            r0 = 2143289344(0x7fc00000, float:NaN)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.detector.Detector.sizeOfBlackWhiteBlackRun(int, int, int, int):float");
    }

    private float sizeOfBlackWhiteBlackRunBothWays(int i, int i2, int i3, int i4) {
        int width;
        float width2;
        float height;
        float fSizeOfBlackWhiteBlackRun = sizeOfBlackWhiteBlackRun(i, i2, i3, i4);
        int i5 = i - (i3 - i);
        int height2 = 0;
        if (i5 < 0) {
            width2 = i / (i - i5);
            width = 0;
        } else if (i5 >= this.image.getWidth()) {
            width2 = ((this.image.getWidth() - 1) - i) / (i5 - i);
            width = this.image.getWidth() - 1;
        } else {
            width = i5;
            width2 = 1.0f;
        }
        float f = i2;
        int i6 = (int) (f - ((i4 - i2) * width2));
        if (i6 < 0) {
            height = f / (i2 - i6);
        } else if (i6 >= this.image.getHeight()) {
            height = ((this.image.getHeight() - 1) - i2) / (i6 - i2);
            height2 = this.image.getHeight() - 1;
        } else {
            height2 = i6;
            height = 1.0f;
        }
        return (fSizeOfBlackWhiteBlackRun + sizeOfBlackWhiteBlackRun(i, i2, (int) (i + ((width - i) * height)), height2)) - 1.0f;
    }

    protected final float calculateModuleSize(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        return (calculateModuleSizeOneWay(resultPoint, resultPoint2) + calculateModuleSizeOneWay(resultPoint, resultPoint3)) / 2.0f;
    }

    public DetectorResult detect() {
        return detect(null);
    }

    public final DetectorResult detect(Map<DecodeHintType, ?> map) {
        this.resultPointCallback = map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        return processFinderPatternInfo(new FinderPatternFinder(this.image, this.resultPointCallback).find(map));
    }

    protected final AlignmentPattern findAlignmentInRegion(float f, int i, int i2, float f2) throws NotFoundException {
        int i3 = (int) (f2 * f);
        int iMax = Math.max(0, i - i3);
        int iMin = Math.min(this.image.getWidth() - 1, i + i3) - iMax;
        float f3 = 3.0f * f;
        if (iMin < f3) {
            throw NotFoundException.getNotFoundInstance();
        }
        int iMax2 = Math.max(0, i2 - i3);
        int iMin2 = Math.min(this.image.getHeight() - 1, i2 + i3) - iMax2;
        if (iMin2 < f3) {
            throw NotFoundException.getNotFoundInstance();
        }
        return new AlignmentPatternFinder(this.image, iMax, iMax2, iMin, iMin2, f, this.resultPointCallback).find();
    }

    protected final BitMatrix getImage() {
        return this.image;
    }

    protected final ResultPointCallback getResultPointCallback() {
        return this.resultPointCallback;
    }

    protected final DetectorResult processFinderPatternInfo(FinderPatternInfo finderPatternInfo) throws NotFoundException, FormatException {
        FinderPattern topLeft = finderPatternInfo.getTopLeft();
        FinderPattern topRight = finderPatternInfo.getTopRight();
        FinderPattern bottomLeft = finderPatternInfo.getBottomLeft();
        float fCalculateModuleSize = calculateModuleSize(topLeft, topRight, bottomLeft);
        if (fCalculateModuleSize < 1.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int iComputeDimension = computeDimension(topLeft, topRight, bottomLeft, fCalculateModuleSize);
        Version provisionalVersionForDimension = Version.getProvisionalVersionForDimension(iComputeDimension);
        int dimensionForVersion = provisionalVersionForDimension.getDimensionForVersion() - 7;
        AlignmentPattern alignmentPatternFindAlignmentInRegion = null;
        if (provisionalVersionForDimension.getAlignmentPatternCenters().length > 0) {
            float x = (topRight.getX() - topLeft.getX()) + bottomLeft.getX();
            float y = (topRight.getY() - topLeft.getY()) + bottomLeft.getY();
            float f = 1.0f - (3.0f / dimensionForVersion);
            int x2 = (int) (topLeft.getX() + ((x - topLeft.getX()) * f));
            int y2 = (int) (topLeft.getY() + (f * (y - topLeft.getY())));
            int i = 4;
            while (true) {
                if (i > 16) {
                    break;
                }
                try {
                    alignmentPatternFindAlignmentInRegion = findAlignmentInRegion(fCalculateModuleSize, x2, y2, i);
                    break;
                } catch (NotFoundException unused) {
                    i <<= 1;
                }
            }
        }
        return new DetectorResult(sampleGrid(this.image, createTransform(topLeft, topRight, bottomLeft, alignmentPatternFindAlignmentInRegion, iComputeDimension), iComputeDimension), alignmentPatternFindAlignmentInRegion == null ? new ResultPoint[]{bottomLeft, topLeft, topRight} : new ResultPoint[]{bottomLeft, topLeft, topRight, alignmentPatternFindAlignmentInRegion});
    }
}
