package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    //这步应该在服务层中写
    @Resource
    UserMapper userMapper;

    @RequestMapping
    public Result save(@RequestBody User user){
        if(user.getPassword() == null)user.setPassword("123456");
        userMapper.insert(user);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody User user){
        userMapper.updateById(user);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        userMapper.deleteById(id);
        return Result.success();
    }


    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StringUtils.isNotBlank(search)){
            wrapper.like(User::getNickName, search);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize),wrapper );
        return Result.success(userPage);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        //System.out.println(user.getUsername()+" "+user.getPassword());
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
        if(res == null){
            return Result.error("-1","用户名或者密码错误");
        }
        return Result.success(res);
    }

    @PostMapping("/backstage")
    public Result backstage(@RequestBody User user){
        //System.out.println(user.getUsername()+" "+user.getPassword());
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()).eq(User::getPower,1));
        if(res == null){
            return Result.error("-1","用户名或者密码错误或者没有权限");
        }
        return Result.success(res);
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(userMapper.selectById(id));
    }

    @GetMapping("/author/{author}")
    public Result<?> getByNickName(@PathVariable String author) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("nick_name",author);
        return Result.success(userMapper.selectList(wrapper));
    }


    @PostMapping("/register")
    public Result register(@RequestBody User user){
        //System.out.println(user.getUsername()+" "+user.getPassword());
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if(res == null){
            user.setNickName(user.getUsername());
            user.setPower(2);
            userMapper.insert(user);
            return Result.success();
        }
        return Result.error("-1","用户名重复");
    }

}
