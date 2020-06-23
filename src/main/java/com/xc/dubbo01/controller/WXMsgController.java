package com.xc.dubbo01.controller;

import com.xc.dubbo01.entity.WXConfig;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import weixin.popular.api.MessageAPI;
import weixin.popular.api.QrcodeAPI;
import weixin.popular.bean.message.templatemessage.TemplateMessage;
import weixin.popular.bean.message.templatemessage.TemplateMessageResult;
import weixin.popular.bean.qrcode.QrcodeTicket;
import weixin.popular.support.TokenManager;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("msg")
public class WXMsgController {

    @Autowired
    WXConfig wxconf;

    @RequestMapping("/tem")
    @ResponseBody
    public TemplateMessageResult list(){
        TemplateMessage temMess = new TemplateMessage();
        temMess.setTouser("o2r8C1tiZqfKrdaRpAe_ZvPIhlV8");
        temMess.setTemplate_id("hS5zrkILEbuhkW8Bj5MaKl5qMsA-4eOj7FTBRj74hE0");
        temMess.setUrl("http://6mhp7m.natappfree.cc/profile");
        TemplateMessageResult temMessResult = MessageAPI.messageTemplateSend(TokenManager.getDefaultToken(),temMess);
        return temMessResult;

    }

    @RequestMapping("/img")
    @ResponseBody
    public Object getImage(HttpServletRequest req, HttpServletResponse rep) throws IOException {
        QrcodeTicket qrcodeTicket = QrcodeAPI.qrcodeCreateTemp(TokenManager.getDefaultToken(),60480,"123");
        System.out.println(ToStringBuilder.reflectionToString(qrcodeTicket));

        BufferedImage showQrcode = QrcodeAPI.showqrcode(qrcodeTicket.getTicket());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(showQrcode,"png",os);
        byte b[] =os.toByteArray();

        rep.getOutputStream().write(b);
        //qrcodeTicket.getTicket().getBytes();
        return qrcodeTicket;

    }


}
