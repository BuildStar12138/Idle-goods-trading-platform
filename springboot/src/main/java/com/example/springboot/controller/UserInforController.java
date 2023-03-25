package com.example.springboot.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.springboot.common.Result;
import com.example.springboot.entity.FamilyInfor;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserInfor;
import com.example.springboot.service.UserInforService;
import com.example.springboot.service.UserService;
import com.example.springboot.vo.Query;
import com.example.springboot.vo.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userinfor")
public class UserInforController {
    @Autowired
    UserInforService userInforService;

    /**
     * 插入或者更新,必须有userId
     * @param userInfor
     * @return
     */
    @PostMapping("/insertOrUpdate")
    public Result<UserInfor> insertInfor(@RequestBody UserInfor userInfor){
        boolean result = this.userInforService.insertOrUpdate(userInfor);
        return result == true ? Result.success(userInforService.selectById(userInfor.getUserId())) : Result.error("500","编辑失败");
    }

    /**
     * 获取个人信息
     * @param userId
     * @return
     */
    @GetMapping("/getInfor/{userId}")
    public Result<UserInfor> updateInfor(@PathVariable("userId")String userId){
        return Result.success(userInforService.selectById(userId));
    }

    /**
     * 加入家族
     * @param familyId
     * @return
     */
    @GetMapping("/updateFamilyId/{userId}/{familyId}")
    public Result<String> updateFamilyId(@PathVariable("userId")String userId,
                                         @PathVariable("familyId")String familyId){
        if(userInforService.selectById(userId).getFamilyId() != null){
            return Result.error("500","已加入其他家族");
        }
        return userInforService.updateFamilyId(userId,familyId) ? Result.success("加入成功") : Result.error("500","加入失败");
    }

    /**
     * 查看家族成员列表
     * @param query
     * @return
     */
    @PostMapping("/member/getList")
    public Result<IPage<UserInfor>> getList(@RequestBody Query query){
        return Result.success(userInforService.getList(query.getPageIndex(),query.getPageSize(),query.getSearch(),query.getUserId()));
    }

    /**
     * 添加家族成员，不需要填写userId，自动生成
     * @param userInfor
     * @return
     */
    @PostMapping("/insertMember")
    public Result<String> insertMember(@RequestBody UserInfor userInfor){
//        System.out.println(userInfor);
        UserInfor userInfor1 = userInforService.selectById(userInfor.getUserId());
        userInfor.setFamilyId(userInfor1.getFamilyId());
        userInfor.setUserId(null);
//        System.out.println("-------------------------------------------");
//        System.out.println(userInfor);
        boolean result = this.userInforService.insert(userInfor);
        return result == true ? Result.success("插入成功") : Result.error("500","编辑失败");
    }

    @PostMapping("/member/setRelation")
    public Result<String> setRelation(@RequestBody Relation relation){
        return userInforService.setRelation(relation) ? Result.success("设置成功") : Result.error("500","设置失败");
    }

}
