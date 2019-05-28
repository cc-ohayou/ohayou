package com.cc.ccspace.facade.domain.common.test.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/3/2/002 18:40.
 */
public class ConcurrentUtilTest {





    public static void main(String[] args) {
        testCountDownLatch();
        ConcurrentUtilTest test=new ConcurrentUtilTest();
        /*try {
            test.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }


    public static void testCountDownLatch(){

        Executor executor= Executors.newFixedThreadPool(2);

        CountDownLatch latch=new CountDownLatch(4);
        Service service;
        for(int i=0;i<4;i++){
            service=new HealthService(latch,String.valueOf(i));
            executor.execute(service);
        }

        try {
//            中断线程则主线程调用latch.await方法会报错 因为内部会进行线程中断状态的校验
            //一旦发现中断则不再进入 排队等待队列一系列的判断
//            Thread.currentThread().interrupt();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("all service start");

    }
}
abstract class Service implements  Runnable{
    private CountDownLatch latch;
    public Service(CountDownLatch latch) {
        this.latch=latch;
    }

    @Override
    public void run() {
        try {
            execute();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            latch.countDown();
        }
    }

    public abstract void execute() ;
}


class HealthService  extends Service{
  private String serviceName;
    public HealthService(CountDownLatch latch,String name) {
        super(latch);
        this.serviceName=name;
    }

    @Override
    public void execute() {
        try {
            System.out.println("thread:"+Thread.currentThread().getName()+"health service "+serviceName+" start ");
            Thread.sleep(2);
            System.out.println("thread:"+Thread.currentThread().getName()+"health service "+serviceName+" over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

 class Test {
    public static void main(String[] args) {
        int N = 4;

        AtomicInteger count = new AtomicInteger(6);
        System.out.println(count.getAndIncrement());
        CyclicBarrier barrier  = new CyclicBarrier(N);
        for(int i=0;i<N;i++)
            new Writer(barrier).start();
    }
    static class Writer extends Thread{
        private CyclicBarrier cyclicBarrier;
        public Writer(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
            try {
                Thread.sleep(5000);      //以睡眠来模拟写入数据操作
                System.out.println("线程"+Thread.currentThread().getName()+"第一代写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
                System.out.println("线程"+Thread.currentThread().getName()+"第一代写入完毕，继续处理其他任务...");
                System.out.println("线程"+Thread.currentThread().getName()+"第二代写入数据完毕，等待其他线程写入完毕");
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(BrokenBarrierException e){
                e.printStackTrace();
            }
            System.out.println("所有线程第二代写入完毕，继续处理其他任务...");
        }
    }
}