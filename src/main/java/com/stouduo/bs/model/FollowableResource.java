package com.stouduo.bs.model;

import org.springframework.data.annotation.Id;

public class FollowableResource {
    public static final String FOLLOW_TYPE_RESOURCE = "RESOURCE";
    public static final String FOLLOW_TYPE_USER = "USER";
    @Id
    protected String id;
    protected String type;

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
