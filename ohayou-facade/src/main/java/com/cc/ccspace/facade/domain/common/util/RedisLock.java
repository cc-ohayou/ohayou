package com.cc.ccspace.facade.domain.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Random;

public class RedisLock {

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    //@SuppressWarnings("rawtypes")
    //@Resource(name="redisTemplate")
    private static RedisTemplate redisTemplate;
    static {
    redisTemplate=(RedisTemplate) SpringContextUtils.getBean("redisTemplate");
    }
    private int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = new Random().nextInt(100);

    /**
     * Lock key path.
     */
    private String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 1* 1000;

    private volatile boolean locked = false;

    /**
     * Detailed constructor with default acquire timeout 10000 msecs and lock expiration of 60000 msecs.
     *
     * @param lockKey lock key (ex. account:1, ...)
     */
    @SuppressWarnings("rawtypes")
	public RedisLock(RedisTemplate redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    /**
     * Detailed constructor with default lock expiration of 60000 msecs.
     *
     */
    public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs) {
        this(redisTemplate, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }
    public RedisLock(String lockKey, int timeoutMsecs) {
        this(redisTemplate, lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }
    /**
     * Detailed constructor.
     *
     */
    public RedisLock(RedisTemplate redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs) {
        this(redisTemplate, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }
    //使用自身的redisTemplate作为参数
    public RedisLock(String lockKey, int timeoutMsecs, int expireMsecs) {
        this(redisTemplate, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }
    /**
     * @return lock key
     */
    public String getLockKey() {
        return lockKey;
    }

    @SuppressWarnings("unchecked")
	private String get(final String key) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] data = connection.get(serializer.serialize(key));
                    connection.close();
                    if (data == null) {
                        return null;
                    }
                    return serializer.deserialize(data);
                }
            });
        } catch (Exception e) {
            logger.error("get redis error, key : {}");
        }
        return obj != null ? obj.toString() : null;
    }
/**
 * key不存在的时候设置新值返回成功 true
 * key存在的时候返回false
 *@author CAI.F
 * @date: 日期：2016年11月4日 时间:下午1:02:40
 * @param key
 * @param value
 * @return
 *
 */
    @SuppressWarnings("unchecked")
	private boolean setNX(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    Boolean success = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}"+ key);
        }
        return obj != null ? (Boolean) obj : false;
    }
/**
 * key存在的时候返回key的旧值 并设置新值
 * key不存在的时候返回null 设置新值
 *@author CAI.F
 * @date: 日期：2016年11月4日 时间:下午1:01:29
 * @param key
 * @param value
 * @return
 *
 */
    
    @SuppressWarnings("unchecked")
	private String getSet(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] ret = connection.getSet(serializer.serialize(key), serializer.serialize(value));
                    connection.close();
                    return serializer.deserialize(ret);
                }
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}"+key);
        }
        return obj != null ? (String) obj : null;
    }

    /**
     * 获得 lock.
     * 实现思路: 主要是使用了redis 的setnx命令,缓存了锁.
     * reids缓存的key是锁的key,所有的共享, value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间)
     * 执行过程:
     * 1.通过setnx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁
     * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时且还没有被其他人重新设置的话,则设置新的值
     *
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    public synchronized boolean lock() throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs ;
            String expiresStr = String.valueOf(expires); //锁到期时间
            if (this.setNX(lockKey, expiresStr)) {//首次设置该值，直接返回成功 获得锁 返回
                // lock acquired
                locked = true;
                return true;
            }
          //不是第一次设置该锁 ，也就是别人抢先了，但万一那人有什么情况
            // （倒霉的redis挂了且key的信息还没同步好 亿万分之一的几率）锁又被释放了（或者超时 太磨叽）
            // 那别人就又有竞争机会了 继续进行下面的尝试
            String currentValueStr = this.get(lockKey); //redis里的时间
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()){
                //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
            	//其他线程设置key的值 当前redis中的lock_key值为当前时间+超时时间
                // lock is expired
                String newExpireTime=String.valueOf(System.currentTimeMillis() + expireMsecs);
                String oldValueStr = this.getSet(lockKey, newExpireTime);
                //获取上次的到期时间，并设置现在的锁到期时间，
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //防止误删（覆盖，因为key是相同的）了他人的锁
                    // 这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受
                    //[分布式的情况下]:如果这个时候，多个线程恰好都到了这里，
                    // 但是只有一个线程的设置值和当前值相同，才有权利获取锁
                    // lock acquired
                    locked = true;//获取到锁 锁超时了 重新设置超时时间
                    return true;
                }
            }
            int waitTimeMsecs=DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
            timeout -= waitTimeMsecs;

            /*
                延迟100 毫秒,  这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进程,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                使用随机的等待时间可以一定程度上保证公平性
             */
            Thread.sleep(waitTimeMsecs);

        }
        return false;
    }


    /**
     * Acqurired lock release.
     */
    public synchronized void unlock() {
        if (locked) {
            redisTemplate.delete(lockKey);
            locked = false;
        }
    }

}