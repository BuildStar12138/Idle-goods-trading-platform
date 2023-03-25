package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.FamilyInfor;
import com.example.springboot.entity.UserInfor;
import com.example.springboot.mapper.FamilyInforMapper;
import com.example.springboot.mapper.UserInforMapper;
import com.example.springboot.service.FamilyInforService;
import com.example.springboot.vo.UapdateFamilyInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FamilyInforServiceImpl implements FamilyInforService {
    @Autowired
    FamilyInforMapper familyInforMapper;
    @Autowired
    UserInforMapper userInforMapper;

    @Override
    public int insert(FamilyInfor familyInfor,String userId) {
        UserInfor userInfor = userInforMapper.selectById(userId);
        if(userInfor.getFamilyId() != null){
            return 0;
        }else{
            familyInfor.setFamilyId(userId);
            familyInfor.setFounder(userInfor.getRealName());
            familyInfor.setManager(userInfor.getRealName());
            familyInfor.setCreateTime(new Date());
            familyInfor.setSize(1);
            int i = userInforMapper.updateFamilyId(userId, userId);
            if(i == 1){
                return familyInforMapper.insert(familyInfor);
            }else{
                return 0;
            }
        }
    }

    @Override
    public boolean update(FamilyInfor familyInfor) {
        if (familyInfor.getFamilyId() == null)return false;
        return familyInforMapper.updateById(familyInfor) == 1 ? true : false;
    }

    @Override
    public FamilyInfor selectById(String familyId) {
        return familyInforMapper.selectById(familyId);
    }

    @Override
    public Page<FamilyInfor> getList(Integer pageIndex, Integer pageSize, String search) {
        LambdaQueryWrapper<FamilyInfor> wrapper = Wrappers.<FamilyInfor>lambdaQuery();
        if(search != null){
            wrapper.eq(FamilyInfor::getFamilyId,search)
                    .or(i -> {i.like(FamilyInfor::getFamilyName,search);})
                    .or(i -> {i.like(FamilyInfor::getAddress,search);});
        }
        Page<FamilyInfor> familyInforPage = familyInforMapper.selectPage(new Page<>(pageIndex, pageSize), wrapper);
        return familyInforPage;
    }

    @Override
    public int updateNameAndAddress(UapdateFamilyInfor updateFamilyInfor) {
        return familyInforMapper.updateNameAndAddress(updateFamilyInfor.getFamilyId(),
                updateFamilyInfor.getFamilyName(),
                updateFamilyInfor.getAddress());
    }

    @Override
    public void addSize(String familyId) {
        familyInforMapper.addSize(familyId);
        return ;
    }
}
