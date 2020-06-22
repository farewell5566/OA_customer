package com.xc.dubbo01.controller.rest;


import com.xc.dubbo01.Stas;
import com.xc.dubbo01.service.IRoleService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/manager/role")
public class RoleManagerRestController {

    @Reference(version = "1.0.0")
    IRoleService roleServ;

    @RequestMapping("/update")
    public Stas perModify(@RequestParam int[] idData, @RequestParam int id) {
        System.out.println("idData: " + idData.length);

        System.out.println("idData: " + ToStringBuilder.reflectionToString(idData));
        System.out.println("id: " + id);

        return roleServ.updateRolePer(id, idData);

    }
}
