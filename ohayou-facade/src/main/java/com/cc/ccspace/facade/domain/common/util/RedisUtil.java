package com.cc.ccspace.facade.domain.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis cache 工具类
 *
 */
//@Service
public  class RedisUtil {
    private Logger logger = LoggerFactory.getLogger(RedisUtil.class);
    /*@Resource(name="redisTemplate")*/
    private static RedisTemplate redisTemplate;//在app-redis.xml中配置的属性
    //private static RedisTemplate redisTemplate;
    /*static{
    	redisTemplate=(RedisTemplate)SpringContextUtils.getBean("redisTemplate");//不能采用此种方法 因为redisUtil是在上下文初始化完成之前注入到容器中的
    }*/

    public static RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}


	/**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public static boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public static Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate
                .opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 使用最原始直接的命令获取string key的value字节数组
     *@author CAI.F
     * @date: 日期：2016年12月18日 时间:下午6:16:28
     * @param key
     * @return
     *
     */
    public Object get(final byte[] key) {
        byte[] result= (byte[])redisTemplate.execute(new RedisCallback<Object>(){

            @Override
            public Object doInRedis(RedisConnection redCon)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                byte[] o= redCon.get(key);

                return o;
            }


        });
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public static boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            result = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 获取某地市关联的部门信息 部门名称和id
     *@author CAI.F
     * @date: 日期：2016年12月15日 时间:下午3:22:31
     * @param
     * @return
     *
     */
   /* public Map<String,String> getAllDeptHash(final String regDeptKey){

        Map<String,String>  result=(Map<String, String>)redisTemplate.execute(new RedisCallback<Object>(){
            Map<String,String> deptStrInfo=new HashMap<String,String>();
            @Override
            public Map<String,String> doInRedis(RedisConnection con)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                StringRedisSerializer ser = new StringRedisSerializer();
        *//*   List<Map> list=new ArrayList<Map>();
           for(int i=0;i<3;i++){
            Map<String,String> m1=new HashMap<String,String>();
            String dept="eask_thr_yw"+String.valueOf(i);
            String id=String.valueOf(48746+i);
            m1.put(dept,id);
            list.add(m1);
           }
           for(Map<String,String> m:list){
        	   Set<String> s=m.keySet();
                  for(String str:s){
                	  con.hSet(ser.serialize(regKey), ser.serialize(str),ser.serialize(m.get(str)));
                  }
        	   } *//*
                //获取到所有的部门 key为部门名称 value为部门id
                Map<byte [],byte []> deptInfo=con.hGetAll(ser.serialize(regDeptKey));
                Set<byte []> deptNames=deptInfo.keySet();//获取所有部门名称字节数组集合
                for(byte[] depName:deptNames){
                    System.out.println("部门名称"+ser.deserialize(depName));
                    System.out.println("部门id"+ser.deserialize(deptInfo.get(depName)));
                    deptStrInfo.put(ser.deserialize(deptInfo.get(depName)),ser.deserialize(depName));

                }
                return deptStrInfo;

                //Map<String,Object> result=new HashMap<String,Object>();

                //result.add("deptInfo",deptStrInfo);
                //result.add("subInfo",subStrInfo);
                //JSON.json(l);


            }


        });
        return result;

    }*/
    /**
     * 获取某地市关联的部门信息 部门名称和id
     *@author CAI.F
     * @date: 日期：2016年12月15日 时间:下午3:22:31
     * @param
     * @return
     *
     */
   /* public Map<String,String> getAllSpecialHash(final String regSpecialKey){

        Map<String,String>  result=(Map<String, String>)redisTemplate.execute(new RedisCallback<Object>(){
            Map<String,String> deptStrInfo=new HashMap<String,String>();
            @Override
            public Map<String,String> doInRedis(RedisConnection con)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                StringRedisSerializer ser = new StringRedisSerializer();
        *//*   List<Map> list=new ArrayList<Map>();
           for(int i=0;i<3;i++){
            Map<String,String> m1=new HashMap<String,String>();
            String dept="eask_thr_yw"+String.valueOf(i);
            String id=String.valueOf(48746+i);
            m1.put(dept,id);
            list.add(m1);
           }
           for(Map<String,String> m:list){
        	   Set<String> s=m.keySet();
                  for(String str:s){
                	  con.hSet(ser.serialize(regKey), ser.serialize(str),ser.serialize(m.get(str)));
                  }
        	   } *//*
                //获取到所有的部门 key为部门名称 value为部门id
                Map<byte [],byte []> specInfo=con.hGetAll(ser.serialize(regSpecialKey));
                Set<byte []> specIds=specInfo.keySet();//获取所有部门名称字节数组集合
                for(byte[] specId:specIds){
                    System.out.println("专业id"+ser.deserialize(specId));
                    System.out.println("专业名称"+ser.deserialize(specInfo.get(specId)));
                    deptStrInfo.put(ser.deserialize(specId),ser.deserialize(specInfo.get(specId)));

                }
                return deptStrInfo;

                //Map<String,Object> result=new HashMap<String,Object>();

                //result.add("deptInfo",deptStrInfo);
                //result.add("subInfo",subStrInfo);
                //JSON.json(l);


            }


        });
        return result;

    }*/
    /**
     * 获取某地市关联的部门信息 部门名称和id
     *@author CAI.F
     * @date: 日期：2016年12月15日 时间:下午3:22:31
     * @param
     * @return
     *
     */
    public static  boolean setHashKeyDefaultExpireOneWeek(final String key, final String field, final String value){
        
        boolean result=(Boolean)redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection con)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                StringRedisSerializer ser = new StringRedisSerializer();
                //  con.sMembers("eask_thr_17706525623_".getBytes());
                boolean res=false;
                byte [] b1 = ser.serialize(key);
                boolean exists=con.exists(b1);


                byte [] b2 = ser.serialize(field);
                byte [] b3=serialize(value);
                con.hSet(b1,b2,b3);
                if(!exists){
                    con.expire(b1,64800);
                    //首次设置的key，设置key的过期时间为一周
                    // 一般问题一周内就会处理完毕，处理完毕的在结案时会自动删除相应的key
                    // 群组人员数量的key和问题详情key过期时间都设置为1周 以免特殊情形有工单一直不结案的
                    //特殊情况下（过了一周才处理问题）需要对应的信息时则从数据库中查询 本地要存储一个问题群组人员关联表 结案时删除
                }
                //
                res=true;
                return res;
            }
        });
    return result;

    }

