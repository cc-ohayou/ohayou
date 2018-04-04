package com.cc.ccspace.facade.domain.common.constants;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/11/1 14:52.
 */
public class RedisSettleTradeErrorKey {

    public static String getTradeKey(String merchantId, String tradeId) {
        return "PHOENIX_SETTLE_TRADE_" + merchantId + "_" + tradeId;
    }

    public static String getErrorNeedHandleTradeIdSetKey(String merchantId) {
        return "SET_PHOENIX_SETTLE_ERROR_TRADE_" + merchantId;
    }
}
