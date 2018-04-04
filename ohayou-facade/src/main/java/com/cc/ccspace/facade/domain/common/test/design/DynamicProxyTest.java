package com.cc.ccspace.facade.domain.common.test.design;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/2 22:08.
 */
public class DynamicProxyTest  implements InvocationHandler{

    private Subject subj;
    public DynamicProxyTest(Subject subj) {
        this.subj = subj;
    }

    private static Subject getProxyInstance(Subject subject){
        if(subject==null)
            throw new NullPointerException("Subject can not be null!");
        InvocationHandler handler=new DynamicProxyTest(subject);
       /*
        *通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
        * 第一个参数 handler.getClass().getClassLoader() ，
        * 我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
        * 第二个参数realSubject.getClass().getInterfaces()，
        * 我们这里为代理对象提供的接口是真实对象所实行的接口，
        * 表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
        * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler
        * 这个对象上
        */
        Subject sub= (Subject)Proxy.newProxyInstance(handler.getClass().getClassLoader(),
                subject.getClass().getInterfaces(),handler);
        return sub;
    }

    private void beforeRequest(){

    System.out.println("代理方法被调用之前");
}
private void afterRequest(){

    System.out.println("代理方法被调用之后");
}
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        beforeRequest();
       Object o= method.invoke(subj,args);
        afterRequest();
        return o;
    }


    public static void main(String[] args) {
        Subject sub=new SubjectReal();
        Subject proxySub= DynamicProxyTest.getProxyInstance(sub);
        proxySub.request("cc");
        proxySub.post();

    }
}

interface Subject{

    void request(String name);
    void post();

}

class SubjectReal implements Subject{


    @Override
    public void request(String name) {
        System.out.println("被代理对象的request方法"+name);
    }

    public void post(){
        System.out.println("被代理对象的post方法");

    }
}


