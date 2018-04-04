package com.cc.ccspace.facade.domain.common.exception;

public enum ErrorEnum {
    // 系统
    SYSTEM_ERROR(999999, "系统异常"),
    SMS_ERROR(900001, "短信发送异常"),
    APP_NOT_EXIST(900002,"未找到APP"),
    APP_IDENTIFIER_NOT_EXIST(900003,"为了您的产品体验，请去官网下载最新版本 !"),
    APP_VERSION_NEED_UPDATE(900004,"为了您的产品体验，请升级至最新版本 !"),
    /**
     * saas平台全局系统日常
     */
    STEP_CONFIG_NOT_EXIST(600, "全局ID所需的【步长配置】未初始化或配置错误"),
    STEP_DB_INCREASE_ERROR(601, "全局ID数据库增长后数值和前值不应该相等"),

    /**
     * 策略相关
     */
    TOTAL_AMOUNT_LIMIT(1000, "您已达到策略持仓总额限制,请与客服联系,获取更多详情"),
    T1_AMOUNT_LIMIT(1001, "您已达到单股策略持仓总额限制,请与客服联系,获取更多详情"),
    TD_AMOUNT_LIMIT(1002, "您已达到单股策略持仓总额限制,请与客服联系,获取更多详情"),
    SINGLE_STOCK_LIMIT(1003, "个股风险控制不允许购买"),
    MAIN_STOCK_LIMIT(1005, "主板股票不允许购买"),
    ZXB_STOCK_LIMT(1006, "中小板股票不允许购买"),
    CYB_STOCK_LIMT(1007, "创业板股票不允许购买"),
    ST_STOCK_LIMT(1008, "S|ST|*ST|SST|S*ST股票不允许购买"),
    UP_RATIO_LIMIT(1009, "涨幅超过%s%%股票不允许购买"),
    DOWN_RATIO_LIMIT(1010, "跌幅超过%s%%股票不允许购买"),
    STOP_LOSS_NOT_INRANGE(1011, "止损价格超出范围"),
    STOP_PROFIT_LIMIT(1012, "跌幅超过%s%%股票不允许购买"),
    STRATEGY_CREATE_FAILED(1013, "策略创建失败"),
    STRATEGY_QUANTITY_ERROR(1014, "策略股票数量不正确"),
    STRATEGY_QUANTITY_TOO_LARGE(1015, "股票数量过大无法成交,请提高保证金或减小股票数量"),
    STOP_PROFIT_PRICE_LIMIT(1016, "止盈价格超出范围"),
    STRATEGY_NOT_EXISTS(1017, "策略不存在"),
    STRATEGY_SHOULDBE_UNPAIED(1018, "交易策略应为未支付"),
    STRATEGY_PAY_FAILED(1019, "策略支付失败"),
    STRATEGY_CANNOT_DEFER(1020, "策略未到到期日，无法递延"),
    TODAY_TRADE_CANNOT_SOLD(1021, "当日策略不能卖出"),
    STRATEGY_CANNOT_SOLD(1022, "请勿重复卖出策略"),
    STRATEGY_SOLD_FAILED(1023, "策略卖出失败或被重复卖出"),
    ALREADY_DEFER(1024, "策略已递延"),
    DEFER_STATUS_INVALID(1025, "买入成功的策略才可递延"),
    BUYOUT_CANNOT_DEFER(1026, "已买断策略不可递延"),
    SUSPENSION_CANNOT_DEFER(1027, "停牌策略不可手动递延"),
    STRATEGY_NOT_HOLDING(1028, "非持仓中策略，无法修改止盈止损"),
    STRATEGY_BUY_OUT_MODIFY_DENY(1029, "买断策略，无法修改止盈止损"),
    NEED_APPEND_DEPOSIT(1030, "需补充保证金"),
    MODIFY_DEPOSIT_NOT_ENOUGH(1031, "补入保证金不足"),
    MODIFY_LOSS_FAILED(1032, "止损修改失败"),
    MODIFY_PROFIT_FAILED(1033, "止盈修改失败"),
    APPEND_DEPOSIT_FAILED(1035, "保证金追加失败"),
    STRATEGY_FEE_NOT_EXISTS(1036, "策略费用信息不存在"),
    STRATEGY_FEE_RECORD_FAILED(1037, "策略费用记录添加失败"),
    RETURN_BALANCE_FAILED(1038, "撤销策略返还信用金时修改余额失败"),
    CLOSED_PROFIT_MODIFY_UNALLOWED(1039, "收盘结束前不允许修改止盈价"),
    STOP_PROFIT_MODIFY_FAILED(1040, "止盈价格修改失败"),
    PROFIT_MODIFY_PRICE_UNAVAILABLE(1041, "止盈价格不能低于现价"),
    NOW_PRICE_NOT_LIMIT_UP(1042, "策略未达到涨停价不能调整止盈"),
    PROFIT_PRICE_MAX_LIMIT(1043, "止盈价超出上限"),
    ALREADY_AUTO_DEFER(1044,"此策略已设自动递延"),
    ALREADY_CANCEL_DEFER(1045,"此策略已经取消了自动递延"),
    DEFER_NOT_INTIME(1046,"14:40之后不能递延"),
    STOP_STOCK_CANNOT_MODIFY_LOSS(1047, "停牌股票无法修改止损价"),
    SETTLE_STATUS_CANNOT_DEFER(1048, "已结算的策略不能递延"),

