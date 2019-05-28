package com.cc.ccspace.facade.domain.common.test.sort;

import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/*      插入、希尔对比
        insert costTime:1652646.0数组大小100000,运行次数：100
        shell costTime:5401.0数组大小100000,运行次数：100
        插入、希尔、选择对比
        insert costTime:37661.0数组大小100000,运行次数：1
        shell costTime:77.0数组大小100000,运行次数：1
        select costTime:57484.0数组大小100000,运行次数：1
        希尔千万单次排序时间
        shell costTime:1630.0数组大小1000000,运行次数：1
        shell costTime:29147.0数组大小10000000,运行次数：1
        //希尔、堆排序对比 单次100万
        shell costTime:1475.0数组大小1000000,运行次数：1
        merge costTime:485.0数组大小1000000,运行次数：1
        10次 一百万
        shell costTime:13443.0数组大小1000000,运行次数：10
        merge costTime:4380.0数组大小1000000,运行次数：10
        100次十万
        shell costTime:5203.0数组大小100000,运行次数：100
        merge costTime:2549.0数组大小100000,运行次数：100
        单次千万级别 希尔和堆排序时间对比
        shell costTime:29861.0数组大小10000000,运行次数：1
        merge costTime:6977.0数组大小10000000,运行次数：1

        merge costTime:6183.0数组大小10000000,运行次数：1
        quick costTime:4343.0数组大小10000000,运行次数：1
merge costTime:2938.0数组大小100000,运行次数：100
quick costTime:1965.0数组大小100000,运行次数：100
        merge costTime:4666.0数组大小1000000,运行次数：10
        quick costTime:3320.0数组大小1000000,运行次数：10



 */

/**
 * @AUTHOR CF
 * @DATE Created on 2017/2/28 13:23.
 */
public abstract class AlgorithmSuper {

    public static int exchCou;
    public static int cmpCou;
    public abstract boolean showFlag();
    public abstract void sort(Comparable [] a);
    public  void sortT(Comparable[] a){
        StopWatch timer=new StopWatch();
        timer.start();
        this.sort(a);
        timer.stop();
        System.out.println("cost time mills:"+timer.getTotalTimeMillis()+",exchCou="+exchCou+",cmpCou="+cmpCou);
        if(showFlag()) {
            System.out.println(isSorted(a));
            show(a);
        }
        cmpCou=0;
        exchCou=0;

    }

    public static double time(String algName,Comparable[] a){
        StopWatch timer=new StopWatch(algName);
        timer.start();
       switch(algName){
           case "select":selectSort(a);
               break;
           case "shell":shellSort(a);
               break;
           case "bubble":bubbleSort(a);
               break;
           case "insert":insertSort(a);
               break;
           case "quick":new QuickSort().sort(a);
               break;
           case "merge":new MergeSort().sort(a);
               break;

       }

       timer.stop();
//        System.out.println(timer.prettyPrint());
        return timer.getTotalTimeMillis();
    }

    private static void insertSort(Comparable[] a) {
        int len=a.length;
        for(int i=1;i<len;i++){
            for(int j = i; j>0&&less(a[j],a[j-1]); j--){
                exch(a,j,j-1);
            }

        }

    }

    public static void main(String[] args) throws Exception {
//     Integer [] a= inputNumbers();

       // String read=scan.nextLine();
       /*
        for(int i=0;i<a.length;i++){
            String ss= scan.next();
            int si=Integer.parseInt(ss);
            a[i]=scan.nextInt();
           // System.out.println();
        }*/

         // quickSort(b,1,8);
//        selectSort(b);
//        shellSort(b);


//       boolean sort= isSorted(b);
//        System.out.println(b.toString());
//        show(b);

        String alg0="bubble";
        String alg1="shell";
        String alg2="insert";
        String alg3="select";
        String alg4="merge";
        String alg5="quick";
        int n=100000;
        int times=100;

        Comparable [] a=new Double[n];
       /* double t1= timeRandomInput(alg1,n,times,a);
//        show(a);
        System.out.println(alg1+" costTime:"+t1+"数组大小"+n+",运行次数："+times);
     */ /*  double t2= timeRandomInput(alg2,n,times,a);
        System.out.println(alg2+" costTime:"+t2+"数组大小"+n+",运行次数："+times);
        double t3=timeRandomInput(alg3,n,times,a);
        System.out.println(alg3+" costTime:"+t3+"数组大小"+n+",运行次数："+times);
       */ double t4=timeRandomInput(alg4,n,times,a);
        System.out.println(alg4+" costTime:"+t4+"数组大小"+n+",运行次数："+times);
       double t5=timeRandomInput(alg5,n,times,a);
        System.out.println(alg5+" costTime:"+t5+"数组大小"+n+",运行次数："+times);
    }

