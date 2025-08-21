package android.support.graphics.drawable;

import android.graphics.Path;
import android.util.Log;
import java.util.ArrayList;

/* loaded from: classes.dex */
class PathParser {
    private static final String LOGTAG = "PathParser";

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        ExtractFloatResult() {
        }
    }

    public static class PathDataNode {
        float[] params;
        char type;

        PathDataNode(char c, float[] fArr) {
            this.type = c;
            this.params = fArr;
        }

        PathDataNode(PathDataNode pathDataNode) {
            this.type = pathDataNode.type;
            this.params = PathParser.copyOfRange(pathDataNode.params, 0, pathDataNode.params.length);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        private static void addCommand(Path path, float[] fArr, char c, char c2, float[] fArr2) {
            int i;
            int i2;
            int i3;
            float f;
            float f2;
            float f3;
            float f4;
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            float f10;
            boolean z = false;
            float f11 = fArr[0];
            float f12 = fArr[1];
            float f13 = fArr[2];
            float f14 = fArr[3];
            float f15 = fArr[4];
            float f16 = fArr[5];
            switch (c2) {
                case 'A':
                case 'a':
                    i = 7;
                    i2 = i;
                    break;
                case 'C':
                case 'c':
                    i = 6;
                    i2 = i;
                    break;
                case 'H':
                case 'V':
                case 'h':
                case 'v':
                    i2 = 1;
                    break;
                case 'L':
                case 'M':
                case 'T':
                case 'l':
                case 'm':
                case 't':
                default:
                    i2 = 2;
                    break;
                case 'Q':
                case 'S':
                case android.support.design.R.styleable.AppCompatTheme_windowMinWidthMinor /* 113 */:
                case 's':
                    i2 = 4;
                    break;
                case 'Z':
                case 'z':
                    path.close();
                    path.moveTo(f15, f16);
                    f11 = f15;
                    f13 = f11;
                    f12 = f16;
                    f14 = f12;
                    i2 = 2;
                    break;
            }
            float f17 = f11;
            float f18 = f12;
            float f19 = f15;
            float f20 = f16;
            int i4 = 0;
            char c3 = c;
            while (i4 < fArr2.length) {
                float f21 = 0.0f;
                switch (c2) {
                    case 'A':
                        i3 = i4;
                        int i5 = i3 + 5;
                        int i6 = i3 + 6;
                        drawArc(path, f17, f18, fArr2[i5], fArr2[i6], fArr2[i3 + 0], fArr2[i3 + 1], fArr2[i3 + 2], fArr2[i3 + 3] != 0.0f, fArr2[i3 + 4] != 0.0f);
                        f17 = fArr2[i5];
                        f18 = fArr2[i6];
                        f14 = f18;
                        f13 = f17;
                        break;
                    case 'C':
                        i3 = i4;
                        int i7 = i3 + 2;
                        int i8 = i3 + 3;
                        int i9 = i3 + 4;
                        int i10 = i3 + 5;
                        path.cubicTo(fArr2[i3 + 0], fArr2[i3 + 1], fArr2[i7], fArr2[i8], fArr2[i9], fArr2[i10]);
                        f17 = fArr2[i9];
                        float f22 = fArr2[i10];
                        float f23 = fArr2[i7];
                        float f24 = fArr2[i8];
                        f18 = f22;
                        f14 = f24;
                        f13 = f23;
                        break;
                    case 'H':
                        i3 = i4;
                        int i11 = i3 + 0;
                        path.lineTo(fArr2[i11], f18);
                        f17 = fArr2[i11];
                        break;
                    case 'L':
                        i3 = i4;
                        int i12 = i3 + 0;
                        int i13 = i3 + 1;
                        path.lineTo(fArr2[i12], fArr2[i13]);
                        f17 = fArr2[i12];
                        f18 = fArr2[i13];
                        break;
                    case 'M':
                        i3 = i4;
                        int i14 = i3 + 0;
                        f17 = fArr2[i14];
                        int i15 = i3 + 1;
                        f18 = fArr2[i15];
                        if (i3 <= 0) {
                            path.moveTo(fArr2[i14], fArr2[i15]);
                            f20 = f18;
                            f19 = f17;
                            break;
                        } else {
                            path.lineTo(fArr2[i14], fArr2[i15]);
                            break;
                        }
                    case 'Q':
                        i3 = i4;
                        int i16 = i3 + 0;
                        int i17 = i3 + 1;
                        int i18 = i3 + 2;
                        int i19 = i3 + 3;
                        path.quadTo(fArr2[i16], fArr2[i17], fArr2[i18], fArr2[i19]);
                        f = fArr2[i16];
                        f2 = fArr2[i17];
                        f17 = fArr2[i18];
                        f18 = fArr2[i19];
                        f13 = f;
                        f14 = f2;
                        break;
                    case 'S':
                        float f25 = f18;
                        float f26 = f17;
                        i3 = i4;
                        if (c3 == 'c' || c3 == 's' || c3 == 'C' || c3 == 'S') {
                            f3 = (2.0f * f26) - f13;
                            f4 = (2.0f * f25) - f14;
                        } else {
                            f3 = f26;
                            f4 = f25;
                        }
                        int i20 = i3 + 0;
                        int i21 = i3 + 1;
                        int i22 = i3 + 2;
                        int i23 = i3 + 3;
                        path.cubicTo(f3, f4, fArr2[i20], fArr2[i21], fArr2[i22], fArr2[i23]);
                        f = fArr2[i20];
                        f2 = fArr2[i21];
                        f17 = fArr2[i22];
                        f18 = fArr2[i23];
                        f13 = f;
                        f14 = f2;
                        break;
                    case 'T':
                        float f27 = f18;
                        float f28 = f17;
                        i3 = i4;
                        if (c3 == 'q' || c3 == 't' || c3 == 'Q' || c3 == 'T') {
                            f27 = (2.0f * f27) - f14;
                            f28 = (2.0f * f28) - f13;
                        }
                        int i24 = i3 + 0;
                        int i25 = i3 + 1;
                        path.quadTo(f28, f27, fArr2[i24], fArr2[i25]);
                        f17 = fArr2[i24];
                        f18 = fArr2[i25];
                        f13 = f28;
                        f14 = f27;
                        break;
                    case 'V':
                        i3 = i4;
                        int i26 = i3 + 0;
                        path.lineTo(f17, fArr2[i26]);
                        f18 = fArr2[i26];
                        break;
                    case 'a':
                        int i27 = i4 + 5;
                        int i28 = i4 + 6;
                        i3 = i4;
                        drawArc(path, f17, f18, fArr2[i27] + f17, fArr2[i28] + f18, fArr2[i4 + 0], fArr2[i4 + 1], fArr2[i4 + 2], fArr2[i4 + 3] != 0.0f, fArr2[i4 + 4] != 0.0f);
                        f17 += fArr2[i27];
                        f18 += fArr2[i28];
                        f14 = f18;
                        f13 = f17;
                        break;
                    case 'c':
                        int i29 = i4 + 2;
                        int i30 = i4 + 3;
                        int i31 = i4 + 4;
                        int i32 = i4 + 5;
                        path.rCubicTo(fArr2[i4 + 0], fArr2[i4 + 1], fArr2[i29], fArr2[i30], fArr2[i31], fArr2[i32]);
                        f5 = fArr2[i29] + f17;
                        f6 = fArr2[i30] + f18;
                        f17 += fArr2[i31];
                        f7 = fArr2[i32];
                        f18 += f7;
                        f13 = f5;
                        f14 = f6;
                        i3 = i4;
                        break;
                    case 'h':
                        int i33 = i4 + 0;
                        path.rLineTo(fArr2[i33], 0.0f);
                        f17 += fArr2[i33];
                        i3 = i4;
                        break;
                    case 'l':
                        int i34 = i4 + 0;
                        int i35 = i4 + 1;
                        path.rLineTo(fArr2[i34], fArr2[i35]);
                        f17 += fArr2[i34];
                        f8 = fArr2[i35];
                        f18 += f8;
                        i3 = i4;
                        break;
                    case 'm':
                        int i36 = i4 + 0;
                        f17 += fArr2[i36];
                        int i37 = i4 + 1;
                        f18 += fArr2[i37];
                        if (i4 > 0) {
                            path.rLineTo(fArr2[i36], fArr2[i37]);
                        } else {
                            path.rMoveTo(fArr2[i36], fArr2[i37]);
                            f20 = f18;
                            f19 = f17;
                        }
                        i3 = i4;
                        break;
                    case android.support.design.R.styleable.AppCompatTheme_windowMinWidthMinor /* 113 */:
                        int i38 = i4 + 0;
                        int i39 = i4 + 1;
                        int i40 = i4 + 2;
                        int i41 = i4 + 3;
                        path.rQuadTo(fArr2[i38], fArr2[i39], fArr2[i40], fArr2[i41]);
                        f5 = fArr2[i38] + f17;
                        f6 = fArr2[i39] + f18;
                        f17 += fArr2[i40];
                        f7 = fArr2[i41];
                        f18 += f7;
                        f13 = f5;
                        f14 = f6;
                        i3 = i4;
                        break;
                    case 's':
                        if (c3 == 'c' || c3 == 's' || c3 == 'C' || c3 == 'S') {
                            float f29 = f17 - f13;
                            f9 = f18 - f14;
                            f21 = f29;
                        } else {
                            f9 = 0.0f;
                        }
                        int i42 = i4 + 0;
                        int i43 = i4 + 1;
                        int i44 = i4 + 2;
                        int i45 = i4 + 3;
                        path.rCubicTo(f21, f9, fArr2[i42], fArr2[i43], fArr2[i44], fArr2[i45]);
                        f5 = fArr2[i42] + f17;
                        f6 = fArr2[i43] + f18;
                        f17 += fArr2[i44];
                        f7 = fArr2[i45];
                        f18 += f7;
                        f13 = f5;
                        f14 = f6;
                        i3 = i4;
                        break;
                    case 't':
                        if (c3 == 'q' || c3 == 't' || c3 == 'Q' || c3 == 'T') {
                            f21 = f17 - f13;
                            f10 = f18 - f14;
                        } else {
                            f10 = 0.0f;
                        }
                        int i46 = i4 + 0;
                        int i47 = i4 + 1;
                        path.rQuadTo(f21, f10, fArr2[i46], fArr2[i47]);
                        float f30 = f21 + f17;
                        float f31 = f10 + f18;
                        f17 += fArr2[i46];
                        f18 += fArr2[i47];
                        f14 = f31;
                        f13 = f30;
                        i3 = i4;
                        break;
                    case 'v':
                        int i48 = i4 + 0;
                        path.rLineTo(0.0f, fArr2[i48]);
                        f8 = fArr2[i48];
                        f18 += f8;
                        i3 = i4;
                        break;
                    default:
                        i3 = i4;
                        break;
                }
                i4 = i3 + i2;
                c3 = c2;
                z = false;
            }
            fArr[z ? 1 : 0] = f17;
            fArr[1] = f18;
            fArr[2] = f13;
            fArr[3] = f14;
            fArr[4] = f19;
            fArr[5] = f20;
        }

        private static void arcToBezier(Path path, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
            double d10 = d3;
            int iCeil = (int) Math.ceil(Math.abs((d9 * 4.0d) / 3.141592653589793d));
            double dCos = Math.cos(d7);
            double dSin = Math.sin(d7);
            double dCos2 = Math.cos(d8);
            double dSin2 = Math.sin(d8);
            double d11 = -d10;
            double d12 = d11 * dCos;
            double d13 = d4 * dSin;
            double d14 = (d12 * dSin2) - (d13 * dCos2);
            double d15 = d11 * dSin;
            double d16 = d4 * dCos;
            double d17 = (dSin2 * d15) + (dCos2 * d16);
            double d18 = d9 / iCeil;
            int i = 0;
            double d19 = d5;
            double d20 = d6;
            double d21 = d17;
            double d22 = d8;
            while (i < iCeil) {
                double d23 = d15;
                double d24 = d22 + d18;
                double dSin3 = Math.sin(d24);
                double dCos3 = Math.cos(d24);
                double d25 = d18;
                double d26 = (d + ((d10 * dCos) * dCos3)) - (d13 * dSin3);
                double d27 = d2 + (d10 * dSin * dCos3) + (d16 * dSin3);
                double d28 = (d12 * dSin3) - (d13 * dCos3);
                double d29 = (dSin3 * d23) + (dCos3 * d16);
                double d30 = d24 - d22;
                double d31 = d16;
                double dTan = Math.tan(d30 / 2.0d);
                double dSin4 = (Math.sin(d30) * (Math.sqrt(4.0d + ((3.0d * dTan) * dTan)) - 1.0d)) / 3.0d;
                double d32 = d19;
                int i2 = iCeil;
                double d33 = d20;
                float f = (float) d32;
                float f2 = (float) d33;
                path.rCubicTo(((float) (d32 + (d14 * dSin4))) - f, ((float) (d33 + (d21 * dSin4))) - f2, ((float) (d26 - (dSin4 * d28))) - f, ((float) (d27 - (dSin4 * d29))) - f2, ((float) d26) - f, ((float) d27) - f2);
                i++;
                d20 = d27;
                d19 = d26;
                d15 = d23;
                d21 = d29;
                d14 = d28;
                d18 = d25;
                d16 = d31;
                d22 = d24;
                iCeil = i2;
                dCos = dCos;
                dSin = dSin;
                d10 = d3;
            }
        }

        private static void drawArc(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean z, boolean z2) {
            double d;
            double d2;
            double radians = Math.toRadians(f7);
            double dCos = Math.cos(radians);
            double dSin = Math.sin(radians);
            double d3 = f;
            double d4 = f2;
            double d5 = (d3 * dCos) + (d4 * dSin);
            double d6 = f5;
            double d7 = d5 / d6;
            double d8 = ((-f) * dSin) + (d4 * dCos);
            double d9 = f6;
            double d10 = d8 / d9;
            double d11 = f4;
            double d12 = ((f3 * dCos) + (d11 * dSin)) / d6;
            double d13 = (((-f3) * dSin) + (d11 * dCos)) / d9;
            double d14 = d7 - d12;
            double d15 = d10 - d13;
            double d16 = (d7 + d12) / 2.0d;
            double d17 = (d10 + d13) / 2.0d;
            double d18 = (d14 * d14) + (d15 * d15);
            if (d18 == 0.0d) {
                Log.w(PathParser.LOGTAG, " Points are coincident");
                return;
            }
            double d19 = (1.0d / d18) - 0.25d;
            if (d19 < 0.0d) {
                Log.w(PathParser.LOGTAG, "Points are too far apart " + d18);
                float fSqrt = (float) (Math.sqrt(d18) / 1.99999d);
                drawArc(path, f, f2, f3, f4, f5 * fSqrt, f6 * fSqrt, f7, z, z2);
                return;
            }
            double dSqrt = Math.sqrt(d19);
            double d20 = d14 * dSqrt;
            double d21 = dSqrt * d15;
            if (z == z2) {
                d = d16 - d21;
                d2 = d17 + d20;
            } else {
                d = d16 + d21;
                d2 = d17 - d20;
            }
            double dAtan2 = Math.atan2(d10 - d2, d7 - d);
            double dAtan22 = Math.atan2(d13 - d2, d12 - d) - dAtan2;
            if (z2 != (dAtan22 >= 0.0d)) {
                dAtan22 = dAtan22 > 0.0d ? dAtan22 - 6.283185307179586d : dAtan22 + 6.283185307179586d;
            }
            double d22 = d * d6;
            double d23 = d2 * d9;
            arcToBezier(path, (d22 * dCos) - (d23 * dSin), (d22 * dSin) + (d23 * dCos), d6, d9, d3, d4, radians, dAtan2, dAtan22);
        }

        public static void nodesToPath(PathDataNode[] pathDataNodeArr, Path path) {
            float[] fArr = new float[6];
            char c = 'm';
            for (int i = 0; i < pathDataNodeArr.length; i++) {
                addCommand(path, fArr, c, pathDataNodeArr[i].type, pathDataNodeArr[i].params);
                c = pathDataNodeArr[i].type;
            }
        }

        public void interpolatePathDataNode(PathDataNode pathDataNode, PathDataNode pathDataNode2, float f) {
            for (int i = 0; i < pathDataNode.params.length; i++) {
                this.params[i] = (pathDataNode.params[i] * (1.0f - f)) + (pathDataNode2.params[i] * f);
            }
        }
    }

    PathParser() {
    }

    private static void addNode(ArrayList<PathDataNode> arrayList, char c, float[] fArr) {
        arrayList.add(new PathDataNode(c, fArr));
    }

    public static boolean canMorph(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        if (pathDataNodeArr == null || pathDataNodeArr2 == null || pathDataNodeArr.length != pathDataNodeArr2.length) {
            return false;
        }
        for (int i = 0; i < pathDataNodeArr.length; i++) {
            if (pathDataNodeArr[i].type != pathDataNodeArr2[i].type || pathDataNodeArr[i].params.length != pathDataNodeArr2[i].params.length) {
                return false;
            }
        }
        return true;
    }

    static float[] copyOfRange(float[] fArr, int i, int i2) {
        if (i > i2) {
            throw new IllegalArgumentException();
        }
        int length = fArr.length;
        if (i < 0 || i > length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int i3 = i2 - i;
        int iMin = Math.min(i3, length - i);
        float[] fArr2 = new float[i3];
        System.arraycopy(fArr, i, fArr2, 0, iMin);
        return fArr2;
    }

    public static PathDataNode[] createNodesFromPathData(String str) {
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int i = 1;
        int i2 = 0;
        while (i < str.length()) {
            int iNextStart = nextStart(str, i);
            String strTrim = str.substring(i2, iNextStart).trim();
            if (strTrim.length() > 0) {
                addNode(arrayList, strTrim.charAt(0), getFloats(strTrim));
            }
            i2 = iNextStart;
            i = iNextStart + 1;
        }
        if (i - i2 == 1 && i2 < str.length()) {
            addNode(arrayList, str.charAt(i2), new float[0]);
        }
        return (PathDataNode[]) arrayList.toArray(new PathDataNode[arrayList.size()]);
    }

    public static Path createPathFromPathData(String str) {
        Path path = new Path();
        PathDataNode[] pathDataNodeArrCreateNodesFromPathData = createNodesFromPathData(str);
        if (pathDataNodeArrCreateNodesFromPathData == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath(pathDataNodeArrCreateNodesFromPathData, path);
            return path;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in parsing " + str, e);
        }
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] pathDataNodeArr) {
        if (pathDataNodeArr == null) {
            return null;
        }
        PathDataNode[] pathDataNodeArr2 = new PathDataNode[pathDataNodeArr.length];
        for (int i = 0; i < pathDataNodeArr.length; i++) {
            pathDataNodeArr2[i] = new PathDataNode(pathDataNodeArr[i]);
        }
        return pathDataNodeArr2;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x002c, code lost:
    
        if (r2 == false) goto L15;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0038 A[LOOP:0: B:3:0x0007->B:24:0x0038, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x003b A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void extract(java.lang.String r8, int r9, android.support.graphics.drawable.PathParser.ExtractFloatResult r10) {
        /*
            r0 = 0
            r10.mEndWithNegOrDot = r0
            r1 = r9
            r2 = r0
            r3 = r2
            r4 = r3
        L7:
            int r5 = r8.length()
            if (r1 >= r5) goto L3b
            char r5 = r8.charAt(r1)
            r6 = 32
            r7 = 1
            if (r5 == r6) goto L33
            r6 = 69
            if (r5 == r6) goto L31
            r6 = 101(0x65, float:1.42E-43)
            if (r5 == r6) goto L31
            switch(r5) {
                case 44: goto L33;
                case 45: goto L2a;
                case 46: goto L22;
                default: goto L21;
            }
        L21:
            goto L2f
        L22:
            if (r3 != 0) goto L27
            r2 = r0
            r3 = r7
            goto L35
        L27:
            r10.mEndWithNegOrDot = r7
            goto L33
        L2a:
            if (r1 == r9) goto L2f
            if (r2 != 0) goto L2f
            goto L27
        L2f:
            r2 = r0
            goto L35
        L31:
            r2 = r7
            goto L35
        L33:
            r2 = r0
            r4 = r7
        L35:
            if (r4 == 0) goto L38
            goto L3b
        L38:
            int r1 = r1 + 1
            goto L7
        L3b:
            r10.mEndPosition = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.PathParser.extract(java.lang.String, int, android.support.graphics.drawable.PathParser$ExtractFloatResult):void");
    }

    private static float[] getFloats(String str) {
        int i = 1;
        if ((str.charAt(0) == 'z') || (str.charAt(0) == 'Z')) {
            return new float[0];
        }
        try {
            float[] fArr = new float[str.length()];
            ExtractFloatResult extractFloatResult = new ExtractFloatResult();
            int length = str.length();
            int i2 = 0;
            while (i < length) {
                extract(str, i, extractFloatResult);
                int i3 = extractFloatResult.mEndPosition;
                if (i < i3) {
                    fArr[i2] = Float.parseFloat(str.substring(i, i3));
                    i2++;
                }
                i = extractFloatResult.mEndWithNegOrDot ? i3 : i3 + 1;
            }
            return copyOfRange(fArr, 0, i2);
        } catch (NumberFormatException e) {
            throw new RuntimeException("error in parsing \"" + str + "\"", e);
        }
    }

    private static int nextStart(String str, int i) {
        while (i < str.length()) {
            char cCharAt = str.charAt(i);
            if (((cCharAt - 'A') * (cCharAt - 'Z') <= 0 || (cCharAt - 'a') * (cCharAt - 'z') <= 0) && cCharAt != 'e' && cCharAt != 'E') {
                return i;
            }
            i++;
        }
        return i;
    }

    public static void updateNodes(PathDataNode[] pathDataNodeArr, PathDataNode[] pathDataNodeArr2) {
        for (int i = 0; i < pathDataNodeArr2.length; i++) {
            pathDataNodeArr[i].type = pathDataNodeArr2[i].type;
            for (int i2 = 0; i2 < pathDataNodeArr2[i].params.length; i2++) {
                pathDataNodeArr[i].params[i2] = pathDataNodeArr2[i].params[i2];
            }
        }
    }
}