    /**
     * 交易领域相关
     */
    TRADE_NOT_EXISTS(1101, "交易不存在"),
    TRADE_ALREADY_CHANGED(1102, "交易状态变更，撤销失败"),
    TRADE_STATUS_NOT_ENTRUSTING(1103, "非委托中交易无法撤销"),
    TRADE_INVESTOR_NOT_EXIST(1104, "卖单对应投资人不存在"),
    TRADE_MERCHANT_NOT_EXIST(1105, "卖单对应商户不存在"),
    TRADE_TYPE_NOT_EXIST(1106, "未定义的交易状态"),
    INVESTOR_CAN_NOT_SELL(1107, "此投资人风控设置不能卖出"),

    /**
     * 股票领域相关
     */
    STOCK_STOP_PLATE(2000, "股票停牌中"),
    STOCK_CLOSED(2001, "股票休市中"),
    STOCK_NOT_ALLOW(2002, "股票变更不允许买入"),
    STOCK_PRICE_ERROR(2003, "股票价格错误"),
    STOCK_NOT_EXISTS(2004, "股票代码不存在"),
    STOCK_BASE_UPDATE_FAILED(2005, "股票基本信息更新失败"),

    /**
     * 商户系统领域 相关
     */
    RISKCONFIG_NOT_EXISTS(2100, "风控全局配置不存在"),
    RISKCONFIG_TO_OBJECT_FAILED(2101, "风控全局配置不存在"),
    STOCKCONFIG_NOT_EXISTS(2200, "个股配置不存在"),
    STOCKCONFIG_NOT_EXISTS_IN_REDIS(2201, "个股配置在Redis中不存在"),
    STOCKCONFIG_TO_OBJECT_FAILED(2201, "个股配置对象转换异常"),
    FEECONFIG_NOT_EXISTS(2300, "费用全局配置不存在"),
    FEECONFIG_TO_OBJECT_FAILED(2301, "费用配置对象转换异常"),
    STRATEGYCONFIG_NOT_EXISTS(2400, "策略全局配置不存在"),
    STRATEGYCONFIG_TO_OBJECT_FAILED(2401, "策略配置对象转换异常"),
    T1_FEE_CONFIG_NOT_EXISTS(2402, "商户t+1策略费用配置不存在"),
    TD_FEE_CONFIG_NOT_EXISTS(2403, "商户t+d策略费用配置不存在"),

    /**
     * 用户相关
     */
    AUTH_FAILED(3001, "Token Auth Failed"),
    USER_NOT_EXISTS(3002, "未找到对应用户"),
    USER_ALREADY_EXISTS(3010, "用户已经存在"),
    USER_NOT_HIMSELF(3011, "用户非本人"),
    USER_REGIST_FAILED(3012, "注册用户失败"),

    /**
     * 资金领域 相关
     */
    BALANCE_NOT_ENOUGH(3003, "余额不足,请充值"),
    BALANCE_UN_AVIALABLE(3004, "余额记录不存在或账户已被冻结，请充值或联系客服"),
    FUND_INSERT_FAILED(3005, "资金流水添加失败"),
    User_TRANSPWD_NOT_CORRECT(3006,"用户交易密码不正确"),
    WITHDRAW_STATUS_ERROR(3007,"用户提现状态不正确"),
    BALANCE_UN_WITHDRAW(3012, "提现需额外扣除2元手续费，当前余额不足"),
    WITHDRAW_NOT_ENOUGH(3013, "提现金额超过余额，无法提现"),
    WITHDRAW_REPEAT_ERROR(3014, "每日每张银行卡只允许同样金额提现一次"),

    /**
     *模拟资金相关
     */
    SIMU_BALANCE_NOT_ENOUGH(3008,"余额不足,创建失败"),
    SIMU_BALANCE_UN_AVIALABLE(3009, "余额记录不存在或账户已被冻结，请联系客服"),
    SUB_REPEAT(3014, "已订阅过该用户"),
    UNSUB_REPEAT(3014, "已取消订阅"),
    /**
     * 充值
     */
    DEPOSIT_NOT_CORRECT(4001, "充值金额不符"),


    SIMU_STRATEGY_ERROR(5001, "模拟策略操作错误"),

    /**
     * AccountService
     */
    JSON_IS_NULL(6001,"ACCOUNT SERVICE结果返回空");


    private int code;
    private String message;
    private Object[] params;

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    ErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    ErrorEnum(int code, String message, Object[] params) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }


}