/**  * describe: 用于设置永久性的key 类似于人员信息这种
	 * @author CAI.F
	 * @date:  日期:2017/2/23 时间:17:26
	 * @param    
	 */
    public static  boolean setHashKeyWithOutExpireTime(final String key, final String field, final String value){

        boolean result=(Boolean)redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection con)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                StringRedisSerializer ser = new StringRedisSerializer();
                //  con.sMembers("eask_thr_17706525623_".getBytes());
                boolean res=false;
                byte [] b1 = ser.serialize(key);

                byte [] b2 = ser.serialize(field);
                byte [] b3=serialize(value);
                con.hSet(b1,b2,b3);

                res=true;
                return res;
            }
        });
        return result;

    }
















    /**
     * 设置属性值为object的hashkey
     *@author CAI.F
     * @date: 日期：2017年2月15日 时间:下午1:04:48
     * @param key
     * @param field
     * @param value
     * @return
     *
     */
  public static  boolean setHashKeyObject(final String key, final String field, final Object value){
	  
        boolean result=(Boolean)redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection con)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                StringRedisSerializer ser = new StringRedisSerializer();
                boolean res=false;
                con.hSet(ser.serialize(key),ser.serialize(field),serialize(value));
                res=true;
                return res;
            }
        });
    return result;

    }
  
  //序列化 
  public static byte [] serialize(Object obj){
      ObjectOutputStream oos=null;
      ByteArrayOutputStream baos=null;
      try {
          baos=new ByteArrayOutputStream();
          oos=new ObjectOutputStream(baos);
          oos.writeObject(obj);
          byte[] byt=baos.toByteArray();
          
          oos.flush();
		  oos.close();
		  baos.close();
          return byt;
      } catch (IOException e) {
          e.printStackTrace();
      }
      return null;
  }
  //反序列化
  public static Object deserialize(byte[] bytes){
	  Object obj=null;
	  try {
          ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
          ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bais));
             obj=ois.readObject();     
             ois.close();
             bais.close();
		} catch (OptionalDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
  
      
      return obj;
  }

  /**
   * 反序列化 注意redis中存储的数据 尤其是object类型的  写入和读取使用的序列化方式必须
   * 要保持一致
   * 一般key 和field都是string的 
   * 直接getBytes或者使用StringRedisSerializer的serialize方法即可
   *@author CAI.F
   * @date: 日期：2017年2月16日 时间:上午9:44:47
   * @param object
   * @return
   *
   */
  
  public static Map<String,Object> deserializeBytesMap(Map<byte[],byte[]> object){
	
      Map<String,Object> question = new HashMap<String,Object>();
      StringRedisSerializer ser = new StringRedisSerializer();

	  try {
		  Set<Entry<byte[], byte[]>> entries = object.entrySet();
		  for (Entry<byte[], byte[]> entry : entries) {
			  
			  ByteArrayInputStream bais = new ByteArrayInputStream(entry.getValue());
			  ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bais));
			  String key = ser.deserialize(entry.getKey());
			  //System.out.println(key);
			  //System.out.println(key+":"+ois.readObject()); 
			  question.put(key,ois.readObject());
		  } 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
      return question;
  }

  
  
  /**
   * 获取属性值为object的hashkey
   * @author CAI.F
   * @date: 日期：2017年2月15日 时间:下午1:04:48
   * @param key
   * @param field
   * @param
   * @return
   *
   */
