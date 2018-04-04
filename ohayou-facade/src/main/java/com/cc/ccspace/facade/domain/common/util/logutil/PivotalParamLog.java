package com.cc.ccspace.facade.domain.common.util.logutil;


import com.cc.ccspace.facade.domain.common.util.DateUtil;

import java.util.Date;

/**
 * 日志的抽象类父类  使用模板设计模式 调用子类的toKVMiddle()方法 追加一些子类特有的参数
 * 公用的一些参数直接在父类中进行拼接
 *
 * @AUTHOR CF
 * @DATE Created on 2017/3/13 15:24.
 */
public abstract class PivotalParamLog {
    private String reqId;
    private String appName;//应用标识 譬如eask_throwbill
    private String logTime;//记录时间（格式参考 2017-01-01 10:01:29）

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String buildLogFileLineStr() {
        StringBuilder builder = new StringBuilder();
        builder.append("req_id=" + UUIDGenerator.getUUID()).append("&");
        builder.append("appname=" + "cc").append("&");
        String middle = toKVMiddle();
        if (middle != null) {
            builder.append(middle);
        }
        builder.append("logtime=").append(DateUtil.standardFormat(new Date())).append("&");
        return builder.toString();
    }

    //很多类型的消息记录log类都需要用到的方法，用于给子类继承方便使用
    protected abstract String toKVMiddle();
}
