package com.stouduo.bs.sort;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.repository.FeedRepository;

import java.util.Iterator;
import java.util.List;

public class LinkIterator implements Iterator<Link> {
    private FeedRepository feedRepository;
    private Link link, next;
    private String edge;
    private String owner;
    private long creatTime;

    public LinkIterator(FeedRepository feedRepository, Link link, Link next, String edge, String owner, long creatTime) {
        this.feedRepository = feedRepository;
        this.link = link;
        this.next = next;
        this.edge = edge;
        this.owner = owner;
        this.creatTime = creatTime;
    }

    @Override
    public boolean hasNext() {
        List<Feed> feeds = feedRepository.findNextN(link.getHead().getId(), 1, edge, owner, creatTime);
        if (feeds.size() > 0) {
            next = new Link(feeds.get(0), this);
            return true;
        }
        return false;
    }

    @Override
    public Link next() {
        if (next == null) {
            hasNext();
        }
        this.link = this.next;
        return next;
    }
}
