package com.cc.ccspace.test.spring.aop;

import javax.annotation.Resource;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/3/1 15:22.
 */

public class Nurse extends Worker {
    private String name;

    public Nurse(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 通过依赖注入的方式完成 体现依赖注入的好处
     */
    @Resource(name = "nurseTask")
    Task task;

    @Override
    public void work() {
        task.run();
    }
}
