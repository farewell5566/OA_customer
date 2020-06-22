package com.xc.dubbo01.service;

import com.github.pagehelper.PageInfo;
import com.xc.dubbo01.Stas;
import com.xc.dubbo01.entity.Permission;

import java.util.List;

public interface IPermissionService {
    PageInfo<Permission> findAll(int pageNum, Integer pageSize);

    Permission findById(Integer id);

    Stas update(Permission per);

    List<Permission> findName();

    List<Permission> findAll();
}
