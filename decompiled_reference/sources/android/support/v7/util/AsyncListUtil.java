package android.support.v7.util;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.support.v7.util.ThreadUtil;
import android.support.v7.util.TileList;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

/* loaded from: classes.dex */
public class AsyncListUtil<T> {
    static final boolean DEBUG = false;
    static final String TAG = "AsyncListUtil";
    boolean mAllowScrollHints;
    final ThreadUtil.BackgroundCallback<T> mBackgroundProxy;
    final DataCallback<T> mDataCallback;
    final ThreadUtil.MainThreadCallback<T> mMainThreadProxy;
    final Class<T> mTClass;
    final TileList<T> mTileList;
    final int mTileSize;
    final ViewCallback mViewCallback;
    final int[] mTmpRange = new int[2];
    final int[] mPrevRange = new int[2];
    final int[] mTmpRangeExtended = new int[2];
    private int mScrollHint = 0;
    int mItemCount = 0;
    int mDisplayedGeneration = 0;
    int mRequestedGeneration = this.mDisplayedGeneration;
    final SparseIntArray mMissingPositions = new SparseIntArray();
    private final ThreadUtil.MainThreadCallback<T> mMainThreadCallback = new ThreadUtil.MainThreadCallback<T>() { // from class: android.support.v7.util.AsyncListUtil.1
        private boolean isRequestedGeneration(int i) {
            return i == AsyncListUtil.this.mRequestedGeneration;
        }

        private void recycleAllTiles() {
            for (int i = 0; i < AsyncListUtil.this.mTileList.size(); i++) {
                AsyncListUtil.this.mBackgroundProxy.recycleTile(AsyncListUtil.this.mTileList.getAtIndex(i));
            }
            AsyncListUtil.this.mTileList.clear();
        }

        @Override // android.support.v7.util.ThreadUtil.MainThreadCallback
        public void addTile(int i, TileList.Tile<T> tile) {
            if (!isRequestedGeneration(i)) {
                AsyncListUtil.this.mBackgroundProxy.recycleTile(tile);
                return;
            }
            TileList.Tile<T> tileAddOrReplace = AsyncListUtil.this.mTileList.addOrReplace(tile);
            if (tileAddOrReplace != null) {
                Log.e(AsyncListUtil.TAG, "duplicate tile @" + tileAddOrReplace.mStartPosition);
                AsyncListUtil.this.mBackgroundProxy.recycleTile(tileAddOrReplace);
            }
            int i2 = tile.mStartPosition + tile.mItemCount;
            int i3 = 0;
            while (i3 < AsyncListUtil.this.mMissingPositions.size()) {
                int iKeyAt = AsyncListUtil.this.mMissingPositions.keyAt(i3);
                if (tile.mStartPosition > iKeyAt || iKeyAt >= i2) {
                    i3++;
                } else {
                    AsyncListUtil.this.mMissingPositions.removeAt(i3);
                    AsyncListUtil.this.mViewCallback.onItemLoaded(iKeyAt);
                }
            }
        }

        @Override // android.support.v7.util.ThreadUtil.MainThreadCallback
        public void removeTile(int i, int i2) {
            if (isRequestedGeneration(i)) {
                TileList.Tile<T> tileRemoveAtPos = AsyncListUtil.this.mTileList.removeAtPos(i2);
                if (tileRemoveAtPos != null) {
                    AsyncListUtil.this.mBackgroundProxy.recycleTile(tileRemoveAtPos);
                    return;
                }
                Log.e(AsyncListUtil.TAG, "tile not found @" + i2);
            }
        }

        @Override // android.support.v7.util.ThreadUtil.MainThreadCallback
        public void updateItemCount(int i, int i2) {
            if (isRequestedGeneration(i)) {
                AsyncListUtil.this.mItemCount = i2;
                AsyncListUtil.this.mViewCallback.onDataRefresh();
                AsyncListUtil.this.mDisplayedGeneration = AsyncListUtil.this.mRequestedGeneration;
                recycleAllTiles();
                AsyncListUtil.this.mAllowScrollHints = false;
                AsyncListUtil.this.updateRange();
            }
        }
    };
    private final ThreadUtil.BackgroundCallback<T> mBackgroundCallback = new ThreadUtil.BackgroundCallback<T>() { // from class: android.support.v7.util.AsyncListUtil.2
        private int mFirstRequiredTileStart;
        private int mGeneration;
        private int mItemCount;
        private int mLastRequiredTileStart;
        final SparseBooleanArray mLoadedTiles = new SparseBooleanArray();
        private TileList.Tile<T> mRecycledRoot;

        private TileList.Tile<T> acquireTile() {
            if (this.mRecycledRoot == null) {
                return new TileList.Tile<>(AsyncListUtil.this.mTClass, AsyncListUtil.this.mTileSize);
            }
            TileList.Tile<T> tile = this.mRecycledRoot;
            this.mRecycledRoot = this.mRecycledRoot.mNext;
            return tile;
        }

        private void addTile(TileList.Tile<T> tile) {
            this.mLoadedTiles.put(tile.mStartPosition, true);
            AsyncListUtil.this.mMainThreadProxy.addTile(this.mGeneration, tile);
        }

        private void flushTileCache(int i) {
            int maxCachedTiles = AsyncListUtil.this.mDataCallback.getMaxCachedTiles();
            while (this.mLoadedTiles.size() >= maxCachedTiles) {
                int iKeyAt = this.mLoadedTiles.keyAt(0);
                int iKeyAt2 = this.mLoadedTiles.keyAt(this.mLoadedTiles.size() - 1);
                int i2 = this.mFirstRequiredTileStart - iKeyAt;
                int i3 = iKeyAt2 - this.mLastRequiredTileStart;
                if (i2 > 0 && (i2 >= i3 || i == 2)) {
                    removeTile(iKeyAt);
                } else {
                    if (i3 <= 0) {
                        return;
                    }
                    if (i2 >= i3 && i != 1) {
                        return;
                    } else {
                        removeTile(iKeyAt2);
                    }
                }
            }
        }

        private int getTileStart(int i) {
            return i - (i % AsyncListUtil.this.mTileSize);
        }

        private boolean isTileLoaded(int i) {
            return this.mLoadedTiles.get(i);
        }

        private void log(String str, Object... objArr) {
            Log.d(AsyncListUtil.TAG, "[BKGR] " + String.format(str, objArr));
        }

        private void removeTile(int i) {
            this.mLoadedTiles.delete(i);
            AsyncListUtil.this.mMainThreadProxy.removeTile(this.mGeneration, i);
        }

        private void requestTiles(int i, int i2, int i3, boolean z) {
            int i4 = i;
            while (i4 <= i2) {
                AsyncListUtil.this.mBackgroundProxy.loadTile(z ? (i2 + i) - i4 : i4, i3);
                i4 += AsyncListUtil.this.mTileSize;
            }
        }

        @Override // android.support.v7.util.ThreadUtil.BackgroundCallback
        public void loadTile(int i, int i2) {
            if (isTileLoaded(i)) {
                return;
            }
            TileList.Tile<T> tileAcquireTile = acquireTile();
            tileAcquireTile.mStartPosition = i;
            tileAcquireTile.mItemCount = Math.min(AsyncListUtil.this.mTileSize, this.mItemCount - tileAcquireTile.mStartPosition);
            AsyncListUtil.this.mDataCallback.fillData(tileAcquireTile.mItems, tileAcquireTile.mStartPosition, tileAcquireTile.mItemCount);
            flushTileCache(i2);
            addTile(tileAcquireTile);
        }

        @Override // android.support.v7.util.ThreadUtil.BackgroundCallback
        public void recycleTile(TileList.Tile<T> tile) {
            AsyncListUtil.this.mDataCallback.recycleData(tile.mItems, tile.mItemCount);
            tile.mNext = this.mRecycledRoot;
            this.mRecycledRoot = tile;
        }

        @Override // android.support.v7.util.ThreadUtil.BackgroundCallback
        public void refresh(int i) {
            this.mGeneration = i;
            this.mLoadedTiles.clear();
            this.mItemCount = AsyncListUtil.this.mDataCallback.refreshData();
            AsyncListUtil.this.mMainThreadProxy.updateItemCount(this.mGeneration, this.mItemCount);
        }

        @Override // android.support.v7.util.ThreadUtil.BackgroundCallback
        public void updateRange(int i, int i2, int i3, int i4, int i5) {
            if (i > i2) {
                return;
            }
            int tileStart = getTileStart(i);
            int tileStart2 = getTileStart(i2);
            this.mFirstRequiredTileStart = getTileStart(i3);
            this.mLastRequiredTileStart = getTileStart(i4);
            if (i5 == 1) {
                requestTiles(this.mFirstRequiredTileStart, tileStart2, i5, true);
                requestTiles(tileStart2 + AsyncListUtil.this.mTileSize, this.mLastRequiredTileStart, i5, false);
            } else {
                requestTiles(tileStart, this.mLastRequiredTileStart, i5, false);
                requestTiles(this.mFirstRequiredTileStart, tileStart - AsyncListUtil.this.mTileSize, i5, true);
            }
        }
    };

