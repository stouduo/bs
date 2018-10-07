package com.stouduo.bs.sort;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.repository.FeedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component("linkIterator")
@Scope("prototype")
public class LinkIterator implements Iterator<Link> {
    public static final String LINK_PUBLISH = "publish";
    public static final String LINK_INBOX = "inbox";
    @Autowired
    protected FeedRepository feedRepository;
    protected Feed feed;
    protected Link next;
    protected String edge;
    protected String owner;
    protected long creatTime;

    public LinkIterator(Feed feed, String edge, String owner, long creatTime) {
        this.edge = edge;
        this.feed = feed;
        this.owner = owner;
        this.creatTime = creatTime;
    }

    @Override
    public boolean hasNext() {
        List<Feed> feeds = feedRepository.findNextN("feeds/" + feed.getId(), 1, edge, owner, creatTime);
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
