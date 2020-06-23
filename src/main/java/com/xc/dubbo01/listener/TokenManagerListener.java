package com.xc.dubbo01.listener;

import com.xc.dubbo01.entity.WXConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import weixin.popular.support.TicketManager;
import weixin.popular.support.TokenManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class TokenManagerListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(TokenManagerListener.class);

    @Autowired
    WXConfig wxConf;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("----------TicketmanagerListener-------contextInitialized-------");
        TokenManager.init(wxConf.getAppID(),wxConf.getAppsecret());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("----------TicketmanagerListener-------contextDestroyed-------");
        TokenManager.destroyed();
    }
}
