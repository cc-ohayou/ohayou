package com.cc.ccspace.facade.domain.common.constants;


import com.cc.ccspace.facade.domain.common.property.PropertyHolder;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/30 22:31.
 */
public enum ActiveMQEnum {

    ACTIVE_MQ_BROKEN_URL((String) PropertyHolder.getContextProperty("activemq.BROKER_URL")),
    ACTIVE_MQ_USERNAME((String)PropertyHolder.getContextProperty("activemq.USERNAME")),
    ACTIVE_MQ_PASSWORD((String)PropertyHolder.getContextProperty("activemq.PASSWORD"));
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    ActiveMQEnum(String value){
        this.value=value;
    }
}
