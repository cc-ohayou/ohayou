package com.cc.ccspace.facade.domain.common.test.design;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/22 18:05.
 */
public class SingletonLazy {
    private static volatile SingletonLazy sl;
/*如果没有volatile修饰的话 可能会由于指令重排序等情况
    导致sl已经不是null了但是 初始化没有很好的完成第一个线程挂掉了
    这个sl就错误的被后来的线程拿去使用了
    也就是双检锁的缺陷所在 使用volatile可以避免这个情况
    因为使用了内存屏障 阻止了指令重排序 不会出现没有初始化完成sl却已经不等于null
  */

    private static SingletonLazy getInstance(){
        if(sl==null){
            synchronized(SingletonLazy.class){

                sl=new SingletonLazy();
            }
        }

        return sl;
    }

}
