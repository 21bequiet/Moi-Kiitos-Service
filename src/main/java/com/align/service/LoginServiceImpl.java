package com.align.service;

import com.align.entity.User;
import com.align.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl {
    @Autowired
    private LoginRepository repository;

    public User getUser(String name) {
        return repository.getUserByName(name);
    }

    public User registerUser(User user) {
        return repository.save(user);
    }


}
