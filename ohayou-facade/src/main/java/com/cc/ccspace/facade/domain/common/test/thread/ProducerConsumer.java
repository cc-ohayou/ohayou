package com.cc.ccspace.facade.domain.common.test.thread;

import java.util.Random;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/18 22:03.
 */
public class ProducerConsumer {

    public static void main(String[] args) {
        Goods g=new Goods();
        Runnable rt=new Runnable() {
            @Override
            public void run() {
                System.out.println("lasldlasdldls");
            }
        };
        rt.run();
     Thread producer=new Thread(new Producer(g));
     Thread producer2=new Thread(new Producer(g));
     Thread consumer=new Consumer(g);
        producer.setName("生产者001");
        producer2.setName("生产者003");
        consumer.setName("消费者002");
        producer.start();
        producer2.start();
        consumer.start();


        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        producer.interrupt();

    }
}

class Producer implements Runnable{

Goods g;
public Producer(Goods goods){
    this.g=goods;
}

    @Override
    public void run() {

          Random r=new Random();
            while(true){
                produce(r.nextInt(10));
            }

    }

    private void produce(int no) {
        synchronized (g){
            try {
                Thread.currentThread().sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(g.getNum()+no<g.max){
                g.add(no);
                System.out.println(Thread.currentThread().getName()+"此次生产货物："+no+"现有货物"+g.num+"");
            }
            else{
                try {
                    System.out.println(Thread.currentThread().getName()+"此次生产货物："+no+"现有货物"+g.num+"等待消费");
                    g.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            g.notify();

        }


    }
}

class Consumer extends Thread{

    Goods g;
    public Consumer(Goods goods){
        g=goods;
    }
public void run(){

    Random r=new Random();
    while(true){
        consume(r.nextInt(30));
    }



}

    private void consume(int no) {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (g){
            if(g.getNum()-no<0){
                try {
                    g.wait();
                    System.out.println("货物数量剩下："+g.getNum()+","+getName()+"此次需求："+no+"等待生产。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            else{
                g.reduce(no);
                System.out.println(getName()+"此次消费了："+no+"货物数量剩下："+g.getNum()+"");

            }
            g.notify();

        }
    }


}

class Goods{
   volatile int num;
    int max=60;
    public  int getNum(){
        return this.num;
    }
    public void  add(int no){
        num+=no;
    }
    public void reduce(int no){
        num-=no;
    }
    public void setNum(int no){
        this.num=no;
    }
}