package com.cc.ccspace.facade.domain.common.util;

import com.alibaba.fastjson.JSON;
import com.cc.ccspace.facade.domain.common.constants.RedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.SortingParams;
import tk.mybatis.mapper.util.StringUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/6/21 22:41.
 */

public final class JedisUtil {

    private static JedisPool jedisPool = null;
    private static final String charSet="UTF-8";
    static Logger logger = LoggerFactory.getLogger(JedisUtil.class);
    /**
     * 初始化Redis连接池
     */
    static {
        try {
            String location="D:\\cc\\conf";//System.getenv("CC_RESOURCE");
//          "D:\\cc\\conf";//
            FileInputStream in = new FileInputStream(new File(location+"/jedis.properties"));
            //System.getenv("CC_RESOURCE");
            Properties prop = new Properties();
            prop.load(in);
            String ADDR = prop.getProperty("jedis.host").trim();
            int PORT = Integer.parseInt(prop.getProperty("jedis.port").trim());
            String AUTH = prop.getProperty("jedis.auth").trim();
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(RedisConstants.MAX_ACTIVE);
            config.setMaxIdle(RedisConstants.MAX_IDLE);
            config.setMaxWaitMillis(RedisConstants.MAX_WAIT);///表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出 　
            config.setMinEvictableIdleTimeMillis(RedisConstants.MIN_IDLE_TIME);
            config.setTestOnBorrow(RedisConstants.TEST_ON_BORROW);
            logger.info("ADDR:"+ADDR+",PORT:"+PORT+",AUTH:"+AUTH);
            if (StringUtil.isEmpty(AUTH)) {
                jedisPool = new JedisPool(config, ADDR, PORT, RedisConstants.TIMEOUT);
            }else {
                jedisPool = new JedisPool(config, ADDR, PORT, RedisConstants.TIMEOUT, AUTH);
            }
            logger.debug("Redis连接池初始化成功，Host" + ADDR + ",PORT:" + PORT);
        } catch (Exception e) {
            logger.error("Redis 连接配置文件 jedis.properties 加载失败！");
        }
    }

    /**
     * 获取Jedis实例
     * @return Jedis实例
     */
    public  static Jedis getJedis() {
        Jedis jedis = null;
        int count =0;
        do{
            try{
                jedis = jedisPool.getResource();
            } catch (Exception e) {
                jedisPool.returnResource(jedis);
                logger.error("Redis Exception :"+e.toString());
                e.printStackTrace();
            }
            count++;
        }while(jedis==null&&count< RedisConstants.REDIS_RETRY_COUNT);//重试10次
        return jedis;
    }