    public static abstract class DataCallback<T> {
        @WorkerThread
        public abstract void fillData(T[] tArr, int i, int i2);

        @WorkerThread
        public int getMaxCachedTiles() {
            return 10;
        }

        @WorkerThread
        public void recycleData(T[] tArr, int i) {
        }

        @WorkerThread
        public abstract int refreshData();
    }

    public static abstract class ViewCallback {
        public static final int HINT_SCROLL_ASC = 2;
        public static final int HINT_SCROLL_DESC = 1;
        public static final int HINT_SCROLL_NONE = 0;

        @UiThread
        public void extendRangeInto(int[] iArr, int[] iArr2, int i) {
            int i2 = (iArr[1] - iArr[0]) + 1;
            int i3 = i2 / 2;
            iArr2[0] = iArr[0] - (i == 1 ? i2 : i3);
            int i4 = iArr[1];
            if (i != 2) {
                i2 = i3;
            }
            iArr2[1] = i4 + i2;
        }

        @UiThread
        public abstract void getItemRangeInto(int[] iArr);

        @UiThread
        public abstract void onDataRefresh();

        @UiThread
        public abstract void onItemLoaded(int i);
    }

    public AsyncListUtil(Class<T> cls, int i, DataCallback<T> dataCallback, ViewCallback viewCallback) {
        this.mTClass = cls;
        this.mTileSize = i;
        this.mDataCallback = dataCallback;
        this.mViewCallback = viewCallback;
        this.mTileList = new TileList<>(this.mTileSize);
        MessageThreadUtil messageThreadUtil = new MessageThreadUtil();
        this.mMainThreadProxy = messageThreadUtil.getMainThreadProxy(this.mMainThreadCallback);
        this.mBackgroundProxy = messageThreadUtil.getBackgroundProxy(this.mBackgroundCallback);
        refresh();
    }

