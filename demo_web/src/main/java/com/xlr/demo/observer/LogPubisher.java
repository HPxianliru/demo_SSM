package com.xlr.demo.observer;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

/**
 * @Author: xlr
 * @Date: Created in 下午11:28 2017/8/30
 */
@Service("logPubisher")
public class LogPubisher implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext =  applicationContext;

    }

    public void publishEvent(ApplicationEvent event){
        System.out.println("发布事件");
        applicationContext.publishEvent(event);
    }
}
