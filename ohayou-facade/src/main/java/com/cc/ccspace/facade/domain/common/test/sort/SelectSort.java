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


    /**
     * 把最小元素往前送
     * @param a
     */
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


    /**
     * 把最大元素往后送
     * @param a
     */
    public  static void sortSelect(Comparable[] a){

        int len=a.length;
        for(int i=0;i<len;i++){
            int max=i;
            //选择一个下标  假定对应元素为最大元素
            for(int j=0;j<len-i;j++){
                //如果发现比假定元素更大的 更新最大元素的下标，否则继续找寻下一个
                if(less(a[max],a[j])){

                    max=j;
                }
            }
            //一轮结束 交换此轮选出的最大元素到数组len-i位置
            //  i为已经选出的最大元素个数=（已进行轮数）=外循环（一层循环）次数
            exch(a,len-i,max);


        }

    }

    public static void main(String[] args) throws Exception {
       Comparable a[]= generateArray(10,"int",100);
      /*  AlgorithmSuper as=new SelectSort();
        as.sortT(a);*/
        selectSort(a);
      show(a);

    }

}
