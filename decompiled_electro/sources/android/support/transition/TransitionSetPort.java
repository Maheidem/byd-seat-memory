package android.support.transition;

import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.transition.TransitionPort;
import android.util.AndroidRuntimeException;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;

@RequiresApi(14)
@TargetApi(14)
/* loaded from: classes.dex */
class TransitionSetPort extends TransitionPort {
    public static final int ORDERING_SEQUENTIAL = 1;
    public static final int ORDERING_TOGETHER = 0;
    int mCurrentListeners;
    ArrayList<TransitionPort> mTransitions = new ArrayList<>();
    boolean mStarted = false;
    private boolean mPlayTogether = true;

    static class TransitionSetListener extends TransitionPort.TransitionListenerAdapter {
        TransitionSetPort mTransitionSet;

        TransitionSetListener(TransitionSetPort transitionSetPort) {
            this.mTransitionSet = transitionSetPort;
        }

        @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
        public void onTransitionEnd(TransitionPort transitionPort) {
            TransitionSetPort transitionSetPort = this.mTransitionSet;
            transitionSetPort.mCurrentListeners--;
            if (this.mTransitionSet.mCurrentListeners == 0) {
                this.mTransitionSet.mStarted = false;
                this.mTransitionSet.end();
            }
            transitionPort.removeListener(this);
        }

        @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
        public void onTransitionStart(TransitionPort transitionPort) {
            if (this.mTransitionSet.mStarted) {
                return;
            }
            this.mTransitionSet.start();
            this.mTransitionSet.mStarted = true;
        }
    }

    private void setupStartEndListeners() {
        TransitionSetListener transitionSetListener = new TransitionSetListener(this);
        Iterator<TransitionPort> it = this.mTransitions.iterator();
        while (it.hasNext()) {
            it.next().addListener(transitionSetListener);
        }
        this.mCurrentListeners = this.mTransitions.size();
    }

    @Override // android.support.transition.TransitionPort
    public TransitionSetPort addListener(TransitionPort.TransitionListener transitionListener) {
        return (TransitionSetPort) super.addListener(transitionListener);
    }

    @Override // android.support.transition.TransitionPort
    public TransitionSetPort addTarget(int i) {
        return (TransitionSetPort) super.addTarget(i);
    }

    @Override // android.support.transition.TransitionPort
    public TransitionSetPort addTarget(View view) {
        return (TransitionSetPort) super.addTarget(view);
    }

    public TransitionSetPort addTransition(TransitionPort transitionPort) {
        if (transitionPort != null) {
            this.mTransitions.add(transitionPort);
            transitionPort.mParent = this;
            if (this.mDuration >= 0) {
                transitionPort.setDuration(this.mDuration);
            }
        }
        return this;
    }

