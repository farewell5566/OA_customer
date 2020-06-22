package com.xc.dubbo01.controller;


import com.xc.dubbo01.service.ISayService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class demo {


    @Reference(version = "1.0.0")
    ISayService serv;

    @RequestMapping("/say")
    @ResponseBody
    public String say(String name){
        return name + " 收到了";
    }


}
