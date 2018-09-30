package com.stouduo.bs.sort;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class SimpleSorter<T> implements Sorter<T> {
    private TreeSet<T> sorter;

    public SimpleSorter(Comparator<T> comparator) {
        sorter = new TreeSet(comparator);
    }

    public SimpleSorter() {
    }

    @Override
    public boolean isEmpty() {
        return sorter.isEmpty();
    }


    @Override
    public T pollFirst() {
        return sorter.pollFirst();
    }

    @Override
    public boolean add(T t) {
        return sorter.add(t);
    }

    @Override
    public boolean addAll(Collection<? extends T> all) {
        return sorter.addAll(all);
    }

    @Override
    public Iterator<T> iterator() {
        return sorter.iterator();
    }
}
