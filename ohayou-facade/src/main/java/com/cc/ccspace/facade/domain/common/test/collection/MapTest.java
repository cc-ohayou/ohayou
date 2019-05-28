package com.cc.ccspace.facade.domain.common.test.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2019/4/22/022 17:20.
 */
public class MapTest {

    public static void main(String[] args) {
        testHashMap();
    }

    private static void testHashMap() {
        Map<String,Object> map=new HashMap<>(8);
        for (int i = 0; i <10 ; i++) {
            String str=String.valueOf(i);
            map.put(str,str);
        }
    }
}
