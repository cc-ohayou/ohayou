package com.cc.ccspace.facade.domain.bizobject.common;


import com.cc.ccspace.facade.domain.common.annotation.Field;
import com.cc.ccspace.facade.domain.common.annotation.Table;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/7 0:41.
 */

@Table(name="BnnDemo")
public class BnnTableDemo {
    @Field(isKey = true,type=" int ",isNull = false,comment=" 编号1 ",len="(11)")
    private String id;
    @Field(isKey = true,type=" int ",isNull = false,comment=" 编号2 ",len="(11)")
    private String id2;
    @Field(isKey = false,type=" char ",isNull = true,comment = " 值 ",len="(200)")
    private String value;

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
