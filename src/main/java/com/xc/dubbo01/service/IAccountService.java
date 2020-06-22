package com.xc.dubbo01.service;

import com.github.pagehelper.PageInfo;
import com.xc.dubbo01.Stas;
import com.xc.dubbo01.entity.Account;

public interface IAccountService {
    Account findByLoginNameAndPassword(String loginName, String password);

    PageInfo<Account> findAll(int pageNum, Integer pageSize);

    Stas deleteById(int id);

    void updataAccount(Account acc);
}
