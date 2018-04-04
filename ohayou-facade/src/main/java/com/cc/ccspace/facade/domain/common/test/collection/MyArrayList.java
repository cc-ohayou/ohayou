package com.cc.ccspace.facade.domain.common.test.collection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/4/24 21:59.
 */
public class MyArrayList<E> {

    private int  size;
    private Object[] elementData;
    private int modCount;
    private Object[] EMPTY_DATA={};
    private final int DEFAULLT_CAPACITY=10;

    public MyArrayList(){
        elementData=EMPTY_DATA;
    }

    public MyArrayList(int  len){
       if(len>0){
           elementData=new Object[len];
       }
       else if(len==0){
           elementData=EMPTY_DATA;
       }
       else{
           throw new IllegalArgumentException("IllegalArgument length"+len);
       }


    }

    private boolean add(E e){
        ensureCapacity(size+1);//size小于9之前这个方法其实是不会执行的
        elementData[size]=e;
        size++;
        return true;
    }
/**  * describe: 比较传入的要求数组大小 与原来的数组的length看数组还够不够用 不够的话扩容为原来的1.5倍
 *   取扩容与传入要求大小的最大值进行扩容 elementData
	 * @author CAI.F
	 * @date:  日期:2017/4/27 时间:16:48
	 * @param
	 */
    private void ensureCapacity(int minCapacity) {
        modCount++;
        if (elementData == EMPTY_DATA) {//空数组的话 下面的if条件必定成立 一定增长空间的
            // 不是空数组但是空间不够用也会走下面的if
            // 最终效果就是如果list原来是空的就扩容成默认的10大小
            // 不是的话按照旧的size的一半增幅扩容
            minCapacity=Math.max(DEFAULLT_CAPACITY,minCapacity);
        }
        if(minCapacity-elementData.length>0){//说明内存不够用了扩容
            int oldLen=elementData.length;
            int newCapacity=oldLen+(oldLen>>1);
            newCapacity=Math.max(minCapacity,newCapacity);
            growCapacity(newCapacity,elementData);//根据新的容量扩大数组
        }
    }

    private void growCapacity(int newCapacity,Object[] data) {
        Object[] newElementData=new Object[newCapacity];
        System.arraycopy(data,0,newElementData,0,size);
        elementData=newElementData;
    }

    private boolean add(int index,E e){
            checkIndex(index);
           ensureCapacity(size+1);//确保数组空间够用，如果是空的就进行初始扩容，
        // 大小为10，如果size+1超出了这个范围，按照旧有大小的1.5位扩容 一次将原来的数组数据复制到新的数组中 并重新赋予elementData
           System.arraycopy(elementData,index,elementData,index+1,size-index);
        elementData[index]=e;
        size++;
        return true;
    }

    private E remove(int index){
        checkIndex(index);
        E oldValue=elementData(index);
        System.arraycopy(elementData,index+1,elementData,index,size-index-1);
        elementData[--size]=null;
        modCount++;
        return oldValue;
    }

    private boolean remove(Object o){
        if(o==null){
            for(int i=0;i<elementData.length;i++){
                if(elementData[i]==null){
                    remove(i);
                    return true;
                }
            }
        }
        else{
            for(int i=0;i<elementData.length;i++){
                if(elementData[i]==o){
                    remove(i);
                    return true;
                }
            }
        }

        return false;
    }

    private int indexOf(Object o){
        int index=-1;
        if(o==null){
            for(int i=0;i<elementData.length;i++){
                if(elementData[i]==null){

                  index=i;
                    break;
                }
            }
        }
        else{
            for(int i=0;i<elementData.length;i++){
                if(elementData[i]==o){

                    index=i;
                    break;
                }
            }
        }

        return index;
    }

    public boolean contains(Object o){
        return indexOf(o)>=0;
    }

    private E get(int index){
        checkIndex(index);
        return elementData(index);
    }
    private E elementData(int index) {
        return (E)elementData[index];
    }

    private void checkIndex(int index) {
        if(index>elementData.length-1||index<0){
              throw new IndexOutOfBoundsException("index "+index+" Illegal!");
        }
    }
        public void clear(){
        for(int i=0;i<elementData.length;i++){
            elementData[i]=null;
        }
            modCount++;
            size=0;
    }


    public static void main(String[] args) throws IOException {
       MyArrayList<Integer> m=new MyArrayList<Integer>();
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
       for(int i=0;i<12;i++){
           System.out.println("请输入第"+i+"个数字:");
           String str=br.readLine();
        if(str.matches("\\d+")) {
               m.add(Integer.parseInt(str));
           }
           else{
               System.out.println("请输入正确的整数类型：");
               i--;
           }

       }


        System.out.println( m.toString());

        System.out.println(m.indexOf(99));
        System.out.println(m.get(7));
        System.out.println(m.contains(99));
        System.out.println(m.add(10,210));
        System.out.println(m.remove(10));
        Integer a=Integer.valueOf(10);
        Integer b=Integer.valueOf(10);
        System.out.println(m.remove(Integer.valueOf(10)));

    }



}
