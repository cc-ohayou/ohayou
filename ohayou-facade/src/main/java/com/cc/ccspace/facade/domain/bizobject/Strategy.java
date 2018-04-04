package com.cc.ccspace.facade.domain.bizobject;

import java.math.BigDecimal;

/**
 * @AUTHOR FangChunjie
 * @DATE Created on 2017/10/24 15:48.
 */
public class Strategy {
    private String strategyId;
    private String userName;//投顾账户
    private String accountName;//资方账户
    private String stockName;//股票名称
    private String createTime;//发布时间
    private String status;//状态
    private String tradeType;//操作类型
    private String settleTime;//成交时间
    private String hangQuantity;//委托数量
    private BigDecimal hangPrice;//委托价格
    private String dealQuantity;//成交数量
    private BigDecimal dealPrice;//成交价格
    private BigDecimal dealAmount;//成交总量
    private BigDecimal profit;//交易盈亏
    //资方分成 盈利为数据库查询出的 亏损为0
    private BigDecimal investProfitLoss;
    //投顾分成 盈利为对应数据库字段值 亏损为0
    private BigDecimal strategyProfitLoss;
    private String holdDays;//持仓天数
    private String holdType;//T+D
    private String channel;
    private String stockCode;
    //穿仓标志 数据库 wearn_sign=-1  有枚举 WearnType
    // 用户亏损
    // 非穿仓情况=strategyProfitLoss
    // 穿仓=strategyProfitLoss绝对值-服务费+（profit绝对值-（strategyProfitLoss绝对值-服务费））*BigDecimal.valueOf(0.8)
    private BigDecimal userLoss;//金顾亏损
    //穿仓追缴   =（profit绝对值-（strategyProfitLoss绝对值-服务费））*BigDecimal.valueOf(0.8)
    private BigDecimal needAppendOfWearn;
    //服务费
    private BigDecimal servFee;
    //平台亏损=（profit绝对值-（strategyProfitLoss绝对值-服务费））*BigDecimal.valueOf(0.2)
    private BigDecimal platLoss;
    //信用金
    private BigDecimal deposit;
    //初始信用金
    private BigDecimal originDeposit;
    //追加信用金
    private BigDecimal appendDeposit;
    //买入总额
    private BigDecimal buyAmount;
    //卖出总额
    private BigDecimal soldAmount;
    //买入手续费
    private BigDecimal buyCommission;
    //卖出手续费
    private BigDecimal soldCommission;
    //盈亏率
    private BigDecimal profitRate;
    //递延费
    private BigDecimal deferFee;
    //平仓类型
    private String soldType;
    //平台服务费
    private BigDecimal platformServerFee;
    //信用金返还
    private BigDecimal depositReturn;
    //策略结算返还
    private BigDecimal strategyReturn;
    //买入过户费
    private BigDecimal buyTransferFee;
    //卖出过户费
    private BigDecimal soldTransferFee;
    //印花税
    private BigDecimal stampDuty;
    //渠道名称
    private String spreadName;
    //合伙人
    private String partner;
    //团队
    private String team;
    //真实姓名
    private String realName;
    private String manager;
    //递延到期日
    private String arriveDate;
    //已缴递延费
    private BigDecimal paidDeferFee;
    //未缴递延费
    private BigDecimal unpaidDeferFee;
    //未递延天数
    private String undeferDays;
    private BigDecimal buyPrice;
    private BigDecimal soldPrice;
    //递延标识
    private int deferSign;
    //可用余额
    private BigDecimal balance;
    //穿仓处理状态
    private String wearnHandleStatus;
    //应扣穿仓金额
    private BigDecimal subWearnFee;

    //资方应承担金额
    private BigDecimal investBear;
    //账户总余额
    private BigDecimal totalBalance;

    private String isDefer;

    private  Integer autoDefer;

    private  String isAutoDefer;

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getInvestBear() {
        return investBear;
    }

    public void setInvestBear(BigDecimal investBear) {
        this.investBear = investBear;
    }

    public BigDecimal getSubWearnFee() {
        return subWearnFee;
    }

    public void setSubWearnFee(BigDecimal subWearnFee) {
        this.subWearnFee = subWearnFee;
    }

    public String getWearnHandleStatus() {
        return wearnHandleStatus;
    }

    public void setWearnHandleStatus(String wearnHandleStatus) {
        this.wearnHandleStatus = wearnHandleStatus;
    }

    public BigDecimal getUserLoss() {
        return userLoss;
    }

    public void setUserLoss(BigDecimal userLoss) {
        this.userLoss = userLoss;
    }

    public BigDecimal getNeedAppendOfWearn() {
        return needAppendOfWearn;
    }

    public void setNeedAppendOfWearn(BigDecimal needAppendOfWearn) {
        this.needAppendOfWearn = needAppendOfWearn;
    }

    public BigDecimal getServFee() {
        return servFee;
    }

    public void setServFee(BigDecimal servFee) {
        this.servFee = servFee;
    }

    public BigDecimal getPlatLoss() {
        return platLoss;
    }

