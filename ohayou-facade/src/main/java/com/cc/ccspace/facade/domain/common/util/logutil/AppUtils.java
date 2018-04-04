package com.cc.ccspace.facade.domain.common.util.logutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;

/**
 * 日志工具类
 *
 * @author CF create on 2017/10/20 9:48
 * @description
 */
public class AppUtils {
    private static Logger logger = LoggerFactory.getLogger(AppUtils.class);

    /**
     * 通过反射将对象属性及属性值组装成键值对.
     * 以=和&连接   &链接不同的参数  =链接同一参数的key和value
     *
     * @param className
     * @param object
     * @return 键值对组成的字符串
     */
    public static String buildKVStr(Class<?> className, Object object) {
        StringBuilder builder = new StringBuilder();
        Field[] fields = className.getDeclaredFields();
        String key;
        String value;
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                key = field.getName();
                value = (String) field.get(object);
                builder.append(key).append("=").append(encode(value)).append("&");

            } catch (Exception e) {
                logger.error("buildKVStr error", e);
            }
        }
        return builder.toString();
    }

    /**
     * 特殊字符编码.
     * utf-8
     *
     * @param string
     * @return
     */
    public static String encode(String string) {
        try {
            if (string != null) {
                return URLEncoder.encode(string, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("AppUtils encode error", e);

        }

        return string;
    }

}
