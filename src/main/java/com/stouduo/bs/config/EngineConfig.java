package com.stouduo.bs.config;

import com.stouduo.bs.repository.FollowRepository;
import com.stouduo.bs.repository.InboxRepository;
import com.stouduo.bs.repository.UserRepository;
import com.stouduo.bs.strategy.PullStrategy;
import com.stouduo.bs.strategy.PushPullStrategy;
import com.stouduo.bs.strategy.PushStrategy;
import com.stouduo.bs.strategy.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EngineConfig {
    @Value("${engine.v.followersCount.min:1000}")
    private int vFollowersCount;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private InboxRepository inboxRepository;
    @Autowired
    private UserRepository userRepository;


    @Bean
    @ConditionalOnProperty(value = "engine.feed.transform.strategy", havingValue = "push")
    public Strategy pushStrategy() {
        return new PushStrategy(vFollowersCount, followRepository, inboxRepository, userRepository);
    }

    @Bean
    @ConditionalOnProperty(value = "engine.feed.transform.strategy", havingValue = "pull_push")
    public Strategy pullPushStrategy() {
        return new PushPullStrategy(vFollowersCount, followRepository, inboxRepository, userRepository);
    }

    @Bean
    @ConditionalOnMissingBean(Strategy.class)
    public Strategy pullStrategy() {
        return new PullStrategy();
    }

}
