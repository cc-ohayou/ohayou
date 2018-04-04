package com.cc.ccspace.facade.domain.bizobject.common;


import com.cc.ccspace.facade.domain.common.constants.CommonConstants;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/8/6 23:12.
 */
public class Result<T>{
    private int code;//返回结果的代码 0代表请求正常  -1代表请求失败
    private String msg;//返回信息
    private T data;//返回对象
    public Result(int code, String msg, T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
   }
   public Result(T data){
       this(0, CommonConstants.SUCCESS_MSG,data);
   }
    public Result(String msg){
        this(-1, msg,null);
    }
    public Result(){
        this(-1, CommonConstants.FAIL_MSG,null);

    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
