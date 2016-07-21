package com.sean.ttj.service.impl;

import com.sean.ttj.dao.UserDao;
import com.sean.ttj.model.UserModel;
import com.sean.ttj.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by sean on 2016/7/12.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public UserModel queryById(Long id) {
        return userDao.queryById(id);
    }

    @Override
    public UserModel queryByNamePwd(String name, String pwd) {
        return userDao.queryByNamePwd(name, pwd);
    }
}
