package com.cc.ccspace.core.manager.impl;

import com.cc.ccspace.core.manager.RedisManager;
import com.cc.ccspace.facade.domain.bizobject.handler.MyThreadTaskAbortPolicy;
import com.cc.ccspace.facade.domain.common.constants.CommonConstants;
import com.cc.ccspace.facade.domain.common.constants.RedisConstants;
import com.cc.ccspace.facade.domain.common.util.DateUtil;
import com.cc.ccspace.facade.domain.common.util.logutil.InitLog;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import redis.clients.jedis.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @AUTHOR CF
 * @DATE Created on 2017/9/18 18:49.
 */
@Component
public class RedisManagerImpl implements RedisManager {
    private static JedisPool jedisPool = null;
    private static InitLog logger = new InitLog();
    /**
     * redis lock模式  不为空则说明使用降级模式
     * 一旦lock被某一线程持有 其他线程直接返回false 不在进行重试操作 降低线程sleep重试的性能损耗
     */
    private static final  String LOCK_MODEL="ps_str_lock_model";

    /**
     * 锁等待时间，防止线程饥饿
     */
    private  int timeoutMsecs = 1* 1000;
    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;
    //   初始化Redis连接池
    static {
        initJedisPool();
    }

    /**
     * 获取Jedis实例
     */
    public static Jedis getJedis() {
        Jedis jedis = null;
        int count = 0;
        do {
            try {
                if (jedisPool == null) {
                    initJedisPool();
                }
                jedis = jedisPool.getResource();
                logger.info("时间" + DateUtil.standardFormat.get().format(new Date()) + ",jedisPool中实例情况," +
                        "active=" + jedisPool.getNumActive()+",idle="+jedisPool.getNumIdle()+",waiters="+jedisPool.getNumWaiters());
            } catch (Exception e) {
                logger.error("Redis Exception :", e);
                logger.warn("时间" + DateUtil.standardFormat.get().format(new Date()) + ",jedisPool中实例情况," +
                        "active=" + jedisPool.getNumActive()+",idle="+jedisPool.getNumIdle()+",waiters="+jedisPool.getNumWaiters());
                if (jedisPool.getNumIdle() == 0 && jedisPool.getNumActive() == RedisConstants.MAX_ACTIVE) {
                    logger.warn("时间" + DateUtil.standardFormat.get().format(new Date()) + ",jedisPool中实例耗尽," +
                            "active=" + jedisPool.getNumActive()+jedisPool.getNumIdle());
                }
                jedisPool = null;
                initJedisPool();// 防止意外情况资源耗尽 重新初始化jedisPool 这个只是一解燃眉之急而已 最终解决还是要在程序中注意及时释放连接实例
            }
            count++;
        } while (jedis == null && count < RedisConstants.REDIS_RETRY_COUNT);//重试10次
        return jedis;
    }

    //初始化Redis连接池
    private static void initJedisPool() {
        FileInputStream in = null;//System.getenv("CC_RESOURCE");
        try {
            in = new FileInputStream(new File(System.getenv(CommonConstants.CC_RESOURCES_DIR) + "/cc_jedis.properties"));
            Properties prop = new Properties();
            prop.load(in);
            String ADDR = prop.getProperty("jedis.host").trim();
            int PORT = Integer.parseInt(prop.getProperty("jedis.port").trim());
            String AUTH = prop.getProperty("jedis.auth").trim();
            JedisPoolConfig config = initConfig();
            logger.info("ADDR:" + ADDR + ",PORT:" + PORT + ",AUTH:" + AUTH);
            if (StringUtils.isEmpty(AUTH)) {
                jedisPool = new JedisPool(config, ADDR, PORT, RedisConstants.TIMEOUT);
            } else {
                jedisPool = new JedisPool(config, ADDR, PORT, RedisConstants.TIMEOUT, AUTH);
            }
            logger.info("Redis连接池初始化成功，Host" + ADDR + ",PORT:" + PORT);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            logger.error("Redis 连接配置文件 jedis.properties 加载失败！");
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }
    }

