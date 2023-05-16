package com.align.controller;

import com.align.entity.User;
import com.align.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;

    @GetMapping(value = "/users/{name}")
    public Boolean login(@PathVariable("name") String name){
        return loginService.getUser(name);
    }

    @PostMapping(value = "/register/user")
    public User registerUser(User user){
        return loginService.registerUser(user);
    }
}
