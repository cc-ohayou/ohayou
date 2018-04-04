package com.cc.ccspace.test.spring.aop;

import javax.annotation.Resource;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/3/1 15:22.
 */

public class Nurse  implements Worker {
    /**
     * 通过依赖注入的方式完成
     */
    @Resource
    Task task;
    @Override
    public void work() {
        task.run();
    }
}
