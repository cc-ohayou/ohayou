package com.cc.ccspace.test.spring;

import com.cc.ccspace.test.spring.aop.Worker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/3 15:32.
 */
public class SpringTest {

    public static void main(String[] args) {
//        Resource res=new ClassPathResource("classpath:config/spring-mybatis.xml");
//        BeanFactory bf=new XmlBeanFactory(res);
        ApplicationContext ac=new ClassPathXmlApplicationContext("/config/test.xml");
        BeanContextTest bct0=(BeanContextTest)ac.getBean("cc");
//        BeanContextTest bct=(BeanContextTest)bf.getBean("cc");
//        bct.sayHello("cc");
        bct0.sayHello("lala");
        Worker w= ac.getBean(Worker.class);
        w.work();
    }
}
