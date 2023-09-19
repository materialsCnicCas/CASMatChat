package com.materials.gpt.utils;

import lombok.Data;

@Data
public class ResponseInfo<T> {

    private String status;

    private String msg;

    private T data;

    private Long total;


    public ResponseInfo(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResponseInfo(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResponseInfo() {
    }

    public ResponseInfo(String status, String msg, Long total,T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.total = total;
    }
}
