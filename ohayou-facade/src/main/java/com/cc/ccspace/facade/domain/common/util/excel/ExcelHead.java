

package com.cc.ccspace.facade.domain.common.util.excel;


import com.cc.ccspace.facade.domain.common.constants.CommonConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Excl导出表格头部类
 *
 * @author guanhj
 * @version 2015年9月8日
 * @see ExcelHead
 */
public class ExcelHead {

    /**
     * 穿仓金额
     */
    public static  String[] WEARING_LIST_EXCEL_HEAD ={
            "策略单号:strategyId",
            "资金账号:accountId",
            "投顾uid:dmUid",
            "投顾姓名:dmName",
            "投顾手机:phone",
            "发布时间:createTime",
            "结算时间:settleTime",
            "客户经理:saleName",
            "股票代码:stockCode",
            "股票名称:stockName",
            "成交数量:dealQuantity",
            "保证金:totalDeposit",
            "初始保证金:deposit",
            "追加保证金:appendDeposit",
            "盈亏:profitLoss",
            "交易费:tradeFee",
            "穿仓金额:wearingAmount",
            "买入金额:buyAmount",
            "卖出金额:soldAmount",
            "平仓原因:soldTypeLabel",
            "盈亏比(%):percent",
            "穿仓处理状态:wearnHandleType",
            "持仓类型:holdTypeLabel"
    } ;

    public static  String[] STRATEGY_LIST_EXCEL_HEAD = {"客服经理:saleName",
            "策略单号:strategyId",
            "策略发起时间:createTime",
            "投顾姓名:dmName",
            "投顾手机号:phone",
            "投顾性别:gender",
            "投顾年龄:age",
            "投顾地址:address",
            "投顾存活周期:lifeCycle",
            "是否补保:isDepositAppendedLabel",
            "总共缴纳保证金:totalDeposit",
            "初始保证金:deposit",
            "追加保证金:appendDeposit",
            "抵用金:deduction",
            "服务费:dmFee",
            "投资人姓名:invName",
            "投资人id:invUid",
            "券商:company",
            "证券账户ID:accountId",
            "股票名称:stockName",
            "股票代码:stockFullCode",
            "本金:dmCapital",
            "买入金额:buyAmount",
            "到达平仓日期:arriveDate",
            "策略结算时间:settleTime",
            "点卖金额:soldAmount",
            "盈利(无关税费):profit",
            "买入税费:dmTradeFee",
            "卖出税费:dmaiTradeFee",
            "平台分成:investorProfitAmount",
            "投资人分成:investorProfitAmount",
            "投顾分成:profitAmount",
            "当前投顾分成比例:profitRatio",
            "亏损(无关税费):loss",
            "投顾亏损扣减:lossAmount",
            "退还金额:rebackAmount",
            "递延费:deferFee",
            "策略纯盈亏:pureProfitLoss",
            "盈亏比(%):percent",
            "持仓天数:holdDays",
            "持仓类型:holdTypeLabel",
            "卖出类型:soldTypeLabel",
            "今日递延费:dailyDeferFee",
            "递延费说明:reasonforDeferFee",
            "买入单价:buyPrice",
            "卖出单价:soldPrice",
            "买入委托价:buyHangPrice",
            "卖出委托价:soldHangPrice",
            "股数:dealQuantity",
            "止盈价:stopProfit",
            "止损价:stopLoss",
            "推广渠道:channelName",
            "推荐人号码:inviterPhone",
            "推荐人姓名:inviterName",
            "涨停卡:roseLabel",
            "翻倍卡:multipleValue",
            "红包:bonus",
            "红包来源:sourceTypeLabel",
            "第一笔策略时间:firstBuyTime"
    };


    public static  String[] RISK_LIST_EXCEL_HEAD = {
            "股票代码:stockCode",
            "股票名称:stockName",
            "股票杠杆:lever",
            "状态:allow",
            "风控警示语:warning"
    };

    /**
     * Description: 转换Excel表头<br>
     *
     * @param heads
     * @return List<Map<String,String>>
     */
    public static List<Map<String, String>> excelHeader(String[] heads) {
        // 表头集合
        List<Map<String, String>> headLst = new ArrayList<Map<String, String>>();
        // 表头个数
        int len = heads.length;
        // 单个表头Map
        Map<String, String> map;
        // 循环处理
        for (int i = 0; i < len; i++) {
            // 当前表头
            String head = heads[i];
            // 根据冒号分隔
            String[] headVector = head.split(CommonConstants.STR_COMMA);
            // 如果格式不正确则不认为是表头
            if (headVector.length == CommonConstants.NUM_TWO) {
                // 实例化表头Map
                map = new HashMap<>();
                // 设定Field
                map.put(CommonConstants.EXCEL_FIELD, headVector[CommonConstants.NUM_ONE]);
                // 设定Field名称
                map.put(CommonConstants.EXCEL_FIELD_NAME,
                        headVector[CommonConstants.NUM_ZERO]);
                // 写入头部集合
                headLst.add(map);
            }
        }
        return headLst;
    }
}
