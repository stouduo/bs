package com.stouduo.bs.model;

import com.arangodb.springframework.annotation.Document;

@Document("resources")
public class Resource extends FollowableResource {
    private String name;
    private String des;

    public Resource() {
        this.type = FOLLOW_TYPE_RESOURCE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
