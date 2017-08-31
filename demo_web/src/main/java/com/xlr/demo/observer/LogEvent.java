package com.xlr.demo.observer;

import org.springframework.context.ApplicationEvent;

/**
 * @Author: xlr
 * @Date: Created in 下午11:25 2017/8/30
 */
public class LogEvent extends ApplicationEvent {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public LogEvent(Object source) {
        super( source );
        System.out.println( "传送过来对象" + source );
    }

    public void print() {
        System.out.println( "hello spring event[MyEvent]" );
    }
}