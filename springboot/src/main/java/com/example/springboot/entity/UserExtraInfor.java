package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("user_extra_infor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExtraInfor {
    @TableId(value = "user_id")
    private String userId;
    private String picturesId;
    private String experiences;
    private String culture;
}
