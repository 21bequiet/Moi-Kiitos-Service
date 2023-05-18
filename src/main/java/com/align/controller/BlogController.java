package com.align.controller;

import com.align.entity.CountDto;
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

    @PostMapping(value = "/following/{name}")
    public Following follow(@RequestBody User user,@PathVariable("name") String name) {
        return blogService.follow(user, name);
    }

    @PostMapping(value = "/post")
    public Post post(@RequestBody Post item) {
        return blogService.postBlog(item);
    }

    @RequestMapping(value = "/posts/{name}", method = RequestMethod.GET)
    public List<Post> getAllPost(@PathVariable("name") String name) {
        return blogService.getAllPosts(name);
    }

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public List<Post> getAllPost() {
        return blogService.getAllPosts();
    }

    @GetMapping(value = "/followings/{name}")
    public List<User> getFollowing(@PathVariable("name") String name) {
        return blogService.getFollowingList(name);
    }

    @GetMapping(value = "/followers/{name}")
    public List<User> getFollowers(@PathVariable("name") String name) {
        return blogService.getFollowersByName(name);
    }

    @GetMapping(value = "/count/{name}")
    public CountDto getCount(@PathVariable("name") String name) {
        return blogService.getCount(name);
    }


}
