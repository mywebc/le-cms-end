package com.chenxiaolani.lecmsend.entity;

import lombok.Data;

@Data
public class Result<T> {
    private boolean status;
    private String msg;
    T data;

    public Result(boolean status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
