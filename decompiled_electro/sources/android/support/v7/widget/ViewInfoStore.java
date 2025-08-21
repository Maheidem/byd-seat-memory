package android.support.v7.widget;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.Pools;
import android.support.v7.widget.RecyclerView;

/* loaded from: classes.dex */
class ViewInfoStore {
    private static final boolean DEBUG = false;

    @VisibleForTesting
    final ArrayMap<RecyclerView.ViewHolder, InfoRecord> mLayoutHolderMap = new ArrayMap<>();

    @VisibleForTesting
    final LongSparseArray<RecyclerView.ViewHolder> mOldChangedHolders = new LongSparseArray<>();

    static class InfoRecord {
        static final int FLAG_APPEAR = 2;
        static final int FLAG_APPEAR_AND_DISAPPEAR = 3;
        static final int FLAG_APPEAR_PRE_AND_POST = 14;
        static final int FLAG_DISAPPEARED = 1;
        static final int FLAG_POST = 8;
        static final int FLAG_PRE = 4;
        static final int FLAG_PRE_AND_POST = 12;
        static Pools.Pool<InfoRecord> sPool = new Pools.SimplePool(20);
        int flags;

        @Nullable
        RecyclerView.ItemAnimator.ItemHolderInfo postInfo;

        @Nullable
        RecyclerView.ItemAnimator.ItemHolderInfo preInfo;

        private InfoRecord() {
        }

        static void drainCache() {
            while (sPool.acquire() != null) {
            }
        }

        static InfoRecord obtain() {
            InfoRecord infoRecordAcquire = sPool.acquire();
            return infoRecordAcquire == null ? new InfoRecord() : infoRecordAcquire;
        }

        static void recycle(InfoRecord infoRecord) {
            infoRecord.flags = 0;
            infoRecord.preInfo = null;
            infoRecord.postInfo = null;
            sPool.release(infoRecord);
        }
    }

    interface ProcessCallback {
        void processAppeared(RecyclerView.ViewHolder viewHolder, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void processDisappeared(RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, @Nullable RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void processPersistent(RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo, @NonNull RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo2);

        void unused(RecyclerView.ViewHolder viewHolder);
    }

    ViewInfoStore() {
    }

    private RecyclerView.ItemAnimator.ItemHolderInfo popFromLayoutStep(RecyclerView.ViewHolder viewHolder, int i) {
        InfoRecord infoRecordValueAt;
        RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo;
        int iIndexOfKey = this.mLayoutHolderMap.indexOfKey(viewHolder);
        if (iIndexOfKey < 0 || (infoRecordValueAt = this.mLayoutHolderMap.valueAt(iIndexOfKey)) == null || (infoRecordValueAt.flags & i) == 0) {
            return null;
        }
        infoRecordValueAt.flags &= ~i;
        if (i == 4) {
            itemHolderInfo = infoRecordValueAt.preInfo;
        } else {
            if (i != 8) {
                throw new IllegalArgumentException("Must provide flag PRE or POST");
            }
            itemHolderInfo = infoRecordValueAt.postInfo;
        }
        if ((infoRecordValueAt.flags & 12) == 0) {
            this.mLayoutHolderMap.removeAt(iIndexOfKey);
            InfoRecord.recycle(infoRecordValueAt);
        }
        return itemHolderInfo;
    }

    void addToAppearedInPreLayoutHolders(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecordObtain = this.mLayoutHolderMap.get(viewHolder);
        if (infoRecordObtain == null) {
            infoRecordObtain = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecordObtain);
        }
        infoRecordObtain.flags |= 2;
        infoRecordObtain.preInfo = itemHolderInfo;
    }

    void addToDisappearedInLayout(RecyclerView.ViewHolder viewHolder) {
        InfoRecord infoRecordObtain = this.mLayoutHolderMap.get(viewHolder);
        if (infoRecordObtain == null) {
            infoRecordObtain = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecordObtain);
        }
        infoRecordObtain.flags |= 1;
    }

    void addToOldChangeHolders(long j, RecyclerView.ViewHolder viewHolder) {
        this.mOldChangedHolders.put(j, viewHolder);
    }

