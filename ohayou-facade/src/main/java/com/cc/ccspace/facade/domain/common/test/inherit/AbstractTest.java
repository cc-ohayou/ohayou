package com.cc.ccspace.facade.domain.common.test.inherit;


import com.cc.ccspace.facade.domain.common.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/16 17:58.
 */
 interface Smokeable
{
    public  final static String NIGU="有害";
    void smoke();
}

abstract class A{

    void a(){
        System.out.println("A a()");
    }
      void c(){//private 注意不能用private修饰 这样子类就无法重载  this.c();调用的就不是子类的方法了
        System.out.println("A c()");
    }
    public  abstract  void b();
    void template(){
        this.a();
        this.c();
    }

    public static void main(String[] args) {
        System.out.println("ccc");
    }
}

class B extends   A{
    public void a(int c) {

    }
    @Override
    public void b() {
        System.out.println("class B");

    }
    void a(){
        System.out.println("B a()");
    }
    public  void c(){
        System.out.println("B c()");
    }
}

class C extends  A{

    @Override
    public void b() {
        System.out.println("class c");

    }
}

public abstract class AbstractTest {
public static int cc=00;
    public AbstractTest(){

    }

    public static void test01(A a){
        a.b();
    }
    public static void test02(A a){
        a.template();
    }

    public static void main(String[] args) {
        B b=new B();
        System.out.println(b.getClass());
        test01(b);
        test01(new C());
        test02(b);
        Date d=new Date();
        SimpleDateFormat sdf= (SimpleDateFormat) DateUtil.ymdFormat.get();
        String ss=sdf.format(d);
        System.out.println(ss);
    }
}
