package com.cc.ccspace.facade.domain.common.test.mq.activemq;

import java.util.concurrent.ExecutorService;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/14 18:04.
 */
public class ConsumeTest {
    static  ConsumeMq conmq;
    static{
        Consumer consumer=new Consumer();
        consumer.setName("cc007");
        consumer.inint();
        conmq=new ConsumeMq(consumer);
    }
    public  static void consume(ExecutorService es){
        es.execute(conmq);
    }
    public static void main(String[] args) {

    }
    private static  class  ConsumeMq implements Runnable{
        Consumer consumer;
        public ConsumeMq(Consumer consumer){
            this.consumer=consumer;
        }
        @Override
        public void run() {
                try {
                    consumer.receiveMessage("mq test");
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
