package com.xlr.demo.util.memcached;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;
import com.xlr.demo.utils.InitProperties;
import org.springframework.stereotype.Service;

/**
 * @Author: xlr
 * @Date: Created in 上午9:54 2017/8/30
 */
@Service("menacedUtil")
@SuppressWarnings("all")
public class MenacedUtil {

    //true表示二进制传输协议，默认false
    public static MemCachedClient mcc = new MemCachedClient(true);

    private static SockIOPool pool = null;

    static {
        System.out.println("memcached 配置进行加载中。。。。。。。");
        pool = SockIOPool.getInstance();
        String[] servers = new String[]{InitProperties.get("servers"),InitProperties.get("servers1")};
        pool.setServers(servers);
        pool.setWeights(new Integer[]{Integer.valueOf(InitProperties.get("weights")),2});
        pool.setInitConn(Integer.valueOf(InitProperties.get("initConn")));
        pool.setMinConn(Integer.valueOf(InitProperties.get("initConn")));
        pool.setMaxConn(Integer.valueOf(InitProperties.get("maxConn")));
        pool.setMaxIdle(Integer.valueOf(InitProperties.get("maxIdle")));
        pool.setMaintSleep(Integer.valueOf(InitProperties.get("maintSleep")));
        pool.setNagle("true".equals(InitProperties.get("nagle")));
        pool.setSocketConnectTO(Integer.valueOf(InitProperties.get("socketConnectTO")));
        pool.setSocketTO(Integer.valueOf(InitProperties.get("socketTO")));
        pool.initialize();
    }

    public boolean setToCache(String prefix, Object key, Object value) {
        return mcc.set(buildKey(prefix, key), value);
    }

    public boolean setToCache(String prefix, Object key, Object value,
                              long second) {
        return mcc.set(buildKey(prefix, key), value, toDate(second));
    }

    public boolean addToCache(String prefix, Object key, Object value) {
        return mcc.add(buildKey(prefix, key), value);
    }

    public boolean addToCache(String prefix, Object key, Object value, long second) {
        return mcc.add(buildKey(prefix, key), value, toDate(second));
    }

    public boolean replaceToCache(String prefix, Object key, Object value) {
        return mcc.replace(buildKey(prefix, key), value);
    }

    public boolean replaceToCache(String prefix, Object key, Object value,
                                  long second) {
        return mcc.replace(buildKey(prefix, key), value, toDate(second));
    }

    public boolean putCounterToCache(String prefix, Object key, long counter) {
        return mcc.set(buildKey(prefix, key), "" + counter);
		/*
		 * 客户端的storeCounter存在问题，在内部set，没有将counter值转化为String，而是直接作为Object进行处理的。因此调用incr、decr等方法会出错。
		 * 在本方法中，我们使用set方法，自己来实现这个简单功能。
		 *
		 * return mcc.storeCounter(buildKey(prefix, key), new Long(counter));
		 */
    }

    public long incrCounter(String prefix, Object key) {
        return mcc.incr(buildKey(prefix, key));
    }

    public long incrCounter(String prefix, Object key, long number) {
        return mcc.incr(buildKey(prefix, key), number);
    }

    public long decrCounter(String prefix, Object key) {
        return mcc.decr(buildKey(prefix, key));
    }

    public long decrCounter(String prefix, Object key, long number) {
        return mcc.decr(buildKey(prefix, key), number);
    }

    public long addOrIncrCounter(String prefix, Object key, long number) {
        return mcc.addOrIncr(buildKey(prefix, key), number);
    }

    public long addOrDecrCounter(String prefix, Object key, long number) {
        return mcc.addOrDecr(buildKey(prefix, key), number);
    }

    public Object getFromCache(String prefix, Object key) {
        return mcc.get(buildKey(prefix, key));
    }

    public Object[] getArrayFromCache(String prefix, Object[] key) {
        if(key == null){
            return null;
        }
        return mcc.getMultiArray(buildKeys(prefix, key));
    }

    public <E> List<E> getObjArrayFromCache(String prefix, Object[] key,
                                            Class<E> clasz) {
        Object[] objs = getArrayFromCache(prefix, key);
        if (objs == null) {
            return null;
        }
        List<E> list = new ArrayList<E>();
        for(Object obj : objs){
            list.add(clasz.cast(obj));
        }
        return list;
    }

