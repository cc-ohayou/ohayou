package com.cc.ccspace.facade.domain.common.test.gc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/23 11:17.
 */
public class GCTest01 {

private static final int _1MB=1024*1024;
    public static void main(String[] args) throws IllegalAccessException {

        Field unsafe= Unsafe.class.getDeclaredFields()[0];
        unsafe.setAccessible(true);
        Unsafe uf=(Unsafe)unsafe.get(null);
        int i=0;
        while(true){
            System.out.println(++i);
            uf.allocateMemory(_1MB);
        }
    }

}
