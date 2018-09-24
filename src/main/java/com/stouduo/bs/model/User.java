package com.stouduo.bs.model;

import com.arangodb.springframework.annotation.Document;

@Document("users")
public class User extends FollowableResource {

    private String name;
    private String email;

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