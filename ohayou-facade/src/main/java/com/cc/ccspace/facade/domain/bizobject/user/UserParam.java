package com.cc.ccspace.facade.domain.bizobject.user;

/**
 * Created by huzhiyong on 2017/9/20.
 */
public class    UserParam {

    private String merchantId;

    private String userName;

    private String password;

    private String verifyCode;

    private String inviterPhone;

    private String phone;

    private String source;

    private String loginHistoryId;

    private String sid;

//    private Date loginTime;
//
//    private Date logoutTime;

    private String ip;

    private String deviceId;

    private String userId;

    private String spreadCode;

    private String systemVersion;

    private String softVersion;

    private int verifyCodeType;

    private String regSpreadCode;

    private String regInviterPhone;

    private String regRootSpreadCode;

    private String regRootInviterPhone;

    private String pushClientId;

    public String getRegInviterPhone() {
        return regInviterPhone;
    }

    public void setRegInviterPhone(String regInviterPhone) {
        this.regInviterPhone = regInviterPhone;
    }

    public String getRegRootInviterPhone() {
        return regRootInviterPhone;
    }

    public void setRegRootInviterPhone(String regRootInviterPhone) {
        this.regRootInviterPhone = regRootInviterPhone;
    }

    public String getSpreadCode() {
        return spreadCode;
    }

    public void setSpreadCode(String spreadCode) {
        this.spreadCode = spreadCode;
    }

    public String getRegSpreadCode() {

        return regSpreadCode;
    }

    public void setRegSpreadCode(String regSpreadCode) {
        this.regSpreadCode = regSpreadCode;
    }

    public String getRegRootSpreadCode() {
        return regRootSpreadCode;
    }

    public void setRegRootSpreadCode(String regRootSpreadCode) {
        this.regRootSpreadCode = regRootSpreadCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getVerifyCodeType() {
        return verifyCodeType;
    }

    public void setVerifyCodeType(int verifyCodeType) {
        this.verifyCodeType = verifyCodeType;
    }
    //    private Map<String, String> extraMap;
//
//    private Date createTime;
//
//    private Date updateTime;


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getInviterPhone() {
        return inviterPhone;
    }

    public void setInviterPhone(String inviterPhone) {
        this.inviterPhone = inviterPhone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLoginHistoryId() {
        return loginHistoryId;
    }

    public void setLoginHistoryId(String loginHistoryId) {
        this.loginHistoryId = loginHistoryId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }

    public String getPushClientId() {
        return pushClientId;
    }

    public void setPushClientId(String pushClientId) {
        this.pushClientId = pushClientId;
    }
}
