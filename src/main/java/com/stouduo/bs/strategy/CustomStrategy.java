package com.stouduo.bs.strategy;

import com.stouduo.bs.model.*;
import com.stouduo.bs.sort.Link;
import com.stouduo.bs.sort.LinkIterator;
import com.stouduo.bs.sort.Sorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class CustomStrategy extends PushPullStrategy {
    public static final String REDIS_PROFIX = "inbox_";
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${engine.redis.user.inbox.size:200}")
    private int inboxSize;
    @Value("${engine.user.followed.count.min:1000}")
    private int minFollowedCount;

    @Value("${engine.v.followersCount.min:1000}")
    public void setVFollowersCount(int vFollowersCount) {
        this.vFollowersCount = vFollowersCount;
    }

    @Override
    public void doPush(FollowableResource followableResource, Feed feed) {
        followRepository.findAll(followableResource.getType() + "/" + followableResource.getId()).forEach(follow -> {
            User fan = follow.getFrom();
            if (!feed.getId().equals(fan.getInboxLink()) && !inboxRepository.findOne(Example.of(new Inbox(feed))).isPresent()) {
                if (!StringUtils.isEmpty(fan.getInboxLink()))
                    inboxRepository.save(new Inbox(feed, new Feed(fan.getInboxLink()), fan.getType() + "/" + fan.getId()));
                fan.setInboxLink(feed.getId());
                fan.setInboxCount(fan.getInboxCount() + 1);
                userRepository.save(fan);
                if (fan.getFollowedCount() >= minFollowedCount) push2Redis(fan.getId(), feed);
            }
        });
    }

    //    @Async("asyncServiceExecutor")
    private void push2Redis(String userId, Feed feed) {
        ZSetOperations<String, String> zsOpt = redisTemplate.opsForZSet();
        zsOpt.add(REDIS_PROFIX + userId, feed.getId(), feed.getCreateTime());
        while (zsOpt.size(REDIS_PROFIX + userId) > inboxSize) {
            zsOpt.removeRange(REDIS_PROFIX + userId, 0, 0);
        }
    }

    private Set<String> pullFromRedis(String userId, long score, int size) {
        return redisTemplate.opsForZSet().reverseRangeByScore(REDIS_PROFIX + userId, -1, score, 0, size);
    }

    @Override
    public List<Feed> pull(String userId, long score, int size) {
        List<Feed> ret = new ArrayList<>();
        List<Link> links = new ArrayList<>();
        userRepository.findById(userId).ifPresent(user -> {
            if (user.getFollowedCount() >= minFollowedCount) {
                List<Feed> temp = pullFromRedis(userId, score, size).parallelStream().map(id ->
                        feedRepository.findById(id).get()).collect(Collectors.toList());
                ret.addAll(temp);
            }
            if (user.getPublishLink() != null)
                feedRepository.findById(user.getPublishLink()).ifPresent(feed -> links.add(new Link(feed, getIterator(feed, LinkIterator.LINK_PUBLISH, userId, score))));
            if (user.getInboxLink() != null)
                feedRepository.findById(user.getInboxLink()).ifPresent(feed -> links.add(new Link(feed, getIterator(feed, LinkIterator.LINK_INBOX, userId, score))));
        });
        userRepository.findAllVFollowers(userId, Follow.class, vFollowersCount).forEach(followableResource -> {
            if (followableResource.getPublishLink() != null)
                feedRepository.findById(followableResource.getPublishLink()).ifPresent(feed -> links.add(new Link(feed, getIterator(feed, LinkIterator.LINK_PUBLISH, followableResource.getId(), score))));
        });
        if (ret.size() < size)
            ret.addAll(doPull(links, size - ret.size()));
        return ret;
    }

}
