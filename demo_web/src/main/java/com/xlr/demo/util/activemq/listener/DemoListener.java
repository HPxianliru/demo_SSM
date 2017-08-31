package com.xlr.demo.util.activemq.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @Author: xlr java配置消费者
 * @Date: Created in 下午10:23 2017/8/30
 */
@Component("demoListener")
public class DemoListener extends MessageListenerAdapter {

    @JmsListener(destination = "aa", concurrency = "5-10")
    public void placeOrder(Message message, Session session) throws JMSException {
        try {
            Object object = getMessageConverter().fromMessage( message );
            System.out.println(object.toString());
            System.out.println(session);
            message.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
