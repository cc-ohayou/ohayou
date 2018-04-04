package com.cc.ccspace.facade.domain.common.test.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/22 14:37.
 */
public class LockTest {

    public static void main(String[] args) {
        Lock loc=new ReentrantLock();
        ReentrantReadWriteLock loc2=new ReentrantReadWriteLock();


    }
}
