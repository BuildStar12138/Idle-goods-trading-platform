package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Book;
import com.example.springboot.mapper.BookMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class BookController {
    //这步应该在服务层中写
    @Resource
    BookMapper bookMapper;

    @RequestMapping
    public Result save(@RequestBody Book book){
        bookMapper.insert(book);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Book book){
        bookMapper.updateById(book);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        bookMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "10") Integer pageSize,
                           @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<Book> wrapper = Wrappers.<Book>lambdaQuery();
        if(StringUtils.isNotBlank(search)){
            wrapper.like(Book::getName, search);
        }
        Page<Book> bookPage = bookMapper.selectPage(new Page<>(pageNum, pageSize),wrapper );
        return Result.success(bookPage);
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return Result.success(bookMapper.selectById(id));
    }

}
