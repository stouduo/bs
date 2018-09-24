package com.stouduo.bs;

import com.stouduo.bs.model.User;
import com.stouduo.bs.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BsApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setEmail("xx.@xxx.com");
        user.setName("xx");
        userService.insertUser(user);
    }

}
