package com.xc.dubbo01.listener;

import com.xc.dubbo01.entity.WXConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import weixin.popular.support.TicketManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class TicketManagerListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(TicketManagerListener.class);

    @Autowired
    WXConfig wxConf;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("----------TicketmanagerListener-------contextInitialized-------");
        TicketManager.init(wxConf.getAppID(),15,60*119);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("----------TicketmanagerListener-------contextDestroyed-------");
        TicketManager.destroyed();
    }
}
