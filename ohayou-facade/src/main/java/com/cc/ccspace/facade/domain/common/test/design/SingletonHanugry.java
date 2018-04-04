package com.cc.ccspace.facade.domain.common.test.design;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/22 18:02.
 * 饿汉模式 由虚拟机进行同步安全的保障  类加载初始化的时候 会被锁上的 其它的初始化都在队列阻塞
 */
public class SingletonHanugry {
    private static SingletonHanugry sh=new SingletonHanugry();


    private static SingletonHanugry getInstance(){
        return sh;
    }


}
