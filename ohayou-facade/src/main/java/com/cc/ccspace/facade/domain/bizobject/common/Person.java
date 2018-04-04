package com.cc.ccspace.facade.domain.bizobject.common;


import com.cc.ccspace.facade.domain.common.annotation.Table;

import java.util.List;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/7 11:19.
 */
@Table(name="person")
public class Person {
    private List<String> urls;
    private String  name;
    private int  age;
    private int vipLevel;//VIP星级
    private Long createTime;//创建时间
    private String  updateTime;//更新时间

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(int vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
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
