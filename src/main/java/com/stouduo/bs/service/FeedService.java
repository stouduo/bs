package com.stouduo.bs.service;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

public class FeedService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private PublishRepository publishRepository;
    @Autowired
    private LinkRepository linkRepository;

    public void publishFeed(Feed feed) {

    }

}
