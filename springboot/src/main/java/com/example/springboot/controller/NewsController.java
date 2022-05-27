package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.News;
import com.example.springboot.mapper.NewsMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    //这步应该在服务层中写
    @Resource
    NewsMapper newsMapper;

    @RequestMapping
    public Result save(@RequestBody News news){
        news.setTime(new Date());
        newsMapper.insert(news);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody News news){
        newsMapper.updateById(news);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        newsMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery();
        if(StringUtils.isNotBlank(search)){
            wrapper.like(News::getTitle, search).or().like(News::getType, search);
        }
        Page<News> newsPage = newsMapper.selectPage(new Page<>(pageNum, pageSize),wrapper );
        return Result.success(newsPage);
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(newsMapper.selectById(id));
    }

    @GetMapping("/author/{author}")
    public Result<?> getByAuthor(@PathVariable String author){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("author",author);
        return Result.success(newsMapper.selectList(wrapper));
    }

}
