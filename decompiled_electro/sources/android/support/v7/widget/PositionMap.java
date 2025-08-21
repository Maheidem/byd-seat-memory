package android.support.v7.widget;

import java.util.ArrayList;

/* loaded from: classes.dex */
class PositionMap<E> implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    static class ContainerHelpers {
        static final boolean[] EMPTY_BOOLEANS = new boolean[0];
        static final int[] EMPTY_INTS = new int[0];
        static final long[] EMPTY_LONGS = new long[0];
        static final Object[] EMPTY_OBJECTS = new Object[0];

        ContainerHelpers() {
        }

        static int binarySearch(int[] iArr, int i, int i2) {
            int i3 = i - 1;
            int i4 = 0;
            while (i4 <= i3) {
                int i5 = (i4 + i3) >>> 1;
                int i6 = iArr[i5];
                if (i6 < i2) {
                    i4 = i5 + 1;
                } else {
                    if (i6 <= i2) {
                        return i5;
                    }
                    i3 = i5 - 1;
                }
            }
            return ~i4;
        }
    }

    public PositionMap() {
        this(10);
    }

    public PositionMap(int i) {
        Object[] objArr;
        this.mGarbage = false;
        if (i == 0) {
            this.mKeys = ContainerHelpers.EMPTY_INTS;
            objArr = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            int iIdealIntArraySize = idealIntArraySize(i);
            this.mKeys = new int[iIdealIntArraySize];
            objArr = new Object[iIdealIntArraySize];
        }
        this.mValues = objArr;
        this.mSize = 0;
    }

    private void gc() {
        int i = this.mSize;
        int[] iArr = this.mKeys;
        Object[] objArr = this.mValues;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (obj != DELETED) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    objArr[i2] = obj;
                    objArr[i3] = null;
                }
                i2++;
            }
        }
        this.mGarbage = false;
        this.mSize = i2;
    }

    static int idealBooleanArraySize(int i) {
        return idealByteArraySize(i);
    }

    static int idealByteArraySize(int i) {
        for (int i2 = 4; i2 < 32; i2++) {
            int i3 = (1 << i2) - 12;
            if (i <= i3) {
                return i3;
            }
        }
        return i;
    }

    static int idealCharArraySize(int i) {
        return idealByteArraySize(i * 2) / 2;
    }

    static int idealFloatArraySize(int i) {
        return idealByteArraySize(i * 4) / 4;
    }

    static int idealIntArraySize(int i) {
        return idealByteArraySize(i * 4) / 4;
    }

    static int idealLongArraySize(int i) {
        return idealByteArraySize(i * 8) / 8;
    }

    static int idealObjectArraySize(int i) {
        return idealByteArraySize(i * 4) / 4;
    }

    static int idealShortArraySize(int i) {
        return idealByteArraySize(i * 2) / 2;
    }

    public void append(int i, E e) {
        if (this.mSize != 0 && i <= this.mKeys[this.mSize - 1]) {
            put(i, e);
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc();
        }
        int i2 = this.mSize;
        if (i2 >= this.mKeys.length) {
            int iIdealIntArraySize = idealIntArraySize(i2 + 1);
            int[] iArr = new int[iIdealIntArraySize];
            Object[] objArr = new Object[iIdealIntArraySize];
            System.arraycopy(this.mKeys, 0, iArr, 0, this.mKeys.length);
            System.arraycopy(this.mValues, 0, objArr, 0, this.mValues.length);
            this.mKeys = iArr;
            this.mValues = objArr;
        }
        this.mKeys[i2] = i;
        this.mValues[i2] = e;
        this.mSize = i2 + 1;
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
    public PositionMap<E> m6clone() {
        try {
            PositionMap<E> positionMap = (PositionMap) super.clone();
            try {
                positionMap.mKeys = (int[]) this.mKeys.clone();
                positionMap.mValues = (Object[]) this.mValues.clone();
                return positionMap;
            } catch (CloneNotSupportedException unused) {
                return positionMap;
            }
        } catch (CloneNotSupportedException unused2) {
            return null;
        }
    }

    public void delete(int i) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (iBinarySearch < 0 || this.mValues[iBinarySearch] == DELETED) {
            return;
        }
        this.mValues[iBinarySearch] = DELETED;
        this.mGarbage = true;
    }

    public E get(int i) {
        return get(i, null);
    }

    public E get(int i, E e) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        return (iBinarySearch < 0 || this.mValues[iBinarySearch] == DELETED) ? e : (E) this.mValues[iBinarySearch];
    }

    public int indexOfKey(int i) {
        if (this.mGarbage) {
            gc();
        }
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
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

    public void insertKeyRange(int i, int i2) {
    }

    public int keyAt(int i) {
        if (this.mGarbage) {
            gc();
        }
        return this.mKeys[i];
    }

    public void put(int i, E e) {
        int iBinarySearch = ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        if (iBinarySearch >= 0) {
            this.mValues[iBinarySearch] = e;
            return;
        }
        int i2 = ~iBinarySearch;
        if (i2 < this.mSize && this.mValues[i2] == DELETED) {
            this.mKeys[i2] = i;
            this.mValues[i2] = e;
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            gc();
            i2 = ~ContainerHelpers.binarySearch(this.mKeys, this.mSize, i);
        }
        if (this.mSize >= this.mKeys.length) {
            int iIdealIntArraySize = idealIntArraySize(this.mSize + 1);
            int[] iArr = new int[iIdealIntArraySize];
            Object[] objArr = new Object[iIdealIntArraySize];
            System.arraycopy(this.mKeys, 0, iArr, 0, this.mKeys.length);
            System.arraycopy(this.mValues, 0, objArr, 0, this.mValues.length);
            this.mKeys = iArr;
            this.mValues = objArr;
        }
        if (this.mSize - i2 != 0) {
            int i3 = i2 + 1;
            System.arraycopy(this.mKeys, i2, this.mKeys, i3, this.mSize - i2);
            System.arraycopy(this.mValues, i2, this.mValues, i3, this.mSize - i2);
        }
        this.mKeys[i2] = i;
        this.mValues[i2] = e;
        this.mSize++;
    }

    public void remove(int i) {
        delete(i);
    }

    public void removeAt(int i) {
        if (this.mValues[i] != DELETED) {
            this.mValues[i] = DELETED;
            this.mGarbage = true;
        }
    }

    public void removeAtRange(int i, int i2) {
        int iMin = Math.min(this.mSize, i2 + i);
        while (i < iMin) {
            removeAt(i);
            i++;
        }
    }

    public void removeKeyRange(ArrayList<E> arrayList, int i, int i2) {
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
