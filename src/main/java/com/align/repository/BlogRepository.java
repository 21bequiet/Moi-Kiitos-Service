package com.align.repository;

import com.align.entity.Following;
import com.align.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class BlogRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public <T> T save(T t){
        log.info("mongodb object save start {}",t.getClass());
        mongoTemplate.save(t);
        return t;
    }

    public List<Post> getAllPosts(String name) {
        Query query =  new Query();
        query.addCriteria(Criteria.where("userName").is(name));
        List<Post> result = mongoTemplate.find(query, Post.class);

        return result;
    }


    public Following getFollowingByName(String name) {
        Query query =  new Query();
        query.addCriteria(Criteria.where("userName").is(name));
        Following result = mongoTemplate.findOne(query, Following.class);
        return result;
    }

}
