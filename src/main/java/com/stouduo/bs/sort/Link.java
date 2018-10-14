package com.stouduo.bs.sort;

import com.stouduo.bs.model.Feed;

import java.util.Iterator;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(head, link.head);
    }

    @Override
    public int hashCode() {

        return Objects.hash(head);
    }
}
