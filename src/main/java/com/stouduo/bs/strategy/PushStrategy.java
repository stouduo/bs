package com.stouduo.bs.strategy;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.User;
import com.stouduo.bs.repository.FollowRepository;
import com.stouduo.bs.repository.InboxRepository;
import com.stouduo.bs.repository.UserRepository;
import com.stouduo.bs.sort.Link;
import com.stouduo.bs.sort.LinkIterator;
import com.stouduo.bs.sort.SimpleSorter;
import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;

public class PushStrategy extends BaseStrategy implements Strategy {

    @Override
    @Async("asyncServiceExecutor")
    public void push(FollowableResource followableResource, Feed feed) {
        doPush(followableResource, feed);
    }

    @Override
    public List<Feed> pull(String userId, long score, int size) {
        List<Link> links = new ArrayList<>();
        userRepository.findById(userId).ifPresent(user -> {
            feedRepository.findById(user.getPublishLink()).ifPresent(feed -> links.add(new Link(feed, getIterator( feed, LinkIterator.LINK_PUBLISH, userId, score))));
            feedRepository.findById(user.getInboxLink()).ifPresent(feed -> links.add(new Link(feed, getIterator( feed, LinkIterator.LINK_INBOX, userId, score))));
        });
        return doPull(links, size);
    }
}
