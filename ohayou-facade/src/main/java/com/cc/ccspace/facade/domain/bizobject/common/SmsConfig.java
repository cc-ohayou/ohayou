package com.cc.ccspace.facade.domain.bizobject.common;

import java.util.List;

/**
 * @AUTHOR chenhui
 * @DATE Created on 2017/12/29 0029 下午 12:56.
 */
public class SmsConfig {
    private String sign;//商户签名
    private List<TemplateCode> templateCodes;//短信模板
    private String accessKeyId;//阿里云短信keyID
    private String secret;//阿里云短信secret

    public String getSign() { return sign; }

    public void setSign(String sign) { this.sign = sign; }

    public List<TemplateCode> getTemplateCodes() { return templateCodes; }

    public void setTemplateCodes(List<TemplateCode> templateCodes) { this.templateCodes = templateCodes; }

    public String getAccessKeyId() { return accessKeyId; }

    public void setAccessKeyId(String accessKeyId) { this.accessKeyId = accessKeyId; }

    public String getSecret() { return secret; }

    public void setSecret(String secret) { this.secret = secret; }
}
