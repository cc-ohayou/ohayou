package com.cc.ccspace.facade.domain.common.test.thread;


import com.cc.ccspace.facade.domain.common.util.JedisUtil;

import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/6/16 15:03.
 */
public class ThreadPoolTest {
//    Logger logger= LoggerFactory.getLogger(this.getClass());
    static ExecutorService service = Executors.newFixedThreadPool(40);
    private static volatile Integer  count;
    private static boolean THREAD_STOP_GLOBAL_SIGN=false;
    private static String interruptSign="interruptSign";
    private static String taskNum="taskNum";
    private static String taskStatus="taskStatus";
    private static String taskParam="taskParam";
    private static final String  FAILED="-1";
    private static final String  SUCCESS="1";
    /**
    * @description  n使用submit方法提交线程任务会对传入的任务进行FutureTask的包装
    * @author CF create on 2017/7/28 15:15
    * @param
    * @return
    */
  public static void testFuture(){
       List list=new ArrayList<>();
      for (int i = 0; i < 100; i++) {
            Future f=(Future) service.submit((Callable<Object>) () -> {
                System.out.println("lala");
                count++;
             return count;
            });
          list.add(f);
      }
      System.out.println(list.get(0));

  }
    /**
     * <一>
     * 1. 用List收集任务结果 (List记录每个submit返回的Future)
     * 2. 循环查看结果, Future不一定完成, 如果没有完成, 那么调用get会租塞
     * 3. 如果排在前面的任务没有完成, 那么就会阻塞, 这样后面已经完成的任务就没法获得结果了, 导致了不必要的等待时间.
     *    更为严重的是: 第一个任务如果几个小时或永远完成不了, 而后面的任务几秒钟就完成了, 那么后面的任务的结果都将得不到处理
     *
     * 导致: 已完成的任务可能得不到及时处理
     */
    private static void case1() throws ExecutionException, InterruptedException {
        final Random random = new Random();
        List<Future<String>> taskResultHolder = new ArrayList<>();
        for(int i=0; i<50; i++) {
            //搜集任务结果
            taskResultHolder.add(service.submit(() -> {
                Thread.sleep(random.nextInt(5000));
                return Thread.currentThread().getName();
            }));
        }
        // 处理任务结果
        int count = 0;
        System.out.println("handle result begin");
        for(Future<String> future : taskResultHolder) {
            System.out.println(future.get());
            count++;
        }
        System.out.println("handle result end");
        System.out.println(count + " task done !");

        //关闭线程池
        service.shutdown();
    }
    /**
     * <二> 只对第一种情况进行的改进
     *      1. 查看任务是否完成, 如果完成, 就获取任务的结果, 让后重任务列表中删除任务.
     *      2. 如果任务未完成, 就跳过此任务, 继续查看下一个任务结果.
     *      3. 如果到了任务列表末端, 那么就从新回到任务列表开始, 然后继续从第一步开始执行
     *
     *      这样就可以及时处理已完成任务的结果了
     */
    private static void case2()   {
        ExecutorService service = Executors.newFixedThreadPool(10);
        List results = new ArrayList<>();
        Map param=new HashMap();
        for(int i=0; i<30; i++) {
            Callable<Map<String,Object>> task = new Callable<Map<String, Object>>() {
                @Override
                public Map<String, Object> call() throws Exception {
                    Map<String, Object> result = taskBody(param);//result 必然返回 纪录有任务的执行成功与否和是否被终止的（人为、被动）情况
                    return result;
                }
            };
//            FutureTask ft=new FutureTask(task);
              FutureTask ft=(FutureTask)service.submit(task);
            // 提交给线程池执行任务，也可以通过 service.invokeAll(taskList)一次性提交所有任务;
            results.add(ft); // 搜集任务结果
        }
        dealThreadTask(results);
        //线程池使用完必须关闭
        service.shutdown();
    }
  /**
      * @description  处理异步线程任务
      * @author CF create on 2017/7/28 17:07
      * @param
      * @return
      */
    private static void dealThreadTask(List results) {
        int count = 0;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //自旋, 获取结果
        System.out.println("handle result begin");
        for(int i=0; i<results.size(); i++) {
            FutureTask taskHolder = (FutureTask) results.get(i);
                   /* if(i==15){
                        taskHolder.cancel(true);//此处若取消成功 则下面再次调用get方法则会抛异常
                        //Exception in thread "main" java.util.concurrent.CancellationException
                        //可以利用这种特性检测到一些异常（人为主动中断或者程序遇到问题自己主动中断）没有执行的任务 进而进行判断对这些任务再次进行处理 直到成功为止
                        continue;
                    }*/
            if(taskHolder.isDone()) { //任务完成
                Object result = null;//获取结果, 进行某些操作  任务没有结束时则阻塞等待
                try {
                    result = taskHolder.get();//有个弊端 这个方法会阻塞 所以最好换种方式
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println("result: " + result);
                Map<String,Object> res=(Map)result;
                if(FAILED.equals(res.get(taskStatus))){
                    System.out.println("任务："+res.get(taskNum)+"执行 失败");
                }
                if(false== (boolean) res.get(interruptSign)){
                    System.out.println("任务："+res.get(taskNum)+"执行 失败");
                }
                results.remove(taskHolder);
                i--;
                count++; //完成的任务的计数器
            }
            //回到列表开头, 从新获取结果
            if(i == results.size() - 1) i = -1;
        }
        System.out.println("handle result end");
        System.out.println(count + " task done !");
    }
  /**
      * @description  任务执行主体
      * @author CF create on 2017/7/28 17:14
      * @param
      * @return
      */
    private static Map<String,Object> taskBody(Map param) throws InterruptedException {
        Map<String,Object> result=new HashMap(4);
        boolean flag=false;
        Long num=0L;
        try{
            Random random = new Random();
            int tm=random.nextInt(5000);
           num= JedisUtil.incr("cctest");
        if(num==15){
            THREAD_STOP_GLOBAL_SIGN=true;//15执行过之后的线程都不会被执行
            System.out.println("task can't run now ");
//                        Thread.currentThread().interrupt();//线程自我中断
            //譬如处理业务时 遇到未知异常可以手动进行捕捉 除了记录必要日志以外
            // 更可以借此进行自我中断，抛出中断异常  在返回的task集合中遍历进行get()获取返回结果
            //抛出此类异常的任务会被筛选出来  放入队列中进行再次执行直到成功为止
        }
                 /*   System.out.println("线程："+num+
                            "thread.currentThread().getName():+"+tm);*/
//                    System.out.println("thread.currentThread().getName():+"+tm);
        //线程被中断后继续调用sleep方法会报这个错误 java.lang.InterruptedException: sleep interrupted
        if(!Thread.currentThread().isInterrupted()&&!THREAD_STOP_GLOBAL_SIGN){
            //定义一个全局的标志可以自如的控制线程的运行 但凡还没有运行的任务 修改了这个之后
            System.out.println("线程"+num+"开始执行");
            Thread.sleep(tm); //模拟耗时操作
            flag=true;
        }
            result.put(interruptSign,flag);
            result.put(taskNum,num);
            result.put(taskParam,param);
            result.put(taskStatus,SUCCESS);
        }catch(Exception e){
            result.put(interruptSign,flag);
            result.put(taskNum,num);
            result.put(taskParam,param);
            result.put(taskStatus,FAILED);
        }
        return result;
    }
  /**
      * @description  停止进行测试
      * @author CF create on 2017/7/28 16:46
      * @param
      * @return
      */
    public static void stopThreadTest(){
       FutureTask<?> futureTask = new FutureTask<String>(new Callable<String>() {
           @Override
           public String call() throws Exception {
               for(int i=0;i<10000&&!Thread.currentThread().isInterrupted();i++){
                   System.out.println(i);
               }
               return null;
           }
       });
       service.execute(futureTask);
       System.out.println("futureTask start");
       try {
           Thread.sleep(10);
       } catch (InterruptedException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       futureTask.cancel(true);
       System.out.println("futureTask cancel");
   }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        testFuture();
//          case2();//  可行的中断线程并记录线程执行结果的方案
//        stopThreadTest();

        System.out.println(getNotifyHost());
    }
    private static String getNotifyHost() {
        String merchantHost="sscxian.com";//;"118.31.74.51";
        Pattern p = Pattern.compile("^[a-zA-Z]+");
        Matcher m = p.matcher(merchantHost);
        if (m.find()) {//域名
            return "http://api." + merchantHost;
        } else {
            return "http://" + merchantHost;//测试环境ip
        }
    }


    /**
     * <三> 使用ExecutorCompletionService管理异步任务
     * 1. Java中的ExecutorCompletionService<V>本身有管理任务队列的功能
     *    i. ExecutorCompletionService内部维护列一个队列, 用于管理已完成的任务
     *    ii. 内部还维护列一个Executor, 可以执行任务
     *
     * 2. ExecutorCompletionService内部维护了一个BlockingQueue, 只有完成的任务才被加入到队列中
     *
     * 3. 任务一完成就加入到内置管理队列中, 如果队列中的数据为空时, 调用take()就会阻塞 (等待任务完成)
     *    i. 关于完成任务是如何加入到完成队列中的, 请参考ExecutorCompletionService的内部类QueueingFuture的done()方法
     *
     * 4. ExecutorCompletionService的take/poll方法是对BlockingQueue对应的方法的封装, 关于BlockingQueue的take/poll方法:
     *    i. take()方法, 如果队列中有数据, 就返回数据, 否则就一直阻塞;
     *    ii. poll()方法: 如果有值就返回, 否则返回null
     *    iii. poll(long timeout, TimeUnit unit)方法: 如果有值就返回, 否则等待指定的时间; 如果时间到了如果有值, 就返回值, 否则返回null
     *
     * 解决了已完成任务得不到及时处理的问题
     */
    static void case3() throws InterruptedException, ExecutionException {
        Random random = new Random();
        int size=50;
         List  list=new ArrayList(size);
        ExecutorService service = Executors.newFixedThreadPool(10);
        ExecutorCompletionService<String> completionService = new ExecutorCompletionService<String>(service);

        for(int i=0; i<size; i++) {
           FutureTask ft= (FutureTask) completionService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(random.nextInt(5000));
                    return Thread.currentThread().getName();
                }
            });
            list.add(ft);
        }

        int completionTask = 0;
        while(completionTask < size) {
            //如果完成队列中没有数据, 则阻塞; 否则返回队列中的数据
            Future<String> resultHolder = completionService.take();
            System.out.println("result: " + resultHolder.get());
            completionTask++;
        }

        System.out.println(completionTask + " task done !");

        //ExecutorService使用完一定要关闭 (回收资源, 否则系统资源耗尽! .... 呵呵...)
        service.shutdown();
    }
}




