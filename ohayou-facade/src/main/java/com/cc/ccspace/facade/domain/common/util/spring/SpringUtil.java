package com.cc.ccspace.facade.domain.common.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//spring bean 获取类
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        SpringUtil.applicationContext = arg0;
    }
    public static Object getBean(String name){
        return applicationContext.getBean(name);
    }
}
