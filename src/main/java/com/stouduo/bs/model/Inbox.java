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
    private Feed from;
    @To
    private Feed to;
    private long createTime;
    private String owner;

    public Inbox() {
        this.createTime = System.currentTimeMillis();
    }

    public Inbox(Feed to) {
        this();
        this.to = to;
    }

    public Inbox(Feed from, Feed to,String owner) {
        this();
        this.from = from;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