    public void setPlatLoss(BigDecimal platLoss) {
        this.platLoss = platLoss;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public String getHangQuantity() {
        return hangQuantity;
    }

    public void setHangQuantity(String hangQuantity) {
        this.hangQuantity = hangQuantity;
    }

    public BigDecimal getHangPrice() {
        return hangPrice;
    }

    public void setHangPrice(BigDecimal hangPrice) {
        this.hangPrice = hangPrice;
    }

    public String getDealQuantity() {
        return dealQuantity;
    }

    public void setDealQuantity(String dealQuantity) {
        this.dealQuantity = dealQuantity;
    }

    public BigDecimal getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(BigDecimal dealPrice) {
        this.dealPrice = dealPrice;
    }

    public BigDecimal getDealAmount() {
        return dealAmount;
    }

    public void setDealAmount(BigDecimal dealAmount) {
        this.dealAmount = dealAmount;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public BigDecimal getInvestProfitLoss() {
        return investProfitLoss;
    }

    public void setInvestProfitLoss(BigDecimal investProfitLoss) {
        this.investProfitLoss = investProfitLoss;
    }

    public BigDecimal getStrategyProfitLoss() {
        return strategyProfitLoss;
    }

    public void setStrategyProfitLoss(BigDecimal strategyProfitLoss) {
        this.strategyProfitLoss = strategyProfitLoss;
    }

    public String getHoldDays() {
        return holdDays;
    }

    public void setHoldDays(String holdDays) {
        this.holdDays = holdDays;
    }

    public String getHoldType() {
        return holdType;
    }

    public void setHoldType(String holdType) {
        this.holdType = holdType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public BigDecimal getOriginDeposit() {
        return originDeposit;
    }

    public void setOriginDeposit(BigDecimal originDeposit) {
        this.originDeposit = originDeposit;
    }

    public BigDecimal getAppendDeposit() {
        return appendDeposit;
    }

    public void setAppendDeposit(BigDecimal appendDeposit) {
        this.appendDeposit = appendDeposit;
    }

    public BigDecimal getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(BigDecimal buyAmount) {
        this.buyAmount = buyAmount;
    }

    public BigDecimal getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(BigDecimal soldAmount) {
        this.soldAmount = soldAmount;
    }

    public BigDecimal getBuyCommission() {
        return buyCommission;
    }

    public void setBuyCommission(BigDecimal buyCommission) {
        this.buyCommission = buyCommission;
    }

    public BigDecimal getSoldCommission() {
        return soldCommission;
    }

    public void setSoldCommission(BigDecimal soldCommission) {
        this.soldCommission = soldCommission;
    }

    public BigDecimal getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    public BigDecimal getDeferFee() {
        return deferFee;
    }

    public void setDeferFee(BigDecimal deferFee) {
        this.deferFee = deferFee;
    }

    public String getSoldType() {
        return soldType;
    }

    public void setSoldType(String soldType) {
        this.soldType = soldType;
    }

    public BigDecimal getPlatformServerFee() {
        return platformServerFee;
    }

    public void setPlatformServerFee(BigDecimal platformServerFee) {
        this.platformServerFee = platformServerFee;
    }

    public BigDecimal getDepositReturn() {
        return depositReturn;
    }

    public void setDepositReturn(BigDecimal depositReturn) {
        this.depositReturn = depositReturn;
    }

    public BigDecimal getStrategyReturn() {
        return strategyReturn;
    }

    public void setStrategyReturn(BigDecimal strategyReturn) {
        this.strategyReturn = strategyReturn;
    }

    public BigDecimal getBuyTransferFee() {
        return buyTransferFee;
    }

    public void setBuyTransferFee(BigDecimal buyTransferFee) {
        this.buyTransferFee = buyTransferFee;
    }

    public BigDecimal getSoldTransferFee() {
        return soldTransferFee;
    }

    public void setSoldTransferFee(BigDecimal soldTransferFee) {
        this.soldTransferFee = soldTransferFee;
    }

    public BigDecimal getStampDuty() {
        return stampDuty;
    }

    public void setStampDuty(BigDecimal stampDuty) {
        this.stampDuty = stampDuty;
    }

    public String getSpreadName() {
        return spreadName;
    }

    public void setSpreadName(String spreadName) {
        this.spreadName = spreadName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
    }

    public BigDecimal getPaidDeferFee() {
        return paidDeferFee;
    }

    public void setPaidDeferFee(BigDecimal paidDeferFee) {
        this.paidDeferFee = paidDeferFee;
    }

    public BigDecimal getUnpaidDeferFee() {
        return unpaidDeferFee;
    }

    public void setUnpaidDeferFee(BigDecimal unpaidDeferFee) {
        this.unpaidDeferFee = unpaidDeferFee;
    }

    public String getUndeferDays() {
        return undeferDays;
    }

    public void setUndeferDays(String undeferDays) {
        this.undeferDays = undeferDays;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(BigDecimal soldPrice) {
        this.soldPrice = soldPrice;
    }

    public int getDeferSign() {
        return deferSign;
    }

    public void setDeferSign(int deferSign) {
        this.deferSign = deferSign;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getIsDefer() {
        return isDefer;
    }

    public void setIsDefer(String isDefer) {
        this.isDefer = isDefer;
    }

    public Integer getAutoDefer() {
        return autoDefer;
    }

    public void setAutoDefer(Integer autoDefer) {
        this.autoDefer = autoDefer;
    }

    public String getIsAutoDefer() {
        return isAutoDefer;
    }

    public void setIsAutoDefer(String isAutoDefer) {
        this.isAutoDefer = isAutoDefer;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