    @Override // android.support.transition.TransitionPort
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected void cancel() {
        super.cancel();
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).cancel();
        }
    }

    @Override // android.support.transition.TransitionPort
    public void captureEndValues(TransitionValues transitionValues) {
        long id = transitionValues.view.getId();
        if (isValidTarget(transitionValues.view, id)) {
            Iterator<TransitionPort> it = this.mTransitions.iterator();
            while (it.hasNext()) {
                TransitionPort next = it.next();
                if (next.isValidTarget(transitionValues.view, id)) {
                    next.captureEndValues(transitionValues);
                }
            }
        }
    }

    @Override // android.support.transition.TransitionPort
    public void captureStartValues(TransitionValues transitionValues) {
        long id = transitionValues.view.getId();
        if (isValidTarget(transitionValues.view, id)) {
            Iterator<TransitionPort> it = this.mTransitions.iterator();
            while (it.hasNext()) {
                TransitionPort next = it.next();
                if (next.isValidTarget(transitionValues.view, id)) {
                    next.captureStartValues(transitionValues);
                }
            }
        }
    }

    @Override // android.support.transition.TransitionPort
    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public TransitionSetPort mo1clone() {
        TransitionSetPort transitionSetPort = (TransitionSetPort) super.mo1clone();
        transitionSetPort.mTransitions = new ArrayList<>();
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            transitionSetPort.addTransition(this.mTransitions.get(i).mo1clone());
        }
        return transitionSetPort;
    }

    @Override // android.support.transition.TransitionPort
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected void createAnimators(ViewGroup viewGroup, TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2) {
        Iterator<TransitionPort> it = this.mTransitions.iterator();
        while (it.hasNext()) {
            it.next().createAnimators(viewGroup, transitionValuesMaps, transitionValuesMaps2);
        }
    }

    public int getOrdering() {
        return !this.mPlayTogether ? 1 : 0;
    }

    @Override // android.support.transition.TransitionPort
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void pause(View view) {
        super.pause(view);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).pause(view);
        }
    }

    @Override // android.support.transition.TransitionPort
    public TransitionSetPort removeListener(TransitionPort.TransitionListener transitionListener) {
        return (TransitionSetPort) super.removeListener(transitionListener);
    }

    @Override // android.support.transition.TransitionPort
    public TransitionSetPort removeTarget(int i) {
        return (TransitionSetPort) super.removeTarget(i);
    }

    @Override // android.support.transition.TransitionPort
    public TransitionSetPort removeTarget(View view) {
        return (TransitionSetPort) super.removeTarget(view);
    }

    public TransitionSetPort removeTransition(TransitionPort transitionPort) {
        this.mTransitions.remove(transitionPort);
        transitionPort.mParent = null;
        return this;
    }

    @Override // android.support.transition.TransitionPort
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void resume(View view) {
        super.resume(view);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).resume(view);
        }
    }

    @Override // android.support.transition.TransitionPort
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected void runAnimators() {
        if (this.mTransitions.isEmpty()) {
            start();
            end();
            return;
        }
        setupStartEndListeners();
        if (this.mPlayTogether) {
            Iterator<TransitionPort> it = this.mTransitions.iterator();
            while (it.hasNext()) {
                it.next().runAnimators();
            }
            return;
        }
        for (int i = 1; i < this.mTransitions.size(); i++) {
            TransitionPort transitionPort = this.mTransitions.get(i - 1);
            final TransitionPort transitionPort2 = this.mTransitions.get(i);
            transitionPort.addListener(new TransitionPort.TransitionListenerAdapter() { // from class: android.support.transition.TransitionSetPort.1
                @Override // android.support.transition.TransitionPort.TransitionListenerAdapter, android.support.transition.TransitionPort.TransitionListener
                public void onTransitionEnd(TransitionPort transitionPort3) {
                    transitionPort2.runAnimators();
                    transitionPort3.removeListener(this);
                }
            });
        }
        TransitionPort transitionPort3 = this.mTransitions.get(0);
        if (transitionPort3 != null) {
            transitionPort3.runAnimators();
        }
    }

    @Override // android.support.transition.TransitionPort
    void setCanRemoveViews(boolean z) {
        super.setCanRemoveViews(z);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).setCanRemoveViews(z);
        }
    }

    @Override // android.support.transition.TransitionPort
    public TransitionSetPort setDuration(long j) {
        super.setDuration(j);
        if (this.mDuration >= 0) {
            int size = this.mTransitions.size();
            for (int i = 0; i < size; i++) {
                this.mTransitions.get(i).setDuration(j);
            }
        }
        return this;
    }

    @Override // android.support.transition.TransitionPort
    public TransitionSetPort setInterpolator(TimeInterpolator timeInterpolator) {
        return (TransitionSetPort) super.setInterpolator(timeInterpolator);
    }

    public TransitionSetPort setOrdering(int i) {
        boolean z;
        switch (i) {
            case 0:
                z = true;
                break;
            case 1:
                z = false;
                break;
            default:
                throw new AndroidRuntimeException("Invalid parameter for TransitionSet ordering: " + i);
        }
        this.mPlayTogether = z;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.support.transition.TransitionPort
    public TransitionSetPort setSceneRoot(ViewGroup viewGroup) {
        super.setSceneRoot(viewGroup);
        int size = this.mTransitions.size();
        for (int i = 0; i < size; i++) {
            this.mTransitions.get(i).setSceneRoot(viewGroup);
        }
        return this;
    }

    @Override // android.support.transition.TransitionPort
    public TransitionSetPort setStartDelay(long j) {
        return (TransitionSetPort) super.setStartDelay(j);
    }

    @Override // android.support.transition.TransitionPort
    String toString(String str) {
        String string = super.toString(str);
        for (int i = 0; i < this.mTransitions.size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(string);
            sb.append("\n");
            sb.append(this.mTransitions.get(i).toString(str + "  "));
            string = sb.toString();
        }
        return string;
    }
}
