package com.cc.ccspace.facade.domain.bizobject.common;

/**
 * @AUTHOR chenhui
 * @DATE Created on 2017/12/29 0029 下午 2:47.
 */
public class TemplateCode {
    private String internalCode;//自己系统平台对应的短信模版编号
    private String outCode;//阿里云对应的短信模板编号

    public String getInternalCode() { return internalCode; }

    public void setInternalCode(String internalCode) { this.internalCode = internalCode; }

    public String getOutCode() { return outCode; }

    public void setOutCode(String outCode) { this.outCode = outCode; }
}
