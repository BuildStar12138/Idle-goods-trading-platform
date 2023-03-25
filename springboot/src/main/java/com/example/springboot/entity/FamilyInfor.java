package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@TableName("family_infor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyInfor {
    @TableId(value = "family_id")
    private String familyId;
    private String familyName;
    private int size;
    private String founder;
    private String manager;
    private String address;
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date createTime;
}
