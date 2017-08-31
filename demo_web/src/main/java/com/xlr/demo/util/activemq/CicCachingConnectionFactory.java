package com.xlr.demo.util.activemq;

import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.stereotype.Service;


/**
 * @Author: xlr
 * @Date: Created in 下午8:37 2017/8/30
 */
@Service("connectionFactory")
public class CicCachingConnectionFactory extends CachingConnectionFactory {

    private int sessionCacheSize = 10;

    public CicCachingConnectionFactory() {
        super(new ActiveMqFactory().getPooledConnectionFactory());
    }

    @Override
    public int getSessionCacheSize() {
        return sessionCacheSize;
    }

    @Override
    public void setSessionCacheSize(int sessionCacheSize) {
        if(sessionCacheSize>10){
            this.sessionCacheSize = sessionCacheSize;
        }
    }
}
