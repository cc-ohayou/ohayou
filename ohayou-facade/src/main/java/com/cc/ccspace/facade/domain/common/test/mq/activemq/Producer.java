package com.cc.ccspace.facade.domain.common.test.mq.activemq;


import com.cc.ccspace.facade.domain.common.constants.ActiveMQEnum;
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/14 16:18.
 */
public class Producer {
    //默认用户名
    private static final String USERNAME= ActiveMQEnum.ACTIVE_MQ_USERNAME.getValue();
    private static final String PASS=ActiveMQEnum.ACTIVE_MQ_PASSWORD.getValue();
    private static final String BROKEN_URL=ActiveMQEnum.ACTIVE_MQ_BROKEN_URL.getValue();

    AtomicInteger count=new AtomicInteger();
    ConnectionFactory connFactory;
    Connection conn;
    Session session;
    ThreadLocal<MessageProducer> threadLocal=new ThreadLocal<>();
    public void init(){
        //创建链接工厂
        //从工厂中获取一个链接
        try {
            connFactory=new ActiveMQConnectionFactory(USERNAME,PASS,BROKEN_URL);
            conn=connFactory.createConnection();
            conn.start();
            session=conn.createSession(true,Session.CLIENT_ACKNOWLEDGE);
            //两个参数 一个事务支持 一个设置是否签收消息模式  通常设置手动模式签收 以免自动签收了 执行业务却失败了
             //CLIENT_ACKNOWLEDGE 手动签收模式  Auto_ACKNOWLEDGE 自动签收模式
        } catch (JMSException e) {
            try {
                conn.close();//释放链接
            } catch (JMSException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }

    }
    public void sendMessage(String disname,String mess){
        try {
            Queue queue=session.createQueue(disname);
            //消息生产者
            MessageProducer messProducer=null;
            if(threadLocal.get()!=null){
                messProducer=threadLocal.get();
            }
            else{
                messProducer=session.createProducer(queue);
                messProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
                //消息持久化设置，mq重启后消息也不会丢失可设置持久化到何种数据库
                threadLocal.set(messProducer);
            }
                Thread.sleep(1000);
                int num=count.getAndIncrement();
                //创建消息
                TextMessage msg=session.createTextMessage(Thread.currentThread().getName()+
               mess+num);
                System.out.println(Thread.currentThread().getName()+
                        "productor:我是大帅哥，我现在正在生产东西！,count:"+num);
                messProducer.send(msg);
                session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}
