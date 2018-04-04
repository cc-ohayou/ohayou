package com.cc.ccspace.facade.domain.bizobject.common;

public class VersionStatusParam {
    private String id;
    private String beforeStatus;
    private String afterStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBeforeStatus() {
        return beforeStatus;
    }

    public void setBeforeStatus(String beforeStatus) {
        this.beforeStatus = beforeStatus;
    }

    public String getAfterStatus() {
        return afterStatus;
    }

    public void setAfterStatus(String afterStataus) {
        this.afterStatus = afterStataus;
    }
}