    /**
     * 释放jedis资源
     * @param jedis 需要释放的jedis实例
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResourceObject(jedis);
        }
    }

    public static void removeKey(String key){
        Jedis jedis = getJedis();
        if(jedis!=null){
            try{
                @SuppressWarnings("unused")
                long ret = jedis.del(key);
                logger.debug("removeKey key:"+key+",ret:"+ret);
            }finally{
                JedisUtil.returnResource(jedis);
            }
        }
    }

    /**
     * @description 对传入对象进行序列化转换后再存入redis 一般用于存储list对象
     * 或者提前从数据库查询出的自定义数据结构 适用于大类型value存储
     * @author CF create on 2017/6/23 11:28
     * @param
     * @return
     */
    public static void setSerializeObj(String key,Object value){
        Jedis jedis = getJedis();
        if(jedis!=null){
            try{
                Charset cs=Charset.defaultCharset();
                String ret= jedis.set(key.getBytes(),serialize(value));
                logger.debug("set key:"+key+",value:"+ JSON.toJSONString(value)+",ret:"+ret);
            } finally{
                JedisUtil.returnResource(jedis);
            }
        }
    }
    /**
     * @description 对key设置超时时间
     * @author CF create on 2017/6/23 15:15
     * @param
     * @return
     */
    public static void setSerializeObj(String key,Object value,int expire){
        Jedis jedis = getJedis();
        if(jedis!=null){
            try{
                String ret= jedis.set(key.getBytes("UTF-8"),serialize(value));
                //字节流转换默认编码方式       Charset cs=Charset.defaultCharset();
                jedis.expire(key.getBytes("UTF-8"),expire);
                //此处即便不进行编码方式制定 后面也会自动转换为UTF-8的编码字节流 指定下会稍稍快点减少判断
                logger.debug("set key:"+key+",value:"+ JSON.toJSONString(value)+",ret:"+ret);
            } catch (IOException e) {
                logger.error("redis set io异常，错误信息："+e.getMessage()+",key："+key);//e.getStackTrace()[0]
            } finally{
                JedisUtil.returnResource(jedis);
            }
        }
    }
    /**
     * @description  设置key的超时时间
     * @author CF create on 2017/6/23 15:16
     * @param
     * @return
     */
    public static void expire(String key,int expireSeconds){
        Jedis jedis = getJedis();
        if(jedis!=null){
            try{
                if(key!=null){
                    jedis.expire(key.getBytes("UTF-8"),expireSeconds);
                }
            } catch (UnsupportedEncodingException e) {
                logger.error("redis expire key异常："+e.getMessage()+":key="+key);
            } finally{
                returnResource(jedis);
            }
        }

    }
    /**
     * @description 获取序列化存储的对象
     * @author CF create on 2017/6/23 13:21
     * @param
     * @return
     */
    public static Object getSerializeObj(String key) {
        Jedis jedis = getJedis();
        Object value = null;
        if (jedis != null) {
            try {
                byte[] ret = jedis.get(key.getBytes(Charset.defaultCharset()));
                value=ret==null?null: unserizlize(ret);
                logger.debug("get key:" + key + ",value:" + JSON.toJSONString(value));
            } finally {
                JedisUtil.returnResource(jedis);
            }
        }
        return value;
    }
    /**
     * @description  对key进行自增操作  increase 增加量
     * 前提是key要是整数类型的 string类型会报错
     * 对于不存在的key则会直接生成key并赋值
     * @author CF create on 2017/6/23 16:00
     * @param
     * @return 返回增加后的实际key值
     */
    public static Long incrBy(String key,long increase){
        Jedis jedis = getJedis();
        Long value = null;
        if (jedis != null) {
            try {
                value=jedis.incrBy(key,increase);
            }catch(Exception e){
                logger.error("incrBy failed! key"+key);
            }
            finally{
                returnResource(jedis);
            }
        }
        return value;
    }
    /**
     * @description 简单的设置String类型键值
     * @author CF create on 2017/6/23 13:12
     * @param
     * @return
     */
    public static void set(String key,String value){
        Jedis jedis = getJedis();
        if(jedis!=null){
            try{
                String ret = jedis.set(key,value);
                logger.debug("set key:"+key+",value:"+value+",ret:"+ret);
            }  finally{
                JedisUtil.returnResource(jedis);
            }
        }
    }
    public static void set(String key,String value,int expires){
        Jedis jedis = getJedis();
        if(jedis!=null){
            try{
                String ret = jedis.set(key,value);
                jedis.expire(key,expires);
                logger.debug("set key:"+key+",value:"+value+",ret:"+ret);
            }  finally{
                JedisUtil.returnResource(jedis);
            }
        }
    }
    //序列化
    public static byte [] serialize(Object obj){
        ObjectOutputStream obi;
        ByteArrayOutputStream bai;
        try {
            bai=new ByteArrayOutputStream();
            obi=new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt=bai.toByteArray();
            obi.close();
            bai.close();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //反序列化
    public static Object unserizlize(byte[] byt){
        ObjectInputStream oii;
        ByteArrayInputStream bis;
        bis=new ByteArrayInputStream(byt);
        try {
            oii=new ObjectInputStream(bis);
            Object obj=oii.readObject();
            oii.close();
            bis.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @description 获取key的value
     * @author CF create on 2017/6/23 15:19
     * @param
     * @return
     */
    public static String get(String key){
        Jedis jedis = getJedis();
        if(jedis!=null){
            try{
                return jedis.get(key);//协议默认编码是UTF-8
            }finally{
                JedisUtil.returnResource(jedis);
            }
        }
        return "";
    }
    /**
     * @description  获取对象 一般存储的是Bean对象 文章 list 之类的自定义复杂结构
     * @author CF create on 2017/6/28 17:57
     * @param
     * @return
     */
    public static Object getObject(String key){
        Jedis jedis = getJedis();
        if(jedis!=null){
            try{
                return jedis.get(key.getBytes());//协议默认编码是UTF-8
            }finally{
                JedisUtil.returnResource(jedis);
            }
        }
        return "";
    }
    public static void hset(String key,String field,String value){
        Jedis jedis = getJedis();
        if(jedis!=null){
            try{
                @SuppressWarnings("unused")
                long ret = jedis.hset(key, field, value);
                //logger.info("hset key:"+key+"field:"+field+",value:"+value+",ret:"+ret);
            }finally{
                JedisUtil.returnResource(jedis);
            }
        }
    }
    public static String hget(String key,String field){
        Jedis jedis = getJedis();
        if(jedis!=null){
            try{
                return jedis.hget(key, field);
            }finally{
                JedisUtil.returnResource(jedis);
            }
        }
        return null;
    }
    /**
     * @description  获取普通key的过期时间 返回-1说明无过期时间  -2说明key已经过期
     * get key的时候系统默认编码与set时的编码相同的话（key为string类型）不用担心编码转换问题
     * @author CF create on 2017/6/23 14:58
     * @param
     * @return
     */
    public static long ttlKey(String key){
        Jedis jedis = getJedis();
        long time=0;
        if(jedis!=null){
            try {
                time= jedis.ttl(key);
            }finally{
                JedisUtil.returnResource(jedis);
            }
        }
        return time;
    }
    /**
     * @description  查看key存在与否
     * @author CF create on 2017/6/23 15:29
     * @param
     * @return
     */
    public static boolean exists(String key){
        Jedis jedis = getJedis();
        boolean exists=false;
        if(jedis!=null){
            try {
                exists=jedis.exists(key);
            }finally{
                returnResource(jedis);
            }
        }
        return exists;

    }
  /**
      * @description  自增key并返回key的值 要求key必须是数字  默认自增 1
      * @author CF create on 2017/7/28 15:42
      * @param
      * @return
      */
    public  static Long incr(String key){
      Jedis jedis=getJedis();
        Long nowValue=0L;
        if (jedis != null) {
            try {
                nowValue = jedis.incr(key);
            } catch (Exception e) {
                logger.warn("redis oper error -- key:" + key + ",method:incr");
            } finally {
                returnResource(jedis);
            }
        }
        return nowValue;
    }

//  score从小到大的顺序排列set中的成员并取出
    public static Set<String> getZSetMembersRevRange(String key,Long startTime,Long endTime){
        Jedis jedis=getJedis();
        Set<String> result = null;
        if (jedis != null) {
            try {
                result = jedis.zrevrangeByScore(key, startTime, endTime);
            }catch(Exception e){
                logger.warn("redis oper error -- key:" + key + ",method:getZSetMembersRevRange");
            }finally{
                returnResource(jedis);
            }
        }
    return result;
    }

    /**
     * @description  对list进行排序 list存储hset类型
     * @author CF create on 2017/9/6 11:35
     */
    public static List<String> sortList(String key,String pattern){
        Jedis jedis=getJedis();
        List<String> resultList = null;
        if (jedis != null) {
            try {
              /* Random r=new Random();
                for(int i=0;i<10;i++){
                    int level=r.nextInt(10);
                    Map<String,Object> m=new HashMap<>();
                    m.put("age",r.nextInt(100));
                    m.put("vipLevel",level);
                    m.put("name","test "+i);
                    Thread.sleep(1000);
                    Date date=new Date();
                    Long nowTime=date.getTime();
                    m.put("createTime",nowTime);
                    m.put("updateTime",DateUtils.df.get().format(date));

                  *//*  Person  p=new Person();
                    p.setAge(r.nextInt(100));
                    p.setVipLevel(level);
                    p.setName("test "+i);*//*
//                    p.setCreateTime(nowTime);
//                    p.setUpdateTime(DateUtils.df.get().format(new Date()));
                    //通过人员关联的创建时间排序
                    jedis.hset("person-time-viplevel:10000"+i,"createTime",String.valueOf(nowTime));
                    jedis.hset("person-time-viplevel:10000"+i,"updateTime",DateUtils.df.get().format(date));
                    jedis.hset("person-time-viplevel:10000"+i,"vipLevel",String.valueOf(level));
                    jedis.hset("person-time-viplevel:10000"+i,"age",String.valueOf(r.nextInt(100)));
                    jedis.hset("person-time-viplevel:10000"+i,"key","10000"+i);
                    jedis.lpush(key, JSON.toJSONString(m));
                }*/
                SortingParams sp=new SortingParams();
                sp.by(pattern);
                sp.asc();
                sp.limit(0,100);
                resultList=jedis.sort(key,sp);
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally{
                returnResource(jedis);
            }
        }
        return resultList;
    }

    public static void main(String[] args) throws Exception {
//        simpleGetTest();
        Double.parseDouble("1504688570639");
        String timePattern= "person-time-viplevel:*->createTime";
        String levelPattern="person-time-viplevel:*->vipLevel";

        String agePattern="person-time-viplevel:10000*->age";

        List<String>   list = sortList("cc-sort-test",timePattern);
//        List<String>   list = sortList("cc-sort-test2",agePattern);
        for(int i=0;i<list.size();i++){
            System.out.println("listdata:"+list.get(i));
        }
        for(int i=0;i<list.size();i++){
            System.out.println("hashData:"+ JSON.toJSONString(
                    JedisUtil.getJedis().hgetAll("person-time-viplevel:10000"+i)));
//                    JedisUtil.getJedis().del("person-time-viplevel:"+i)));
        }

    }

    private static void simpleGetTest() throws IOException, InterruptedException {
        Map m=new HashMap();
        m.put("cc","lalala");
        m.put("sex","man");
//        setSerializeObj("cc",m);
        String key="cc";
        set(key, JSON.toJSONString(m),10);
//        setSerializeObj(key,m,10);
        while(exists(key)) {
            Thread.sleep(1000);
            System.out.println(ttlKey(key));//-1 无过期时间 -2 已过期
//            System.out.println(get(key));
            if(get(key)!=null){//getSerializeObj
//               System.out.println("serialize key get:"+JSON.toJSONString(getSerializeObj(key)));
                System.out.println("json key get:"+get(key));
            }else{
                System.out.println(key+"已过期或已不存在");
            }
        }
    }

}
