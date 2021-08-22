package com.chenxiaolani.lecmsend.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Repository;

@Data
@Repository // 加上这个，解决依赖注入的红色警告
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    @JSONField(serialize = false)
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
