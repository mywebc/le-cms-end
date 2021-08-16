package com.chenxiaolani.lecmsend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY) // 属性值为空的将不会序列化
public class Result<T> {
    private boolean status;
    private String msg;
    private int code;
    T data;
    private String token;

    public Result(int id, boolean status, String msg, int code, T data) {
        this.status = status;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public Result(boolean status, String msg, int code, T data, String token) {
        this.status = status;
        this.msg = msg;
        this.code = code;
        this.data = data;
        this.token = token;
    }
}
