package com.cc.ccspace.facade.domain.common.test.algs;

import java.util.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/5/20/020 15:52.
 */
public class TwoSumTest {


    public static void main(String[] args) {
        int[] a={1,3,10,13,4,5,8,7,2,6,-1};
        int target=9;

        Map map=new HashMap<>();
        Map resultMap=new HashMap();
        for (int i=0;i<a.length;i++) {
            int temp=target-a[i];
            if(map.containsKey(temp)){
                resultMap.put(temp,a[i]);
            }else{
                map.put(a[i],i);
            }
        }

         Set<Map.Entry> entrys  = resultMap.entrySet();
        for (Map.Entry twoSum:
        entrys) {
            System.out.println(twoSum.getKey()+"-"+twoSum.getValue());
        }
    }
}
