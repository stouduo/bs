package com.stouduo.bs.strategy;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.User;
import com.stouduo.bs.repository.FollowRepository;
import com.stouduo.bs.repository.InboxRepository;
import com.stouduo.bs.repository.UserRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public class PushStrategy extends BaseStrategy implements Strategy {
    private int vFollowersCount;

    public PushStrategy(int vFollowersCount, FollowRepository followRepository, InboxRepository inboxRepository, UserRepository userRepository) {
        super(followRepository, inboxRepository, userRepository);
        this.vFollowersCount = vFollowersCount;
    }

    @Override
//    @Async("asyncServiceExecutor")
    public void push(FollowableResource followableResource, Feed feed) {
        if (vFollowersCount >= followableResource.getFollowersCount()) {
            doPush(followableResource, feed);
        }
    }

    @Override
    public List<Feed> pull(User user) {
        return null;
    }
}
