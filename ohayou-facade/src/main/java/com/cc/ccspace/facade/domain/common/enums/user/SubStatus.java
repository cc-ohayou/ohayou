package com.cc.ccspace.facade.domain.common.enums.user;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/10/28 17:18.
 */
public enum SubStatus {
    SUB("1", "sub", "订阅"),
    UNSUB("0", "unsub", "点赞");

    private String value;
    private String dbName;
    private String label;

    SubStatus(String value, String dbName, String label) {
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

    public static String switchToDbName(String value) {
        String dbName = "";
        for (SubStatus sign : SubStatus.values()) {
            if (sign.value.equals(value)) {
                dbName = sign.dbName;
                break;
            }
        }
        return dbName;
    }
}
