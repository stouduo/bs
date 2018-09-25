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
    private FollowableResource from;
    @To
    private Feed to;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FollowableResource getFrom() {
        return from;
    }

    public void setFrom(FollowableResource from) {
        this.from = from;
    }

    public Feed getTo() {
        return to;
    }

    public void setTo(Feed to) {
        this.to = to;
    }
}
