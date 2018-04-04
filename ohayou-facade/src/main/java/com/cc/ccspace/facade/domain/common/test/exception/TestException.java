package com.cc.ccspace.facade.domain.common.test.exception;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/17 15:45.
 */
public class TestException {
public static TestException te=null;
    @Override
      public void finalize() throws Throwable {
        super.finalize();
           System.out.println("finailize gc executed!");
        te=this;
    }
   void isAlive(){
    System.out.println("i'm still alived!");
}
    public static void main(String[] args) throws InterruptedException {
        te = new TestException();
        te = null;
        Thread.sleep(15000);//finalize优先级很低 暂停1秒等待

        System.gc();

        Thread.sleep(2000);//finalize优先级很低 暂停1秒等待
        if (te == null) {
            System.out.println("no i'm dead");
        }
    else{
            te.isAlive();
        }
        te = null;
        System.gc();
        Thread.sleep(1000);
        if (te == null) {
            System.out.println("no i'm dead");
        }
        else{
            te.isAlive();
        }
        //输出结果如下 每个对象的自救只能有效一次 因为系统判断对象可被回收时调用
        /*finailize gc executed!
        i'm still alived!
        no i'm dead*/
    }
    static int test(){

        int x=1;
        try{
            return x;
        }
        finally{
            ++x;
        }
    }
}
