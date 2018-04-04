package com.cc.ccspace.facade.domain.common.constants;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/10/8 11:28.
 */
public class UserRedisKey {
    public static final String PREFIX_USER_REGISTER_VERIFY = "PREFIX_USER_REGISTER_VERIFY";

    // 注册验证码  注册时候只有userName（phone）和商户ID
    public static String USER_REGISTER_VERIFY_KEY(String merchantId, String userName) {
        return "PHOENIX_" + "REGISTER_VERIFY_" + merchantId + "_" + userName;
    }

    //交易密码修改验证码
    public static String USER_TRANSPWD_VERIFY_KEY(String merchantId, String userName) {
        return "PHOENIX_" + "TRANSPWD_VERIFY_" + merchantId + "_" + userName;
    }

    //登录密码修改  两种模式 登录状态下有user_id 非登录 状态
    public static String USER_LOGINPWD_VERIFY_KEY(String merchantId, String userName) {
        return "PHOENIX_" + "LOGINPWD_VERIFY_" + merchantId + "_" + userName;
    }
    //注册验证码输入的错误次数
    public static String USER_REGISTER_VERIFY_ERROR_COUNT_KEY(String merchantId,String userName){
        return "PHOENIX_" + "REGISTER_VERIFY_ERROR_COUNT_" + merchantId + "_" + userName;
    }

    //修改交易密码时输入验证码的错误次数
    public static String USER_TRANSPWD_VERIFY_ERROR_COUNT_KEY(String merchantId,String userName){
        return "PHOENIX_" + "TRANSPWD_VERIFY_ERROR_COUNT_"+merchantId + "_" + userName;
    }

    //修改登录密码时输入验证码的错误次数
    public static String USER_LOGINPWD_VERIFY_ERROR_COUNT_KEY(String merchantId,String userName){
        return "PHOENIX_" + "LOGINPWD_VERIFY_ERROR_COUNT_" + merchantId + "_" + userName;
    }

    //发送验证码次数
    public static String USER_SEND_VERIFY_COUNT_KEY(String merchantId,String userName){
        return "PHOENIX_" + "SEND_VERIFY_COUNT_" + merchantId + "_" + userName;
    }

    public static String USER_SEND_VERIFY_TOTAL_COUNT_KEY(String merchantId,String userName){
        return "PHOENIX_" + "SEND_VERIFY_TOTAL_COUNT_" + merchantId + "_" + userName;
    }
}
