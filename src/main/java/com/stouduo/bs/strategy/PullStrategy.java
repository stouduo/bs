package com.stouduo.bs.strategy;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.User;
import com.stouduo.bs.sort.Link;
import com.stouduo.bs.sort.LinkIterator;
import com.stouduo.bs.sort.SimpleSorter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PullStrategy extends BaseStrategy implements Strategy {

    @Override
    public void push(FollowableResource followableResource, Feed feed) {
    }

    @Override
    public List<Feed> pull(String userId, long score, int size) {
        List<Link> links = new ArrayList<>();
        userRepository.findById(userId).ifPresent(user -> feedRepository.findById(user.getPublishLink()).ifPresent(feed -> links.add(new Link(feed, getIterator(feed, LinkIterator.LINK_PUBLISH, userId, score)))));
        followRepository.findAll(userId).forEach(follow -> {
            if (follow.getFrom().getPublishLink() != null)
                feedRepository.findById(follow.getFrom().getPublishLink()).ifPresent(feed -> links.add(new Link(feed, getIterator(feed, LinkIterator.LINK_PUBLISH, follow.getFrom().getId(), score))));
        });
        return doPull(links, size);
    }
}
