package com.cc.ccspace.facade.domain.common.test.mq.activemq;

import java.util.concurrent.ExecutorService;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/14 16:59.
 */
public class ProduceTest {
    //普遍使用的线程池 生产固定数量的线程 去统一的任务队列处获取任务执行

/*    static{
       schedulerPool.schedule(new

    Runnable() {
        public void run () {
            System.out.println("delay 3 seconds");
        }
    },3,TimeUnit.SECONDS);
}*/
     static ProducerMq proMq;
     static{
    Producer producer=new Producer();
    producer.init();
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
     proMq=new ProducerMq(producer);
    proMq.setMsg("i'm producing mess ,count=");

  }
   public static void produceMess(ExecutorService es){
      es.execute(proMq);
   }

    public static void main(String[] args) {


    }

    private static class ProducerMq implements Runnable{
        Producer producer;
        public ProducerMq(Producer producer){
            this.producer=producer;
        }
        private String msg;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
                producer.sendMessage("mq test",msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }

    }
