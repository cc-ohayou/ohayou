package com.cc.ccspace.facade.domain.common.enums.user;


public enum BankCardStatus {
    SUCCESS("1", "成功"),
    OPERATING("0", "处理中"),
    FAILED("-1", "失败");
    private String value;
    private String label;


    BankCardStatus(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static String switchType(String value) {
        String labelStr = "";
        for (BankCardStatus status : BankCardStatus.values()) {
            if (status.value.equals(value)) {
                labelStr = status.label;
                break;
            }
        }
        return labelStr;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }


}
