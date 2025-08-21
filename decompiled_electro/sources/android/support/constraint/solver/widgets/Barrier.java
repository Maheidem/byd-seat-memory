package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintWidget;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class Barrier extends Helper {
    public static final int BOTTOM = 3;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;
    private int mBarrierType = 0;
    private ArrayList<ResolutionAnchor> mNodes = new ArrayList<>(4);
    private boolean mAllowsGoneWidget = true;

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void addToSolver(LinearSystem linearSystem) {
        boolean z;
        SolverVariable solverVariable;
        ConstraintAnchor constraintAnchor;
        this.mListAnchors[0] = this.mLeft;
        this.mListAnchors[2] = this.mTop;
        this.mListAnchors[1] = this.mRight;
        this.mListAnchors[3] = this.mBottom;
        for (int i = 0; i < this.mListAnchors.length; i++) {
            this.mListAnchors[i].mSolverVariable = linearSystem.createObjectVariable(this.mListAnchors[i]);
        }
        if (this.mBarrierType < 0 || this.mBarrierType >= 4) {
            return;
        }
        ConstraintAnchor constraintAnchor2 = this.mListAnchors[this.mBarrierType];
        for (int i2 = 0; i2 < this.mWidgetsCount; i2++) {
            ConstraintWidget constraintWidget = this.mWidgets[i2];
            if ((this.mAllowsGoneWidget || constraintWidget.allowedInBarrier()) && (((this.mBarrierType == 0 || this.mBarrierType == 1) && constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) || ((this.mBarrierType == 2 || this.mBarrierType == 3) && constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT))) {
                z = true;
                break;
            }
        }
        z = false;
        if (this.mBarrierType == 0 || this.mBarrierType == 1 ? getParent().getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT : getParent().getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            z = false;
        }
        for (int i3 = 0; i3 < this.mWidgetsCount; i3++) {
            ConstraintWidget constraintWidget2 = this.mWidgets[i3];
            if (this.mAllowsGoneWidget || constraintWidget2.allowedInBarrier()) {
                SolverVariable solverVariableCreateObjectVariable = linearSystem.createObjectVariable(constraintWidget2.mListAnchors[this.mBarrierType]);
                constraintWidget2.mListAnchors[this.mBarrierType].mSolverVariable = solverVariableCreateObjectVariable;
                if (this.mBarrierType == 0 || this.mBarrierType == 2) {
                    linearSystem.addLowerBarrier(constraintAnchor2.mSolverVariable, solverVariableCreateObjectVariable, z);
                } else {
                    linearSystem.addGreaterBarrier(constraintAnchor2.mSolverVariable, solverVariableCreateObjectVariable, z);
                }
            }
        }
        if (this.mBarrierType == 0) {
            linearSystem.addEquality(this.mRight.mSolverVariable, this.mLeft.mSolverVariable, 0, 6);
            if (z) {
                return;
            }
            solverVariable = this.mLeft.mSolverVariable;
            constraintAnchor = this.mParent.mRight;
        } else if (this.mBarrierType == 1) {
            linearSystem.addEquality(this.mLeft.mSolverVariable, this.mRight.mSolverVariable, 0, 6);
            if (z) {
                return;
            }
            solverVariable = this.mLeft.mSolverVariable;
            constraintAnchor = this.mParent.mLeft;
        } else if (this.mBarrierType == 2) {
            linearSystem.addEquality(this.mBottom.mSolverVariable, this.mTop.mSolverVariable, 0, 6);
            if (z) {
                return;
            }
            solverVariable = this.mTop.mSolverVariable;
            constraintAnchor = this.mParent.mBottom;
        } else {
            if (this.mBarrierType != 3) {
                return;
            }
            linearSystem.addEquality(this.mTop.mSolverVariable, this.mBottom.mSolverVariable, 0, 6);
            if (z) {
                return;
            }
            solverVariable = this.mTop.mSolverVariable;
            constraintAnchor = this.mParent.mTop;
        }
        linearSystem.addEquality(solverVariable, constraintAnchor.mSolverVariable, 0, 5);
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public boolean allowedInBarrier() {
        return true;
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void analyze(int i) {
        ConstraintAnchor constraintAnchor;
        ConstraintAnchor constraintAnchor2;
        ConstraintAnchor constraintAnchor3;
        ResolutionAnchor resolutionNode;
        if (this.mParent != null && ((ConstraintWidgetContainer) this.mParent).optimizeFor(2)) {
            switch (this.mBarrierType) {
                case 0:
                    constraintAnchor = this.mLeft;
                    break;
                case 1:
                    constraintAnchor = this.mRight;
                    break;
                case 2:
                    constraintAnchor = this.mTop;
                    break;
                case 3:
                    constraintAnchor = this.mBottom;
                    break;
                default:
                    return;
            }
            ResolutionAnchor resolutionNode2 = constraintAnchor.getResolutionNode();
            resolutionNode2.setType(5);
            if (this.mBarrierType == 0 || this.mBarrierType == 1) {
                this.mTop.getResolutionNode().resolve(null, 0.0f);
                constraintAnchor2 = this.mBottom;
            } else {
                this.mLeft.getResolutionNode().resolve(null, 0.0f);
                constraintAnchor2 = this.mRight;
            }
            constraintAnchor2.getResolutionNode().resolve(null, 0.0f);
            this.mNodes.clear();
            for (int i2 = 0; i2 < this.mWidgetsCount; i2++) {
                ConstraintWidget constraintWidget = this.mWidgets[i2];
                if (this.mAllowsGoneWidget || constraintWidget.allowedInBarrier()) {
                    switch (this.mBarrierType) {
                        case 0:
                            constraintAnchor3 = constraintWidget.mLeft;
                            resolutionNode = constraintAnchor3.getResolutionNode();
                            break;
                        case 1:
                            constraintAnchor3 = constraintWidget.mRight;
                            resolutionNode = constraintAnchor3.getResolutionNode();
                            break;
                        case 2:
                            constraintAnchor3 = constraintWidget.mTop;
                            resolutionNode = constraintAnchor3.getResolutionNode();
                            break;
                        case 3:
                            constraintAnchor3 = constraintWidget.mBottom;
                            resolutionNode = constraintAnchor3.getResolutionNode();
                            break;
                        default:
                            resolutionNode = null;
                            break;
                    }
                    if (resolutionNode != null) {
                        this.mNodes.add(resolutionNode);
                        resolutionNode.addDependent(resolutionNode2);
                    }
                }
            }
        }
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void resetResolutionNodes() {
        super.resetResolutionNodes();
        this.mNodes.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007b  */
    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void resolve() {
        /*
            r8 = this;
            int r0 = r8.mBarrierType
            r1 = 2139095039(0x7f7fffff, float:3.4028235E38)
            r2 = 0
            switch(r0) {
                case 0: goto L18;
                case 1: goto L10;
                case 2: goto Ld;
                case 3: goto La;
                default: goto L9;
            }
        L9:
            return
        La:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r8.mBottom
            goto L12
        Ld:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r8.mTop
            goto L1a
        L10:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r8.mRight
        L12:
            android.support.constraint.solver.widgets.ResolutionAnchor r0 = r0.getResolutionNode()
            r1 = r2
            goto L1e
        L18:
            android.support.constraint.solver.widgets.ConstraintAnchor r0 = r8.mLeft
        L1a:
            android.support.constraint.solver.widgets.ResolutionAnchor r0 = r0.getResolutionNode()
        L1e:
            java.util.ArrayList<android.support.constraint.solver.widgets.ResolutionAnchor> r2 = r8.mNodes
            int r2 = r2.size()
            r3 = 0
            r4 = 0
        L26:
            if (r4 >= r2) goto L54
            java.util.ArrayList<android.support.constraint.solver.widgets.ResolutionAnchor> r5 = r8.mNodes
            java.lang.Object r5 = r5.get(r4)
            android.support.constraint.solver.widgets.ResolutionAnchor r5 = (android.support.constraint.solver.widgets.ResolutionAnchor) r5
            int r6 = r5.state
            r7 = 1
            if (r6 == r7) goto L36
            return
        L36:
            int r6 = r8.mBarrierType
            if (r6 == 0) goto L47
            int r6 = r8.mBarrierType
            r7 = 2
            if (r6 != r7) goto L40
            goto L47
        L40:
            float r6 = r5.resolvedOffset
            int r6 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r6 <= 0) goto L51
            goto L4d
        L47:
            float r6 = r5.resolvedOffset
            int r6 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r6 >= 0) goto L51
        L4d:
            float r1 = r5.resolvedOffset
            android.support.constraint.solver.widgets.ResolutionAnchor r3 = r5.resolvedTarget
        L51:
            int r4 = r4 + 1
            goto L26
        L54:
            android.support.constraint.solver.Metrics r2 = android.support.constraint.solver.LinearSystem.getMetrics()
            if (r2 == 0) goto L65
            android.support.constraint.solver.Metrics r2 = android.support.constraint.solver.LinearSystem.getMetrics()
            long r4 = r2.barrierConnectionResolved
            r6 = 1
            long r4 = r4 + r6
            r2.barrierConnectionResolved = r4
        L65:
            r0.resolvedTarget = r3
            r0.resolvedOffset = r1
            r0.didResolve()
            int r0 = r8.mBarrierType
            switch(r0) {
                case 0: goto L7b;
                case 1: goto L78;
                case 2: goto L75;
                case 3: goto L72;
                default: goto L71;
            }
        L71:
            return
        L72:
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mTop
            goto L7d
        L75:
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mBottom
            goto L7d
        L78:
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mLeft
            goto L7d
        L7b:
            android.support.constraint.solver.widgets.ConstraintAnchor r8 = r8.mRight
        L7d:
            android.support.constraint.solver.widgets.ResolutionAnchor r8 = r8.getResolutionNode()
            r8.resolve(r3, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.Barrier.resolve():void");
    }

    public void setAllowsGoneWidget(boolean z) {
        this.mAllowsGoneWidget = z;
    }

    public void setBarrierType(int i) {
        this.mBarrierType = i;
    }
}
