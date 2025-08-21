package android.support.transition;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
abstract class VisibilityPort extends TransitionPort {
    private static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
    private static final String PROPNAME_PARENT = "android:visibility:parent";
    private static final String[] sTransitionProperties = {PROPNAME_VISIBILITY, PROPNAME_PARENT};

    private static class VisibilityInfo {
        ViewGroup endParent;
        int endVisibility;
        boolean fadeIn;
        ViewGroup startParent;
        int startVisibility;
        boolean visibilityChange;

        VisibilityInfo() {
        }
    }

    VisibilityPort() {
    }

    private void captureValues(TransitionValues transitionValues) {
        transitionValues.values.put(PROPNAME_VISIBILITY, Integer.valueOf(transitionValues.view.getVisibility()));
        transitionValues.values.put(PROPNAME_PARENT, transitionValues.view.getParent());
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0074, code lost:
    
        if (r5.endVisibility == 0) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0076, code lost:
    
        r5.fadeIn = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0086, code lost:
    
        if (r5.startParent == null) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private android.support.transition.VisibilityPort.VisibilityInfo getVisibilityChangeInfo(android.support.transition.TransitionValues r6, android.support.transition.TransitionValues r7) {
        /*
            r5 = this;
            android.support.transition.VisibilityPort$VisibilityInfo r5 = new android.support.transition.VisibilityPort$VisibilityInfo
            r5.<init>()
            r0 = 0
            r5.visibilityChange = r0
            r5.fadeIn = r0
            r1 = 0
            r2 = -1
            if (r6 == 0) goto L2b
            java.util.Map<java.lang.String, java.lang.Object> r3 = r6.values
            java.lang.String r4 = "android:visibility:visibility"
            java.lang.Object r3 = r3.get(r4)
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            r5.startVisibility = r3
            java.util.Map<java.lang.String, java.lang.Object> r3 = r6.values
            java.lang.String r4 = "android:visibility:parent"
            java.lang.Object r3 = r3.get(r4)
            android.view.ViewGroup r3 = (android.view.ViewGroup) r3
            r5.startParent = r3
            goto L2f
        L2b:
            r5.startVisibility = r2
            r5.startParent = r1
        L2f:
            if (r7 == 0) goto L4e
            java.util.Map<java.lang.String, java.lang.Object> r1 = r7.values
            java.lang.String r2 = "android:visibility:visibility"
            java.lang.Object r1 = r1.get(r2)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            r5.endVisibility = r1
            java.util.Map<java.lang.String, java.lang.Object> r1 = r7.values
            java.lang.String r2 = "android:visibility:parent"
            java.lang.Object r1 = r1.get(r2)
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
        L4b:
            r5.endParent = r1
            goto L51
        L4e:
            r5.endVisibility = r2
            goto L4b
        L51:
            r1 = 1
            if (r6 == 0) goto L89
            if (r7 == 0) goto L89
            int r2 = r5.startVisibility
            int r3 = r5.endVisibility
            if (r2 != r3) goto L63
            android.view.ViewGroup r2 = r5.startParent
            android.view.ViewGroup r3 = r5.endParent
            if (r2 != r3) goto L63
            return r5
        L63:
            int r2 = r5.startVisibility
            int r3 = r5.endVisibility
            if (r2 == r3) goto L79
            int r2 = r5.startVisibility
            if (r2 != 0) goto L72
        L6d:
            r5.fadeIn = r0
        L6f:
            r5.visibilityChange = r1
            goto L89
        L72:
            int r2 = r5.endVisibility
            if (r2 != 0) goto L89
        L76:
            r5.fadeIn = r1
            goto L6f
        L79:
            android.view.ViewGroup r2 = r5.startParent
            android.view.ViewGroup r3 = r5.endParent
            if (r2 == r3) goto L89
            android.view.ViewGroup r2 = r5.endParent
            if (r2 != 0) goto L84
            goto L6d
        L84:
            android.view.ViewGroup r2 = r5.startParent
            if (r2 != 0) goto L89
            goto L76
        L89:
            if (r6 != 0) goto L90
            r5.fadeIn = r1
        L8d:
            r5.visibilityChange = r1
            return r5
        L90:
            if (r7 != 0) goto L95
            r5.fadeIn = r0
            goto L8d
        L95:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.VisibilityPort.getVisibilityChangeInfo(android.support.transition.TransitionValues, android.support.transition.TransitionValues):android.support.transition.VisibilityPort$VisibilityInfo");
    }

    @Override // android.support.transition.TransitionPort
    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.support.transition.TransitionPort
    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    @Override // android.support.transition.TransitionPort
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityChangeInfo = getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityChangeInfo.visibilityChange) {
            boolean z = false;
            if (this.mTargets.size() > 0 || this.mTargetIds.size() > 0) {
                View view = transitionValues != null ? transitionValues.view : null;
                View view2 = transitionValues2 != null ? transitionValues2.view : null;
                int id = view != null ? view.getId() : -1;
                int id2 = view2 != null ? view2.getId() : -1;
                if (isValidTarget(view, id) || isValidTarget(view2, id2)) {
                    z = true;
                }
            }
            if (z || visibilityChangeInfo.startParent != null || visibilityChangeInfo.endParent != null) {
                return visibilityChangeInfo.fadeIn ? onAppear(viewGroup, transitionValues, visibilityChangeInfo.startVisibility, transitionValues2, visibilityChangeInfo.endVisibility) : onDisappear(viewGroup, transitionValues, visibilityChangeInfo.startVisibility, transitionValues2, visibilityChangeInfo.endVisibility);
            }
        }
        return null;
    }

    @Override // android.support.transition.TransitionPort
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public boolean isVisible(TransitionValues transitionValues) {
        if (transitionValues == null) {
            return false;
        }
        return ((Integer) transitionValues.values.get(PROPNAME_VISIBILITY)).intValue() == 0 && ((View) transitionValues.values.get(PROPNAME_PARENT)) != null;
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return null;
    }

    public Animator onDisappear(ViewGroup viewGroup, TransitionValues transitionValues, int i, TransitionValues transitionValues2, int i2) {
        return null;
    }
}
