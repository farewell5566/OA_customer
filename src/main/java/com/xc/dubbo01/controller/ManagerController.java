package com.xc.dubbo01.controller;


import com.github.pagehelper.PageInfo;
import com.xc.dubbo01.Stas;
import com.xc.dubbo01.entity.Account;
import com.xc.dubbo01.entity.Permission;
import com.xc.dubbo01.entity.Role;
import com.xc.dubbo01.service.IAccountService;
import com.xc.dubbo01.service.IPermissionService;
import com.xc.dubbo01.service.IRoleService;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Reference(version = "1.0.0")
    IAccountService accServ;

    @Reference(version = "1.0.0")
    IPermissionService perServ;

    @Reference(version = "1.0.0")
    IRoleService roleServ;

    @RequestMapping("/accountList")
    public String listAll(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "4") Integer pageSize, Model data) {
        PageInfo<Account> pageData = accServ.findAll(pageNum, pageSize);
        data.addAttribute("pageData", pageData);
        return "manager/accountList";
    }

    @RequestMapping("/permissionList")
    public String permissionlistAll(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "4") Integer pageSize, Model data) {
        PageInfo<Permission> pageData = perServ.findAll(pageNum, pageSize);
        System.out.println(pageData);
        data.addAttribute("pageData", pageData);
        return "manager/permissionList";
    }


    @RequestMapping("/roleList")
    public String roleList(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "4") Integer pageSize, Model data) {

        PageInfo<Role> roleData = roleServ.findAll(pageNum, pageSize);
        System.out.println(ToStringBuilder.reflectionToString(roleData));
        data.addAttribute("pageData", roleData);
        return "manager/roleList";
    }


    @RequestMapping("/deleteById")
    @ResponseBody
    public Stas deleteById(int id) {
        System.out.println("deleteById = " + id);
        Stas status = accServ.deleteById(id);
        System.out.println(status);
        return status;
    }


    @RequestMapping("/permissionModify")
    public String permissionlistModify(@RequestParam Integer id, Model data) {
        System.out.println(id);
        Permission per = perServ.findById(id);
        System.out.println(per);
        data.addAttribute("per", per);
        //return "index";
        return "/manager/permissionModify";
    }


    @RequestMapping("/rolePermission")
    public String roleModify(@RequestParam Integer id, Model data) {

        Role role = roleServ.findById(id);
        List<Permission> pers = perServ.findAll();

        System.out.println("-----------------------");
        System.out.println(role.getPermissions().size());
        System.out.println(ToStringBuilder.reflectionToString(role));
        data.addAttribute("role", role);
        data.addAttribute("permissions", pers);

        //return "index";
        return "manager/rolePermission";
    }


    @RequestMapping("/permissionAdd")
    public String add() {
        System.out.println("modify");

        return "/manager/permissionModify";
    }


}
