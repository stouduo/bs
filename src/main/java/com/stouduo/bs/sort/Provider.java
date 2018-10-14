package com.stouduo.bs.sort;

import com.stouduo.bs.model.Feed;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class Provider implements ApplicationContextAware {
    private ApplicationContext context;

    @Value("${engine.sorter.name:simple}")
    private String sorterName;
    @Value("${engine.iterator.name:linkIterator}")
    private String iteratorName;

    public Sorter<Link> getSorter() {
        return context.getBean(sorterName, Sorter.class);
    }

    public Iterator<Link> getIterator(Feed feed, String edge, String owner, long creatTime) {
        return (Iterator<Link>) context.getBean(iteratorName, feed, edge, owner, creatTime);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
