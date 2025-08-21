package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiresApi(19)
@TargetApi(19)
/* loaded from: classes.dex */
class TransitionKitKat extends TransitionImpl {
    private CompatListener mCompatListener;
    TransitionInterface mExternalTransition;
    android.transition.Transition mTransition;

    private class CompatListener implements Transition.TransitionListener {
        private final ArrayList<TransitionInterfaceListener> mListeners = new ArrayList<>();

        CompatListener() {
        }

        void addListener(TransitionInterfaceListener transitionInterfaceListener) {
            this.mListeners.add(transitionInterfaceListener);
        }

        boolean isEmpty() {
            return this.mListeners.isEmpty();
        }

        @Override // android.transition.Transition.TransitionListener
        public void onTransitionCancel(android.transition.Transition transition) {
            Iterator<TransitionInterfaceListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onTransitionCancel(TransitionKitKat.this.mExternalTransition);
            }
        }

        @Override // android.transition.Transition.TransitionListener
        public void onTransitionEnd(android.transition.Transition transition) {
            Iterator<TransitionInterfaceListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onTransitionEnd(TransitionKitKat.this.mExternalTransition);
            }
        }

        @Override // android.transition.Transition.TransitionListener
        public void onTransitionPause(android.transition.Transition transition) {
            Iterator<TransitionInterfaceListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onTransitionPause(TransitionKitKat.this.mExternalTransition);
            }
        }

        @Override // android.transition.Transition.TransitionListener
        public void onTransitionResume(android.transition.Transition transition) {
            Iterator<TransitionInterfaceListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onTransitionResume(TransitionKitKat.this.mExternalTransition);
            }
        }

        @Override // android.transition.Transition.TransitionListener
        public void onTransitionStart(android.transition.Transition transition) {
            Iterator<TransitionInterfaceListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onTransitionStart(TransitionKitKat.this.mExternalTransition);
            }
        }

        void removeListener(TransitionInterfaceListener transitionInterfaceListener) {
            this.mListeners.remove(transitionInterfaceListener);
        }
    }

    private static class TransitionWrapper extends android.transition.Transition {
        private TransitionInterface mTransition;

        public TransitionWrapper(TransitionInterface transitionInterface) {
            this.mTransition = transitionInterface;
        }

        @Override // android.transition.Transition
        public void captureEndValues(android.transition.TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureEndValues(this.mTransition, transitionValues);
        }

        @Override // android.transition.Transition
        public void captureStartValues(android.transition.TransitionValues transitionValues) {
            TransitionKitKat.wrapCaptureStartValues(this.mTransition, transitionValues);
        }

        @Override // android.transition.Transition
        public Animator createAnimator(ViewGroup viewGroup, android.transition.TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
            return this.mTransition.createAnimator(viewGroup, TransitionKitKat.convertToSupport(transitionValues), TransitionKitKat.convertToSupport(transitionValues2));
        }
    }

    TransitionKitKat() {
    }

    static android.transition.TransitionValues convertToPlatform(TransitionValues transitionValues) {
        if (transitionValues == null) {
            return null;
        }
        android.transition.TransitionValues transitionValues2 = new android.transition.TransitionValues();
        copyValues(transitionValues, transitionValues2);
        return transitionValues2;
    }

    static TransitionValues convertToSupport(android.transition.TransitionValues transitionValues) {
        if (transitionValues == null) {
            return null;
        }
        TransitionValues transitionValues2 = new TransitionValues();
        copyValues(transitionValues, transitionValues2);
        return transitionValues2;
    }

    static void copyValues(TransitionValues transitionValues, android.transition.TransitionValues transitionValues2) {
        if (transitionValues == null) {
            return;
        }
        transitionValues2.view = transitionValues.view;
        if (transitionValues.values.size() > 0) {
            transitionValues2.values.putAll(transitionValues.values);
        }
    }

    static void copyValues(android.transition.TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null) {
            return;
        }
        transitionValues2.view = transitionValues.view;
        if (transitionValues.values.size() > 0) {
            transitionValues2.values.putAll(transitionValues.values);
        }
    }

    static void wrapCaptureEndValues(TransitionInterface transitionInterface, android.transition.TransitionValues transitionValues) {
        TransitionValues transitionValues2 = new TransitionValues();
        copyValues(transitionValues, transitionValues2);
        transitionInterface.captureEndValues(transitionValues2);
        copyValues(transitionValues2, transitionValues);
    }

    static void wrapCaptureStartValues(TransitionInterface transitionInterface, android.transition.TransitionValues transitionValues) {
        TransitionValues transitionValues2 = new TransitionValues();
        copyValues(transitionValues, transitionValues2);
        transitionInterface.captureStartValues(transitionValues2);
        copyValues(transitionValues2, transitionValues);
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl addListener(TransitionInterfaceListener transitionInterfaceListener) {
        if (this.mCompatListener == null) {
            this.mCompatListener = new CompatListener();
            this.mTransition.addListener(this.mCompatListener);
        }
        this.mCompatListener.addListener(transitionInterfaceListener);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl addTarget(int i) {
        this.mTransition.addTarget(i);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl addTarget(View view) {
        this.mTransition.addTarget(view);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public void captureEndValues(TransitionValues transitionValues) {
        android.transition.TransitionValues transitionValues2 = new android.transition.TransitionValues();
        copyValues(transitionValues, transitionValues2);
        this.mTransition.captureEndValues(transitionValues2);
        copyValues(transitionValues2, transitionValues);
    }

    @Override // android.support.transition.TransitionImpl
    public void captureStartValues(TransitionValues transitionValues) {
        android.transition.TransitionValues transitionValues2 = new android.transition.TransitionValues();
        copyValues(transitionValues, transitionValues2);
        this.mTransition.captureStartValues(transitionValues2);
        copyValues(transitionValues2, transitionValues);
    }

    @Override // android.support.transition.TransitionImpl
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        android.transition.TransitionValues transitionValues3;
        android.transition.TransitionValues transitionValues4 = null;
        if (transitionValues != null) {
            transitionValues3 = new android.transition.TransitionValues();
            copyValues(transitionValues, transitionValues3);
        } else {
            transitionValues3 = null;
        }
        if (transitionValues2 != null) {
            transitionValues4 = new android.transition.TransitionValues();
            copyValues(transitionValues2, transitionValues4);
        }
        return this.mTransition.createAnimator(viewGroup, transitionValues3, transitionValues4);
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl excludeChildren(int i, boolean z) {
        this.mTransition.excludeChildren(i, z);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl excludeChildren(View view, boolean z) {
        this.mTransition.excludeChildren(view, z);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl excludeChildren(Class cls, boolean z) {
        this.mTransition.excludeChildren(cls, z);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl excludeTarget(int i, boolean z) {
        this.mTransition.excludeTarget(i, z);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl excludeTarget(View view, boolean z) {
        this.mTransition.excludeTarget(view, z);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl excludeTarget(Class cls, boolean z) {
        this.mTransition.excludeTarget(cls, z);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public long getDuration() {
        return this.mTransition.getDuration();
    }

    @Override // android.support.transition.TransitionImpl
    public TimeInterpolator getInterpolator() {
        return this.mTransition.getInterpolator();
    }

    @Override // android.support.transition.TransitionImpl
    public String getName() {
        return this.mTransition.getName();
    }

    @Override // android.support.transition.TransitionImpl
    public long getStartDelay() {
        return this.mTransition.getStartDelay();
    }

    @Override // android.support.transition.TransitionImpl
    public List<Integer> getTargetIds() {
        return this.mTransition.getTargetIds();
    }

    @Override // android.support.transition.TransitionImpl
    public List<View> getTargets() {
        return this.mTransition.getTargets();
    }

    @Override // android.support.transition.TransitionImpl
    public String[] getTransitionProperties() {
        return this.mTransition.getTransitionProperties();
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionValues getTransitionValues(View view, boolean z) {
        TransitionValues transitionValues = new TransitionValues();
        copyValues(this.mTransition.getTransitionValues(view, z), transitionValues);
        return transitionValues;
    }

    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    @Override // android.support.transition.TransitionImpl
    public void init(TransitionInterface transitionInterface, Object obj) {
        this.mExternalTransition = transitionInterface;
        this.mTransition = obj == null ? new TransitionWrapper(transitionInterface) : (android.transition.Transition) obj;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl removeListener(TransitionInterfaceListener transitionInterfaceListener) {
        if (this.mCompatListener == null) {
            return this;
        }
        this.mCompatListener.removeListener(transitionInterfaceListener);
        if (this.mCompatListener.isEmpty()) {
            this.mTransition.removeListener(this.mCompatListener);
            this.mCompatListener = null;
        }
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl removeTarget(int i) {
        if (i > 0) {
            getTargetIds().remove(Integer.valueOf(i));
        }
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl removeTarget(View view) {
        this.mTransition.removeTarget(view);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl setDuration(long j) {
        this.mTransition.setDuration(j);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl setInterpolator(TimeInterpolator timeInterpolator) {
        this.mTransition.setInterpolator(timeInterpolator);
        return this;
    }

    @Override // android.support.transition.TransitionImpl
    public TransitionImpl setStartDelay(long j) {
        this.mTransition.setStartDelay(j);
        return this;
    }

    public String toString() {
        return this.mTransition.toString();
    }
}
