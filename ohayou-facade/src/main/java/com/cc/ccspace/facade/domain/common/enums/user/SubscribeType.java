package com.cc.ccspace.facade.domain.common.enums.user;


/**
 * @AUTHOR CF
 * @DATE Created on 2017/10/28 16:30.
 */
public enum SubscribeType {
    SUB("1", "sub", "订阅"),
    THUMB("2", "thumb", "点赞"),
    COLLECT("3", "collect", "收藏");

    private String value;
    private String label;
    private String dbName;

    SubscribeType(String value, String dbName, String label) {
        this.value = value;
        this.label = label;
        this.dbName = dbName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public static String switchToDbName(String type) {
        String dbName = "";
        for (SubscribeType sign : SubscribeType.values()) {
            if (sign.value.equals(type)) {
                dbName = sign.dbName;
                break;
            }
        }
        return dbName;
    }
}
