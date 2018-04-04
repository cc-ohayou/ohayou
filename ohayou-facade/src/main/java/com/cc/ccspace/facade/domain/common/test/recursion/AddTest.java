package com.cc.ccspace.facade.domain.common.test.recursion;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/9 16:09.
 */
public class AddTest {


    public static void main(String[] args) {
        char c1='\u0061';//java字符采用Unicode编码 ，一个字符两个字节 采用16进制
        //这个转换到计算机就是前面8个0 后面 6转换为二进制0110 1转换为2进制0001 16进制转换二进制4位二进制换一个16进制
          // 代表后面跟的数字是Unicode的字符编码   \转义字符  \n换行
//        String c="\"\"\n\\n";
        System.out.println(c1);
        char a=1000;
        int max=655360000;
        double log10=Math.log(10);
        System.out.println(log10);
        int x=2,y=31;
       double sumPow= powSum(x,y);
        System.out.println(x+"的0次方到"+x+"的"+y+"次方之和 sumPow="+sumPow);
        int A='A';
        System.out.println(a);
        System.out.println(A);
      int n=6;
      int result=  method01(n);
        System.out.println("sum 1+2+.."+n+"="+result);
        long res3= fibonacci(n);
        System.out.println("fibonacci:f(n)=f(n-1)+f(n-2) "+"n="+n+",:res="+res3);
        int res2= factorial01(n);
        System.out.println("factorial:"+n+"!="+res2);
        long res4= factorialSum(6);
        System.out.println("factorialSum:1+2!+3!+.."+n+"!="+res4);
        long res5=fibonacciWithOutRecursion(n);//递归计算
        System.out.println("fibonacciWithOutRecursion : n="+n+"res="+res5);
    }
/**  * describe: 计算
	 * @author CAI.F
	 * @date:  日期:2017/4/9 时间:17:17
	 * @param
	 */
    private static double powSum(int x, int y) {

        if(y==1)
        {
            return Math.pow(x,1)+Math.pow(x,0);
        }
        else{
            return Math.pow(x,y)+powSum(x,y-1);
        }
    }

    /**  * describe: 使用非递归方式完成fabonacci计算  辗转相加法
	 * @author CAI.F
	 * @date:  日期:2017/4/9 时间:16:49
	 * @param
	 */
    private static long fibonacciWithOutRecursion(int n) {
        if(n<1){
            System.out.println("invalid parameter!");
            return -1;
        }
        if(n==1||n==2){
            return 1;
        }
        long f1=1L;
        long f2=1L;
        long f=0;
        for(int i=0;i<n;i++){
            f=f1+f2;
            f1=f2;
            f2=f;//这样下次循环的话 f2就代表上次相加的和 f1则代表上次f2的值也即上上次相加的和

        }
        return f;
    }

    /**  * describe: n的阶乘之和  1+2！+3！+....+n!
	 * @author CAI.F
	 * @date:  日期:2017/4/9 时间:16:36
	 * @param
	 */
    private static long factorialSum(int n) {
         if(n==1){
             return 1;
         }
         else{
             return factorial01(n)+factorialSum(n-1);
         }

    }

    /**  * describe: 非不利兮数列 f(n)=f(n-1)+f(n-2);
	 * @author CAI.F
	 * @date:  日期:2017/4/9 时间:16:26
	 * @param
	 */
    private static long fibonacci(int n) {
        if(n==1||n==2){
            return 1;
        }
        else{
            return fibonacci(n-1)+fibonacci(n-2);
        }

    }


    /**  * describe: 不使用for表达式实现 计算1到n的和
     * n层递归内存中同时有n个相同的方法运行 第一个运行的等着后面被调用的方法返回结果 才能继续运行下去
     * method01(5)等待method01(4)等待emethod01(3)..等待method01(1)返回结果 获得返回结果全部调用结束 方法结束
     * 栈空间中会堆积大量的由于方法相关而产生的一层层的数据 递归层数过深就会造成栈溢出
	 * @author CAI.F
	 * @date:  日期:2017/4/9 时间:16:12
	 * @param
	 */
    private static int method01(int n) {
        if(n==1){
            return n;
        }
        else {
            return n+method01(n-1);
        }
    }
/**  * describe: 计算1到n的阶乘
	 * @author CAI.F
	 * @date:  日期:2017/4/9 时间:16:18
	 * @param
	 */
    private static int  factorial01(int n) {
                    if(n==1){
                        return 1;
                    }
                    else{
                        return n*factorial01(n-1);
                    }

    }





}