    public static Comparable[] generateArray(int cou,String type,int range) {
        Comparable [] a=new Comparable[cou];
       if("double".equals(type)){
           for(int i=0;i<cou;i++){
               a[i]=new Random().nextDouble();
           }
       }
       else if("int".equals(type)){
           for(int i=0;i<cou;i++){
               a[i]=new Random().nextInt(range);
           }
       }
       else if("long".equals(type)){
           for(int i=0;i<cou;i++){
               a[i]=new Random().nextLong();
           }
       }

        return a;
    }

    public static double timeRandomInput(String algName,int count,int times,Comparable[] c){
        double total=0.0;
//        Double [] c=new Double[count];
        for(int t=0;t<times;t++){
            for(int i=0;i<count;i++){
                c[i]=new Random().nextDouble();
            }
            total+=time(algName,c);
        }
//        Assert.isTrue(isSorted(c));
          return total;
    }
/**  * describe: 希尔排序
	 * @author CAI.F
	 * @date:  日期:2017/4/25 时间:9:39
	 * @param
	 */
    private static void shellSort(Comparable[] b) {
        int len=b.length;
        int h=1;
        while(h<len/3){
            h=3*h+1;
        }
        while(h>=1){
            for(int i=h;i<len;i++){
                for(int j = i; j>=h&&less(b[j],b[j-h]); j-=h){
                    exch(b,j,j-h);
                }
            }
            h=h/3;
        }
//        System.out.println("exch count:"+c);
    }

