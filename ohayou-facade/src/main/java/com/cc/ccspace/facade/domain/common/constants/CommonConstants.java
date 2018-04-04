package com.cc.ccspace.facade.domain.common.constants;

import java.io.File;

/**
 * Created by Shangdu Lin on 2017/9/25 17:10.
 */
public class CommonConstants {

    /**
     * 用户类型 1、系统 2、前台用户 3、风控 4、后台管理人员
     */
    public static final String USER_TYPE_SYSTEM = "SYSTEM";
    public static final String USER_TYPE_REGISTER = "REGISTER";
    public static final String USER_TYPE_RISK_CONTROLLER = "RISK_CONTROLLER";
    public static final String USER_TYPE_MANAGER = "MANAGER";

    //系统用户
    public static final String USER_SYSTEM = "SYS";

    public static final String USER_AUTO_DEFER = "auto-defer";

    public static final int REGISTER_VERIFY_EXPIRE_TIME = 600;

    public static String CC_RESOURCES_DIR = "CC_RESOURCES_DIR";

    public static final String OS_TYPE_PC = "PC";

    public static final String DEFAULT_WARNING_MESSAGE = "风控禁止买入";


    public static final String AVAILABLE = "1";
    public static final String UN_AVAILABLE = "0";
    public static final String SUCCESS_MSG ="sucess" ;
    public static final String FAIL_MSG ="failed" ;

    /** 数字0 */
    public final static int NUM_ZERO = 0;

    /** 数字1 */
    public final static int NUM_ONE = 1;

    /** 数字2 */
    public final static int NUM_TWO = 2;

    /** 数字3 */
    public final static int NUM_THREE = 3;
    /** 英文符号：冒号 */
    public final static String STR_COMMA = ":";

    /** 英文符号：逗号 */
    public final static String STR_COLON = ",";
    /** 英文符号：点 */
    public final static String STR_DOC = ".";

    /** HTTP参数：接受 */
    public final static String ACCEPT = "Accept";

    /** HTTP参数：接受内容 */
    public final static String ACCEPT_CONTENT = "*/*";

    /** HTTP参数：链接 */
    public final static String CONNECTION = "Connection";

    /** HTTP参数：链接内容 */
    public final static String CONNECTION_CONTENT = "Keep-Alive";

    /** HTTP参数：用户代理 */
    public final static String USER_AGENT = "User-Agent";

    /** HTTP参数：用户代理内容 */
    public final static String USER_AGENT_CONTENT = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)";

    /** 前台内容的数据格式 */
    public final static String CONTENT_TYPE = "text/plain; charset=UTF-8";
    /** 手机号码正则校验字符串 */
    public final static String PHONE_REG = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    /** QQ号码正则校验字符串 */
    public final static String QQ_REG = "[1-9][0-9]{4,14}";

    /** 0或正整数正则校验字符串 */
    public final static String POSITIVE_INTEGER_REG = "^(0|[1-9]\\d*)$";


    /** EXCEL导出头部字段 */
    public static final String EXCEL_FIELD = "field";

    /** EXCEL导出头部字段名称 */
    public static final String EXCEL_FIELD_NAME = "name";

    /** Excel导出文件夹 */
    public static final String EXCEL_EXPORT_DIR = "download" + File.separator
            + "excel" + File.separator;
    //空字符串
    public static final String NOTHING ="" ;

}
