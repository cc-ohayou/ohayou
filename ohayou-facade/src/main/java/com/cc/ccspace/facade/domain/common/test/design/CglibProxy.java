package com.cc.ccspace.facade.domain.common.test.design;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/2 23:08.
 */
public class CglibProxy implements MethodInterceptor {

//private Object ss;
    private  Object createProxyOfSth(Object o){
//           ss=o;//可用可不用 看环境
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(o.getClass());
        /**
         * 设置回调接口对象 注意，只所以在setCallback()方法中可以写上this，
         * 是因为MethodIntecepter接口继承自Callback，是其子接口
         */
        enhancer.setCallback(this);
        return enhancer.create();
    }
    private void beforeOper(){
        System.out.println(" before invoke");
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        beforeOper();
//        Object result=method.invokeSuper(o,objects);
        methodProxy.invokeSuper(o,objects);//o是生成的代理对象
        return null;
    }


    public static void main(String[] args) {
        SomethingService sts=new SomethingServiceImpl() ;
                /*
                //直接使用匿名内部类也是可以的
                new SomethingService(){
            @Override
            public void eat(String name) {
            }
            @Override
            public void speak() {

            }
        };*/
         CglibProxy cp=new CglibProxy();
         SomethingService  proxySth= (SomethingService) cp.createProxyOfSth(sts);
         SomeSthNoImpl  ssnl=new SomeSthNoImpl();
//        可以不实现接口一样可以被代理 cglib帮忙生成子类覆盖原来类的方法并实现增强
         SomeSthNoImpl ssnlProxy= (SomeSthNoImpl) cp.createProxyOfSth(ssnl);
         proxySth.eat("cc");
         proxySth.speak();
         ssnlProxy.talk("dd");

    }

}

interface SomethingService{

    void eat(String name);
    void speak();

}

class SomethingServiceImpl implements SomethingService{


    @Override
    public void eat(String name) {
        System.out.println(name+" is eating sth..");
    }

    @Override
    public void speak() {
        System.out.println("speaking sth");
    }
}

class SomeSthNoImpl{

   void talk(String name){
       System.out.println(name+" is talking ");
   }
}
