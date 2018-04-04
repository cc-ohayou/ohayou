package com.cc.ccspace.facade.domain.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cc.ccspace.facade.domain.common.util.http.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HSUtil {
    private static String BASIC = "Basic ";
    public static final String LAST_PRICE = "last_price";// 现价
    public static final String PRECLOSE_PX = "preclose_px";// 昨日收盘价
    public static final String OPEN_PX = "open_px";// 开盘价
    public static final String HSET_STOCK_CACHE = "HSET_STOCK_CACHE";

    public static final String REAL_FIELD = "trade_status,preclose_px,last_px";// 实时行情需要的字段
    // 行情字段索引，0:时间戳，1:最新交易单元。请注意，上面如果增加或者调整字段，下面的索引值必须做相应调整
    public static final int REAL_FIELD_TRADE_STATUS = 2;// 交易状态
    public static final int REAL_FIELD_PRECLOSE_PX = 3;// 昨日收盘价
    public static final int REAL_FIELD_LAST_PX = 4;// 最新价格

    private static Logger log = LoggerFactory.getLogger(HSUtil.class);

    /**
     * 获取恒生签名信息
     *
     * @return
     */
    public static String getAuth(String tokenType) {
        String clientId;
        String clientSecret;
        String env = tokenType;//System.getenv("DEV_ENV");
        if (env.equals("com")) {
            clientId = "75375d0f-637d-4a0c-a08f-5f8997ca1e9d";
            clientSecret = "c65f5a51-d4ec-4b96-a8be-861b219f8118";
        } else if (env.equals("me")) {
            clientId = "c1f0134d-a374-4b96-8917-3efc1fccb9f7";
            clientSecret = "95201db4-3e25-4c64-8db0-e23621b101f2";
        } else {
            clientId = "4798fe0d-9338-4927-be3a-9b983fe51de4";
            clientSecret = "f074d594-8f21-409d-8fc7-1f7f5be06cca";
        }
        log.info("env:" + env + " clientId:" + clientId);
        String auth = null;
        try {
            auth = HttpClientUtil.Base64(clientId, clientSecret, BASIC);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return auth;
    }

    /**
     * 获取恒生token
     *
     * @return
     */
   /* public static String getAccessToken(String tokenType) {
        // 客户端凭证模式 获取令牌
        Map<String, String> token_map = new HashMap<>();
        token_map.put("grant_type", "client_credentials");// 客户端凭证模式时，必须为“client_credentials”；
        String tokenResult = HttpClientUtil.sendPost(HttpClientUtil.OPENURL + "/oauth2/oauth2/token", token_map,
                HttpClientUtil.CHARSET, HttpClientUtil.CHARSET, null, HSUtil.getAuth(tokenType), "获取公共令牌");

        log.info("getAccessToken result:" + tokenResult);
        if (StringUtils.isEmpty(tokenResult)) {
            log.error("getAccessToken result is null!");
            return null;
        }
        // 首先检查错误状态
        TokenError error = JSON.parseObject(tokenResult, TokenError.class);
        String token = null;
        if (error == null || StringUtils.isEmpty(error.getError())) {
            // 解析返回数据json
            AccessToken accesstoken = JSON.parseObject(tokenResult, AccessToken.class);
            if (accesstoken == null) {
                log.error("getAccessToken json2Object is null!");
                return null;
            }
            token = accesstoken.getAccess_token();
        }
        return token;
    }*/


    /**
     * 检查接口请求及返回是否正确
     *
     * @param result http请求结果字符串
     * @return true：成功， false：错误
     */
  /*  public static boolean isResultOK(String result) {
        if (result == null) {
            log.error("result is null:");
            return false;
        }
        // 不含错误代码
        String temp = result.toLowerCase();
        if (!temp.contains("error")) {
            return true;
        }


        TokenError error = JSON.parseObject(result, TokenError.class);
        if (error != null) {
            log.error("TokenError:" + error.getError_description());
            return false;
        }
        BusError buserror = JSON.parseObject(result, BusError.class);
        if (buserror != null) {
            log.error("getOneHundred buserror:" + buserror);
            return false;
        }
        return true;
    }*/

    /**
     * 解析市场详细信息 （market/detail），取出里面A股股票代码列表
     *
     * @param result
     * @return A股股票代码列表  stockcode,stockname的hash
     */
    public static Map<String, String> parseMarketResult(String result) {

        Map<String, String> stockInfo = new HashMap<String, String>();
        try {
            // 获取交易市场所有品种
            JSONArray stockArray = JSONObject.parseObject(result).getJSONObject("data")
                    .getJSONArray("market_detail_prod_grp");
            // 依次解析
            Iterator<Object> it = stockArray.iterator();
            while (it.hasNext()) {
                JSONObject ob = (JSONObject) it.next();
                // {"hq_type_code":"ESA.M","prod_code":"000001","prod_name":"平安银行"}
                /*
                 * 获取类型代码，详情查看恒生接口文档 ES Shares 普通股票 ESA 普通 A 股 ESB 普通 B 股 ESH 普通
				 * H 股
				 */
                String hq_type_code = ob.getString("hq_type_code");
                if (hq_type_code.startsWith("ESA")) {// 仅A股股市
                    stockInfo.put(ob.getString("prod_code"), ob.getString("prod_name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return stockInfo;
    }





}
