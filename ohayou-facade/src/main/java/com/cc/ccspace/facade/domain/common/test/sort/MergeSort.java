package com.cc.ccspace.facade.domain.common.test.sort;

import java.util.Random;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/25 18:37.
 */
public class MergeSort extends AlgorithmSuper{

    private static Comparable[] temp;
    private  boolean showFlag=false;
    @Override
    public boolean showFlag() {

        return showFlag;
    }
    public MergeSort(boolean showFlag){
    this.showFlag=showFlag;

   }
    public MergeSort(){

    }
    public  void sort(Comparable[] a){
        temp=new Comparable[a.length];
        sort(a,0,a.length-1);
    }
public static boolean less(Comparable b,Comparable c){
    return b.compareTo(c)<0;
}
    public static void merge(Comparable[] a,int lo,int mid,int hi) {
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            temp[k] = a[k];
        }
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {//左边元素用完
                a[k] = temp[j++];
            } else if (j > hi) {//右边元素用完
                a[k] = temp[i++];
            } else if (less(temp[j], temp[i])) {
                a[k] = temp[j++];//右半边的元素小于左半边的元素 取右半边的元素
            } else {
                a[k] = temp[i++];//右半边的元素大于等于左半边的
            }

        }
    }
    private static void sort(Comparable[] a, int lo, int hi) {
        if(hi<=lo)
            return;
        int mid=lo+((hi-lo)>>1);
        sort(a,lo,mid);
        sort(a,mid+1,hi);
        merge(a,lo,mid,hi);

    }

    public static void main(String[] args) {
        int count=10000000;
        Comparable []c=new Comparable[count];
        for(int i=0;i<count;i++){
            c[i]=new Random().nextInt();
        }
        AlgorithmSuper as=new MergeSort();
        as.sortT(c);
    }

}
