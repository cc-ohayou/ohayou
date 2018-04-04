package com.cc.ccspace.facade.domain.common.util.logutil;


import org.springframework.util.StopWatch;

import java.util.UUID;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/7/26 18:05.
 */
public class UUIDGenerator {


    /**
     * 获得一个UUID  返回32位十六进制数
     * 例如：ad6c7aafbc354ae89d0d2e685b9d0b16
     *
     * @return String UUID
     */
    public static String getUUID() {
        String s = UUID.randomUUID().toString();
        // 去掉“-”符号 这个效率更高 return s.replaceAll("-", "");
       /* return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
                + s.substring(19, 23) + s.substring(24);*/
        return s.replaceAll("-", "");
    }

    public static String getUUID2() {
        String s = UUID.randomUUID().toString();
        return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18)
                + s.substring(19, 23) + s.substring(24);
    }

    public static void main(String[] args) {
//      testWhoIsFaster(1000000);

    }

    /**
     * @param
     * @return
     * @description 测试哪种更快
     * @author CF create on 2017/7/26 18:16
     */
    private static void testWhoIsFaster(int count) {
        StopWatch sw = new StopWatch();
        sw.start();
        for (int i = 0; i < count; i++) {
            getUUID();
        }
        sw.stop();
        System.out.println("getUUID:" + sw.getLastTaskTimeMillis());


        sw.start();
        for (int i = 0; i < count; i++) {
            getUUID2();
        }
        sw.stop();
        System.out.println("getUUID2:" + sw.getLastTaskTimeMillis());
    }
}
