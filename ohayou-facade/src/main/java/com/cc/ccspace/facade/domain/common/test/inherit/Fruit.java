package com.cc.ccspace.facade.domain.common.test.inherit;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/3/10 16:42.
 */
public abstract class Fruit {
    private String  category="cc";
    public abstract void color();
    public String  getCategory(){
        System.out.println("category is:"+category);
        return category;
    }
}
