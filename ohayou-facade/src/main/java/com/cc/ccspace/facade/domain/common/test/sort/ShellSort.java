package com.cc.ccspace.facade.domain.common.test.sort;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/26 21:42.
 */
public class ShellSort extends AlgorithmSuper {

    @Override
    public boolean showFlag() {
        return false;
    }

    @Override
    public  void sort(Comparable[] a){

        int n=a.length;
        int h=1;
        while(h<n/3){
            h=3*h+1;
        }
        while(h>=1){
            for(int k=h;k<n;k++){
                for(int j=k;j>=h&&less(a[j],a[j-h]);j-=h){
                    exch(a,j,j-h);
                }
            }
            h=h/3;
        }
    }

    public static void main(String[] args) {
        Comparable[] a=generateArray(10,"int",100);
        AlgorithmSuper as=new ShellSort();
        as.sortT(a);

    }
}
