package com.xc.dubbo01.controller;

import com.xc.dubbo01.entity.WXConfig;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


@Controller
public class WXController {

    @Autowired
    WXConfig wxconf;

    Logger logger = LoggerFactory.getLogger(WXController.class);


    @RequestMapping("/sig")
    @ResponseBody
    public String sig(@RequestParam Map<String ,String> param, HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        logger.info("---");
        ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();


        String signature = param.get("signature");
        String echostr = param.get("echostr");
        String timestamp = param.get("timestamp");
        String nonce = param.get("nonce");
        logger.info("start");
        logger.info(signature);
        logger.info(echostr);
        logger.info(timestamp);
        logger.info(nonce);
        logger.info("end");
        String token = wxconf.getToken();

        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp)){
            outputStreamWrite(outputStream,"failed request");
            return "";
        }

        // 验证请求签名
        if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
            System.out.println("The request signature is invalid");
            return;
        }


        return "验证";
    }

    private boolean outputStreamWrite(OutputStream outputStream,String text){
        try {
            outputStream.write(text.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