    public <T extends Serializable> Map<T, Object> getMapFromCache(
            String prefix, T[] key) {
        if(key == null){
            return null;
        }
        Map<String, Object> map = mcc.getMulti(buildKeys(prefix, key));
        if(map == null || map.isEmpty()){
            return null;
        }
        Map<T, Object> ret = new HashMap<T, Object>();
        for(T k : key){
            Object obj = map.get(buildKey(prefix, k));
            ret.put(k, obj);
        }
        return ret;
    }

    public <T extends Serializable, E> Map<T, E> getMapFromCache(String prefix,
                                                                 T[] key, Class<E> clasz) {
        if(key == null || clasz == null){
            return null;
        }
        Map<String, Object> map = mcc.getMulti(buildKeys(prefix, key));
        if(map == null || map.isEmpty()){
            return null;
        }
        Map<T, E> ret = new HashMap<T, E>();
        for(T k : key){
            Object obj = map.get(buildKey(prefix, k));
            //使用cast，而不是(E)obj的强制类型转换，是因为通过clasz，可以对用户传入的E做详细控制。如果使用(E)obj,则用户无需传入clasz，则用户可以用任意E来接收返回值，从而调用时发生类型转换异常。
            ret.put(k, clasz.cast(obj));
        }
        return ret;
    }

    public long getCounter(String prefix, Object key) {
        return mcc.getCounter(buildKey(prefix, key));
    }

    public <T extends Serializable> Map<T, Long> getCounterMap(String prefix,
                                                               T[] key) {
        if(key == null){
            return null;
        }
        Map<String, Object> map = mcc.getMulti(buildKeys(prefix, key), null, true);
        Map<T, Long> ret = new HashMap<T, Long>();
        for(T k : key){
            Long count = -1L;
            if(map != null){
                Object obj = map.get(buildKey(prefix, k));
                if(obj != null){
                    try{
                        count = Long.valueOf((String)obj);
                    }catch(Exception e){
                        //出现问题，count返回-1即可。不做处理。
                        e.printStackTrace();
                    }
                }
            }
            ret.put(k, count);
        }
        return ret;
    }

    public boolean keyExists(String prefix, Object key){
        return mcc.keyExists(buildKey(prefix, key));
    }

    public boolean removeFromCache(String prefix, Object key) {
        return mcc.delete(buildKey(prefix, key));
    }

    public boolean flushAll() {
        return mcc.flushAll();
    }

    public long accessControl(String prefix, Object key, long millisecond, long limit){
        return this.accessControl(prefix, key, 1, millisecond, limit);
    }

    private long accessControl(String prefix, Object key, long number, long millisecond, long limit){
        String memKey = buildKey(prefix, key);
        long num = mcc.getCounter(memKey);
        //计数值小于limit，才进行增加，大于等于，都已经超越上限值，不考虑。
        if(num < limit){
            boolean notIncSign = false;
            //num为-1，表示计数值不存在。
            if(num == -1){
                //如果需要处理失效时间。
                if(millisecond > 0){
                    notIncSign = mcc.add(memKey, number + "", new Date(millisecond));
                }else{
                    notIncSign = mcc.add(memKey, number + "");
                }
            }
            //notIncSign为true，表示add成功，不需要inc,num为number值。为false时有两个可能性：1、计数值存在，需要inc；2、在add时候，产生了并发，没有add成功，则进行inc操作。
            if(notIncSign){
                num = number;
            }else{
                num = mcc.incr(memKey, number);
            }
        }
        //计数值通过上一个代码块，incr方法，因为并发问题，有可能返回大于limit的值，因此，在小于limit时，返回计数值，大于limit，一律返回limit值作为最终计数值。
        if(num < limit){
            return num;
        }
        return limit;
    }

    private String buildKey(String prefix, Object key){
        return (new StringBuilder()).append(prefix).append(key).toString();
    }

    private String[] buildKeys(String prefix, Object[] key){
        if(key == null){
            return null;
        }
        int size = key.length;
        String[] ret = new String[size];
        for(int i =0;i<size ;i++){
            ret[i] = buildKey(prefix, key[i]);
        }
        return ret;
    }

    private Date toDate(long second){
        return new Date(second * 1000l);
        //早起版本，下面注释的这段代码设置时间，会有问题。目前的版本该问题已经被修正。上面使用的方式，早期和目前版本都适用。
		/*
		 * return new Date(System.currentTimeMillis() + (second * 1000l));
		 */
    }

}

