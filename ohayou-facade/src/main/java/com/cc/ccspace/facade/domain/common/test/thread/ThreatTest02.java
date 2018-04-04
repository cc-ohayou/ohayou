package com.cc.ccspace.facade.domain.common.test.thread;


import com.cc.ccspace.facade.domain.common.test.design.Singleton;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/18 15:01.
 */
public class ThreatTest02 extends Thread{
    public String tname;

    public void setTName(String name) {
        this.tname = name;
    }
    public void run(){

        System.out.println(tname+"start");
    }

    public static void main(String[] args) {
        //当多个线程共享同一资源的时候，容易出现线程安全问题（数据紊乱的情况）
//解决方法：使用同步synchronized（给对象枷锁），每个对象都有一把锁只在同步中使用
        Station s1=new Station();
        Thread t1=new Thread(s1);
        //三个线程三个 station对象 普通方法的话锁是锁在实例对象上的
        // 去执行sell()方法时 由于锁住了i 所以其他线程需要等等待i被释放，
        // 但由于是不同的锁 执行顺序是没有保障的  因此改为类锁 输出有序（synchronized方法改为static的 锁加在字节码对象上）
      /*
        sell()方法修改为static的输出如下 不需要有序输出的话只需要去掉static即可  恢复线程的正常竞争
        12306售出一票,当前余票5
        东站售出一票,当前余票4
        西站售出一票,当前余票3
        东站售出一票,当前余票2
        12306售出一票,当前余票1
        西站售出一票,当前余票0
        */

        t1.setName("12306");
        Station s2=new Station();
        Thread t2=new Thread(s1);
        t2.setName("西站");
        Station s3=new Station();
        Thread t3=new Thread(s1);
        t3.setName("东站");

              t1.start();
              t2.start();
              t3.start();
        Singleton instance = Singleton.INSTANCE;
        instance.add();


    }

}
