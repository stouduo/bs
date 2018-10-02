package com.stouduo.bs.sort;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.repository.FeedRepository;

import java.util.Iterator;
import java.util.List;

public class LinkIterator implements Iterator<Link> {
    public static final String LINK_PUBLISH = "publish";
    public static final String LINK_INBOX = "inbox";
    private FeedRepository feedRepository;
    private Feed feed;
    private Link next;
    private String edge;
    private String owner;
    private long creatTime;

    public LinkIterator(FeedRepository feedRepository, Feed feed, String edge, String owner, long creatTime) {
        this.feedRepository = feedRepository;
        this.edge = edge;
        this.feed = feed;
        this.owner = owner;
        this.creatTime = creatTime;
    }

    @Override
    public boolean hasNext() {
        List<Feed> feeds = feedRepository.findNextN("feeds/"+feed.getId(), 1, edge, owner, creatTime);
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
        this.feed = this.next.getHead();
        return next;
    }
}
