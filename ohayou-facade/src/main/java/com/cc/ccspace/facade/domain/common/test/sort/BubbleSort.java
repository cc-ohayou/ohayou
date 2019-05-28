package com.cc.ccspace.facade.domain.common.test.sort;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/4/15/015 19:58.
 */
public class BubbleSort extends AlgorithmSuper {
    @Override
    public boolean showFlag() {
        return true;
    }

    @Override
    /**  * describe: 冒泡排序算法
     * @author CAI.F
     * @date:  日期:2017/2/28 时间:13:26
     * @param
     */
    public   void sort(Comparable[] a) {
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
            //加入此种优化，一旦发现是有序数组则不在进行后面几轮的比较排序 直接return
            boolean didSwap = false;
            for (int j = 0; j < a.length - i; j++) {
                if (less(a[j + 1], a[j])) {
                    exch(a, j, j + 1);
                    //一旦任意位置发现无序 则标识置为true 按照正常方式排序
                    didSwap = true;
                }
            }
            //加入此种优化，一旦发现是有序数组则不在进行后面几轮的比较排序 直接return
            if (!didSwap) {
                return;
            }
        }
    }


    public static void main(String[] args) {
        Comparable [] a=generateArray(10,"int",100);
        Comparable [] b={1,2,44,53,4,5};
        AlgorithmSuper as=new BubbleSort();
//        as.sortT(a);
//        as.sortT(b);
        as.sort(b);

        for (int i=0;i<b.length;i++){
            System.out.println(b[i]);
        }
    }
}
