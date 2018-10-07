package com.stouduo.bs.sort;

import com.stouduo.bs.cache.CacheUnit;
import com.stouduo.bs.cache.LRUCache;
import com.stouduo.bs.model.Feed;
import com.stouduo.bs.repository.FeedRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

@Component("cachedLinkIterator")
@Scope("prototype")
public class CachedLinkIterator extends LinkIterator {
    public static ThreadLocal<LRUCache<String, CacheUnit<Link>>> cache = new ThreadLocal<>();

    public CachedLinkIterator(Feed feed, String edge, String owner, long creatTime) {
        super(feed, edge, owner, creatTime);
    }

    @Override
    public boolean hasNext() {
        LRUCache<String, CacheUnit<Link>> cacheUnit = cache.get();
        if (cacheUnit == null) {
            cacheUnit = new LRUCache<>();
            cache.set(cacheUnit);
        }
        CacheUnit<Link> cacheUnitValue = cacheUnit.getOrDefault(owner, new CacheUnit<>((l1, l2) -> (int) (l2.getHead().getCreateTime() - l1.getHead().getCreateTime())));
        if (!cacheUnitValue.isEmpty()) {
            SortedSet<Link> set = cacheUnitValue.tailSet(new Link(feed, this), false);
            if (!set.isEmpty()) {
                next = set.first();
                return true;
            }
        }
        List<Feed> feeds = feedRepository.findNextN("feeds/" + feed.getId(), 10, edge, owner, creatTime);
        if (feeds.size() > 0) {
            next = new Link(feeds.get(0), this);
            CachedLinkIterator that = this;
            cacheUnitValue.addAll(feeds.stream().map(feed -> {
                return new Link(feeds.get(0), that);
            }).collect(Collectors.toList()));
            cacheUnit.put(owner, cacheUnitValue);
            return true;
        }
        return false;
    }

}