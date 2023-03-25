package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.entity.UserExtraInfor;
import com.example.springboot.entity.UserInfor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserExtraInforMapper extends BaseMapper<UserExtraInfor> {
    @Update("update user_extra_infor set experiences = #{experiences} where user_id = #{userId}")
    int updateExp(String userId,String experiences);
}
