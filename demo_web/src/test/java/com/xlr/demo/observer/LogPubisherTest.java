package com.xlr.demo.observer;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.junit.Assert.*;

/**
 * @Author: xlr
 * @Date: Created in 下午11:35 2017/8/30
 */
@ContextConfiguration(locations = {
        "/spring/applicationContext-test-web.xml",
        "/spring/applicationContext-test-jms.xml"
})
public class LogPubisherTest extends AbstractJUnit4SpringContextTests {
    @Autowired
    LogPubisher logPubisher;
    @Test
    public void publishEvent() throws Exception {
        logPubisher.publishEvent(new LogEvent( "aa" ));
    }

}