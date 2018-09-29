package com.stouduo.bs.service;

import com.stouduo.bs.model.Follow;
import com.stouduo.bs.model.FollowableResource;
import com.stouduo.bs.model.User;
import com.stouduo.bs.repository.FollowRepository;
import com.stouduo.bs.repository.FollowableResourceRepository;
import com.stouduo.bs.repository.ResourceRepository;
import com.stouduo.bs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FollowService {
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private FollowableResourceRepository followableResourceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ResourceRepository resourceRepository;

    private void doFollow(User from, FollowableResource to, Follow follow) {
        followRepository.save(follow);
        from.setFollowedCount(from.getFollowedCount() + 1);
        userRepository.save(from);
        to.setFollowersCount(to.getFollowersCount() + 1);
        followableResourceRepository.save(to);
    }

    public void followUser(String fromId, String toId) {
        userRepository.findById(toId).ifPresent(to ->
                userRepository.findById(fromId).ifPresent(from -> {
                    Follow follow = new Follow(from, to);
                    follow.setCreateTime(System.currentTimeMillis());
                    if (!followRepository.findOneByFromAndTo("users/" + from.getId(), "users/" + to.getId()).isPresent()) {
                        doFollow(from, to, follow);
                    }
                })
        );
    }

    public void followResource(String fromId, String toId) {
        resourceRepository.findById(toId).ifPresent(to ->
                userRepository.findById(fromId).ifPresent(from -> {
                    Follow follow = new Follow(from, to);
                    follow.setCreateTime(System.currentTimeMillis());
                    if (!followRepository.findOneByFromAndTo("users/" + from.getId(), "resources/" + to.getId()).isPresent()) {
                        doFollow(from, to, follow);
                    }
                })
        );
    }

    public void unfollowUser(String fromId, String toId) {
        if (fromId == toId || fromId.equals(toId)) return;
        userRepository.findById(toId).ifPresent(to ->
                userRepository.findById(fromId).ifPresent(from -> {
                    Follow follow = new Follow(from, to);
                    followRepository.findOneByFromAndTo("users/" + from.getId(), "users/" + to.getId()).ifPresent(f -> {
                        doUnfollow(from, to, follow);
                    });
                })
        );
    }

    public void unfollowResource(String fromId, String toId) {
        if (fromId == toId || fromId.equals(toId)) return;
        resourceRepository.findById(toId).ifPresent(to ->
                userRepository.findById(fromId).ifPresent(from -> {
                    Follow follow = new Follow(from, to);
                    followRepository.findOneByFromAndTo("users/" + from.getId(), "resources/" + to.getId()).ifPresent(f -> {
                        doUnfollow(from, to, follow);
                    });
                })
        );
    }

    private void doUnfollow(User from, FollowableResource to, Follow follow) {
        followRepository.delete(follow);
        from.setFollowedCount(from.getFollowedCount() - 1);
        userRepository.save(from);
        to.setFollowersCount(to.getFollowersCount() - 1);
        followableResourceRepository.save(to);
    }
}
