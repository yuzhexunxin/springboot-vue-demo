package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.News;
import com.example.demo.mapper.NewsMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/news")
public class NewsController {
    @Resource
    NewsMapper newsMapper;
    //登录接口
    @PostMapping
    public Result<?> save(@RequestBody News news) {
        news.setTime(new Date());
        newsMapper.insert(news);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody News news) {
        newsMapper.updateById(news);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable long id) {
        newsMapper.deleteById(id);
        return Result.success();
    }
//    @GetMapping("/{id}")
//    public Result<?> getById(@PathVariable Long id) {
//        return Result.success(newsMapper.selectById(id));
//    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String keyWord,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery();
        if(keyWord.equals("title")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(News::getTitle, search);
            }
        }else if(keyWord.equals("author")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(News::getAuthor, search);
            }
        }
        Page<News> newsPage = newsMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(newsPage);
    }
}
