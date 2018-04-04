package com.cc.ccspace.facade.domain.common.test;

import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/2/26 23:15.
 */
public class Test1 {
    public static int a=0;
    public static int b=0;
    public Test1() {
        System.out.println("Test1"+a++);
    }
 /*  jdk1.7之后 字符串常量池被转移到堆中
    常量池
    在class中，所以一个类就会有一个常量池。用于存放字面量和符号引用。
    字面常量相当于常量的概念，包括字符串呀，声明为final的常量呀等
    符号引用就是用来描述所引用的目标的东东，包括以下三类：
    1.类和接口的全限定名
    2.字段的名称和描述符
    3.方法的名称和描述符
    这里的常量池是一个静态的概念，具体的内容可以去关注class文件的的结构~
    四、运行时常量池
　　   在类加载的时候，class文件的常量池的内容会存到运行时常量池中，
      同时，更重要的，会把其中的符号引用转成直接引用。
      首先会去字符串常量池中找有没有要引用的字符串，如果有就返回引用给运行时常量池
      如果没有，那就创建在返回。保证运行时常量池与字符串常量池的引用是一样的。
    */
    public static void main(String[] args) throws InterruptedException {
        /* int COUNT_BITS = Integer.SIZE - 3;
         int RUNNING    = -1 << COUNT_BITS;
         int SHUTDOWN   =  0 << COUNT_BITS;
         final int STOP       =  1 << COUNT_BITS;
         final int TIDYING    =  2 << COUNT_BITS;
         final int TERMINATED =  3 << COUNT_BITS;

        String d=new StringBuilder("ab").append("c").toString();
        String a=new String("ab")+"c";//false  字符串常量池初始化 这个优先级比d的赋值运算高
        String f=a.intern();
        String b=new String("a")+new String("bc");
        String c="a"+new String("bc");
        String e=d.intern();
        //javap -verbose Test.cass    查看反编译类文件
        System.out.println(a==b);//false
        System.out.println(a==c);//false
        System.out.println(c==b);//false
        System.out.println(c==d);//false
        System.out.println(e==d);//false
        System.out.println(e==c);//false
        System.out.println(e==a);//true
        System.out.println(f==a);//true
        System.out.println(f==d);//false*/
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        Thread.sleep(2000);
        stopWatch.stop();
        System.out.println("导出到流耗时:"+stopWatch.getLastTaskTimeMillis());
        stopWatch.start();
        Thread.sleep(3000);
        stopWatch.stop();
        System.out.println("导出到流耗时:"+stopWatch.getLastTaskTimeMillis());
        System.out.println("总耗时:"+stopWatch.getTotalTimeSeconds());

//        decimalOperTest();

        // 上面无论哪个跟 String c="abc";同时出现的话 指令重排序的原因还是这句先执行 有"abc"这个表达式的是优先级较高的
    }

    private static void decimalOperTest() {
        BigDecimal contractAmount = new BigDecimal(323.23423423423);
        BigDecimal cv = new BigDecimal(-139.7500);
        cv=cv.abs();
        System.out.println(cv);
        Map<String,Object> m=new HashMap<>(4);
        m.put("num",contractAmount);
        BigDecimal c=(BigDecimal)m.get("num");
        System.out.println(c);
    }



}
