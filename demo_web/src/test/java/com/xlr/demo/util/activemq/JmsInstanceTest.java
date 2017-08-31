package com.xlr.demo.util.activemq;

import org.junit.Test;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import static org.junit.Assert.*;

/**
 * @Author: xlr
 * @Date: Created in 下午9:15 2017/8/30
 */
@ContextConfiguration(locations = {
        "/spring/applicationContext-test-web.xml"
})
public class JmsInstanceTest extends AbstractJUnit4SpringContextTests {
    Destination destination ;
    @Test
    public void jmsTemplate(){
        JmsInstance jmsInstance = new JmsInstance();
        jmsInstance.setDeliveryMode(2);
        jmsInstance.setExplicitQosEnabled(true);
        jmsInstance.setPubSubDomain(false);
        jmsInstance.setReceiveTimeout(3000);

        final String content = "消息";

        jmsInstance.send("aa", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(content);
            }
        });
    }
}