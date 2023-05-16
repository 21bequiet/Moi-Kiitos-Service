package com.align.repository;

import com.align.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class LoginRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    public <T> T save(T t){
        log.info("mongodbDB object save start");
        mongoTemplate.save(t);
        return t;
    }


    public User getUserByName(String name) {
        Query query =  new Query();
        query.addCriteria(Criteria.where("userName").is(name));
        User result = mongoTemplate.findOne(query, User.class);
        return result;
    }
}
