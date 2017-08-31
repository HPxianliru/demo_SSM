package com.xlr.demo.observer;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

/**
 * @Author: xlr
 * @Date: Created in 下午11:27 2017/8/30
 */
@Service("logListener")
public class LogListener implements ApplicationListener<LogEvent> {


    public void onApplicationEvent(LogEvent event) {
        if(event instanceof LogEvent){
            System.out.println("观察者收到消息处理");
            LogEvent myEvent=(LogEvent)event;
            System.err.println( myEvent.getSource()+"观察者处理");
        }
    }

}
