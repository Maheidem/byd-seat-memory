package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor;
import android.support.constraint.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class ConstraintTableLayout extends ConstraintWidgetContainer {
    public static final int ALIGN_CENTER = 0;
    private static final int ALIGN_FULL = 3;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;
    private ArrayList<Guideline> mHorizontalGuidelines;
    private ArrayList<HorizontalSlice> mHorizontalSlices;
    private int mNumCols;
    private int mNumRows;
    private int mPadding;
    private boolean mVerticalGrowth;
    private ArrayList<Guideline> mVerticalGuidelines;
    private ArrayList<VerticalSlice> mVerticalSlices;
    private LinearSystem system;

    class HorizontalSlice {
        ConstraintWidget bottom;
        int padding;
        ConstraintWidget top;

        HorizontalSlice() {
        }
    }

    class VerticalSlice {
        int alignment = 1;
        ConstraintWidget left;
        int padding;
        ConstraintWidget right;

        VerticalSlice() {
        }
    }

    public ConstraintTableLayout() {
        this.mVerticalGrowth = true;
        this.mNumCols = 0;
        this.mNumRows = 0;
        this.mPadding = 8;
        this.mVerticalSlices = new ArrayList<>();
        this.mHorizontalSlices = new ArrayList<>();
        this.mVerticalGuidelines = new ArrayList<>();
        this.mHorizontalGuidelines = new ArrayList<>();
        this.system = null;
    }

    public ConstraintTableLayout(int i, int i2) {
        super(i, i2);
        this.mVerticalGrowth = true;
        this.mNumCols = 0;
        this.mNumRows = 0;
        this.mPadding = 8;
        this.mVerticalSlices = new ArrayList<>();
        this.mHorizontalSlices = new ArrayList<>();
        this.mVerticalGuidelines = new ArrayList<>();
        this.mHorizontalGuidelines = new ArrayList<>();
        this.system = null;
    }

    public ConstraintTableLayout(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
        this.mVerticalGrowth = true;
        this.mNumCols = 0;
        this.mNumRows = 0;
        this.mPadding = 8;
        this.mVerticalSlices = new ArrayList<>();
        this.mHorizontalSlices = new ArrayList<>();
        this.mVerticalGuidelines = new ArrayList<>();
        this.mHorizontalGuidelines = new ArrayList<>();
        this.system = null;
    }

    private void setChildrenConnections() {
        ConstraintAnchor anchor;
        ConstraintAnchor.Type type;
        ConstraintAnchor anchor2;
        ConstraintAnchor.Type type2;
        ConstraintAnchor anchor3;
        ConstraintAnchor.Strength strength;
        int size = this.mChildren.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget = this.mChildren.get(i2);
            int containerItemSkip = i + constraintWidget.getContainerItemSkip();
            int i3 = containerItemSkip % this.mNumCols;
            HorizontalSlice horizontalSlice = this.mHorizontalSlices.get(containerItemSkip / this.mNumCols);
            VerticalSlice verticalSlice = this.mVerticalSlices.get(i3);
            ConstraintWidget constraintWidget2 = verticalSlice.left;
            ConstraintWidget constraintWidget3 = verticalSlice.right;
            ConstraintWidget constraintWidget4 = horizontalSlice.top;
            ConstraintWidget constraintWidget5 = horizontalSlice.bottom;
            constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).connect(constraintWidget2.getAnchor(ConstraintAnchor.Type.LEFT), this.mPadding);
            if (constraintWidget3 instanceof Guideline) {
                anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
                type = ConstraintAnchor.Type.LEFT;
            } else {
                anchor = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
                type = ConstraintAnchor.Type.RIGHT;
            }
            anchor.connect(constraintWidget3.getAnchor(type), this.mPadding);
            switch (verticalSlice.alignment) {
                case 1:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).setStrength(ConstraintAnchor.Strength.STRONG);
                    anchor3 = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
                    strength = ConstraintAnchor.Strength.WEAK;
                    anchor3.setStrength(strength);
                    break;
                case 2:
                    constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).setStrength(ConstraintAnchor.Strength.WEAK);
                    anchor3 = constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT);
                    strength = ConstraintAnchor.Strength.STRONG;
                    anchor3.setStrength(strength);
                    break;
                case 3:
                    constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                    break;
            }
            constraintWidget.getAnchor(ConstraintAnchor.Type.TOP).connect(constraintWidget4.getAnchor(ConstraintAnchor.Type.TOP), this.mPadding);
            if (constraintWidget5 instanceof Guideline) {
                anchor2 = constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM);
                type2 = ConstraintAnchor.Type.TOP;
            } else {
                anchor2 = constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM);
                type2 = ConstraintAnchor.Type.BOTTOM;
            }
            anchor2.connect(constraintWidget5.getAnchor(type2), this.mPadding);
            i = containerItemSkip + 1;
        }
    }

    private void setHorizontalSlices() {
        this.mHorizontalSlices.clear();
        float f = 100.0f / this.mNumRows;
        ConstraintWidget constraintWidget = this;
        float f2 = f;
        for (int i = 0; i < this.mNumRows; i++) {
            HorizontalSlice horizontalSlice = new HorizontalSlice();
            horizontalSlice.top = constraintWidget;
            if (i < this.mNumRows - 1) {
                Guideline guideline = new Guideline();
                guideline.setOrientation(0);
                guideline.setParent(this);
                guideline.setGuidePercent((int) f2);
                f2 += f;
                horizontalSlice.bottom = guideline;
                this.mHorizontalGuidelines.add(guideline);
            } else {
                horizontalSlice.bottom = this;
            }
            constraintWidget = horizontalSlice.bottom;
            this.mHorizontalSlices.add(horizontalSlice);
        }
        updateDebugSolverNames();
    }

    private void setVerticalSlices() {
        this.mVerticalSlices.clear();
        float f = 100.0f / this.mNumCols;
        ConstraintWidget constraintWidget = this;
        float f2 = f;
        for (int i = 0; i < this.mNumCols; i++) {
            VerticalSlice verticalSlice = new VerticalSlice();
            verticalSlice.left = constraintWidget;
            if (i < this.mNumCols - 1) {
                Guideline guideline = new Guideline();
                guideline.setOrientation(1);
                guideline.setParent(this);
                guideline.setGuidePercent((int) f2);
                f2 += f;
                verticalSlice.right = guideline;
                this.mVerticalGuidelines.add(guideline);
            } else {
                verticalSlice.right = this;
            }
            constraintWidget = verticalSlice.right;
            this.mVerticalSlices.add(verticalSlice);
        }
        updateDebugSolverNames();
    }

    private void updateDebugSolverNames() {
        if (this.system == null) {
            return;
        }
        int size = this.mVerticalGuidelines.size();
        for (int i = 0; i < size; i++) {
            this.mVerticalGuidelines.get(i).setDebugSolverName(this.system, getDebugName() + ".VG" + i);
        }
        int size2 = this.mHorizontalGuidelines.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mHorizontalGuidelines.get(i2).setDebugSolverName(this.system, getDebugName() + ".HG" + i2);
        }
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem) {
        super.addToSolver(linearSystem);
        int size = this.mChildren.size();
        if (size == 0) {
            return;
        }
        setTableDimensions();
        if (linearSystem == this.mSystem) {
            int size2 = this.mVerticalGuidelines.size();
            int i = 0;
            while (true) {
                boolean z = true;
                if (i >= size2) {
                    break;
                }
                Guideline guideline = this.mVerticalGuidelines.get(i);
                if (getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
                    z = false;
                }
                guideline.setPositionRelaxed(z);
                guideline.addToSolver(linearSystem);
                i++;
            }
            int size3 = this.mHorizontalGuidelines.size();
            for (int i2 = 0; i2 < size3; i2++) {
                Guideline guideline2 = this.mHorizontalGuidelines.get(i2);
                guideline2.setPositionRelaxed(getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
                guideline2.addToSolver(linearSystem);
            }
            for (int i3 = 0; i3 < size; i3++) {
                this.mChildren.get(i3).addToSolver(linearSystem);
            }
        }
    }

    public void computeGuidelinesPercentPositions() {
        int size = this.mVerticalGuidelines.size();
        for (int i = 0; i < size; i++) {
            this.mVerticalGuidelines.get(i).inferRelativePercentPosition();
        }
        int size2 = this.mHorizontalGuidelines.size();
        for (int i2 = 0; i2 < size2; i2++) {
            this.mHorizontalGuidelines.get(i2).inferRelativePercentPosition();
        }
    }

    public void cycleColumnAlignment(int i) {
        int i2;
        VerticalSlice verticalSlice = this.mVerticalSlices.get(i);
        switch (verticalSlice.alignment) {
            case 0:
                i2 = 2;
                break;
            case 1:
                i2 = 0;
                break;
            case 2:
                i2 = 1;
                break;
            default:
                setChildrenConnections();
        }
        verticalSlice.alignment = i2;
        setChildrenConnections();
    }

    public String getColumnAlignmentRepresentation(int i) {
        VerticalSlice verticalSlice = this.mVerticalSlices.get(i);
        return verticalSlice.alignment == 1 ? "L" : verticalSlice.alignment == 0 ? "C" : verticalSlice.alignment == 3 ? "F" : verticalSlice.alignment == 2 ? "R" : "!";
    }

    public String getColumnsAlignmentRepresentation() {
        StringBuilder sb;
        String str;
        int size = this.mVerticalSlices.size();
        String string = "";
        for (int i = 0; i < size; i++) {
            VerticalSlice verticalSlice = this.mVerticalSlices.get(i);
            if (verticalSlice.alignment == 1) {
                sb = new StringBuilder();
                sb.append(string);
                str = "L";
            } else if (verticalSlice.alignment == 0) {
                sb = new StringBuilder();
                sb.append(string);
                str = "C";
            } else if (verticalSlice.alignment == 3) {
                sb = new StringBuilder();
                sb.append(string);
                str = "F";
            } else if (verticalSlice.alignment == 2) {
                sb = new StringBuilder();
                sb.append(string);
                str = "R";
            }
            sb.append(str);
            string = sb.toString();
        }
        return string;
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidgetContainer
    public ArrayList<Guideline> getHorizontalGuidelines() {
        return this.mHorizontalGuidelines;
    }

    public int getNumCols() {
        return this.mNumCols;
    }

    public int getNumRows() {
        return this.mNumRows;
    }

    public int getPadding() {
        return this.mPadding;
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidgetContainer, android.support.constraint.solver.widgets.ConstraintWidget
    public String getType() {
        return "ConstraintTableLayout";
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidgetContainer
    public ArrayList<Guideline> getVerticalGuidelines() {
        return this.mVerticalGuidelines;
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidgetContainer
    public boolean handlesInternalConstraints() {
        return true;
    }

    public boolean isVerticalGrowth() {
        return this.mVerticalGrowth;
    }

    public void setColumnAlignment(int i, int i2) {
        if (i < this.mVerticalSlices.size()) {
            this.mVerticalSlices.get(i).alignment = i2;
            setChildrenConnections();
        }
    }

    public void setColumnAlignment(String str) {
        int i;
        int length = str.length();
        for (int i2 = 0; i2 < length; i2++) {
            char cCharAt = str.charAt(i2);
            if (cCharAt == 'L') {
                i = 1;
            } else {
                if (cCharAt != 'C') {
                    if (cCharAt == 'F') {
                        i = 3;
                    } else if (cCharAt == 'R') {
                        i = 2;
                    }
                }
                setColumnAlignment(i2, 0);
            }
            setColumnAlignment(i2, i);
        }
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void setDebugSolverName(LinearSystem linearSystem, String str) {
        this.system = linearSystem;
        super.setDebugSolverName(linearSystem, str);
        updateDebugSolverNames();
    }

    public void setNumCols(int i) {
        if (!this.mVerticalGrowth || this.mNumCols == i) {
            return;
        }
        this.mNumCols = i;
        setVerticalSlices();
        setTableDimensions();
    }

    public void setNumRows(int i) {
        if (this.mVerticalGrowth || this.mNumCols == i) {
            return;
        }
        this.mNumRows = i;
        setHorizontalSlices();
        setTableDimensions();
    }

    public void setPadding(int i) {
        if (i > 1) {
            this.mPadding = i;
        }
    }

    public void setTableDimensions() {
        int size = this.mChildren.size();
        int containerItemSkip = 0;
        for (int i = 0; i < size; i++) {
            containerItemSkip += this.mChildren.get(i).getContainerItemSkip();
        }
        int i2 = size + containerItemSkip;
        if (this.mVerticalGrowth) {
            if (this.mNumCols == 0) {
                setNumCols(1);
            }
            int i3 = i2 / this.mNumCols;
            if (this.mNumCols * i3 < i2) {
                i3++;
            }
            if (this.mNumRows == i3 && this.mVerticalGuidelines.size() == this.mNumCols - 1) {
                return;
            }
            this.mNumRows = i3;
            setHorizontalSlices();
        } else {
            if (this.mNumRows == 0) {
                setNumRows(1);
            }
            int i4 = i2 / this.mNumRows;
            if (this.mNumRows * i4 < i2) {
                i4++;
            }
            if (this.mNumCols == i4 && this.mHorizontalGuidelines.size() == this.mNumRows - 1) {
                return;
            }
            this.mNumCols = i4;
            setVerticalSlices();
        }
        setChildrenConnections();
    }

    public void setVerticalGrowth(boolean z) {
        this.mVerticalGrowth = z;
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void updateFromSolver(LinearSystem linearSystem) {
        super.updateFromSolver(linearSystem);
        if (linearSystem == this.mSystem) {
            int size = this.mVerticalGuidelines.size();
            for (int i = 0; i < size; i++) {
                this.mVerticalGuidelines.get(i).updateFromSolver(linearSystem);
            }
            int size2 = this.mHorizontalGuidelines.size();
            for (int i2 = 0; i2 < size2; i2++) {
                this.mHorizontalGuidelines.get(i2).updateFromSolver(linearSystem);
            }
        }
    }
}