    void addToPostLayout(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecordObtain = this.mLayoutHolderMap.get(viewHolder);
        if (infoRecordObtain == null) {
            infoRecordObtain = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecordObtain);
        }
        infoRecordObtain.postInfo = itemHolderInfo;
        infoRecordObtain.flags |= 8;
    }

    void addToPreLayout(RecyclerView.ViewHolder viewHolder, RecyclerView.ItemAnimator.ItemHolderInfo itemHolderInfo) {
        InfoRecord infoRecordObtain = this.mLayoutHolderMap.get(viewHolder);
        if (infoRecordObtain == null) {
            infoRecordObtain = InfoRecord.obtain();
            this.mLayoutHolderMap.put(viewHolder, infoRecordObtain);
        }
        infoRecordObtain.preInfo = itemHolderInfo;
        infoRecordObtain.flags |= 4;
    }

    void clear() {
        this.mLayoutHolderMap.clear();
        this.mOldChangedHolders.clear();
    }

    RecyclerView.ViewHolder getFromOldChangeHolders(long j) {
        return this.mOldChangedHolders.get(j);
    }

    boolean isDisappearing(RecyclerView.ViewHolder viewHolder) {
        InfoRecord infoRecord = this.mLayoutHolderMap.get(viewHolder);
        return (infoRecord == null || (infoRecord.flags & 1) == 0) ? false : true;
    }

    boolean isInPreLayout(RecyclerView.ViewHolder viewHolder) {
        InfoRecord infoRecord = this.mLayoutHolderMap.get(viewHolder);
        return (infoRecord == null || (infoRecord.flags & 4) == 0) ? false : true;
    }

    void onDetach() {
        InfoRecord.drainCache();
    }

    public void onViewDetached(RecyclerView.ViewHolder viewHolder) {
        removeFromDisappearedInLayout(viewHolder);
    }

    @Nullable
    RecyclerView.ItemAnimator.ItemHolderInfo popFromPostLayout(RecyclerView.ViewHolder viewHolder) {
        return popFromLayoutStep(viewHolder, 8);
    }

    @Nullable
    RecyclerView.ItemAnimator.ItemHolderInfo popFromPreLayout(RecyclerView.ViewHolder viewHolder) {
        return popFromLayoutStep(viewHolder, 4);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x003e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void process(android.support.v7.widget.ViewInfoStore.ProcessCallback r6) {
        /*
            r5 = this;
            android.support.v4.util.ArrayMap<android.support.v7.widget.RecyclerView$ViewHolder, android.support.v7.widget.ViewInfoStore$InfoRecord> r0 = r5.mLayoutHolderMap
            int r0 = r0.size()
            int r0 = r0 + (-1)
        L8:
            if (r0 < 0) goto L6e
            android.support.v4.util.ArrayMap<android.support.v7.widget.RecyclerView$ViewHolder, android.support.v7.widget.ViewInfoStore$InfoRecord> r1 = r5.mLayoutHolderMap
            java.lang.Object r1 = r1.keyAt(r0)
            android.support.v7.widget.RecyclerView$ViewHolder r1 = (android.support.v7.widget.RecyclerView.ViewHolder) r1
            android.support.v4.util.ArrayMap<android.support.v7.widget.RecyclerView$ViewHolder, android.support.v7.widget.ViewInfoStore$InfoRecord> r2 = r5.mLayoutHolderMap
            java.lang.Object r2 = r2.removeAt(r0)
            android.support.v7.widget.ViewInfoStore$InfoRecord r2 = (android.support.v7.widget.ViewInfoStore.InfoRecord) r2
            int r3 = r2.flags
            r4 = 3
            r3 = r3 & r4
            if (r3 != r4) goto L24
        L20:
            r6.unused(r1)
            goto L68
        L24:
            int r3 = r2.flags
            r3 = r3 & 1
            if (r3 == 0) goto L37
            android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo r3 = r2.preInfo
            if (r3 != 0) goto L2f
            goto L20
        L2f:
            android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo r3 = r2.preInfo
            android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo r4 = r2.postInfo
        L33:
            r6.processDisappeared(r1, r3, r4)
            goto L68
        L37:
            int r3 = r2.flags
            r4 = 14
            r3 = r3 & r4
            if (r3 != r4) goto L46
        L3e:
            android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo r3 = r2.preInfo
            android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo r4 = r2.postInfo
            r6.processAppeared(r1, r3, r4)
            goto L68
        L46:
            int r3 = r2.flags
            r4 = 12
            r3 = r3 & r4
            if (r3 != r4) goto L55
            android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo r3 = r2.preInfo
            android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo r4 = r2.postInfo
            r6.processPersistent(r1, r3, r4)
            goto L68
        L55:
            int r3 = r2.flags
            r3 = r3 & 4
            if (r3 == 0) goto L5f
            android.support.v7.widget.RecyclerView$ItemAnimator$ItemHolderInfo r3 = r2.preInfo
            r4 = 0
            goto L33
        L5f:
            int r3 = r2.flags
            r3 = r3 & 8
            if (r3 == 0) goto L66
            goto L3e
        L66:
            int r1 = r2.flags
        L68:
            android.support.v7.widget.ViewInfoStore.InfoRecord.recycle(r2)
            int r0 = r0 + (-1)
            goto L8
        L6e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ViewInfoStore.process(android.support.v7.widget.ViewInfoStore$ProcessCallback):void");
    }

    void removeFromDisappearedInLayout(RecyclerView.ViewHolder viewHolder) {
        InfoRecord infoRecord = this.mLayoutHolderMap.get(viewHolder);
        if (infoRecord == null) {
            return;
        }
        infoRecord.flags &= -2;
    }

    void removeViewHolder(RecyclerView.ViewHolder viewHolder) {
        int size = this.mOldChangedHolders.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            if (viewHolder == this.mOldChangedHolders.valueAt(size)) {
                this.mOldChangedHolders.removeAt(size);
                break;
            }
            size--;
        }
        InfoRecord infoRecordRemove = this.mLayoutHolderMap.remove(viewHolder);
        if (infoRecordRemove != null) {
            InfoRecord.recycle(infoRecordRemove);
        }
    }
}
