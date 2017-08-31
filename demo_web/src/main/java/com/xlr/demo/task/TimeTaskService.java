package com.xlr.demo.task;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

/**
 * @Author: xlr
 * @Date: Created in 下午3:14 2017/8/30
 */
@Service("timeTaskService")
public class TimeTaskService {

    public void findTask(){
        System.out.println("定时任务启动==============");
    }
}
