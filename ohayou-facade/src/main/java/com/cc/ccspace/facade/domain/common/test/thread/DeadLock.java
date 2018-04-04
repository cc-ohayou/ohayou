package com.cc.ccspace.facade.domain.common.test.thread;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/2 11:38.
 */
public class DeadLock extends Thread{

Object o1;
    Object o2;
    public DeadLock(Object o1,Object o2){
        super();
        this.o1=o1;
        this.o2=o2;
    }
    public void run(){

        synchronized(o1){
            System.out.println(o1);
            synchronized(o2){
                System.out.println(o2);
            }
        }
    }

    public static void main(String[] args) {
        Object o1=new Object();
        Object o2=new Object();
        Thread dl=new DeadLock(o1,o2);
        Thread dl2=new DeadLock2(o1,o2);
        dl.start();
        dl2.start();
    }
}

class DeadLock2 extends Thread{

   Object o1;
   Object o2;
   public DeadLock2(Object o1,Object o2){
       super();
       this.o1=o1;
       this.o2=o2;
   }
   public void run(){

       synchronized(o2){
           System.out.println(o2);
           synchronized(o1){
               System.out.println(o1);
           }
       }
   }


}
