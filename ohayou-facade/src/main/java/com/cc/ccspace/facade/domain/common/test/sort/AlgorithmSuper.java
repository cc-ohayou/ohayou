package com.cc.ccspace.facade.domain.common.test.sort;

import com.cc.ccspace.facade.domain.common.exception.ParamException;
import com.sun.javafx.scene.traversal.Algorithm;
import com.sun.scenario.effect.Merge;
import jdk.nashorn.tools.Shell;
import org.springframework.util.StopWatch;

import javax.swing.text.ParagraphView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
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
public abstract class AlgorithmSuper  {

    public static int exchCou;
    public static int cmpCou;
    public static final String SELECT = "select";
    public static final String QUICK = "quick";
    public static final String BUBBLE = "bubble";
    public static final String SHELL = "shell";
    public static final String MERGE = "merge";
    public static final String INSERT = "insert";

    private static Map<String, Class> algClassMaps = new HashMap<>();

    static {
        algClassMaps.put(SELECT, SelectSort.class);
        algClassMaps.put(QUICK, QuickSort.class);
        algClassMaps.put(BUBBLE, BubbleSort.class);
        algClassMaps.put(SHELL, ShellSort.class);
        algClassMaps.put(MERGE, MergeSort.class);
        algClassMaps.put(INSERT, InsertSort.class);
    }

    public abstract boolean showFlag();

    public abstract void sort(Comparable[] a);

    public void sortT(Comparable[] a) {
        StopWatch timer = new StopWatch();
        timer.start();
        this.sort(a);
        timer.stop();
        System.out.println("cost time mills:" + timer.getTotalTimeMillis() + ",exchCou=" + exchCou + ",cmpCou=" + cmpCou);
        if (showFlag()) {
            System.out.println(isSorted(a));
            show(a);
        }
        cmpCou = 0;
        exchCou = 0;

    }

    public static double time(String algName, Comparable[] a) throws ParamException {
        StopWatch timer = new StopWatch(algName);
        timer.start();

        if(!algClassMaps.containsKey(algName)){
            throw new ParamException("algClassMaps not contain "+algName+" sort algorithm");
        }
        SimpleAlgFactory.createAlgorithm((Class) algClassMaps.get(algName)).sort(a);
        timer.stop();
        return timer.getTotalTimeMillis();
    }

    private static void compareTwoAlgorithm(String alg1,String alg2,int length,int times) throws ParamException {
        Comparable[] a = new Double[length];
        double t4 = timeRandomInput(alg1, length, times, a);
        System.out.println(alg1 + " costTime:" + t4 + "数组大小" + length + ",运行次数：" + times);
        double t5 = timeRandomInput(alg2, length, times, a);
        System.out.println(alg2 + " costTime:" + t5 + "数组大小" + length + ",运行次数：" + times);
    }

    public static Comparable[] generateArray(int cou, String type, int range) {
        Comparable[] a = new Comparable[cou];
        if ("double".equals(type)) {
            for (int i = 0; i < cou; i++) {
                a[i] = new Random().nextDouble();
            }
        } else if ("int".equals(type)) {
            for (int i = 0; i < cou; i++) {
                a[i] = new Random().nextInt(range);
            }
        } else if ("long".equals(type)) {
            for (int i = 0; i < cou; i++) {
                a[i] = new Random().nextLong();
            }
        }

        return a;
    }

    public static double timeRandomInput(String algName, int count, int times, Comparable[] c) throws ParamException {
        double total = 0.0;
//        Double [] c=new Double[count];
        for (int t = 0; t < times; t++) {
            for (int i = 0; i < count; i++) {
                c[i] = new Random().nextDouble();
            }
            total += time(algName, c);
        }
//        Assert.isTrue(isSorted(c));
        return total;
    }

    /**
     * describe: 手动输入数组
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/4/5 时间:22:49
     */
    static Integer[] inputNumbers() {
        System.out.println("请输入10个数字，将会对这些数字进行降序排序：");
        //Scanner scan=new Scanner(System.in);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //直接输入的是字节流 通过InputStreamreader转换成字符流再用缓冲流装填 减少读写 次数
        //就好比是你写字的时候 是直接看一行记住了再去默写 还是看一个字写一次效率高呢 人的这种暂时性记忆其实就是缓冲一样的

        String num1 = null;
        Integer[] a = new Integer[10];
        try {
            for (int i = 0; i < a.length; i++) {
                System.out.println("请输入第" + i + "个数字:");
                num1 = reader.readLine();
                while (!num1.matches("\\d+")) {
                    System.out.println("输入的不是数字，请重新输入");
                    num1 = reader.readLine();
                }
                a[i] = Integer.parseInt(num1);
            }
            System.out.println("请输入第二个数字:");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }


    /**
     * describe: 交换
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/3/7 时间:22:18
     */
    protected static void exch(Comparable[] a, int i, int j) {
        exchCou++;
        //要求被比较对象是一定要实现Comparable接口的 譬如Integer String Long这些是都实现该接口的
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * describe: 比较两个对象的大小 各种意义上 譬如对于int来讲就是单纯的数字大小
     * 但对于User来讲 比较的可能就是 年龄啊或者 id什么的 视情境而定 v<w返回true
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/3/7 时间:23:04
     */
    public static boolean less(Comparable v, Comparable w) {
//    String a="12";
        //String的compareTo方法稍微特殊点
//    Integer b=4;//Integer的compareTo和compare方法意义是一样的
        cmpCou++;
        //一般来讲compareTo方法返回小于0的数标示前者小于后者成立
        //当然这个要看你比较的对象的compareTo方法是否是正常规则
        // 譬如Integer 和String Double等  可以参考源码
        return v.compareTo(w) <= 0;

    }

    /**
     * describe: 遍历输出
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/3/7 时间:23:13
     */
    public static void show(Comparable[] a) {
        System.out.println("排序后的数组如下：");
        StringBuilder sb = new StringBuilder(a.length);
        for (int i = 0; i < a.length; i++) {
            sb.append(a[i]);
            sb.append(",");
        }
        System.out.println(sb.toString());
        //    System.out.println("exchange count:"+count);
    }

    /**
     * describe:验证数组元素是否有序
     *
     * @param
     * @author CAI.F
     * @date: 日期:2017/3/7 时间:23:18
     */
    public static boolean isSorted(Comparable[] a) {
        boolean flag = false;
        for (int i = 1; i < a.length; i++) {
            flag = less(a[i - 1], a[i]);
        }
        return flag;
    }


    public static void main(String[] args) throws Exception {

        Comparable[] a = {1,3,42,5,6,34,45};
        SimpleAlgFactory.createAlgorithm(algClassMaps.get(INSERT)).sort(a);
        show(a);

//        compareTwoAlgorithm(MERGE,QUICK,10000,10);
    }




}
