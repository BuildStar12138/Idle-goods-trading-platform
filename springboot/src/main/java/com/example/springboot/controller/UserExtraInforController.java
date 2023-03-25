package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.entity.UserExtraInfor;
import com.example.springboot.entity.UserInfor;
import com.example.springboot.service.UserExtraInforService;
import com.example.springboot.service.UserInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userExtraInfor")
public class UserExtraInforController {
    @Autowired
    UserExtraInforService userExtraInforService;

    /**
     * 更新经历
     * @param userId
     * @param experiences
     * @return
     */
    @PostMapping("/updateExp")
    public Result<UserExtraInfor> updateExp(@RequestParam("userId") String userId,
                                            @RequestParam("experiences") String experiences){
        boolean result = this.userExtraInforService.uppdateExp(userId,experiences);
//        System.out.println(userId);
//        System.out.println(experiences);
        return result == true ? Result.success(userExtraInforService.selectById(userId)) : Result.error("500","编辑失败");
    }

    /**
     * 获取个人经历
     * @param userId
     * @return
     */
    @GetMapping("/getExp/{userId}")
    public Result<UserExtraInfor> updateInfor(@PathVariable("userId")String userId){
        return Result.success(userExtraInforService.selectById(userId));
    }

}
