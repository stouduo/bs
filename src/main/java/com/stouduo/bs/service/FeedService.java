package com.stouduo.bs.service;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.Follow;
import com.stouduo.bs.model.Publish;
import com.stouduo.bs.repository.*;
import com.stouduo.bs.strategy.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
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

    public void publish(Feed feed) {
        if (!feedRepository.findOne(Example.of(feed, ExampleMatcher.matchingAll().withIgnorePaths("createTime"))).isPresent()) {
            Feed f = feedRepository.save(feed);
            userRepository.findById(f.getAuthor()).ifPresent(user -> {
                if (!f.getId().equals(user.getPublishLink()) && !publishRepository.findOneByToAndOwner("feeds/" + f.getId(), user.getId()).isPresent()) {
                    if (!StringUtils.isEmpty(user.getPublishLink()))
                        publishRepository.save(new Publish(f, new Feed(user.getPublishLink()), "users/" + user.getId()));
                    user.setPublishCount(user.getPublishCount() + 1);
                    user.setPublishLink(f.getId());
                    userRepository.save(user);
                }
                strategy.push(user, f);
            });
            resourceRepository.findById(f.getResource()).ifPresent(resource -> {
                if (!f.getId().equals(resource.getPublishLink()) && !publishRepository.findOneByToAndOwner("feeds/" + f.getId(), resource.getId()).isPresent()) {
                    if (!StringUtils.isEmpty(resource.getPublishLink()))
                        publishRepository.save(new Publish(f, new Feed(resource.getPublishLink()), "resources/" + resource.getId()));
                    resource.setPublishLink(f.getId());
                    resource.setPublishCount(resource.getPublishCount() + 1);
                    resourceRepository.save(resource);
                }
                strategy.push(resource, f);
            });
        }
    }


//    public List<Feed> browse(String userId, int score) {
//        return
//    }
}
