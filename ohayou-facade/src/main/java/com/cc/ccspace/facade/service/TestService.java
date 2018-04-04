package com.cc.ccspace.facade.service;


import com.cc.ccspace.facade.domain.common.exception.BusinessException;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/2/26 16:43.
 */
public interface TestService {
     String createTableStr(String beanName);
     void test(int mode) throws Exception;
     void consume(int count) throws BusinessException;
     void produce(int count) throws BusinessException;


    String httpTest();
}
