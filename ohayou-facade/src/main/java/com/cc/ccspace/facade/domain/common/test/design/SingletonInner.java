package com.cc.ccspace.facade.domain.common.test.design;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/22 17:58.
 */
public class SingletonInner {
 private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private static class singleInner{
        private static SingletonInner si=new SingletonInner();
    }
    private static SingletonInner getInstance(){
        return singleInner.si;
    }


    public static void main(String[] args) {
        SingletonInner s1=SingletonInner.getInstance();
              s1.setName("cc");
        SingletonInner s2=SingletonInner.getInstance();
            s2.setName("aa");
    }
}
