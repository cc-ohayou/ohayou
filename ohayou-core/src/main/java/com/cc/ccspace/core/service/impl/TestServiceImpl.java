package com.cc.ccspace.core.service.impl;

import com.cc.ccspace.facade.domain.common.constants.ClassLoadConstant;
import com.cc.ccspace.facade.domain.common.constants.ExceptionCode;
import com.cc.ccspace.facade.domain.common.exception.BusinessException;
import com.cc.ccspace.facade.domain.common.test.mq.activemq.ConsumeTest;
import com.cc.ccspace.facade.domain.common.test.mq.activemq.ProduceTest;
import com.cc.ccspace.facade.domain.common.util.ClassLoadUtil;
import com.cc.ccspace.facade.domain.common.util.http.HttpClientUtil;
import com.cc.ccspace.facade.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/7 0:37.
 */
@Service
public class TestServiceImpl  implements TestService {
    private static final Logger logger= LoggerFactory.getLogger(TestServiceImpl.class);
    private static ExecutorService es = Executors.newFixedThreadPool(10);

   /* @Resource
    private JtaTransactionManager jtaTransactionManager;*/
    @Override
    public String createTableStr(String beanName) {
        Set<Class<?>> clsSet= ClassLoadUtil.getClassSetOfPackage(ClassLoadConstant.BnnTableDemoPath);
        Class  bnnClass=ClassLoadUtil.getClassByBeanName(clsSet,beanName);
        if(bnnClass==null){
            return "";
        }
        Map<String,Object> m=new HashMap<>();
        Field[] fields= bnnClass.getDeclaredFields();
        for(Field f:fields){
            f.setAccessible(true);
            com.cc.ccspace.facade.domain.common.annotation.Field field=
                   f.getAnnotation(com.cc.ccspace.facade.domain.common.annotation.Field.class);
             m.put(f.getName(),field);
        }
        String tableStr=ClassLoadUtil.generateTableSql(beanName,m);
        return tableStr;

    }

    //可采用aop配置不使用注释，针对某些命名的方法进行事务管理 readOnly =true控制库的读写 有待研究
    @Transactional(rollbackFor=Exception.class,propagation= Propagation.REQUIRED)
    @Override
    public void test(int mode) throws Exception {
        Map m=new HashMap();
        m.put("uid","001");
        m.put("price","10");
        m.put("status","1");
//       TransactionManager tm= jtaTransactionManager.getTransactionManager();//手动管理模式 此时可把注解去除
          /*
          try {
            tm.getStatus();
            tm.begin();
              catch (Exception e){
            log.error(e.getMessage(),e);
            tm.rollback();
             throw new Exception("error rollback");
         }    }

            */
            if (mode==1) {
               throw new BusinessException(ExceptionCode.BIZ_ERROR,"回滚测试！");
            }
        }

    @Override
    public void consume(int count) throws BusinessException {
        for(int i=0;i<count;i++){
        ConsumeTest.consume(es);
        }
    }

    @Override
    public void produce(int count) throws BusinessException {
        for (int i = 0; i <count ; i++) {
            ProduceTest.produceMess(es);
        }

    }

    @Override
    public String httpTest() {
        // 获取token
        // 构造请求参数
        Map<String, String> params = new HashMap<>();
        params.put("finance_mic", "SH");
        String result = HttpClientUtil.sendGet(HttpClientUtil.OPENURL + "/quote/v1/market/detail", params,
                HttpClientUtil.CHARSET, null, HttpClientUtil.BEARER + "", "全部A股行情");
        logger.info(result);
        System.out.println(result);
        return result;

    }


}
