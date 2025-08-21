package org.acra.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/* loaded from: classes.dex */
public class BoundedLinkedList<E> extends LinkedList<E> {
    private final int maxSize;

    public BoundedLinkedList(int i) {
        this.maxSize = i;
    }

    @Override // java.util.LinkedList, java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public void add(int i, E e) {
        if (size() == this.maxSize) {
            removeFirst();
        }
        super.add(i, e);
    }

    @Override // java.util.LinkedList, java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque, java.util.Queue
    public boolean add(E e) {
        if (size() == this.maxSize) {
            removeFirst();
        }
        return super.add(e);
    }

    @Override // java.util.LinkedList, java.util.AbstractSequentialList, java.util.AbstractList, java.util.List
    public boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.LinkedList, java.util.AbstractCollection, java.util.Collection, java.util.List, java.util.Deque
    public boolean addAll(Collection<? extends E> collection) {
        int size = (size() + collection.size()) - this.maxSize;
        if (size > 0) {
            removeRange(0, size);
        }
        return super.addAll(collection);
    }

    @Override // java.util.LinkedList, java.util.Deque
    public void addFirst(E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.LinkedList, java.util.Deque
    public void addLast(E e) {
        add(e);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
        }
        return sb.toString();
    }
}
