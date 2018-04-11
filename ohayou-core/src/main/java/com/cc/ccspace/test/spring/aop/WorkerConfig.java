package com.cc.ccspace.test.spring.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/3/1 15:29.
 */
@Configuration
public class WorkerConfig {
    @Bean
    public Worker  worker(){
        System.out.println("工作者初始化 是nurse");
        return new Nurse("lucy");
    }
    @Bean(name = "nurseTask")
    public Task task(){
        return new NursingTask();
    }
}
