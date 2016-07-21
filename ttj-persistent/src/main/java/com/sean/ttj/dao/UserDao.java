package com.sean.ttj.dao;

import com.sean.ttj.model.UserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by sean on 2016/7/12.
 */
@Repository
public interface UserDao {
    UserModel queryById(Long id);

    UserModel queryByNamePwd(@Param("name") String name, @Param("pwd") String pwd);
}
