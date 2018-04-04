package com.cc.ccspace.facade.domain.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/10/27 13:42.
 */
public class PropertyUtil {
    static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    public static void loadProperties(Map<String, String> params, String path) {
        if (null == params) {
            params = new HashMap<>();
        }

        try {
            File f = new File(path);
            if (!f.exists()) {
                logger.error(path + "文件不存.");
            }
            InputStream is = new FileInputStream(f);
            Properties prop = new Properties();
            prop.load(is);
            for (Map.Entry<Object, Object> entry : prop.entrySet()) {
                params.put(entry.getKey().toString(), entry.getValue().toString());
            }
        } catch (Exception e) {
            logger.error("！！！！！######load 环信 properties file failed :", e);
        }

    }
}