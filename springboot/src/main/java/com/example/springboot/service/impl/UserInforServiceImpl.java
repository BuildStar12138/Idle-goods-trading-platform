package com.example.springboot.service.impl;

import cn.hutool.db.sql.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.FamilyInfor;
import com.example.springboot.entity.UserInfor;
import com.example.springboot.mapper.UserInforMapper;
import com.example.springboot.service.FamilyInforService;
import com.example.springboot.service.UserInforService;
import com.example.springboot.vo.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInforServiceImpl implements UserInforService {
    @Autowired
    UserInforMapper userInforMapper;

    @Autowired
    FamilyInforService familyInforService;

    @Override
    public boolean insertOrUpdate(UserInfor userInfor) {
        if (userInfor.getUserId() == null)return false;
        if(selectById(userInfor.getUserId()) == null){
            return userInforMapper.insert(userInfor) == 1 ? true : false;
        }else{
            return userInforMapper.updateById(userInfor) == 1 ? true : false;
        }
    }

    @Override
    public boolean insert(UserInfor userInfor) {
        familyInforService.addSize(userInfor.getFamilyId());
        return userInforMapper.insert(userInfor) == 1 ? true : false;
    }

    @Override
    public boolean update(UserInfor userInfor) {
        if (userInfor.getUserId() == null)return false;
        return userInforMapper.updateById(userInfor) == 1 ? true : false;
    }

    @Override
    public UserInfor selectById(String userId) {
        return userInforMapper.selectById(userId);
    }

    @Override
    public boolean updateFamilyId(String userId, String familyId) {
        return userInforMapper.updateFamilyId(userId,familyId) == 1 ? true : false;
    }

    @Override
    public Page<UserInfor> getList(Integer pageIndex, Integer pageSize, String search, String userId) {
        UserInfor userInfor = userInforMapper.selectById(userId);
        if(userInfor.getFamilyId() == null){
            return new Page<UserInfor>();
        }
        LambdaQueryWrapper<UserInfor> wrapper = Wrappers.<UserInfor>lambdaQuery();
        if(search != null){
            wrapper.eq(UserInfor::getFamilyId,userInfor.getFamilyId())
                    .and(i -> {i.like(UserInfor::getRealName,search)
                            .or().like(UserInfor::getAddress,search);});
        }else{
            wrapper.eq(UserInfor::getFamilyId,userInfor.getFamilyId());
        }
        Page<UserInfor> userInforPage = userInforMapper.selectPage(new Page<>(pageIndex, pageSize), wrapper);
        return userInforPage;
    }

    @Override
    public boolean setRelation(Relation relation) {
        int i = 0;
        if (relation.getRelation().equals("father")){
            if(relation.getOption().equals("binding")){
                i = userInforMapper.updateFatherId(relation.getUserId(), relation.getTargetId());
            }else{
                i = userInforMapper.updateFatherId(relation.getUserId(), "");
            }
        }else{
            if(userInforMapper.selectById(relation.getUserId()).getMaritalStatus().equals("未婚"))return false;
            if(userInforMapper.selectById(relation.getUserId()).getMaritalStatus().equals("离异"))return false;
            if(relation.getOption().equals("binding")){
                i = userInforMapper.updateMateId(relation.getUserId(), relation.getTargetId());
                i = userInforMapper.updateMateId(relation.getTargetId(), relation.getUserId());
            }else{
                i = userInforMapper.updateMateId(relation.getUserId(), "");
                i = userInforMapper.updateMateId(relation.getTargetId(), "");
            }
        }
        return i == 1 ? true : false;
    }
}
