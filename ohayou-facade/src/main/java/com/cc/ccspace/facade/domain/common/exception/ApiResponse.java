package com.cc.ccspace.facade.domain.common.exception;

import java.io.Serializable;

public class ApiResponse implements Serializable {
    //0代表成功
    Integer code=0;
    String msg;
    Object data;

    public static ApiResponse instance(){
        ApiResponse res= new ApiResponse();
        res.setCode(0);
        res.setMsg("");
        res.setData("");
        return new ApiResponse();
    }
    public static ApiResponse error(){
        ApiResponse res= new ApiResponse();
        res.setCode(1);
        res.setMsg("failed");
        res.setData("");
        return res;
    }
    public static ApiResponse success(){
        ApiResponse res= new ApiResponse();
        res.setCode(0);
        res.setMsg("success");
        res.setData("");
        return res;
    }



    public Integer getCode() {
        return code;
    }

    public ApiResponse setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ApiResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ApiResponse setData(Object data) {
        this.data = data;
        return this;
    }
}