package com.xc.dubbo01.service;

import com.github.pagehelper.PageInfo;
import com.xc.dubbo01.Stas;
import com.xc.dubbo01.entity.Role;

public interface IRoleService {
    PageInfo<Role> findAll(int pageNum, Integer pageSize);

    Role findById(Integer id);

    Stas updateRolePer(int id, int[] idData);
}