    private static JedisPoolConfig initConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(RedisConstants.MAX_ACTIVE);
        config.setMaxIdle(RedisConstants.MAX_IDLE);
        config.setMaxWaitMillis(RedisConstants.MAX_WAIT);///表示当borrow一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出 　
        config.setMinEvictableIdleTimeMillis(RedisConstants.MIN_IDLE_TIME);
        config.setTestOnBorrow(RedisConstants.TEST_ON_BORROW);
        return config;
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = getJedis();
        Map<String, String> map = new HashMap<>();
        try {
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            logger.error("redis hget failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return map;
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = getJedis();
        String str = "";
        try {
            str = jedis.hget(key, field);
        } catch (Exception e) {
            logger.error("redis hget failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return str;
    }

    @Override
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = getJedis();
        List<String> list = new ArrayList<>();
        try {
            list = jedis.hmget(key, fields);
        } catch (Exception e) {
            logger.error("redis hget failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return list;
    }

    @Override
    public long hset(String key, String field, String value) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error("redis hset failed, key=" + key + ",value=" + value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return count;
    }

    @Override
    public long hset(String key, String field, String value, int expireTime) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.hset(key, field, value);
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            logger.error("redis hset failed, key=" + key + ",value=" + value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return count;
    }

    @Override
    public void hmset(String key, Map<String, String> fieldValueMap) {
        Jedis jedis = getJedis();

        try {
            jedis.hmset(key, fieldValueMap);
        } catch (Exception e) {
            logger.error("redis hmset failed, key=" + key + ",fieldValueMap=" + fieldValueMap.toString());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    @Override
    public void set(String key, String value) {
        Jedis jedis = getJedis();

        try {
            jedis.set(key, value);
        } catch (Exception e) {
            logger.error("redis set failed, key=" + key + ",value=" + value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public String get(String key) {
        Jedis jedis = getJedis();
        String value = "";
        try {
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error("redis get failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return value;
    }

    @Override
    public void set(String key, String value, int expireTime) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
            jedis.expire(key, expireTime);
        } catch (Exception e) {
            logger.error("redis set expire failed, key=" + key + ",value=" + value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public long expire(String key, int expireTime) {
        Jedis jedis = getJedis();
        long time = 0l;
        try {
            time = jedis.expire(key, expireTime);
        } catch (Exception e) {
            logger.error("redis del failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return time;
    }

    @Override
    public long del(String key) {
        Jedis jedis = getJedis();
        long count = 0l;
        try {
            count = jedis.del(key);
        } catch (Exception e) {
            logger.error("redis del failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return count;
    }

    @Override
    public long hdel(String key, String field) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.hdel(key, field);
        } catch (Exception e) {
            logger.error("redis hdel failed, key=" + key + ",field=" + field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return count;
    }

    @Override
    public long hsetnx(String key, String field, String value) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.hsetnx(key, field, value);
        } catch (Exception e) {
            logger.error("redis hsetnx failed, key=" + key + ",field=" + field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return count;
    }

    @Override
    public void incr(String tradeKey) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.incr(tradeKey);
        } catch (Exception e) {
            logger.error("redis incr failed, key=" + tradeKey);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void zadd(String key, long score, String value) {
        Jedis jedis = getJedis();
        long count = 0L;
        try {
            count = jedis.zadd(key, score, value);
        } catch (Exception e) {
            logger.error("redis zadd failed, key=" + key);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public List<String> configGet(String pattern) {
        Jedis jedis = getJedis();
        List<String> result=null;
        try {
            result=jedis.configGet(pattern);
        }catch (Exception e){
            logger.error("redis configGet failed, pattern=" + pattern);
        }finally{
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public String configSet(String parameter, String value) {

        Jedis jedis = getJedis();
        String result=null;
        try {
            result=jedis.configSet(parameter,value);
        }catch (Exception e){
            logger.error("redis configSet failed, parameter=" + parameter+",value="+value);
        }finally{
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public Long publish(String channel, String message) {

        Jedis jedis = getJedis();
        Long result=null;
        try {
            result=jedis.publish(channel,message);
        }catch (Exception e){
            logger.error("redis publish failed, channel=" + channel+",message="+message);
        }finally{
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public void subscribe(JedisPubSub jedisPubSub, String describe,String... channels) {
        Jedis jedis = getJedis();
        try {
            jedis.subscribe(jedisPubSub,channels);
        }catch (Exception e){
            logger.error("redis subscribe failed, jedisPubSubName=" + describe+",channels="+ Arrays.toString(channels));
        }finally{
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public void psubscribe(JedisPubSub jedisPubSub, String describe,String... patterns) {
        Jedis jedis = getJedis();
        try {
            jedis.subscribe(jedisPubSub,patterns);
        }catch (Exception e){
            logger.error("redis psubscribe failed, jedisPubSubName=" + describe+",patterns="+ Arrays.toString(patterns));
        }finally{
            if (jedis != null) {
                jedis.close();
            }
        }
    }
    @Override
    public boolean lock(String lockKey) {
        Jedis jedis = getJedis();
        //jedis连接获取不到的情况直接返回true 也即redis lock不再起效 所有进来的都直接认为成功
        if (jedis == null) {
            return true;
        }
        try {
            boolean lockModelHighCostLevel = StringUtils.isEmpty(jedis.get(LOCK_MODEL));
            if (lockModelHighCostLevel) {
                /*重试模式 场景举例  甲客户充值时遇到task占用余额lock 此时可以等待一秒钟左右进行重试
                  task操作完后可以继续完成充值 而不是直接返回系统繁忙的提示 这样体验不是很好
                  延迟一定的毫秒数,最多延迟一秒钟  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                  只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进程,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                  使用随机的等待时间可以一定程度上保证公平性
                 */
                return retryGetLock(jedis, lockKey);
            } else {
                //不提供重试 适用于秒杀抢夺一件产品的场景 直接返回秒杀失败即可
                return tryGetLock(jedis, lockKey);
            }
        } catch (Exception e) {
            logger.error("redis lock  failed, lockKey=" + lockKey, e);
        } finally {
            jedis.close();
        }
        return false;
    }

    private boolean retryGetLock(Jedis jedis, String lockKey) throws InterruptedException {
        int timeout = timeoutMsecs;
        int waitMsecs = new Random().nextInt(100) + 200;//200~300ms
        int retryCou = 0;
        while (timeout >= 0) {
            if (tryGetLock(jedis, lockKey)) {
                return true;
            }
            timeout -= waitMsecs;
            Thread.sleep(waitMsecs);
            retryCou++;
        }
        if (retryCou > 0) {
            logger.info("retryGetLock failed,thread="+Thread.currentThread().getName() + ",retryCou=" + retryCou + ",lockKey=" + lockKey);
        }
        return false;
    }

    private boolean tryGetLock(Jedis jedis, String lockKey) {
        long expires = System.currentTimeMillis() + expireMsecs;
        String expiresStr = String.valueOf(expires); //锁到期时间
        if (jedis.setnx(lockKey, expiresStr) == 1) {//首次设置该值，直接返回成功 获得锁 返回
            // lock acquired
            return true;
        }
        // 不是第一次设置该锁 ，也就是别人抢先了，但万一那人有什么情况
        // （倒霉的redis挂了且key的信息还没同步好 亿万分之一的几率 或者超时 太磨叽）总之锁又被释放了
        //  那别人就又有竞争机会了 继续进行下面的尝试
        String currentValueStr = this.get(lockKey); //redis里的时间
        if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()) {
            //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
            // lock is expired
            String newExpireTime = String.valueOf(System.currentTimeMillis() + expireMsecs);
            //竞态获取资格
            String oldValueStr = jedis.getSet(lockKey, newExpireTime);
            //获取上次的到期时间，并设置现在的锁到期时间，
            if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void unLock(String key) {
        del(key);
    }


   static class CCTestPubSub extends JedisPubSub{

        @Override
        public void onMessage(String channel, String message) {
            System.out.println(message);
        }
        @Override
        public void onSubscribe(String channel, int subscribedChannels) {

            System.out.println(channel);

        }

    }

    private static ExecutorService ratePool = initPool("ratePool");// newWorkStealingPool(16);  适用于任务之间相差时间较大 波动性很大的场景

    private static ExecutorService initPool(String type) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(type + "-SettleTaskJob-pool-%d").build();
        return new ThreadPoolExecutor(8, 8,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10000),
                namedThreadFactory,
                new MyThreadTaskAbortPolicy(logger, type));
    }

    public static void main(String[] args) {
        Jedis jedis = getJedis();
//        incrTest(jedis);
//        hsetTest(jedis);
//        transTest(jedis);
//        pipeLineTest(jedis);
          System.out.println(jedis.hget("cc","cc"));
       /* publishTest(jedis);
        subscribeTest(jedis);*/

    }

    /**
     * @description  管道测试 将所有命令一次性发出
     * @author CF create on 2018/3/17 18:55
     */
    private static void pipeLineTest(Jedis jedis) {
        Pipeline pl=jedis.pipelined();

        System.out.println(pl.isInMulti());
        pl.set("pipe-test01","01");
        pl.set("pipe-test02","02");
        pl.hset("pipe-test03","03","03");
        List list=pl.syncAndReturnAll();
        System.out.println(list.toString());
    }

    /**
     * @description 事务测试 保证某些redis操作必然成功
     * 在客户端做这些并发严重的场景下性能很差  会产生大量无用的重试
     * 最好使用分布式锁 或者脚本
     * @author CF create on 2018/3/17 18:07
     */
    private static void transTest(Jedis jedis) {
        while("OK".equals(jedis.watch("tras-cc-test"))){
            try{
                Transaction trans=jedis.multi();
                trans.set("tras-cc-test","lalalla");

                trans.set("tras-cc-test","djfsdkjfjk");
                //只要提交前有任何对此key的更改发生 返回的result就会变为空
                // 相反则返回对应操作的成功默认值
                List result=trans.exec();
                if(CollectionUtils.isEmpty(result)){
                    jedis.unwatch();
                }else {
                    break;
                }
            }catch(Exception e){
               e.printStackTrace();
            }

        }
    }

    private static void publishTest(Jedis jedis) {
        jedis.publish("cc-pb-test-01"," pubsub test lalalal");
        jedis.close();

    }

    private static void subscribeTest(Jedis jedis) {
        CCTestPubSub sub=new CCTestPubSub();
        jedis.subscribe(sub,"cc-pb-test-01");

    }

    private static void hsetTest(Jedis jedis) {
        //        jedis.hget("cc", "cc");

        String str = "{\n" +
                "\"stockCode\":\"300573\",\n" +
                "\"stockName\":\"建设银行\",\n" +
                "\"nowPrice\":\"32.52\",\n" +
                "\"yesPrice\":\"32.78\",\n" +
                "\"status\":\"1\",\n" +
                "\"stockFullCode\":\"SZ300573\"\n" +
                "}";
        String str2 = jedis.hget("STOCK_CODE_LIST", "ALL_MARKET_CODE");
        jedis.hset("PHOENIX_STOCK_CODE_LIST", "ALL_MARKET_CODE", str2);
/*        JedisPoolConfig config = initConfig();
        JedisPool jedisPool2 = new JedisPool(config, "116.62.21.135", 6379, RedisConstants.TIMEOUT, "redis_dev_1234");
        Jedis jedis = jedisPool2.getResource();
        System.out.println(jedis.get("cc"));*/
    }

    private static void incrTest(Jedis jedis) {
        String hourKey = "PHOENIX_SEND_VERIFY_COUNT_201709211815270000011001_13758080963";
        String dayKey = "PHOENIX_SEND_VERIFY_TOTAL_COUNT_201709211815270000011001_13758080963";
        String hourCou = jedis.get(hourKey);
        String dayCou = jedis.get(dayKey);
        int count = StringUtils.isEmpty(hourCou) ? 0 : Integer.parseInt(hourCou);
        int totalCount = StringUtils.isEmpty(dayCou) ? 0 : Integer.parseInt(dayCou);
        jedis.incr(hourKey);
        jedis.incr(dayKey);
        jedis.close();
    }


}
