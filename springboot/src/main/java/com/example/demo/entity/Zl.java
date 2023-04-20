package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("zl")
@Data
public class Zl {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content;
    private String floor;
    private Date time;
    private String headimg;
    private String imglist;




}