    /**  * describe: 手动输入数组
	 * @author CAI.F
	 * @date:  日期:2017/4/5 时间:22:49
	 * @param
	 */
     static  Integer[] inputNumbers() {
        System.out.println("请输入10个数字，将会对这些数字进行降序排序：");
        //Scanner scan=new Scanner(System.in);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
         //直接输入的是字节流 通过InputStreamreader转换成字符流再用缓冲流装填 减少读写 次数
         //就好比是你写字的时候 是直接看一行记住了再去默写 还是看一个字写一次效率高呢 人的这种暂时性记忆其实就是缓冲一样的

         String num1 = null;
         Integer [] a=new Integer[10];
         try {
        for(int i=0;i<a.length;i++) {
            System.out.println("请输入第"+i+"个数字:");
            num1 = reader.readLine();
            while (!num1.matches("\\d+")) {
                System.out.println("输入的不是数字，请重新输入");
                num1 = reader.readLine();
            }
            a[i]=Integer.parseInt(num1);
        }
        System.out.println("请输入第二个数字:");
       /* String num2 = reader.readLine();
        while(!Pattern.compile("[0-9]*").matcher(num2).matches())
        {
            System.out.println("输入的不是数字，请重新输入");
            num2 = reader.readLine();
        }*/
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }

    /**  * describe: 选择排序算法  每次循环 选出一个最小数  i=0时a[0] 从其它元素中选出一个最小的跟它交换
     * 一轮过后a[0]变为最小数 第二轮先认为a[1]是最小的 再从包裹a[1]在内的所有元素中选出一个最小的跟a[1]交换
     *   a[1]页变为最小的元素 后面的以此类推 最终经过a.length次交换 比较则是n-1+n-2+。。+1=n2/2次比较
    	 * @author CAI.F
    	 * @date:  日期:2017/3/7 时间:22:11
    	 * @param
    	 */
public static void selectSort(Comparable[] a){
for(int i=0;i<a.length;i++){//每一轮都要找出所有元素中最小的一个数跟a[i]交换 总共n次交换 比较次数n2/2
    int min=i;
    for(int j=i+1;j<a.length;j++){//i的每一轮对于内部所有元素来讲
        if(less(a[j],a[min])){
            min=j;
        }
//        c++;
    }
    exch(a,i,min);
}
//    System.out.println("exch count:"+exchCou);


}

/**  * describe: 交换
	 * @author CAI.F
	 * @date:  日期:2017/3/7 时间:22:18
	 * @param
	 */
protected static void exch(Comparable[] a, int i, int j){
     exchCou++;
    Comparable temp=a[i];//要求被比较对象是一定要实现Comparable接口的 譬如Integer String Long这些是都实现该接口的
    a[i]=a[j];
    a[j]=temp;
}

/**  * describe: 比较两个对象的大小 各种意义上 譬如对于int来讲就是单纯的数字大小
 *    但对于User来讲 比较的可能就是 年龄啊或者 id什么的 视情境而定 v<w返回true
	 * @author CAI.F
	 * @date:  日期:2017/3/7 时间:23:04
 * @param
 */
public static boolean less(Comparable v,Comparable w){
    String a="12"; //String的compareTo方法稍微特殊点
//    Integer b=4;//Integer的compareTo和compare方法意义是一样的
    cmpCou++;
    return v.compareTo(w)>0;//一般来讲compareTo方法返回小于0的数标示前者小于后者成立
    //当然这个要看你比较的对象的compareTo方法是否是正常规则
    // 譬如Integer 和String Double等  可以参考源码

}
/**  * describe: 遍历输出
	 * @author CAI.F
	 * @date:  日期:2017/3/7 时间:23:13
	 * @param    
	 */
public  static void show(Comparable[] a){
    System.out.println("排序后的数组如下：");
    StringBuilder sb=new StringBuilder(a.length);
    for(int i=0;i<a.length;i++){
       sb.append(a[i]);
        sb.append(",");
    }
    System.out.println(sb.toString());
 //    System.out.println("exchange count:"+count);
}
/**  * describe:验证数组元素是否有序
	 * @author CAI.F
	 * @date:  日期:2017/3/7 时间:23:18
	 * @param
	 */
    public static boolean isSorted(Comparable[] a){
        boolean flag = false;
     for(int i=1;i<a.length;i++) {
         if (less(a[i-1],a[i]))
             flag=true;
         else
             flag= false;
     }
     return flag;
    }

    /**  * describe: 快速排序 选择一个基准进行排序 效率相较于冒泡是快的多的
     * 算法思想：以军训排队为例，教官说以第一个同学为中心，
     * 比他矮的站他左边，比他高的站他右边，这就是一趟快速排序。因此，一趟快速排序是以一个枢轴，
     * 将序列分成两部分，枢轴的一边比它小（或小于等于），另一边比它大（或大于等于）。
    	 * @author CAI.F
    	 * @date:  日期:2017/2/28 时间:23:35
    	 * @param
    	 */

    private static void quickSort(Comparable [] b,int lef,int right) {




    }

    /**  * describe: 冒泡排序算法
     * @author CAI.F
     * @date:  日期:2017/2/28 时间:13:26
     * @param
     */
    private static void bubbleSort(Comparable[] a) {
       // Comparable[] a = new Comparable[5];
//        Comparable[] b = {10, 2, 5, 4, 7, 3, 8, 6, 1, 9};
//        a = b;
        //System.arraycopy(b,0,a,0,3);
        //int c=a.length;
        //第一次交换最多 a[0] 要和a[1]...a[8] 所有元素依次比较 从第一个元素开始
        // 最坏情况 要交换n-1次 依旧认为最坏情况 下一次循环要交换8次
        //直到第n-1个元素交换1次  总的比较交换次数是 1+2+3+..+n-1 总的比较交换次数是n2/2
        //如果是最好的情况 每次都不用交换 交换次数0 循环次数仍是n-1 比较次数n
        for (int i = 1; i < a.length; i++) {
            boolean didSwap=false;
            for (int j = 0; j < a.length - i; j++) {
                if (less(a[j+1],a[j])) {
                    exch(a, j, j + 1);
                    didSwap=true;
                }
                if(didSwap){
                    break;
                }

            }

        }
//        show(a);


        /*for(int i=b.length-1;i>0;--i){
              for(int j=0;j<i;++j){
                  if(b[j]<b[j+1]){
                      int temp=b[j];
                      b[j]=b[j+1];
                      b[j+1]=temp;
                  }
              }

        }*/
        //两种方式原理是一样的  第一次比较  排首的元素一路找准一个方向前进 跟途中的元素比较
        // 满足条件则交换 交换后的元素继续之前的使命继续往终点驶去 最终保证到达终点的元素是符合我们要求的
        //譬如说 j=4的时候 发现4位的元素满足了我们的条件大于5位上的元素
        //则4与5位的元素互换 下一个循环时5位的元素已然变成了原来4位的元素继续与下一个元素 6位比较
        //依次向下 直到基于i来讲的一轮结束 最后一位的就变为最大的或者最小的值 之后下一轮
        // 都是升序排列
        // if判断修改就可以变为降序

        /*for(int i=1;i<b.length;i++){
            for(int j=0;j<b.length-i;j++){//要注意随着i的增大即轮数的增大最后的比较次数肯定是越来越少的
                //譬如这里来将讲第一轮的比较肯定是最多次的 10个数字需要比较9次
                //而下一轮 只剩下9个元素需要比较 比较次数降为8次 所以是j<b.length-i  数组的长度减去已经进行过的轮数
                //由此也可见冒泡排序效率是很低下的 是最笨重的方法
                if(b[j]>b[j+1]) {
                    int temp = b[j];
                    b[j] = b[j + 1];
                    b[j + 1] = temp;
                }
            }

        }*/


/*
        for(int i=1;i<b.length;i++){
            for(int j=0;j<b.length-i;j++){

                if(b[j]<b[j+1]){
                    int temp=b[j];
                    temp=b[j];
                    b[j]=b[j+1];
                    b[j+1]=temp;
                }


            }

        }*/


    }





}
