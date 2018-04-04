package com.cc.ccspace.facade.domain.common.test.init;

import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/19 15:55.
 */
public class InitTest01 {
     static int i=0;
    int j=1;
    static{
        System.out.println("i"+i);
    }
    {
        System.out.println("j"+j);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class c0=InitTest01.class;
        c0.getClassLoader().loadClass("com.cc.ccspace.common.test.init.InitTest01");
       /* Class c1=Class.forName("com.cc.ccspace.common.test.init.InitTest01");
        InitTest01 c=new InitTest01();
        Class c2=c.getClass();*/
          Map m=new HashMap();
              m.put(c0,"oooo");
    }
}
