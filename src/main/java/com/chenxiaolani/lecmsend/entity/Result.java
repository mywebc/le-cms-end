package com.chenxiaolani.lecmsend.entity;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class Result<T> {
    private boolean status;
    private String msg;
    private int code;
    T data;
    private String token;

    public Result(boolean status, String msg, int code, T data) {
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