public static Object getHashKeyObject(final String key, final String field){
	  
      Object result=redisTemplate.execute(new RedisCallback<Object>(){
          @Override
          public Object doInRedis(RedisConnection con)
                  throws DataAccessException {
              // TODO Auto-generated method stub
              StringRedisSerializer ser = new StringRedisSerializer();
             Object res=deserialize(con.hGet(ser.serialize(key),ser.serialize(field)));
             // con.hSet(key,ser.serialize("update_time"),ser.serialize(String.valueOf(System.currentTimeMillis())));
              return res;
          }
      });
  return result;
  }

public static Map<String,Object> getAllHashFiedlsByKey(final String key){
	  
    Map<String,Object> result=(Map<String, Object>) redisTemplate.execute(new RedisCallback<Object>(){
        @Override
        public Object doInRedis(RedisConnection con)
                throws DataAccessException {
            // TODO Auto-generated method stub
            StringRedisSerializer ser = new StringRedisSerializer();
           Object res=deserializeBytesMap(con.hGetAll(ser.serialize(key)));
           // con.hSet(key,ser.serialize("update_time"),ser.serialize(String.valueOf(System.currentTimeMillis())));
            return res;
        }
    });
return result;

}
/**
 * 获取hashKey中普通的string类型的field 
 * @author CAI.F
 * @date: 日期：2017年2月15日 时间:下午1:04:48
 * @param key
 * @param field
 * @param
 * @return
 */
 public static String getHashKey(final String key, final String field){
        
        String value= (String)redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection con)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                StringRedisSerializer ser = new StringRedisSerializer();
                //  con.sMembers("eask_thr_17706525623_".getBytes());
                String result="";
                if(con.hExists(ser.serialize(key), ser.serialize(field))){
                	// byte[]   values =con.hGet(key.getBytes(),field.getBytes());
                  byte[]  values2 =con.hGet(ser.serialize(key),ser.serialize(field));
                  Object ll=deserialize(values2);
                  result= String.valueOf(ll);
                }
               // con.hSet(key,ser.serialize("update_time"),ser.serialize(String.valueOf(System.currentTimeMillis())));
               
                return result;
            }
        });
    return value;

    }
    
    /**
     * 设置键值及过期时间
     *@author CAI.F
     * @date: 日期：2016年12月18日 时间:下午5:12:07
     * @param
     * @return
     *
     */
    public static boolean set(final byte[] key, final byte[] value, final long seconds)
    {
        boolean result =(Boolean)redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection redCon)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                boolean setResult=false;
                redCon.set(key,value);
                redCon.expire(key,seconds);//设置超时时间
                setResult=true;
                return setResult;
            }
        });
        return result;
    }

