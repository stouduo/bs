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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BaseStrategy {
    public static final int CPU_CORES =   1;
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

    @Value("${engine.pull.strategy.multi:false}")
    protected boolean multi;

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
        if (multi) {
            final int limit = links.size() / CPU_CORES, remain = links.size() % CPU_CORES;
            return Stream.iterate(0, n -> n + 1).limit(CPU_CORES).parallel().map(i -> doPullSingle(links.stream().skip(i * limit + (i < remain ? i : remain)).limit(i < remain ? limit + 1 : limit).collect(Collectors.toList()), size)).
                    flatMap(Collection::stream).sorted((l1, l2) -> {
                int createTime = (int) (l2.getCreateTime() - l1.getCreateTime());
                if (createTime == 0) {
                    return l2.getId().compareTo(l1.getId());
                }
                return createTime;
            }).limit(size).collect(Collectors.toList());
        } else return doPullSingle(links, size);
    }

    public List<Feed> doPullSingle(List<Link> links, int size) {
//        System.out.println(Thread.currentThread().getName() + "---" + links.size());
        List<Feed> result = new ArrayList<>();
        if (links.size() == 1) {
            Link link = links.get(0);
            result.add(link.getHead());
            while (link.hasNext() && result.size() < size) {
                result.add(link.next().getHead());
            }
            return result;
        }
        Sorter<Link> sorter = getSorter();
        links.forEach(link -> sorter.add(link));
        while (!sorter.isEmpty() && result.size() < size) {
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