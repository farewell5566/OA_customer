package com.xc.dubbo01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WXProfileController {

    @RequestMapping("/profile")
    public String profile(){

        return "wx/profile";
    }
}
