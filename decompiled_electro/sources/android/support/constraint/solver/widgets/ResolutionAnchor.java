package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor;

/* loaded from: classes.dex */
public class ResolutionAnchor extends ResolutionNode {
    public static final int BARRIER_CONNECTION = 5;
    public static final int CENTER_CONNECTION = 2;
    public static final int CHAIN_CONNECTION = 4;
    public static final int DIRECT_CONNECTION = 1;
    public static final int MATCH_CONNECTION = 3;
    public static final int UNCONNECTED = 0;
    float computedValue;
    ConstraintAnchor myAnchor;
    float offset;
    private ResolutionAnchor opposite;
    private float oppositeOffset;
    float resolvedOffset;
    ResolutionAnchor resolvedTarget;
    ResolutionAnchor target;
    int type = 0;
    private ResolutionDimension dimension = null;
    private int dimensionMultiplier = 1;
    private ResolutionDimension oppositeDimension = null;
    private int oppositeDimensionMultiplier = 1;

    public ResolutionAnchor(ConstraintAnchor constraintAnchor) {
        this.myAnchor = constraintAnchor;
    }

    void addResolvedValue(LinearSystem linearSystem) {
        SolverVariable solverVariable = this.myAnchor.getSolverVariable();
        if (this.resolvedTarget == null) {
            linearSystem.addEquality(solverVariable, (int) this.resolvedOffset);
        } else {
            linearSystem.addEquality(solverVariable, linearSystem.createObjectVariable(this.resolvedTarget.myAnchor), (int) this.resolvedOffset, 6);
        }
    }

    public void dependsOn(int i, ResolutionAnchor resolutionAnchor, int i2) {
        this.type = i;
        this.target = resolutionAnchor;
        this.offset = i2;
        this.target.addDependent(this);
    }

    public void dependsOn(ResolutionAnchor resolutionAnchor, int i) {
        this.target = resolutionAnchor;
        this.offset = i;
        this.target.addDependent(this);
    }

    public void dependsOn(ResolutionAnchor resolutionAnchor, int i, ResolutionDimension resolutionDimension) {
        this.target = resolutionAnchor;
        this.target.addDependent(this);
        this.dimension = resolutionDimension;
        this.dimensionMultiplier = i;
        this.dimension.addDependent(this);
    }

    public float getResolvedValue() {
        return this.resolvedOffset;
    }

    @Override // android.support.constraint.solver.widgets.ResolutionNode
    public void remove(ResolutionDimension resolutionDimension) {
        if (this.dimension == resolutionDimension) {
            this.dimension = null;
            this.offset = this.dimensionMultiplier;
        } else if (this.dimension == this.oppositeDimension) {
            this.oppositeDimension = null;
            this.oppositeOffset = this.oppositeDimensionMultiplier;
        }
        resolve();
    }

    @Override // android.support.constraint.solver.widgets.ResolutionNode
    public void reset() {
        super.reset();
        this.target = null;
        this.offset = 0.0f;
        this.dimension = null;
        this.dimensionMultiplier = 1;
        this.oppositeDimension = null;
        this.oppositeDimensionMultiplier = 1;
        this.resolvedTarget = null;
        this.resolvedOffset = 0.0f;
        this.computedValue = 0.0f;
        this.opposite = null;
        this.oppositeOffset = 0.0f;
        this.type = 0;
    }

