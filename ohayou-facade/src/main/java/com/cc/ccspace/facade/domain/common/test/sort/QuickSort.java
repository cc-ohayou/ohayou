package com.cc.ccspace.facade.domain.common.test.sort;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/26 21:22.
 */
public class QuickSort extends AlgorithmSuper{


public int partition(Comparable[] a ,int lo,int hi)

    {
        int i=lo;
        int j=hi+1;
        Comparable v=a[lo];
        while(true){
            while(less(a[++i],v)){//左边的元素小于切分元素 就继续往下找 直到最后一个
                if(i==hi)break;
            }
            while(less(v,a[--j])){//右边的元素大于切分元素则继续往前找找寻 直到跟i碰头或者到起始元素
                if(j==lo)break;
            }
            if(i>=j)break;//两者相遇 说明从头到尾的数据都已经比较完了 退出循环
            exch(a,i,j);
        }
        exch(a,lo,j);//交换比较元素和 将符合切分规则的比较元素a[lo]放到a[j]处
        return j;
    }


    @Override
    public boolean showFlag() {
        return false;
    }

    @Override
    public void sort(Comparable[] a) {
//        sort(a,0,a.length-1);
        sort3Way(a,0,a.length-1);
    }

    private void sort(Comparable[] a, int lo, int hi) {
        if(hi<=lo)
            return;
        int j=partition(a,lo,hi);//找出切分元素的索引
        sort(a,lo,j-1);//左半边有序
        sort(a,j+1,hi);//右半边有序

    }

    public void sort3Way(Comparable[] a,int lo,int hi){
        if(hi<=lo)return;
        int lt=lo;
        int mid=lo+1;
        int gt=hi;
        Comparable v=a[lo];
        while(mid<=gt){
            int cmp=a[mid].compareTo(v);
            if(cmp<0){//比切分元素小 与实际的切分元素位置交换 实际的切分元素交换到后面继续参下次交换
                exch(a,mid++,lt++);
            }
            else if(cmp>0){
                exch(a,gt--,mid);//比切分元素大的交换到最后
            }
            else{//与切分元素相等
                mid++;
            }
        }
        sort3Way(a,lo,lt-1);
        sort3Way(a,gt+1,hi);


    }


    public static void main(String[] args) {
        Comparable[] a=generateArray(1000000,"double",10);
        AlgorithmSuper as=new QuickSort();
        as.sortT(a);
    }
}
