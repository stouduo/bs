package com.stouduo.bs.strategy;

import com.stouduo.bs.model.*;
import com.stouduo.bs.repository.FollowRepository;
import com.stouduo.bs.repository.InboxRepository;
import com.stouduo.bs.repository.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.util.StringUtils;

public class BaseStrategy {

    private FollowRepository followRepository;

    private InboxRepository inboxRepository;

    private UserRepository userRepository;

    public BaseStrategy(FollowRepository followRepository, InboxRepository inboxRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.inboxRepository = inboxRepository;
        this.userRepository = userRepository;
    }

    public BaseStrategy() {
    }

    public void doPush(FollowableResource followableResource, Feed feed) {
        followRepository.findAll(followableResource.getType() + "/" + followableResource.getId()).forEach(follow -> {
            User fan = follow.getFrom();
            if (!feed.getId().equals(fan.getInboxLink()) && !inboxRepository.findOne(Example.of(new Inbox(feed))).isPresent()) {
                if (!StringUtils.isEmpty(fan.getInboxLink()))
                    inboxRepository.save(new Inbox(feed, new Feed(fan.getInboxLink()), fan.getType() + "/" + fan.getId()));
                fan.setInboxLink(feed.getId());
                fan.setInboxCount(fan.getInboxCount() + 1);
                userRepository.save(fan);
            }
        });
    }
}
