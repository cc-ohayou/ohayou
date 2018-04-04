package com.cc.ccspace.facade.domain.common.test.design;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/22 17:54.
 */
public enum Singleton {
    INSTANCE,Red("",1); ;
    private String name;
    private int index;
    private Singleton(){}
    private Singleton(String color,int index){
        this.name=color;
        this.index=index;
    }
   public void add(){
       System.out.println("aaaaa");
    }

    public static void main(String[] args) {
       Singleton.INSTANCE.add();
    }
}
