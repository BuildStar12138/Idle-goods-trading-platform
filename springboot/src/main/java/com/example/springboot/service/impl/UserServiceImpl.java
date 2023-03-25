package com.example.springboot.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public int insertUser(User user) {
        if(user.getNickname() == null){
            user.setNickname(user.getUsername());
        }
        return userMapper.insert(user);
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public boolean login(String username,String password){
        User user = selectByUsername(username);
        if(user == null || !password.equals(user.getPassword())){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean register(String username, String password) {
        User user = selectByUsername(username);
        if(user == null){
            User user1 = new User();
            user1.setPassword(password);
            user1.setUsername(username);
            insertUser(user1);
            return true;
        }else{
            return false;
        }
    }
}
