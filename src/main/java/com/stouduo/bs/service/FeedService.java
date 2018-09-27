package com.stouduo.bs.service;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.Follow;
import com.stouduo.bs.model.Publish;
import com.stouduo.bs.repository.*;
import com.stouduo.bs.strategy.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.util.StringUtils;

public class FeedService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private PublishRepository publishRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private Strategy strategy;

    public void publishFeed(Feed feed) {
        if (!feedRepository.findOne(Example.of(feed)).isPresent()) {
            Feed f = feedRepository.save(feed);
            Follow example = new Follow();
            userRepository.findById(f.getAuthor()).ifPresent(user -> {
                if (!user.getPublishLink().equals(f.getId()) && !publishRepository.findOne(Example.of(new Publish(f, user.getId()))).isPresent()) {
                    if (!StringUtils.isEmpty(user.getPublishLink())) {
                        publishRepository.save(new Publish(f, new Feed(user.getPublishLink()), user.getId()));
                    }
                    user.setPublishLink(f.getId());
                    userRepository.save(user);
                }
                strategy.push(user, f);
//                if (vUserFollowersCount >= user.getFollowersCount()) {
//                    example.setTo(user);
//                    followRepository.findAll(Example.of(example)).forEach(follower -> {
////                        Publish publishEx = new Publish();
////                        User from = new User();
////                        from.setId(follower.getFrom().getId());
////                        publishEx.setFrom(from);
////                        publishEx.setTo();
////                        if (!publishRepository.findOne(Example.of()))
//                    });
//                }
            });
            resourceRepository.findById(f.getResource()).ifPresent(resource -> {
                if (!resource.getPublishLink().equals(f.getId()) && !publishRepository.findOne(Example.of(new Publish(f, resource.getId()))).isPresent()) {
                    publishRepository.save(new Publish(f, new Feed(resource.getPublishLink()), resource.getId()));
                    resource.setPublishLink(f.getId());
                    resourceRepository.save(resource);
                }
                strategy.push(resource, f);
//                if (vResourceFollowersCount >= resource.getFollowersCount()) {
//                    example.setTo(resource);
//                    followRepository.findAll(Example.of(example)).forEach(follower -> {
//
//                    });
//                }
            });
        }
    }

}
