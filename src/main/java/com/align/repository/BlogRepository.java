package com.align.repository;

import com.align.entity.Follower;
import com.align.entity.Following;
import com.align.entity.Post;
import com.align.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason Chen
 * @version 1.0
 * @summary This used to persistence data for blog related.
 */
@Repository
@Slf4j
public class BlogRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public <T> T save(T t) {
        log.info("mongodb object save start {}", t.getClass());
        mongoTemplate.save(t);
        return t;
    }

    public List<Post> getPostsByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(name));
        List<Post> result = mongoTemplate.find(query, Post.class);

        return result;
    }

    public List<Post> getPostsByNameList(List<String> nameList) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").in(nameList));
        query.with(Sort.by(Sort.Direction.DESC,"createTs"));
        List<Post> result = mongoTemplate.find(query, Post.class);

        return result;
    }

    public List<Post> getPosts() {
        return mongoTemplate.findAll(Post.class);
    }

    public Following getFollowingByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(name));
        Following result = mongoTemplate.findOne(query, Following.class);
        return result;
    }

    public Follower getFollowerByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(name));
        Follower result = mongoTemplate.findOne(query, Follower.class);
        return result;
    }

}
