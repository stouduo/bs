package com.stouduo.bs.strategy;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.User;

import java.util.List;

public class PushPullStrategy extends PushStrategy {

    @Override
    public List<Feed> pull(User user) {
        return null;
    }
}
