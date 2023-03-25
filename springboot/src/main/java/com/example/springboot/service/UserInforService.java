package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.FamilyInfor;
import com.example.springboot.entity.UserInfor;
import com.example.springboot.vo.Relation;

public interface UserInforService {
    boolean insertOrUpdate(UserInfor userInfor);
    boolean insert(UserInfor userInfor);
    boolean update(UserInfor userInfor);
    UserInfor selectById(String userId);
    boolean updateFamilyId(String userId,String familyId);
    Page<UserInfor> getList(Integer pageIndex, Integer pageSize, String search, String userId);
    boolean setRelation(Relation relation);


}