    private boolean isRefreshPending() {
        return this.mRequestedGeneration != this.mDisplayedGeneration;
    }

    public T getItem(int i) {
        if (i < 0 || i >= this.mItemCount) {
            throw new IndexOutOfBoundsException(i + " is not within 0 and " + this.mItemCount);
        }
        T itemAt = this.mTileList.getItemAt(i);
        if (itemAt == null && !isRefreshPending()) {
            this.mMissingPositions.put(i, 0);
        }
        return itemAt;
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    void log(String str, Object... objArr) {
        Log.d(TAG, "[MAIN] " + String.format(str, objArr));
    }

    public void onRangeChanged() {
        if (isRefreshPending()) {
            return;
        }
        updateRange();
        this.mAllowScrollHints = true;
    }

    public void refresh() {
        this.mMissingPositions.clear();
        ThreadUtil.BackgroundCallback<T> backgroundCallback = this.mBackgroundProxy;
        int i = this.mRequestedGeneration + 1;
        this.mRequestedGeneration = i;
        backgroundCallback.refresh(i);
    }

    void updateRange() {
        this.mViewCallback.getItemRangeInto(this.mTmpRange);
        if (this.mTmpRange[0] > this.mTmpRange[1] || this.mTmpRange[0] < 0 || this.mTmpRange[1] >= this.mItemCount) {
            return;
        }
        if (!this.mAllowScrollHints || this.mTmpRange[0] > this.mPrevRange[1] || this.mPrevRange[0] > this.mTmpRange[1]) {
            this.mScrollHint = 0;
        } else if (this.mTmpRange[0] < this.mPrevRange[0]) {
            this.mScrollHint = 1;
        } else if (this.mTmpRange[0] > this.mPrevRange[0]) {
            this.mScrollHint = 2;
        }
        this.mPrevRange[0] = this.mTmpRange[0];
        this.mPrevRange[1] = this.mTmpRange[1];
        this.mViewCallback.extendRangeInto(this.mTmpRange, this.mTmpRangeExtended, this.mScrollHint);
        this.mTmpRangeExtended[0] = Math.min(this.mTmpRange[0], Math.max(this.mTmpRangeExtended[0], 0));
        this.mTmpRangeExtended[1] = Math.max(this.mTmpRange[1], Math.min(this.mTmpRangeExtended[1], this.mItemCount - 1));
        this.mBackgroundProxy.updateRange(this.mTmpRange[0], this.mTmpRange[1], this.mTmpRangeExtended[0], this.mTmpRangeExtended[1], this.mScrollHint);
    }
}
