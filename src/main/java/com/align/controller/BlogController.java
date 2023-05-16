package com.align.controller;

import com.align.entity.Following;
import com.align.entity.Post;
import com.align.service.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    private BlogServiceImpl blogService;

    @PostMapping(value = "/following")
    public Following follow(Following item) {
        return blogService.follow(item);
    }

    @PostMapping(value = "/post")
    public Post post(Post item) {
        return blogService.post(item);
    }

    @RequestMapping(value = "/posts/{name}", method = RequestMethod.GET)
    public List<Post> getAllPost(@PathVariable("name") String name) {
        return blogService.getAllPosts(name);
    }

    @GetMapping(value = "/following/{name} {searchName}")
    public Following follow(@PathVariable("name") String name, @PathVariable("searchName") String searchName) {
        return blogService.search(name,searchName);
    }

}
