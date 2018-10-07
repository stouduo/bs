package com.stouduo.bs.strategy;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.Inbox;
import com.stouduo.bs.model.User;
import com.stouduo.bs.repository.FeedRepository;
import com.stouduo.bs.repository.FollowRepository;
import com.stouduo.bs.repository.InboxRepository;
import com.stouduo.bs.repository.UserRepository;
import com.stouduo.bs.sort.Link;
import com.stouduo.bs.sort.Sorter;
import com.stouduo.bs.sort.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class BaseStrategy {

    @Autowired
    protected FollowRepository followRepository;
    @Autowired
    protected InboxRepository inboxRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected FeedRepository feedRepository;
    @Autowired
    protected Provider provider;

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

    public List<Feed> doPull(List<Link> links, int size) {
        List<Feed> result = new ArrayList<>();
        Sorter<Link> sorter = getSorter();
        links.forEach(link -> sorter.add(link));
        while (!sorter.isEmpty() && result.size() <= size) {
            Link link = sorter.pollFirst();
            if (link.hasNext()) sorter.add(link.next());
            result.add(link.getHead());
        }
        return result;
    }

    protected Sorter<Link> getSorter() {
        return provider.getSorter();
    }

    protected Iterator<Link> getIterator(Feed feed, String edge, String owner, long creatTime) {
        return provider.getIterator(feed, edge, owner, creatTime);
    }

}