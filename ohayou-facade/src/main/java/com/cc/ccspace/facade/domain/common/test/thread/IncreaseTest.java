package com.cc.ccspace.facade.domain.common.test.thread;

import java.util.concurrent.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/21 16:36.
 */
public class IncreaseTest {
    public static long beforeOperTime;
   public  static volatile int  a;
//    public static AtomicInteger a=new AtomicInteger(0);
    //workStealing pool 每个线程下面有各自的任务队列，自己的任务执行完毕回去窃取别人的任务执行
    public static ExecutorService stealPool = Executors.newWorkStealingPool(20);
    //缓存型线程池 没有核心线程 线程不够用就创建新的 线程空闲就回收 使用SynchronousQueue 里面的任务不被消耗掉不会进入新的任务
//    public static ExecutorService cachePool = Executors.newCachedThreadPool();
    //定时类线程池 可安一定时间间隔进行 定长的线程池 corePoolSize可初始化
    public static ScheduledExecutorService schedulerPool = Executors.newScheduledThreadPool(5);
//    单一线程的线程池 即只创建唯一的工作者线程来执行任务，如果这个线程异常结束，会有另一个取代它，
//    保证顺序执行(我觉得这点是它的特色)。
//    单工作线程最大的特点是可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的 。
//    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    public static   ExecutorService es=Executors.newFixedThreadPool(20);
    public static ExecutorService cachePool=Executors.newCachedThreadPool();
    {
    }
    public  static void increase(){//不加synchronized的话 是线程不安全的
        synchronized(IncreaseTest.class){ a++;}
//        a.incrementAndGet();
        if(a==1000000) {
         /*   ThreadPoolExecutor o= (ThreadPoolExecutor)es;
            ThreadPoolExecutor cac= (ThreadPoolExecutor)cachePool;
             int cac0= cac.getActiveCount();
             int cac01= cac.getCorePoolSize();
             int cac02= cac.getMaximumPoolSize();
             long cac03= cac.getCompletedTaskCount();
             int cac04= cac.getPoolSize();
             long cac05= cac.getTaskCount();
            long cac06=cac.getLargestPoolSize();

            System.out.println(o.isTerminated());
            int c=o.getActiveCount();//当前正在执行任务的线程数
            int c1=o.getCorePoolSize();//核心线程数
            int c2=o.getLargestPoolSize();//运行过程中线程池达到的实际最大数量
            int c3=o.getMaximumPoolSize();//线程池允许的最大线程数量
            int c4=o.getPoolSize();//线程池当前线程数量
            long c5=o.getCompletedTaskCount();//已执行的任务总数
            long c6= cac.getTaskCount();//线程池已执行和未执行的任务总数
*/
           /* System.out.println("before shutdown "+o.isTerminated());
            o.shutdown();
            System.out.println("before shutdownNow "+o.isTerminated());
            o.shutdownNow();*/

//                Thread.sleep(1000);//会抛异常的 java.lang.InterruptedException: sleep interrupted

            System.out.println("inner用时ms" + (System.currentTimeMillis() - beforeOperTime));
            System.out.println("over!"+"a="+a);
        }
    }

    public static void main(String[] args) throws InterruptedException {
         beforeOperTime=System.currentTimeMillis();
        es.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {

                return null;
            }
        });
       for(int i=0;i<1000000;i++){
           es.execute(new Runnable() {
               @Override
               public void run() {
                   increase();
//                   System.out.println(Thread.currentThread().getName()+":"+a);
               }
           });
       }
     /*  for(int i=0;i<1000000;i++){

           cachePool.execute(new Runnable() {
               @Override
               public void run() {
                   increase();
//                   System.out.println(Thread.currentThread().getName()+":"+a);

               }
           });
       }*/
       /* ThreadPoolExecutor o= (ThreadPoolExecutor)es;

        System.out.println("before shutdown "+o.isTerminated());
        o.shutdown();
        System.out.println("before shutdownNow "+o.isTerminated());
        o.shutdownNow();
        Thread.sleep(1000);
        System.out.println("after shutdownNow "+o.isTerminated());*/
        //并不一定是true 要看队列的所有任务是否执行完毕 workerCount (有效线程数) 是否为0 这样状态后
        //还要再调用terminated()方法 线程池才会最终转入这个状态 所以让主线程sleep()一秒等待线程池完成这些转化


       /* ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("delay 1 seconds, and excute every 3 seconds");
            }
        }, 1, 3, TimeUnit.SECONDS);    }//表示延迟1秒后每3秒执行一次。
*/
        System.out.println("sleep  2 seconds waiting for add over!");
       Thread.sleep(2000);
        ThreadPoolExecutor o= (ThreadPoolExecutor)es;
        System.out.println("before shutdown "+o.isTerminated());
        o.shutdown();
        System.out.println("before shutdownNow "+o.isTerminated());
        o.shutdownNow();
        Thread.sleep(500);
        System.out.println("after shutdownNow "+o.isTerminated());

}
/*

class MyThreadPoolExecutor extends ThreadPoolExecutor{

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        System.out.println(super.getCompletedTaskCount());
    }
}
*/
/*class ThreadPoolExecutorTest {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("delay 1 seconds, and excute every 3 seconds");
            }
        }, 1, 3, TimeUnit.SECONDS);
    }*/
}