package com.cc.ccspace.facade.domain.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class StockCodeUtil {
    private static BigDecimal LIMIT_UP_RATIO = BigDecimal.valueOf(1.1);
    private static Logger logger = LoggerFactory.getLogger(StockCodeUtil.class);

    public static String getFullCode(String code) {
        String fullCode = null;
        if (code.startsWith("6")) {
            fullCode = "sh" + code;
        } else {
            fullCode = "sz" + code;
        }
        return fullCode;
    }

    /**
     * @description 获取买入价
     * @author CF create on 2017/9/22 15:16
     */
    public static BigDecimal getBuyHangPrice(String stockName, BigDecimal yesPrice) {
        BigDecimal ratio;
        if (stockName == null) {
            logger.error("stock name is null,please check");
            stockName = "中国银行";
        }
        if (stockName.matches("(\\*|(?i)s).*")) {
            ratio = new BigDecimal(1.045);
        } else {
            ratio = new BigDecimal(1.095);
        }
        BigDecimal price = yesPrice.multiply(ratio);
        return NumberUtil.decimalHalf2Up(price);
    }

    /**
     * @description 获取卖出价
     * @author CF create on 2017/9/22 15:17
     */
    public static BigDecimal getSoldHangPrice(String stockName, BigDecimal yesPrice) {
        BigDecimal ratio;
        if (stockName == null) {
            logger.error("stock name is null,please check");
            stockName = "中国银行";
        }
        if (stockName.matches("(\\*|(?i)s).*")) {
            ratio = new BigDecimal(0.955);
        } else {
            ratio = new BigDecimal(0.905);
        }
        BigDecimal price = yesPrice.multiply(ratio);
        return NumberUtil.decimalHalf2Up(price);
    }

    //    委托相关 暂时不用
    public static BigDecimal maxWtPrice(String stockName, BigDecimal yesPrice) {
        if (stockName == null) {
            logger.error("stock name is null,please check");
            stockName = "中国银行";
        }
        if (stockName.matches("(\\*|(?i)s).*")) {
            return NumberUtil.decimalHalf2Up(yesPrice.multiply(new BigDecimal(1.05)));
        } else {
            return NumberUtil.decimalHalf2Up(yesPrice.multiply(new BigDecimal(1.09)));
        }
    }

    //    委托相关 暂时不用
    public static BigDecimal minWtPrice(String stockName, BigDecimal yesPrice) {
        if (stockName == null) {
            logger.error("stock name is null,please check");
            stockName = "中国银行";
        }
        if (stockName.matches("(\\*|(?i)s).*")) {
            return NumberUtil.decimalHalf2Up(yesPrice.multiply(new BigDecimal(0.95)));
        } else {
            return NumberUtil.decimalHalf2Up(yesPrice.multiply(new BigDecimal(0.90)));
        }
    }

    //判断当前价是否到达涨停价  采用四舍五入
    // 极端情况譬如 当前价 14.23（涨停价）    12.94 昨日收盘价  12.94*1.1 四舍五入<=14.23
    // 四舍五入有才符合涨停判断比例1.1
    public static Boolean nowPriceReachLimitUp(BigDecimal nowPrice, BigDecimal yesPrice) {
        return yesPrice.multiply(LIMIT_UP_RATIO).setScale(2, BigDecimal.ROUND_HALF_UP).compareTo(nowPrice) <= 0;
    }

    public static void main(String[] args) {
        BigDecimal nowPrice = BigDecimal.valueOf(9.79);
        BigDecimal yesPrice = BigDecimal.valueOf(8.90);
        System.out.println(nowPriceReachLimitUp(nowPrice,yesPrice));
    }
}
