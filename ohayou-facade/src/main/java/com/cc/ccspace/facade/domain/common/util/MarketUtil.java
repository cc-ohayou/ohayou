package com.cc.ccspace.facade.domain.common.util;

import java.util.Calendar;

/**
 * Created by Shangdu Lin on 2017/10/7 16:50.
 */
public class MarketUtil {
    private static boolean isMarketOpen(){
        Calendar cal = Calendar.getInstance();
        int nowHour = cal.get(Calendar.HOUR_OF_DAY);
        if(nowHour<9 || nowHour>=15){
            return false;
        }
        int miniute = cal.get(Calendar.MINUTE);
        if(nowHour==11 && miniute>=30){
            return false;
        }
        if(nowHour==12){
            return false;
        }
        return !(nowHour == 9 && miniute < 30);
    }

    /**
     * 判断是否是交易时间（包括中午休市时间）
     * @return
     */
    private static boolean isMarketOpen2(){
        Calendar cal = Calendar.getInstance();
        int nowHour = cal.get(Calendar.HOUR_OF_DAY);
        if(nowHour<9 || nowHour>=15){
            return false;
        }
        int miniute = cal.get(Calendar.MINUTE);
        return !(nowHour == 9 && miniute < 30);
    }

    /**
     * 判断是否是可交易的时间段
     *
     * @param isFullTradeTime  true：9：30~15：00
     * false： 9：30~11：30  13：00~15：00
     * @return
     */
    public static boolean isOpened(boolean isFullTradeTime){

        if(isFullTradeTime==true){ // 9：30~15:00
            return isMarketOpen2();
        }
        else{
            return isMarketOpen();
        }
    }

    public static boolean isOpened(){
        return isOpened(false);
    }

}
