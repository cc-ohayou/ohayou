package com.cc.ccspace.facade.domain.common.util.logutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/3/23 14:29.
 */
public class RateLimitCheckLog extends BaseLog{
    private Logger logger = LoggerFactory.getLogger(RateLimitCheckLog.class);


    @Override
    public Logger getLogger() {
        return this.logger;
    }
}
