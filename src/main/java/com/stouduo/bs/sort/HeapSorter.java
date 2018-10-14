package com.stouduo.bs.sort;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;

@Scope("prototype")
@Component("heap")
public class HeapSorter implements Sorter<Link> {
    private PriorityQueue<Link> heap;

    public HeapSorter() {
        this.heap = new PriorityQueue<>((l1, l2) -> {
            int createTime = (int) (l2.getHead().getCreateTime() - l1.getHead().getCreateTime());
            if (createTime == 0) {
                return l2.getHead().getId().compareTo(l1.getHead().getId());
            }
            return createTime;
        });
    }

    @Override
    public Link pollFirst() {
        return heap.poll();
    }

    @Override
    public boolean add(Link link) {
        return heap.add(link);
    }

    @Override
    public boolean addAll(Collection<? extends Link> all) {
        return heap.addAll(all);
    }

    @Override
    public Iterator<Link> iterator() {
        return heap.iterator();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
