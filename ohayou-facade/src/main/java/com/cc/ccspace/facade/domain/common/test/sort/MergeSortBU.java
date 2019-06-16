package com.cc.ccspace.facade.domain.common.test.sort;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/26 22:39.
 */
public class MergeSortBU extends AlgorithmSuper{

   private Comparable [] temp;
    @Override
    public boolean showFlag() {
        return false;
    }


    @Override
    public void sort(Comparable[] a) {
       /* temp=new Comparable[a.length];
        sort(a,0,a.length-1);*/

              int len=a.length;
        temp=new Comparable[len];
        for(int sz=1;sz<len;sz+=sz) {
            for (int lo = 0; lo < len - sz; lo += sz + sz) {
                merge(a, lo, lo + sz - 1, Math.min(sz + lo + sz - 1, len - 1));
            }
        }
            //两两归并 内循环从最小的子数组a[0]a[1]  a[2]a[3]将最底层的子数组一一合并
            //sz每增加一次 下一次的合并子数组容量增大一倍  合并次数减少1倍
            //2的倍数减少 直到合并的只剩下一个数组位置 最终完成归并 最后一次归并 merge(a,0,len/2,len-1);
       //lo+sz-1其实就是(len-1)/2 sz结束循环的条件是2*sz>len 因此sz=len/2+1  sz-1=len/2    hi=2sz+lo-1
            // merge里面的参数都是围绕着 int mid=lo+(hi-1o)/2来换算的}
    }



    public  void merge(Comparable[] a,int lo,int mid,int hi){
        int left=lo;
        int right=mid+1;
        for(int k=lo;k<=hi;k++){
            temp[k]=a[k];
        }
        for(int i=lo;i<=hi;i++){
            if(left>mid){//左半边元素用完
                a[i]=temp[right++];
            }
            else if(right>hi){
                a[i]=temp[left++];//右半边元素用完
            }
            else if(less(temp[right],temp[left])){//右半边元素小于左半边的
                a[i]=temp[right++];
            }
            else{
                a[i]=temp[left++];
            }
        }




    }
    public void sort(Comparable[] a,int lo,int hi){
       if(hi<=lo)
           return;
        int mid=lo+((hi-lo)>>1);
        sort(a,lo,mid);
        sort(a,mid+1,hi);
        merge(a,lo,mid,hi);

    }

    public static void main(String[] args) {
        Comparable [] a=generateArray(100000,"double",10);
        AlgorithmSuper as=new MergeSortBU();
        as.sortT(a);
    }
}
