package com.xlr.demo.controller;

import com.xlr.demo.bean.User;
import com.xlr.demo.util.memcached.MenacedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xlr
 * @Date: Created in 下午3:40 2017/8/30
 */
@Controller
public class UserController {

    @Autowired
    MenacedUtil menacedUtil;

    @ResponseBody()
    @RequestMapping("/getUser")
    public String getUsers(){
        ModelAndView mv = new ModelAndView();
        User user = new User();
        user.setAge(11);
        user.setPassword("tom");
        user.setUserName("tom");
        System.out.println("menacedUtil: "+menacedUtil);
        if(menacedUtil.setToCache("user","",user)){
            mv.addObject("message","存入成功");
            System.out.println("存入成功");
        }else {
            System.out.println("存入失败");
        }

        return "Login";
    }

    @RequestMapping("/login")
    public ModelAndView login(){
        System.out.println( "进入login方法" );
        ModelAndView mv = new ModelAndView(  );
        mv.addObject(   "message","这是一次跳转" );
        return mv;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView getLoginPage(@RequestParam(value = "error", required = false) boolean error) {
        System.out.println( "error = [" + "进入方法内部" + "]" );
        String errorValue = null;
        if (error == true) {
            errorValue = "You have entered an invalid username or password!";
        } else {
            errorValue = "";
        }
        Map<String, String> model = new HashMap<String, String>();
        model.put("error", errorValue);
        ModelAndView mav = new ModelAndView("carInfo", model);
        return mav;
    }
}
