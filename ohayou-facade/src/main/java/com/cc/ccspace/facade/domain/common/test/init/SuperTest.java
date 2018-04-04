package com.cc.ccspace.facade.domain.common.test.init;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/17 16:07.
 */
public class SuperTest
{
    public static int k=0;
    public static SuperTest t1=new SuperTest("t1");//首先执行主方法 new对象之前要先把SuperTest中的静态属性初始化好
    public static SuperTest t2=new SuperTest("t2");//k=0;初始化 先静态后非静态 先属性后方法 t1属性先声明后赋值
    public static int i=print("i");//先声明属性 k=0 T1=NULL;t2=null i=0 n=0 再赋值 k=0 t1=new SuperTest("t1") 开始new对象
    public static int n=99;//值得注意****对象创建只针对非静态属性和方法 静态的属性和方法都由类自身在new之前初始化
    private int a=0;//因此开始对t1赋值 首先找到a=0 j=print("j")调用print方法打印 1:j i=0 n=0 然后i n自增1 往下执行构造块
    public int j=print("j");//print("构造块") 第二次调用print方法 打印 2:构造块 i=1 n=1 然后执行构造方法 打印 3:t1 i=2 n=2 至此t1赋值完毕
    {                      //开始给t2赋值完全同理t1的赋值过程 先非静态属性j=print("j")打印 4：j i=3 n=3 后构造块 打印 5:构造块 i=4 n=4
        print("构造块");  //最后执行构造方法 打印 6：t2 i=5 n=5 属性t2赋值完毕
    }                     //接下来为i赋值 print（"i"） 7：i i=6 n=6 然后为属性n赋值 n=99
    static {print("静态块");}//静态属性初始化完毕 接下来则是静态代码块 print("静态块")打印 8:静态块 i=7 n=99
    public SuperTest(String str)//这样类中的静态属性和方法初始化完毕 开始new对象 st  仍然是从非静态属性和代码块开始 9:j i=8 n=100
    {                           //print("构造块") 10:构造块 i=9 n=101 最后执行构造方法 11：init i=10 n=102 (每次执行print或构造方法完毕i和n都会自增1)
        System.out.println((++k)+":"+str+" i="+i+" n="+n);
        ++i;
        ++n;
    }
    public static int print(String str)
    {
        System.out.println((++k)+":"+str+" i="+i+" n="+n);
        ++n;
        return ++i;
    }
    public static void main(String args[])
    {
        SuperTest st=new SuperTest("init");
    }
}
