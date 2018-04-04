package com.cc.ccspace.facade.domain.bizobject.task;

/**
 * @AUTHOR FangChunjie
 * @DATE Created on 2017/10/11 10:50.
 */
public class TaskJob {

    private String methodName;//方法名称
    private String startTime;//开始时间
    private String costTime;//耗费时间

    public String getMethodName() { return methodName; }

    public void setMethodName(String methodName) { this.methodName = methodName; }

    public String getStartTime() { return startTime; }

    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getCostTime() { return costTime; }

    public void setCostTime(String costTime) { this.costTime = costTime; }
}
