package com.cc.ccspace.facade.domain.common.constants;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/6/22 16:00.
 */
public class RedisConstants {
    /**
     * redis 全局配置
     */
    public static final int REDIS_RETRY_COUNT = 10;
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    public final static int MAX_ACTIVE = 1024;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    public final static int MAX_IDLE = 200;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    public final static int MAX_WAIT = 10000;
    public final static int TIMEOUT = 10000;
    //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
    public final static int MIN_IDLE_TIME = 60 * 1000 * 5;//5分钟释放
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    public final static boolean TEST_ON_BORROW = true;

    /**
     * sass全局系统领域 相关常量
     */
    //全局id生成器 master key 前缀
    public static final String PREFIX_SYSTEM_ID = "SYS_ID_";

    //登陆拦截路径
    public static final String LOGIN_AUTH = "phoenix_login_auth";

    /**
     * 股票领域 相关常量
     */
    public static final String PHOENIX_STOCK_INFO_CACHE = "PHOENIX_STOCK_INFO_CACHE";// REDIS_股票实时行情
    //控制股票开市校验 以及停牌校验的rediskey
    public static final String STRATEGY_TEST_SIGN_SWITCH = "PHOENIX_STRATEGY_TEST_SIGN_SWITCH";
    //股票行情信息定时规则
    public static final String PHOENIX_STOCK_MARKET_UPDATE_CRON = "PHOENIX_STOCK_MARKET_UPDATE_CRON";
    //股票基本信息每天一次加载 定时规则
    public static final String PHOENIX_STOCK_BASE_UPDATE_CRON = "PHOENIX_STOCK_BASE_UPDATE_CRON";
    //股票基本信息定时任务 一天执行一次
    public static final String PHOENIX_STOCK_BASE_UPDATE_SIGN = "PHOENIX_STOCK_BASE_UPDATE_SIGN";
    //key的超时时间  早十分钟不到一天
    public static final int PHOENIX_STOCK_BASE_UPDATE_EXPIRE_TIME = 85800;
    //token错误更新失败时 重新更新token信息
    public static String PHOENIX_STOCK_TOKEN_ERROR_COU = "PHOENIX_STOCK_TOKEN_ERROR_COU";
    //超时时间60s
    public static int PHOENIX_STOCK_TOKEN_ERROR_COU_EXPIRE = 60;

    //热门股票key
    public static final String PHOENIX_HOT_STOCKS ="PHOENIX_HOT_STOCKS" ;
    //股票行情的开关key 测试用
    public static final String STOCK_MARKET_SWITCH = "PHOENIX_STOCK_MARKET_SWITCH";

    // 所有交易成功的策略
    public static final String PHOENIX_ALL_SUCCESS_STRATEGIES = "PHOENIX_ALL_SUCCESS_STRATEGIES";
    //结算定时的标识
    public static final String PHOENIX_SETTLE_TASK_SIGN = "PHOENIX_SETTLE_TASK_SIGN";
    //结算定时的cron规则
    public static final String PHOENIX_STRATEGY_SETTLE_CRON = "PHOENIX_STRATEGY_SETTLE_CRON";
    public static final String HS_TOKEN_AUTH_TYPE = "HS_TOKEN_AUTH_TYPE";

    //休市后自动撤销买入中卖出中的交易
    public static final String PHOENIX_AUTO_CANCEL_TRADE_CRON = "PHOENIX_AUTO_CANCEL_TRADE_CRON";

    /**
     * 合伙人相关
     */
    public static final String DAILY_COMMISSION_STATISTIC = "phoenix-partner-dailycommission-task-sign";
    //默认每天下午16点40 50各执行一次任务
    public static final String DAILY_COMMISSION_STATISTIC_CRON = "phoenix-partner-dailycommission-task-cron";
    //周五标识
    public static final String COMMISSION_FRIDAY = "phoenix-bg-dailycommission-friday";
    /**
     * 后台管理每日统计标识
     */
    public static String BgHomePageDailyStatisticSign = "BgHomePageDailyStatisticSign";
    //1800s每半小时过期 每半小时执行redis中可以随时更改key来执行定时任务
    public static int BG_HOME_STATIC_EXPIRE = 1800;


    /**
     * 验证码相关常量
     */
    //验证码失效时间（单位秒）
    public static final int VERIFY_CODE_EXPIRE_TIME = 60;

    //验证码错误次数起始为1
    public static final int VERIFY_CODE_ERROR_COUNT_START_ONE = 1;

    //验证码错误次数结束为5
    public static final int VERIFY_CODE_ERROR_COUNT_END_FIVE = 5;

    public static final int SEND_MESSAGE_GLOBLE_EXPIRE_TIME = 60*60*24;

    public static final int SEND_MESSAGE_EXPIRE_TIME = 60*60;

    public static final String SEND_MESSAGE_COUNT_START_ONE = "1";

}
