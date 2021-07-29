package com.chenxiaolani.lecmsend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Builder;
import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
}
