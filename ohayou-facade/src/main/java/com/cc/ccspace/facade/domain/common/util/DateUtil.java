package com.cc.ccspace.facade.domain.common.util;

import com.cc.ccspace.facade.domain.common.constants.ExceptionCode;
import com.cc.ccspace.facade.domain.common.exception.BizException;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/9/20 10:37.
 */
public class DateUtil {
    /**
     * @description 获取线程安全的标准日期格式
     * @author CF create on 2017/9/20 10:57
     */
    public static final ThreadLocal<DateFormat> standardFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    public static final ThreadLocal<DateFormat> ymdFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static final ThreadLocal<DateFormat> mdhmFormat = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("MM-dd HH:mm");
        }
    };

    /**
     * @description 根据传入日期判断是否是周末
     * @author CF create on 2017/9/20 10:38
     */
    public static boolean isWeekend(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfweek = c.get(7);
        return (dayOfweek == 1) || (dayOfweek == 7);
    }

    /**
     * @description 判断今天是否是周末
     * @author CF create on 2017/9/20 10:38
     */
    public static boolean isWeekendToday() {
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfweek = c.get(7);
        return (dayOfweek == 1) || (dayOfweek == 7);
    }

    /**
     * @description 验证是否是同一日期
     * @author CF create on 2017/9/20 11:15
     */
    public static boolean isSameDay(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return (c1.get(6) == c2.get(6)) &&
                (c1.get(1) == c2.get(1));
    }

    public static int getDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(6);
    }

    /**
     * 连连用
     * @return
     */
    public static String getDateTimeForLL() {
        return formatDate(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param pattern 时间格式
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * @description 输出年月日时分秒
     * @author CF create on 2017/9/24 20:41
     */
    public static String standardFormat(Date date) {
        return standardFormat.get().format(date);
    }

    /**
     * @description 输出年月日
     * @author CF create on 2017/9/24 20:41
     */
    public static String ymdFormat(Date date) {
        return ymdFormat.get().format(date);
    }

    /**
     * @description 输出月日 时分
     * @author CF create on 2017/9/25 15:25
     */
    public static String mdhmFormat(Date date) {
        return mdhmFormat.get().format(date);
    }


    public static Date getTodayTime(int time) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, time);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date date = c.getTime();
        return date;
    }

    //获取当前日期的前一天时间，后期可优化
    public static Date getBeforeTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date dBefore = calendar.getTime();
        return dBefore;
    }

    //获取前N天或者后N天时间
    public static Date getDaysBeforeAfterTime(int dayIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, dayIndex);
        Date dBefore = calendar.getTime();
        return dBefore;
    }

    /**
     * 判断两个日期中间相差几天
     * @param fDate
     * @param oDate
     * @return
     */
    public static Integer daysOfTwo(Date fDate, Date oDate) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(fDate);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(oDate);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2) { //不同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {//闰年
                    timeDistance += 366;
                } else {//不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1) + 1;
        } else { //同年
            return day2 - day1 + 1;
        }
    }

    /**
     * 调取方法
     *
     * @param year  年
     * @param month 月
     * @param weeks 第几周
     * @return 返回第几周的开始与结束日期
     */
    public static Map<String, Object> getScopeForWeeks(int year, int month, int weeks) throws BizException {
        Map<String, Object> map = new HashMap<>();
        String time = year + "-" + getMonthToStr(month);
        Map<String, Object> result = getDateScope(time);
        //获取天数和周数
        int resultDays = Integer.parseInt(result.get("days").toString());
        int resultWeeks = Integer.parseInt(result.get("weeks").toString());
        /**
         * 如果参数周数大于实际周数
         * 则返回一个不存在的日期
         * 默认设置为当前 天数+1
         */
        if (weeks > resultWeeks) {
            throw new BizException(ExceptionCode.BIZ_ERROR, "周数非法");
           /* int days = resultDays + 1;
            String beginDate = year + "-" + getMonthToStr(month) + "-" + days;
            map.put("beginDate", beginDate);
            map.put("endDate", beginDate);*/
        } else {
            //获取当月第一天属于周几
            Map<Integer, Object> scopes = getScope(time, resultDays, weeks);
            map = (Map<String, Object>) scopes.get(weeks);
        }
        return map;
    }

    /**
     * 获取某年某月的天数以及周数
     *
     * @return
     * @param(格式：yyyy-MM)
     */
    private static Map<String, Object> getDateScope(String time) {
        Map<String, Object> map = new HashMap<>();
        try {
            //保证日期位数
            if (time.length() <= 7) {
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(time + "-01");
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                //获取天数
                int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);

                //获取当前月周数
                int weeks = getMaxWeeks(c);
                map.put("days", days);
                map.put("weeks", weeks);
            } else {
                throw new RuntimeException("日期格式不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @description 根据当月第一天日期判断本月最大周数
     * 不是get(Calendar.WEEK_OF_MONTH) 这个永远都只有五周或4周
     * @author CF create on 2017/11/1 15:59
     */
    public static int getMaxWeeks(Calendar c) {
        //如果当月开头是周五之后（周六、周日） 且有三十一天则认为有6周   如果开头是周六之后且有30天认为是6周
        //如果是29天则必然是五周  如果是28天第一天是周一则最大周算是四周 否则是五周
        int weekDay = c.get(Calendar.DAY_OF_WEEK);
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days == 31 && (weekDay == 7 || weekDay == 1)) {
            return 6;
        } else if (days == 30 && weekDay == 1) {
            return 6;
        } else if (days == 28 && weekDay == 2) {
            return 4;
        } else {
            return 5;
        }
    }

    public static int getNowMonthFisrtDayWeekDay(int year, int month) {
        String time = year + "-" + getMonthToStr(month);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(time + "-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取本月周区间
     *
     * @param date(yyyy-mm)日期
     * @param days            天数
     * @param weeks           周数
     * @return
     */
    private static Map<Integer, Object> getScope(String date, int days, int weeks) {
        Map<Integer, Object> map = new HashMap<>();
        //遍历周数
        int midNum = 0;
        for (int i = 1; i <= weeks; i++) {
            //第一周
            if (i == 1) {
                String time = date + "-01";
                String week = getWeekOfDate(time);
                //获取第一周还有多少天
                int firstDays = getSurplusDays(week);
                //获取第一周结束日期
                int endDays = 1 + firstDays;
                String time2 = date + "-0" + endDays;
                Map<String, Object> data = new HashMap<>();
                data.put("beginDate", time);
                data.put("endDate", time2);
                map.put(i, data);
                //
                midNum = endDays;
            } else {
                //当前日期数+7 比较 当月天数
                if ((midNum + 7) <= days) {
                    int beginNum = midNum + 1;
//					System.out.println("begin:"+beginNum);

                    int endNum = midNum + 7;
//					System.out.println("end:"+endNum);

                    String time1 = date + "-" + getNum(beginNum);
                    String time2 = date + "-" + getNum(endNum);
                    Map<String, Object> data = new HashMap<>();
                    data.put("beginDate", time1);
                    data.put("endDate", time2);
                    map.put(i, data);
                    midNum = endNum;
                } else {
                    int beginNum = midNum + 1 < days ? midNum + 1 : days;
                    int endNum = days;
                    String time1 = date + "-" + getNum(beginNum);
                    String time2 = date + "-" + getNum(endNum);
                    Map<String, Object> data = new HashMap<>();
                    data.put("beginDate", time1);
                    data.put("endDate", time2);
                    map.put(i, data);
                    midNum = endNum;
                }
            }
        }

        return map;
    }

    /**
     * 获取日期属于周几
     *
     * @param time(格式：yyyy-MM-dd)
     * @return
     * @throws Exception
     */
    private static String getWeekOfDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String week = "";
        try {
            Date date = sdf.parse(time);
            String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0) {
                w = 0;
            }
            week = weekDays[w];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return week;
    }

    /**
     * 获取数字
     *
     * @return
     */
    private static String getNum(int num) {
        int result = num / 10;
        if (result == 0) {
            return "0" + num;
        } else {
            return num + "";
        }
    }

    /**
     * 返回月份的字符串
     *
     * @param month
     * @return
     */
    public static String getMonthToStr(int month) {
        String str = "";
        switch (month) {
            case 1:
                str = "01";
                break;
            case 2:
                str = "02";
                break;
            case 3:
                str = "03";
                break;
            case 4:
                str = "04";
                break;
            case 5:
                str = "05";
                break;
            case 6:
                str = "06";
                break;
            case 7:
                str = "07";
                break;
            case 8:
                str = "08";
                break;
            case 9:
                str = "09";
                break;
            case 10:
                str = "10";
                break;
            case 11:
                str = "11";
                break;
            case 12:
                str = "12";
                break;
            default:
                str = "0";
                break;
        }
        return str;
    }

    /**
     * 根据当前周几判断当前周还有几天
     *
     * @param week{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}
     * @return
     */
    private static int getSurplusDays(String week) {
        int num = 0;
        if ("星期日".equals(week)) {
            num = 0;
        } else if ("星期一".equals(week)) {
            num = 6;
        } else if ("星期二".equals(week)) {
            num = 5;
        } else if ("星期三".equals(week)) {
            num = 4;
        } else if ("星期四".equals(week)) {
            num = 3;
        } else if ("星期五".equals(week)) {
            num = 2;
        } else if ("星期六".equals(week)) {
            num = 1;
        }
        return num;
    }

    /**
     * @description 根据当月第一天周几获取当前日期应该加上的天数
     * @author CF create on 2017/11/1 15:20
     */
    public static int getSurplusDays(int week) {
        int num;
        if (week == 1) {
            num = 6;
        } else {
            num = week - 3;
        }
        return num;
    }

    /**
     *判断当前时间是否在当天16:00之前
     * @param
     * @return
     */
    public static boolean compareDate() {
        Date now = new Date();
        String a  = ymdFormat(now);
        String mid = a+" 16:00:00";
        Date midDate = null;
        try {
            midDate = standardFormat.get().parse(mid);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (now.getTime() >= midDate.getTime()){
            return false;
        }
        else {
            return true;
        }
    }

    public static int getNowRealWeekNo(int days) {
        return days / 7 + 1;
    }


    public static void main(String[] args) throws ParseException, BizException {
  /*      DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2017-11" + "-06");
        Date date2 = sdf.parse("2017-10" + "-02");
        Calendar c = Calendar.getInstance();
        Calendar c1 = Calendar.getInstance();
        c.setTime(date);
        c1.setTime(date2);
        System.out.println(c.get(Calendar.DAY_OF_WEEK));
        System.out.println(c.get(Calendar.DAY_OF_MONTH));
       int weekDay= getNowMonthFisrtDayWeekDay(2017,10);
       int addDays = getSurplusDays(weekDay);

       int no= getNowRealWeekNo(c1.get(Calendar.DAY_OF_MONTH)+addDays);
        System.out.println("第几周"+no);*/
//        LocalDate initialDate = LocalDate.parse("2017-07-20");
//        System.out.println(getScopeForWeeks(2017, 12, 5));
//        System.out.println(isLastDayOfMonth());
        getDayOfYearFirstAndLast(1);
        System.out.println(LocalDate.now().compareTo(LocalDate.now()));

    }

    public static boolean isLastDayOfMonth() {
        LocalDate now = LocalDate.now();
        LocalDate lastDay = now.with(TemporalAdjusters.lastDayOfMonth());
        return now.compareTo(lastDay) == 0;
    }

    public static Map<String, LocalDate> getDayOfYearFirstAndLast(int yearToAdd) {
        LocalDate now = LocalDate.now();
        LocalDate nextYear = now.plusYears(yearToAdd);
        LocalDate firstDay = nextYear.with(TemporalAdjusters.firstDayOfYear());
        LocalDate lastDay = nextYear.with(TemporalAdjusters.lastDayOfYear());
        Map<String, LocalDate> pair = new HashMap<>(2);
        pair.put("firstDay", firstDay);
        pair.put("lastDay", lastDay);
        return pair;
    }

    public static Date transLocalDateToDate(LocalDate dutyDate) {
        return Date.from(dutyDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
