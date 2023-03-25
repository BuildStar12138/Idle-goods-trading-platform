package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.FamilyInfor;
import com.example.springboot.vo.UapdateFamilyInfor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface FamilyInforMapper extends BaseMapper<FamilyInfor> {
    @Update("update family_infor set family_name = #{familyName},address = #{address} where family_id = #{familyId}")
    int updateNameAndAddress(String familyId,String familyName,String address);

    @Update("update family_infor set size = size + 1 where family_id = #{familyId}")
    void addSize(String familyId);
}
