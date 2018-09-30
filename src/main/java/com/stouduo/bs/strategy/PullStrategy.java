package com.stouduo.bs.strategy;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.User;

import java.util.List;

public class PullStrategy extends BaseStrategy implements Strategy {

    @Override
    public void push(FollowableResource followableResource,Feed feed) {
    }

    @Override
    public List<Feed> pull(String userId, long score, int size) {
        return null;
    }
}
