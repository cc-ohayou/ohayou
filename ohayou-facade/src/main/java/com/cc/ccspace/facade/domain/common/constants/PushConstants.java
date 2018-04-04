package com.cc.ccspace.facade.domain.common.constants;


import com.cc.ccspace.facade.domain.common.util.push.PushConfig;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/10/27 13:34.
 */
public class PushConstants {

    // API_HTTP_SCHEMA
    public static String API_HTTP_SCHEMA = "https";
    // API_SERVER_HOST
    public static String API_SERVER_HOST = PushConfig.getMap().get("API_SERVER_HOST");
    // APPKEY
    public static String APPKEY = PushConfig.getMap().get("APPKEY");
    // APP_CLIENT_ID
    public static String APP_CLIENT_ID = PushConfig.getMap().get("APP_CLIENT_ID");
    // APP_CLIENT_SECRET
    public static String APP_CLIENT_SECRET = PushConfig.getMap().get("APP_CLIENT_SECRET");
    // DEFAULT_PASSWORD
    public static String DEFAULT_PASSWORD = "123456";

    public static final String targetType = "users";

    /**
     * 个推推送相关配置
     */
    public static final String GETUI_host = "http://sdk.open.api.igexin.com/apiex.htm";
}
