package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Zl;
import com.example.demo.mapper.ZlMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/zl")
public class ZlController {
    @Resource
    ZlMapper zlMapper;
    //登录接口
    @PostMapping
    public Result<?> save(@RequestBody Zl zl) {
        zlMapper.insert(zl);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Zl zl) {
        zlMapper.updateById(zl);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable long id) {
        zlMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String keyWord,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Zl> wrapper = Wrappers.<Zl>lambdaQuery();
        if(keyWord.equals("title")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(Zl::getTitle, search);
            }
        }else if(keyWord.equals("floor")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(Zl::getFloor, search);
            }
        }
        Page<Zl> exhPage = zlMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(exhPage);
    }
}
