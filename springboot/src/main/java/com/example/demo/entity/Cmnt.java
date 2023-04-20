package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("cmnt")
@Data
public class Cmnt {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String type;
    private String text;
    private String userid;
    private String exhid;
    private String zlid;
    private String newid;
    private Date date;




}
