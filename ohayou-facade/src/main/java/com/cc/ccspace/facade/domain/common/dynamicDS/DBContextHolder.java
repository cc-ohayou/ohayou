package com.cc.ccspace.facade.domain.common.dynamicDS;

public class DBContextHolder{  
    public static final String DATA_SOURCE_CQ = "dataSourceCQ";  
    public static final String DATA_SOURCE_HI = "dataSourceHI";  
    public static final String DATA_SOURCE_JX = "dataSourceJX";
    public static final String DATA_SOURCE_JT = "dataSourceJT";
    public static final String DATA_SOURCE_NATION= "dataSourceNation";
    private static final ThreadLocal<String> dbtype = new ThreadLocal<String>();
    public static void setDBType(String dbType) {
        dbtype.set(dbType);
    }
    public static String getDBType() {  
        return dbtype.get();
    }
    /**  * describe: 每次前端访问后台时设置一个当前线程的count用于存储对于内部来讲是第几次访问
         * 1次以上的视为是本次线程内第二次调用方法，则不进行拦截判断
    	 * @author CAI.F
    	 * @date:  日期:2017/3/8 时间:11:54
    	 * @param
    	 */
    public static void setVisitCount(String count) {
        dbtype.set(count);
    }
    public static String getVisitCount(){
        return dbtype.get();
    }
    public static void clearDBType() {
        dbtype.remove();
    }  
}  