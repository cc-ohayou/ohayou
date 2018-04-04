package com.cc.ccspace.facade.domain.common.test.sort;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/26 22:12.
 */
public class InsertSort extends AlgorithmSuper {


    @Override
    public boolean showFlag() {
        return false;
    }

    public  void sort(Comparable [] a){
        int len=a.length;
        for(int i=1;i<len;i++){
            for(int j=i;j>=1&&less(a[j],a[j-1]);j--){
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
