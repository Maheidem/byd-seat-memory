package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.v7.widget.ActivityChooserView;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class WidgetContainer extends ConstraintWidget {
    protected ArrayList<ConstraintWidget> mChildren;

    public WidgetContainer() {
        this.mChildren = new ArrayList<>();
    }

    public WidgetContainer(int i, int i2) {
        super(i, i2);
        this.mChildren = new ArrayList<>();
    }

    public WidgetContainer(int i, int i2, int i3, int i4) {
        super(i, i2, i3, i4);
        this.mChildren = new ArrayList<>();
    }

    public static Rectangle getBounds(ArrayList<ConstraintWidget> arrayList) {
        Rectangle rectangle = new Rectangle();
        if (arrayList.size() == 0) {
            return rectangle;
        }
        int size = arrayList.size();
        int x = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int y = Integer.MAX_VALUE;
        int right = 0;
        int bottom = 0;
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = arrayList.get(i);
            if (constraintWidget.getX() < x) {
                x = constraintWidget.getX();
            }
            if (constraintWidget.getY() < y) {
                y = constraintWidget.getY();
            }
            if (constraintWidget.getRight() > right) {
                right = constraintWidget.getRight();
            }
            if (constraintWidget.getBottom() > bottom) {
                bottom = constraintWidget.getBottom();
            }
        }
        rectangle.setBounds(x, y, right - x, bottom - y);
        return rectangle;
    }

    public void add(ConstraintWidget constraintWidget) {
        this.mChildren.add(constraintWidget);
        if (constraintWidget.getParent() != null) {
            ((WidgetContainer) constraintWidget.getParent()).remove(constraintWidget);
        }
        constraintWidget.setParent(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x006d A[PHI: r3
      0x006d: PHI (r3v5 android.support.constraint.solver.widgets.WidgetContainer) = 
      (r3v8 android.support.constraint.solver.widgets.WidgetContainer)
      (r3v9 android.support.constraint.solver.widgets.WidgetContainer)
     binds: [B:26:0x006b, B:17:0x0044] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Type inference failed for: r3v4, types: [android.support.constraint.solver.widgets.ConstraintWidget] */
    /* JADX WARN: Type inference failed for: r3v7, types: [android.support.constraint.solver.widgets.ConstraintWidget] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.support.constraint.solver.widgets.ConstraintWidget findWidget(float r9, float r10) {
        /*
            r8 = this;
            int r0 = r8.getDrawX()
            int r1 = r8.getDrawY()
            int r2 = r8.getWidth()
            int r2 = r2 + r0
            int r3 = r8.getHeight()
            int r3 = r3 + r1
            float r0 = (float) r0
            int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r0 < 0) goto L28
            float r0 = (float) r2
            int r0 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
            if (r0 > 0) goto L28
            float r0 = (float) r1
            int r0 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r0 < 0) goto L28
            float r0 = (float) r3
            int r0 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r0 > 0) goto L28
            r0 = r8
            goto L29
        L28:
            r0 = 0
        L29:
            r1 = 0
            java.util.ArrayList<android.support.constraint.solver.widgets.ConstraintWidget> r2 = r8.mChildren
            int r2 = r2.size()
        L30:
            if (r1 >= r2) goto L71
            java.util.ArrayList<android.support.constraint.solver.widgets.ConstraintWidget> r3 = r8.mChildren
            java.lang.Object r3 = r3.get(r1)
            android.support.constraint.solver.widgets.ConstraintWidget r3 = (android.support.constraint.solver.widgets.ConstraintWidget) r3
            boolean r4 = r3 instanceof android.support.constraint.solver.widgets.WidgetContainer
            if (r4 == 0) goto L47
            android.support.constraint.solver.widgets.WidgetContainer r3 = (android.support.constraint.solver.widgets.WidgetContainer) r3
            android.support.constraint.solver.widgets.ConstraintWidget r3 = r3.findWidget(r9, r10)
            if (r3 == 0) goto L6e
            goto L6d
        L47:
            int r4 = r3.getDrawX()
            int r5 = r3.getDrawY()
            int r6 = r3.getWidth()
            int r6 = r6 + r4
            int r7 = r3.getHeight()
            int r7 = r7 + r5
            float r4 = (float) r4
            int r4 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r4 < 0) goto L6e
            float r4 = (float) r6
            int r4 = (r9 > r4 ? 1 : (r9 == r4 ? 0 : -1))
            if (r4 > 0) goto L6e
            float r4 = (float) r5
            int r4 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r4 < 0) goto L6e
            float r4 = (float) r7
            int r4 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1))
            if (r4 > 0) goto L6e
        L6d:
            r0 = r3
        L6e:
            int r1 = r1 + 1
            goto L30
        L71:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.WidgetContainer.findWidget(float, float):android.support.constraint.solver.widgets.ConstraintWidget");
    }

    public ArrayList<ConstraintWidget> findWidgets(int i, int i2, int i3, int i4) {
        ArrayList<ConstraintWidget> arrayList = new ArrayList<>();
        Rectangle rectangle = new Rectangle();
        rectangle.setBounds(i, i2, i3, i4);
        int size = this.mChildren.size();
        for (int i5 = 0; i5 < size; i5++) {
            ConstraintWidget constraintWidget = this.mChildren.get(i5);
            Rectangle rectangle2 = new Rectangle();
            rectangle2.setBounds(constraintWidget.getDrawX(), constraintWidget.getDrawY(), constraintWidget.getWidth(), constraintWidget.getHeight());
            if (rectangle.intersects(rectangle2)) {
                arrayList.add(constraintWidget);
            }
        }
        return arrayList;
    }

    public ArrayList<ConstraintWidget> getChildren() {
        return this.mChildren;
    }

    public ConstraintWidgetContainer getRootConstraintContainer() {
        ConstraintWidget parent = getParent();
        ConstraintWidgetContainer constraintWidgetContainer = this instanceof ConstraintWidgetContainer ? (ConstraintWidgetContainer) this : null;
        while (parent != null) {
            ConstraintWidget parent2 = parent.getParent();
            if (parent instanceof ConstraintWidgetContainer) {
                constraintWidgetContainer = (ConstraintWidgetContainer) parent;
            }
            parent = parent2;
        }
        return constraintWidgetContainer;
    }

    public void layout() {
        updateDrawPosition();
        if (this.mChildren == null) {
            return;
        }
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = this.mChildren.get(i);
            if (constraintWidget instanceof WidgetContainer) {
                ((WidgetContainer) constraintWidget).layout();
            }
        }
    }

    public void remove(ConstraintWidget constraintWidget) {
        this.mChildren.remove(constraintWidget);
        constraintWidget.setParent(null);
    }

    public void removeAllChildren() {
        this.mChildren.clear();
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void reset() {
        this.mChildren.clear();
        super.reset();
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void resetSolverVariables(Cache cache) {
        super.resetSolverVariables(cache);
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            this.mChildren.get(i).resetSolverVariables(cache);
        }
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void setOffset(int i, int i2) {
        super.setOffset(i, i2);
        int size = this.mChildren.size();
        for (int i3 = 0; i3 < size; i3++) {
            this.mChildren.get(i3).setOffset(getRootX(), getRootY());
        }
    }

    @Override // android.support.constraint.solver.widgets.ConstraintWidget
    public void updateDrawPosition() {
        super.updateDrawPosition();
        if (this.mChildren == null) {
            return;
        }
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = this.mChildren.get(i);
            constraintWidget.setOffset(getDrawX(), getDrawY());
            if (!(constraintWidget instanceof ConstraintWidgetContainer)) {
                constraintWidget.updateDrawPosition();
            }
        }
    }
}
