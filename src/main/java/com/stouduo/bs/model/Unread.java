package com.stouduo.bs.model;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import org.springframework.data.annotation.Id;

@Edge("unreads")
public class Unread {
    @Id
    private String id;
    @From
    private Feed from;
    @To
    private Feed to;
    private long createTime;

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
