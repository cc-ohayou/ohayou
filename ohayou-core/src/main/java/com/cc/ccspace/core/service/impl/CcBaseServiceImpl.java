package com.cc.ccspace.core.service.impl;


import com.alibaba.fastjson.JSON;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/7 2:06.
 */
public abstract class CcBaseServiceImpl<T> {

    protected String json(T t){

        return JSON.toJSONString(t);

    }

}
