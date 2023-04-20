package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("orders")
@Data
public class Order {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userid;
    private String name;
    private Date date;
    private String idcard;
    private String phone;
    private Integer nump;
    private Integer statu;
    private String ordercode;
    private Date dodate;




}
