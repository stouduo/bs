package com.stouduo.bs.strategy;

import com.stouduo.bs.model.*;
import com.stouduo.bs.repository.FollowRepository;
import com.stouduo.bs.repository.InboxRepository;
import com.stouduo.bs.repository.UserRepository;
import com.stouduo.bs.sort.Link;
import com.stouduo.bs.sort.LinkIterator;
import com.stouduo.bs.sort.SimpleSorter;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PushPullStrategy extends BaseStrategy implements Strategy {
    protected int vFollowersCount;

    public PushPullStrategy() {
    }

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
        List<Link> links = new ArrayList<>();
        userRepository.findById(userId).ifPresent(user -> {
            if (user.getPublishLink() != null)
                feedRepository.findById(user.getPublishLink()).ifPresent(feed -> links.add(new Link(feed, getIterator(feed, LinkIterator.LINK_PUBLISH, userId, score))));
            if (user.getInboxLink() != null)
                feedRepository.findById(user.getInboxLink()).ifPresent(feed -> links.add(new Link(feed, getIterator(feed, LinkIterator.LINK_INBOX, userId, score))));
        });
        userRepository.findAllVFollowers(userId, Follow.class, vFollowersCount).forEach(followableResource -> {
            if (followableResource.getPublishLink() != null)
                feedRepository.findById(followableResource.getPublishLink()).ifPresent(feed -> links.add(new Link(feed, getIterator(feed, LinkIterator.LINK_PUBLISH, followableResource.getId(), score))));
        });
        return doPull(links, size);
    }
}
