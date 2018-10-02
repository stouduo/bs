package com.stouduo.bs.config;

import com.stouduo.bs.strategy.PullStrategy;
import com.stouduo.bs.strategy.PushPullStrategy;
import com.stouduo.bs.strategy.PushStrategy;
import com.stouduo.bs.strategy.Strategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EngineConfig {
    @Value("${engine.v.followersCount.min:1000}")
    private int vFollowersCount;


    @Bean
    @ConditionalOnProperty(value = "engine.feed.transform.strategy", havingValue = "push")
    public Strategy pushStrategy() {
        return new PushStrategy();
    }

    @Bean
    @ConditionalOnProperty(value = "engine.feed.transform.strategy", havingValue = "push_pull")
    public Strategy pullPushStrategy() {
        return new PushPullStrategy(vFollowersCount);
    }

    @Bean
    @ConditionalOnMissingBean(Strategy.class)
    public Strategy pullStrategy() {
        return new PullStrategy();
    }

}
