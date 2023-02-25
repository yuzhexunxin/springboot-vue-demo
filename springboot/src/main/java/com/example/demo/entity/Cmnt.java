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
    private  Integer cmntId;
    private String cmntText;
    private Integer cmntUserid;
    private Integer cmntExhid;
    private Date cmntDate;
    private Integer cmntParentid;




}