/**  * describe: 自增key的值，不是int的value的话会报错
	 * @author CAI.F
	 * @date: 2017/1/11 时间:10:43
	 * @param
	 */
      public static boolean incr(final byte[] key, final long num)
    {
        boolean result =(Boolean)redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection redCon)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                boolean setResult=false;
                redCon.incrBy(key,num);
                setResult=true;
                return setResult;
            }
        });
        return result;
    }

    /**  * describe: 获取hashkey中的field数量 可用于存储群组的成员 快速获取群组人数 用户标示作为field
    	 * @author CAI.F
    	 * @date:  日期:2017/2/23 时间:17:44
    	 * @param    
    	 */
    public static long hLen(final String key)
    {
        long result =(Long) redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection redCon)
                    throws DataAccessException {
                // TODO Auto-generated method stub

                StringRedisSerializer ser = new StringRedisSerializer();
                long result=redCon.hLen(ser.serialize(key));
                return result;
            }
        });
        return result;
    }
    /**  * describe: 删除缓存key 譬如问题结案时删除
    	 * @author CAI.F
    	 * @date:  日期:2017/2/23 时间:17:58
    	 * @param    
    	 */
    public static long del(final String key)
    {
        long result =(Long)redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection redCon)
                    throws DataAccessException {
                // TODO Auto-generated method stub
                StringRedisSerializer ser = new StringRedisSerializer();
                long result = redCon.del(ser.serialize(key));
                return result;
            }
        });
        return result;
    }

    public void setRedisTemplate(
            RedisTemplate<Serializable, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

/**
 * 设置普通的string类型的key
 *@author CAI.F
 * @date: 日期：2017年2月16日 时间:上午10:14:40
 * @param key
 * @param value
 * @return
 *
 */
	public static boolean set(final byte[] key, final byte[] value) {
		// TODO Auto-generated method stub
		 boolean result =(Boolean)redisTemplate.execute(new RedisCallback<Object>(){
	            @Override
	            public Object doInRedis(RedisConnection redCon)
	                    throws DataAccessException {
	                // TODO Auto-generated method stub
	                boolean setResult=false;
	                redCon.set(key,value);
	                setResult=true;
	                return setResult;
	            }
	        });
	        return result;
	}

	
	/**
	 * 查看redis中某个hash类型key中是否存在某个属性
	 *@author CAI.F
	 * @date: 日期：2017年2月16日 时间:上午10:18:18
	 * @param key
	 * @param field
	 * @return
	 *
	 */
	public static boolean hashFieldExists(final String key, final String field) {
		// TODO Auto-generated method stub
		 boolean exists =(Boolean)redisTemplate.execute(new RedisCallback<Object>(){
	            @Override
	            public Object doInRedis(RedisConnection redCon)
	                    throws DataAccessException {
	                // TODO Auto-generated method stub
	                StringRedisSerializer srs=new StringRedisSerializer();
	                boolean exists=  redCon.hExists(srs.serialize(key),srs.serialize(field));
	                return exists;
	            }
	        });
	        return exists;
	}


    public static void main(String[] args) {

    }
} 