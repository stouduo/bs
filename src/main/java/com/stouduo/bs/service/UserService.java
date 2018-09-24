package com.stouduo.bs.service;

import com.stouduo.bs.model.User;
import com.stouduo.bs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void insertUser(User user) {
        System.out.println(userRepository.save(user).toString());
    }
}