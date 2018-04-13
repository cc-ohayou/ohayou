package com.cc.ccspace.test.spring.aop;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/3/1 15:25.
 */

public class NursingTask implements Task{
    @Override
    public void run() {
        System.out.println("护士的任务是照顾病人,护士开始照顾病人");
    }
}
