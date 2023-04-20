package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("exh")
@Data
public class Exh {
    @TableId(type = IdType.AUTO)
    private  Integer exhId;
    private String exhName;
    private String exhType;
    private String exhImg;
    private String exhAge;
    private String exhText;
    private String floor;
    private float pam1;
    private float pam2;
    private float pam3;
    private float pam4;




}
