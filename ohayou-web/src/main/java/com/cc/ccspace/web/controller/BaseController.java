package com.cc.ccspace.web.controller;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/9/19 20:10.
 */
public class BaseController<T> {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);
    private static final Pattern p = Pattern.compile("^[a-zA-Z]+");
    /**
     * @param
     * @return
     * @description 获取http请求中的json参数并转化为map 为防某些参数不是string类型的 遍历转化
     * @author CF create on 2017/6/14 13:28
     */
    public Map<String, String> getRequestBodyParam() {

        Map<String, String> param = new HashMap<>();

        return param;
    }



    protected String getNotifyHost(String merchantHost) {
        Matcher m = p.matcher(merchantHost);
        if (m.find()) {//域名
            return "http://api." + merchantHost;
        } else {
            return "http://" + merchantHost;//测试环境ip
        }
    }

    /**
     * 读取request流
     *
     * @param request
     * @return
     * @author guoyx
     */
    public static String readReqStr(HttpServletRequest request) {
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(request
                    .getInputStream(), "utf-8"));
            String line = null;

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
            } catch (IOException e) {

            }
        }
        return sb.toString();
    }
    protected String json(T t){

        return JSON.toJSONString(t);

    }



}
