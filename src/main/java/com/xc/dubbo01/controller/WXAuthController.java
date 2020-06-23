package com.xc.dubbo01.controller;

import com.xc.dubbo01.entity.WXConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import weixin.popular.api.SnsAPI;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class WXAuthController {

    @Autowired
    WXConfig wxconf;


    @RequestMapping(value = "")
    public String list(@RequestParam Map<String,String >param, HttpServletRequest request){

        System.out.println("come into auth");
        String code = param.get("code");
        SnsToken stoken = SnsAPI.oauth2AccessToken(wxconf.getAppID(),wxconf.getAppsecret(),code);
        User user = SnsAPI.userinfo(stoken.getAccess_token(),wxconf.getAppID(),"zh_CN");

        request.getSession().setAttribute("user",user);
        System.out.println(param.get("uri"));
        return "redirect:" + param.get("uri");
    }
}
