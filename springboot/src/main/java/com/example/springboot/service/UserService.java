package com.example.springboot.service;

import com.example.springboot.entity.User;

public interface UserService {
    int insertUser(User user);
    User selectByUsername(String username);
    boolean login(String username,String password);
    boolean register(String username,String password);
}
