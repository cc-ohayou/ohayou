package com.cc.ccspace.facade.domain.common.test.thread;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/18 15:45.
 */
public class Station implements Runnable{

    private static int count=6;
    public static synchronized void sell(){
        if(count>0) {
            System.out.println(Thread.currentThread().getName() + "售出一票,当前余票"+(--count));

         }
        }


    @Override
    public void run() {
        for(int i=0;i<10;i++){
            sell();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
