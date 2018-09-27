package com.stouduo.bs.model;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import org.springframework.data.annotation.Id;

@Edge("inbox")
public class Inbox {
    @Id
    private String id;
    @From
    private User from;
    @To
    private Feed to;
    private long createTime;

    public Inbox() {
    }

    public Inbox(User from, Feed to) {
        this.from = from;
        this.to = to;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public Feed getTo() {
        return to;
    }

    public void setTo(Feed to) {
        this.to = to;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
