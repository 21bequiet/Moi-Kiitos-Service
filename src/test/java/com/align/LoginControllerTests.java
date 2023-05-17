package com.align;

import com.align.controller.LoginController;
import com.align.entity.User;
import com.align.service.LoginServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LoginControllerTests {

    @Mock
    LoginServiceImpl loginService;
    @InjectMocks
    LoginController loginController;

    @Test
    public void test() throws Exception {

        User user = new User();
        user.setUserName("Jack");
        user.setEmail("Jack@163.com");
        user.setPassword("123456");

        Mockito.when(loginService.getUser(user.getUserName())).thenReturn(user);

        User item = loginController.getUser(user.getUserName());

        Assert.assertEquals(user.getUserName(), item.getUserName());


    }

}
