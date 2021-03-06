package com.stouduo.bs.sort;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

@Scope("prototype")
@Component("simple")
public class SimpleSorter implements Sorter<Link> {
    private TreeSet<Link> sorter;

    public SimpleSorter() {
        sorter = new TreeSet<>((l1, l2) -> {
            int createTime = (int) (l2.getHead().getCreateTime() - l1.getHead().getCreateTime());
            if (createTime == 0) {
                return l2.getHead().getId().compareTo(l1.getHead().getId());
            }
            return createTime;
        });
    }

    @Override
    public boolean isEmpty() {
        return sorter.isEmpty();
    }


    @Override
    public Link pollFirst() {
        return sorter.pollFirst();
    }

    @Override
    public boolean add(Link Link) {
        return sorter.add(Link);
    }

    @Override
    public boolean addAll(Collection<? extends Link> all) {
        return sorter.addAll(all);
    }

    @Override
    public Iterator<Link> iterator() {
        return sorter.iterator();
    }
}
