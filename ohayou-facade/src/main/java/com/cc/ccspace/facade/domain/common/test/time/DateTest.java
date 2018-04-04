package com.cc.ccspace.facade.domain.common.test.time;


import com.cc.ccspace.facade.domain.common.util.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/7/31 10:52.
 */
public class DateTest {
    public static void main(String[] args) {
//    localDateTimeTest();
        System.out.println(new StringBuilder("预计到账时间：")
                .append(getArrivalTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .append("前").toString());
        transferDateTimeToMilliSeconds();
//    simpleTest();
//        System.out.println(getScopeForweeks(2017,7,1));


    }

    public static void transferDateTimeToMilliSeconds(){

        LocalDateTime lt= LocalDateTime.parse("2017-08-31T00:09:00");
        Long  time=lt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        System.out.println(time);
    }

    private static void localDateTimeTest() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
        System.out.println("默认格式化: " + now);
        System.out.println("自定义格式化: " + now.format(dateTimeFormatter));
        System.out.println("自定义格式化2: " + now.format(dateTimeFormatter2));
        System.out.println(LocalDateTime.MAX);
        Calendar cal = Calendar.getInstance();
        System.out.println(DateUtil.ymdFormat.get().format(cal.getTime()));
        System.out.println(LocalTime.parse("23:59:59"));
        Instant instant = cal.getTime().toInstant();
        ZoneId zone = ZoneId.systemDefault();
        System.out.println( LocalDateTime.ofInstant(instant, zone));
    }

