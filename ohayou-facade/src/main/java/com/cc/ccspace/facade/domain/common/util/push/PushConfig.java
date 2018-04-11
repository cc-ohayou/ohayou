package com.cc.ccspace.facade.domain.common.util.push;


import com.cc.ccspace.facade.domain.common.constants.CommonConstants;
import com.cc.ccspace.facade.domain.common.util.PropertyUtil;

import java.util.HashMap;
import java.util.Map;

public class PushConfig {

    public static Map<String, String> getMap() {
        Map<String, String> params = new HashMap<String, String>();
        PropertyUtil.loadProperties(params, System.getenv(CommonConstants.CC_RESOURCES_DIR) + "/easemob.properties");
        return params;
    }

    public static void main(String[] args) {
        System.out.println(System.getenv(CommonConstants.CC_RESOURCES_DIR));
    }
}
