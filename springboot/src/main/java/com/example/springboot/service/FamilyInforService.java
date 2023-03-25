package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.FamilyInfor;
import com.example.springboot.vo.UapdateFamilyInfor;

public interface FamilyInforService {
    int insert(FamilyInfor familyInfor,String userId);
    boolean update(FamilyInfor familyInfor);
    FamilyInfor selectById(String familyId);
    Page<FamilyInfor> getList(Integer pageIndex,Integer pageSize,String search);
    int updateNameAndAddress(UapdateFamilyInfor updateFamilyInfor);
    void addSize(String familyId);
}
