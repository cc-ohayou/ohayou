package com.cc.ccspace.web.controller;


import com.cc.ccspace.core.manager.RedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/hello")
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisManager redisManager;

// controller层入参要加注解@RequestBody 才能接收到application/json格式的参数
// @RequestBody 接受对象里面,如果已经有了非空构造函数，
// 那么它同时必须要有默认的空构造函数，注意这个构造函数一定要为空
    @ResponseBody
    @RequestMapping(value = "/say" )
    public Object say( String word) {
        logger.info("run into hello say,word is "+word);
        if (StringUtils.isEmpty(word)) {

            return "hello";
        }
        return redisManager.get("cc");
    }



}

