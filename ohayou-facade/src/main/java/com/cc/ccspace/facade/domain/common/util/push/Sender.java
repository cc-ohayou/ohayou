package com.cc.ccspace.facade.domain.common.util.push;


import com.cc.ccspace.facade.domain.common.util.http.HttpClientUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class Sender {
    // API_HTTP_SCHEMA
    public static String API_HTTP_SCHEMA = "https";
    // API_SERVER_HOST
    public static String API_SERVER_HOST = PushConfig.getMap().get("API_SERVER_HOST");
    // APPKEY
    public static String APPKEY = PushConfig.getMap().get("APPKEY");
    // APP_CLIENT_ID
    public static String APP_CLIENT_ID = PushConfig.getMap().get("APP_CLIENT_ID");
    // APP_CLIENT_SECRET
    public static String APP_CLIENT_SECRET = PushConfig.getMap().get("APP_CLIENT_SECRET");
    // DEFAULT_PASSWORD
    public static String DEFAULT_PASSWORD = "123456";

    public static final String targetType = "users";

    /**
     * 个推推送相关配置
     */
    public static final String GETUI_host = "http://sdk.open.api.igexin.com/apiex.htm";
    private static Logger logger = LoggerFactory.getLogger(Sender.class);

    private static Credential credential = new ClientSecretCredential(APP_CLIENT_ID, APP_CLIENT_SECRET, Roles.USER_ROLE_APPADMIN);

    //推送消息
    public static ObjectNode sendMessages(String targetType, ArrayNode target, ObjectNode msg, String from, ObjectNode ext, JsonNodeFactory factory) {
        ObjectNode objectNode = factory.objectNode();
        ObjectNode dataNode = factory.objectNode();

        // check appKey format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.error("Bad format of Appkey: " + APPKEY);
            objectNode.put("message", "Bad format of Appkey");
            return objectNode;
        }

        // check properties that must be provided
        if (!("users".equals(targetType) || "chatgroups".equals(targetType))) {
            logger.error("TargetType must be users or chatgroups .");
            objectNode.put("message", "TargetType must be users or chatgroups .");
            return objectNode;
        }

        try {
            // 构造消息体
            dataNode.put("target_type", targetType);
            dataNode.set("target", target);
            dataNode.set("msg", msg);
            dataNode.put("from", from);
            dataNode.set("ext", ext);

            objectNode = HttpClientUtil.sendHTTPSRequest(EndPoints.MESSAGES_URL, credential, dataNode, HttpMethod.METHOD_POST);

            objectNode = (ObjectNode) objectNode.get("data");
            for (int i = 0; i < target.size(); i++) {
                String resultStr = objectNode.path(target.path(i).asText()).asText();
                if ("success".equals(resultStr)) {
                    logger.info(String.format("Message has been send to user[%s] successfully .", target.path(i).asText()));
                } else if (!"success".equals(resultStr)) {
                    logger.error(String.format("Message has been send to user[%s] failed .", target.path(i).asText()));
                }
            }

        } catch (Exception e) {
            logger.error("!!####sendMessages error", e);
        }
        return objectNode;
    }

    /**
     * @description 个推推送
     * @author Eric create on 2017/12/14 13:25
     */
    public static ObjectNode geTuiSendMessage(JsonNodeFactory factory) {
        ObjectNode objectNode = factory.objectNode();
        ObjectNode dataNode = factory.objectNode();

        return objectNode;
    }

    //判断是否在线
    public static ObjectNode getUserStatus(String username, JsonNodeFactory factory) {
        ObjectNode objectNode = factory.objectNode();

        // check appKey format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.error("Bad format of Appkey: " + APPKEY);
            objectNode.put("message", "Bad format of Appkey");
            return objectNode;
        }

        // check properties that must be provided
        if (StringUtils.isEmpty(username)) {
            logger.error("You must provided a targetUserName .");
            objectNode.put("message", "You must provided a targetUserName .");
            return objectNode;
        }

        try {
            URL userStatusUrl = HttpClientUtils.getURL(APPKEY.replace("#", "/") + "/users/" + username + "/status");
            objectNode = HttpClientUtils.sendHTTPRequest(userStatusUrl, credential, null, HttpMethod.METHOD_GET);
            String userStatus = objectNode.get("data").path(username).asText();
            if ("online".equals(userStatus)) {
                logger.error(String.format("The status of user[%s] is : [%s] .", username, userStatus));
            } else if ("offline".equals(userStatus)) {
                logger.error(String.format("The status of user[%s] is : [%s] .", username, userStatus));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }


    //注册IM用户[单个]
    static void all(JsonNodeFactory factory) {
        ObjectNode datanode = JsonNodeFactory.instance.objectNode();
        datanode.put("username", "kenshinnuser100");
        datanode.put("password", DEFAULT_PASSWORD);
        ObjectNode createNewIMUserSingleNode = createNewIMUserSingle(datanode, factory);
        if (null != createNewIMUserSingleNode) {
            logger.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
        }

        //IM用户登录
        ObjectNode imUserLoginNode = imUserLogin(datanode.get("username").asText(), datanode.get("password").asText(), factory);
        if (null != imUserLoginNode) {
            logger.info("IM用户登录: " + imUserLoginNode.toString());
        }

        //注册IM用户[批量生成用户然后注册]
        String usernamePrefix = "kenshinnuser";
        Long perNumber = 10l;
        Long totalNumber = 100l;
        ObjectNode createNewIMUserBatchGenNode = createNewIMUserBatchGen(usernamePrefix, perNumber, totalNumber, factory);
        if (null != createNewIMUserBatchGenNode) {
            logger.info("注册IM用户[批量]: " + createNewIMUserBatchGenNode.toString());
        }

        //获取IM用户[主键查询]
        String userName = "kenshinnuser100";
        ObjectNode getIMUsersByUserNameNode = getIMUsersByUserName(userName, factory);
        if (null != getIMUsersByUserNameNode) {
            logger.info("获取IM用户[主键查询]: " + getIMUsersByUserNameNode.toString());
        }

        //重置IM用户密码 提供管理员token
        String username = "kenshinnuser100";
        ObjectNode json2 = JsonNodeFactory.instance.objectNode();
        json2.put("newpassword", DEFAULT_PASSWORD);
        ObjectNode modifyIMUserPasswordWithAdminTokenNode = modifyIMUserPasswordWithAdminToken(username, json2, factory);
        if (null != modifyIMUserPasswordWithAdminTokenNode) {
            logger.info("重置IM用户密码 提供管理员token: " + modifyIMUserPasswordWithAdminTokenNode.toString());
        }
        ObjectNode imUserLoginNode2 = imUserLogin(username, json2.get("newpassword").asText(), factory);
        if (null != imUserLoginNode2) {
            logger.info("重置IM用户密码后,IM用户登录: " + imUserLoginNode2.toString());
        }


        //添加好友[单个]
        String ownerUserName = username;
        String friendUserName = "kenshinnuser099";
        ObjectNode addFriendSingleNode = addFriendSingle(ownerUserName, friendUserName, factory);
        if (null != addFriendSingleNode) {
            logger.info("添加好友[单个]: " + addFriendSingleNode.toString());
        }

        //查看好友
        ObjectNode getFriendsNode = getFriends(ownerUserName, factory);
        if (null != getFriendsNode) {
            logger.info("查看好友: " + getFriendsNode.toString());
        }

        // 解除好友关系
        ObjectNode deleteFriendSingleNode = deleteFriendSingle(ownerUserName, friendUserName, factory);
        if (null != deleteFriendSingleNode) {
            logger.info("解除好友关系: " + deleteFriendSingleNode.toString());
        }

        //删除IM用户[单个]
        ObjectNode deleteIMUserByuserNameNode = deleteIMUserByuserName(userName, factory);
        if (null != deleteIMUserByuserNameNode) {
            logger.info("删除IM用户[单个]: " + deleteIMUserByuserNameNode.toString());
        }

        //删除IM用户[批量]
        Long limit = 2l;
        ObjectNode deleteIMUserByUsernameBatchNode = deleteIMUserByUsernameBatch(limit, factory);
        if (null != deleteIMUserByUsernameBatchNode) {
            logger.info("删除IM用户[批量]: " + deleteIMUserByUsernameBatchNode.toString());
        }
    }

    //注册IM用户[单个]
    public static ObjectNode createNewIMUserSingle(ObjectNode dataNode, JsonNodeFactory factory) {

        ObjectNode objectNode = factory.objectNode();

        // check APPKEY format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.warn("Bad format of APPKEY: " + APPKEY);
            objectNode.put("message", "Bad format of APPKEY");
            return objectNode;
        }

        objectNode.removeAll();
        // check properties that must be provided
        if (null != dataNode && !dataNode.has("username")) {
            logger.warn("Property that named username must be provided .");

            objectNode.put("message", "Property that named username must be provided .");

            return objectNode;
        }
        if (null != dataNode && !dataNode.has("password")) {
            logger.warn("Property that named password must be provided .");
            objectNode.put("message", "Property that named password must be provided .");
            return objectNode;
        }

        try {
            objectNode = HttpClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, dataNode, HttpMethod.METHOD_POST);
        } catch (Exception e) {
            logger.error("环信注册用户失败！dataNode=" + dataNode.toString(), e);
        }

        return objectNode;
    }

    //注册IM用户[批量]
    private static ObjectNode createNewIMUserBatch(ArrayNode dataArrayNode, JsonNodeFactory factory) {

        ObjectNode objectNode = factory.objectNode();

        // check APPKEY format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.error("Bad format of APPKEY: " + APPKEY);

            objectNode.put("message", "Bad format of APPKEY");

            return objectNode;
        }

        // check properties that must be provided
        if (dataArrayNode.isArray()) {
            for (JsonNode jsonNode : dataArrayNode) {
                if (null != jsonNode && !jsonNode.has("username")) {
                    logger.error("Property that named username must be provided .");

                    objectNode.put("message", "Property that named username must be provided .");

                    return objectNode;
                }
                if (null != jsonNode && !jsonNode.has("password")) {
                    logger.error("Property that named password must be provided .");

                    objectNode.put("message", "Property that named password must be provided .");

                    return objectNode;
                }
            }
        }

        try {

            objectNode = HttpClientUtils.sendHTTPRequest(EndPoints.USERS_URL, credential, dataArrayNode, HttpMethod.METHOD_POST);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }

    /**
     * 注册IM用户[批量生成用户然后注册]  给指定APPKEY创建一批用户
     *
     * @param usernamePrefix 生成用户名的前缀
     * @param perNumber      批量注册时一次注册的数量
     * @param totalNumber    生成用户注册的用户总数
     */
    private static ObjectNode createNewIMUserBatchGen(String usernamePrefix, Long perNumber, Long totalNumber, JsonNodeFactory factory) {
        ObjectNode objectNode = factory.objectNode();

        if (totalNumber == 0 || perNumber == 0) {
            return objectNode;
        }
        System.out.println("你即将注册" + totalNumber + "个用户，如果大于" + perNumber + "了,会分批注册,每次注册" + perNumber + "个");
        ArrayNode genericArrayNode = genericArrayNode(usernamePrefix, totalNumber, factory);
        if (totalNumber <= perNumber) {
            objectNode = createNewIMUserBatch(genericArrayNode, factory);
        } else {

            ArrayNode tmpArrayNode = factory.arrayNode();

            for (int i = 0; i < genericArrayNode.size(); i++) {
                tmpArrayNode.add(genericArrayNode.get(i));
                // 300 records on one migration
                if ((i + 1) % perNumber == 0) {
                    objectNode = createNewIMUserBatch(tmpArrayNode, factory);
                    if (objectNode != null) {
                        logger.info("注册IM用户[批量]: " + objectNode.toString());
                    }
                    tmpArrayNode.removeAll();
                    continue;
                }

                // the rest records that less than the times of 300
                if (i > (genericArrayNode.size() / perNumber * perNumber - 1)) {
                    objectNode = createNewIMUserBatch(tmpArrayNode, factory);
                    if (objectNode != null) {
                        logger.info("注册IM用户[批量]: " + objectNode.toString());
                    }
                    tmpArrayNode.removeAll();
                }
            }
        }

        return objectNode;
    }

    //获取IM用户
    //  用户主键：username或者uuid
    private static ObjectNode getIMUsersByUserName(String userName, JsonNodeFactory factory) {
        ObjectNode objectNode = factory.objectNode();

        // check APPKEY format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.error("Bad format of APPKEY: " + APPKEY);

            objectNode.put("message", "Bad format of APPKEY");

            return objectNode;
        }

        // check properties that must be provided
        if (StringUtils.isEmpty(userName)) {
            logger.error("The userName that will be used to query must be provided .");

            objectNode.put("message", "The userName that will be used to query must be provided .");

            return objectNode;
        }

        try {

            URL userPrimaryUrl = HttpClientUtils.getURL(APPKEY.replace("#", "/") + "/users/" + userName);
            objectNode = HttpClientUtils.sendHTTPRequest(userPrimaryUrl, credential, null, HttpMethod.METHOD_GET);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }

    //删除IM用户[单个]
    //删除指定APPKEY下IM单个用户
    private static ObjectNode deleteIMUserByuserName(String userName, JsonNodeFactory factory) {
        ObjectNode objectNode = factory.objectNode();

        // check APPKEY format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.error("Bad format of APPKEY: " + APPKEY);

            objectNode.put("message", "Bad format of APPKEY");

            return objectNode;
        }

        try {

            URL deleteUserPrimaryUrl = HttpClientUtils.getURL(APPKEY.replace("#", "/") + "/users/" + userName);
            objectNode = HttpClientUtils.sendHTTPRequest(deleteUserPrimaryUrl, credential, null,
                    HttpMethod.METHOD_DELETE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }

    //删除IM用户[批量]
    private static ObjectNode deleteIMUserByUsernameBatch(Long limit, JsonNodeFactory factory) {

        ObjectNode objectNode = factory.objectNode();

        // check APPKEY format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.error("Bad format of APPKEY: " + APPKEY);

            objectNode.put("message", "Bad format of APPKEY");

            return objectNode;
        }

        try {

            URL deleteIMUserByUsernameBatchUrl = HttpClientUtils.getURL(APPKEY.replace("#", "/") + "/users"
                    + "?limit=" + limit);
            objectNode = HttpClientUtils.sendHTTPRequest(deleteIMUserByUsernameBatchUrl, credential, null,
                    HttpMethod.METHOD_DELETE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }

    //重置IM用户密码 提供管理员token
    private static ObjectNode modifyIMUserPasswordWithAdminToken(String userName, ObjectNode dataObjectNode, JsonNodeFactory factory) {
        ObjectNode objectNode = factory.objectNode();

        // check APPKEY format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.error("Bad format of APPKEY: " + APPKEY);

            objectNode.put("message", "Bad format of APPKEY");

            return objectNode;
        }

        if (StringUtils.isEmpty(userName)) {
            logger.error("Property that named userName must be provided，the value is username of imuser.");

            objectNode.put("message",
                    "Property that named userName must be provided，the value is username or imuser.");

            return objectNode;
        }

        if (null != dataObjectNode && !dataObjectNode.has("newpassword")) {
            logger.error("Property that named newpassword must be provided .");

            objectNode.put("message", "Property that named newpassword must be provided .");

            return objectNode;
        }

        try {
            URL modifyIMUserPasswordWithAdminTokenUrl = HttpClientUtils.getURL(APPKEY.replace("#", "/")
                    + "/users/" + userName + "/password");
            objectNode = HttpClientUtils.sendHTTPRequest(modifyIMUserPasswordWithAdminTokenUrl, credential,
                    dataObjectNode, HttpMethod.METHOD_PUT);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }

    //添加好友[单个]
    private static ObjectNode addFriendSingle(String ownerUserName, String friendUserName, JsonNodeFactory factory) {
        ObjectNode objectNode = factory.objectNode();

        // check APPKEY format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.error("Bad format of APPKEY: " + APPKEY);

            objectNode.put("message", "Bad format of APPKEY");

            return objectNode;
        }

        if (StringUtils.isEmpty(ownerUserName)) {
            logger.error("Your userName must be provided，the value is username of imuser.");

            objectNode.put("message", "Your userName must be provided，the value is username of imuser.");

            return objectNode;
        }

        if (StringUtils.isEmpty(friendUserName)) {
            logger.error("The userName of friend must be provided，the value is username of imuser.");

            objectNode.put("message",
                    "The userName of friend must be provided，the value is username of imuser.");

            return objectNode;
        }

        try {

            URL addFriendSingleUrl = HttpClientUtils.getURL(APPKEY.replace("#", "/") + "/users/"
                    + ownerUserName + "/contacts/users/" + friendUserName);

            ObjectNode body = factory.objectNode();
            objectNode = HttpClientUtils.sendHTTPRequest(addFriendSingleUrl, credential, body, HttpMethod.METHOD_POST);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }

    //删除好友
    private static ObjectNode deleteFriendSingle(String ownerUserName, String friendUserName, JsonNodeFactory factory) {
        ObjectNode objectNode = factory.objectNode();

        // check APPKEY format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.error("Bad format of APPKEY: " + APPKEY);

            objectNode.put("message", "Bad format of APPKEY");

            return objectNode;
        }

        if (StringUtils.isEmpty(ownerUserName)) {
            logger.error("Your userName must be provided，the value is username of imuser.");

            objectNode.put("message", "Your userName must be provided，the value is username of imuser.");

            return objectNode;
        }

        if (StringUtils.isEmpty(friendUserName)) {
            logger.error("The userName of friend must be provided，the value is username of imuser.");

            objectNode.put("message",
                    "The userName of friend must be provided，the value is username of imuser.");

            return objectNode;
        }

        try {
            URL addFriendSingleUrl = HttpClientUtils.getURL(APPKEY.replace("#", "/") + "/users/"
                    + ownerUserName + "/contacts/users/" + friendUserName);

            ObjectNode body = factory.objectNode();
            objectNode = HttpClientUtils.sendHTTPRequest(addFriendSingleUrl, credential, body, HttpMethod.METHOD_DELETE);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }

    //获取用户所有好友
    private static ObjectNode getFriends(String ownerUserName, JsonNodeFactory factory) {
        ObjectNode objectNode = factory.objectNode();

        // check APPKEY format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.error("Bad format of APPKEY: " + APPKEY);

            objectNode.put("message", "Bad format of APPKEY");

            return objectNode;
        }

        if (StringUtils.isEmpty(ownerUserName)) {
            logger.error("Your userName must be provided，the value is username of imuser.");

            objectNode.put("message", "Your userName must be provided，the value is username of imuser.");

            return objectNode;
        }

        try {

            URL addFriendSingleUrl = HttpClientUtils.getURL(APPKEY.replace("#", "/") + "/users/"
                    + ownerUserName + "/contacts/users");

            ObjectNode body = factory.objectNode();
            objectNode = HttpClientUtils.sendHTTPRequest(addFriendSingleUrl, credential, body, HttpMethod.METHOD_GET);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return objectNode;
    }

    //IM用户登录
    private static ObjectNode imUserLogin(String username, String password, JsonNodeFactory factory) {

        ObjectNode objectNode = factory.objectNode();

        // check appKey format
        if (!HttpClientUtils.match("^(?!-)[0-9a-zA-Z\\-]+#[0-9a-zA-Z]+", APPKEY)) {
            logger.error("Bad format of Appkey: " + APPKEY);

            objectNode.put("message", "Bad format of Appkey");

            return objectNode;
        }
        if (StringUtils.isEmpty(username)) {
            logger.error("Your username must be provided，the value is username of imuser.");

            objectNode.put("message", "Your username must be provided，the value is username of imuser.");

            return objectNode;
        }
        if (StringUtils.isEmpty(password)) {
            logger.error("Your password must be provided，the value is username of imuser.");

            objectNode.put("message", "Your password must be provided，the value is username of imuser.");

            return objectNode;
        }

        try {
            ObjectNode dataNode = factory.objectNode();
            dataNode.put("grant_type", "password");
            dataNode.put("username", username);
            dataNode.put("password", password);

            objectNode = HttpClientUtils.sendHTTPRequest(EndPoints.TOKEN_APP_URL, null, dataNode, HttpMethod.METHOD_POST);

        } catch (Exception e) {
            throw new RuntimeException("Some errors occurred while fetching a token by username and password .");
        }

        return objectNode;
    }

    //指定前缀和数量生成用户基本数据
    private static ArrayNode genericArrayNode(String usernamePrefix, Long number, JsonNodeFactory factory) {
        ArrayNode arrayNode = factory.arrayNode();
        for (int i = 0; i < number; i++) {
            ObjectNode userNode = factory.objectNode();
            userNode.put("username", usernamePrefix + "_" + i);
            userNode.put("password", DEFAULT_PASSWORD);

            arrayNode.add(userNode);
        }

        return arrayNode;
    }
}
