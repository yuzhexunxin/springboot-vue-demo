package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("news")
@Data
public class News {
    @TableId(type = IdType.AUTO)
    private  Integer newId;
    private String title;
    private String content;
    private String author;
    private Date time;




}
