package com.align.service;

import com.align.entity.Follower;
import com.align.entity.Following;
import com.align.entity.Post;
import com.align.entity.User;
import com.align.repository.BlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class BlogServiceImpl {
    @Autowired
    private BlogRepository repository;

    /*
       This method used to follow/unfollow users
     */
    public Following follow(User item, String name) {

        Following searchItem = this.getFollowingByName(name);
        // if this login user have followings
        if (searchItem != null) {
            List<User> userlist = searchItem.getFollowingList();
            userlist.add(item);
            searchItem.setFollowingList(userlist);
        } else {// this login user start following 1st user
            searchItem = new Following();
            searchItem.setUserName(name);
            List<User> userlist = new ArrayList<>();
            userlist.add(item);
            searchItem.setFollowingList(userlist);
        }

        return repository.save(searchItem);
    }

    public Post postBlog(Post item) {
        item.setCreateTs(LocalDateTime.now());
        return repository.save(item);
    }

    public List<Post> getAllPosts(String name) {

        //get the following user list
        Following following = this.getFollowingByName(name);
        List<User> followingList = following.getFollowingList();
        List<Post> feedList = new ArrayList<>();
        if (followingList != null) {
            List<String> userList = new ArrayList<>();
            followingList.forEach(user -> userList.add(user.getUserName()));
            // add the login user to this following user list
            userList.add(name);
            // get all the posts data
            feedList = repository.getPostsByNameList(userList);
            log.info("The retrieved feed list data {}", feedList);
        }
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

    public Integer getFollowingCount(String name) {

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

    public Integer getFollowerCount(String name) {
        List<User> followerList = this.getFollowersByName(name);
        int count = 0;
        if (followerList != null) {
            count = followerList.size();
            log.info("The retrieved follower list count {}", count);
        }
        return count;
    }

}
