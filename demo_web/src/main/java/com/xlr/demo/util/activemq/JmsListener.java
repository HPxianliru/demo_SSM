package com.xlr.demo.util.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

/**
 * @Author: xlr
 * @Date: Created in 下午10:07 2017/8/30
 */
@Configuration
@EnableJms
public class JmsListener{

    @Autowired
    CicCachingConnectionFactory  connectionFactory;

    @Autowired
    JmsInstance jmsInstance;

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(){
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setDestinationResolver(jmsInstance.getDestinationResolver());
        factory.setConcurrency( "2-10" );
        return factory;
    }
}
