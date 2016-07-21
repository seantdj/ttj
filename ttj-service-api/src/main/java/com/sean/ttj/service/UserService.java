package com.sean.ttj.service;

import com.sean.ttj.model.UserModel;

/**
 * Created by sean on 2016/7/12.
 */
public interface UserService {
    UserModel queryById(Long id);

    UserModel queryByNamePwd(String name, String pwd);
}
