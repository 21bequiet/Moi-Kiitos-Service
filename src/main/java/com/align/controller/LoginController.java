package com.align.controller;

import com.align.entity.User;
import com.align.service.LoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * @author Jason Chen
 * @version 1.0
 * @summary This used to user login and register.
 */

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;

    private static final Base64.Decoder decoder = Base64.getDecoder();

    @RequestMapping("/auth")
    @ResponseBody
    public String login(HttpServletRequest req, HttpServletResponse res) {
        if (!isAuth(req, res)) {
            return "{code: 401, msg: \"no auth\"}";
        }

        return "OK";
    }

    private boolean isAuth(HttpServletRequest req, HttpServletResponse res) {
        String base6AuthStr = req.getHeader("Authorization");
        log.info("base6AuthStr= {}", base6AuthStr); // base6AuthStr=Basic dGVzdDoxMjM0NTY=
        if (base6AuthStr == null) {
            res.setStatus(401);
            res.addHeader("WWW-Authenticate", "basic realm=\"no auth\"");
            return false;
        }
        String authStr = new String(decoder.decode(base6AuthStr.substring(6).getBytes()));
        log.info("authStr= {}", authStr); // authStr=xxx:xxx

        String[] arr = authStr.split(":");
        if (arr != null && arr.length == 2) {
            String userName = arr[0];
            String password = arr[1];
            // verify userName and password
            User user = loginService.getUser(userName);
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return true;
            }
        }
        // if the userName and password verify fail, then return 401 and WWW-Authenticate
        res.setStatus(401);
        res.addHeader("WWW-Authenticate", "basic realm=\"no auth\"");
        return false;
    }

    @GetMapping("/users/{name}")
    public User getUser(@PathVariable("name") String name) {
        return loginService.getUser(name);
    }

    @PostMapping(value = "/register/user")
    public User registerUser(User user) {
        return loginService.registerUser(user);
    }
}
