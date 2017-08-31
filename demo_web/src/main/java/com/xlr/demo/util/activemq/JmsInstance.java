package com.xlr.demo.util.activemq;

import org.springframework.jms.core.JmsTemplate;

/**
 * @Author: xlr
 * @Date: Created in 下午8:50 2017/8/30
 */

public class JmsInstance extends JmsTemplate {

    //区别它采用的模式为false是p2p为true是订阅
    private  boolean pubSubDomain;
    //deliveryMode, priority, timeToLive 的开关，要生效，必须配置为true，默认false
    private boolean explicitQosEnabled;
    //发送模式  DeliveryMode.NON_PERSISTENT=1:非持久 ; DeliveryMode.PERSISTENT=2:持久
    private int deliveryMode;
    //超时设置
    private long receiveTimeout;

    public JmsInstance() {
        super(new CicCachingConnectionFactory());
        super.setDeliveryMode(getDeliveryMode());
        super.setExplicitQosEnabled(isMessageTimestampEnabled());
        super.setReceiveTimeout(getReceiveTimeout());
        super.setPubSubDomain(isPubSubDomain());
        System.out.println( "JMS 启动 ==========");
    }

    @Override
    public boolean isPubSubDomain() {
        return pubSubDomain;
    }

    @Override
    public void setPubSubDomain(boolean pubSubDomain) {
        this.pubSubDomain = pubSubDomain;
    }

    @Override
    public boolean isExplicitQosEnabled() {
        return explicitQosEnabled;
    }

    @Override
    public void setExplicitQosEnabled(boolean explicitQosEnabled) {
        this.explicitQosEnabled = explicitQosEnabled;
    }

    @Override
    public int getDeliveryMode() {
        return deliveryMode;
    }

    @Override
    public void setDeliveryMode(int deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    @Override
    public long getReceiveTimeout() {
        return receiveTimeout;
    }

    @Override
    public void setReceiveTimeout(long receiveTimeout) {
        this.receiveTimeout = receiveTimeout;
    }
}
