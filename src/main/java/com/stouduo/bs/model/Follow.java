package com.stouduo.bs.model;

import com.arangodb.springframework.annotation.Edge;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
import org.springframework.data.annotation.Id;

@Edge("follows")
public class Follow {
    @Id
    private String id;
    @From
    private User from;
    @To
    private FollowableResource to;
    private long createTime;
    private String label = "follows";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Follow() {
    }

    public Follow(FollowableResource to) {
        this.to = to;
    }

    public Follow(User from, FollowableResource to) {
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

    public FollowableResource getTo() {
        return to;
    }

    public void setTo(FollowableResource to) {
        this.to = to;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
