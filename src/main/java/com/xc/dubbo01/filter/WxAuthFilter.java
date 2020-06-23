package com.xc.dubbo01.filter;

import com.xc.dubbo01.entity.WXConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import weixin.popular.bean.user.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "WxAuthFilter",urlPatterns ="/profile/*")
public class WxAuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(WxAuthFilter.class);

    String redictUri = "http://6mhp7m.natappfree.cc/auth";

    @Autowired
    WXConfig wxconf;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("-----WXFilter Init-----");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse respone = (HttpServletResponse) rep;

        //1.判断用户登录状态，检查session里有没有user对象，
        User  user =  (User)request.getSession().getAttribute("user");

        //2判断是否登录，没有则跳转登录网页
        if(user ==null){
            String uri = request.getRequestURI();
            String url ="https://open.weixin.qq.com/connect/oauth2/authorize?" +
                    "appid="+ wxconf.getAppID() +
                "&redirect_uri=http://6mhp7m.natappfree.cc/auth" +
                    "?uri=" + uri+
                    "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
            respone.sendRedirect(url);
        }else{
            logger.info("use " + ToStringBuilder.reflectionToString(user));
        }

        //https://open.weixin.qq.com/connect/oauth2/authorize?
        // appid=APPID&
        // redirect_uri=REDIRECT_URI&
        // response_type=code&
        // scope=SCOPE&state=STATE#wechat_redirect

        //3登录调用


        chain.doFilter(req,rep);
    }
}
