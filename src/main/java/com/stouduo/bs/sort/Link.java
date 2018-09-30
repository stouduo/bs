package com.stouduo.bs.sort;

import com.stouduo.bs.model.Feed;

import java.util.Iterator;

public class Link {
    private Feed head;
    private Iterator<Link> links;

    public Link(Feed head, Iterator<Link> links) {
        this.head = head;
        this.links = links;
    }

    public boolean hasNext() {
        return links.hasNext();
    }

    public Link next() {
        return links.next();
    }

    public Feed getHead() {
        return head;
    }

    public void setHead(Feed head) {
        this.head = head;
    }

}
