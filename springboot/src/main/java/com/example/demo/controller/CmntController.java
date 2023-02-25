package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Cmnt;
import com.example.demo.mapper.CmntMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/cmnt")
public class CmntController {
    @Resource
    CmntMapper cmntMapper;
    //登录接口
    @PostMapping
    public Result<?> save(@RequestBody Cmnt cmnt) {
        cmntMapper.insert(cmnt);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Cmnt cmnt) {
        cmntMapper.updateById(cmnt);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable long id) {
        cmntMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String keyWord,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Cmnt> wrapper = Wrappers.<Cmnt>lambdaQuery();
        if(keyWord.equals("cmntText")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(Cmnt::getCmntText, search);
            }
        }else if(keyWord.equals("cmntUserid")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(Cmnt::getCmntUserid, search);
            }
        }else if(keyWord.equals("cmntExhid")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(Cmnt::getCmntExhid, search);
            }
        }
        Page<Cmnt> cmntPage = cmntMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(cmntPage);
    }
}
