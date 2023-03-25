package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("user_infor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfor {
    @TableId(value = "user_id",type = IdType.ASSIGN_ID)
    private String userId;
    private String realName;
    private String sex;
    private String familyId;
    private String fatherId;
    private String maritalStatus;
    private String mateId;
    private String career;
    private String address;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date birthday;
}
