package com.cc.ccspace.facade.domain.common.test.inner;


/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/17 0:09.
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

class B extends A {
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

public class InnerClassTest {
   int out_x=0;
    public static int cc=5;
    public class OutInner{
            public OutInner(){
                System.out.println("OutInner init");
            }
    }

    public void test(){
        final int[] a = {2};
        class Inner2{

             public void method(){
                out_x=3;
                a[0] =4;
             }
            Inner2 i2=new Inner2();

        }

    }
   public static class StaInner{
        int c=2;
        public void siMethod(){
            System.out.println(c);
        }
public int getC(){
    return c;
}
    }

    public static void main(String[] args) {
        InnerClassTest ict=new InnerClassTest();
      InnerClassTest.OutInner oi=ict.new OutInner();
        System.out.println(ict.getClass());
        System.out.println(oi.getClass());
        InnerClassTest.StaInner is=new StaInner();
        is.siMethod();
        ict.test();
        final int[] d = {9};

        B b2=new B(){
            public  void template(){
                int c=0;
                d[0] =5;
                cc=3;
                System.out.println(cc+d[0]+c);
            }
            public void sleep(){//原来的类中没有这个方法的话则无法调用到的
                System.out.println("sleep");
            }
        };
        b2.template();
        System.out.println(b2.getClass());//class com.cc.ccspace.common.test.inner.InnerClassTest$1 匿名
        Smokeable smoke=new Smokeable()
        {
            public final static String NIGU="hh";//常量无法改变 仍然输出原来接口中的量
            public void smoke()
            {
                System.out.println("吸烟");

            }
            //必须复写接口中所有的方法 属性无所谓的 都是常量

        };
        smoke.smoke();
        String s= smoke.NIGU;

    }

}
