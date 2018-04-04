package com.cc.ccspace.facade.domain.common.util.push;

import com.cc.ccspace.facade.domain.common.constants.PushConstants;

import java.net.URL;

/**
 * HTTPClient EndPoints
 *
 * @author Lynch 2014-09-15
 */
public class EndPoints {

    static final URL TOKEN_APP_URL = HttpClientUtils.getURL(PushConstants.APPKEY.replace("#", "/") + "/token");

    static final URL USERS_URL = HttpClientUtils.getURL(PushConstants.APPKEY.replace("#", "/") + "/users");

    static final URL MESSAGES_URL = HttpClientUtils.getURL(PushConstants.APPKEY.replace("#", "/") + "/messages");
}
