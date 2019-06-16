package com.cc.ccspace.facade.domain.common.test.sort;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/26 22:12.
 */
public class InsertSort extends AlgorithmSuper {


    @Override
    public boolean showFlag() {
        return true;
    }

/*    public  void sort(Comparable [] a){
        int len=a.length;
        for(int i=1;i<len;i++){
            for(int j=i;j>=1&&less(a[j],a[j-1]);j--){
                exch(a,j,j-1);
            }

        }

    }
    */

    @Override
    public  void sort(Comparable[] a){
        int len=a.length;
        for(int i=1;i<len;i++){
            // 每轮过去前i+1个元素有序  内层循环 后一元素大于前一元素直接结束 内层循环
            // 小于则一次往前推进
            for(int j=i;j>0&&less(a[j],a[j-1]);j--){
                exch(a,j,j-1);
            }
        }
    }

    public static void main(String[] args) {
        Comparable [] a=generateArray(10,"int",100);
        AlgorithmSuper as=new InsertSort();
        as.sortT(a);

    }
}
