package com.stouduo.bs.strategy;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.Follow;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.User;
import com.stouduo.bs.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public class PushStrategy implements Strategy {
    //    @Value("${engine.V.followersCount:20000}")
    private int vFollowersCount;

    private FollowRepository followRepository;

    public PushStrategy(int vFollowersCount, FollowRepository followRepository) {
        this.vFollowersCount = vFollowersCount;
        this.followRepository = followRepository;
    }

    @Override
    @Async("asyncServiceExecutor")
    public void push(FollowableResource followableResource, Feed feed) {
        if (vFollowersCount >= followableResource.getFollowersCount()) {
            followRepository.findAll(Example.of(new Follow(followableResource))).forEach(follow -> {

            });
//                    example.setTo(user);
//                    followRepository.findAll(Example.of(example)).forEach(follower -> {
////                        Publish publishEx = new Publish();
////                        User from = new User();
////
// });
        }
    }

    @Override
    public List<Feed> pull(User user) {
        return null;
    }
}
