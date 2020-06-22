package com.xc.dubbo01.controller;



import com.github.pagehelper.PageInfo;
import com.xc.dubbo01.Stas;
import com.xc.dubbo01.entity.Account;
import com.xc.dubbo01.service.IAccountService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/account")
@Configuration
/*@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)*/
public class AccountController {


    @Reference(version = "1.0.0")
    IAccountService accServ;

    @RequestMapping("/login")
    public String login() {
        return "account/login";
    }

/*
    @Autowired
    FastFileStorageClient fs;
*/


    @RequestMapping("/validataAccount")
    @ResponseBody
    public String validataAccount(String loginName, String password, HttpServletRequest request) {
        Account acc = accServ.findByLoginNameAndPassword(loginName, password);
        System.out.println("账号：" + loginName);
        System.out.println("密码：" + password);
        if (acc != null) {
            request.getSession().setAttribute("account", acc);
            return "success";
        } else
            return "error";
    }

    @RequestMapping("/logout")
    public String logOut(HttpServletRequest request) {
        request.getSession().removeAttribute("account");
        return "index";
    }

    @RequestMapping("/profile")
    //@ResponseBody
    public String profile() {
        System.out.println("weisha");
        return "account/profile";
    }


    @RequestMapping("/list")
    public String listAll(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "4") Integer pageSize, Model data) {
        PageInfo<Account> pageData = accServ.findAll(pageNum, pageSize);
        data.addAttribute("pageData", pageData);
        return "account/list";
    }

    @RequestMapping("/deleteById")
    @ResponseBody
    public Stas deleteById(int id) {
        System.out.println("deleteById = " + id);
        Stas status = accServ.deleteById(id);
        System.out.println(status);
        return status;
    }

/*    @RequestMapping("/down")
    @ResponseBody
    public ResponseEntity<byte[]> down() {
        DownloadByteArray cd = new DownloadByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "weisha");
        byte[] bs = fs.downloadFile("group1", "M00/00/00/wKhqWF7gPNaAN7fJAADDpqwCpLM858_big.png", cd);
        return new ResponseEntity<>(bs, headers, HttpStatus.OK);

    }*/


/*    @RequestMapping("/uploadImage")
    public String uploadImage(String password, MultipartFile filename, HttpServletRequest request) throws IOException {

        URL url1 = new URL("http://www.baidu.com");
        String aa = loadURL(url1);

        URL url2 = new URL("http://192.168.106.88/group1/M00/00/00/wKhqWF7mtA-AYNErAAAib-i5DLU495.log");
        String aa2 = loadURL(url2);
        try {
            Account acc = (Account) request.getSession().getAttribute("account");
            //定位项目路径，用于war，不能用在jar，用在jar的话，需要在项目外边进性生成
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            File upload = new File(path.getAbsoluteFile(), "static/upload/");

            //设置文件保留的绝对路径

            String pathName = "D:/upload";
            System.out.println("11111111111111111111111111111111");

            Set<MetaData> metaDataSet = new HashSet<MetaData>();
            metaDataSet.add(new MetaData("Author", "yimingge"));
            metaDataSet.add(new MetaData("CreateDate", "2016-01-05"));
            Set<MetaData> metaDataSet1 = new HashSet<MetaData>();
            metaDataSet1.add(new MetaData("Author", "XC"));
            metaDataSet1.add(new MetaData("Data", "2020.0610"));
            try {
                StorePath uploadFile = null;
                uploadFile = fs.uploadFile(filename.getInputStream(), filename.getSize(),
                        FilenameUtils.getExtension(filename.getOriginalFilename()), metaDataSet);
                System.out.println(ToStringBuilder.reflectionToString(uploadFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //之前保存文件
            //filename.transferTo(new File(pathName+ "/" + filename.getOriginalFilename()));

            acc.setPassword(password);
            acc.setAddress(filename.getOriginalFilename());
            accServ.updataAccount(acc);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "account/profile";
    }*/

    private String loadURL(URL url) throws IOException {
        //URL url = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        /**
         * 3.设置请求方式
         * 4.设施请求内容类型
         */
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        /**
         * 5.设置请求参数
         * 6.使用输出流发送参数
         */
        //String content="username:";
        //OutputStream outputStream = httpURLConnection.getOutputStream();
        //outputStream.write(content.getBytes());
        /**
         * 7.使用输入流接受数据
         */
        InputStream inputStream = httpURLConnection.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//此处可以用Stringbuffer等接收
        byte[] b = new byte[1024];
        int len = 0;
        while (true) {
            len = inputStream.read(b);
            if (len == -1) {
                break;
            }
            byteArrayOutputStream.write(b, 0, len);
        }
        System.out.println(byteArrayOutputStream.toString());
        return byteArrayOutputStream.toString();
    }


}
