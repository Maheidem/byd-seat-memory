package android.support.constraint.solver;

import android.support.constraint.solver.SolverVariable;
import java.util.Arrays;

/* loaded from: classes.dex */
public class ArrayVariables {
    private static final boolean DEBUG = false;
    private final Cache mCache;
    private final ArrayRow mRow;
    int currentSize = 0;
    private int ROW_SIZE = 8;
    private int[] mArrayIndices = new int[this.ROW_SIZE];
    private float[] mArrayValues = new float[this.ROW_SIZE];
    private boolean[] mArrayValid = new boolean[this.ROW_SIZE];

    ArrayVariables(ArrayRow arrayRow, Cache cache) {
        this.mRow = arrayRow;
        this.mCache = cache;
    }

    private boolean isNew(SolverVariable solverVariable, LinearSystem linearSystem) {
        return solverVariable.mClientEquationsCount <= 1;
    }

    final void add(SolverVariable solverVariable, float f, boolean z) {
        if (f == 0.0f) {
            return;
        }
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayIndices[i] == solverVariable.id) {
                float[] fArr = this.mArrayValues;
                fArr[i] = fArr[i] + f;
                return;
            }
        }
        if (this.currentSize >= this.mArrayIndices.length) {
            this.ROW_SIZE *= 2;
            this.mArrayValues = Arrays.copyOf(this.mArrayValues, this.ROW_SIZE);
            this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
            this.mArrayValid = Arrays.copyOf(this.mArrayValid, this.ROW_SIZE);
        }
        this.mArrayIndices[this.currentSize] = solverVariable.id;
        float[] fArr2 = this.mArrayValues;
        int i2 = this.currentSize;
        fArr2[i2] = fArr2[i2] + f;
        this.mArrayValid[this.currentSize] = true;
        solverVariable.usageInRowCount++;
        solverVariable.addToRow(this.mRow);
        if (this.mArrayValues[this.currentSize] == 0.0f) {
            solverVariable.usageInRowCount--;
            solverVariable.removeFromRow(this.mRow);
            this.mArrayValid[this.currentSize] = false;
        }
        this.currentSize++;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0094 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    android.support.constraint.solver.SolverVariable chooseSubject(android.support.constraint.solver.LinearSystem r15) {
        /*
            r14 = this;
            r0 = 0
            r1 = 0
            r2 = 0
            r3 = r0
            r4 = r3
            r0 = r1
            r6 = r0
            r8 = r6
            r5 = r2
            r7 = r5
        La:
            int r9 = r14.currentSize
            if (r0 >= r9) goto L98
            boolean[] r9 = r14.mArrayValid
            boolean r9 = r9[r0]
            r10 = 1
            if (r9 != 0) goto L17
            goto L94
        L17:
            float[] r9 = r14.mArrayValues
            r9 = r9[r0]
            r11 = 981668463(0x3a83126f, float:0.001)
            android.support.constraint.solver.Cache r12 = r14.mCache
            android.support.constraint.solver.SolverVariable[] r12 = r12.mIndexedVariables
            int[] r13 = r14.mArrayIndices
            r13 = r13[r0]
            r12 = r12[r13]
            int r13 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r13 >= 0) goto L42
            r11 = -1165815185(0xffffffffba83126f, float:-0.001)
            int r11 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r11 <= 0) goto L4f
            float[] r9 = r14.mArrayValues
            r9[r0] = r2
            boolean[] r9 = r14.mArrayValid
            r9[r0] = r1
        L3b:
            android.support.constraint.solver.ArrayRow r9 = r14.mRow
            r12.removeFromRow(r9)
            r9 = r2
            goto L4f
        L42:
            int r11 = (r9 > r11 ? 1 : (r9 == r11 ? 0 : -1))
            if (r11 >= 0) goto L4f
            float[] r9 = r14.mArrayValues
            r9[r0] = r2
            boolean[] r9 = r14.mArrayValid
            r9[r0] = r1
            goto L3b
        L4f:
            int r11 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r11 != 0) goto L54
            goto L94
        L54:
            android.support.constraint.solver.SolverVariable$Type r11 = r12.mType
            android.support.constraint.solver.SolverVariable$Type r13 = android.support.constraint.solver.SolverVariable.Type.UNRESTRICTED
            if (r11 != r13) goto L74
            if (r3 != 0) goto L64
        L5c:
            boolean r3 = r14.isNew(r12, r15)
            r6 = r3
            r5 = r9
        L62:
            r3 = r12
            goto L94
        L64:
            int r11 = (r5 > r9 ? 1 : (r5 == r9 ? 0 : -1))
            if (r11 <= 0) goto L69
            goto L5c
        L69:
            if (r6 != 0) goto L94
            boolean r11 = r14.isNew(r12, r15)
            if (r11 == 0) goto L94
            r5 = r9
            r6 = r10
            goto L62
        L74:
            if (r3 != 0) goto L94
            int r11 = (r9 > r2 ? 1 : (r9 == r2 ? 0 : -1))
            if (r11 >= 0) goto L94
            if (r4 != 0) goto L84
        L7c:
            boolean r4 = r14.isNew(r12, r15)
            r8 = r4
            r7 = r9
        L82:
            r4 = r12
            goto L94
        L84:
            int r11 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r11 <= 0) goto L89
            goto L7c
        L89:
            if (r8 != 0) goto L94
            boolean r11 = r14.isNew(r12, r15)
            if (r11 == 0) goto L94
            r7 = r9
            r8 = r10
            goto L82
        L94:
            int r0 = r0 + 1
            goto La
        L98:
            if (r3 == 0) goto L9b
            return r3
        L9b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.ArrayVariables.chooseSubject(android.support.constraint.solver.LinearSystem):android.support.constraint.solver.SolverVariable");
    }

    public final void clear() {
        for (int i = 0; i < this.currentSize; i++) {
            SolverVariable solverVariable = this.mCache.mIndexedVariables[this.mArrayIndices[i]];
            if (solverVariable != null) {
                solverVariable.removeFromRow(this.mRow);
            }
        }
        this.currentSize = 0;
    }

    final boolean containsKey(SolverVariable solverVariable) {
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayValid[i] && this.mArrayIndices[i] == solverVariable.id) {
                return true;
            }
        }
        return false;
    }

    public void display() {
        SolverVariable variable;
        int i = this.currentSize;
        System.out.print("{ ");
        for (int i2 = 0; i2 < i; i2++) {
            if (this.mArrayValid[i2] && (variable = getVariable(i2)) != null) {
                System.out.print(variable + " = " + getVariableValue(i2) + " ");
            }
        }
        System.out.println(" }");
    }

    void divideByAmount(float f) {
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayValid[i]) {
                float[] fArr = this.mArrayValues;
                fArr[i] = fArr[i] / f;
            }
        }
    }

    public final float get(SolverVariable solverVariable) {
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayValid[i] && this.mArrayIndices[i] == solverVariable.id) {
                return this.mArrayValues[i];
            }
        }
        return 0.0f;
    }

    SolverVariable getPivotCandidate() {
        SolverVariable solverVariable = null;
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayValid[i] && this.mArrayValues[i] < 0.0f) {
                SolverVariable solverVariable2 = this.mCache.mIndexedVariables[this.mArrayIndices[i]];
                if (solverVariable == null || solverVariable.strength < solverVariable2.strength) {
                    solverVariable = solverVariable2;
                }
            }
        }
        return solverVariable;
    }

    SolverVariable getPivotCandidate(boolean[] zArr, SolverVariable solverVariable) {
        SolverVariable solverVariable2 = null;
        float f = 0.0f;
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayValid[i] && this.mArrayValues[i] < 0.0f) {
                SolverVariable solverVariable3 = this.mCache.mIndexedVariables[this.mArrayIndices[i]];
                if ((zArr == null || !zArr[solverVariable3.id]) && solverVariable3 != solverVariable && (solverVariable3.mType == SolverVariable.Type.SLACK || solverVariable3.mType == SolverVariable.Type.ERROR)) {
                    float f2 = this.mArrayValues[i];
                    if (f2 < f) {
                        solverVariable2 = solverVariable3;
                        f = f2;
                    }
                }
            }
        }
        return solverVariable2;
    }

    final SolverVariable getVariable(int i) {
        if (i < this.currentSize) {
            return this.mCache.mIndexedVariables[this.mArrayIndices[i]];
        }
        return null;
    }

    final float getVariableValue(int i) {
        if (i < this.currentSize) {
            return this.mArrayValues[i];
        }
        return 0.0f;
    }

    boolean hasAtLeastOnePositiveVariable() {
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayValid[i] && this.mArrayValues[i] > 0.0f) {
                return true;
            }
        }
        return false;
    }

    void invert() {
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayValid[i]) {
                float[] fArr = this.mArrayValues;
                fArr[i] = fArr[i] * (-1.0f);
            }
        }
    }

    public final void put(SolverVariable solverVariable, float f) {
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayIndices[i] == solverVariable.id) {
                this.mArrayValues[i] = f;
                if (f == 0.0f) {
                    this.mArrayValid[i] = false;
                    solverVariable.removeFromRow(this.mRow);
                    return;
                }
                return;
            }
        }
        if (this.currentSize >= this.mArrayIndices.length) {
            this.ROW_SIZE *= 2;
            this.mArrayValues = Arrays.copyOf(this.mArrayValues, this.ROW_SIZE);
            this.mArrayIndices = Arrays.copyOf(this.mArrayIndices, this.ROW_SIZE);
            this.mArrayValid = Arrays.copyOf(this.mArrayValid, this.ROW_SIZE);
        }
        this.mArrayIndices[this.currentSize] = solverVariable.id;
        this.mArrayValues[this.currentSize] = f;
        this.mArrayValid[this.currentSize] = true;
        if (f == 0.0f) {
            solverVariable.removeFromRow(this.mRow);
            this.mArrayValid[this.currentSize] = false;
        }
        solverVariable.usageInRowCount++;
        solverVariable.addToRow(this.mRow);
        this.currentSize++;
    }

    public final float remove(SolverVariable solverVariable, boolean z) {
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayIndices[i] == solverVariable.id) {
                float f = this.mArrayValues[i];
                this.mArrayValues[i] = 0.0f;
                this.mArrayValid[i] = false;
                if (z) {
                    solverVariable.usageInRowCount--;
                    solverVariable.removeFromRow(this.mRow);
                }
                return f;
            }
        }
        return 0.0f;
    }

    int sizeInBytes() {
        return 0 + (3 * this.mArrayIndices.length * 4) + 36;
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayValid[i] && this.mArrayValues[i] != 0.0f) {
                str = ((str + " -> ") + this.mArrayValues[i] + " : ") + this.mCache.mIndexedVariables[this.mArrayIndices[i]];
            }
        }
        return str;
    }

    final void updateFromRow(ArrayRow arrayRow, ArrayRow arrayRow2, boolean z) {
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayValid[i] && this.mArrayIndices[i] == arrayRow2.variable.id) {
                float f = this.mArrayValues[i];
                if (f != 0.0f) {
                    this.mArrayValues[i] = 0.0f;
                    this.mArrayValid[i] = false;
                    if (z) {
                        arrayRow2.variable.removeFromRow(this.mRow);
                    }
                    ArrayVariables arrayVariables = (ArrayVariables) arrayRow2.variables;
                    for (int i2 = 0; i2 < arrayVariables.currentSize; i2++) {
                        add(this.mCache.mIndexedVariables[arrayVariables.mArrayIndices[i2]], arrayVariables.mArrayValues[i2] * f, z);
                    }
                    arrayRow.constantValue += arrayRow2.constantValue * f;
                    if (z) {
                        arrayRow2.variable.removeFromRow(arrayRow);
                    }
                }
            }
        }
    }

    void updateFromSystem(ArrayRow arrayRow, ArrayRow[] arrayRowArr) {
        for (int i = 0; i < this.currentSize; i++) {
            if (this.mArrayValid[i]) {
                SolverVariable solverVariable = this.mCache.mIndexedVariables[this.mArrayIndices[i]];
                if (solverVariable.definitionId != -1) {
                    float f = this.mArrayValues[i];
                    this.mArrayValues[i] = 0.0f;
                    this.mArrayValid[i] = false;
                    solverVariable.removeFromRow(this.mRow);
                    ArrayRow arrayRow2 = arrayRowArr[solverVariable.definitionId];
                    if (!arrayRow2.isSimpleDefinition) {
                        ArrayVariables arrayVariables = (ArrayVariables) arrayRow2.variables;
                        for (int i2 = 0; i2 < arrayVariables.currentSize; i2++) {
                            add(this.mCache.mIndexedVariables[arrayVariables.mArrayIndices[i2]], arrayVariables.mArrayValues[i2] * f, true);
                        }
                    }
                    arrayRow.constantValue += arrayRow2.constantValue * f;
                    arrayRow2.variable.removeFromRow(arrayRow);
                }
            }
        }
    }
}
