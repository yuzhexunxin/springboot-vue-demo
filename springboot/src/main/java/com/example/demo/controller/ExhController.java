package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Exh;
import com.example.demo.mapper.ExhMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/exh")
public class ExhController {
    @Resource
    ExhMapper exhMapper;
    //登录接口
    @PostMapping
    public Result<?> save(@RequestBody Exh exh) {
        exhMapper.insert(exh);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Exh exh) {
        exhMapper.updateById(exh);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable long id) {
        exhMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String keyWord,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Exh> wrapper = Wrappers.<Exh>lambdaQuery();
        if(keyWord.equals("exhName")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(Exh::getExhName, search);
            }
        }else if(keyWord.equals("exhType")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(Exh::getExhType, search);
            }
        }
        Page<Exh> exhPage = exhMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(exhPage);
    }
}
