package com.cc.ccspace.facade.domain.bizobject.common;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/7/12 14:28.
 */
public class Result implements Serializable {
    private int code;

    private Object data;

    private String msg;

    public Result() {
        this(0, null, "");
    }

    public Result(Object data) {
        this(0, data, "");
    }

    public Result(int code, String message) {
        this(code, null, message);
    }

    public Result(int code, Object data, String message) {
        this.code = code;
        this.data = data != null ? data : new HashMap<String, String>();
        this.msg = message != null ? message : "";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String message) {
        this.msg = message;
    }
}
