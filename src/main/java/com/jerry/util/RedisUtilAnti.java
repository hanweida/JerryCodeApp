package com.jerry.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 与反作弊系统交互的redis
 * @Class Name RedisUtilAnti
 * @Author 005
 * @Create In 2014-6-12
 */
public class RedisUtilAnti{
	

	private static String SYSTEM_CONFIG_FILE = "application.properties";
	/**
     * 非切片客户端链接
     */
    private static Jedis jedis;

    /**
     * 非切片链接池
     */
    private static JedisPool jedisPool; 
    
	private static final String HOST = "";
	private static final int PORT = 0;  
    

    static {
    	InputStream is = null;
		try {
//			is = SyncToRedis.class.getClassLoader().getResourceAsStream(SYSTEM_CONFIG_FILE);
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(SYSTEM_CONFIG_FILE);
			Properties props = new Properties();
			props.load(is);
			initialPool(props);
		} catch (Exception e) {
		} finally {
			if (null != is ) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	
    }
    
	
	private static void initialPool(Properties props) {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(Integer.parseInt(props.getProperty("anti_redis_maxActive")));
		config.setMaxIdle(Integer.parseInt(props.getProperty("anti_redis_setMaxIdle")));
		config.setMaxWait(Long.valueOf(props.getProperty("anti_redis_maxWait")));
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		config.setTestWhileIdle(true);
        try {
            jedisPool = new JedisPool(config, props.getProperty("anti_redis_host"), Integer.parseInt(props.getProperty("anti_redis_port")), Integer.parseInt(props.getProperty("anti_redis_timeout")), props.getProperty("anti_redis_password"));
            jedis = jedisPool.getResource();
        } catch (JedisConnectionException e){
            jedisPool = new JedisPool(config, props.getProperty("anti_redis_host"), Integer.parseInt(props.getProperty("anti_redis_port")), Integer.parseInt(props.getProperty("anti_redis_timeout")));
        }
	}
	

	/**
	 * 
	 * @Title: getDataByKey
	 * @Description: TODO(取得当前key和field下的值)
	 * @param @param key
	 * @param @param field
	 * @param @return    设定文件
	 * @return byte[]    返回类型
	 * @throws
	 */
	public static byte[] getDataByKey(String key , String field){
		jedis = jedisPool.getResource();
		byte[] reStr = jedis.hget(key.getBytes(), field.getBytes());
		jedisPool.returnResource(jedis);
		return reStr;
	}
 
	/**
	 * 支持字节类型存储
	 * @param key
	 * @param value
	 * @return String
	 */
	public static boolean saveByte(String key, String field, byte[] value) {
		boolean flag = false;//返回串
		try{
			jedis = jedisPool.getResource(); 
			//String state = jedis.set(key.getBytes(), value);
			long s = jedis.hset(key.getBytes(), field.getBytes(), value);
			if(s==1||s==0){//当返回为1时说明是新建，当返回为0时说明是更新
				flag = true;
			}
			jedisPool.returnResource(jedis);
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}
	
	public static boolean saveHset(String key, String field, String value) {
		boolean flag = false;//返回串
		try{
			jedis = jedisPool.getResource(); 
			long s = jedis.hset(key, field, value);
			if(s==1||s==0){//当返回为1时说明是新建，当返回为0时说明是更新
				flag = true;
			}
			jedisPool.returnResource(jedis);
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}

	
	public static boolean updateByte(String key, String field, byte[] value) {
		boolean flag = false;
		try{
			jedis = jedisPool.getResource(); 
			//String state = jedis.set(key.getBytes(), value);
			long s = jedis.hset(key.getBytes(), field.getBytes(), value);
			if(s==1||s==0){//当返回为1时说明是新建，当返回为0时说明是更新
				flag = true;
			}
			jedisPool.returnResource(jedis);
		}catch(Exception e){
			flag = false;
		}
		//return state;
		return flag;
	}
	
	
	public static boolean deleteByte(String key, String field) {
		boolean flag = false;
		try{
			jedis = jedisPool.getResource();
			long s = jedis.hdel(key.getBytes(), field.getBytes());
			if(s==1){//当返回为1时说明删除成功
				flag = true;
			}
			jedisPool.returnResource(jedis);
		}catch(Exception e){
			flag = false;
		}
		return flag;
	}
	
	public static String getByte(String key, String field) {
		jedis = jedisPool.getResource(); 
		String retval = jedis.hget(key, field);
		jedisPool.returnResource(jedis);
		return retval;
	}
	
	public static Map<String,String> getHsetAll(String key) {
		jedis = jedisPool.getResource(); 
		Map<String,String> map = jedis.hgetAll(key);
		jedisPool.returnResource(jedis);
		return map;
	}
	
	
	/**
	 * 反回字节类型数据
	 * @param key
	 * @return String
	 */
	public static byte[] getByte(String key) {
		jedis = jedisPool.getResource(); 
		byte[] value = jedis.get(key.getBytes());
		jedisPool.returnResource(jedis);
		return value;
	}	  
	
	public static String save(String key, String value) {
		jedis = jedisPool.getResource(); 
		String state = jedis.set(key, value);
		jedisPool.returnResource(jedis);
		return state;  
	}
	
	public static String get(String key) {
		jedis = jedisPool.getResource(); 
		String value = jedis.get(key);
		jedisPool.returnResource(jedis);
		return value;
	}
	
	public static String update(String key, String value) {
		jedis = jedisPool.getResource();
		String state = jedis.set(key, value);
		jedisPool.returnResource(jedis);
		return state;
	}
	
	public static void remove(String key, String field) {
		jedis = jedisPool.getResource();
		//jedis.del(key.getBytes());抛异常还是改用字符串remove
		jedis.hdel(key.getBytes(), field.getBytes());
		jedisPool.returnResource(jedis);
	}
	
	public static void deleteByKey(String key) {
		jedis = jedisPool.getResource();
		jedis.del(key);
		jedisPool.returnResource(jedis);
	}
	
	/**
	 * dbsize 数据库key总数
	 * @param jedis
	 * @return long 
	 */
	private static long getDBSize(Jedis jedis) {
		jedis = jedisPool.getResource();
		long size = jedis.dbSize();
		jedisPool.returnResource(jedis);
		return size;
	}

	/**
	 * 查询持久化策略
	 * @param jedis
	 * @return List<String>
	 */
	private static List<String> getSaveConfig(Jedis jedis) {
		return jedis.configGet("save");
	}

	/**
	 * 设置持久化策略
	 * @param jedis
	 * @return String
	 */
	private static String setSaveConfig(Jedis jedis) {
		String celue_1 = "800 1";
		String celue_2 = "400 2";
		return jedis.configSet("save", celue_1 + " " + celue_2);
	}

	/**
	 * 阻塞IO后持久化数据然后关闭redis (shutdown)
	 * @param jedis
	 * @return String
	 */
	private static String shutdown(Jedis jedis) {
		return jedis.shutdown();
	}

	/**
	 * 将此redis设置为master主库
	 * @param jedis
	 * @return String
	 */
	private static String slaveofNoOne(Jedis jedis) {
		return jedis.slaveofNoOne();
	}

	/**
	 * 将此redis根据host/port设置为slaveof从库 
	 * @param jedis
	 * @return String
	 */
	private static String slaveof(Jedis jedis) {
		return jedis.slaveof(HOST, PORT);
	}

	/**
	 * 查询redis的info信息 
	 * @param jedis
	 * @return String
	 */
	private static String info(Jedis jedis) {
		return jedis.info();
	}

	/**
	 * select? 
	 * @param jedis
	 * @return String
	 */
	private static String select(Jedis jedis) {
		return jedis.select(1);
	}  
	
	/**
	 * 批量查询
	 * @param ids
	 * @return List<String>
	 */
	public List<String> batchSet(String[] ids) {
		return jedis.mget(ids);
	}
	
	//TODO 等下一阶段实现动态扩容，可以加master节量来扩容redis内存,需要一致性hash算法
	public void set() {

	}
	
	/**
	 * 清空所有数据库中的所有键
	 */
	public static void fullAll() {
		jedis = jedisPool.getResource();
		jedis.flushAll();
		jedisPool.returnResource(jedis);
	}
	//TODO 容灾能否实现javaAPI级
	
	/**
	 * @Title: getAllKeys
	 * @Description: TODO(取得顶层的所有key)
	 * @param @return    设定文件
	 * @return Set<String>    返回类型
	 * @throws
	 */
	public static Set<String> getAllKeys(){
		jedis = jedisPool.getResource();
		Set<String> setStr = jedis.keys("[0-9]");
		jedisPool.returnResource(jedis);
		return setStr;
	}
	
	/**
	 * @Title: searchKeys
	 * @Description: TODO(查询当前的key)
	 * @param @param pattern
	 * @param @return    设定文件
	 * @return Set<String>    返回类型
	 * @throws
	 */
	public static Set<String> searchKeys(String pattern){
		jedis = jedisPool.getResource();
		Set<String> setStr = jedis.hkeys(pattern);
		jedisPool.returnResource(jedis);
		return setStr;
	}
	

	
}

