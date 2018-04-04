package com.cc.ccspace.facade.domain.common.exception;

public enum ErrorEnum {
    // 系统
    SYSTEM_ERROR(999999, "系统异常"),
    SMS_ERROR(900001, "短信发送异常"),
    APP_NOT_EXIST(900002,"未找到APP"),
    VISIT_TOO_MUCH(3014,"您的访问太过频繁,喝杯茶休息会吧"),

    /**
     * 用户相关
     */
    AUTH_FAILED(3001, "Token Auth Failed"),
    USER_NOT_EXISTS(3002, "未找到对应用户"),
    USER_ALREADY_EXISTS(3010, "用户已经存在"),
    USER_NOT_HIMSELF(3011, "用户非本人"),
    USER_REGIST_FAILED(3012, "注册用户失败"),



    /**
     * AccountService
     */
    JSON_IS_NULL(6001,"ACCOUNT SERVICE结果返回空");


    private int code;
    private String message;
    private Object[] params;

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    ErrorEnum(int code, String message, Object[] params) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }


}