package com.xc.dubbo01.controller.rest;


import com.xc.dubbo01.Stas;
import com.xc.dubbo01.entity.Permission;
import com.xc.dubbo01.service.IPermissionService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manager/permission")
public class PermissionManagerRestController {

    @Reference(version = "1.0.0")
    IPermissionService permServ;

    @RequestMapping("/update")
    public Stas perModify(@RequestBody Permission per) {
        System.out.println("per: " + per);
        return permServ.update(per);

    }
}
