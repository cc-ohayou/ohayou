package com.cc.ccspace.facade.domain.common.test.inherit;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/3/10 16:42.
 */
public class Apple extends Fruit{
    private String ca="dss";
    private String color;
    public Apple(String color){
        this.color=color;

    }
    @Override
    public void color() {

    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {


        Fruit  f=new Apple("red");
        Apple a=new Apple("yellow");

    String c=  f.getCategory();
    }

}
