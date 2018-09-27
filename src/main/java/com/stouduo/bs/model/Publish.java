package com.stouduo.bs.model;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import org.springframework.data.annotation.Id;

@Edge("publish")
public class Publish {
    @Id
    private String id;
    @From
    private Feed from;
    @To
    private Feed to;

    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Publish() {
    }

    public Publish(Feed from, Feed to, String owner) {
        this.from = from;
        this.to = to;
        this.owner = owner;
    }
    public Publish(Feed from, Feed to) {
        this.from = from;
        this.to = to;
    }

    public Publish(Feed to, String owner) {
        this.to = to;
        this.owner = owner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Feed getFrom() {
        return from;
    }

    public void setFrom(Feed from) {
        this.from = from;
    }

    public Feed getTo() {
        return to;
    }

    public void setTo(Feed to) {
        this.to = to;
    }
}
