package com.xlr.demo.util.memcached;

import com.xlr.demo.bean.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @Author: xlr
 * @Date: Created in 下午4:24 2017/8/30
 */
@ContextConfiguration(locations = {
        "/spring/applicationContext-test-web.xml"
})
public class MemcachedUtilTest extends AbstractJUnit4SpringContextTests{

    private final static String TEST = "test";

    @Autowired
    MenacedUtil menacedUtil;

    @Test
    public void addSetValue(){
        User user = new User();
        user.setUserName("aaa");
        user.setPassword("ddd");
        user.setAge(26);
//        if(menacedUtil.setToCache(TEST,"user",user)){
//            System.out.println("存入成功");
//        }else{
//            System.out.println("存入失败");
//        }
            System.out.println("MCC存入成功"+menacedUtil.mcc.get("foo"));

    }
}
