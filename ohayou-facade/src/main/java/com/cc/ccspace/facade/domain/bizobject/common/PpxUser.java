package com.cc.ccspace.facade.domain.bizobject.common;


import com.cc.ccspace.facade.domain.common.annotation.Table;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/7 11:19.
 */
@Table(name="person")
public class PpxUser {
    private String id;
    private String  name;
    private int  age;
    private String sex;//性别 1、男 2、女
    private String status;//有效状态 1、有效 0 无效   默认1',
    private String createTime;//创建时间
    private String  updateTime;//更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
