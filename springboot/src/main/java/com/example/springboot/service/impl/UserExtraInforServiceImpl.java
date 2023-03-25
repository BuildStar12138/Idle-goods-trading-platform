package com.example.springboot.service.impl;

import com.example.springboot.entity.UserExtraInfor;
import com.example.springboot.entity.UserInfor;
import com.example.springboot.mapper.UserExtraInforMapper;
import com.example.springboot.mapper.UserInforMapper;
import com.example.springboot.service.UserExtraInforService;
import com.example.springboot.service.UserInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserExtraInforServiceImpl implements UserExtraInforService {
    @Autowired
    UserExtraInforMapper userExtraInforMapper;

    @Override
    public boolean uppdateExp(String userId, String expriences) {
        UserExtraInfor userExtraInfor = userExtraInforMapper.selectById(userId);
        if(userExtraInfor == null){
            UserExtraInfor userExtraInfor1 = new UserExtraInfor();
            userExtraInfor1.setExperiences(expriences);
            userExtraInfor1.setUserId(userId);
            return userExtraInforMapper.insert(userExtraInfor1) == 1 ? true : false;
        }else{
            return userExtraInforMapper.updateExp(userId,expriences) == 1 ? true : false;
        }
    }

    @Override
    public UserExtraInfor selectById(String userId) {
        return userExtraInforMapper.selectById(userId);
    }
}
