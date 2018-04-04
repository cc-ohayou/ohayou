package com.cc.ccspace.facade.domain.common.test.mq.activemq;

import com.cc.ccspace.facade.domain.common.constants.ActiveMQEnum;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/14 16:18.
 */
public class Consumer {
    private static final String USERNAME= ActiveMQEnum.ACTIVE_MQ_USERNAME.getValue();
    private static final String PASS=ActiveMQEnum.ACTIVE_MQ_PASSWORD.getValue();
    private static final String BROKEN_URL=ActiveMQEnum.ACTIVE_MQ_BROKEN_URL.getValue();

    ConnectionFactory connFactory;
    Connection conn;
    Session session;
    ThreadLocal<MessageConsumer> threadLocal=new ThreadLocal<>();
    AtomicInteger count=new AtomicInteger();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public void inint(){
        try {
            connFactory=new ActiveMQConnectionFactory(USERNAME,PASS,BROKEN_URL);
            conn=connFactory.createConnection();
            conn.start();
            session=conn.createSession(false,Session.CLIENT_ACKNOWLEDGE);
            //两个参数 一个事务支持 一个设置是否签收消息模式  通常设置手动模式签收
            // 以免自动签收了 执行业务却失败了 CLIENT_ACKNOWLEDGE 手动签收模式
            // Auto_ACKNOWLEDGE 自动签收模式 签收消息 消息就会从队列移除
            // 消费者可以调用acknowledge()方法进行签收

        } catch (JMSException e) {
            e.printStackTrace();
        }


    }
    public void receiveMessage(String disname){
        try {
            //根据队列名称获得队列
            Queue queue=session.createQueue(disname);//p2p模式 消息只能被一个消费者接收
            Destination des=session.createTopic("cc01");//pub/sub模式 订阅主题 消息可被多个消费者接收
            MessageConsumer consumer=null;
            if(threadLocal.get()!=null){
                consumer=threadLocal.get();
            }
            else{
                consumer=session.createConsumer(queue);
                //根据队列创建对应的消息消费者
                //第一次访问时将消息消费者设置到本地线程，以免多线程环境下找不到自己的consumer
//                因为每个consumer都是不同的session创建的 session依赖于disname
                threadLocal.set(consumer);
            }
                Thread.sleep(1000);
                TextMessage msg=(TextMessage)consumer.receive();
                if(msg!=null){
                    System.out.println(Thread.currentThread().getName()+": Consumer:"+this.name+
                            ",正在消费Msg"+msg.getText()+"--->"
                            +count.getAndIncrement());
                    msg.acknowledge();
                }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
