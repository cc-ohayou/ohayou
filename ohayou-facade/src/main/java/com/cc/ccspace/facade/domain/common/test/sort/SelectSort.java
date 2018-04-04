package com.cc.ccspace.facade.domain.common.test.sort;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/26 21:59.
 */
public class SelectSort extends AlgorithmSuper{

    @Override
    public boolean showFlag() {
        return false;
    }

    public void sort(Comparable[] a)  {

        int len=a.length;
        for(int i=0;i<len;i++){
            int min=i;
            for(int j=i+1;j<len;j++){
                if(less(a[j],a[min])){
                    min=j;
                }
            }
            exch(a,i,min);

        }

    }

    public static void main(String[] args) throws Exception {
       Comparable a[]= generateArray(10,"int",100);
        AlgorithmSuper as=new SelectSort();
        as.sortT(a);
    }

}
