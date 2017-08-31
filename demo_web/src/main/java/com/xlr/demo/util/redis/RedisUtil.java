package com.xlr.demo.util.redis;

import com.xlr.demo.utils.AppactionContextUtil;
import com.xlr.demo.utils.InitProperties;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: xlr
 * @Date: Created in 上午12:00 2017/8/30
 */
@SuppressWarnings("all")
public class RedisUtil {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(RedisUtil.class);

    private static JedisPool pool;

    static {
        System.out.println("redis 配置进行加载中。。。。。。。");
        if("on".equals(InitProperties.get("redis.on-off"))){
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(Integer.valueOf(InitProperties.get("codis.pool.maxActive")));
            config.setMaxIdle(Integer.valueOf(InitProperties.get("codis.pool.maxIdle")));
            config.setTestOnBorrow(Boolean.valueOf(InitProperties.get("codis.pool.testOnBorrow")));
            config.setTestOnReturn(Boolean.valueOf(InitProperties.get("codis.pool.testOnReturn")));
            if (InitProperties.get("codis.auth") == null) {
                pool = new JedisPool(config, InitProperties.get("codis.ip"),
                        Integer.valueOf(InitProperties.get("codis.port")),
                        InitProperties.get("codis.pool.timeout") == null ? Protocol.DEFAULT_TIMEOUT : Integer.valueOf(InitProperties.get("codis.pool.timeout")));
            } else {
                pool = new JedisPool(config, InitProperties.get("codis.ip"),
                        Integer.valueOf(InitProperties.get("codis.port")),
                        InitProperties.get("codis.pool.timeout") == null ? Protocol.DEFAULT_TIMEOUT : Integer.valueOf(InitProperties.get("codis.pool.timeout")),
                        InitProperties.get("codis.auth"));

            }
        }
    }

    public RedisUtil() {
    }

    public static Jedis getJedis() {
        return pool.getResource();
    }

    public static boolean set(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            String result = jedis.set(key, value);
            if (result.equals("OK")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    public static boolean set(String key, int expire, String value) {
        Jedis jedis = pool.getResource();
        try {
            String result = jedis.setex(key, expire, value);
            if (result.equals("OK")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }


    public static String get(String key) {
        Jedis jedis = pool.getResource();
        String result = null;
        try {
            result = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return result;
    }

    public static Set<String> keys(String pattern) {
        Jedis jedis = pool.getResource();
        Set<String> result = null;
        try {
            result = jedis.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return result;
    }


    public static long del(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return 0;
    }

    public static boolean exists(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     * 头入队列
     *
     * @param key
     * @param value
     * @return
     */
    public static Long lpush(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.lpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 未入队列
     *
     * @param key
     * @param value
     * @return
     */
    public static Long rpush(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.rpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 从左出队列
     *
     * @param key
     * @return
     */
    public static String lpop(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.lpop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 从右出队列
     *
     * @param key
     * @return
     */
    public static String rpop(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.rpop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * Return the length of the list stored
     *
     * @param key
     * @return
     */
    public static Long llen(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.llen(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 有序集合
     *
     * @param key
     * @param value
     * @return
     */
    public static void addSet(String key, String value) {
        Jedis jedis = pool.getResource();
        try {

            Long score = jedis.zcard(key);
            jedis.zadd(key, ++score, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    /**
     * 得到有序集合
     *
     * @param key
     * @return
     */
    public static Set<String> getSet(String key) {
        Jedis jedis = pool.getResource();
        try {

            Set<String> sets = jedis.zrange(key, 0, -1);
            return sets;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    public static String loadScript(String script) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.scriptLoad(script);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 分页得到有序集合
     *
     * @param key
     * @param size
     * @return
     */
    public static Set<String> zrange(String key, int size) {
        Jedis jedis = pool.getResource();
        try {
            Set<String> sets = jedis.zrange(key, 0, size);
            return sets;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 删除指定的元素
     *
     * @param key
     * @param values
     * @return
     */
    public static Long zrem(String key, String... values) {
        Jedis jedis = pool.getResource();
        try {
            Long count = jedis.zrem(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0l;
        } finally {
            jedis.close();
        }
    }



    public static Map<String, String> hget(String key) {
        Jedis jedis = pool.getResource();

        Map<String, String> map = null;
        try {
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return map;
    }

    public static void hmset(String key, Map<String,String> values) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hmset(key, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    /**
     *
     * @param key
     * @param values
     * @param expireSeconds 过期时间 (单位: 秒)
     */
    public static void hmset(String key, Map<String,String> values, int expireSeconds) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hmset(key, values);
            jedis.expire(key, expireSeconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }



    public static boolean hset(String key, String field, String fieldVal) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hset(key, field, fieldVal);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    public static boolean hset(String key, Map<String, String> map) {
        Jedis jedis = pool.getResource();
        try {
            for (Map.Entry<String, String> e : map.entrySet()) {
                jedis.hset(key, e.getKey(), e.getValue());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    public static boolean hset(String key, Map<String, String> map, int exprise) {
        Jedis jedis = pool.getResource();
        try {
            for (Map.Entry<String, String> e : map.entrySet()) {
                jedis.hset(key, e.getKey(), e.getValue());
            }
            jedis.expire(key, exprise);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     * 批量更新codis
     *
     * @param keys
     * @param maps
     * @return
     */
    public static boolean batchHset(List<String> keys, List<Map<String, String>> maps) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Pipeline pipeline = jedis.pipelined();
            int index = 0;
            for (String key : keys) {
                pipeline.hmset(key, maps.get(index++)); //key -map
            }
            if (index % keys.size() == 0) {
                pipeline.sync();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.disconnect();
                jedis.close();
            }
        }
        return false;
    }

    public static void main(String[] args) {

        //Codis.set("renthouse_13683685242_key",3600,"1");
//        int count = 10;
//        Jedis jedis = Codis.getJedis();
//        Pipeline p = jedis.pipelined();
//        Map<String, String> maps = new HashMap<>();
//        for (int i = 0; i < count; i++) {
//            maps.clear();
//            maps.put("k_" + i, "v_" + i);
//            p.hmset("k_" + i, maps);
//        }
//        p.sync();
//        jedis.close();
    }

    public static long incr(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.incr(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public static long decr(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.decr(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public static long setnx(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.setnx(key, "1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return -1L;
    }
    public static long setnx(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.setnx(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public static long setnx(String key, int expire, String value) {
        Jedis jedis = pool.getResource();
        try {

            if ("OK".equals(jedis.set(key, value, "NX", "EX", expire))) {
                return 1;
            }
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public static void expire(String key, int second) {
        Jedis jedis = pool.getResource();
        try {
            jedis.expire(key, second);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    public static long sadd(String key, String... members) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.sadd(key, members);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public static long scard(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.scard(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public static Set<String> smembers(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.smembers(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return null;
    }

    public static boolean sismember(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.sismember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return false;
    }

    public static long srem(String redisKey, String... strings) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.srem(redisKey, strings);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return -1l;
    }

    public static List<String> hmget(String key, String... fields){
        Jedis jedis=pool.getResource();
        try{
            return jedis.hmget(key,fields);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            jedis.close();
        }
    }

    public static String hget(String key, String field){
        Jedis jedis=pool.getResource();
        try{
            return jedis.hget(key,field);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            jedis.close();
        }
    }
}