    private static LocalDateTime getArrivalTime() {
        Calendar cal = Calendar.getInstance();
        LocalDate nowDate = LocalDate.now();
        LocalTime now = LocalTime.now();
        if (now.compareTo(LocalTime.parse("18:00")) < 0) {//6点前提现当日到账
            return LocalDateTime.of(nowDate, LocalTime.parse("23:59:59"));
        } else {//下一个交易日到账
            Instant instant = cal.getTime().toInstant();
            ZoneId zone = ZoneId.systemDefault();
            LocalDate nextTradeDate = LocalDateTime.ofInstant(instant, zone).toLocalDate();
            return LocalDateTime.of(nextTradeDate, LocalTime.parse("23:59:59"));
        }
    }
    private static void simpleTest() {
        Calendar c = Calendar.getInstance();
        System.out.println(c.get(Calendar.YEAR));
        System.out.println(c.get(Calendar.MONTH));
        System.out.println(c.get(Calendar.WEEK_OF_MONTH));
        c.set(Calendar.YEAR, 2017); // 2010年
        c.set(Calendar.MONTH, 6); // 6 月
        c.set(Calendar.WEEK_OF_MONTH,1);
        System.out.println("------------" + c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月的天数和周数-------------");
        System.out.println("天数：" + c.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println("周数：" + c.getActualMaximum(Calendar.WEEK_OF_MONTH));
        System.out.println("第一天：" + c.getTime());
        System.out.println("第一天：" + c.getTimeInMillis());
        System.out.println("第一天：" + DateUtil.ymdFormat.get().format(c.getTime()));
        c.set(Calendar.WEEK_OF_MONTH,2);
        System.out.println("第一天：" + c.getTime());
        System.out.println("第一天：" + c.getTimeInMillis());
        System.out.println("第一天：" + DateUtil.ymdFormat.get().format(c.getTime()));
    }

    /**
     * 调取方法
     * @param year 年
     * @param month 月
     * @param weeks 第几周
     * @return 返回第几周的开始与结束日期
     */
    public static Map<String,Object> getScopeForweeks(int year,int month,int weeks){
        Map<String,Object> map=new HashMap<String, Object>();
        String time=year+"-"+getMonthToStr(month);
        Map<String, Object> result = getDateScope(time);
        //获取天数和周数
        int resultDays=Integer.parseInt(result.get("days").toString());
        int resultWeeks=Integer.parseInt(result.get("weeks").toString());
        /**
         * 如果参数周数大于实际周数
         * 则返回一个不存在的日期
         * 默认设置为当前 天数+1
         */
        if(weeks>resultWeeks){
            int days=resultDays+1;
            String beginDate=year+"-"+getMonthToStr(month)+"-"+days;
            map.put("beginDate",beginDate);
            map.put("endDate",beginDate);
        }else{
            //获取当月第一天属于周几
            Map<Integer, Object> scopes = getScope(time, resultDays, resultWeeks);
            map=(Map<String,Object>)scopes.get(weeks);
        }
        return map;
    }
    /**
     *获取某年某月的天数以及周数
     * @param(格式：yyyy-MM)
     * @return
     */
    private static Map<String,Object> getDateScope(String time){
        Map<String,Object> map=new HashMap<String, Object>();
        try{
            //保证日期位数
            if(time.length()<=7){
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date=sdf.parse(time+"-01");
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                //获取天数
                int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                //获取周数
                int weeks = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
                map.put("days",days);
                map.put("weeks",weeks);
            }else {
                throw new RuntimeException("日期格式不正确");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }
    /**
     *获取本月周区间
     * @param date(yyyy-mm)日期
     * @param days 天数
     * @param weeks 周数
     * @return
     */
    private static Map<Integer,Object> getScope(String date, int days, int weeks){
        Map<Integer,Object> map=new HashMap<Integer, Object>();
        //遍历周数
        int midNum=0;
        for(int i=1;i<=weeks;i++){
            //第一周
            if(i==1){
                String time=date+"-01";
                String week=getWeekOfDate(time);
                //获取第一周还有多少天
                int firstDays = getSurplusDays(week);
                //获取第一周结束日期
                int endDays=1+firstDays;
                String time2=date+"-0"+endDays;
                Map<String,Object> data=new HashMap<String, Object>();
                data.put("beginDate",time);
                data.put("endDate",time2);
                map.put(i,data);
                //
                midNum=endDays;
            }else{
                //当前日期数+7 比较 当月天数
                if((midNum+7)<=days){
                    int beginNum=midNum+1;
                    System.out.println("begin:"+beginNum);

                    int endNum=midNum+7;
                    System.out.println("end:"+endNum);

                    String time1=date+"-"+getNum(beginNum);
                    String time2=date+"-"+getNum(endNum);
                    Map<String,Object> data=new HashMap<String, Object>();
                    data.put("beginDate",time1);
                    data.put("endDate",time2);
                    map.put(i,data);
                    midNum=endNum;
                }else{
                    int beginNum=midNum+1;
                    int endNum=days;
                    String time1=date+"-"+getNum(beginNum);
                    String time2=date+"-"+getNum(endNum);
                    Map<String,Object> data=new HashMap<String, Object>();
                    data.put("beginDate",time1);
                    data.put("endDate",time2);
                    map.put(i,data);
                    midNum=endNum;
                }
            }
        }

        return map;
    }
    /**
     *获取日期属于周几
     * @param time(格式：yyyy-MM-dd)
     * @return
     * @throws Exception
     */
    private static String getWeekOfDate(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String week="";
        try{
            Date date=sdf.parse(time);
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;
            week= weekDays[w];
        }catch(Exception e){
            e.printStackTrace();
        }
        return week;
    }
    /**
     * 获取数字
     * @return
     */
    private static String getNum(int num){
        int result=num/10;
        if(result==0){
            return "0"+num;
        }else{
            return num+"";
        }
    }
    /**
     * 返回月份的字符串
     * @param month
     * @return
     */
    private static String getMonthToStr(int month){
        String str="";
        switch (month){
            case 1:
                str="01";
                break;
            case 2:
                str="02";
                break;
            case 3:
                str="03";
                break;
            case 4:
                str="04";
                break;
            case 5:
                str="05";
                break;
            case 6:
                str="06";
                break;
            case 7:
                str="07";
                break;
            case 8:
                str="08";
                break;
            case 9:
                str="09";
                break;
            case 10:
                str="10";
                break;
            case 11:
                str="11";
                break;
            case 12:
                str="12";
                break;
        }
        return str;
    }
    /**
     * 根据当前周几判断当前周还有几天
     * @param week{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}
     * @return
     */
    private static int getSurplusDays(String week){
        int num=0;
        if("星期日".equals(week)){
            num=0;
        }else if("星期一".equals(week)){
            num=6;
        }else if("星期二".equals(week)){
            num=5;
        }else if("星期三".equals(week)){
            num=4;
        }else if("星期四".equals(week)){
            num=3;
        }else if("星期五".equals(week)){
            num=2;
        }else if("星期六".equals(week)){
            num=1;
        }
        return num;
    }
}
