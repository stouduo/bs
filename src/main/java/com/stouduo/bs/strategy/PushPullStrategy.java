package com.stouduo.bs.strategy;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.User;
import com.stouduo.bs.repository.FollowRepository;
import com.stouduo.bs.repository.InboxRepository;
import com.stouduo.bs.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public class PushPullStrategy extends BaseStrategy implements Strategy {
    private int vFollowersCount;

    public PushPullStrategy(int vFollowersCount) {
        this.vFollowersCount = vFollowersCount;
    }

    @Override
    @Async("asyncServiceExecutor")
    public void push(FollowableResource followableResource, Feed feed) {
        if (vFollowersCount >= followableResource.getFollowersCount()) {
            doPush(followableResource, feed);
        }
    }

    @Override
    public List<Feed> pull(String userId, long score, int size) {
        return null;
    }
}
