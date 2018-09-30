package com.stouduo.bs.sort;

import java.util.Collection;
import java.util.Iterator;

public interface Sorter<T> {
    T pollFirst();

    boolean add(T t);

    boolean addAll(Collection<? extends T> all);

    Iterator<T> iterator();

    boolean isEmpty();
}
