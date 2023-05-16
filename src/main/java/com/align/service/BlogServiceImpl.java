package com.align.service;

import com.align.entity.Following;
import com.align.entity.Post;
import com.align.entity.User;
import com.align.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl {
    @Autowired
    private BlogRepository repository;

    /*
       This method used to follow/unfollow users
     */
    public Following follow(Following item) {

        Following searchItem = this.getFollowingByName(item.getUserName());
        item.setId(searchItem.getId());
        return repository.save(item);
    }

    public Post post(Post item) {
        return repository.save(item);
    }

    public List<Post> getAllPosts(String name) {
        List<Post> feedList = new ArrayList<>();
        feedList = repository.getAllPosts(name);

        Following item = this.getFollowingByName(name);
        List<User> followingList = item.getFollowingList();
        for (int i = 0; i < followingList.size(); i++) {
            List<Post> newList = new ArrayList<>();
            String userName = followingList.get(i).getUserName();
            newList = repository.getAllPosts(userName);
            if (newList.size() > 0) {
                feedList.add(newList.get(0));
            }
        }

        return feedList;
    }

    public Following search(String name, String searchName) {

        Following searchItem = this.getFollowingByName(name);
        Following searchList = new Following();
        List<User> searchedFolList = new ArrayList<>();
        searchList.setUserName(name);
        List<User> followingList  = searchItem.getFollowingList();
        for(User user: followingList){
            if (searchName.equals(user.getUserName())){
                searchedFolList.add(user);
               break;
            }
        }
        searchList.setFollowingList(searchedFolList);

        return searchList;
    }


    public Following getFollowingByName(String name) {
        return repository.getFollowingByName(name);
    }

}
