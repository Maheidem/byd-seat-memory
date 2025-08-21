package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor;

/* loaded from: classes.dex */
public class ConstraintHorizontalLayout extends ConstraintWidgetContainer {
    private ContentAlignment mAlignment;

    public enum ContentAlignment {
        BEGIN,
        MIDDLE,
        END,
        TOP,
        VERTICAL_MIDDLE,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public ConstraintHorizontalLayout() {
        this.mAlignment = ContentAlignment.MIDDLE;
    }

    public ConstraintHorizontalLayout(int i, int i2) {
        super(i, i2);
        this.mAlignment = ContentAlignment.MIDDLE;
    }

    public ConstraintHorizontalLayout(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
        this.mAlignment = ContentAlignment.MIDDLE;
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem) {
        if (this.mChildren.size() != 0) {
            int i = 0;
            int size = this.mChildren.size();
            ConstraintHorizontalLayout constraintHorizontalLayout = this;
            while (i < size) {
                ConstraintWidget constraintWidget = this.mChildren.get(i);
                if (constraintHorizontalLayout != this) {
                    constraintWidget.connect(ConstraintAnchor.Type.LEFT, constraintHorizontalLayout, ConstraintAnchor.Type.RIGHT);
                    constraintHorizontalLayout.connect(ConstraintAnchor.Type.RIGHT, constraintWidget, ConstraintAnchor.Type.LEFT);
                } else {
                    ConstraintAnchor.Strength strength = ConstraintAnchor.Strength.STRONG;
                    if (this.mAlignment == ContentAlignment.END) {
                        strength = ConstraintAnchor.Strength.WEAK;
                    }
                    constraintWidget.connect(ConstraintAnchor.Type.LEFT, constraintHorizontalLayout, ConstraintAnchor.Type.LEFT, 0, strength);
                }
                constraintWidget.connect(ConstraintAnchor.Type.TOP, this, ConstraintAnchor.Type.TOP);
                constraintWidget.connect(ConstraintAnchor.Type.BOTTOM, this, ConstraintAnchor.Type.BOTTOM);
                i++;
                constraintHorizontalLayout = constraintWidget;
            }
            if (constraintHorizontalLayout != this) {
                ConstraintAnchor.Strength strength2 = ConstraintAnchor.Strength.STRONG;
                if (this.mAlignment == ContentAlignment.BEGIN) {
                    strength2 = ConstraintAnchor.Strength.WEAK;
                }
                ConstraintHorizontalLayout constraintHorizontalLayout2 = constraintHorizontalLayout;
                constraintHorizontalLayout2.connect(ConstraintAnchor.Type.RIGHT, this, ConstraintAnchor.Type.RIGHT, 0, strength2);
            }
        }
        super.addToSolver(linearSystem);
    }
}
