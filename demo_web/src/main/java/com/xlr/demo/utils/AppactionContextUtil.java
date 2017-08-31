package com.xlr.demo.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by xianliru on 2017/7/26.
 */
public class AppactionContextUtil implements ApplicationContextAware {

    static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext =applicationContext;
    }

    public static  <T> T getBeanByname(String beanName){
      return (T) applicationContext.getBean(beanName);
    }

}
