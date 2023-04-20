package com.example.demo.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Cmnt;
import com.example.demo.entity.Order;
import com.example.demo.mapper.OrderMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    OrderMapper orderMapper;
    @PostMapping
    public Result<?> save(@RequestBody Order order) {
        orderMapper.insert(order);
        return Result.success();
    }
    @PutMapping
    public Result<?> update(@RequestBody Order order) {
        orderMapper.updateById(order);
        return Result.success();
    }
    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable long id) {
        orderMapper.deleteById(id);
        return Result.success();
    }
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String keyWord,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Order> wrapper = Wrappers.<Order>lambdaQuery();
        if(keyWord.equals("userid")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(Order::getUserid, search);
            }
        }else if(keyWord.equals("ordercode")) {
            if(StrUtil.isNotBlank(search)) {
                wrapper.like(Order::getOrdercode, search);
            }
        }
        Page<Order> cmntPage = orderMapper.selectPage(new Page<>(pageNum,pageSize), wrapper);
        return Result.success(cmntPage);
    }
}
