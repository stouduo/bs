package com.stouduo.bs.strategy;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.sort.Link;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomStrategy extends PushPullStrategy {
    @Value("${engine.v.followersCount.min:1000}")
    public void setVFollowersCount(int vFollowersCount) {
        this.vFollowersCount = vFollowersCount;
    }

    @Override
    public void doPush(FollowableResource followableResource, Feed feed) {
        super.doPush(followableResource, feed);
    }

    @Override
    public List<Feed> doPull(List<Link> links, int size) {
        return super.doPull(links, size);
    }
}
