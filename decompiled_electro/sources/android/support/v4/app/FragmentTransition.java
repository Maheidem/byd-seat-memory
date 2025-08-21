package android.support.v4.app;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import java.util.ArrayList;

/* loaded from: classes.dex */
class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6};

    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }

    FragmentTransition() {
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0073  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void addToFirstInLastOut(android.support.v4.app.BackStackRecord r16, android.support.v4.app.BackStackRecord.Op r17, android.util.SparseArray<android.support.v4.app.FragmentTransition.FragmentContainerTransition> r18, boolean r19, boolean r20) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 240
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentTransition.addToFirstInLastOut(android.support.v4.app.BackStackRecord, android.support.v4.app.BackStackRecord$Op, android.util.SparseArray, boolean, boolean):void");
    }

    public static void calculateFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean z) throws Resources.NotFoundException {
        int size = backStackRecord.mOps.size();
        for (int i = 0; i < size; i++) {
            addToFirstInLastOut(backStackRecord, backStackRecord.mOps.get(i), sparseArray, false, z);
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int i, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i2, int i3) {
        ArrayList<String> arrayList3;
        ArrayList<String> arrayList4;
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        for (int i4 = i3 - 1; i4 >= i2; i4--) {
            BackStackRecord backStackRecord = arrayList.get(i4);
            if (backStackRecord.interactsWith(i)) {
                boolean zBooleanValue = arrayList2.get(i4).booleanValue();
                if (backStackRecord.mSharedElementSourceNames != null) {
                    int size = backStackRecord.mSharedElementSourceNames.size();
                    if (zBooleanValue) {
                        arrayList3 = backStackRecord.mSharedElementSourceNames;
                        arrayList4 = backStackRecord.mSharedElementTargetNames;
                    } else {
                        ArrayList<String> arrayList5 = backStackRecord.mSharedElementSourceNames;
                        arrayList3 = backStackRecord.mSharedElementTargetNames;
                        arrayList4 = arrayList5;
                    }
                    for (int i5 = 0; i5 < size; i5++) {
                        String str = arrayList4.get(i5);
                        String str2 = arrayList3.get(i5);
                        String strRemove = arrayMap.remove(str2);
                        if (strRemove != null) {
                            arrayMap.put(str, strRemove);
                        } else {
                            arrayMap.put(str, str2);
                        }
                    }
                }
            }
        }
        return arrayMap;
    }

    public static void calculatePopFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean z) throws Resources.NotFoundException {
        if (backStackRecord.mManager.mContainer.onHasView()) {
            for (int size = backStackRecord.mOps.size() - 1; size >= 0; size--) {
                addToFirstInLastOut(backStackRecord, backStackRecord.mOps.get(size), sparseArray, true, z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void callSharedElementStartEnd(Fragment fragment, Fragment fragment2, boolean z, ArrayMap<String, View> arrayMap, boolean z2) {
        SharedElementCallback enterTransitionCallback = z ? fragment2.getEnterTransitionCallback() : fragment.getEnterTransitionCallback();
        if (enterTransitionCallback != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            int size = arrayMap == null ? 0 : arrayMap.size();
            for (int i = 0; i < size; i++) {
                arrayList2.add(arrayMap.keyAt(i));
                arrayList.add(arrayMap.valueAt(i));
            }
            if (z2) {
                enterTransitionCallback.onSharedElementStart(arrayList2, arrayList, null);
            } else {
                enterTransitionCallback.onSharedElementEnd(arrayList2, arrayList, null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ArrayMap<String, View> captureInSharedElements(ArrayMap<String, String> arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback enterTransitionCallback;
        ArrayList<String> arrayList;
        String strFindKeyForValue;
        Fragment fragment = fragmentContainerTransition.lastIn;
        View view = fragment.getView();
        if (arrayMap.isEmpty() || obj == null || view == null) {
            arrayMap.clear();
            return null;
        }
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        FragmentTransitionCompat21.findNamedViews(arrayMap2, view);
        BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction;
        if (fragmentContainerTransition.lastInIsPop) {
            enterTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = backStackRecord.mSharedElementSourceNames;
        } else {
            enterTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = backStackRecord.mSharedElementTargetNames;
        }
        arrayMap2.retainAll(arrayList);
        if (enterTransitionCallback != null) {
            enterTransitionCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view2 = arrayMap2.get(str);
                if (view2 == null) {
                    String strFindKeyForValue2 = findKeyForValue(arrayMap, str);
                    if (strFindKeyForValue2 != null) {
                        arrayMap.remove(strFindKeyForValue2);
                    }
                } else if (!str.equals(ViewCompat.getTransitionName(view2)) && (strFindKeyForValue = findKeyForValue(arrayMap, str)) != null) {
                    arrayMap.put(strFindKeyForValue, ViewCompat.getTransitionName(view2));
                }
            }
        } else {
            retainValues(arrayMap, arrayMap2);
        }
        return arrayMap2;
    }

    private static ArrayMap<String, View> captureOutSharedElements(ArrayMap<String, String> arrayMap, Object obj, FragmentContainerTransition fragmentContainerTransition) {
        SharedElementCallback exitTransitionCallback;
        ArrayList<String> arrayList;
        if (arrayMap.isEmpty() || obj == null) {
            arrayMap.clear();
            return null;
        }
        Fragment fragment = fragmentContainerTransition.firstOut;
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        FragmentTransitionCompat21.findNamedViews(arrayMap2, fragment.getView());
        BackStackRecord backStackRecord = fragmentContainerTransition.firstOutTransaction;
        if (fragmentContainerTransition.firstOutIsPop) {
            exitTransitionCallback = fragment.getEnterTransitionCallback();
            arrayList = backStackRecord.mSharedElementTargetNames;
        } else {
            exitTransitionCallback = fragment.getExitTransitionCallback();
            arrayList = backStackRecord.mSharedElementSourceNames;
        }
        arrayMap2.retainAll(arrayList);
        if (exitTransitionCallback != null) {
            exitTransitionCallback.onMapSharedElements(arrayList, arrayMap2);
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                String str = arrayList.get(size);
                View view = arrayMap2.get(str);
                if (view == null) {
                    arrayMap.remove(str);
                } else if (!str.equals(ViewCompat.getTransitionName(view))) {
                    arrayMap.put(ViewCompat.getTransitionName(view), arrayMap.remove(str));
                }
            }
        } else {
            arrayMap.retainAll(arrayMap2.keySet());
        }
        return arrayMap2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ArrayList<View> configureEnteringExitingViews(Object obj, Fragment fragment, ArrayList<View> arrayList, View view) {
        if (obj == null) {
            return null;
        }
        ArrayList<View> arrayList2 = new ArrayList<>();
        FragmentTransitionCompat21.captureTransitioningViews(arrayList2, fragment.getView());
        if (arrayList != null) {
            arrayList2.removeAll(arrayList);
        }
        if (arrayList2.isEmpty()) {
            return arrayList2;
        }
        arrayList2.add(view);
        FragmentTransitionCompat21.addTargets(obj, arrayList2);
        return arrayList2;
    }

    private static Object configureSharedElementsOptimized(final ViewGroup viewGroup, View view, ArrayMap<String, String> arrayMap, FragmentContainerTransition fragmentContainerTransition, ArrayList<View> arrayList, ArrayList<View> arrayList2, Object obj, Object obj2) {
        Object obj3;
        View inEpicenterView;
        final Rect rect;
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment != null) {
            fragment.getView().setVisibility(0);
        }
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = fragmentContainerTransition.lastInIsPop;
        Object sharedElementTransition = arrayMap.isEmpty() ? null : getSharedElementTransition(fragment, fragment2, z);
        ArrayMap<String, View> arrayMapCaptureOutSharedElements = captureOutSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
        final ArrayMap<String, View> arrayMapCaptureInSharedElements = captureInSharedElements(arrayMap, sharedElementTransition, fragmentContainerTransition);
        if (arrayMap.isEmpty()) {
            obj3 = null;
        } else {
            arrayList.addAll(arrayMapCaptureOutSharedElements.values());
            arrayList2.addAll(arrayMapCaptureInSharedElements.values());
            obj3 = sharedElementTransition;
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null;
        }
        callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureOutSharedElements, true);
        if (obj3 != null) {
            arrayList2.add(view);
            FragmentTransitionCompat21.setSharedElementTargets(obj3, view, arrayList);
            setOutEpicenter(obj3, obj2, arrayMapCaptureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            Rect rect2 = new Rect();
            inEpicenterView = getInEpicenterView(arrayMapCaptureInSharedElements, fragmentContainerTransition, obj, z);
            if (inEpicenterView != null) {
                FragmentTransitionCompat21.setEpicenter(obj, rect2);
            }
            rect = rect2;
        } else {
            inEpicenterView = null;
            rect = null;
        }
        final View view2 = inEpicenterView;
        viewGroup.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: android.support.v4.app.FragmentTransition.3
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                viewGroup.getViewTreeObserver().removeOnPreDrawListener(this);
                FragmentTransition.callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureInSharedElements, false);
                if (view2 == null) {
                    return true;
                }
                FragmentTransitionCompat21.getBoundsOnScreen(view2, rect);
                return true;
            }
        });
        return obj3;
    }

    private static Object configureSharedElementsUnoptimized(final ViewGroup viewGroup, View view, ArrayMap<String, String> arrayMap, final FragmentContainerTransition fragmentContainerTransition, final ArrayList<View> arrayList, final ArrayList<View> arrayList2, final Object obj, Object obj2) {
        Object sharedElementTransition;
        final ArrayMap<String, String> arrayMap2;
        Object obj3;
        final View view2;
        final Fragment fragment = fragmentContainerTransition.lastIn;
        final Fragment fragment2 = fragmentContainerTransition.firstOut;
        Rect rect = null;
        if (fragment == null || fragment2 == null) {
            return null;
        }
        final boolean z = fragmentContainerTransition.lastInIsPop;
        if (arrayMap.isEmpty()) {
            arrayMap2 = arrayMap;
            sharedElementTransition = null;
        } else {
            sharedElementTransition = getSharedElementTransition(fragment, fragment2, z);
            arrayMap2 = arrayMap;
        }
        ArrayMap<String, View> arrayMapCaptureOutSharedElements = captureOutSharedElements(arrayMap2, sharedElementTransition, fragmentContainerTransition);
        if (arrayMap.isEmpty()) {
            obj3 = null;
        } else {
            arrayList.addAll(arrayMapCaptureOutSharedElements.values());
            obj3 = sharedElementTransition;
        }
        if (obj == null && obj2 == null && obj3 == null) {
            return null;
        }
        callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureOutSharedElements, true);
        if (obj3 != null) {
            rect = new Rect();
            view2 = view;
            FragmentTransitionCompat21.setSharedElementTargets(obj3, view2, arrayList);
            setOutEpicenter(obj3, obj2, arrayMapCaptureOutSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
            if (obj != null) {
                FragmentTransitionCompat21.setEpicenter(obj, rect);
            }
        } else {
            view2 = view;
        }
        final Rect rect2 = rect;
        final Object obj4 = obj3;
        viewGroup.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: android.support.v4.app.FragmentTransition.4
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                viewGroup.getViewTreeObserver().removeOnPreDrawListener(this);
                ArrayMap arrayMapCaptureInSharedElements = FragmentTransition.captureInSharedElements(arrayMap2, obj4, fragmentContainerTransition);
                if (arrayMapCaptureInSharedElements != null) {
                    arrayList2.addAll(arrayMapCaptureInSharedElements.values());
                    arrayList2.add(view2);
                }
                FragmentTransition.callSharedElementStartEnd(fragment, fragment2, z, arrayMapCaptureInSharedElements, false);
                if (obj4 == null) {
                    return true;
                }
                FragmentTransitionCompat21.swapSharedElementTargets(obj4, arrayList, arrayList2);
                View inEpicenterView = FragmentTransition.getInEpicenterView(arrayMapCaptureInSharedElements, fragmentContainerTransition, obj, z);
                if (inEpicenterView == null) {
                    return true;
                }
                FragmentTransitionCompat21.getBoundsOnScreen(inEpicenterView, rect2);
                return true;
            }
        });
        return obj3;
    }

    private static void configureTransitionsOptimized(FragmentManagerImpl fragmentManagerImpl, int i, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap<String, String> arrayMap) {
        Object obj;
        ViewGroup viewGroup = (ViewGroup) fragmentManagerImpl.mContainer.onFindViewById(i);
        if (viewGroup == null) {
            return;
        }
        Fragment fragment = fragmentContainerTransition.lastIn;
        Fragment fragment2 = fragmentContainerTransition.firstOut;
        boolean z = fragmentContainerTransition.lastInIsPop;
        boolean z2 = fragmentContainerTransition.firstOutIsPop;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Object enterTransition = getEnterTransition(fragment, z);
        Object exitTransition = getExitTransition(fragment2, z2);
        Object objConfigureSharedElementsOptimized = configureSharedElementsOptimized(viewGroup, view, arrayMap, fragmentContainerTransition, arrayList2, arrayList, enterTransition, exitTransition);
        if (enterTransition == null && objConfigureSharedElementsOptimized == null) {
            obj = exitTransition;
            if (obj == null) {
                return;
            }
        } else {
            obj = exitTransition;
        }
        ArrayList<View> arrayListConfigureEnteringExitingViews = configureEnteringExitingViews(obj, fragment2, arrayList2, view);
        ArrayList<View> arrayListConfigureEnteringExitingViews2 = configureEnteringExitingViews(enterTransition, fragment, arrayList, view);
        setViewVisibility(arrayListConfigureEnteringExitingViews2, 4);
        Object objMergeTransitions = mergeTransitions(enterTransition, obj, objConfigureSharedElementsOptimized, fragment, z);
        if (objMergeTransitions != null) {
            replaceHide(obj, fragment2, arrayListConfigureEnteringExitingViews);
            ArrayList<String> arrayListPrepareSetNameOverridesOptimized = FragmentTransitionCompat21.prepareSetNameOverridesOptimized(arrayList);
            FragmentTransitionCompat21.scheduleRemoveTargets(objMergeTransitions, enterTransition, arrayListConfigureEnteringExitingViews2, obj, arrayListConfigureEnteringExitingViews, objConfigureSharedElementsOptimized, arrayList);
            FragmentTransitionCompat21.beginDelayedTransition(viewGroup, objMergeTransitions);
            FragmentTransitionCompat21.setNameOverridesOptimized(viewGroup, arrayList2, arrayList, arrayListPrepareSetNameOverridesOptimized, arrayMap);
            setViewVisibility(arrayListConfigureEnteringExitingViews2, 0);
            FragmentTransitionCompat21.swapSharedElementTargets(objConfigureSharedElementsOptimized, arrayList2, arrayList);
        }
    }

    private static void configureTransitionsUnoptimized(FragmentManagerImpl fragmentManagerImpl, int i, FragmentContainerTransition fragmentContainerTransition, View view, ArrayMap<String, String> arrayMap) {
        ViewGroup viewGroup = (ViewGroup) fragmentManagerImpl.mContainer.onFindViewById(i);
        if (viewGroup == null) {
            return;
        }
        Fragment fragment = fragmentContainerTransition.lastIn;
        Fragment fragment2 = fragmentContainerTransition.firstOut;
        boolean z = fragmentContainerTransition.lastInIsPop;
        boolean z2 = fragmentContainerTransition.firstOutIsPop;
        Object enterTransition = getEnterTransition(fragment, z);
        Object exitTransition = getExitTransition(fragment2, z2);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Object objConfigureSharedElementsUnoptimized = configureSharedElementsUnoptimized(viewGroup, view, arrayMap, fragmentContainerTransition, arrayList, arrayList2, enterTransition, exitTransition);
        if (enterTransition == null && objConfigureSharedElementsUnoptimized == null && exitTransition == null) {
            return;
        }
        ArrayList<View> arrayListConfigureEnteringExitingViews = configureEnteringExitingViews(exitTransition, fragment2, arrayList, view);
        if (arrayListConfigureEnteringExitingViews == null || arrayListConfigureEnteringExitingViews.isEmpty()) {
            exitTransition = null;
        }
        FragmentTransitionCompat21.addTarget(enterTransition, view);
        Object objMergeTransitions = mergeTransitions(enterTransition, exitTransition, objConfigureSharedElementsUnoptimized, fragment, fragmentContainerTransition.lastInIsPop);
        if (objMergeTransitions != null) {
            ArrayList arrayList3 = new ArrayList();
            FragmentTransitionCompat21.scheduleRemoveTargets(objMergeTransitions, enterTransition, arrayList3, exitTransition, arrayListConfigureEnteringExitingViews, objConfigureSharedElementsUnoptimized, arrayList2);
            scheduleTargetChange(viewGroup, fragment, view, arrayList2, enterTransition, arrayList3, exitTransition, arrayListConfigureEnteringExitingViews);
            FragmentTransitionCompat21.setNameOverridesUnoptimized(viewGroup, arrayList2, arrayMap);
            FragmentTransitionCompat21.beginDelayedTransition(viewGroup, objMergeTransitions);
            FragmentTransitionCompat21.scheduleNameReset(viewGroup, arrayList2, arrayMap);
        }
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition fragmentContainerTransition, SparseArray<FragmentContainerTransition> sparseArray, int i) {
        if (fragmentContainerTransition != null) {
            return fragmentContainerTransition;
        }
        FragmentContainerTransition fragmentContainerTransition2 = new FragmentContainerTransition();
        sparseArray.put(i, fragmentContainerTransition2);
        return fragmentContainerTransition2;
    }

    private static String findKeyForValue(ArrayMap<String, String> arrayMap, String str) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            if (str.equals(arrayMap.valueAt(i))) {
                return arrayMap.keyAt(i);
            }
        }
        return null;
    }

    private static Object getEnterTransition(Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return FragmentTransitionCompat21.cloneTransition(z ? fragment.getReenterTransition() : fragment.getEnterTransition());
    }

    private static Object getExitTransition(Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return FragmentTransitionCompat21.cloneTransition(z ? fragment.getReturnTransition() : fragment.getExitTransition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static View getInEpicenterView(ArrayMap<String, View> arrayMap, FragmentContainerTransition fragmentContainerTransition, Object obj, boolean z) {
        BackStackRecord backStackRecord = fragmentContainerTransition.lastInTransaction;
        if (obj == null || backStackRecord.mSharedElementSourceNames == null || backStackRecord.mSharedElementSourceNames.isEmpty()) {
            return null;
        }
        return arrayMap.get((z ? backStackRecord.mSharedElementSourceNames : backStackRecord.mSharedElementTargetNames).get(0));
    }

    private static Object getSharedElementTransition(Fragment fragment, Fragment fragment2, boolean z) {
        if (fragment == null || fragment2 == null) {
            return null;
        }
        return FragmentTransitionCompat21.wrapTransitionInSet(FragmentTransitionCompat21.cloneTransition(z ? fragment2.getSharedElementReturnTransition() : fragment.getSharedElementEnterTransition()));
    }

    private static Object mergeTransitions(Object obj, Object obj2, Object obj3, Fragment fragment, boolean z) {
        return (obj == null || obj2 == null || fragment == null) ? true : z ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap() ? FragmentTransitionCompat21.mergeTransitionsTogether(obj2, obj, obj3) : FragmentTransitionCompat21.mergeTransitionsInSequence(obj2, obj, obj3);
    }

    private static void replaceHide(Object obj, Fragment fragment, final ArrayList<View> arrayList) {
        if (fragment != null && obj != null && fragment.mAdded && fragment.mHidden && fragment.mHiddenChanged) {
            fragment.setHideReplaced(true);
            FragmentTransitionCompat21.scheduleHideFragmentView(obj, fragment.getView(), arrayList);
            final ViewGroup viewGroup = fragment.mContainer;
            viewGroup.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: android.support.v4.app.FragmentTransition.1
                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                public boolean onPreDraw() {
                    viewGroup.getViewTreeObserver().removeOnPreDrawListener(this);
                    FragmentTransition.setViewVisibility(arrayList, 4);
                    return true;
                }
            });
        }
    }

    private static void retainValues(ArrayMap<String, String> arrayMap, ArrayMap<String, View> arrayMap2) {
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            if (!arrayMap2.containsKey(arrayMap.valueAt(size))) {
                arrayMap.removeAt(size);
            }
        }
    }

    private static void scheduleTargetChange(final ViewGroup viewGroup, final Fragment fragment, final View view, final ArrayList<View> arrayList, final Object obj, final ArrayList<View> arrayList2, final Object obj2, final ArrayList<View> arrayList3) {
        viewGroup.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: android.support.v4.app.FragmentTransition.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                viewGroup.getViewTreeObserver().removeOnPreDrawListener(this);
                if (obj != null) {
                    FragmentTransitionCompat21.removeTarget(obj, view);
                    arrayList2.addAll(FragmentTransition.configureEnteringExitingViews(obj, fragment, arrayList, view));
                }
                if (arrayList3 == null) {
                    return true;
                }
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add(view);
                FragmentTransitionCompat21.replaceTargets(obj2, arrayList3, arrayList4);
                arrayList3.clear();
                arrayList3.add(view);
                return true;
            }
        });
    }

    private static void setOutEpicenter(Object obj, Object obj2, ArrayMap<String, View> arrayMap, boolean z, BackStackRecord backStackRecord) {
        if (backStackRecord.mSharedElementSourceNames == null || backStackRecord.mSharedElementSourceNames.isEmpty()) {
            return;
        }
        View view = arrayMap.get((z ? backStackRecord.mSharedElementTargetNames : backStackRecord.mSharedElementSourceNames).get(0));
        FragmentTransitionCompat21.setEpicenter(obj, view);
        if (obj2 != null) {
            FragmentTransitionCompat21.setEpicenter(obj2, view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void setViewVisibility(ArrayList<View> arrayList, int i) {
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            arrayList.get(size).setVisibility(i);
        }
    }

    static void startTransitions(FragmentManagerImpl fragmentManagerImpl, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int i, int i2, boolean z) {
        if (fragmentManagerImpl.mCurState < 1 || Build.VERSION.SDK_INT < 21) {
            return;
        }
        SparseArray sparseArray = new SparseArray();
        for (int i3 = i; i3 < i2; i3++) {
            BackStackRecord backStackRecord = arrayList.get(i3);
            if (arrayList2.get(i3).booleanValue()) {
                calculatePopFragments(backStackRecord, sparseArray, z);
            } else {
                calculateFragments(backStackRecord, sparseArray, z);
            }
        }
        if (sparseArray.size() != 0) {
            View view = new View(fragmentManagerImpl.mHost.getContext());
            int size = sparseArray.size();
            for (int i4 = 0; i4 < size; i4++) {
                int iKeyAt = sparseArray.keyAt(i4);
                ArrayMap<String, String> arrayMapCalculateNameOverrides = calculateNameOverrides(iKeyAt, arrayList, arrayList2, i, i2);
                FragmentContainerTransition fragmentContainerTransition = (FragmentContainerTransition) sparseArray.valueAt(i4);
                if (z) {
                    configureTransitionsOptimized(fragmentManagerImpl, iKeyAt, fragmentContainerTransition, view, arrayMapCalculateNameOverrides);
                } else {
                    configureTransitionsUnoptimized(fragmentManagerImpl, iKeyAt, fragmentContainerTransition, view, arrayMapCalculateNameOverrides);
                }
            }
        }
    }
}
