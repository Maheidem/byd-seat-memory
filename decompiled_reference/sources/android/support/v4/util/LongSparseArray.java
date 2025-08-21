package android.support.v4.util;

/* loaded from: classes.dex */
public class LongSparseArray<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private long[] mKeys;
    private int mSize;
    private Object[] mValues;

    public LongSparseArray() {
        this(10);
    }

    public LongSparseArray(int i) {
        Object[] objArr;
        this.mGarbage = false;
        if (i == 0) {
            this.mKeys = ContainerHelpers.EMPTY_LONGS;
            objArr = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            int iIdealLongArraySize = ContainerHelpers.idealLongArraySize(i);
            this.mKeys = new long[iIdealLongArraySize];
            objArr = new Object[iIdealLongArraySize];
        }
        this.mValues = objArr;
        this.mSize = 0;
    }

    private void gc() {
        int i = this.mSize;
        long[] jArr = this.mKeys;
        Object[] objArr = this.mValues;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    jArr[i2] = jArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    public void append(long j, E e) {
        if (this.mSize != 0 && j <= this.mKeys[this.mSize - 1]) {
            put(j, e);
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc();
        }
        int i = this.mSize;
        if (i >= this.mKeys.length) {
            int iIdealLongArraySize = ContainerHelpers.idealLongArraySize(i + 1);
            long[] jArr = new long[iIdealLongArraySize];
            Object[] objArr = new Object[iIdealLongArraySize];
            System.arraycopy(this.mKeys, 0, jArr, 0, this.mKeys.length);
            System.arraycopy(this.mValues, 0, objArr, 0, this.mValues.length);
            this.mKeys = jArr;
            this.mValues = objArr;
        }
        this.mKeys[i] = j;
        this.mValues[i] = e;
        this.mSize = i + 1;
    }

    public void clear() {
        int i = this.mSize;
        Object[] objArr = this.mValues;
        for (int i2 = 0; i2 < i; i2++) {
            objArr[i2] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public LongSparseArray<E> m4clone() {
        try {
            LongSparseArray<E> longSparseArray = (LongSparseArray) super.clone();
            try {
                longSparseArray.mKeys = (long[]) this.mKeys.clone();
                longSparseArray.mValues = (Object[]) this.mValues.clone();
                return longSparseArray;
            } catch (CloneNotSupportedException unused) {
                return longSparseArray;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public void delete(long j) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (iBinarySearch < 0 || this.mValues[iBinarySearch] == DELETED) {
            return;
        }
        this.mValues[iBinarySearch] = DELETED;
        this.mGarbage = true;
    }

    public E get(long j) {
        return get(j, null);
    }

    public E get(long j, E e) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        return (iBinarySearch < 0 || this.mValues[iBinarySearch] == DELETED) ? e : (E) this.mValues[iBinarySearch];
    }

    public int indexOfKey(long j) {
        if (this.mGarbage) {
            gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
    }

    public int indexOfValue(E e) {
        if (this.mGarbage) {
            gc();
        }
        for (int i = 0; i < this.mSize; i++) {
            if (this.mValues[i] == e) {
                return i;
            }
        }
        return -1;
    }

    public long keyAt(int i) {
        if (this.mGarbage) {
            gc();
        }
        return this.mKeys[i];
    }

    public void put(long j, E e) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = e;
            return;
        }
        int i = ~iBinarySearch;
        if (i < this.mSize && this.mValues[i] == DELETED) {
            this.mKeys[i] = j;
            this.mValues[i] = e;
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc();
            i = ~ContainerHelpers.binarySearch(this.mKeys, this.mSize, j);
        }
        if (this.mSize >= this.mKeys.length) {
            int iIdealLongArraySize = ContainerHelpers.idealLongArraySize(this.mSize + 1);
            long[] jArr = new long[iIdealLongArraySize];
            Object[] objArr = new Object[iIdealLongArraySize];
            System.arraycopy(this.mKeys, 0, jArr, 0, this.mKeys.length);
            System.arraycopy(this.mValues, 0, objArr, 0, this.mValues.length);
            this.mKeys = jArr;
            this.mValues = objArr;
        }
        if (this.mSize - i != 0) {
            int i2 = i + 1;
            System.arraycopy(this.mKeys, i, this.mKeys, i2, this.mSize - i);
            System.arraycopy(this.mValues, i, this.mValues, i2, this.mSize - i);
        }
        this.mKeys[i] = j;
        this.mValues[i] = e;
        this.mSize++;
    }

    public void remove(long j) {
        delete(j);
    }

    public void removeAt(int i) {
        if (this.mValues[i] != DELETED) {
            this.mValues[i] = DELETED;
            this.mGarbage = true;
        }
    }

    public void setValueAt(int i, E e) {
        if (this.mGarbage) {
            gc();
        }
        this.mValues[i] = e;
    }

    public int size() {
        if (this.mGarbage) {
            gc();
        }
        return this.mSize;
    }

    public String toString() {
        if (size() <= 0) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder(this.mSize * 28);
        sb.append('{');
        for (int i = 0; i < this.mSize; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(keyAt(i));
            sb.append('=');
            E eValueAt = valueAt(i);
            if (eValueAt != this) {
                sb.append(eValueAt);
            } else {
                sb.append("(this Map)");
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public E valueAt(int i) {
        if (this.mGarbage) {
            gc();
        }
        return (E) this.mValues[i];
    }
}
