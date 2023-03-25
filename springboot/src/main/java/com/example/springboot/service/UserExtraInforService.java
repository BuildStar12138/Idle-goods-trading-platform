package com.example.springboot.service;

import com.example.springboot.entity.UserExtraInfor;
import com.example.springboot.entity.UserInfor;

public interface UserExtraInforService {
    boolean uppdateExp(String userId,String expriences);
    UserExtraInfor selectById(String userId);
}
