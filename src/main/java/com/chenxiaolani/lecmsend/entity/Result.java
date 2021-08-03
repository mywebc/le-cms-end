package com.chenxiaolani.lecmsend.entity;

import lombok.Data;

@Data
public class Result<T> {
    private boolean status;
    private String msg;
    private int code;
    T data;

    public Result(boolean status, String msg, int code, T data) {
        this.status = status;
        this.msg = msg;
        this.code = code;
        this.data = data;
    }
}
