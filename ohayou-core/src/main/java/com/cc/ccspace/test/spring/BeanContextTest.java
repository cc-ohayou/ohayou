package com.cc.ccspace.test.spring;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/5/3 15:29.
 */
public class BeanContextTest {

    private String name;

    public BeanContextTest() {
        System.out.println("##HelloSpring.BeanContextTest初始化……………………………………");
    }

    public BeanContextTest(String name) {
        this.name = name;
    }

    public void sayHello(String something){
        System.out.println("hello"+something);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HelloSpring{" +
                "name='" + name + '\'' +
                '}';
    }


}
