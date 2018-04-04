package com.cc.ccspace.facade.domain.common.enums.user;

/**
 * Created by huzhiyong on 2017/9/20.
 */
public enum SmsCodeType {
    SMS_REG(1, "注册发送验证码"),
    SMS_FORGET_PWD(2, "登录状态修改登录密码发送验证码"),
    SMS_FORGET_TRANS_PWD(3, "找回交易密码发送验证码"),
    SMS_FORGET_PWD_UNLOGIN(4, "非登录状态修改密码发送验证码"),;

    private int type;
    private String name;

    SmsCodeType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
