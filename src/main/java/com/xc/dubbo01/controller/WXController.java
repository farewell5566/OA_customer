package com.xc.dubbo01.controller;

import com.xc.dubbo01.entity.WXConfig;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import weixin.popular.api.MenuAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLImageMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.TokenManager;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;


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

    public static ExpireKey expireKey = new DefaultExpireKey();



    String menuJson =" {\n" +
            "     \"button\":[\n" +
            "     {\t\n" +
            "          \"type\":\"click\",\n" +
            "          \"name\":\"今日歌曲\",\n" +
            "          \"key\":\"V1001_TODAY_MUSIC\"\n" +
            "      },\n" +
            "      {\n" +
            "           \"name\":\"菜单\",\n" +
            "           \"sub_button\":[\n" +
            "           {\t\n" +
            "               \"type\":\"view\",\n" +
            "               \"name\":\"搜索\",\n" +
            "               \"url\":\"http://www.soso.com/\"\n" +
            "            },\n" +
            "            {\t\n" +
            "               \"type\":\"view\",\n" +
            "               \"name\":\"哈哈哈\",\n" +
            "               \"url\":\"http://6mhp7m.natappfree.cc/\"\n" +
            "            },\n" +
            "            {\n" +
            "               \"type\":\"click\",\n" +
            "               \"name\":\"赞一下我们\",\n" +
            "               \"key\":\"V1001_GOOD\"\n" +
            "            }]\n" +
            "       }]\n" +
            " } ";


    String menuJson2 = " {\n" +
            "     \"button\":[\n" +
            "     {\t\n" +
            "          \"type\":\"click\",\n" +
            "          \"name\":\"今日歌曲\",\n" +
            "          \"key\":\"V1001_TODAY_MUSIC\"\n" +
            "      },\n" +
            "       {\n" +
            "            \"name\": \"发图\", \n" +
            "            \"sub_button\": [\n" +
            "                {\n" +
            "                    \"type\": \"pic_sysphoto\", \n" +
            "                    \"name\": \"系统拍照发图\", \n" +
            "                    \"key\": \"rselfmenu_1_0\", \n" +
            "                   \"sub_button\": [ ]\n" +
            "                 }, \n" +
            "                {\n" +
            "                    \"type\": \"pic_photo_or_album\", \n" +
            "                    \"name\": \"拍照或者相册发图\", \n" +
            "                    \"key\": \"rselfmenu_1_1\", \n" +
            "                    \"sub_button\": [ ]\n" +
            "                }, \n" +
            "                {\n" +
            "                    \"type\": \"pic_weixin\", \n" +
            "                    \"name\": \"微信相册发图\", \n" +
            "                    \"key\": \"rselfmenu_1_2\", \n" +
            "                    \"sub_button\": [ ]\n" +
            "                }\n" +
            "            ]\n" +
            "        }]\n" +
            " }";

    @RequestMapping("/sig")
    @ResponseBody
    public void sig(@RequestParam Map<String ,String> param, HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        logger.info("---");
        ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();

        String signature = param.get("signature");
        String echostr = param.get("echostr");
        String timestamp = param.get("timestamp");
        String nonce = param.get("nonce");

        logger.info(signature);
        logger.info(echostr);
        logger.info(timestamp);
        logger.info(nonce);

        String token = wxconf.getToken();

        if (StringUtils.isEmpty(signature) || StringUtils.isEmpty(timestamp)){
            outputStreamWrite(outputStream,"failed request");
            return ;
        }

        // 验证请求签名
        if (!signature.equals(SignatureUtil.generateEventMessageSignature(token, timestamp, nonce))) {
            System.out.println("The request signature is invalid");
            return;
        }
        //  这一步返回微信服务器确认信息接收到了
        if (!StringUtils.isEmpty(echostr))
            outputStreamWrite(outputStream,echostr);


        //这一步处理上传信息：
        if (inputStream!=null){
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class,inputStream);
            logger.info("messageInfo : " + ToStringBuilder.reflectionToString(eventMessage));

            String key = eventMessage.getFromUserName() + "__" + eventMessage.getToUserName() + "__" + eventMessage.getMsgId() + "__" + eventMessage.getCreateTime();
            if (expireKey.exists(key))
                logger.info("key已存在，不做重复处理。");
            else {
                expireKey.add(key);
                //发文字
/*                XMLTextMessage xmlTextMess = new XMLTextMessage(eventMessage.getFromUserName(),eventMessage.getToUserName(),
                        "你猜猜我是谁");
                xmlTextMess.outputStreamWrite(outputStream);*/

                XMLTextMessage xmlTextMess = new XMLTextMessage(eventMessage.getFromUserName(),eventMessage.getToUserName(),
                        "http://baidu.com");
                    //"请先<a herf=http://6mhp7m.natappfree.cc/profile> 完善信息 </a>");
                xmlTextMess.outputStreamWrite(outputStream);

                //发图这里的mediaID是上传图片时候留下的。
/*                XMLImageMessage xmlImagemess = new XMLImageMessage(eventMessage.getFromUserName(),eventMessage.getToUserName(),
                        "-k0mzIqfGkvVBW_rKOyRCP9nx9MxUTcfup73NnXlb4v-63eqofzDobff_tszx6LN");

                xmlImagemess.outputStreamWrite(outputStream);*/
            }
        }

        return ;
    }

    @RequestMapping("/menu")
    @ResponseBody
    public BaseResult menu(){
        BaseResult baseResult = MenuAPI.menuCreate(TokenManager.getDefaultToken(),menuJson2);
        return baseResult;
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
