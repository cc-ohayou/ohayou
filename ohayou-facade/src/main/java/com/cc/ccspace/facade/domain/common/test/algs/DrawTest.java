package com.cc.ccspace.facade.domain.common.test.algs;

import com.alibaba.fastjson.JSONObject;
import com.cc.ccspace.facade.domain.bizobject.Strategy;
import com.cc.ccspace.facade.domain.common.test.stdlib.StdDraw;
import com.cc.ccspace.facade.domain.common.test.stdlib.StdRandom;
import com.cc.ccspace.facade.domain.common.util.excel.MapUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/1/23 12:44.
 */
public class DrawTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        new DrawTest().exportTest("");
//        randomArray();
/*
        String ss = "{\"code\":0,\"data\":[\"600658\",\"601128\",\"000961\",\"603698\",\"002656\",\"002768\",\"600288\",\"601137\",\"002658\"],\"msg\":\"请求成功\"}";
        String ssr = "\\[.*?\\]";
        Matcher match = Pattern.compile(ssr).matcher(ss);
        while (match.find()) {
            System.out.println(match.group(0));
        }
        String str = "Hello,World! in Java.";
        Pattern pattern = Pattern.compile("W(or)(ld!)");
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()) {
            System.out.println(matcher.group(0));
        }*/
//        System.out.println(match.group(0));

    }

    private static void randomArray() {
        int n = 50;
        double[] a = new double[n];
        for (int i = 0; i < n; i++) {
            a[i] = StdRandom.random();

        }

        for (int j = 0; j < 2; j++) {
            System.out.println(a[j]);
            double x = 1.0 * j / n;
            double y = a[j] / 2.0;
            double rw = 0.5 / n;
            double rh = a[j] / 2.0;
            StdDraw.filledRectangle(x, y, rw, rh);
        }
    }


    public void exportTest(String str0) throws UnsupportedEncodingException {

        Map<String, String> headers = new LinkedHashMap<>();
        headers.put("strategyId", "委托单编号");
        headers.put("accountName", "资金账号");
        headers.put("userName", "金顾账户");
        headers.put("realName", "真实姓名");
        headers.put("originDeposit", "初始信用金");
        headers.put("appendDeposit", "追加信用金");
        headers.put("balance", "可用余额");
        headers.put("settleTime", "结算时间");
        headers.put("stockName", "股票名称");
        headers.put("stockCode", "股票代码");
        headers.put("buyPrice", "买入价格");
        headers.put("deferFee", "递延费");
        headers.put("partner", "合伙人");
        headers.put("spreadName", "渠道");
        headers.put("team", "团队");
        headers.put("manager", "客户经理");
        headers.put("channel", "渠道码");

        headers.put("createTime", "发起时间");
        headers.put("status", "状态");
        headers.put("dealQuantity", "股数（股）");
        headers.put("hangPrice", "委托价格");
        headers.put("holdDays", "持仓天数");
        headers.put("holdType", "持仓类型");


        List items = JSONObject.parseObject(str0, List.class);

        System.out.println(items.size());
       /* List<Map<String, Object>> list = listToMap();
        res.setContentType("application/x-msdownload");
        res.setHeader("Content-Disposition", "attachment; filename="
                + URLEncoder.encode("策略查询列表.xls", "UTF-8"));
        ExcelUtil.exportExcel(headers, list, res.getOutputStream(), true);
        res.getOutputStream().close();*/


    }

    public List<Map<String, Object>> listToMap(List<Strategy> list) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Strategy strategy : list) {
            String soldType = "";
            switch (strategy.getSoldType()) {
                case "stp_pro":
                    soldType = "止盈平仓";
                    break;
                case "stp_loss":
                    soldType = "止损平仓";
                    break;
                case "manual":
                    soldType = "手动平仓";
                    break;
                case "def_arrive":
                    soldType = "递延日期到达";
                    break;
                case "unenough":
                    soldType = "余额不足";
                    break;
                case "dec_trig":
                    soldType = "触发跌幅线卖出";
                    break;
            }
            strategy.setSoldType(soldType);

            Map<String, Object> map = MapUtils.java2Map(strategy);
            result.add(map);
        }
        return result;
    }
}