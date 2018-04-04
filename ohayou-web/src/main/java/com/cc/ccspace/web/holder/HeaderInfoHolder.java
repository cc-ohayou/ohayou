package com.cc.ccspace.web.holder;

import org.springframework.core.NamedThreadLocal;

/**
 * headerinfo的信息holder类
 */
public abstract class HeaderInfoHolder {

    private static final ThreadLocal<HeaderInfo> headerInfoThreadLocalHolder = new NamedThreadLocal("headinfo");

    public HeaderInfoHolder() {
    }

    public static void setHeaderInfo(HeaderInfo info) {
        headerInfoThreadLocalHolder.set(info);
    }

    public static HeaderInfo getHeaderInfo(){
        return headerInfoThreadLocalHolder.get();
    }

    public static String getToken(){
        return headerInfoThreadLocalHolder.get().getToken();
    }

    public static String getMerchantId(){
        return headerInfoThreadLocalHolder.get().getMerchantid();
    }

    public static String getDeviceId() {
        return headerInfoThreadLocalHolder.get().getDeviceid();
    }

    public static String getUserId(){
        return headerInfoThreadLocalHolder.get().getUserid();
    }

    public static String getSid() {
        return headerInfoThreadLocalHolder.get().getSid();
    }

    public static String getSource() {
        return headerInfoThreadLocalHolder.get().getSource();
    }

    public static String getSoftVersion() {
        return headerInfoThreadLocalHolder.get().getSoftversion();
    }

    public static String getSystemVersion() {
        return headerInfoThreadLocalHolder.get().getSystemversion();
    }


    public static String getClientType() {
        return headerInfoThreadLocalHolder.get().getClienttype();
    }

    public static String getIp() {
        return headerInfoThreadLocalHolder.get().getIp();
    }

    public static String getAppIdentifier() {
        return headerInfoThreadLocalHolder.get().getAppidentifier();
    }

    public static String getPushClientId() {
        return headerInfoThreadLocalHolder.get().getPushclientid();
    }
}
