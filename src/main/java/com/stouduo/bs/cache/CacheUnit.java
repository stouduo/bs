package com.stouduo.bs.cache;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class CacheUnit<E> extends TreeSet<E> {
    private static final int MAX_CAPACITY = 50;
    private int capacity;

    public CacheUnit() {
        super();
    }

    public CacheUnit(Comparator<? super E> comparator) {
        super(comparator);
    }

    public CacheUnit(Collection<? extends E> c) {
        super(c);
    }

    public CacheUnit(SortedSet<E> s) {
        super(s);
    }

    public CacheUnit setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    @Override
    public boolean add(E e) {
        boolean ret = super.add(e);
        if (size() - capacity > 0)
            pollLast();
        return ret;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean ret = super.addAll(c);
        int delCount = size() - capacity;
        while (delCount-- > 0) {
            pollLast();
        }
        return ret;
    }
}
