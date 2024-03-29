package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private  Integer userId;
    private String userName;
    private String password;
    private String nickname;
    private Integer age;
    private String sex;
    private String address;
    private Integer role;
    private String headimg;
    private String email;
    private String phone;

}
