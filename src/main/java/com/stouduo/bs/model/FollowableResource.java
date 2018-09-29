package com.stouduo.bs.model;

import org.springframework.data.annotation.Id;

public class FollowableResource {
    public static final String FOLLOW_TYPE_RESOURCE = "resources";
    public static final String FOLLOW_TYPE_USER = "users";
    @Id
    protected String id;
    protected String type;
    protected int followersCount = 0;
    protected String publishLink;
    protected int publishCount = 0;

    public int getPublishCount() {
        return publishCount;
    }

    public void setPublishCount(int publishCount) {
        this.publishCount = publishCount;
    }

    public FollowableResource(String id) {
        this.id = id;
    }

    public FollowableResource() {
    }

    public String getPublishLink() {
        return publishLink;
    }

    public void setPublishLink(String publishLink) {
        this.publishLink = publishLink;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
