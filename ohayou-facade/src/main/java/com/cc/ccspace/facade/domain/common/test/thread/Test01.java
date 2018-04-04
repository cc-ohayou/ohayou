package com.cc.ccspace.facade.domain.common.test.thread;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/18 14:48.
 */
public class Test01  implements Runnable{

    String name;
    public void setName(String name){
        this.name=name;
    }
    @Override
    public void run() {
        for(int i=0;i<100;i++) {
            System.out.println(this.name + "running");
         /*   try {
//                sleep(1000);
            } catch (InterruptedException e) {
            return;
            }*/
        }
    }

    public static void main(String[] args) {

        Test01 t1=new Test01();
        t1.setName("t1");
        Test01 t2=new Test01();
        Thread t=new Thread(t1);
          t2.setName("t2");
        Thread  t02=new Thread(t2);
//        t.setDaemon(true);
        t.setPriority(Thread.NORM_PRIORITY+3);
        t.start();
        t02.start();
      /*  try{
//            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        for(int i=0;i<100;i++)
        {
            System.out.println("main running!");
        }
    }
}
