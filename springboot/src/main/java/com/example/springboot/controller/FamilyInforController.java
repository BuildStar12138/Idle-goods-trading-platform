package com.example.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.common.Result;
import com.example.springboot.entity.FamilyInfor;
import com.example.springboot.entity.UserInfor;
import com.example.springboot.service.FamilyInforService;
import com.example.springboot.service.UserInforService;
import com.example.springboot.vo.Query;
import com.example.springboot.vo.UapdateFamilyInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/familyInfor")
public class FamilyInforController {
    @Autowired
    FamilyInforService familyInforService;

    @Autowired
    UserInforService userInforService;
    /**
     * 创建家族
     * @param data
     * @return
     */
    @PostMapping("/insert")
    public Result<String> insertInfor(@RequestParam HashMap<String, String> data){
        FamilyInfor familyInfor1 = JSON.parseObject(data.get("familyInfor"), FamilyInfor.class);
        int result = this.familyInforService.insert(familyInfor1,data.get("userId"));
        return result == 1 ? Result.success("创建成功") : Result.error("500","创建失败");
    }

    @PostMapping("/updateFamily")
    public Result<String> getList(@RequestBody FamilyInfor familyInfor){
        boolean update = familyInforService.update(familyInfor);
        return update ? Result.success("更新成功") : Result.error("500","更新失败");
    }

    @PostMapping("/getList")
    public Result<IPage<FamilyInfor>> getList(@RequestBody Query query){
        return Result.success(familyInforService.getList(query.getPageIndex(),query.getPageSize(),query.getSearch()));
    }

    @GetMapping("/getInfor/{userId}")
    public Result<FamilyInfor> getFamilyInfor(@PathVariable("userId") String userId){
        UserInfor userInfor = userInforService.selectById(userId);
        return userInfor.getFamilyId() != null ? Result.success(familyInforService.selectById(userInfor.getFamilyId())) : Result.error("500","未加入家族");
    }

}
