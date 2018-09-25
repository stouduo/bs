package com.stouduo.bs.service;

import com.stouduo.bs.model.Follow;
import com.stouduo.bs.repository.FollowRepository;
import com.stouduo.bs.repository.FollowableResourceRepository;
import com.stouduo.bs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private FollowableResourceRepository followableResourceRepository;
    @Autowired
    private UserRepository userRepository;

    public void follow(String fromId, String toId) {
        if (fromId == toId || fromId.equals(toId)) return;
        Follow follow = new Follow();
        followableResourceRepository.findById(toId).ifPresent(to ->
                userRepository.findById(fromId).ifPresent(from -> {
                    follow.setFrom(from);
                    follow.setTo(to);
                    if (!followRepository.findOne(Example.of(follow)).isPresent()) {
                        followRepository.save(follow);
                        from.setFollowedCount(from.getFollowedCount() + 1);
                        userRepository.save(from);
                        to.setFollowersCount(to.getFollowersCount() + 1);
                        followableResourceRepository.save(to);
                    }
                })
        );
    }

    public void unfollow(String fromId, String toId) {
        if (fromId == toId || fromId.equals(toId)) return;
        Follow follow = new Follow();
        followableResourceRepository.findById(toId).ifPresent(to ->
                userRepository.findById(fromId).ifPresent(from -> {
                    follow.setFrom(from);
                    follow.setTo(to);
                    followRepository.findOne(Example.of(follow)).ifPresent(f -> {
                        followRepository.delete(follow);
                        from.setFollowedCount(from.getFollowedCount() - 1);
                        userRepository.save(from);
                        to.setFollowersCount(to.getFollowersCount() - 1);
                        followableResourceRepository.save(to);
                    });
                })
        );
    }
}
