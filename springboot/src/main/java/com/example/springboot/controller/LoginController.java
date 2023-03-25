package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestParam("username")String username,
                              @RequestParam("password")String password){
        boolean result = this.userService.login(username,password);
        return result == true ? Result.success(userService.selectByUsername(username)) : Result.error("500","登录失败");
    }

    @PostMapping("/register")
    public Result<String> register(@RequestParam("username")String username,
                                 @RequestParam("password")String password){
        boolean result = userService.register(username,password);
        return result == true ? Result.success("注册成功") : Result.error("500","注册失败");
    }

}
