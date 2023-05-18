package com.align.service;

import com.align.entity.*;
import com.align.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
public class BlogServiceImpl {
    @Autowired
    private BlogRepository repository;

    @Autowired
    private LoginServiceImpl loginService;

    /*
       This method used to follow/unfollow users
     */
    public FollowDto follow(User target, String loginName) {

        FollowDto dto = new FollowDto();
        Following following = this.updateFollowing(target, loginName);

        User user = new User();
        user.setUserName(loginName);

        Follower follower = this.updateFollower(user, target.getUserName());
        dto.setFollowingList(following.getFollowingList());

        return dto;
    }

    private Following updateFollowing(User item, String loginName) {

        Following following = this.getFollowingByName(loginName);
        if (following == null) {
            following = new Following();
            List<User> followingList = new ArrayList<>();
            item.setEmail(this.getEmailByName(item.getUserName()));
            followingList.add(item);
            following.setFollowingList(followingList);
            following.setUserName(loginName);
            return following;
        }
        // if this login user have followings

        List<User> userlist = following.getFollowingList() == null ?
                Collections.emptyList() : following.getFollowingList();


        Boolean exist = false;
        for (User user : userlist) {
            // if the following list have this userName, means unfollow logic
            if (user.getUserName().equals(item.getUserName())) {
                exist = true;
                userlist.remove(item);
                break;
            }
        }
        if (!exist) {
            item.setEmail(this.getEmailByName(item.getUserName()));
            userlist.add(item);
        }
        following.setFollowingList(userlist);

        repository.save(following);
        return following;
    }

    private String getEmailByName(String name) {
        User targetUser = loginService.getUser(name);
        String email = "";
        if (targetUser != null) {
            email = targetUser.getEmail();
            log.info("The user email is {}", email);
        }
        return email;
    }

    private Follower updateFollower(User target, String name) {
        Follower follower = new Follower();
        List<User> userList = this.getFollowersByName(name);

        Boolean exist = false;
        for (User user : userList) {
            // if the follower list have this userName, means unfollow logic
            if (user.getUserName().equals(name)) {
                exist = true;
                userList.remove(user);
                break;
            }
        }
        if (!exist) {
            User targetUser = loginService.getUser(target.getUserName());
            if (targetUser != null) {
                String email = targetUser.getEmail();
                log.info("The login user email is {}", email);
                target.setEmail(email);
            }
            userList.add(target);
        }
        follower.setUserName(name);
        follower.setFollewerList(userList);
        repository.save(follower);

        return follower;
    }

    public Post postBlog(Post item) {
        item.setCreateTs(LocalDateTime.now());
        return repository.save(item);
    }

    public List<Post> getAllPosts(String name) {

        //get the following user list
        Following following = this.getFollowingByName(name);
        List<Post> feedList = new ArrayList<>();
        if (following != null) {
            List<User> followingList = following.getFollowingList();
            if (followingList != null) {
                List<String> userList = new ArrayList<>();
                followingList.forEach(user -> userList.add(user.getUserName()));
                // add the login user to this following user list
                userList.add(name);
                // get all the posts data
                feedList = repository.getPostsByNameList(userList);
                log.info("The retrieved feed list data {}", feedList);
            }
        }
        return feedList;
    }

    public List<Post> getAllPosts() {
        List<Post> feedList = new ArrayList<>();
        feedList = repository.getPosts();
        return feedList;
    }

    public List<User> getFollowingList(String name) {

        Following searchItem = this.getFollowingByName(name);
        List<User> followingList = new ArrayList<>();
        if (searchItem != null) {
            followingList = searchItem.getFollowingList();
            log.info("The retrieved following list data {}", followingList.size());
        }
        return followingList;
    }

    public List<User> getFollowersByName(String name) {

        Follower searchItem = repository.getFollowerByName(name);
        List<User> followerList = new ArrayList<>();
        if (searchItem != null) {
            followerList = searchItem.getFollewerList();
            log.info("The retrieved follower list data {}", followerList.size());
        }
        return followerList;
    }


    public Following getFollowingByName(String name) {
        return repository.getFollowingByName(name);
    }

    public CountDto getCount(String name) {

        CountDto dto = new CountDto();
        int followingCount = this.getFollowingCount(name);
        int followerCount = this.getFollowerCount(name);
        dto.setFollowingCount(followingCount);
        dto.setFollowerCount(followerCount);

        return dto;
    }

    private Integer getFollowingCount(String name) {

        Following searchItem = this.getFollowingByName(name);
        int count = 0;
        if (searchItem != null) {
            List<User> followingList = searchItem.getFollowingList();
            if (followingList != null && !followingList.isEmpty()) {
                count = followingList.size();
                log.info("The retrieved following list count {}", count);
            }
        }
        return count;
    }

    private Integer getFollowerCount(String name) {
        List<User> followerList = this.getFollowersByName(name);
        int count = 0;
        if (followerList != null) {
            count = followerList.size();
            log.info("The retrieved follower list count {}", count);
        }
        return count;
    }

}
