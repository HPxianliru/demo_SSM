package com.xlr.demo.util.memcached;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

/**
 * @Author: xlr
 * @Date: Created in 下午5:14 2017/8/30
 */
public class test {

    protected static MemCachedClient mcc = new MemCachedClient();

    static{

        // 设置缓存服务器列表，当使用分布式缓存的时，可以指定多个缓存服务器。这里应该设置为多个不同的服务，我这里将两个服务设置为一样的，大家不要向我学习，呵呵。
        String[] servers =
                {
                        "100.39.3.41:12001",
                        "100.39.3.41:12002",
                };

        // 设置服务器权重
        Integer[] weights = {3, 2};

        // 创建一个Socked连接池实例
        SockIOPool pool = SockIOPool.getInstance();

        // 向连接池设置服务器和权重
        pool.setServers(servers);
        pool.setWeights(weights);
        pool.setNagle(false);
        pool.setSocketTO(3000);
        pool.setSocketConnectTO(0);

        // initialize the connection pool
        pool.initialize();
    }

    public static void main(String[] args) {
        mcc.set("foo", "This is a test String");
        String bar = mcc.get("foo").toString();
        System.out.println(">>> " + bar);
    }
}
