package com.stouduo.bs.model;

import com.arangodb.springframework.annotation.Document;
import org.springframework.data.annotation.Id;

@Document("feeds")
public class Feed {
    @Id
    private String id;
    private String msg;
    private long createTime;
    private String author;
    private String resource;

    public Feed() {
    }

    public Feed(String id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
