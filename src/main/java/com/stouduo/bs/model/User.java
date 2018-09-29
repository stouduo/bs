package com.stouduo.bs.model;

import com.arangodb.springframework.annotation.Document;

@Document("users")
public class User extends FollowableResource {

    private String name;
    private String email;
    private int followedCount = 0;
    private String inboxLink;
    private int inboxCount = 0;

    public int getInboxCount() {
        return inboxCount;
    }

    public void setInboxCount(int inboxCount) {
        this.inboxCount = inboxCount;
    }

    public String getInboxLink() {
        return inboxLink;
    }

    public void setInboxLink(String inboxLink) {
        this.inboxLink = inboxLink;
    }

    public int getFollowedCount() {
        return followedCount;
    }

    public void setFollowedCount(int followedCount) {
        this.followedCount = followedCount;
    }

    public User(String id) {
        super(id);
    }

    public User() {
        this.type = FOLLOW_TYPE_USER;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}