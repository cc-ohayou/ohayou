package com.cc.ccspace.test.spring.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/3/1 16:14.
 */
@Configuration
public class CameraSystemConfig {

    @Bean(name = "camera")
    public SecurityCameraSystem carmera(){
        return new SecurityCameraSystem();
    }

}