    @Override // android.support.constraint.solver.widgets.ResolutionNode
    public void resolve() {
        float f;
        ResolutionAnchor resolutionAnchor;
        float width;
        float f2;
        float f3;
        if (this.state == 1 || this.type == 4) {
            return;
        }
        if (this.dimension != null) {
            if (this.dimension.state != 1) {
                return;
            } else {
                this.offset = this.dimensionMultiplier * this.dimension.value;
            }
        }
        if (this.oppositeDimension != null) {
            if (this.oppositeDimension.state != 1) {
                return;
            } else {
                this.oppositeOffset = this.oppositeDimensionMultiplier * this.oppositeDimension.value;
            }
        }
        if (this.type == 1 && (this.target == null || this.target.state == 1)) {
            if (this.target == null) {
                this.resolvedTarget = this;
                f3 = this.offset;
            } else {
                this.resolvedTarget = this.target.resolvedTarget;
                f3 = this.target.resolvedOffset + this.offset;
            }
            this.resolvedOffset = f3;
        } else {
            if (this.type == 2 && this.target != null && this.target.state == 1 && this.opposite != null && this.opposite.target != null && this.opposite.target.state == 1) {
                if (LinearSystem.getMetrics() != null) {
                    LinearSystem.getMetrics().centerConnectionResolved++;
                }
                this.resolvedTarget = this.target.resolvedTarget;
                this.opposite.resolvedTarget = this.opposite.target.resolvedTarget;
                if (this.oppositeOffset > 0.0f) {
                    f = this.target.resolvedOffset;
                    resolutionAnchor = this.opposite.target;
                } else {
                    f = this.opposite.target.resolvedOffset;
                    resolutionAnchor = this.target;
                }
                float f4 = f - resolutionAnchor.resolvedOffset;
                if (this.myAnchor.mType == ConstraintAnchor.Type.LEFT || this.myAnchor.mType == ConstraintAnchor.Type.RIGHT) {
                    width = f4 - this.myAnchor.mOwner.getWidth();
                    f2 = this.myAnchor.mOwner.mHorizontalBiasPercent;
                } else {
                    width = f4 - this.myAnchor.mOwner.getHeight();
                    f2 = this.myAnchor.mOwner.mVerticalBiasPercent;
                }
                int margin = this.myAnchor.getMargin();
                int margin2 = this.opposite.myAnchor.getMargin();
                if (this.myAnchor.getTarget() == this.opposite.myAnchor.getTarget()) {
                    f2 = 0.5f;
                    margin = 0;
                    margin2 = 0;
                }
                float f5 = margin;
                float f6 = margin2;
                float f7 = (width - f5) - f6;
                if (this.oppositeOffset > 0.0f) {
                    this.opposite.resolvedOffset = this.opposite.target.resolvedOffset + f6 + (f7 * f2);
                    this.resolvedOffset = (this.target.resolvedOffset - f5) - (f7 * (1.0f - f2));
                } else {
                    this.resolvedOffset = this.target.resolvedOffset + f5 + (f7 * f2);
                    this.opposite.resolvedOffset = (this.opposite.target.resolvedOffset - f6) - (f7 * (1.0f - f2));
                }
            } else {
                if (this.type != 3 || this.target == null || this.target.state != 1 || this.opposite == null || this.opposite.target == null || this.opposite.target.state != 1) {
                    if (this.type == 5) {
                        this.myAnchor.mOwner.resolve();
                        return;
                    }
                    return;
                }
                if (LinearSystem.getMetrics() != null) {
                    LinearSystem.getMetrics().matchConnectionResolved++;
                }
                this.resolvedTarget = this.target.resolvedTarget;
                this.opposite.resolvedTarget = this.opposite.target.resolvedTarget;
                this.resolvedOffset = this.target.resolvedOffset + this.offset;
                this.opposite.resolvedOffset = this.opposite.target.resolvedOffset + this.opposite.offset;
            }
            didResolve();
            this = this.opposite;
        }
        this.didResolve();
    }

    public void resolve(ResolutionAnchor resolutionAnchor, float f) {
        if (this.state == 0 || !(this.resolvedTarget == resolutionAnchor || this.resolvedOffset == f)) {
            this.resolvedTarget = resolutionAnchor;
            this.resolvedOffset = f;
            if (this.state == 1) {
                invalidate();
            }
            didResolve();
        }
    }

    String sType(int i) {
        return i == 1 ? "DIRECT" : i == 2 ? "CENTER" : i == 3 ? "MATCH" : i == 4 ? "CHAIN" : i == 5 ? "BARRIER" : "UNCONNECTED";
    }

    public void setOpposite(ResolutionAnchor resolutionAnchor, float f) {
        this.opposite = resolutionAnchor;
        this.oppositeOffset = f;
    }

    public void setOpposite(ResolutionAnchor resolutionAnchor, int i, ResolutionDimension resolutionDimension) {
        this.opposite = resolutionAnchor;
        this.oppositeDimension = resolutionDimension;
        this.oppositeDimensionMultiplier = i;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String toString() {
        StringBuilder sb;
        String str;
        if (this.state != 1) {
            sb = new StringBuilder();
            sb.append("{ ");
            sb.append(this.myAnchor);
            str = " UNRESOLVED} type: ";
        } else if (this.resolvedTarget == this) {
            sb = new StringBuilder();
            sb.append("[");
            sb.append(this.myAnchor);
            sb.append(", RESOLVED: ");
            sb.append(this.resolvedOffset);
            str = "]  type: ";
        } else {
            sb = new StringBuilder();
            sb.append("[");
            sb.append(this.myAnchor);
            sb.append(", RESOLVED: ");
            sb.append(this.resolvedTarget);
            sb.append(":");
            sb.append(this.resolvedOffset);
            str = "] type: ";
        }
        sb.append(str);
        sb.append(sType(this.type));
        return sb.toString();
    }

    public void update() {
        ConstraintAnchor target = this.myAnchor.getTarget();
        if (target == null) {
            return;
        }
        if (target.getTarget() == this.myAnchor) {
            this.type = 4;
            target.getResolutionNode().type = 4;
        }
        int margin = this.myAnchor.getMargin();
        if (this.myAnchor.mType == ConstraintAnchor.Type.RIGHT || this.myAnchor.mType == ConstraintAnchor.Type.BOTTOM) {
            margin = -margin;
        }
        dependsOn(target.getResolutionNode(), margin);
    }
}
