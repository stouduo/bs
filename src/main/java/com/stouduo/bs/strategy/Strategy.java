package com.stouduo.bs.strategy;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.User;
import com.stouduo.bs.sort.Sorter;

import java.util.List;

public interface Strategy {
    void push(FollowableResource followableResource, Feed feed);

    List<Feed> pull(String userId, long score, int size);

}
