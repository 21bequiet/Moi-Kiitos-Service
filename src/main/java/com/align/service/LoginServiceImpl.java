package com.align.service;

import com.align.entity.User;
import com.align.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl {
    @Autowired
    private LoginRepository repository;

    public Boolean getUser(String name) {
        User existUser = repository.getUserByName(name);
        if (existUser != null && existUser.getPassword().equals(name)) {
            return true;
        }
        return false;
    }

    public User registerUser(User user) {
        return repository.save(user);
    }
}
