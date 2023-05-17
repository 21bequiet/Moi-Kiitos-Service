package com.align.controller;

import com.align.entity.Following;
import com.align.entity.Post;
import com.align.entity.User;
import com.align.service.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Jason Chen
 * @version 1.0
 * @summary This used to blog function, list post and feed list, etc..
 */
@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogServiceImpl blogService;

    @PostMapping(value = "/following")
    public Following follow(@RequestBody Following item) {
        return blogService.follow(item);
    }

    @PostMapping(value = "/post")
    public Post post(@RequestBody Post item) {
        return blogService.postBlog(item);
    }

    @RequestMapping(value = "/posts/{name}", method = RequestMethod.GET)
    public List<Post> getAllPost(@PathVariable("name") String name) {
        return blogService.getAllPosts(name);
    }

    @GetMapping(value = "/followings/{name} ")
    public List<User> getFollowing(@PathVariable("name") String name) {
        return blogService.getFollowingList(name);
    }

    @GetMapping(value = "/followers/{name} ")
    public List<User> getFollowers(@PathVariable("name") String name) {
        return blogService.getFollowersByName(name);
    }

    @GetMapping(value = "/following/count/{name} ")
    public Integer getFollowingCount(@PathVariable("name") String name) {
        return blogService.getFollowingCount(name);
    }

    @GetMapping(value = "/follower/count/{name} ")
    public Integer getFollowerCount(@PathVariable("name") String name) {
        return blogService.getFollowerCount(name);
    }

}
