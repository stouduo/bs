package com.stouduo.bs;

import com.stouduo.bs.model.Feed;
import com.stouduo.bs.model.Publish;
import com.stouduo.bs.model.Resource;
import com.stouduo.bs.model.User;
import com.stouduo.bs.repository.FeedRepository;
import com.stouduo.bs.repository.FollowRepository;
import com.stouduo.bs.service.FeedService;
import com.stouduo.bs.service.FollowService;
import com.stouduo.bs.service.ResourceService;
import com.stouduo.bs.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BsApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private FollowService followService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private FeedService feedService;
    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private FeedRepository feedRepository;

    @Test
    public void testFindAll() {
        followRepository.findAll("users/stouduo").forEach(System.out::println);
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testBatchInsertUser() {
        IntStream.rangeClosed(1001, 2500).forEach(i -> {
            User user = new User();
            user.setId(i + "");
            user.setEmail(String.format("%s%s.@stouduo.com", i, i));
            user.setName(i + "");
            System.out.println(userService.insertUser(user));
        });
    }

    @Test
    public void testBatchFollowUser() {
        IntStream.rangeClosed(1001, 2500).forEach(i -> {
            followService.followUser("stouduo", "" + i);
            followService.followUser("" + i, "stouduo");
        });
    }

    @Test
    public void testBatchPublishFeed() {
        long t = System.currentTimeMillis();
        IntStream.rangeClosed(10001, 15000).forEach(i -> {
            int j = i / 10 + 1;
            Feed feed = new Feed();
            feed.setAuthor(j + "");
            feed.setCreateTime(t);
            feed.setMsg("这是" + j + "第" + i + "条动态！");
            feed.setResource("movie");
            feedService.publish(feed);
        });
    }

    @Test
    public void testInsertUser() {
        User user = new User();
        user.setId("zz");
        user.setEmail("zz.@stouduo.com");
        user.setName("张晨阳");
        System.out.println(userService.insertUser(user));
        user.setId("rb");
        user.setEmail("rb.@stouduo.com");
        user.setName("陈瑞");
        System.out.println(userService.insertUser(user));
        user.setId("jb");
        user.setEmail("jb.@stouduo.com");
        user.setName("陈长军");
        System.out.println(userService.insertUser(user));
        user.setId("stouduo");
        user.setEmail("stouduo.@stouduo.com");
        user.setName("张磊");
        System.out.println(userService.insertUser(user));
    }

    @Test
    public void testFollowUser() {
        followService.followUser("zz", "stouduo");
        followService.followUser("rb", "stouduo");
        followService.followUser("jb", "stouduo");
        followService.followUser("stouduo", "zz");
        followService.followUser("stouduo", "rb");
        followService.followUser("stouduo", "jb");
        followService.followUser("rb", "jb");
        followService.followUser("jb", "rb");
    }

    @Test
    public void testInsertResource() {
        Resource resource = new Resource();
        resource.setId("movie");
        resource.setDes("好看的电影");
        resource.setName("电影");
        resourceService.insertResource(resource);
    }

    @Test
    public void testPublishFeed() {
        long t = System.currentTimeMillis();
        Feed feed = new Feed();
        feed.setAuthor("users/stouduo");
        feed.setCreateTime(t);
        feed.setMsg("《速度与激情13》可太好看了！");
        feed.setResource("resources/movie");
        feedService.publish(feed);
        feed = new Feed();
        feed.setAuthor("users/stouduo");
        feed.setCreateTime(t);
        feed.setMsg("《变形金刚12也很好看！");
        feed.setResource("resources/movie");
        feedService.publish(feed);
    }

    @Test
    public void testPullUserFeeds() {
        feedService.pullUserFeeds("stouduo", 1, 10).getContent().forEach(System.out::println);
    }

    @Test
    public void testPullAllFeeds() {
        long start = System.currentTimeMillis();
        feedService.pullAllFeeds("users/stouduo", 0, 50);
        long end = System.currentTimeMillis();
        System.out.println((end - start));
    }

    @Test
    public void testFindNextN() {
        long start = System.currentTimeMillis();
        IntStream.rangeClosed(1, 10).forEach(i -> {
            feedRepository.findNextN("feeds/283229", 1, "publish", "users/stouduo", 0).forEach(System.out::println);
        });
        long end = System.currentTimeMillis();
        System.out.println((end - start));
    }

    @Test
    public void testFindNextN1() {
        long start = System.currentTimeMillis();
        feedRepository.findNextN("feeds/283229", 10, "publish", "users/stouduo", 0).forEach(System.out::println);
        long end = System.currentTimeMillis();
        System.out.println((end - start));
    }


}
