package com.xlr.demo.util.activemq;

import com.xlr.demo.utils.InitProperties;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.jms.pool.PooledConnectionFactory;


import static com.xlr.demo.utils.InitProperties.get;

/**
 * @Author: xlr
 * @Date: Created in 下午8:14 2017/8/30
 */
public class ActiveMqFactory extends PooledConnectionFactory {

    static ActiveMQConnectionFactory factory = null;//

    PooledConnectionFactory pooledConnectionFactory = null;
    static String broker = InitProperties.get("jms.brokerURL");
    static {
        try {
            factory = new ActiveMQConnectionFactory(broker);
        } catch (Exception e) {
            try {
                throw new Exception("The jms.brokerURL Must not be empty\n");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    public ActiveMqFactory() {
        pooledConnectionFactory = new PooledConnectionFactory();
        pooledConnectionFactory.setConnectionFactory(factory);
        pooledConnectionFactory.initConnectionsPool();
    }

    public PooledConnectionFactory getPooledConnectionFactory(){
        if(null != pooledConnectionFactory){
            return pooledConnectionFactory;
        }
        return null;
    }

}
