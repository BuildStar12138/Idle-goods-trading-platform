package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.UserInfor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserInforMapper extends BaseMapper<UserInfor> {
    @Update("update user_infor set family_id = #{familyId} where user_id = #{userId}")
    int updateFamilyId(String userId,String familyId);

    @Update("update user_infor set father_id = #{fatherId} where user_id = #{userId}")
    int updateFatherId(String userId,String fatherId);

    @Update("update user_infor set mate_id = #{mateId} where user_id = #{userId}")
    int updateMateId(String userId,String mateId);
}
