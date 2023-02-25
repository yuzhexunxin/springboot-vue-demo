package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;
    //登录接口
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()).eq(User::getPassword, user.getPassword()));
        if(res == null) {
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success(res);//
    }
    //注册接口
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserName, user.getUserName()));
        if(res != null) {
            return Result.error("-1","用户名重复");
        }
        if(user.getPassword() == null){
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }
    @PostMapping
    public Result<?> save(@RequestBody User user) {
        if(user.getPassword() == null ) {
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody User user) {
        userMapper.updateById(user);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable long id) {
        userMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String keyWord,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(keyWord.equals("nickname")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(User::getNickname, search);
            }
        }else if(keyWord.equals("sex")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(User::getSex, search);
            }
        }else if(keyWord.equals("address")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(User::getAddress, search);
            }
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(userPage);
    }
}
